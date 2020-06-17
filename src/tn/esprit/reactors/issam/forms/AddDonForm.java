package tn.esprit.reactors.issam.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import java.util.List;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.models.Category;
import tn.esprit.reactors.chihab.services.CategoryService;
import tn.esprit.reactors.issam.models.Don;
import tn.esprit.reactors.issam.services.DonService;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;

/**
 *
 * @author Issam
 */
public class AddDonForm extends Form {

    private Form _parent;

    Label titleL;
    Label descL;
    Label addressL;
    Label phoneL;
    Label imageL;
    Label longL;
    Label latL;
    String filePath;
    TextField descriptionInput;
    TextField titleInput;
    TextField addressInput;
    TextField phoneInput;
    TextField longInput;
    TextField latInput;
    Button imagePicker;
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    Don don = new Don();
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_ADD_A_PHOTO, s);
    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 8, PEOPLE.getHeight() * 8), false);
    private final MultiButton domaineDropDownButton = new MultiButton("Pick your sector...");
    private final FontImage DOMAINES = FontImage.createMaterial(FontImage.MATERIAL_SPA, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOMAINE = FontImage.createMaterial(FontImage.MATERIAL_GOLF_COURSE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOWN_ARROW = FontImage.createMaterial(FontImage.MATERIAL_ARROW_DROP_DOWN_CIRCLE, UIManager.getInstance().getComponentStyle("MultiLine1"));

    private List<Category> cats = null;
    Button submitBtn;
    File file;
    private final Validator validator = new Validator();

    private boolean domaineSelected = false;
    private ImageViewer imageViewr = new ImageViewer(PLACEHOLDER);
    private Image img = null;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public AddDonForm(Form parent) {

        _parent = parent;
        style();
        setup();

        validator.addConstraint(titleInput, new RegexConstraint("^[\\w\\s]{3,10}$", ""));
        validator.addConstraint(descriptionInput, new RegexConstraint("^[\\d\\w\\s]{8,255}$", ""));
        validator.addConstraint(addressInput, new RegexConstraint("^[\\d\\w\\s]{3,8}$", ""));
        validator.addConstraint(phoneInput, new RegexConstraint("^[\\d]{8}$", ""));
        validator.addSubmitButtons(submitBtn);

    }

    private void setup() {
        this.setTitle("Ajouter une donnation");
        setupForm();
        setupInputActions();
        setupToolbar();
    }

    private void setupInputActions() {

        this.imagePicker.addActionListener(e -> {

            /**
             * String i =
             * Capture.capturePhoto(Display.getInstance().getDisplayWidth(),
             * -1); if (i != null) { file = new File(i); } });
             *
             */
            Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {

                filePath = (String) ev.getSource();
                try {
                    if (filePath == null) {
                        ToastBar.showInfoMessage("Please provide a working sector!");
                    } else {
                        this.setImg(Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath)));
                        imageViewr.setImage(img);
                    }
                } catch (IOException e1) {
                }
            }, Display.GALLERY_IMAGE);

        });

        domaineDropDownButton.setIcon(DOMAINES);
        domaineDropDownButton.setEmblem(DOWN_ARROW);
        domaineDropDownButton.addActionListener(e -> {
            try {
                this.cats = CategoryService.getInstance().fetchDraChnya();
            } catch (IOException ex) {
            }

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
                    domaineSelected = true;

                });
            }
            d.showPopupDialog(domaineDropDownButton);
        });

        submitBtn.addActionListener((evt)
                -> {

            try {
                if (don.getDomaine() == 0) {
                    ToastBar.showInfoMessage("Please provide a working sector!");
                } else if (filePath == null) {
                    ToastBar.showInfoMessage("Please pick a picture");
                } else {
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
                            don.setImage(new File(filePath));
                            boolean result = DonService.getInstance().addTask(don);
                            if (result) {
                                _parent.showBack();
                                System.out.println(don.getDomaine());
                            }

                        }

                        addressInput.setText("");
                        titleInput.setText("");
                        phoneInput.setText("");
                        descriptionInput.setText("");
                        domaineDropDownButton.setTextLine1("Pick your sector...");

                    });
                    NetworkManager.getInstance().addToQueueAndWait(photoUploadRequest);
                }

            } catch (IOException ex) {
            }

            //} else if (Dialog.show("Valeurs entrées non valides", "Voulez vous réinitialiser les champs?", "Oui", "Non")) {
            //  resetForm();
            //}
        });
    }

    private void setupForm() {
        titleL = new Label("Titre: ");
        descL = new Label("Description: ");
        addressL = new Label("adresse: ");
        phoneL = new Label("Telephone: ");
        latL = new Label("long: ");
        longL = new Label("lat: ");

        imageL = new Label("Image: ");
        titleInput = new TextField();

        descriptionInput = new TextField();
        addressInput = new TextField();
        phoneInput = new TextField();
        latInput = new TextField();
        longInput = new TextField();

        imagePicker = new Button("Choisir une image");

        submitBtn = new Button("Ajouter");
        this.addAll(
                inlineFormGroup(titleL, titleInput),
                inlineFormGroup(descL, descriptionInput),
                inlineFormGroup(addressL, addressInput),
                inlineFormGroup(phoneL, phoneInput),
                inlineFormGroup(imageL, imagePicker),
                inlineFormGroup(domaineDropDownButton),
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

    private ActionListener callback = e
            -> {
        if (e != null && e.getSource() != null) {
            String filePath = (String) e.getSource();
            file = new File(filePath);
        }
    };

    private void resetForm() {
        descriptionInput.setText("");
        titleInput.setText("");
        addressInput.setText("");
    }

    private boolean validateForm() {
        boolean result = true;

        if (anyInputFieldIsEmpty()) {
            result = false;
        } else if (file == null) {
            result = false;
        }

        return result;
    }

    private boolean anyInputFieldIsEmpty() {
        boolean result = false;

        if (titleInput.getText().trim().isEmpty()) {
            result = true;
        } else if (descriptionInput.getText().trim().isEmpty()) {
            result = true;
        } else if (addressInput.getText().trim().isEmpty()) {
            result = true;
        }
        return result;
    }

}
