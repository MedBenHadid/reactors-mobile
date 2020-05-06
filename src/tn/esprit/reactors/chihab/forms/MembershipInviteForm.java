/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import java.io.IOException;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.chihab.services.AssociationService;
import tn.esprit.reactors.chihab.services.MembershipService;
import tn.esprit.reactors.chihab.services.UserService;

/**
 *
 * @author Chihab
 */
public class MembershipInviteForm extends ReactorsForm {
    public MembershipInviteForm(String title, Form previous, int userId) {
        super(title, previous);
        this.add("Message");
        TextArea tr = new TextArea();
        this.add(tr);
        Button confirm = new Button("Request to join");
        this.add(confirm);
        confirm.addActionListener(e->{
            if(tr.getText().length()<8){
                ToastBar.showErrorMessage("Fill out the description bratan, SKKRT SKRRRRT",FontImage.MATERIAL_ERROR_OUTLINE);
            }else{
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                try {
                    MembershipService.getInstance().addMembership(new Membership(AssociationService.getInstance().findAssByManager(UserService.getInstance().getUser().getId()).getId(),userId,3,"LIVREUR",tr.getText(),Membership.INVITE_PENDING,Display.getInstance().getLocationManager().getCurrentLocation().getLongitude(),Display.getInstance().getLocationManager().getCurrentLocation().getLatitude()));
                } catch (IOException ex) {}
                ToastBar.showMessage("Gucci gucci bratan, SKKRT SKRRRRT",FontImage.MATERIAL_DONE);
                ip.dispose();
                previous.showBack();
            }
        });
    }
}
