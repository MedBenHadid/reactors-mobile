/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.gui;

import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.nasri.entities.HebergementOffer;
import tn.esprit.reactors.nasri.services.ServiceHebergementOffer;

/**
 *
 * @author nasri
 */
public class UpdateOfferForm extends Form 
{
    private Form _parent;
    private HebergementOffer offer;
    
    Label descriptionL;
    Label governoratL;
    Label durationL;
    Label telephoneL;
    Label imageL;
    Label numberRoumsL;
    
    TextField descriptionInput;
    TextField governoratInput;
    TextField durationInput;
    TextField telephoneInput;
    TextField numberRoomsInput;
    Button imagePicker;
    
    Button submitBtn;
    
    File file;
    
    public UpdateOfferForm(Form parent, HebergementOffer offer)
    {
        _parent = parent;
        this.offer = offer;
        style();
        setup(); 
    }
    
    private void setup()
    {
        this.setTitle("Modifier une offre d'hébérgement");
        setupForm();
        setupInputActions();
        setupToolbar();
    }
    
    private void setupInputActions()
    {
        imagePicker.addActionListener((evt) -> 
        {
            Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
        });
        
        submitBtn.addActionListener((evt) -> 
        {
            offer.setDescription(descriptionInput.getText());
            offer.setGovernorat(governoratInput.getText());
            offer.setNumberRooms(Integer.parseInt(numberRoomsInput.getText()));
            offer.setDuration(Integer.parseInt(durationInput.getText()));
            offer.setTelephone(telephoneInput.getText());
            offer.setImage(file);
            
            
            boolean result = ServiceHebergementOffer.getInstance().update(offer);
            
            if (result)
            {
                ((MainForm)_parent).setListOffersForm(new ListOffersForm(_parent));
                _parent.showBack();
            }
        });
    }
    
    private void setupForm()
    {
        descriptionL = new Label("Description: ");
        governoratL = new Label("Governorat: ");
        durationL = new Label("Durée (mois): ");
        numberRoumsL = new Label("Nombre des chambres: ");
        telephoneL = new Label("Telephone: ");
        imageL = new Label("Image: ");
        
        descriptionInput = new TextField(offer.getDescription());
        governoratInput = new TextField(offer.getGovernorat());
        durationInput = new TextField(String.valueOf(offer.getDuration()));
        numberRoomsInput = new TextField(String.valueOf(offer.getNumberRooms()));
        telephoneInput = new TextField(offer.getTelephone());
        imagePicker = new Button("Choisir une image ");
        
        submitBtn = new Button("Modifier");
        
        this.addAll(
                inlineFormGroup(descriptionL, descriptionInput),
                inlineFormGroup(governoratL, governoratInput),
                inlineFormGroup(durationL, durationInput),
                inlineFormGroup(numberRoumsL, numberRoomsInput),
                inlineFormGroup(telephoneL, telephoneInput),
                inlineFormGroup(imageL, imagePicker),
                inlineFormGroup(submitBtn)
        );
    }
    
    private Container inlineFormGroup(Component first, Component second)
    {
        Container formContainer = new Container(new BorderLayout());
        
        formContainer.add(BorderLayout.WEST, first);
        formContainer.add(BorderLayout.CENTER, second);

        return formContainer;
    }
    
    private Container inlineFormGroup(Component first)
    {
        Container formContainer = new Container(new BorderLayout());
        
        formContainer.add(BorderLayout.WEST, first);

        return formContainer;
    }
    
    private void style()
    {
        this.setLayout(BoxLayout.y());
    }
    
    private void setupToolbar()
    {
        this.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            _parent.showBack();
        });
    }
    
    private ActionListener callback = e-> 
    {
        if (e != null && e.getSource() != null) 
        {
            String filePath = (String)e.getSource();
            file = new File(filePath);
        }
    };
}
