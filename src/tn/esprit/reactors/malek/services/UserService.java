/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.malek.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.rest.Rest;
import java.io.IOException;
import java.util.Map;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.chihab.models.enums.RoleEnum;

/**
 *
 * @author Chihab
 */
public class UserService {

    private User user=null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    private static UserService instance=null;
    private boolean resultOK;
    private ConnectionRequest req;

    private UserService() {
         this.req = new ConnectionRequest();
         // 78
         this.user=new User(105, "admin@enactus.tn", "assadmin");
         //this.user.addRole(RoleEnum.ROLE_ADMIN_ASSOCIATION);
         //this.user.addRole(RoleEnum.ROLE_MEMBER);
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    public boolean isLoggedIn(){
        return user!=null;
    }

    
    public boolean login(String cred, String password){
        //this.user=parseUser();
        return false;
    }
    public User parseUser(Map<String,Object> obj) throws IOException{  
        User u;
        u = new User((int)(Double.parseDouble(obj.get("id").toString())),
                obj.get("email").toString(),
                obj.get("username").toString(),
                obj.get("password").toString(),
                obj.get("nom").toString(),
                obj.get("prenom").toString(),
                (int) Double.parseDouble(obj.get("telephone").toString()),
                obj.get("adresse").toString(),
                obj.get("image").toString(),
                obj.get("cin").toString());
        return u;
    }
    
    public User getUser(int id){
        try {
            return parseUser(Rest.get(Statics.BASE_URL+"/api/user/"+id).acceptJson().getAsJsonMap().getResponseData());
        } catch (IOException ex) {
            return null;
        }
    }
    
    public User getUserProfile(int id) throws IOException{
        return parseProfile(Rest.get(Statics.BASE_URL+"/api/user/"+id).acceptJson().getAsJsonMap().getResponseData());
    }
    
    public User parseProfile(Map<String,Object> obj) throws IOException{  
        return new User(obj.get("username").toString(),obj.get("image").toString());
    }
}
