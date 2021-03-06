/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.gui;

import com.codename1.capture.Capture;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.ImageIO;
import java.io.IOException;
import java.io.OutputStream;
import javafx.stage.FileChooser;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.nasri.entities.HebergementOffer;
import tn.esprit.reactors.nasri.services.ServiceHebergementOffer;
import tn.esprit.reactors.nasri.utils.Helpers;

/**
 *
 * @author nasri
 */
public class AddOfferForm extends Form
{
    private Form _parent;
    
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
    
    public AddOfferForm(Form parent)
    {
        _parent = parent;
        style();
        setup(); 
    }
    
    private void setup()
    {
        this.setTitle("Ajouter une offre d'hébérgement");
        setupForm();
        setupInputActions();
        setupToolbar();
    }
    
    private void setupInputActions()
    {
        imagePicker.addActionListener((evt) -> 
        {
            //Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
            String i = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if (i != null)
            {                    
                file = new File(i);
            }
        });
        
        handleSubmit();
    }
    
    private void handleSubmit()
    {
        submitBtn.addActionListener((evt) -> 
        {
            if (validateForm())
            {
                HebergementOffer offer = new HebergementOffer();
                offer.setUserId(Statics.CURRENT_USER_ID);
                offer.setDescription(descriptionInput.getText());
                offer.setGovernorat(governoratInput.getText());
                offer.setNumberRooms(Integer.parseInt(numberRoomsInput.getText()));
                offer.setDuration(Integer.parseInt(durationInput.getText()));
                offer.setTelephone(telephoneInput.getText());

                offer.setImage(file);


                boolean result = ServiceHebergementOffer.getInstance().add(offer);

                if (result)
                {
                    ((MainForm)_parent).setListOffersForm(new ListOffersForm(_parent));
                    _parent.showBack();
                }
            }
            else
            {
                if(Dialog.show("Valeurs entrées non valides", "Voulez vous réinitialiser les champs?", "Oui", "Non"))
                {
                    resetForm();
                }
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
        
        descriptionInput = new TextField();
        governoratInput = new TextField();
        durationInput = new TextField();
        numberRoomsInput = new TextField();
        telephoneInput = new TextField();
        imagePicker = new Button("Choisir une image");
        
        submitBtn = new Button("Ajouter");
        
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
        
        formContainer.add(BorderLayout.CENTER, first);

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
    
    
    private boolean validateForm()
    {
        boolean result = true;
        
        if (anyInputFieldIsEmpty())
        {
            result = false;
        }
        else if (file == null)
        {
            result = false;
        }
        else if (!Helpers.phoneNumberIsValid(telephoneInput.getText()))
        {
            result = false;
        }
        else if (!Helpers.isNumericAndPositive(durationInput.getText()))
        {
            result = false;
        }
        else if (!Helpers.isNumericAndPositive(numberRoomsInput.getText()))
        {
            result = false;
        }
        
        return result;
    }
    
    
    private void resetForm()
    {
        descriptionInput.setText("");
        governoratInput.setText("");
        durationInput.setText("");
        numberRoomsInput.setText("");
        telephoneInput.setText("");
        file = null;
    }
    
    private ActionListener callback = e-> 
    {
        if (e != null && e.getSource() != null) 
        {
            String filePath = (String)e.getSource();
            file = new File(filePath);
        }
    };
    
    private boolean anyInputFieldIsEmpty()
    {
        boolean result = false;
        
        if (descriptionInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (governoratInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (durationInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (numberRoomsInput.getText().trim().isEmpty())
        {
            result = true;
        }
        
        return result;
    }
}
