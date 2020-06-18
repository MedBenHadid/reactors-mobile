/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors;


import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.UIBuilder;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import tn.esprit.reactors.nasri.entities.HebergementOffer;
import tn.esprit.reactors.nasri.services.ServiceHebergementOffer;
import tn.esprit.reactors.nasri.entities.HebergementRequest;
import tn.esprit.reactors.nasri.enums.CivilStatus;
import tn.esprit.reactors.nasri.enums.HebergementStatus;
import tn.esprit.reactors.nasri.gui.HomeForm;
import tn.esprit.reactors.nasri.gui.MainForm;
import tn.esprit.reactors.nasri.gui.test.*;
import tn.esprit.reactors.nasri.services.ServiceHebergementRequest;

/**
 *
 * @author nasri
 */
public class MyApplicationNasri {
    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
        //hebergementOfferCrud();
        //hebergementRequestCrud();
        
        
        
        //Form hi = new InboxForm();
        Form hi = new HomeForm();
        hi.show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

    
    private void hebergementRequestCrud()
    {
       HebergementRequest entity = new HebergementRequest();
       entity.setId(17);
       entity.setDescription("abc");
       entity.setNativeCountry("USA");
       entity.setArrivalDate(new Date());
       entity.setPassportNumber("123");
       entity.setCivilStatus(CivilStatus.Married);
       entity.setChildrenNumber(3);
       entity.setRegion("abc");
       entity.setState(HebergementStatus.inProcess);
       entity.setName("nasri");
       entity.setTelephone("58808609");
       entity.setAnonymous(true);
       entity.setUserId(80);
       
       boolean result = ServiceHebergementRequest.getInstance().update(entity);
        
       //ArrayList<HebergementRequest> requests = ServiceHebergementRequest.getInstance().getAll();
       //ServiceHebergementRequest.getInstance().delete(16);
       System.out.println("lol");
    }
    
    
    private void hebergementOfferCrud()
    {
        //ArrayList<HebergementOffer> offers = ServiceHebergementOffer.getInstance().getAll();
        
        HebergementOffer entity = new HebergementOffer();
        entity.setId(17);
        entity.setDescription("a");
        entity.setGovernorat("b");
        entity.setNumberRooms(3);
        entity.setDuration(3);
        entity.setTelephone("58808609bvcv");
        entity.setUserId(80);
        
        
        Image screenshot = Image.createImage(300, 300);

        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot123.png";
        try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
            entity.setImage(new File(imageFile));
        } catch(IOException err) {
            System.out.println(err.getMessage());
        }
           
        
        boolean result = ServiceHebergementOffer.getInstance().update(entity);
        
        //ServiceHebergementOffer.getInstance().delete(38);
    }
}
