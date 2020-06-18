/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.gui;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.nasri.entities.HebergementOffer;
import tn.esprit.reactors.nasri.services.ServiceHebergementOffer;
import tn.esprit.reactors.nasri.utils.Helpers;

/**
 *
 * @author nasri
 */
public class _AddOfferForm extends BaseForm {
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
    
    public _AddOfferForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    @Override
    protected boolean isCurrentAddOffer() {
        return true;
    }
    
    public _AddOfferForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Ajouter une offre", "Title")
                )
        );
        
        installSidemenu(resourceObjectInstance);
        
        getToolbar().addCommandToRightBar("", resourceObjectInstance.getImage("toolbar-profile-pic.png"), e -> {});
        
        gui_Label_5.setShowEvenIfBlank(true);
        gui_Label_6.setShowEvenIfBlank(true);
        gui_Label_7.setShowEvenIfBlank(true);
        gui_Label_8.setShowEvenIfBlank(true);
        gui_Label_9.setShowEvenIfBlank(true);
        
        gui_Text_Area_1.setRows(2);
        gui_Text_Area_1.setColumns(100);
        gui_Text_Area_1.setEditable(false);
        gui_Text_Area_1_1.setRows(2);
        gui_Text_Area_1_1.setColumns(100);
        gui_Text_Area_1_1.setEditable(false);
        gui_Text_Area_1_2.setRows(2);
        gui_Text_Area_1_2.setColumns(100);
        gui_Text_Area_1_2.setEditable(false);
        gui_Text_Area_1_3.setRows(2);
        gui_Text_Area_1_3.setColumns(100);
        gui_Text_Area_1_3.setEditable(false);
        gui_Text_Area_1_4.setRows(2);
        gui_Text_Area_1_4.setColumns(100);
        gui_Text_Area_1_4.setEditable(false);
    }

//-- DON'T EDIT BELOW THIS LINE!!!
    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_4 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_3 = new com.codename1.ui.Label();
    private com.codename1.ui.Label gui_Label_2 = new com.codename1.ui.Label();
    private com.codename1.ui.TextArea gui_Text_Area_1 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Label gui_Label_6 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_2_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_1_1 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_4_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_4_1 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_3_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_3_1 = new com.codename1.ui.Label();
    private com.codename1.ui.Label gui_Label_2_1 = new com.codename1.ui.Label();
    private com.codename1.ui.TextArea gui_Text_Area_1_1 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Label gui_Label_7 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_1_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_2_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_1_2 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_4_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_4_2 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_3_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_3_2 = new com.codename1.ui.Label();
    private com.codename1.ui.Label gui_Label_2_2 = new com.codename1.ui.Label();
    private com.codename1.ui.TextArea gui_Text_Area_1_2 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Label gui_Label_8 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_1_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_2_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_1_3 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_4_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_4_3 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_3_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_3_3 = new com.codename1.ui.Label();
    private com.codename1.ui.Label gui_Label_2_3 = new com.codename1.ui.Label();
    private com.codename1.ui.TextArea gui_Text_Area_1_3 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Label gui_Label_9 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_1_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_2_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_1_4 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_4_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
    private com.codename1.ui.Label gui_Label_4_4 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_Container_3_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_3_4 = new com.codename1.ui.Label();
    private com.codename1.ui.Label gui_Label_2_4 = new com.codename1.ui.Label();
    private com.codename1.ui.TextArea gui_Text_Area_1_4 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Label gui_Label_5 = new com.codename1.ui.Label();


// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
        setTitle("Ajouter une demande");
        
        setupForm();
        setupInputActions();
    }// </editor-fold>
    
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
                    _ListOffersForm listOffersForm = new _ListOffersForm();
                    listOffersForm.showBack();
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
