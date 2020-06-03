/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.controllers.MembershipController;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.chihab.services.AssociationService;
import tn.esprit.reactors.malek.services.UserService;

/**
 *
 * @author Chihab
 */
public class MyAssociationHomeForm extends ReactorsForm{
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, s);
    private final FontImage BUTTON_RIGHT = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, s);
    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 1, PEOPLE.getHeight() * 1), false); 
    private final FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE, UIManager.getInstance().getComponentStyle("TitleCommand"));
    private UserService uS = UserService.getInstance();
    private MyAssociationHomeForm(Form previous) {
        super(null,previous);
        this.getToolbar().addCommandToRightBar("Invites", icon, ev->{
            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
            InvitesRequestsForm m = new InvitesRequestsForm("Invites",this,false);
            ip.dispose();
            m.showBack();
        });
        if(uS.getUser().isAssociationAdmin()){
            this.add(new Label("Associations you manage"));
            Association managed = AssociationService.getInstance().findAssByManager(uS.getUser().getId());
            MultiButton managedAss = new MultiButton(managed.getNom());
            managedAss.setEmblem(BUTTON_RIGHT);
            managedAss.setIcon(URLImage.createToStorage(PLACEHOLDER, "association"+String.valueOf(managed.getId()), Statics.BASE_URL+"/api/association/image/download/"+managed.getPhotoAgence()));
            managedAss.addActionListener(e->{
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                 AssociationManagerForm mm =   new AssociationManagerForm(this, managed);
                ip.dispose();
                mm.showBack();
            });
            this.add(managedAss);
            this.add("                    ");
         }
         if(uS.getUser().isMember()){
            this.add(new Label("Associations that you are an affiliate with :"));
            for(Membership m : MembershipController.getInstance().getMemberships()){
                if(m.getStatus().equals(Membership.ACCEPTED)){
                    Association a = AssociationService.getInstance().getAssociation(m.getAssId());
                    MultiButton b = new MultiButton(AssociationService.getInstance().getAssociation(m.getAssId()).getNom());
                    b.setEmblem(BUTTON_RIGHT);
                    b.setIcon(URLImage.createToStorage(PLACEHOLDER, "association"+String.valueOf(a.getId()), Statics.BASE_URL+"/api/association/image/download/"+a.getPhotoAgence()));
                    b.addActionListener(e->{
                        Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                        AssociationProfileAssociationForm mm = new AssociationProfileAssociationForm(this, a);
                        ip.dispose();
                        mm.showBack();
                    });
                    Button delete = new Button("Delete affiliation");
                    delete.setMaterialIcon(FontImage.MATERIAL_REMOVE);
                    this.add(b);
                    this.add(delete);
                    delete.addActionListener(r->{
                        if (Dialog.show("Are you sure you want to delete your affiliation with"+a.getNom(), "Do you want to proceed?", "Yes", "No, ghalta ya bro")) {
                            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                            MembershipController.getInstance().deleteMembership(m.getId());
                            delete.remove();
                            b.remove();
                            ip.dispose();
                            ToastBar.showMessage("Succesfully deleted membership", FontImage.MATERIAL_DONE);
                        }else{
                            ToastBar.showInfoMessage("Canceled");
                        }
                    });
                }
            }
            
        }
        


    }
    private static MyAssociationHomeForm instance=null;
    public static MyAssociationHomeForm getInstance(Form previous) {
        if (instance == null) {
            instance = new MyAssociationHomeForm(previous);
        }
        return instance;
    }
    
}
