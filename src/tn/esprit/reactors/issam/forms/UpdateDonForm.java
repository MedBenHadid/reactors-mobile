/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.List;

import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.models.Category;
import tn.esprit.reactors.chihab.services.CategoryService;
import tn.esprit.reactors.issam.models.Don;
import tn.esprit.reactors.issam.services.DonService;

/**
 *
 * @author nasri
 */
public class UpdateDonForm extends Form {

    private Form _parent;
    private Don don;
    Label titleL;
    Label descriptionL;
    Label addressL;
    Label phoneL;
    Label longL;
    Label latL;
    Label imageL;
    String filePath = "       ";

    TextField descriptionInput;
    TextField titleInput;
    TextField addressInput;
    TextField phoneInput;

    Button imagePicker;

    Button submitBtn;
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");

    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_ADD_A_PHOTO, s);
    private final FontImage DOMAINE = FontImage.createMaterial(FontImage.MATERIAL_GOLF_COURSE, UIManager.getInstance().getComponentStyle("MultiLine"));

    private final FontImage DOMAINES = FontImage.createMaterial(FontImage.MATERIAL_SPA, UIManager.getInstance().getComponentStyle("MultiLine"));

    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 8, PEOPLE.getHeight() * 8), false);
    private final MultiButton domaineDropDownButton = new MultiButton();
    private String domaineSelected;

    private List<Category> cats;
    private final Validator validator = new Validator();

    File file;
    private final ImageViewer imageViewr = new ImageViewer(PLACEHOLDER);

    private Image img = null;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public UpdateDonForm(Form parent, Don don) {
        try {
            _parent = parent;
            this.don = don;
            style();
            setup();
            System.out.println("doyyyy");
            imageViewr.setImage(URLImage.createToStorage(PLACEHOLDER, "don" + don.getImagePath(), Statics.BASE_URL + "/api/don/image/download/" + don.getImagePath(), URLImage.RESIZE_SCALE));
            // initilaize selected domlaine   
            this.cats = CategoryService.getInstance().fetchDraChnya();
            for (Category c : cats) {
                if (c.getId() == don.getDomaine()) {
                    domaineSelected = c.getNom();
                    System.out.println(domaineSelected);
                    domaineDropDownButton.setTextLine1((domaineSelected));

                    validator.addConstraint(titleInput, new RegexConstraint("^[\\w\\s]{3,10}$", ""));
                    validator.addConstraint(descriptionInput, new RegexConstraint("^[\\d\\w\\s]{8,255}$", ""));
                    validator.addConstraint(addressInput, new RegexConstraint("^[\\d\\w\\s]{3,8}$", ""));
                    validator.addConstraint(phoneInput, new RegexConstraint("^[\\d]{8}$", ""));
                    validator.addSubmitButtons(submitBtn);

                }
            }

        } catch (IOException ex) {
        }
    }

    private void setup() {
        this.setTitle("Modifier une doonation");
        setupForm();
        setupInputActions();
        setupToolbar();
    }

    private void setupInputActions() {

        this.imagePicker.addActionListener(e -> {
            Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
                filePath = (String) ev.getSource();
                if (filePath == null) {
                    ToastBar.showInfoMessage("Please pick a picture");
                } else {
                    try {
                        this.setImg(Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath)));
                        imageViewr.setImage(img);
                    } catch (IOException e1) {
                    }
                }
            }, Display.GALLERY_IMAGE);

        });

        submitBtn.addActionListener((evt)-> {
                if (don.getDomaine() == 0) {
                    ToastBar.showInfoMessage("Please provide a working sector!");
                } else {
                    try {
                        don.setImagePath(System.currentTimeMillis() + 2 + filePath.substring(filePath.lastIndexOf(".")));
                        MultipartRequest photoUploadRequest = DonService.getInstance().uploadImage(filePath, don.getImagePath());
                        photoUploadRequest.addResponseListener(code -> {
                            if (code.getResponseCode() == 200) {
                                don.setUserId(Statics.CURRENT_USER_ID);
                                don.setTitle(titleInput.getText());
                                don.setDescription(descriptionInput.getText());
                                try {
                                    don.setLon(Display.getInstance().getLocationManager().getCurrentLocation().getLongitude());
                                    don.setLat(Display.getInstance().getLocationManager().getCurrentLocation().getLatitude());
                                } catch (IOException e) {
                                }
                                don.setPhone(phoneInput.getText());
                                don.setAddress(addressInput.getText());
                                boolean result = DonService.getInstance().update(don);
                                if (result) {
                                    _parent.showBack();
                                }
                            }
                        });
                        NetworkManager.getInstance().addToQueueAndWait(photoUploadRequest);
                    } catch (IOException ex) {
                    }

                }

    

        });

        // MultiButton domaineDropDownButton =new MultiButton(domaineSelected);
    }

    private void setupForm() {
        titleL = new Label("titre: ");

        descriptionL = new Label("Description: ");
        addressL = new Label("adresse: ");
        phoneL = new Label("Telephone: ");
        longL = new Label("long: ");
        latL = new Label("lat: ");
        imageL = new Label("Image: ");

        titleInput = new TextField(don.getTitle());

        descriptionInput = new TextField(don.getDescription());
        addressInput = new TextField(don.getAddress());
        phoneInput = new TextField(don.getPhone());

        imagePicker = new Button("Choisir une image ");

        submitBtn = new Button("Modifier");

        domaineDropDownButton.setIcon(DOMAINES);

        domaineDropDownButton.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for (Category cat : cats) {
                MultiButton mb = new MultiButton(cat.getNom());
                mb.setIcon(DOMAINE);
                d.add(mb);
                mb.addActionListener(ee -> {
                    domaineDropDownButton.setTextLine1(mb.getTextLine1());
                    domaineDropDownButton.setTextLine2(mb.getTextLine2());
                    domaineDropDownButton.setIcon(mb.getIcon());
                    d.dispose();
                    domaineDropDownButton.revalidate();
                    don.setDomaine(cat.getId());
                });
            }
            d.showPopupDialog(domaineDropDownButton);
        });

        this.addAll(
                inlineFormGroup(titleL, titleInput),
                inlineFormGroup(descriptionL, descriptionInput),
                inlineFormGroup(addressL, addressInput),
                inlineFormGroup(phoneL, phoneInput),
                inlineFormGroup(domaineDropDownButton),
                inlineFormGroup(imageL, imagePicker),
                inlineFormGroup(imageViewr),
                inlineFormGroup(submitBtn)
        );
    }

    private Container inlineFormGroup(Component first, Component second) {
        Container formContainer = new Container(new BorderLayout());

        formContainer.add(BorderLayout.WEST, first);
        formContainer.add(BorderLayout.CENTER, second);

        return formContainer;
    }

    private Container inlineFormGroup(Component first) {
        Container formContainer = new Container(new BorderLayout());

        formContainer.add(BorderLayout.WEST, first);

        return formContainer;
    }

    private void style() {
        this.setLayout(BoxLayout.y());
    }

    private void setupToolbar() {
        this.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            _parent.showBack();
        });
    }

}
