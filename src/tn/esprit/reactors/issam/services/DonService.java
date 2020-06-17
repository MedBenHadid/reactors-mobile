/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.issam.models.Don;
import tn.esprit.reactors.Statics;
/**
 *
 * @author Issam
 */
public class DonService {
      public ArrayList<Don> dons;
    
    public static DonService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;


        private DonService() {
         req = new ConnectionRequest();
    }

        public static DonService getInstance() {
        if (instance == null) {
            instance = new DonService();
        }
        return instance;
    }

        public boolean addTask(Don d) { 
        
            MultipartRequest req = new MultipartRequest();

            //http://127.0.0.1:8000/dons/don/apiX
        String url = Statics.BASE_URL + "/dons/don/addapi?title=" + d.getTitle()+ "&Description=" + d.getDescription()+ "&address="+d.getAddress()+"&phone="+d.getPhone()+"&latitude="+d.getLat()+ "&longitude=" +d.getLon()+"&image="+d.getImagePath()+"&domaine="+d.getDomaine();
           req.setUrl(url);
        
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




        public ArrayList<Don> parseDon(String jsonText){
            //method athiya jawha behi ? ena lmochkla mouch 3aréf él mochkl win okhak rit 
           try {
                dons=new ArrayList<>();
                JSONParser j = new JSONParser();
                Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

                List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");

                //ritha kifeh walet 10
                //ma3neha jawou behi el symfony éyy
                //problem fil method athiya
                for(Map<String,Object> obj : list){
                     Don d = new Don();
                    float id = Float.parseFloat(obj.get("id").toString());
                    d.setId((int)id);
                    d.setTitle(obj.get("title").toString());
                    d.setDescription(obj.get("description").toString());
                    d.setAddress(obj.get("address").toString());
                    d.setState(((int)Float.parseFloat(obj.get("state").toString())));
                    d.setPhone(obj.get("phone").toString());
                    d.setImagePath(obj.get("image").toString());
                    d.setLat((Double.parseDouble(obj.get("latitude").toString())));   
                    d.setLon((Double.parseDouble(obj.get("longitude").toString())));
                    d.setDomaine((int)(Double.parseDouble(((Map<String,Object>)obj.get("domaine")).get("id").toString())));
                    d.setUserId((int)(Double.parseDouble(((Map<String,Object>)obj.get("user")).get("id").toString())));
                    System.out.println(d.getUserId());

                    //d.setCreationDate(obj.get("creationDate").toString());    // win el erreur ?
                   dons.add(d);
                }

            } catch (IOException ex) {

            }
            
           return dons;
    }
        
        
        
        
        
  
    public ArrayList<Don> getAllDon(){
       String url = Statics.BASE_URL+"/dons/don/api";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    dons = parseDon(new String(req.getResponseData()));
                    
                } catch(Exception ex) {
                    System.out.println("***" + ex.getMessage() + "***");
                    
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(dons.size());
        return dons;
    }        
        
     
    
      public MultipartRequest uploadImage (String filePath, String fileName) throws IOException{
            MultipartRequest cr = new MultipartRequest();
            cr.setUrl(Statics.BASE_URL+"/api/don/upload/image");
            cr.setPost(true);
            try {
                cr.addData("file", filePath, "image/jpeg;image/jpeg;image/tiff;image/gif");
            } catch (IOException ex) {System.out.println(ex.getMessage());}
            cr.addArgument("filename", fileName);
            return cr;
        }

    
    
    
        
        public boolean delete(int donId)
    {
        //localhost:8000/refugees/api/hebergement/delete?id=39
        req.setUrl(Statics.BASE_URL + "/dons/don/remove?id=" + donId);
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
    
    public boolean update(Don d)
    {
     MultipartRequest req = new MultipartRequest();

            //http://127.0.0.1:8000/dons/don/api
        String url = Statics.BASE_URL + "/dons/don/apiedit?id=" +d.getId()+"&title="+d.getTitle() + "&Description=" + d.getDescription()+ "&address=" +d.getAddress()+ "&phone=" +d.getPhone()+ "&latitude="+d.getLat()+ "&longitude=" +d.getLon() + "&image=" +d.getImagePath()+"&domaine="+d.getDomaine();
        req.setUrl(url);
        req.setPost(true);
       
        
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

}

        

   

