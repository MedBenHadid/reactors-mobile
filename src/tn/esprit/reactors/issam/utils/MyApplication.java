/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.utils;

import com.codename1.io.Log;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.getCurrentForm;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import tn.esprit.reactors.issam.forms.HomeForm;
/**
 *
 * @author Issam
 */
public class MyApplication {
      private Form current;
      private Resources theme;


    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        // lazemni ntala3 enehi el main cho mouch mén hné ? 
        // trah 3awed 7elha fenetre athika
        // mela la3b ti hatta él main mtaa chihab hattou fi comment fhémtéch féch yrani
        // behi staneni chwaya bech ncherchi fil googleok
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
        new HomeForm().show();
        //hethi chniya HomeForm ?
        //enti 9aditha wela coppier coller men 3and chiheb ? nn gaditha ok
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

}
