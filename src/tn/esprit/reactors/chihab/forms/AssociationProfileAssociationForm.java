/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import java.io.InputStream;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.chihab.services.MembershipService;
import tn.esprit.reactors.chihab.services.UserService;

/**
 *
 * @author Chihab
 */
public class AssociationProfileAssociationForm extends ReactorsForm {
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    private final FontImage PLACE = FontImage.createMaterial(FontImage.MATERIAL_PLACE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DESC = FontImage.createMaterial(FontImage.MATERIAL_LOCAL_LIBRARY, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PHONE = FontImage.createMaterial(FontImage.MATERIAL_CALL, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage ACCOUNT = FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, s);
    private final EncodedImage placeholder = EncodedImage.createFromImage(PEOPLE.scaled(ACCOUNT.getWidth() * 3, ACCOUNT.getHeight() * 3), false); 
    private boolean isMember=false;
    public AssociationProfileAssociationForm(Form previous, Association a) {
        super("Profile : "+a.getNom(),previous);
        InputStream is =  null;
        try {
            is = Storage.getInstance().createInputStream(String.valueOf(a.getId()));
            EncodedImage i = EncodedImage.create(is, is.available());
            ImageViewer imageViewer = new ImageViewer(i);
            this.addComponent(imageViewer);
        } catch (IOException ex) {}
        finally {try {is.close();} catch (IOException ex) {}
        }
        SpanLabel sp = new SpanLabel(a.getDescription());
        sp.setIcon(DESC);
        this.add(new Label("Descripton :")).add(ComponentGroup.enclose(sp));
        MultiButton locationButton = new MultiButton();
        locationButton.setIcon(PLACE);
        locationButton.setTextLine1(a.getRue());
        locationButton.setTextLine2(a.getVille()+" "+a.getCodePostal());
        locationButton.setTextLine3("Horarire de travail :");
        locationButton.setTextLine4(a.getHoraireTravail());
        locationButton.setEmblem(PEOPLE);
        locationButton.addActionListener(e -> {
            new MapForm(this,a);
        });
        
        this.add("Location :").add(ComponentGroup.enclose(locationButton));
        Button phone = new Button("Call now",PHONE);
        phone.addActionListener(e -> {
           Display.getInstance().dial(String.valueOf(a.getTelephone()));
        });
        this.add("Contact info :").add(ComponentGroup.enclose(phone));
        this.add("Memberships :");
        for(Membership m : MembershipService.getInstance().fetchMemberships(a.getId(), 1, Membership.ACCEPTED)){
            MultiButton mm = new MultiButton(m.getFonction());
            mm.setIcon(PEOPLE);
            this.add(mm);
        }
        if(!UserService.getInstance().isMember(a.getId())){
            this.add("Interested by the cause ? Apply now!");
            MultiButton join = new MultiButton("Fill out the form");
            join.addActionListener(e->{
                new MembershipRequestForm("Request to join "+a.getNom(),this,a.getId()).show();
            });
            this.add(join);
        }

        this.show();
    }
}
