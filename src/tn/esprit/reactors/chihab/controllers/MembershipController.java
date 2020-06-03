package tn.esprit.reactors.chihab.controllers;

import com.codename1.io.rest.Response;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.malek.services.UserService;
import tn.esprit.reactors.chihab.services.MembershipService;

public class MembershipController {
    public int createMembership(Membership m){
        Response<Map> addResponse = this.membershipApi.create(m);
        if(addResponse.getResponseCode()==200){
            return (int)Double.parseDouble(addResponse.getResponseData().get("id").toString());
        }else {
            return 0;
        }
    }
    
    public List<Membership> readMemberships(int id, int oneForAssociation, String status){
        try {
            return this.membershipApi.fetchMemberships(id, oneForAssociation, status);
        } catch (IOException ex) {
            return null;
        }
    }
    
    public String updateMembership(Membership m){
        Response<Map> addResponse = this.membershipApi.update(m);
        if(addResponse.getResponseCode()!=200){
            return addResponse.getResponseErrorMessage();
        }else {
            return "";
        }
    }
    
    public String deleteMembership(int mId){
        Response<Map> addResponse = this.membershipApi.delete(mId);
        if(addResponse.getResponseCode()!=200){
            return addResponse.getResponseErrorMessage();
        }else {
            return "";
        }
    }
    
    public String getStatusByAss(int assId){
        for(Membership m : this.memberships){
            if(m.getAssId()==assId)
                return m.getStatus();
        }
        return "";
    }
    
    public boolean isMember(int assId){
        if(!us.isLoggedIn())
            return false;
        else
            for(Membership m : this.memberships)
                if (m.getAssId()==assId) 
                    return true;
        return false;
    }
    
    public boolean isProspectAffiliate(){
        if(!us.isLoggedIn())
            return false;
        else
        for(Membership m : this.memberships)
            if(m.getMemberId()==this.us.getUser().getId())
                return true;
        return false;
    }
    
    public boolean isRequestSent(int assId){
        if(!us.isLoggedIn())
            return false;
        else
        for(Membership m : this.memberships)
            if(m.getAssId()==assId)
                return true;
        return false;
    }
    
    
    private UserService us;
    private List<Membership> memberships;

    public List<Membership> getMemberships() {
        return memberships;
    }
    private MembershipService membershipApi;
    private static MembershipController instance;
    private MembershipController() {
        this.us=UserService.getInstance();
        this.membershipApi = MembershipService.getInstance();
        this.memberships = this.readMemberships(us.getUser().getId(), 0, Membership.ALL);
    }
    public static MembershipController getInstance() {
        if (instance == null) {
            if(UserService.getInstance().isLoggedIn())
                instance = new MembershipController();
        }
        return instance;
    }
}
