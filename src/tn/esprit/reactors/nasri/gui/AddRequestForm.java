/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.gui;

import com.codename1.components.OnOffSwitch;
import com.codename1.components.Switch;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import java.util.Date;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.nasri.entities.HebergementRequest;
import tn.esprit.reactors.nasri.enums.CivilStatus;
import tn.esprit.reactors.nasri.enums.HebergementStatus;
import tn.esprit.reactors.nasri.services.ServiceHebergementRequest;
import tn.esprit.reactors.nasri.utils.Helpers;

/**
 *
 * @author nasri
 */
public class AddRequestForm extends Form
{
    private Form _parent;
    
    Label nameL;
    Label descriptionL;
    Label regionL;
    Label nativeCountryL;
    Label arrivalDateL;
    Label passportL;
    Label civilStatusL;
    Label childrenNumberL;
    Label telephoneL;
    Label isAnonymousL;
    
    
    TextField nameInput;
    TextField descriptionInput;
    TextField regionInput;
    TextField nativeCountryInput;
    Picker arrivalDateInput;
    TextField passportInput;
    Switch civilStatusInput;
    TextField childrenNumberInput;
    TextField telephoneInput;
    Switch isAnonymousInput;
    
    Button submitBtn;
    
    public AddRequestForm(Form parent)
    {
        _parent = parent;
        style();
        setup();
    }
    
    private void style()
    {
        this.setLayout(BoxLayout.y());
    }
    
    private void setup()
    {
        this.setTitle("Ajouter une demande d'hébérgement");
        setupForm();
        handleSubmit();
        setupToolbar();
    }
    
    private void handleSubmit()
    {
        submitBtn.addActionListener((evt) -> 
        {
            if (validateForm())
            {
                CivilStatus civilStatus = civilStatusInput.isOff() ? CivilStatus.Married : CivilStatus.Single;
                boolean isAnonymous = isAnonymousInput.isOff();

                HebergementRequest request = new HebergementRequest();
                request.setUserId(Statics.CURRENT_USER_ID);
                request.setDescription(descriptionInput.getText());
                request.setNativeCountry(nativeCountryInput.getText());
                request.setArrivalDate(arrivalDateInput.getDate());
                request.setPassportNumber(passportInput.getText());
                request.setCivilStatus(civilStatus);
                request.setChildrenNumber(Integer.parseInt(childrenNumberInput.getText()));
                request.setRegion(regionInput.getText());
                request.setState(HebergementStatus.inProcess);
                request.setName(nameInput.getText());
                request.setTelephone(telephoneInput.getText());
                request.setAnonymous(isAnonymous);
                request.setUserId(Statics.CURRENT_USER_ID);


                boolean result = ServiceHebergementRequest.getInstance().add(request);

                if (result)
                {
                    ((MainForm)_parent).setListRequestsForm(new ListRequestsForm(_parent));
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
    
    private void resetForm()
    {
        nameInput.setText("");
        descriptionInput.setText("");
        regionInput.setText("");
        nativeCountryInput.setText("");
        arrivalDateInput.setDate(new Date());
        passportInput.setText("");
        civilStatusInput.setOff();
        childrenNumberInput.setText("");
        telephoneInput.setText("");
        isAnonymousInput.setOff();
    }
    
    private boolean validateForm()
    {
        boolean result = true;
        
        if (anyInputFieldIsEmpty())
        {
            result = false;
        }
        else if (!Helpers.phoneNumberIsValid(telephoneInput.getText()))
        {
            result = false;
        }
        else if (!Helpers.isNumericAndPositive(childrenNumberInput.getText()))
        {
            result = false;
        }
        
        return result;
    }
    
    private boolean anyInputFieldIsEmpty()
    {
        boolean result = false;
        
        if (nameInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (descriptionInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (regionInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (nativeCountryInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (arrivalDateInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (passportInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (childrenNumberInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (telephoneInput.getText().trim().isEmpty())
        {
            result = true;
        }
        
        return result;
    }
    
    private void setupForm()
    {
        nameL = new Label("Nom: ");
        descriptionL = new Label("Description: ");
        regionL = new Label("Région: ");
        nativeCountryL = new Label("Pays d'origine: ");
        arrivalDateL = new Label("Date d'arrivée: ");
        passportL = new Label("Passport: ");
        civilStatusL = new Label("Etat Civil: (Marrié(e))");
        childrenNumberL = new Label("Nombre d'enfants: ");
        telephoneL = new Label("Téléphone: ");
        isAnonymousL = new Label("Anonyme? (Oui)");
        
        nameInput = new TextField();
        descriptionInput = new TextField();
        regionInput = new TextField();
        nativeCountryInput = new TextField();
        arrivalDateInput = new Picker();
        passportInput = new TextField();
        civilStatusInput = new Switch();
        childrenNumberInput = new TextField();
        telephoneInput = new TextField();
        isAnonymousInput = new Switch();
        
        
        //tmp here
        civilStatusInput.addChangeListener((evt) -> 
        {
            if (civilStatusInput.isOn())
            {
                civilStatusL.setText("Etat Civil: (Célibatair(e))");
            }
            if (civilStatusInput.isOff())
            {
                civilStatusL.setText("Etat Civil: (Marrié(e))");
            }
        });
        
        isAnonymousInput.addChangeListener((evt) -> 
        {
            if (isAnonymousInput.isOn())
            {
                isAnonymousL.setText("Anonyme? (Non)");
            }
            if (isAnonymousInput.isOff())
            {
                isAnonymousL.setText("Anonyme? (Oui)");
            }
        });
        
        
        submitBtn = new Button("Ajouter");
        
        this.addAll(
                inlineFormGroup(nameL, nameInput),
                inlineFormGroup(descriptionL, descriptionInput),
                inlineFormGroup(regionL, regionInput),
                inlineFormGroup(nativeCountryL, nativeCountryInput),
                inlineFormGroup(arrivalDateL, arrivalDateInput),
                inlineFormGroup(passportL, passportInput),
                inlineFormGroup(civilStatusL, civilStatusInput),
                inlineFormGroup(childrenNumberL, childrenNumberInput),
                inlineFormGroup(telephoneL, telephoneInput),
                inlineFormGroup(isAnonymousL, isAnonymousInput),
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
    
    private void setupToolbar()
    {
        this.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            _parent.showBack();
        });
    }
}
