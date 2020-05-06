/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.mission.forms;

import tn.esprit.reactors.chihab.forms.*;
import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import tn.esprit.reactors.mission.services.MissionService;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.mission.models.Mission;

/**
 *
 * @author chihab
 */
public class ListMissionForm extends ReactorsForm{
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, s);
    private final FontImage BUTTON_RIGHT = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, s);
    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 1, PEOPLE.getHeight() * 1), false); 
    private Form thisHolder;
    private int page = 1;
    private ListMissionForm(Form previous) {
        super("List Mission",previous);
        this.thisHolder=this;
        InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
            try {
                java.util.List<Mission> data = MissionService.getInstance().fetchMission("","",page);
                MultiButton[] cmps = new MultiButton[data.size()];
                for (int iter = 0; iter < cmps.length; iter++) {
                    Mission currentListing = data.get(iter);
                    cmps[iter] = new MultiButton(currentListing.getTitleMission());
                    cmps[iter].addActionListener(e -> {
                        new MissionProfileForm(thisHolder,currentListing).showBack();
                    });
                    cmps[iter].setEmblem(BUTTON_RIGHT);
                    cmps[iter].setIcon(URLImage.createToStorage(PLACEHOLDER, String.valueOf(currentListing.getId()), Statics.BASE_URL+"/missionApi/Fetchimage/"+currentListing.getPicture()));
                }
                InfiniteScrollAdapter.addMoreComponents(ListMissionForm.this.getContentPane(), cmps, !data.isEmpty());
                page++;
            } catch (IOException ex) {
                InfiniteScrollAdapter.addMoreComponents(ListMissionForm.this.getContentPane(), new Component[0], false);
            }
        }, true); 

  }

   
    private static ListMissionForm instance=null;
    public static ListMissionForm getInstance(Form previous) {
        if (instance == null) {
            instance = new ListMissionForm(previous);
        }
        return instance;
    }

    
}
