/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.gui;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;

/**
 *
 * @author nasri
 */
public class AddRequestForm extends Form
{
    private Form _parent; 
    
    public AddRequestForm(Form parent)
    {
        _parent = parent;
        this.setTitle("Ajouter une demande d'hébérgement");
        setupToolbar();
    }
    
    private void setupToolbar()
    {
        this.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            _parent.showBack();
        });
    }
}
