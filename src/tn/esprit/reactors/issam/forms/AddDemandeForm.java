/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.forms;

import com.codename1.components.ImageViewer;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.issam.models.Demande;
import tn.esprit.reactors.issam.services.DemandeService;
import tn.esprit.reactors.malek.services.UserService;

/**
 *
 * @author Issam
 */
public class AddDemandeForm extends Form{
 private Form _parent;
    
    Label titleL;
    Label descL;
    Label addressL;
    Label phoneL;
    Label imageL;
    Label longL;
    Label latL;
    Label ribL;
    TextField descriptionInput;
    TextField titleInput;
    TextField addressInput;
    TextField phoneInput;
    TextField longInput;
    TextField latInput;
    TextField ribInput; 
    Button imagePicker;
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");

    String filePath;

     private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_ADD_A_PHOTO, s);


    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 8, PEOPLE.getHeight() * 8), false); 


    Button submitBtn;
    
    File file;
        private final ImageViewer imageViewr = new ImageViewer(PLACEHOLDER);



   private Image img = null;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    
    public AddDemandeForm(Form parent)
    {
        _parent = parent;
        style();
        setup(); 
    }
    
    private void setup()
    {
        this.setTitle("Ajouter une donnation");
        setupForm();
        setupInputActions();
        setupToolbar();
    }
    
    private void setupInputActions()
    {
         
        this.imagePicker.addActionListener(e -> {
            
            
            /**        String i = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if (i != null)
            {                    
                file = new File(i);
            }
        });
            **/
            Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
                
                    filePath = (String) ev.getSource();
                    try {
                        this.setImg(Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath)));
                        imageViewr.setImage(img);
                       
                    } catch (IOException e1) {
                    }
            }, Display.GALLERY_IMAGE);
 
        });    
        
        submitBtn.addActionListener((evt) -> 
        {             if (validateForm())
        {
            Demande demande = new Demande();
            demande.setUserId(UserService.getInstance().getUser().getId());
            demande.setTitle(titleInput.getText());
            demande.setDescription(descriptionInput.getText());
            demande.setLat(Double.parseDouble(latInput.getText()));
            demande.setLon(Double.parseDouble(longInput.getText()));
            demande.setPhone(phoneInput.getText());
            demande.setAddress(addressInput.getText());
            demande.setRib(ribInput.getText());
            demande.setImage(new File(filePath));
            Dialog.show("Success","Merci pour votre aide",new Command("OK"));
            boolean result = DemandeService.getInstance().addTask(demande);
            
             if (result)
                {
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
        titleL = new Label("Titre: ");
        descL = new Label("Description: ");
        addressL = new Label("adresse: ");
        phoneL = new Label("Telephone: ");
        latL = new Label("long: ");
        longL = new Label("lat: ");
        ribL = new Label("rib : ");
        imageL = new Label("Image: ");
        titleInput = new TextField();
        
        descriptionInput = new TextField();
        addressInput = new TextField();
        phoneInput = new TextField();
        latInput = new TextField();
        longInput = new TextField();
        ribInput = new TextField();
        imagePicker = new Button("Choisir une image");
        
        submitBtn = new Button("Ajouter");
        this.addAll(
                inlineFormGroup(titleL, titleInput),
                inlineFormGroup(descL, descriptionInput),
                inlineFormGroup(addressL, addressInput),
                inlineFormGroup(phoneL, phoneInput),
                inlineFormGroup(latL, latInput),
                inlineFormGroup(longL, longInput),
                
                inlineFormGroup(ribL, ribInput),
                inlineFormGroup(imageL, imagePicker),
                inlineFormGroup(imageViewr)   ,
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
        private boolean validateForm() {
 boolean result = true;
        
        if (anyInputFieldIsEmpty())
        {
            result = false;
        }
        else if (file == null)
        {
            result = false;
        }
     
        
        return result;
    }
        
        
        
            private boolean anyInputFieldIsEmpty() {
  boolean result = false;
        
        if (titleInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (descriptionInput.getText().trim().isEmpty())
        {
            result = true;
        }
        else if (addressInput.getText().trim().isEmpty())
        {
            result = true;
        }
         else if (ribInput.getText().trim().isEmpty())
        {
            result = true;
        }
        return result;    }
            
            
             private void resetForm()
    {
        descriptionInput.setText("");
        titleInput.setText("");
        addressInput.setText("");
        ribInput.setText("");
        file = null;
    }


}
