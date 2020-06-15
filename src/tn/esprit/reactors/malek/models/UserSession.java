 package tn.esprit.reactors.malek.models;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.RequestBuilder;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.malek.services.UserService;

public final class UserSession {
    private static UserSession instance;
    private User user;
    private boolean valideUser = false;

    public boolean login(String username, String password){
        MultipartRequest ml = new MultipartRequest();
        ml.addArgument("username", username);
        ml.addArgument("password", password);
        ml.setUrl(Statics.BASE_URL + "/api/user/login");
        ml.setPost(true);
        ml.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                int x = 123;
                if(evt.getResponseCode()==200){
                    valideUser = validateUser(new String(ml.getResponseData()));
                                        
                    //if (valideUser)
                    //    user = parseUser();
                    
                    //Map<String, Object> result = jp.parseJSON((InputStream )new ByteArrayInputStream(ml.getResponseData()), "UTF-8");
                    
                    //user = parseUser();
                }
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(ml);
        
        return valideUser;
    }
    
    
    private boolean validateUser(String json) {
        try {
            JSONParser jsonParser = new JSONParser();
            Map<String,Object> userMap = jsonParser.parseJSON(new CharArrayReader(json.toCharArray()));
            
            String canLogin = userMap.get("canLogin").toString();
            
            return canLogin.equals("true");
            
        } catch (IOException ex) {
            
        }
        return false;
    }
    
    private UserSession(User user) {
        this.user = user;
    }
    
    private UserSession() {}

    public User getUser() {
        return user;
    }

    public void cleanUserSession() {
        instance=null;
    }
    public static UserSession getInstance() {
        if (instance == null)
            instance = new UserSession();
        
        return instance;
    }
    public static UserSession getInstance(User user) {
        if(instance == null) {
            instance = new UserSession(user);
        }
        return instance;
    }

}