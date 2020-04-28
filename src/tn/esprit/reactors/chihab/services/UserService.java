/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.services;

import com.codename1.io.ConnectionRequest;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import tn.esprit.reactors.chihab.models.User;

/**
 *
 * @author Chihab
 */
public class UserService {
    public ArrayList<User> users;
    
    public static UserService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private UserService() {
         req = new ConnectionRequest();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User parseUser(Map<String,Object> obj) throws IOException{  
        User u = new User((int)(Double.parseDouble(obj.get("id").toString())),obj.get("email").toString(),obj.get("username").toString());
        
        return u;
    }
}
