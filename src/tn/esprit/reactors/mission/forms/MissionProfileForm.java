/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.mission.forms;

import tn.esprit.reactors.chihab.forms.*;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
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
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.chihab.services.MembershipService;
import tn.esprit.reactors.mission.models.Mission;

/**
 *
 * @author Chihab
 */
public class MissionProfileForm extends ReactorsForm {
    private final FontImage MORE = FontImage.createMaterial(FontImage.MATERIAL_MORE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PLACE = FontImage.createMaterial(FontImage.MATERIAL_PLACE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DESC = FontImage.createMaterial(FontImage.MATERIAL_LOCAL_LIBRARY, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PHONE = FontImage.createMaterial(FontImage.MATERIAL_CALL, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    private final FontImage p = FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, s);
    private final EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 3), false); 
    private int page=1;
    public MissionProfileForm(Form previous, Mission m) {
        super("Profile : "+m.getTitleMission(),previous);
        InputStream is =  null;
        try {
            is = Storage.getInstance().createInputStream(String.valueOf(m.getId()));
            EncodedImage i = EncodedImage.create(is, is.available());
            ImageViewer imageViewer = new ImageViewer(i);
            this.addComponent(imageViewer);
        } catch (IOException ex) {}        SpanLabel sp = new SpanLabel(m.getDescription());
        sp.setIcon(DESC);
        this.add(new Label("Descripton :")).add(ComponentGroup.enclose(sp));
        MultiButton locationButton = new MultiButton();
        locationButton.setIcon(PLACE);
    //    locationButton.setTextLine1(a.getRue());
      //  locationButton.setTextLine2(a.getVille()+" "+a.getCodePostal());
      //  locationButton.setTextLine3("Horarire de travail :");
      //  locationButton.setTextLine4(a.getHoraireTravail());
      //  locationButton.setEmblem(PEOPLE);
        locationButton.addActionListener(e -> {
            new MapForm(this,m);
        });
        
        this.add("Location :").add(ComponentGroup.enclose(locationButton));
        Button phone = new Button("Call now",PHONE);
        //phone.addActionListener(e -> {
         //  Display.getInstance().dial(String.valueOf(m.get()));
       // });
        this.add("Contact info :").add(ComponentGroup.enclose(phone));  
        InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
            java.util.List<Membership> data = MembershipService.getInstance().fetchMemberships(m.getId(),1,Membership.ACCEPTED);
            MultiButton[] cmps = new MultiButton[data.size()];
            for (int iter = 0; iter < cmps.length; iter++) {
                Membership currentListing = data.get(iter);
                if (data.isEmpty()) {
                    InfiniteScrollAdapter.addMoreComponents(getContentPane(), new Component[0], false);
                    return;
                }
                cmps[iter] = new MultiButton();
                cmps[iter].setTextLine2(currentListing.getFonction());
                cmps[iter].setIcon(placeholder);
                cmps[iter].setTextLine3(currentListing.getDescription());
                //cmps[iter].setIcon(URLImage.createToStorage(placeholder, String.valueOf(currentListing.getId()), Statics.BASE_URL+"/api/associations/image/"+currentListing.getPhotoAgence()));
            }
            page++;
            InfiniteScrollAdapter.addMoreComponents(getContentPane(), cmps, !data.isEmpty());
        }, true);  
        this.show();
    }
}
