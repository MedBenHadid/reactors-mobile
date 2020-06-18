/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.rami.services;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.RequestBuilder;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.OnComplete;
import com.google.gson.Gson;
import tn.esprit.reactors.rami.models.Rponse;
import tn.esprit.reactors.rami.models.Requete;
import tn.esprit.reactors.rami.utils.DataSource;
import tn.esprit.reactors.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class RequeteService {
    private ConnectionRequest request;
    private static RequeteService instance;
    private boolean responseResult;
    public ArrayList<Requete> req;
    public Rponse rep = new Rponse();
    private RequeteService() {
        request = DataSource.getInstance().getRequest();
    }
    public static RequeteService getInstance() {
        if (instance == null) {
            instance = new RequeteService();
        }
        return instance;
    }
    
    public boolean addRequete(Requete req) {
        /*Gson g = new Gson();
        System.out.println(g.toJson(req));
        RequestBuilder body = Rest.post("http://127.0.0.1:8000/reclamation/requete/api/add").acceptJson().body(g.toJson(req)).jsonContent();
        body.fetchAsJsonMap((Response<Map> v) -> {
            responseResult = v.getResponseCode() == 200;
            System.out.println(v.getResponseData());
        });
        return responseResult;*/
        MultipartRequest request = new MultipartRequest();
        //request.setPost(true);
        
        request.setUrl("http://127.0.0.1:8000/reclamation/requete/api/add");
        
        request.addArgumentNoEncoding("description", req.getdescription());
        request.addArgumentNoEncoding("sujet", req.getsujet());
        request.addArgumentNoEncoding("type", String.valueOf(req.gettype()));
        
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200;
                request.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;

    }
    public boolean deleteRequete(Requete req) {
        
        MultipartRequest request = new MultipartRequest();
        //request.setPost(true);
        
        request.setUrl("http://127.0.0.1:8000/reclamation/requete/"+req.getid()+"/api/delete");
        
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200;
                request.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;

    }
    public ArrayList<Requete> getAllRequests() {
        String url = Statics.BASE_URL + "/reclamation/requete/api/showall";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req = parseRequete(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return req;
    }
    
    public Rponse getResponse(int requeteId) {
        String url = Statics.BASE_URL + "/reclamation/requete/api/" + requeteId + "/show";
        

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                rep = parseReponse(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return rep;
    }
    
    public Rponse parseReponse(String jsonText) {
        Rponse rep = new Rponse();
        try {

            JSONParser jp = new JSONParser();
            Map<String, Object> reponseJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            int id = (int)Float.parseFloat(reponseJSON.get("id").toString());
                
            String sujet = reponseJSON.get("sujet").toString();
            String reponse = reponseJSON.get("rep").toString();
                
            rep.setId(id);
            rep.setRep(reponse);
            rep.setSujet(sujet);

        } catch (IOException ex) {
        }

        return rep;
    }
    
    public ArrayList<Requete> parseRequete(String jsonText) {
        try {
            req = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            
            
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());
                
                int statut = (int)Float.parseFloat(obj.get("statut").toString());
                int type = (int)Float.parseFloat(obj.get("type").toString());
                String sujet = obj.get("sujet").toString();
                String description = obj.get("description").toString();
                req.add(new Requete(id, description, sujet, statut, type));
            }

        } catch (IOException ex) {
        }

        return req;
    }
    public boolean updateRequete(Requete req) {
        /*Gson g = new Gson();
        System.out.println(g.toJson(req));
        RequestBuilder body = Rest.post("http://127.0.0.1:8000/reclamation/requete/api/add").acceptJson().body(g.toJson(req)).jsonContent();
        body.fetchAsJsonMap((Response<Map> v) -> {
            responseResult = v.getResponseCode() == 200;
            System.out.println(v.getResponseData());
        });
        return responseResult;*/
        MultipartRequest request = new MultipartRequest();
        //request.setPost(true);
        
        request.setUrl("http://127.0.0.1:8000/reclamation/requete/"+req.getid()+"/api/edit");
        
        request.addArgumentNoEncoding("description", req.getdescription());
        request.addArgumentNoEncoding("sujet", req.getsujet());
        
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200;
                request.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;

    }
    
    
}
