/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.issam.models.Demande;
/**
 *
 * @author Issam
 */
public class DemandeService {
        public ArrayList<Demande> demande;
    
    public static DemandeService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;


        private DemandeService() {
         req = new ConnectionRequest();
    }

        public static DemandeService getInstance() {
        if (instance == null) {
            instance = new DemandeService();
        }
        return instance;
    }
     public boolean addTask(Demande d) { 
                     MultipartRequest req = new MultipartRequest();
 
         //http://127.0.0.1:8000/dons/don/api
        String url = Statics.BASE_URL + "/dons/demande/addapi?title=" + d.getTitle()+ "&Description=" + d.getDescription()+ "&address=" +d.getAddress()+ "&phone=" +d.getPhone()+ "&latitude="+d.getLat()+ "&longitude=" +d.getLon()+"&rib=" + d.getRib()+"&image="+d.getImage();
        req.setUrl(url);
              try
        {   
            req.addData("image", 
                    d.getImage().getAbsolutePath(), 
                    "image/jpg;image/jpg;image/tiff;image/gif");
            
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });

          NetworkManager.getInstance().addToQueueAndWait(req);
            return resultOK;
            }



        public ArrayList<Demande> parseDon(String jsonText){
            //method athiya jawha behi ? ena lmochkla mouch 3aréf él mochkl win okhak rit 
           try {
                demande=new ArrayList<>();
                JSONParser j = new JSONParser();
                Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

                List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");

                //ritha kifeh walet 10
                //ma3neha jawou behi el symfony éyy
                //problem fil method athiya
                for(Map<String,Object> obj : list){
                     Demande d = new Demande();
                    float id = Float.parseFloat(obj.get("id").toString());
                    d.setId((int)id);
                    d.setTitle(obj.get("title").toString());
                    d.setDescription(obj.get("description").toString());
                    d.setAddress(obj.get("address").toString());
                    d.setPhone(obj.get("phone").toString());
                    d.setRib(obj.get("rib").toString());
// d.setLat((Double.parseDouble(obj.get("latitude").toString())));   
                    //d.setLon((Double.parseDouble(obj.get("longitude").toString())));
                    //d.setCreationDate(obj.get("creationDate").toString());    // win el erreur ?
                   demande.add(d);
                }

            } catch (IOException ex) {

            }
            
           return demande;
    }
        
        
        
        
        
  
    public ArrayList<Demande> getAllDon(){
        //http://127.0.0.1:8000/dons/don/api
        //hethi erreeur 404 ? nn 9otlék gaitha 404 
        //hédhi ként tjib null exception
        //ok
        String url = Statics.BASE_URL+"/dons/demande/api";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    demande = parseDon(new String(req.getResponseData()));
                } catch(Exception ex) {
                    System.out.println("***" + ex.getMessage() + "***");
                    //taw ya3jbekchi hhhhhhhhhhhhhhhhhhhhhhhh hhhhh mais majabétéch kol chay
                    
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return demande;
    }        
        
}