/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import com.codename1.io.File;
import com.codename1.components.FileEncodedImage;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.ui.Image;
import com.codename1.ui.util.ImageIO;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.nasri.entities.HebergementOffer;
import tn.esprit.reactors.nasri.enums.HebergementStatus;
import tn.esprit.reactors.nasri.utils.LocalJsonParser;

/**
 *
 * @author nasri
 */
public class ServiceHebergementOffer implements IService<HebergementOffer> 
{
    
    public static ServiceHebergementOffer instance = null;
    public boolean resultOk;
    ArrayList<HebergementOffer> offers;
    
    
    private ConnectionRequest request;
    
    private ServiceHebergementOffer() 
    {
         request = new ConnectionRequest();
    }
    public static ServiceHebergementOffer getInstance() 
    {
        if (instance == null) 
        {
            instance = new ServiceHebergementOffer();
        }
        
        return instance;
    }
    
    
    @Override
    public boolean delete(int hebergementOfferId)
    {
        //localhost:8000/refugees/api/hebergement/delete?id=39
        request.setUrl(Statics.BASE_URL_REFUGEES + "hebergement/delete?id=" + hebergementOfferId);
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
    public boolean update(HebergementOffer entity)
    {
        MultipartRequest request = new MultipartRequest();
        
        request.setUrl(Statics.BASE_URL_REFUGEES + "hebergement/edit");
        
        request.addArgument("id", String.valueOf(entity.getId()));
        request.addArgument("description", entity.getDescription());
        request.addArgument("governorat", entity.getGovernorat());
        request.addArgument("nbr-rooms", String.valueOf(entity.getNumberRooms()));
        request.addArgument("duration", String.valueOf(entity.getDuration()));
        request.addArgument("telephone", entity.getTelephone());
        request.addArgument("user-id", String.valueOf(entity.getUserId()));
        
        try
        {
            request.addData("image", 
                    FileSystemStorage.getInstance().getAppHomePath() + entity.getImage().getName(), 
                    "image/jpeg;image/jpeg;image/tiff;image/gif");
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        
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
    public boolean add(HebergementOffer entity)
    {
        MultipartRequest request = new MultipartRequest();
        
        request.setUrl(Statics.BASE_URL_REFUGEES + "hebergement/new");
        
        request.addArgument("description", entity.getDescription());
        request.addArgument("governorat", entity.getGovernorat());
        request.addArgument("nbr-rooms", String.valueOf(entity.getNumberRooms()));
        request.addArgument("duration", String.valueOf(entity.getDuration()));
        request.addArgument("telephone", entity.getTelephone());
        request.addArgument("user-id", String.valueOf(entity.getUserId()));
        try
        {
            request.addData("image", 
                    FileSystemStorage.getInstance().getAppHomePath() + entity.getImage().getName(), 
                    "image/jpeg;image/jpeg;image/tiff;image/gif");
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        
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
    public ArrayList<HebergementOffer> getAll() 
    {
        request.setUrl(Statics.BASE_URL_REFUGEES + "hebergement/");
        
        request.addResponseListener(new ActionListener<NetworkEvent>() 
        {
            @Override
            public void actionPerformed(NetworkEvent evt) 
            {
                offers = parseHebergementOffers(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return offers;
    }
    
    
    private ArrayList<HebergementOffer> parseHebergementOffers(String json) 
    {
        ArrayList<HebergementOffer> hOffers = new ArrayList<>();
        
        
        
        
        try 
        {
            JSONParser jsonParser = new JSONParser();
            Map<String,Object> offersListJson = jsonParser.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)offersListJson.get("root");
            
            for(Map<String,Object> entry : list)
            {
                hOffers.add(mapToOffer(entry));
            }
            
            
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        return hOffers;
    }
    
    
    private HebergementOffer mapToOffer(Map<String, Object> entry) 
    {
        HebergementOffer hOffer = new HebergementOffer();
        
        
        hOffer.setId((int)Float.parseFloat(entry.get("id").toString()));
        hOffer.setDescription(entry.get("description").toString());
        hOffer.setGovernorat(entry.get("governorat").toString());
        hOffer.setNumberRooms((int)Float.parseFloat(entry.get("nbrRooms").toString()));
        hOffer.setDuration((int)Float.parseFloat(entry.get("duration").toString()));
        hOffer.setCreationDate(LocalJsonParser.ParseDate(entry.get("creationDate").toString()));
        hOffer.setState(HebergementStatus.values()[(int)Float.parseFloat(entry.get("state").toString())]);
        hOffer.setTelephone(entry.get("telephone").toString());
        hOffer.setImage(new File(entry.get("image").toString()));
        hOffer.setUserId(LocalJsonParser.ParseUserId(entry.get("user").toString()));
        
        return hOffer;
    }
}
