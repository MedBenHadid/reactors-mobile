/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.MultiButton;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.Hashtable;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Association;

/**
 *
 * @author bhk
 */
public class SignUpAssociation extends ReactorsForm{
    // Icons 
    private final FontImage MISSING = FontImage.createMaterial(FontImage.MATERIAL_HIGHLIGHT_OFF, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DONE_ALL = FontImage.createMaterial(FontImage.MATERIAL_DONE_ALL, UIManager.getInstance().getComponentStyle("MultiLine1"));

    // Components
    private final TextComponent nom = new TextComponent().label("Name :");
    private final TextComponent phone = new TextComponent().label("Phone number :");
    private final TextComponent location = new TextComponent().label("Location");
    private final TextComponent description = new TextComponent().label("Description").multiline(true);
    // Button
    private final MultiButton imageButton = new MultiButton("Image");
    private final MultiButton pieceButton = new MultiButton("File");
    private final Button NEXT_BUTTON = new Button("Next",MISSING);
    private final Button SUBMIT_BUTTON = new Button("Join-us");
    // Validators
    private final Validator nomValidator = new Validator();
    private final Validator phoneValidator = new Validator();
    private final Validator descValidator = new Validator();
    // Declaratives
    private Association a=new Association();
    private Image img = null;
    private File piece = null;
    public SignUpAssociation(Form previous, TextModeLayout tl) {
        super("Association sign-up",previous,tl);
        nomValidator.addConstraint(nom, new RegexConstraint("^[\\w\\s]{3,30}$",""));
        phoneValidator.addConstraint(phone, new RegexConstraint("^[\\d]{8}$",""));
        descValidator.addConstraint(description, new RegexConstraint("^[\\d\\w\\s]{3,255}$",""));
        descValidator.addSubmitButtons(NEXT_BUTTON);
        
        // TODO : TOAST BAR
        NEXT_BUTTON.setDisabledIcon(MISSING);

        this.add(tl.createConstraint().horizontalSpan(2), nom);
        this.add(tl.createConstraint().widthPercentage(30), phone);
        //this.add(tl.createConstraint().widthPercentage(70), location);
        this.add(tl.createConstraint().horizontalSpan(10), description);

        Display.getInstance().openGallery(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (ev != null && ev.getSource() != null) {
                    String filePath = (String) ev.getSource();
                    int fileNameIndex = filePath.lastIndexOf("/") + 1;
                    String fileName = filePath.substring(fileNameIndex);
                    try {
                        img = Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
             //   Display.getInstance().getLocationManager().getCurrentLocation().getL
            }
        }, Display.GALLERY_IMAGE);
        this.NEXT_BUTTON.setMaterialIcon(FontImage.MATERIAL_CHECK_CIRCLE);
        this.add(NEXT_BUTTON);
        this.show();
        this.setEditOnShow(nom.getField()); 
        this.setTintColor(5);
    }
    private final FontImage LOCATION = FontImage.createMaterial(FontImage.MATERIAL_LOCATION_CITY, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOWN_ARROW = FontImage.createMaterial(FontImage.MATERIAL_ARROW_DROP_DOWN_CIRCLE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final String[] villes = {"Ariana", "Béja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"};
    private final MultiButton villeDropDownButton = new MultiButton("Pick your city...");
    private final PickerComponent dePicker = PickerComponent.createTime(0).label("De :");
    private final PickerComponent versPicker = PickerComponent.createTime(0).label("Vers :");
    public class SignUpPhaseTwo extends ReactorsForm {
        public SignUpPhaseTwo (Form previous, TextModeLayout tl){
            super("Sign-up phase two",previous,tl);
            villeDropDownButton.setEmblem(DOWN_ARROW);
            villeDropDownButton.addActionListener(e -> {
                Dialog d = new Dialog();
                d.setLayout(BoxLayout.y());
                d.getContentPane().setScrollableY(true);
                for(int iter = 0 ; iter < villes.length ; iter++) {
                    MultiButton mb = new MultiButton(villes[iter]);
                    mb.setIcon(LOCATION);
                    d.add(mb);
                    mb.addActionListener(ee -> {
                        villeDropDownButton.setTextLine1(mb.getTextLine1());
                        villeDropDownButton.setTextLine2(mb.getTextLine2());
                        villeDropDownButton.setIcon(mb.getIcon());
                        d.dispose();
                        villeDropDownButton.revalidate();
                        a.setVille(mb.getTextLine1());
                    });
                }
                d.showPopupDialog(villeDropDownButton);
            });
            this.add(new Label("Working hours :"));
            this.add(tl.createConstraint().horizontalSpan(2), dePicker);
            this.add(tl.createConstraint().horizontalSpan(2), versPicker);
            this.add(new Label("City :"));
            this.add(tl.createConstraint().horizontalSpan(10),villeDropDownButton);
            this.show();
        }
    }
    public class SignUpPhaseThree extends ReactorsForm {
        public SignUpPhaseThree (Form previous, TextModeLayout tl){
            super("Association sign-up",previous,tl);

            this.show();
        }
    }
}
