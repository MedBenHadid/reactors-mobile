/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
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
public class InvitesRequestsForm extends ReactorsForm{
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PERSON = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage EMBLEM = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, UIManager.getInstance().getComponentStyle("MultiLine1"));
    public InvitesRequestsForm(String title, Form previous, boolean isAssociation) {
        super(title, previous);
        for(Membership m : MembershipController.getInstance().readMemberships((isAssociation ? AssociationService.getInstance().findAssByManager(UserService.getInstance().getUser().getId()).getId() : UserService.getInstance().getUser().getId()), (isAssociation ? 1 : 0), (isAssociation ? Membership.REQUEST_PENDING : Membership.INVITE_PENDING))){
            if((m.getStatus().equals(Membership.REQUEST_PENDING)&&isAssociation)||(m.getStatus().equals(Membership.INVITE_PENDING)&&!isAssociation)){
                Association current = AssociationService.getInstance().getAssociation(m.getAssId());
                MultiButton b = new MultiButton((isAssociation? "Username":current.getNom()));
                b.setIcon(URLImage.createToStorage((isAssociation? EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 1, PEOPLE.getHeight() * 1), false): EncodedImage.createFromImage(PERSON.scaled(PERSON.getWidth() * 1, PERSON.getHeight() * 1), false)),"association"+m.getAssId(),Statics.BASE_URL+"/api/association/image/download/"+current.getPhotoAgence()));
                b.setEmblem(EMBLEM);
                b.setEmblemName("Click to deny/accept");
                b.addActionListener(e->{
                    Dialog ip;
                    if (Dialog.show((isAssociation?"Confirm request":"Confirm invite"), "Do you want to proceed?", "Accept", "Deny")) {
                        ip = new InfiniteProgress().showInfiniteBlocking();
                        m.setStatus(Membership.ACCEPTED);
                        MembershipController.getInstance().updateMembership(m);
                    }else{
                        ip = new InfiniteProgress().showInfiniteBlocking();
                        m.setStatus((isAssociation? Membership.DENIED_BY_ASS : Membership.DENIED_BY_USER ));
                        MembershipController.getInstance().updateMembership(m);
                    }
                    b.remove();
                    ip.dispose();
                    ToastBar.showMessage("Gucci gucci bratan, SKKRT SKRRRRT",FontImage.MATERIAL_DONE);
                });
                b.getStyle().set3DText(isAssociation, isAssociation);
                b.setTextLine2("Tap to resolve !");
                this.add(b);
            }
        }
    }
    public InvitesRequestsForm(boolean isAssociation){
            for(Membership m : MembershipController.getInstance().readMemberships((isAssociation ? AssociationService.getInstance().findAssByManager(UserService.getInstance().getUser().getId()).getId() : UserService.getInstance().getUser().getId()), (isAssociation ? 1 : 0), (isAssociation ? Membership.REQUEST_PENDING : Membership.INVITE_PENDING))){
            if((m.getStatus().equals(Membership.REQUEST_PENDING)&&isAssociation)||(m.getStatus().equals(Membership.INVITE_PENDING)&&!isAssociation)){
                Association current = AssociationService.getInstance().getAssociation(m.getAssId());
                MultiButton b = new MultiButton((isAssociation? "Username":current.getNom()));
                b.setIcon(URLImage.createToStorage((isAssociation? EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 1, PEOPLE.getHeight() * 1), false): EncodedImage.createFromImage(PERSON.scaled(PERSON.getWidth() * 1, PERSON.getHeight() * 1), false)),"association"+m.getAssId(),Statics.BASE_URL+"/api/association/image/download/"+current.getPhotoAgence()));
                b.setEmblem(EMBLEM);
                b.setEmblemName("Click to deny/accept");
                b.addActionListener(e->{
                    Dialog ip;
                    if (Dialog.show((isAssociation?"Confirm request":"Confirm invite"), "Do you want to proceed?", "Accept", "Deny")) {
                        ip = new InfiniteProgress().showInfiniteBlocking();
                        m.setStatus(Membership.ACCEPTED);
                        MembershipController.getInstance().updateMembership(m);
                    }else{
                        ip = new InfiniteProgress().showInfiniteBlocking();
                        m.setStatus((isAssociation? Membership.DENIED_BY_ASS : Membership.DENIED_BY_USER ));
                        MembershipController.getInstance().updateMembership(m);
                    }
                    b.remove();
                    ip.dispose();
                    ToastBar.showMessage("Gucci gucci bratan, SKKRT SKRRRRT",FontImage.MATERIAL_DONE);
                });
                b.getStyle().set3DText(isAssociation, isAssociation);
                this.add(b);
            }
        }
    }
}
