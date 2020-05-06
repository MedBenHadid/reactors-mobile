/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.chihab.services.AssociationService;
import tn.esprit.reactors.chihab.services.MembershipService;
import tn.esprit.reactors.chihab.services.UserService;

/**
 *
 * @author Chihab
 */
public class InvitesRequestsForm extends ReactorsForm{
    
    public InvitesRequestsForm(String title, Form previous, boolean isAssociation) {
        super(title, previous);
        System.out.println((isAssociation? AssociationService.getInstance().findAssByManager(UserService.getInstance().getUser().getId()).getId():UserService.getInstance().getUser().getId()));
        System.out.println((isAssociation ? Membership.REQUEST_PENDING : Membership.INVITE_PENDING));
        for(Membership m : MembershipService.getInstance().fetchMemberships( (isAssociation? AssociationService.getInstance().findAssByManager(UserService.getInstance().getUser().getId()).getId() : UserService.getInstance().getUser().getId()), (isAssociation ? 1 : 0), (isAssociation ? Membership.REQUEST_PENDING : Membership.INVITE_PENDING))){
            MultiButton b = new MultiButton(
                    (m.getStatus().equals(Membership.INVITE_PENDING)? "Association":"Username")
                            );
            b.addActionListener(e->{
                if (Dialog.show((isAssociation?"Confirm request":"Confirm invite"), "Do you want to proceed?", "Accept", "Deny")) {
                    m.setStatus(Membership.ACCEPTED);
                    MembershipService.getInstance().update(m);
                }else{
                    m.setStatus((isAssociation? Membership.DENIED_BY_ASS : Membership.DENIED_BY_USER ));
                    MembershipService.getInstance().update(m);
                }
                b.remove();
                ToastBar.showMessage("Gucci gucci bratan, SKKRT SKRRRRT",FontImage.MATERIAL_DONE);
            });
            add(b);
        }
    }
    
}
