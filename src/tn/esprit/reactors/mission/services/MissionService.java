/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.mission.services;

import tn.esprit.reactors.mission.services.*;
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
import java.util.Date;
import tn.esprit.reactors.chihab.models.Category;
import tn.esprit.reactors.chihab.models.User;
import tn.esprit.reactors.chihab.services.CategoryService;
import tn.esprit.reactors.mission.models.Mission;

/**
 *
 * @author Chihab
 */
public class MissionService {
    private ArrayList<Mission> missions;
    
    public static MissionService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private MissionService() {
         req = new ConnectionRequest();
    }

    public static MissionService getInstance() {
        if (instance == null) {
            instance = new MissionService();
        }
        return instance;
    }

    public Mission parseMission(Map<String,Object> obj){  
        Mission m = new Mission( (int)(Double.parseDouble(obj.get("id").toString())),obj.get("titleMission").toString() );
      //  m.setId((int)obj.get("id"));
      //  m.setTitleMission(obj.get("TitleMission").toString());
      //CategoryService.getInstance().parseCategory(obj);
    //  m.setDomaine((Category)obj.get("domaine"));
        m.setDescription(obj.get("description").toString());
        m.setLocation(obj.get("location").toString());
        m.setUps(((int)(Double.parseDouble(obj.get("ups").toString()))));
        m.setObjectif((Double)obj.get("objectif"));
        m.setSumCollected((Double)obj.get("sumCollected"));
        m.setCretedBy((User)obj.get("CretedBy"));

        m.setDateCreation((Date)obj.get("DateCreation"));
        m.setDateFin((Date)obj.get("DateFin"));

     
        return m;
    }

    public java.util.List<Mission> fetchMission(String title, String location, int page) throws IOException {
        req.setPost(false);
        String optional = (!title.isEmpty()?"/"+title :"")+(!location.isEmpty()?"/"+location:"");
        String association ="73";
        req.setUrl(Statics.BASE_URL+"/missionApi/showAll/"+page+optional);
                       req.addArgument("association", association);
 
        page++;
        NetworkManager.getInstance().addToQueueAndWait(req);
        missions=new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> MissionListJson = j.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)MissionListJson.get("root");
        System.out.print(list);
        for(Map<String,Object> obj : list){
           missions.add(parseMission(obj));
        }
                System.out.println(missions);

        return missions;
    }
    
  
}
