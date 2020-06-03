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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.models.Category;

/**
 *
 * @author Chihab
 */
public class CategoryService {
    private ArrayList<Category> cats;
    
    public static CategoryService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private CategoryService() {
         req = new ConnectionRequest();
    }

    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryService();
        }
        return instance;
    }

    public Category parseCategory(Map<String,Object> obj){  
        return new Category( (int)(Double.parseDouble(obj.get("id").toString())),obj.get("name").toString(),obj.get("description").toString());
    }

    public java.util.List<Category> fetchDraChnya() throws IOException {
        req.setPost(false);
        req.setUrl(Statics.BASE_URL+"/api/domaines");
        NetworkManager.getInstance().addToQueueAndWait(req);
        cats=new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> associationListJson = j.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)associationListJson.get("root");
        for(Map<String,Object> obj : list){
           cats.add(parseCategory(obj));
        }
        return cats;
    }
}
