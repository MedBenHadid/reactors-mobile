/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.components.ToastBar;
import com.codename1.maps.Coord;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import tn.esprit.reactors.GMap;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Association;

/**
 *
 * @author Chihab
 */
public class MapForm extends ReactorsForm {

    public MapForm(Form previous, Association a) {
        super("Map location :",previous);
        GMap g = new GMap();
        g.setCameraPosition(new Coord(a.getLat(), a.getLon()));
        g.addMarker(
            EncodedImage.createFromImage(g.getMarkerImage(), false),
            g.getCameraPosition(),
            a.getNom(),
            a.getVille()+" "+a.getRue()+", "+a.getCodePostal()+"Horarire de travail :"+a.getHoraireTravail(),
            e -> {
                ToastBar.showMessage("You clicked the marker", FontImage.MATERIAL_PLACE);
            }
        ); 
        this.add(g);
        this.show();
    }
    
}
