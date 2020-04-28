/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors;

import com.codename1.googlemaps.MapContainer;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.plaf.Style;

/**
 *
 * @author Chihab
 */
public class GMap extends MapContainer{
    public GMap() {
        super("AIzaSyD00UW-yFHi17pDyHLe19_ImRpo0ja5Q3k");
    }
    public FontImage getMarkerImage(){
        Style s = new Style();
        s.setFgColor(0x6200EE);
        s.setBgTransparency(0);
        FontImage marker = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s , Display.getInstance().convertToPixels(1));
        return marker;
    }
}
