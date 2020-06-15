/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.malek.forms;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import tn.esprit.reactors.malek.models.User;
import org.mindrot.jbcrypt.BCrypt;

import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.malek.services.UserService;
/**
 *
 * @author Malek
 */
public class register extends ReactorsForm {
    Form current;
    String imageName="";
      private String imgName = "";
    private String imgPath = "";
    
    boolean imageselected = false;
     public register(){
         current=this;
        setTitle("Register");
        setLayout(BoxLayout.y());
        Style loginStyle= getAllStyles();
        loginStyle.setBgColor(0xCD853F);
        getToolbar().addMaterialCommandToLeftBar("back", 
                FontImage.MATERIAL_ARROW_BACK, ev->{new login().show();});
         Container cnt1=new Container(new FlowLayout(Container.CENTER));
                  Container cnt4=new Container(new FlowLayout(Container.CENTER));
        Container cnt5=new Container(new FlowLayout(Container.CENTER));

        Container cnt2=new Container(new FlowLayout(Container.CENTER));
        Container cnt3=new Container(new FlowLayout(Container.CENTER));
               
                
     /**************************************************************************************************/            
  Label Seconnecter = new Label("S'inscrire");
  Style connecterStyle=Seconnecter.getAllStyles();
   Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE);

  connecterStyle.setFont(largeBoldSystemFont);
connecterStyle.setFgColor(0x000000);
connecterStyle.setMargin(Component.TOP,130); 
connecterStyle.setMargin(Component.LEFT,280); 
connecterStyle.setMargin(Component.BOTTOM,3);
  
/**************************************************************************************************/ 

                        TextField Username = new TextField("", "saisir votre username");
                        Style userStyle = Username.getAllStyles();
Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
userStyle.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(120).
        stroke(borderStroke));
userStyle.setBgColor(0xffffff);
userStyle.setBgTransparency(255);
userStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
userStyle.setMargin(Component.BOTTOM, 3);       
          userStyle.setMargin(Component.TOP,10);                
      /**************************************************************************************************/  
                              TextField mail = new TextField("", "saisir votre mail");
                        Style userStylemail = mail.getAllStyles();
Stroke borderStroke1 = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
userStylemail.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(120).
        stroke(borderStroke));
userStylemail.setBgColor(0xffffff);
userStylemail.setBgTransparency(255);
userStylemail.setMarginUnit(Style.UNIT_TYPE_DIPS);
userStylemail.setMargin(Component.BOTTOM, 3);       
          userStylemail.setMargin(Component.TOP,1);                
      /**************************************************************************************************/  
               TextField nom = new TextField("", "saisir votre nom");
                        Style userStylnom = nom.getAllStyles();
Stroke borderStroke2 = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
userStylnom.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(120).
        stroke(borderStroke));
userStylnom.setBgColor(0xffffff);
userStylnom.setBgTransparency(255);
userStylnom.setMarginUnit(Style.UNIT_TYPE_DIPS);
userStylnom.setMargin(Component.BOTTOM, 3);       
          userStylnom.setMargin(Component.TOP,1);                
      /**************************************************************************************************/ 
               TextField prenom = new TextField("", "saisir votre prenom");
                        Style userStylprenom = prenom.getAllStyles();
Stroke borderStroke3 = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
userStylprenom.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(120).
        stroke(borderStroke));
userStylprenom.setBgColor(0xffffff);
userStylprenom.setBgTransparency(255);
userStylprenom.setMarginUnit(Style.UNIT_TYPE_DIPS);
userStylprenom.setMargin(Component.BOTTOM, 3);       
          userStylprenom.setMargin(Component.TOP,1);                
      /**************************************************************************************************/  
          
    
                //cnt5.add(btnval);
      
     /**************************************************************************************************/       
      
                        TextField tpassword = new TextField();
           Style passwordStyle = tpassword.getAllStyles();
passwordStyle.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(50).
        stroke(borderStroke));
passwordStyle.setBgColor(0xffffff);
passwordStyle.setBgTransparency(255);                
                         ComboBox tfType = new ComboBox("volentaire","refugier");
                        tpassword.setHint("saisir votre mot de passe");
        tpassword.setConstraint(TextField.PASSWORD);
cnt2.addAll(Username,mail,nom,prenom,tfType);
cnt3.add(tpassword);
              
    /**************************************************************************************************/ 



/***********************************************************************************/

                        Button btnval = new Button("valider");
Style butStyle=btnval.getAllStyles();
butStyle.setBorder(RoundRectBorder.create().
        strokeColor(0xA0522D).
        strokeOpacity(120).
        stroke(borderStroke));
butStyle.setBgColor(0xA0522D);
butStyle.setFgColor(0x000000);
butStyle.setBgTransparency(255);
butStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
butStyle.setMargin(Component.BOTTOM, 3);       

          butStyle.setMargin(Component.TOP,10);  
              
          butStyle.setMargin(Component.LEFT,10);  
           butStyle.setMargin(Component.RIGHT,10); 
                //cnt5.add(btnval);
          
                
             
                
                UserService us = UserService.getInstance();
               
            
               
               ImageViewer pdp = new ImageViewer();
         
        Container CC =new Container(BoxLayout.xCenter());
addAll(Seconnecter,cnt2,cnt3,CC,btnval,cnt5);   
btnval.addActionListener((evt) -> {
    String strong_salt = BCrypt.gensalt(13);
    String S= BCrypt.hashpw(tpassword.getText(),strong_salt);
     if(Username.getText().length()==0 || mail.getText().length()==0 ||tpassword.getText().length()==0 )
   Dialog.show("Error", "Remplir touts les champs ",new Command("ok"));
   else if(us.getUsercherche(Username.getText()))
         Dialog.show("Error", "username deja exisite ", new Command("ok"));
   else if(us.getUserchercheMail(mail.getText()))
       Dialog.show("Error", "mail deja exisite ", new Command("ok"));
   else if(tpassword.getText().length()< 8)
       Dialog.show("Error", "mot de passe < 8 ", new Command("ok"));
    else if(tpassword.getText().length()< 8)
       Dialog.show("Error", "mot de passe < 8 ", new Command("ok"));
   else{
  
       
   us.ajouterUser(Username.getText(),S,mail.getText(),nom.getText(),prenom.getText());
    System.err.println(us);
   Dialog.show("Valide", "user ajoute", new Command("ok"));
    
            
    
    new login().show();}
});
    
}}
