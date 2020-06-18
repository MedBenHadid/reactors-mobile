/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.gui;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.nasri.entities.HebergementOffer;
import tn.esprit.reactors.nasri.entities.HebergementRequest;
import tn.esprit.reactors.nasri.services.ServiceHebergementOffer;
import tn.esprit.reactors.nasri.services.ServiceHebergementRequest;

/**
 *
 * @author nasri
 */
public class ListRequestsForm extends Form
{
    private Form _parent;
    
    TextField searchInput;
    Button searchBtn;
    
    ArrayList<HebergementRequest> requests;
    
    public ListRequestsForm(Form parent)
    {
        _parent = parent;
        requests = ServiceHebergementRequest.getInstance().getAll();
        
        style();
        setup();
    }
    
    
    private void setup()
    {
        init();
        setupSearchForm();
        setupRequestsList();
        setupToolbar();
    }
    
    
    private void init()
    {
        searchInput = new TextField();
        searchInput.setHint("Inserez un mot clé");
        searchBtn = new Button();
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, "Rechercher", 8);
        searchBtn.setIcon(icon);
        this.setTitle("List des demandes d'hébérgement");
    }
    
    private void setupSearchForm()
    {
        Container formContainer = new Container(new BorderLayout());
        
        formContainer.add(BorderLayout.EAST, searchBtn);
        formContainer.add(BorderLayout.CENTER, searchInput);

        this.add(formContainer);
    }
    
    private void setupRequestsList()
    {
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        
        for(HebergementRequest request : requests)
        {
            MultiButton mb = new MultiButton();
            mb.setTextLine1(request.getDescription());
            mb.setTextLine2("Telephone " + request.getTelephone());
            mb.setTextLine4("Créé le " + request.getCreationDate());    
            list.add(mb);
            
            Button editBtn;
            Button deleteBtn;
            
            if (request.getUserId() == Statics.CURRENT_USER_ID)
            {
                editBtn = new Button("Modifier");
                deleteBtn = new Button("Supprimer");
                
                
                editBtn.addActionListener((evt) -> 
                {
                    if(Dialog.show("Confirmation", "Vous etes sure ?", "Ok", "Annuler"))
                    {
                        new UpdateRequestForm(this, request).show();
                    }
                });
                
                deleteBtn.addActionListener((evt) -> 
                {
                    if(Dialog.show("Confirmation", "Vous etes sure ?", "Ok", "Annuler"))
                    {
                        ServiceHebergementRequest.getInstance().delete(request.getId());
                        new _ListRequestsForm().show();
                    }
                });
                
                
                list.addAll(editBtn, deleteBtn);
            }
            
            
        }
        
        this.add(list);
    }
    
    private void setupToolbar()
    {
        this.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            _parent.showBack();
        });
    }
    
    private void style()
    {
        this.setLayout(BoxLayout.y());
        this.setScrollable(false);
    }
}
