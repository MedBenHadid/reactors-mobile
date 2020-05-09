/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import tn.esprit.reactors.nasri.utils.Helpers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.nasri.entities.HebergementOffer;
import tn.esprit.reactors.nasri.entities.HebergementRequest;
import tn.esprit.reactors.nasri.enums.CivilStatus;
import tn.esprit.reactors.nasri.enums.HebergementStatus;
import static tn.esprit.reactors.nasri.services.ServiceHebergementOffer.instance;
import tn.esprit.reactors.nasri.utils.LocalJsonParser;

/**
 *
 * @author nasri
 */
public class ServiceHebergementRequest implements IService<HebergementRequest> 
{
    public static ServiceHebergementRequest instance = null;
    public boolean resultOk;
    ArrayList<HebergementRequest> requests;
    
    private ConnectionRequest request;
    
    private ServiceHebergementRequest() 
    {
         request = new ConnectionRequest();
    }
    public static ServiceHebergementRequest getInstance() 
    {
        if (instance == null) 
        {
            instance = new ServiceHebergementRequest();
        }
        
        return instance;
    }
    
    
    @Override
    public boolean add(HebergementRequest entity) 
    {
        MultipartRequest request = new MultipartRequest();
        
        request.setUrl(Statics.BASE_URL_REFUGEES + "hebergementrequest/new");
        
        request.addArgument("description", entity.getDescription());
        request.addArgument("native-country", entity.getNativeCountry());
        request.addArgument("arrival-date", Helpers.format(entity.getArrivalDate()));
        //request.addArgument("arrival-date", "2000-01-01");
        request.addArgument("passport-number", entity.getPassportNumber());
        request.addArgument("civil-status", String.valueOf(entity.getCivilStatus().ordinal()));
        request.addArgument("children-number", String.valueOf(entity.getChildrenNumber()));
        request.addArgument("region", entity.getRegion());
        request.addArgument("name", entity.getName());
        request.addArgument("telephone", entity.getTelephone());
        request.addArgument("is-anonymous", String.valueOf(entity.isAnonymous() ? 1 : 0));
        
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200; //Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;
    }

    @Override
    public boolean delete(int hebergementRequestId) {
        //localhost:8000/refugees/api/hebergementrequest/delete?id=15
        request.setUrl(Statics.BASE_URL_REFUGEES + "hebergementrequest/delete?id=" + hebergementRequestId);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200; //Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;
    }

    @Override
    public boolean update(HebergementRequest entity) {
        MultipartRequest request = new MultipartRequest();
        
        request.setUrl(Statics.BASE_URL_REFUGEES + "hebergementrequest/edit");
        
        request.addArgument("description", entity.getDescription());
        request.addArgument("native-country", entity.getNativeCountry());
        request.addArgument("arrival-date", Helpers.format(entity.getArrivalDate()));
        request.addArgument("passport-number", entity.getPassportNumber());
        request.addArgument("civil-status", String.valueOf(entity.getState().ordinal()));
        request.addArgument("children-number", String.valueOf(entity.getChildrenNumber()));
        request.addArgument("region", entity.getRegion());
        request.addArgument("name", entity.getName());
        request.addArgument("telephone", entity.getTelephone());
        request.addArgument("is-anonymous", entity.isAnonymous() == true ? "1" : "0");
        request.addArgument("user-id", String.valueOf(entity.getUserId()));
        request.addArgument("id", String.valueOf(entity.getId()));
        
        
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200; //Code HTTP 200 OK
                int x = request.getResponseCode();
                request.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;
    }

    @Override
    public ArrayList<HebergementRequest> getAll() {
        
        request.setUrl(Statics.BASE_URL_REFUGEES + "hebergementrequest/");
        
        request.addResponseListener(new ActionListener<NetworkEvent>() 
        {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                requests = parseHebergementRequests(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return requests;
    }
    
    private ArrayList<HebergementRequest> parseHebergementRequests(String json) 
    {
        ArrayList<HebergementRequest> hRequests = new ArrayList<>();
        
        
        try 
        {
            JSONParser jsonParser = new JSONParser();
            Map<String,Object> offersListJson = jsonParser.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)offersListJson.get("root");
            
            for(Map<String,Object> entry : list)
            {
                hRequests.add(mapToRequest(entry));
            }
            
            
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        return hRequests;
    }
    
    
    private HebergementRequest mapToRequest(Map<String, Object> entry) 
    {
        HebergementRequest hRequest = new HebergementRequest();
        
        hRequest.setNativeCountry(entry.get("nativeCountry").toString());
        hRequest.setArrivalDate(LocalJsonParser.ParseDate(entry.get("arrivalDate").toString()));
        hRequest.setPassportNumber(entry.get("passportNumber").toString());
        hRequest.setCivilStatus(CivilStatus.values()[(int)Float.parseFloat(entry.get("civilStatus").toString())]);
        hRequest.setChildrenNumber((int)Float.parseFloat(entry.get("childrenNumber").toString()));
        hRequest.setName(entry.get("name").toString());
        hRequest.setTelephone(entry.get("telephone").toString());
        hRequest.setCreationDate(LocalJsonParser.ParseDate(entry.get("creationDate").toString()));
        hRequest.setId((int)Float.parseFloat(entry.get("id").toString()));
        hRequest.setDescription(entry.get("description").toString());
        hRequest.setRegion(entry.get("region").toString());
        hRequest.setState(HebergementStatus.values()[(int)Float.parseFloat(entry.get("state").toString())]);
        hRequest.setUserId(LocalJsonParser.ParseUserId(entry.get("user").toString()));
        hRequest.setAnonymous(entry.get("isAnonymous").toString().equals("0"));
        
        return hRequest;
    }
}
