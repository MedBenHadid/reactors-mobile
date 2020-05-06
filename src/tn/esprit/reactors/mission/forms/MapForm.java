/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.mission.forms;

import com.codename1.components.ToastBar;
import com.codename1.maps.Coord;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import tn.esprit.reactors.GMap;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.mission.models.Mission;

/**
 *
 * @author Chihab
 */
public class MapForm extends ReactorsForm {

    public MapForm(Form previous, Mission m) {
        super("Map location :",previous);
        GMap g = new GMap();
        g.setCameraPosition(new Coord(m.getLat(), m.getLon()));
        g.addMarker(
            EncodedImage.createFromImage(g.getMarkerImage(), false),
            g.getCameraPosition(),
            m.getTitleMission(),
            m.getLocation(),
            e -> {
                ToastBar.showMessage("You clicked the marker", FontImage.MATERIAL_PLACE);
            }
        ); 
        this.add(g);
        this.show();
    }
    
}
