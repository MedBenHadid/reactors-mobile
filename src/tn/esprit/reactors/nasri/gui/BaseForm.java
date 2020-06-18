/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package tn.esprit.reactors.nasri.gui;

import tn.esprit.reactors.nasri.gui.test.*;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 * Utility methods common to forms e.g. for binding the side menu
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {
    public void installSidemenu(Resources res) {
        Image selection = res.getImage("selection-in-sidemenu.png");
        
        Image homeImage = null;
        if (isHome()) homeImage = selection;
        
        Image listOffersImage = null;
        if(isCurrentListOffers()) listOffersImage = selection;

        Image listRequestImage = null;
        if(isCurrentListRequests()) listRequestImage = selection;
        
        Image addOfferImage = null;
        if(isCurrentAddOffer()) addOfferImage = selection;
        
        Image addRequestImage = null;
        if(isCurrentAddRequest()) addRequestImage = selection;
        
        getToolbar().addCommandToSideMenu("Accueil", homeImage, e -> new HomeForm(res).show());
        getToolbar().addCommandToSideMenu("Liste des offres", listOffersImage, e -> new _ListOffersForm(res).show());
        getToolbar().addCommandToSideMenu("Liste des demandes", listRequestImage, e -> new _ListRequestsForm(res).show());
        getToolbar().addCommandToSideMenu("Ajouter une offre", addOfferImage, e -> new _AddOfferForm(res).show());
        getToolbar().addCommandToSideMenu("Ajouter une demande", addRequestImage, e -> new _AddRequestForm(res).show());
        
        // spacer
        getToolbar().addComponentToSideMenu(new Label(" ", "SideCommand"));
        getToolbar().addComponentToSideMenu(new Label(res.getImage("profile_image.png"), "Container"));
        getToolbar().addComponentToSideMenu(new Label("Detra Mcmunn", "SideCommandNoPad"));
        getToolbar().addComponentToSideMenu(new Label("Long Beach, CA", "SideCommandSmall"));
    }

    protected boolean isHome() {
        return false;
    }
    
    
    protected boolean isCurrentListOffers() {
        return false;
    }
    
    protected boolean isCurrentListRequests() {
        return false;
    }

    protected boolean isCurrentAddOffer() {
        return false;
    }

    protected boolean isCurrentAddRequest() {
        return false;
    }
}
