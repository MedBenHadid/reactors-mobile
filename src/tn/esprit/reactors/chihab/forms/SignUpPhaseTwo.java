/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextComponent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.chihab.models.Category;
import tn.esprit.reactors.chihab.models.enums.RoleEnum;
import tn.esprit.reactors.chihab.services.AssociationService;
import tn.esprit.reactors.chihab.services.CategoryService;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.malek.services.UserService;


/**
 *
 * @author Chihab
 */
public class SignUpPhaseTwo extends ReactorsForm{
    private final FontImage LOCATION = FontImage.createMaterial(FontImage.MATERIAL_LOCATION_CITY, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOWN_ARROW = FontImage.createMaterial(FontImage.MATERIAL_ARROW_DROP_DOWN_CIRCLE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DONE_ALL = FontImage.createMaterial(FontImage.MATERIAL_DONE_ALL, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOMAINES = FontImage.createMaterial(FontImage.MATERIAL_SPA, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PLACES = FontImage.createMaterial(FontImage.MATERIAL_PLACE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOMAINE = FontImage.createMaterial(FontImage.MATERIAL_GOLF_COURSE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final String[] villes = {"Ariana", "Béja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"};
    private final MultiButton villeDropDownButton = new MultiButton("Pick your city...");
    private final MultiButton domaineDropDownButton = new MultiButton("Pick your sector...");
    private final PickerComponent dePicker = PickerComponent.createTime(0).label("From :");
    private final PickerComponent versPicker = PickerComponent.createTime(0).label("To :");
    private final TextComponent adress = new TextComponent().label("Adress :");
    private final TextComponent zipCode = new TextComponent().label("ZIP Code :");
    private final Validator validator = new Validator();
    private final Button submit = new Button("Join us",DONE_ALL);
    private List<Category> cats = new ArrayList<>();
    private boolean domaineSelected = false, villeSelected = false;
    private SignUpPhaseTwo (Form previous, TextModeLayout tl, Association a, User manager){
        super("Sign-up phase two",previous,tl);
        try {
            this.cats = CategoryService.getInstance().fetchDraChnya();
        } catch (IOException ex) {}
        validator.addConstraint(adress, new RegexConstraint("^[\\d\\w\\s]{6,255}$", "Invalid"));
        validator.addConstraint(zipCode, new RegexConstraint("^[\\d]{4}$", "Invalid"));
        validator.addSubmitButtons(submit);
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
                        domaineSelected=true;
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
                    villeSelected=true;
                });
            }
            d.showPopupDialog(villeDropDownButton);
        });
        this.add(new Label("Working hours :"));
        this.add(tl.createConstraint().horizontalSpan(2), dePicker);
        this.add(tl.createConstraint().horizontalSpan(2), versPicker);
        this.add(tl.createConstraint().horizontalSpan(10),villeDropDownButton);
        this.add(tl.createConstraint().horizontalSpan(10),domaineDropDownButton);
        this.add(tl.createConstraint().horizontalSpan(10),adress);
        this.add(tl.createConstraint().horizontalSpan(10),zipCode);
        this.add(submit);
        this.show();
        submit.addActionListener(e->{
            if(!domaineSelected){
                ToastBar.showInfoMessage("Please provide a working sector!");
            } else if(!villeSelected){
                ToastBar.showInfoMessage("Please pick your city");
            } else if((int)dePicker.getPicker().getValue()>(int)versPicker.getPicker().getValue()){
                ToastBar.showInfoMessage("Start time cant be bigger than closing time");
            }else{
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                try{
                    a.setLon(Display.getInstance().getLocationManager().getCurrentLocation().getLongitude());
                    a.setLat(Display.getInstance().getLocationManager().getCurrentLocation().getLatitude());
                }catch(IOException ee){
                    a.setLon(10.0);
                    a.setLat(30.0);
                }
                a.setApprouved(true);
                a.setCodePostal(Integer.parseInt(zipCode.getText()));
                a.setRue(adress.getText());
                a.setHoraireTravail("De "+((int)dePicker.getPicker().getValue()/60)+" vers "+((int)versPicker.getPicker().getValue()/60));
                String image = SignUpAssociation.getInstance().getImage();
                String file = SignUpAssociation.getInstance().getFile();
                a.setPieceJustificatif(System.currentTimeMillis()+ 1 + file.substring(image.lastIndexOf(".")));
                a.setPhotoAgence(System.currentTimeMillis()+ 2 + image.substring(image.lastIndexOf(".")));
                a.setManager(manager.getId());
                try {
                    MultipartRequest photoUploadRequest = AssociationService.getInstance().uploadImage(image,a.getPhotoAgence());
                    MultipartRequest pieceUploadRequest = AssociationService.getInstance().uploadFile(file ,a.getPieceJustificatif());
                    photoUploadRequest.addResponseListener(code->{
                        System.out.println("photo code:"+code.getResponseCode());
                            if(code.getResponseCode()==200){
                                pieceUploadRequest.addResponseListener(pieceCode->{
                                    System.out.println("file code:"+pieceCode.getResponseCode());
                                    if(pieceCode.getResponseCode()==200){
                                        Response<Map> response = AssociationService.getInstance().addAssociation(a);
                                        System.out.println("Add code :"+response.getResponseCode());
                                        if(response.getResponseCode()==201){
                                            a.setId((int)(Double.parseDouble(response.getResponseData().get("id").toString())));
                                            ip.dispose();
                                            UserService.getInstance().getUser().addRole(RoleEnum.ROLE_ADMIN_ASSOCIATION);
                                            ToastBar.showMessage("Succesfully subscribed "+a.getNom(),FontImage.MATERIAL_DONE);
                                            ListAssociationsForm.getInstance(null).showForm();
                                        }else{
                                            ip.dispose();
                                            ToastBar.showMessage(response.getResponseErrorMessage(),FontImage.MATERIAL_DONE);
                                        }
                                    }else{
                                        ip.dispose();
                                        ToastBar.showErrorMessage("Failed to upload file !"+ pieceCode.getMessage());   
                                    }
                                });
                                NetworkManager.getInstance().addToQueueAndWait(pieceUploadRequest);
                            } else {
                                ip.dispose();
                                ToastBar.showErrorMessage("Failed to upload profile image file !"+ code.getMessage());
                            }
                        });
                    NetworkManager.getInstance().addToQueueAndWait(photoUploadRequest);
                } catch (IOException ex) {
                    ToastBar.showErrorMessage("Failed to connect to server !");
                }
            }
        });
    }
    private static SignUpPhaseTwo instance=null;
    public static SignUpPhaseTwo getInstance(Form previous, TextModeLayout tl,Association a, User manager) {
        if (instance == null) {
            instance = new SignUpPhaseTwo(previous,tl,a,manager);
        }
        return instance;
    }
    public void nullify(){
        this.instance=null;
    }
}

