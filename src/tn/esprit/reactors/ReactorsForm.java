/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors;

import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;

/**
 *
 * @author Chihab
 */
public class ReactorsForm extends Form{
    private static final Toolbar.BackCommandPolicy BACK_POLICY = Toolbar.BackCommandPolicy.AS_ARROW;
    public ReactorsForm(String title,Form previous){
        this.setTitle(title); 
        this.getToolbar().setBackCommand("PropertyCross", BACK_POLICY,  e -> previous.showBack());
        this.getToolbar().setTitleCentered(true);
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    }
    public ReactorsForm(String title, Form previous,Layout contentPaneLayout) {
        super(title,contentPaneLayout);
        this.setTitle(title);
        this.getToolbar().setBackCommand("PropertyCross", BACK_POLICY,  e -> previous.showBack());
        this.getToolbar().setTitleCentered(true);
    }
    
}
