/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.malek.models.User;

/**
 *
 * @author bhk
 */
public class SignUpAssociation extends ReactorsForm{
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, s);
    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 6, PEOPLE.getHeight() * 6), false); 
    private final ImageViewer imageViewr = new ImageViewer(PLACEHOLDER);
    // Icons 
    private final FontImage MISSING = FontImage.createMaterial(FontImage.MATERIAL_HIGHLIGHT_OFF, UIManager.getInstance().getComponentStyle("MultiLine1"));
    // Components
    private final TextComponent nom = new TextComponent().label("Name :");
    private final TextComponent phone = new TextComponent().label("Phone number :");
    private final TextComponent description = new TextComponent().label("Description").multiline(true);
    // Button
    private final Button imageButton = new Button("Profile image");
    private final Button pieceButton = new Button("File prooving legitimacy");
    private final Button nextButton = new Button("Next",MISSING);
    
    // Validators
    private final Validator validator = new Validator();

    // Declaratives
    private Association a=new Association();
    private String file,image = null;

    public String getFile() {
        return file;
    }

    public String getImage() {
        return image;
    }
    /**
    * passed user has to have an id
    * @param User 
    * @author Chihab
    */
    private SignUpAssociation(Form previous, TextModeLayout tl, User manager) {
        super("Association sign-up",previous,tl);
        this.validator.addConstraint(nom, new RegexConstraint("^[\\w\\s]{3,30}$",""));
        this.validator.addConstraint(phone, new RegexConstraint("^[\\d]{8}$",""));
        this.validator.addConstraint(description, new RegexConstraint("^[\\d\\w\\s]{3,255}$",""));
        this.validator.addSubmitButtons(nextButton);
        // TODO : TOAST BAR
        
        
        this.imageButton.addActionListener(e -> {
            Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
                if (ev != null && ev.getSource() != null) {
                    image = (String) ev.getSource();
                    try {
                        imageViewr.setImage(Image.createImage(FileSystemStorage.getInstance().openInputStream(image)));
                    } catch (IOException e1) {
                    }
                }
            }, Display.GALLERY_IMAGE);
        });
        
        
        
                
        this.pieceButton.addActionListener(e -> {
            Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
                if (ev != null && ev.getSource() != null) {
                    file = (String) ev.getSource();
                    this.add("File selected");
                }
            }, Display.GALLERY_IMAGE);
        });
        
        
        /**this.pieceButton.addActionListener(e -> {
            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf, .png", e2-> {
                    file = (String)e2.getSource();
                    if (file == null) {
                        add("No file was selected");
                        revalidate();
                    } else {
                       String extension = null;
                       if (file.lastIndexOf(".") > 0) {
                           a.setPieceJustificatif(file.substring(file.lastIndexOf("/")+1));
                       }
                       add("Selected file "+a.getPieceJustificatif());
                    }
                    revalidate();
                });
            }
        });*/
        this.imageButton.setMaterialIcon(FontImage.MATERIAL_ADD);
        this.pieceButton.setMaterialIcon(FontImage.MATERIAL_FILE_UPLOAD);        
        this.nextButton.setMaterialIcon(FontImage.MATERIAL_CHECK_CIRCLE);
        this.nextButton.addActionListener(e->{
             Dialog ip = new InfiniteProgress().showInfiniteBlocking();
            if(null==this.file||null==this.image){
                ip.dispose();
                Dialog.show("Missing fields", "Please provide the association image and it's dra chnya blah blah blah", "OK", null);
            }else{
                a.setNom(nom.getText());
                a.setDescription(description.getText());
                a.setTelephone(Integer.parseInt(phone.getText()));
                ip.dispose();
                SignUpPhaseTwo.getInstance(this, tl, a,manager).showBack();
            }
        });
        

        
        this.add(tl.createConstraint().horizontalSpan(2), nom);
        this.add(tl.createConstraint().widthPercentage(30), phone);
        this.add(tl.createConstraint().horizontalSpan(10), description);
        this.add(tl.createConstraint().widthPercentage(30),imageViewr);
        this.add(tl.createConstraint().horizontalSpan(2),imageButton);
        this.add(tl.createConstraint().horizontalSpan(2),pieceButton);
        this.add(tl.createConstraint().horizontalSpan(2),nextButton);
        this.setEditOnShow(nom.getField()); 
    }
    private static SignUpAssociation instance=null;
    public static SignUpAssociation getInstance(Form previous, TextModeLayout tl, User manager) {
        if (instance == null) {
            instance = new SignUpAssociation(previous,tl,manager);
        }
        return instance;
    }
    public static SignUpAssociation getInstance() {
        return instance;
    }
    public void nullify(){
        this.instance=null;
    }
}
