/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.io.rest.Response;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.chihab.models.Category;
import tn.esprit.reactors.chihab.services.AssociationService;
import tn.esprit.reactors.chihab.services.CategoryService;

/**
 *
 * @author Chihab
 */
public class AssociationManagerForm extends ReactorsForm{
    // UI
        // Buttons
        private final MultiButton update = new MultiButton("Update fields");
        // ICONS 
        private final FontImage LOCATION = FontImage.createMaterial(FontImage.MATERIAL_LOCATION_CITY, UIManager.getInstance().getComponentStyle("MultiLine"));
        private final FontImage DOWN_ARROW = FontImage.createMaterial(FontImage.MATERIAL_ARROW_DROP_DOWN_CIRCLE, UIManager.getInstance().getComponentStyle("MultiLine"));
        private final FontImage DOMAINES = FontImage.createMaterial(FontImage.MATERIAL_SPA, UIManager.getInstance().getComponentStyle("MultiLine"));
        private final FontImage PLACES = FontImage.createMaterial(FontImage.MATERIAL_PLACE, UIManager.getInstance().getComponentStyle("MultiLine"));
        private final FontImage DOMAINE = FontImage.createMaterial(FontImage.MATERIAL_GOLF_COURSE, UIManager.getInstance().getComponentStyle("MultiLine"));
    // Upload buttons 
    private final Button imageButton = new Button("Update profile image");
    private final Button locationButton = new Button("Update association location to your current location");
    
    // Text components
    private final TextComponent descriptionTextArea = new TextComponent().label("Description :");
    private final TextComponent adressTextArea = new TextComponent().label("Adress :");
    private final TextComponent zipCodeTextArea = new TextComponent().label("ZIP Code :");
    private final TextComponent phoneTextArea = new TextComponent().label("Phone number :");
    // Data
    private final String[] villes = {"Ariana", "Béja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"};
    // Pickers 
    private final MultiButton villeDropDownButton = new MultiButton();
    private final MultiButton domaineDropDownButton = new MultiButton();
    // Validators
    private final Validator validator = new Validator();
    
    private ImageViewer imageViewer = new ImageViewer();
    private List<Category> cats;
    private String domaineSelected;
    public AssociationManagerForm(Form previous, Association a) {
        super(null, previous);
        // Toolbar
        this.getToolbar().addCommandToOverflowMenu("Requests", FontImage.createMaterial(FontImage.MATERIAL_PEOPLE, UIManager.getInstance().getComponentStyle("TitleCommand")), (e) ->{
            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
            InvitesRequestsForm m = new InvitesRequestsForm("Requests",this,true);
            ip.dispose();
            m.showBack();
        });
        this.getToolbar().addCommandToOverflowMenu("Your missions", FontImage.createMaterial(FontImage.MATERIAL_VIEW_AGENDA, UIManager.getInstance().getComponentStyle("TitleCommand")), (e) ->{
            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
            // TODO : Mohamed call to his mission main page
            Form m = new Form();
            ip.dispose();
            m.showBack();
        });
        // Initializing data
        InputStream is =  null;
        try {
            this.cats = CategoryService.getInstance().fetchDraChnya();
            for (Category c : cats){
                if(c.getId()==a.getDomaine())
                    domaineSelected=c.getNom();
            }
            is = Storage.getInstance().createInputStream(String.valueOf("association"+a.getId()));
            imageViewer.setImage(EncodedImage.create(is, is.available()));
        } 
        catch (IOException ex) {}
        finally {try {is.close();} catch (IOException ex) {}}
        // Upload buttons event handlers
        this.imageButton.addActionListener(e -> {
            Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
                if (ev != null && ev.getSource() != null) {
                    String filePath = (String) ev.getSource();
                    Dialog blocker = new InfiniteProgress().showInfiniteBlocking();
                    try {
                        FileSystemStorage.getInstance().rename(filePath, "association"+String.valueOf(a.getId()));
                        imageViewer.setImage(Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath)));
                        NetworkManager.getInstance().addToQueueAndWait(AssociationService.getInstance().uploadImage(filePath,a.getPhotoAgence()));
                        ToastBar.showInfoMessage("Succesfully uploaded image"); 
                    } catch (IOException e1) {
                        ToastBar.showErrorMessage("Error uploading image! check your internet connection", 200);
                    } 
                    blocker.dispose();
                }
            }, Display.GALLERY_IMAGE);
        });
        this.locationButton.addActionListener(e->{
            Dialog c = new InfiniteProgress().showInfiniteBlocking();
            try {
                a.setLat(Display.getInstance().getLocationManager().getCurrentLocation().getLatitude());
                a.setLon(Display.getInstance().getLocationManager().getCurrentLocation().getLongitude());
                AssociationService.getInstance().update(a);
                ToastBar.showInfoMessage("Succesfully updated location"); 
            } catch (IOException ex) {
                ToastBar.showErrorMessage("Error updating location sama7na bro", 200);
            }
            c.dispose();
        });
        // Button icons 
        this.imageButton.setMaterialIcon(FontImage.MATERIAL_FILE_UPLOAD);
        this.locationButton.setMaterialIcon(FontImage.MATERIAL_PLACE);
        this.update.setIcon(FontImage.createMaterial(FontImage.MATERIAL_UPDATE, UIManager.getInstance().getComponentStyle("TitleCommand")));
        // Pickers
        villeDropDownButton.setTextLine1(a.getVille());
        villeDropDownButton.setTextLine3("Update your city");
        domaineDropDownButton.setTextLine1(domaineSelected);
        domaineDropDownButton.setTextLine3("Update your sector");
        domaineDropDownButton.setIcon(DOMAINES);
        domaineDropDownButton.setEmblem(DOWN_ARROW);
        domaineDropDownButton.addActionListener(e -> {
                Dialog d = new Dialog();
                d.setLayout(BoxLayout.y());
                d.getContentPane().setScrollableY(true);
                for(Category cat : cats) {
                    MultiButton mb = new MultiButton(cat.getNom());
                    mb.setIcon(DOMAINE);
                    d.add(mb);
                    mb.addActionListener(ee -> {
                        domaineDropDownButton.setTextLine1(mb.getTextLine1());
                        domaineDropDownButton.setTextLine2(mb.getTextLine2());
                        domaineDropDownButton.setIcon(mb.getIcon());
                        d.dispose();
                        domaineDropDownButton.revalidate();
                        a.setDomaine(cat.getId());
                    });
                }
                d.showPopupDialog(domaineDropDownButton);
        });
        villeDropDownButton.setIcon(PLACES);
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
        // Fields 
        descriptionTextArea.text(a.getDescription());
        phoneTextArea.text(String.valueOf(a.getTelephone()));
        adressTextArea.text(a.getRue());
        zipCodeTextArea.text(String.valueOf(a.getCodePostal()));
        
        
        
        // Validators"
        this.validator.addConstraint(phoneTextArea, new RegexConstraint("^[\\d]{8}$","Invalid phone number")).addConstraint(descriptionTextArea, new RegexConstraint("^[\\d\\w\\s]{3,255}$","Invalid description")).addConstraint(adressTextArea, new RegexConstraint("^[\\d\\w\\s]{6,255}$", "Invalid adress")).addConstraint(zipCodeTextArea, new RegexConstraint("^[\\d]{4}$", "Invalid zip code"));
        this.validator.addSubmitButtons(update);
        // Adding components
        this.add(descriptionTextArea).add(phoneTextArea).add(adressTextArea).add(zipCodeTextArea).add(villeDropDownButton).add(domaineDropDownButton);
        this.add(update);
        this.update.addActionListener(al->{
            a.setDescription(descriptionTextArea.getText());
            a.setRue(adressTextArea.getText());
            a.setCodePostal(Integer.valueOf(zipCodeTextArea.getText()));
            a.setTelephone(Integer.valueOf(phoneTextArea.getText()));
            
            Dialog bd = new InfiniteProgress().showInfiniteBlocking();
            Response<Map> response = AssociationService.getInstance().update(a);
            bd.dispose();
            if(response.getResponseCode()==200)
                ToastBar.showMessage("Succesfully updated your info !!",FontImage.MATERIAL_DONE);
            else 
                ToastBar.showErrorMessage("Failed to update :"+response.getResponseErrorMessage());
        });
    }
}
