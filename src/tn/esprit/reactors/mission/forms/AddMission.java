/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.mission.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.codename1.util.Base64;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.controllers.MembershipController;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.chihab.models.Category;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.chihab.services.AssociationService;
import tn.esprit.reactors.chihab.services.CategoryService;
import tn.esprit.reactors.malek.services.UserService;
import tn.esprit.reactors.mission.models.Mission;
import tn.esprit.reactors.mission.services.MissionService;


public class AddMission extends ReactorsForm{
    
    private java.util.List<Category> cats = null;
    private final ArrayList<String> usersId = new ArrayList();
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, s);
    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 6, PEOPLE.getHeight() * 6), false); 
    private final ImageViewer imageViewr = new ImageViewer(PLACEHOLDER);
    // Icons 
    private final FontImage MISSING = FontImage.createMaterial(FontImage.MATERIAL_HIGHLIGHT_OFF, UIManager.getInstance().getComponentStyle("MultiLine1"));
    // Components
    private final TextComponent Title = new TextComponent().label("Title mission :");
    
    private final TextComponent description = new TextComponent().label("Description").multiline(true);

     private final TextComponent location = new TextComponent().label("location :");
     private final TextComponent objectif = new TextComponent().label("objectif :");
    PickerComponent dateDeb = PickerComponent.createDate(new java.util.Date()).label("Date Début :");
    PickerComponent dateFin = PickerComponent.createDate(new java.util.Date()).label("Date Fin :");
    private final MultiButton domaineDropDownButton = new MultiButton("Pick your domaine...");
      private final MultiButton MemberShipDropDownButton = new MultiButton("Select Members...");

    private final FontImage DOMAINES = FontImage.createMaterial(FontImage.MATERIAL_SPA, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOWN_ARROW = FontImage.createMaterial(FontImage.MATERIAL_ARROW_DROP_DOWN_CIRCLE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOMAINE = FontImage.createMaterial(FontImage.MATERIAL_GOLF_COURSE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final Label labelMember = new Label("Select Members :");

    private final HashMap<Integer,User> users = new HashMap();
    //User & Association connecter
    private User user;
    private Association association;
    // Button
    private final Button imageButton = new Button("Mission image");
    private final Button submit = new Button("Add");

    // Validators
    private final Validator validator = new Validator();

    // Declaratives
    private Mission m=new Mission();
    private Image img = null;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

 

private Container createGenericRendererContainer() {
    Container c = new Container(new BorderLayout());
    c.setUIID("ListRenderer");
    Label name = new Label();
    name.setFocusable(true);
    name.setName("name");
    c.addComponent(BorderLayout.CENTER, name);
    Label surname = new Label();
    surname.setFocusable(true);
    surname.setName("role");
    c.addComponent(BorderLayout.SOUTH, surname);
    CheckBox selected = new CheckBox();
    selected.setName("Selected");
    selected.setFocusable(true);
    c.addComponent(BorderLayout.WEST, selected);
    Label lab = new Label();
    lab.setUIID("ListRenderer");
    lab.setName("obj");
    c.putClientProperty("obj", lab.getName());
    
    
    selected.addActionListener((evt) -> {
        if(usersId.contains(name.getText())){
            usersId.remove(name.getText());
            System.out.println("removed");
        }else {
            usersId.add(name.getText());
            System.out.println("added = "+name.getText());
        }
        System.out.println("Tab;"+usersId);
    });
    return c;
}

private Object[] createGenericListCellRendererModelData(Association association) throws IOException {
    java.util.List<Membership> readMemberships = MembershipController.getInstance().readMemberships(association.getId(), 1, Membership.ACCEPTED);
    Hashtable[] data = new Hashtable[readMemberships.size()];
    int i = 0;
    for(Membership m : readMemberships){
        data[i] = new Hashtable();
        data[i].put("name", UserService.getInstance().getUser(m.getMemberId()).getNom());

        data[i].put("id", m.getMemberId());
        data[i].put("role", m.getFonction());
        users.put(m.getMemberId(), UserService.getInstance().getUser(m.getMemberId()));
        System.out.println("Tel : "+users.get(m.getMemberId()).getTelephone());
        data[i].put("Selected", Boolean.FALSE);
        i++;
    }
    return data;
}
    

    /**
    * passed user has to have an id
    * @param User 
    */
    AddMission(Form previous, TextModeLayout tl) throws IOException {
        super("Add misison",previous,tl);
        this.user=UserService.getInstance().getUser();
       this.association=AssociationService.getInstance().findAssByManager(user.getId());
            com.codename1.ui.List list = new List(createGenericListCellRendererModelData(association));
            list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer()));
         //   Object lists = list.getSelectedItem();
          
        
      ///  System.out.println(lists);
       
        this.validator.addConstraint(Title, new RegexConstraint("^[\\w\\s]{3,30}$",""));
        this.validator.addConstraint(location, new RegexConstraint("^[\\w\\s]{3,30}$",""));
        //     this.validator.addConstraint(objectif, new RegexConstraint());
        this.validator.addConstraint(description, new RegexConstraint("^[\\d\\w\\s]{3,255}$",""));
     //  this.validator.addSubmitButtons(submit);
        // TODO : TOAST BAR
       try {
            this.cats = CategoryService.getInstance().fetchDraChnya();
        } catch (IOException ex) {}   
        
       
       
       
           domaineDropDownButton.setIcon(DOMAINES);
        domaineDropDownButton.setEmblem(DOWN_ARROW);
        domaineDropDownButton.addActionListener(e -> {
                Dialog d = new Dialog();
                d.setLayout(BoxLayout.y());
                d.getContentPane().setScrollableY(true);
                for(Category cat : cats) {
                    MultiButton mb = new MultiButton(cat.getNom());
                    mb.setIcon(DOMAINE);
                    d.add(mb);
                    mb.addActionListener(ee -> {
                        domaineDropDownButton.setTextLine1(mb.getTextLine1());
                        domaineDropDownButton.setTextLine2(mb.getTextLine2());
                        domaineDropDownButton.setIcon(mb.getIcon());
                        d.dispose();
                        domaineDropDownButton.revalidate();
                        m.setDomaine(cat.getId());
                    });
                }
                d.showPopupDialog(domaineDropDownButton);
        });
        this.imageButton.addActionListener(e -> {
            Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
                if (ev != null && ev.getSource() != null) {
                    String filePath = (String) ev.getSource();
                    int fileNameIndex = filePath.lastIndexOf("/") + 1;
                    m.setPicture(filePath.substring(fileNameIndex));
                    try {
                        this.setImg(Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath)));
                        imageViewr.setImage(img);
                    } catch (IOException e1) {
                    }
                }
            }, Display.GALLERY_IMAGE);
        });

        
        this.imageButton.setMaterialIcon(FontImage.MATERIAL_ADD);

        this.add(tl.createConstraint().horizontalSpan(2), Title);
        this.add(tl.createConstraint().horizontalSpan(10), description);
        this.add(tl.createConstraint().horizontalSpan(2),imageButton);
        this.add(tl.createConstraint().widthPercentage(30),imageViewr);

        this.add(tl.createConstraint().widthPercentage(30), location);

        this.add(tl.createConstraint().widthPercentage(30),objectif);
        this.add(tl.createConstraint().horizontalSpan(10),domaineDropDownButton);

        this.add(tl.createConstraint().horizontalSpan(2),dateDeb);
        this.add(tl.createConstraint().horizontalSpan(2),dateFin);
        this.add(labelMember);
        this.addComponent(list);

        this.add(submit);
        this.show();
       // this.setEditOnShow(nom.getField()); 
        
        
            
               submit.addActionListener(new ActionListener() {
                   
                   

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(list.getSelectedIndex());
                int length = usersId.size();
                for (int i = 0; i < length; i++) {
                    
                    
        try {
            
          
            String accountSID = "AC4df69979a4d9384e1f0dcb8ea46cc49e";
            String authToken = "92122839754e3ffc0eb4cd5ba5e29ad3";
            String fromPhone = "+12076067855";
            
            
            Response<Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                    queryParam("To",String.valueOf(UserService.getInstance().getUser(Integer.valueOf(usersId.get(i))).getTelephone())).
                    queryParam("From", fromPhone).
                    queryParam("Body", "Test SMS !").
                    header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                    getAsJsonMap();

            Display.getInstance().sendSMS("+21621820764", "+hetha");
   
        } catch (IOException ex) {
                }

                }
                              
String accountSID = "AC4df69979a4d9384e1f0dcb8ea46cc49e";
String authToken = "92122839754e3ffc0eb4cd5ba5e29ad3";
String fromPhone = "+12076067855";
        

Response<Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
        queryParam("To", "+21621820764").
        queryParam("From", fromPhone).
        queryParam("Body", "Test SMS !").
        header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
        getAsJsonMap();
        try {
                    Display.getInstance().sendSMS("+21621820764", "+hetha");
                } catch (IOException ex) {
                }


                m.setTitleMission(Title.getText());
                m.setDescription(description.getText());
                m.setLocation(location.getText());
                m.setObjectif(Double.parseDouble(objectif.getText()));
                String datestring=(new SimpleDateFormat("yyyy-MM-dd")).format(dateDeb.getPicker().getDate());
                System.out.println(datestring);
                m.setDateCreation(datestring);
                String dateFinstring=(new SimpleDateFormat("yyyy-MM-dd")).format(dateDeb.getPicker().getDate());
                System.out.println(dateFinstring);
                m.setDateFin(dateFinstring);
                ;
                if (AddMission.this.compare((Date) dateFin.getPicker().getValue(), (Date) dateDeb.getPicker().getValue()) == -1) {
                    Dialog.show("Missing fields", "Please check your date info", "OK", null);
                } else {
                    Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                    try{
                        m.setLon(Display.getInstance().getLocationManager().getCurrentLocation().getLongitude());
                        m.setLat(Display.getInstance().getLocationManager().getCurrentLocation().getLatitude());
                        boolean res = MissionService.getInstance().AddMission(m);
                        if(res==true){
                    Dialog.show("Success", "Ok !", "OK", null);
                        }
                        
                    }catch(IOException ee){}
                    
                    
                    //MalekToDo:      // m.setCretedBy(u.getId());
                    
                    // TODO:  image
                    
                    
                    ip.dispose();
                    ToastBar.showMessage("Success !! ",FontImage.MATERIAL_DONE);
                }
            }
        });

    }
    private static AddMission instance=null;
    public static AddMission getInstance(Form previous, TextModeLayout tl) throws IOException {
        if (instance == null) {
            instance = new AddMission(previous,tl);
        }
        return instance;
    }
    public static AddMission getInstance() {
        return instance;
    }
    public void nullify(){
        this.instance=null;
    }

       public static int compare(Date d1, Date d2) {
        if (d1 == null) return d2 == null ? 0 : -1;
        if (d2 == null) return 1;
        if (d1.getTime() < d2.getTime()) {
            return -1;
        } else if (d1.getTime() > d2.getTime()) {
            return 1;
        }
        return 0;
    }
}
