/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.services;

import com.codename1.io.ConnectionRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.chihab.models.User;

/**
 *
 * @author Chihab
 */
public class UserService {
    private List<User> users;
    private List<Membership> memberships;
    private User user=null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public static UserService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private UserService() {
         this.req = new ConnectionRequest();
         this.user=new User(74, "admin@enactus.tn", "assadmin");
         this.memberships=MembershipService.getInstance().fetchMemberships(user.getId(), 0, Membership.ACCEPTED);
         System.out.println(this.memberships);
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
    public boolean isMember(int assId){
        if(!isLoggedIn())
            return false;
        else
            for(Membership m : this.memberships)
                if(m.getAssId()==assId)
                    return true;
        return false;
    }
    public boolean login(String cred, String password){
        //this.user=parseUser();
        return false;
    }
    public User parseUser(Map<String,Object> obj) throws IOException{  
        User u = new User((int)(Double.parseDouble(obj.get("id").toString())),obj.get("email").toString(),obj.get("username").toString());
        return u;
    }
    
    
}
