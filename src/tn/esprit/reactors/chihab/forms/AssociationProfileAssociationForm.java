/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;

import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;

import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.controllers.MembershipController;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.malek.services.UserService;

/**
 *
 * @author Chihab
 */
public class AssociationProfileAssociationForm extends ReactorsForm {
    // Services
    private final UserService uS = UserService.getInstance();
    private final MembershipController mC = MembershipController.getInstance();

    // UI
    private final MultiButton joinMultiButton = new MultiButton("Fill out the form");
    private final MultiButton loginMultiButton = new MultiButton("Fill out the form");
    private final MultiButton locationMultiButton = new MultiButton();
    private final SpanLabel descriptionSpanLabel = new SpanLabel();
    private final Button callButton = new Button("Call now",FontImage.createMaterial(FontImage.MATERIAL_CALL, UIManager.getInstance().getComponentStyle("MultiLine")));
    private final MultiButton horaireMultiButton = new MultiButton("Horarire de travail :");
    private final Association association;
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine"));
    private final EncodedImage PERSON_PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 2, PEOPLE.getHeight() * 2), false); 
    private final MapForm mf ;
    public AssociationProfileAssociationForm(Form previous, Association a) {
        super("Profile : "+a.getNom(),previous);
        this.association=a;
        this.mf = new MapForm(this,association);
    }
    
    public void showForm() throws IOException{
        this.show();
        Dialog ip = new InfiniteProgress().showInfiniteBlocking();
        try (InputStream storageInputStream = Storage.getInstance().createInputStream(String.valueOf("association"+association.getId()))) {
            this.add(new ImageViewer(Image.createImage(storageInputStream).scaled(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayWidth())));
        }
        descriptionSpanLabel.setText(association.getDescription());
        descriptionSpanLabel.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LOCAL_LIBRARY, UIManager.getInstance().getComponentStyle("MultiLine")));
        locationMultiButton.setTextLine1(association.getRue());
        locationMultiButton.setTextLine2(association.getVille()+" "+association.getCodePostal());
        locationMultiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_PLACE, UIManager.getInstance().getComponentStyle("MultiLine")));
        locationMultiButton.addActionListener(e -> {
            mf.show();
        });
        horaireMultiButton.setTextLine3(association.getHoraireTravail());
        callButton.addActionListener(e -> {
           Display.getInstance().dial(String.valueOf(association.getTelephone()));
        });
        
        this.add(new Label("Descripton :")).add(ComponentGroup.enclose(descriptionSpanLabel));
        this.add("Location :").add(ComponentGroup.enclose(locationMultiButton));
        this.add(horaireMultiButton);
        this.add("Contact info :").add(ComponentGroup.enclose(callButton));
        List<Membership> memberships = MembershipController.getInstance().readMemberships(association.getId(), 1, Membership.ACCEPTED);
        if(memberships.size()>0){
            this.add("Members :");
            for(Membership m : memberships){
                User member = UserService.getInstance().getUserProfile(m.getMemberId());
                MultiButton membershipButton = new MultiButton(member.getUsername());
                membershipButton.setTextLine2("Role :"+m.getFonction());
                membershipButton.setTextLine3("Description :"+m.getDescription());
                membershipButton.setIcon(URLImage.createToStorage(PERSON_PLACEHOLDER,"user"+member.getImage(),Statics.BASE_URL+"/api/user/profileimage/"+member.getImage(),URLImage.RESIZE_SCALE));
                
                if(uS.isLoggedIn())
                    if(m.getMemberId()==uS.getUser().getId())
                        membershipButton.getStyle().setBgColor(ColorUtil.MAGENTA);
                this.add(membershipButton);
            } 
        }

        if(uS.isLoggedIn()){
            if(!mC.isMember(association.getId())){
                this.add("Interested by the cause ? Apply now!");
                joinMultiButton.addActionListener(e->{
                    Dialog requestBlocking = new InfiniteProgress().showInfiniteBlocking();
                    MembershipRequestForm m = new MembershipRequestForm("Request to join "+association.getNom(),this,association.getId());
                    requestBlocking.dispose();
                    m.showBack();
                });
                this.add(joinMultiButton);
            }
        } else {
            this.add("Interested by the cause ? Register or login to apply now!!");
            loginMultiButton.addActionListener(e->{
                System.out.println("Login here");
            });
            this.add(loginMultiButton);
        }  
        this.revalidate();
        ip.dispose();
        
    }
}
