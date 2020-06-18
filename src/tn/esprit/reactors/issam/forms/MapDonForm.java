/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.forms;

import com.codename1.components.ToastBar;
import com.codename1.maps.Coord;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import tn.esprit.reactors.GMap;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.issam.models.Don;

/**
 *
 * @author Issam
 */
class MapDonForm extends ReactorsForm {

    public MapDonForm(String title, Form previous, Don don) {
        super(title, previous);

        GMap g = new GMap();
        g.setCameraPosition(new Coord(don.getLat(), don.getLon()));
      //  System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa"+don.getLat());
        g.zoom(g.getCameraPosition(), 10);
        g.addMarker(
                EncodedImage.createFromImage(g.getMarkerImage(), false),
                g.getCameraPosition(),
                
                don.getTitle(),
                "",
                e -> {
                    ToastBar.showMessage("You clicked the marker", FontImage.MATERIAL_PLACE);
                }
        );
        this.add(g);
    }

}
