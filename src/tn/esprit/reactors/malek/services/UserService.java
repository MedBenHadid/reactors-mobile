/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.malek.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Rest;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.chihab.models.enums.RoleEnum;

/**
 *
 * @author Chihab
 */
public class UserService {
    String s;
     private ConnectionRequest req;
         public static UserService  instance=null;
  public ArrayList<User> tasks;
               public User ti=new User();

    private User user=null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
   

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
    public User parseUser(String jsonText){
              System.out.println("**********"+jsonText);
                User t= new User();
              try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
          //  System.out.println(tasksListJson.get(0));
           
              float id = Float.parseFloat(tasksListJson.get("id").toString());
                t.setId((int)id);
                
         
                t.setUsername(tasksListJson.get("username").toString());
                t.setEmail(tasksListJson.get("email").toString());
                t.setPassword(tasksListJson.get("password").toString());
               
                
                tasks.add(t);
            
            
            
        } catch (IOException ex) {
            
        }
        return t;
    }
    
   
    public User getUser(String username){
      //   req.setUrl(Statics.BASE_URL + "/Forum/searchUserMail/"+id);
       
       
        req.setUrl(Statics.BASE_URL + "/Forum/findby/"+username);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("..........");
                System.out.println(req.getUrl());
                ti = parseUser(new String(req.getResponseData()));
                 //System.out.println("chnia mochkol "+tasks);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ti;
    }
    
    public User getUserProfile(int id) throws IOException{
        return parseProfile(Rest.get(Statics.BASE_URL+"/api/user/"+id).acceptJson().getAsJsonMap().getResponseData());
    }
    
    public User parseProfile(Map<String,Object> obj) throws IOException{  
        return new User(obj.get("username").toString(),obj.get("image").toString());
    }
    public void ajouterUser(String username,String password,String email,String prenom,String nom){
    

     
    
     req.setUrl(Statics.BASE_URL + "/Forum/register");
        

        req.setPost(true);
        
        req.addArgumentNoEncoding("nom", nom);
        req.addArgumentNoEncoding("prenom", prenom);
        req.addArgumentNoEncoding("username", username);
        req.addArgumentNoEncoding("email", email);
        req.addArgumentNoEncoding("password", password);
        
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    
    
    }
    
    public boolean getUsercherche(String id){

     
        req.setUrl(Statics.BASE_URL + "/Forum/chercherUser/"+id);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 s = new String(req.getResponseData());
                s=s.substring(1,s.length()-1);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return s.equals("1");
             
    }
    public boolean getUserchercheMail(String id){
       
     
         req.setUrl(Statics.BASE_URL + "/Forum/searchUserMail/"+id);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                s =new String(req.getResponseData());
                s=s.substring(1,s.length()-1);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return s.equals("1");
             
    }
}
