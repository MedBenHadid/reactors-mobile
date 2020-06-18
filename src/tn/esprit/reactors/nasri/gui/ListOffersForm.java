/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import java.util.ArrayList;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.nasri.entities.HebergementOffer;
import tn.esprit.reactors.nasri.services.ServiceHebergementOffer;

/**
 *
 * @author nasri
 */
public class ListOffersForm extends Form
{
    private Form _parent;
    
    TextField searchInput;
    Button searchBtn;
    
    ArrayList<HebergementOffer> offers;
    Container offersList;
    
    public ListOffersForm(Form parent)
    {
        _parent = parent;
        style();
        setup();
    }
    
    private void setup()
    {
        init();
        setupSearchForm();
        setupOffersList();
        fillOffersList();
        setupToolbar();
    }
    
    private void init()
    {
        searchInput = new TextField();
        searchInput.setHint("Inserez un mot clé");
        searchBtn = new Button();
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, "Rechercher", 8);
        searchBtn.setIcon(icon);
        this.setTitle("List des offres d'hébérgement");
    }
    
    
    private void setupOffersList()
    {
        offersList = new Container(BoxLayout.y());
        offersList.setScrollableY(true);
        this.add(offersList);
    }
    
    
    private void fillOffersList()
    {
        offers = ServiceHebergementOffer.getInstance().getAll();
        
        for(HebergementOffer offer : offers)
        {
            MultiButton mb = new MultiButton();
            mb.setTextLine1(offer.getDescription());
            mb.setTextLine2("Governorat " + offer.getGovernorat());
            mb.setTextLine3("Telephone " + offer.getTelephone());
            mb.setTextLine4("Créé le " + offer.getCreationDate());    
            offersList.add(mb);
            
            Button editBtn;
            Button deleteBtn;
            
            if (offer.getUserId() == Statics.CURRENT_USER_ID)
            {
                editBtn = new Button("Modifier");
                deleteBtn = new Button("Supprimer");
                
                
                editBtn.addActionListener((evt) -> 
                {
                    if(Dialog.show("Confirmation", "Vous etes sure ?", "Ok", "Annuler"))
                    {
                        new UpdateOfferForm(this, offer).show();
                    }
                });
                
                deleteBtn.addActionListener((evt) -> 
                {
                    if(Dialog.show("Confirmation", "Vous etes sure ?", "Ok", "Annuler"))
                    {
                        ServiceHebergementOffer.getInstance().delete(offer.getId());
                        new _ListOffersForm().show();
                    }
                });
                
                
                offersList.addAll(editBtn, deleteBtn);
            }
            
            
        }
    }
    
    private void setupSearchForm()
    {
        Container formContainer = new Container(new BorderLayout());
        
        formContainer.add(BorderLayout.EAST, searchBtn);
        formContainer.add(BorderLayout.CENTER, searchInput);

        this.add(formContainer);
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
