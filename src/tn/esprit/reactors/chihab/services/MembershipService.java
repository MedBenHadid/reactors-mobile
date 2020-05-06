/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.models.Membership;

/**
 *
 * @author Chihab
 */
public class MembershipService {
    private final Gson g;
    public static MembershipService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private MembershipService() {
         this.req = new ConnectionRequest();
         this.g = new Gson();
    }

    public static MembershipService getInstance() {
        if (instance == null) {
            instance = new MembershipService();
        }
        return instance;
    }
    /**
     * 
     * @param id
     * @param page
     * @param oneForAssociation
     * @param status
     * @return 
     */
    public java.util.List<Membership> fetchMemberships(int id, int oneForAssociation, String status) {
        try {
            req.setPost(false);
            req.setUrl(Statics.BASE_URL+"/api/associations/memberships/"+String.valueOf(id)+"/"+String.valueOf(oneForAssociation)+"/"+status);
            NetworkManager.getInstance().addToQueueAndWait(req);
            List<Membership> memberships=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> associationListJson = j.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)associationListJson.get("root");
            for(Map<String,Object> obj : list){
                Map<String,Object> user = (Map<String,Object>)obj.get("user");
                Map<String,Object> ass = (Map<String,Object>)obj.get("association");
                memberships.add(
                    new Membership( 
                        (int)(Double.parseDouble(obj.get("id").toString())),
                        (int)(Double.parseDouble(ass.get("id").toString())),
                        (int)(Double.parseDouble(user.get("id").toString())),
                        3,
                        obj.get("fonction").toString(),obj.get("description").toString() , 
                        obj.get("status").toString(),
                        (double)obj.get("longitude"),
                        (double)obj.get("latitude")
                    ));
            }
            return memberships;
        } catch(IOException err) {
            return null;
        }
    }
    
    
    public int addMembership(Membership m) {
        return (int)(Double.parseDouble(Rest.post(Statics.BASE_URL + "/api/membership").contentType("application/json").body(g.toJson(m)).acceptJson().getAsJsonMap().getResponseData().get("id").toString()));
    }

    public void update(Membership m) {
        Rest.patch(Statics.BASE_URL + "/api/membership/update").contentType("application/json").body(g.toJson(m)).getAsBytes();
}   }
