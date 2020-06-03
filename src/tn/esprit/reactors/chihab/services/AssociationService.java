package tn.esprit.reactors.chihab.services;

import tn.esprit.reactors.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.google.gson.Gson;
import java.io.IOException;
import tn.esprit.reactors.chihab.models.Association;

/**
 *
 * @author Chihab
 */
public class AssociationService {
        private final JSONParser j;
        private final Gson g = new Gson();
        private static AssociationService instance=null;
        private ConnectionRequest req;
        private AssociationService() {
             this.req = new ConnectionRequest();
             this.j = new JSONParser();
        }

        public static AssociationService getInstance() {
            if (instance == null) {
                instance = new AssociationService();
            }
            return instance;
        }

        public Association parseAssociation(Map<String,Object> obj){  
            Association a = new Association( (int)(Double.parseDouble(obj.get("id").toString())),obj.get("nom").toString() );
            a.setApprouved(obj.get("approuved").toString().equals("true"));
            a.setDescription(obj.get("description").toString());
            a.setHoraireTravail(obj.get("horaireTravail").toString());
            a.setPhotoAgence(obj.get("photo").toString());
            a.setPieceJustificatif(obj.get("pieceJustificatif").toString());
            a.setRue(obj.get("rue").toString());
            a.setVille(obj.get("ville").toString());
            a.setLon(Double.valueOf(obj.get("longitude").toString()));
            a.setLat(Double.valueOf(obj.get("latitude").toString()));
            a.setCodePostal(Double.valueOf(obj.get("codePostal").toString()).intValue());
            a.setTelephone(Double.valueOf(obj.get("telephone").toString()).intValue());
            a.setDomaine((int)(Double.parseDouble(((Map<String,Object>)obj.get("domaine")).get("id").toString())));
            a.setManager((int)(Double.parseDouble(((Map<String,Object>)obj.get("manager")).get("id").toString())));
            return a;
        }

        public java.util.List<Association> parseAssociations(List<Map<String,Object>> list){
            ArrayList<Association> associations = new ArrayList<>();
            for(Map<String,Object> obj : list){
               associations.add(parseAssociation(obj));
            }
            return associations;
        }

        public java.util.List<Association> readAll() throws IOException {
            req.setUrl(Statics.BASE_URL+"/api/associations");
            req.setPost(false);
            NetworkManager.getInstance().addToQueueAndWait(req);
            Map<String,Object> associationListJson = j.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray())); 
            return parseAssociations((List<Map<String,Object>>)associationListJson.get("root"));
        }

        public java.util.List<Association> readPaginated(int page) throws IOException {
            req.setUrl(Statics.BASE_URL+"/api/associations/"+page);
            req.setPost(false);
            NetworkManager.getInstance().addToQueueAndWait(req);
            Map<String,Object> associationListJson = j.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
            return parseAssociations((List<Map<String,Object>>)associationListJson.get("root"));
        }
    
    

        public Response<Map> addAssociation(Association a) {
            return Rest.post(Statics.BASE_URL + "/api/association").contentType("application/json").body(g.toJson(a)).acceptJson().getAsJsonMap();
        }

        public Association findAssByManager(int managerId){
            return parseAssociation((Map<String,Object>) Rest.get(Statics.BASE_URL +"/api/associations/findoneby/manager/"+managerId).acceptJson().getAsJsonMap().getResponseData());

        }

        public Association getAssociation(int assId) {
            return parseAssociation((Map<String,Object>)Rest.get(Statics.BASE_URL + "/api/association/"+assId).acceptJson().getAsJsonMap().getResponseData());
        }
        public Response<Map> update(Association a){
            return Rest.patch(Statics.BASE_URL + "/api/association/update").contentType("application/json").body(g.toJson(a)).acceptJson().getAsJsonMap();
        }

        public MultipartRequest uploadImage (String filePath, String fileName) throws IOException{
            MultipartRequest cr = new MultipartRequest();
            cr.setUrl(Statics.BASE_URL+"/api/association/upload/image");
            cr.setPost(true);
            try {
                cr.addData("file", filePath, "image/jpeg;image/jpeg;image/tiff;image/gif");
            } catch (IOException ex) {System.out.println(ex.getMessage());}
            cr.addArgument("filename", fileName);
            return cr;
        }
    
        public MultipartRequest uploadFile (String filePath, String fileName) throws IOException{
        MultipartRequest cr = new MultipartRequest();
        cr.setUrl(Statics.BASE_URL+"/api/association/upload/file");
        cr.setPost(true);
        try {
            cr.addData("file", filePath, "image/jpeg;image/jpeg;image/tiff;image/gif");
        } catch (IOException ex) {System.out.println(ex.getMessage());}
        cr.addArgument("filename", fileName);
        return cr;
    }
}