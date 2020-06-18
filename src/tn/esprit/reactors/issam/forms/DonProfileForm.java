/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.forms;

import com.codename1.components.ImageViewer;
import com.codename1.io.Storage;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.InputStream;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.issam.models.Don;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;

import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;

import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import static com.codename1.ui.events.ActionEvent.Type.Response;
import com.codename1.util.Base64;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.forms.MapForm;
import tn.esprit.reactors.chihab.models.Category;
import tn.esprit.reactors.chihab.services.CategoryService;
/**
 *
 * @author Issam
 */
public class DonProfileForm extends ReactorsForm {

    private final Don don;
    private final DonProfileForm thisHolder;
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");

    private final FontImage p = FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, s);
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine1"));

    private final EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 3), false);
    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 12, PEOPLE.getHeight() * 6), false);

    private final SpanLabel descriptionSpanLabel = new SpanLabel();
    private final FontImage DESC = FontImage.createMaterial(FontImage.MATERIAL_LOCAL_LIBRARY, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PHONE = FontImage.createMaterial(FontImage.MATERIAL_SETTINGS_CELL, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage CAT = FontImage.createMaterial(FontImage.MATERIAL_DETAILS, UIManager.getInstance().getComponentStyle("MultiLine1"));

    private final ImageViewer imageViewr = new ImageViewer(PLACEHOLDER);
    private final MultiButton locationMultiButton = new MultiButton();
    private final MultiButton sendButton = new MultiButton();
    TextField popupBody = new TextField("", "Message", 50, TextArea.ANY);
    int h = Display.getInstance().getDisplayHeight();
    
    private final    SpanLabel spPhone = new SpanLabel();

   private final MapDonForm mf;
    private  List<Category> cats;

    public DonProfileForm(Form previous, Don don) {
    super(don.getTitle(), previous);
        InputStream is = null;
        this.don = don;
        this.thisHolder = this;
        this.mf = new MapDonForm(don.getTitle(), previous, don);
        Dialog d = new Dialog("SMS sent" );
               
        try {
            is = Storage.getInstance().createInputStream(String.valueOf(don.getId()));
            EncodedImage i = EncodedImage.create(is, is.available());
            ImageViewer imageViewer = new ImageViewer(i);
            this.addComponent(imageViewer);
        } catch (IOException ex) {
        }
        
        SpanLabel spDesc = new SpanLabel(don.getDescription());
        spDesc.setIcon(DESC);
        imageViewr.setImage(URLImage.createToStorage(PLACEHOLDER, "don" + don.getImagePath(), Statics.BASE_URL + "/api/don/image/download/" + don.getImagePath(), URLImage.RESIZE_SCALE));
        this.add(imageViewr);
        this.add(new Label("Descripton :")).add(ComponentGroup.enclose(spDesc));
        this.show();
              spPhone.setText(don.getPhone());

        spPhone.setIcon(PHONE);
        
        this.add(new Label("Phone :")).add(ComponentGroup.enclose(spPhone));
        
        
        
        
        
        
        
         try {
            this.cats = CategoryService.getInstance().fetchDraChnya();
               for (Category c : cats) 
                if (c.getId() == don.getDomaine()) {
                        SpanLabel SPdomaine = new SpanLabel(c.getNom());
                        SPdomaine.setIcon(CAT);
                        this.add(new Label("Domaine de donnation :")).add(ComponentGroup.enclose(SPdomaine));
                    //this.add(SpanLabel SPdomaine = new SpanLabel(c.getNom()));
                }
        } catch (IOException ex) {
        }
         
        
        
        locationMultiButton.setTextLine1(don.getAddress());
        locationMultiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_PLACE, UIManager.getInstance().getComponentStyle("MultiLine")));
        locationMultiButton.addActionListener(e -> {
          mf.show();
        });
        this.add(locationMultiButton);
          
          sendButton.setTextLine1("contact via   :"+don.getPhone());
          sendButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS_CELL, UIManager.getInstance().getComponentStyle("MultiLine")));
          sendButton.addActionListener(e -> {
              System.out.println("sms");
              String accountSID = "ACaceab88f5e2e1f1c06a9e3cc785c59f2";
              String authToken = "37c61524e9caf746710eeb6374437118";
              String fromPhone = "+12054967545";
              Response<Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                            queryParam("To", String.valueOf("+21652663728")).
                             queryParam("From", fromPhone).
                      queryParam("Body", "fhbssld").
                      header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                      getAsJsonMap();
                ToastBar.showInfoMessage("Message envoyé to " +don.getPhone());



          });
          

                      this.add(sendButton);
     
    }

}
