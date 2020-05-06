/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.services;

import tn.esprit.reactors.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.google.gson.Gson;
import java.io.IOException;
import tn.esprit.reactors.chihab.models.Association;

/**
 *
 * @author Chihab
 */
public class AssociationService {
    private final Gson g = new Gson();
    private static AssociationService instance=null;
    private ConnectionRequest req;
    public ArrayList<Association> asses;
    private AssociationService() {
         this.req = new ConnectionRequest();
         this.asses = new ArrayList();
    }

    public static AssociationService getInstance() {
        if (instance == null) {
            instance = new AssociationService();
        }
        return instance;
    }

    public Association parseAssociations(Map<String,Object> obj){  
        Association a = new Association( (int)(Double.parseDouble(obj.get("id").toString())),obj.get("nom").toString() );
        a.setApprouved(obj.get("approuved").toString().equals("true"));
        a.setDescription(obj.get("description").toString());
        a.setHoraireTravail(obj.get("horaireTravail").toString());
        a.setPhotoAgence(obj.get("photo").toString());
        a.setPieceJustificatif(obj.get("pieceJustificatif").toString());
        a.setRue(obj.get("rue").toString());
        a.setVille(obj.get("ville").toString());
        a.setLon(Double.valueOf(obj.get("longitude").toString()));
        a.setLat(Double.valueOf(obj.get("latitude").toString()));
        a.setCodePostal(Double.valueOf(obj.get("codePostal").toString()).intValue());
        a.setTelephone(Double.valueOf(obj.get("telephone").toString()).intValue());
        return a;
    }

    public java.util.List<Association> fetchDraChnya(String nom, String ville, int page) throws IOException {
        ArrayList<Association> associations;
        req.setUrl(Statics.BASE_URL+"/api/associations/"+page+((!nom.isEmpty()?"/"+nom :"")+(!ville.isEmpty()?"/"+ville:"")));
        req.setPost(false);
        NetworkManager.getInstance().addToQueueAndWait(req);
        associations=new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> associationListJson = j.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)associationListJson.get("root");
        for(Map<String,Object> obj : list){
           associations.add(parseAssociations(obj));
        }
        return associations;
    }
    
    public int addAssociation(Association a) {
        return (int)(Double.parseDouble(Rest.post(Statics.BASE_URL + "/api/association").contentType("application/json").body(g.toJson(a)).acceptJson().getAsJsonMap().getResponseData().get("id").toString()));
    }
    
    public Association findAssByManager(int managerId){
        return parseAssociations((Map<String,Object>) Rest.get(Statics.BASE_URL +"/api/associations/findoneby/manager/"+managerId).acceptJson().getAsJsonMap().getResponseData());
    }
}
