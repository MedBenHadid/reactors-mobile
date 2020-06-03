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
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.events.ActionListener;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.mission.models.Mission;

/**
 *
 * @author Chihab
 */
public class MissionService {
        public boolean resultOk;

    private ArrayList<Mission> missions;
      private final Gson g = new Gson();
      private final JSONParser j;

    public static MissionService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private MissionService() {
         req = new ConnectionRequest();
                  this.j = new JSONParser();

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

      //  m.setDateCreation(((Date) obj.get("DateCreation")).toString());
     //   m.setDateFin(((Date) obj.get("DateFin")).toString());

     
        return m;
    }

    public java.util.List<Mission> fetchMission(String title, String location, int page) throws IOException {
        req.setPost(false);
        String optional = (!title.isEmpty()?"/"+title :"")+(!location.isEmpty()?"/"+location:"");
        String association ="3";
        req.setUrl(Statics.BASE_URL+"/missionApi/showAll/"+page+optional);
        //TODO: User Association ID 
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
    
        public boolean AddMission(Mission m) {
            System.out.println(m);
        MultipartRequest request = new MultipartRequest();
        request.setPost(true);
        req.setUrl(Statics.BASE_URL+"/missionApi/addMission");
                       req.addArgument("description", m.getDescription());
                       req.addArgument("domaine", String.valueOf(m.getDomaine()));
                       req.addArgument("lat", String.valueOf(m.getLat()));
                       req.addArgument("lon", String.valueOf(m.getLon()));
                       req.addArgument("picture", m.getPicture());
                       req.addArgument("TitleMission", m.getTitleMission());
                       req.addArgument("description", m.getDescription());
                       req.addArgument("objectif", String.valueOf(m.getObjectif()));
                       req.addArgument("location", String.valueOf(m.getLocation()));


        NetworkManager.getInstance().addToQueueAndWait(req);
         req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 resultOk = req.getResponseCode() == 200;
                int x = req.getResponseCode();
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
        public boolean update (Mission m){
            System.out.println(m);
        MultipartRequest request = new MultipartRequest();
        request.setPost(true);
        req.setUrl(Statics.BASE_URL+"/missionApi/editMission");
                       req.addArgument("id", String.valueOf(m.getId()));
                       req.addArgument("description", m.getDescription());
                       req.addArgument("domaine", String.valueOf(m.getDomaine()));
                       req.addArgument("lat", String.valueOf(m.getLat()));
                       req.addArgument("lon", String.valueOf(m.getLon()));
                       req.addArgument("picture", m.getPicture());
                       req.addArgument("TitleMission", m.getTitleMission());
                       req.addArgument("description", m.getDescription());
                       req.addArgument("objectif", String.valueOf(m.getObjectif()));
                       req.addArgument("location", String.valueOf(m.getLocation()));


        NetworkManager.getInstance().addToQueueAndWait(req);
         req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 resultOk = req.getResponseCode() == 200;
                int x = req.getResponseCode();
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;      
        }
        
//         public java.util.List<Mission> readPaginated(int page) throws IOException {
//        req.setUrl(Statics.BASE_URL+"/missionApi/showAll/"+page);
//        req.setPost(false);
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        Map<String,Object> MissionListJson = j.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
//        return parseMissions((List<Map<String,Object>>)MissionListJson.get("root"));
//    }
//    
//          public java.util.List<Mission> parseMissions(List<Map<String,Object>> list){
//        ArrayList<Mission> associations = new ArrayList<>();
//        for(Map<String,Object> obj : list){
//           associations.add(parseMission(obj));
//        }
//        return associations;
//    }
          public Response<Map> delete (int missionId){
                return Rest.delete(Statics.BASE_URL + "/missionApi/deleteMission?id="+missionId).contentType("application/json").body(g.toJson(missionId)).acceptJson().getAsJsonMap();
        }
}
