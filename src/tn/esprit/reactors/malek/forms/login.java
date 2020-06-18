/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.malek.forms;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import tn.esprit.reactors.malek.models.User;
import org.mindrot.jbcrypt.*;

import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.malek.services.UserService;

/**
 *
 * @author Malek
 */
public class login extends ReactorsForm {
    Form current;
    static User t=new User();
public login()
        
{
    current=this;
        setTitle("Login");
        setLayout(BoxLayout.y());
        Style loginStyle= getAllStyles();
        loginStyle.setBgColor(0xd3d3d3);
         Container cnt1=new Container(new FlowLayout(Container.CENTER));
                  Container cnt4=new Container(new FlowLayout(Container.CENTER));
        Container cnt5=new Container(new FlowLayout(Container.CENTER));

        Container cnt2=new Container(new FlowLayout(Container.CENTER));
        Container cnt3=new Container(new FlowLayout(Container.CENTER));
        Label Seconnecter = new Label("Se connecterrrr");
  Style connecterStyle=Seconnecter.getAllStyles();
  //Style logoStyle=Logo.getAllStyles();
 //logoStyle.setMargin(Component.TOP,0); 

//logoStyle.setMargin(Component.BOTTOM,0);
  
      Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE);

  connecterStyle.setFont(largeBoldSystemFont);
connecterStyle.setFgColor(0x000000);
connecterStyle.setMargin(Component.TOP,0); 
connecterStyle.setMargin(Component.LEFT,280); 
connecterStyle.setMargin(Component.BOTTOM,0);
  
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
                        TextField tpassword = new TextField();
           Style passwordStyle = tpassword.getAllStyles();
passwordStyle.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(50).
        stroke(borderStroke));
passwordStyle.setBgColor(0xffffff);
passwordStyle.setBgTransparency(255);                
                        
                        tpassword.setHint("saisir votre mot de passe");
        tpassword.setConstraint(TextField.PASSWORD);
cnt2.addAll(Username);
cnt3.add(tpassword);
                //cnt1.add(Logo);
             
    /**************************************************************************************************/             
                        Button btnval = new Button("valider");
Style butStyle=btnval.getAllStyles();
butStyle.setBorder(RoundRectBorder.create().
        strokeColor(0xA0522D).
        strokeOpacity(120).
        stroke(borderStroke));
butStyle.setBgColor(0x904201);
butStyle.setFgColor(0x000000);
butStyle.setBgTransparency(255);
butStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
butStyle.setMargin(Component.BOTTOM, 3);       

          butStyle.setMargin(Component.TOP,10);  
              
          butStyle.setMargin(Component.LEFT,10);  
           butStyle.setMargin(Component.RIGHT,10); 
           Button inscrire= new Button("Inscrire");
                Style butStyle2=inscrire.getAllStyles();

butStyle2.setBgColor(0xCD853F);
butStyle2.setFgColor(0x000000);
butStyle2.setBgTransparency(0);
butStyle2.setMarginUnit(Style.UNIT_TYPE_DIPS);
butStyle2.setMargin(Component.TOP,0);       
inscrire.addActionListener((evt) -> {
    new register().show();
});
                //cnt5.add(btnval);
            addAll(cnt1,Seconnecter,cnt2,cnt3,btnval,cnt5,inscrire);  
                    btnval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((Username.getText().length()==0)||(tpassword.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {              

 t=UserService.getInstance().getUser(Username.getText());
                        System.out.println(".."+t);
String pw = t.getPassword();
                        System.out.println("........................");
                        System.out.println(pw);
String pw1=pw.substring(4);
String pw11="$2a$"+pw1;
                System.out.println("pw1 = " +pw11);
              
                
                        if(t.equals(null))
                        {
                                            Dialog.show("ERROR", "User not found", new Command("OK"));

                        }else if(BCrypt.checkpw(tpassword.getText(), pw11)==false){
                                                                    Dialog.show("ERROR", "Mot de passe invalide", new Command("OK"));

                        }
                           else{
                            Dialog.show("Bienvenue!", "connecté", new Command("OK"));
                        
                        }
                        
                                
                                
                  
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                    
                }
                
                
            }
        });
    /**************************************************************************************************/ 
         getToolbar().addCommandToOverflowMenu("Exit",
            null, ev->{Display.getInstance().exitApplication();});
          
    
}}
     
  /**************************************************************************************************/               
                
               /** Button motOublier= new Button("Mot de passe oublié ?");
                Style butStyle1=motOublier.getAllStyles();

butStyle1.setBgColor(0xCD853F);
butStyle1.setFgColor(0x000000);
butStyle1.setBgTransparency(0);
butStyle1.setMarginUnit(Style.UNIT_TYPE_DIPS);
butStyle1.setMargin(Component.TOP,10);       

          butStyle1.setMargin(Component.TOP,0);
          cnt5.add(motOublier);
  /**************************************************************************************************/ 
         /** Button inscrire= new Button("Inscrire");
                Style butStyle2=inscrire.getAllStyles();

butStyle2.setBgColor(0xCD853F);
butStyle2.setFgColor(0x000000);
butStyle2.setBgTransparency(0);
butStyle2.setMarginUnit(Style.UNIT_TYPE_DIPS);
butStyle2.setMargin(Component.TOP,0);  */     
/**************************************************************************************************/ 
                   
                    
                
                

    


    

