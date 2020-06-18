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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Category;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.chihab.services.CategoryService;
import tn.esprit.reactors.mission.models.Mission;
import tn.esprit.reactors.mission.services.MissionService;


public class updateMission extends ReactorsForm{
    
        private List<Category> cats = null;
     //   User u=new User("","","","");

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
    private final FontImage DOMAINES = FontImage.createMaterial(FontImage.MATERIAL_SPA, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOWN_ARROW = FontImage.createMaterial(FontImage.MATERIAL_ARROW_DROP_DOWN_CIRCLE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DOMAINE = FontImage.createMaterial(FontImage.MATERIAL_GOLF_COURSE, UIManager.getInstance().getComponentStyle("MultiLine1"));


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


    /**
    * passed user has to have an id
    * @param User 
    */
    updateMission(Form previous, TextModeLayout tl,Mission m) {
        super("Update misison",previous,tl);
        Title.text(m.getTitleMission());
        description.text(m.getDescription());
        location.text(m.getLocation());
        objectif.text(String.valueOf(m.getObjectif()));
        try {
            this.cats = CategoryService.getInstance().fetchDraChnya();
        } catch (IOException ex) {
            
        }
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
                        m.setPicture(m.getPicture());
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
        this.add(submit);
        this.show();
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.setTitleMission(Title.getText());
                m.setDescription(description.getText());
                m.setLocation(location.getText());
                m.setObjectif(Double.parseDouble(objectif.getText()));
                String datestring=(new SimpleDateFormat("yyyy-MM-dd")).format(dateDeb.getPicker().getDate());
                m.setDateCreation(datestring);
                String dateFinstring=(new SimpleDateFormat("yyyy-MM-dd")).format(dateDeb.getPicker().getDate());
                m.setDateFin(dateFinstring);
                ;
                if (updateMission.this.compare((Date) dateFin.getPicker().getValue(), (Date) dateDeb.getPicker().getValue()) == -1) {
                    Dialog.show("Missing fields", "Please check your date info", "OK", null);
                } else {
                    Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                    try{
                        m.setLon(Display.getInstance().getLocationManager().getCurrentLocation().getLongitude());
                        m.setLat(Display.getInstance().getLocationManager().getCurrentLocation().getLatitude());
                        boolean res = MissionService.getInstance().update(m);
                        if(res==true){
                            Dialog.show("Success update", "Ok !", "OK", null);
                        }
                        
                    }catch(IOException ee){}
                    //GetPictiqye
                    
                    //MalekToDo:      // m.setCretedBy(u.getId());
                    
                    // TODO:  image
                    
                    
                    ip.dispose();
                    ToastBar.showMessage("Success !! ",FontImage.MATERIAL_DONE);
                }
            }
        });

    }
    private static updateMission instance=null;
    public static updateMission getInstance(Form previous, TextModeLayout tl,Mission m) {
        if (instance == null) {
            instance = new updateMission(previous,tl,m);

        }
        return instance;
    }
    public static updateMission getInstance() {
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

//    private Date SimpleDateFormat(String dateCreation) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
