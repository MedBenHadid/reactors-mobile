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
import com.codename1.io.NetworkEvent;
import com.codename1.ui.events.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import tn.esprit.reactors.chihab.models.Association;

/**
 *
 * @author Chihab
 */
public class AssociationService {
    private ArrayList<Association> associations;
    
    public static AssociationService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private AssociationService() {
         req = new ConnectionRequest();
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
        req.setPost(false);
        String optional = (!nom.isEmpty()?"/"+nom :"")+(!ville.isEmpty()?"/"+ville:"");
        req.setUrl(Statics.BASE_URL+"/api/associations/"+page+optional);
        page++;
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
    
    public boolean addAssociation(Association u) {
        String url = Statics.BASE_URL + "/association";
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
}
