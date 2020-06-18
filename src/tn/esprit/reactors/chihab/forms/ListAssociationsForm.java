/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.l10n.L10NManager;
import com.codename1.location.Location;
import com.codename1.location.LocationListener;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Slider;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.UITimer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.chihab.services.AssociationService;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.controllers.MembershipController;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.malek.services.UserService;

/**
 *
 * @author chihab
 */
public final class ListAssociationsForm extends ReactorsForm{
    private final FontImage BUTTON_RIGHT = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, UIManager.getInstance().getComponentStyle("MultiLine"));
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine"));
    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 2, PEOPLE.getHeight() * 2), false); 
    private final UserService uS = UserService.getInstance();
    private final MembershipController mC = MembershipController.getInstance();
    private final FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_LOCATION_OFF);
    private final InteractionDialog radiusDialog = new InteractionDialog("Radius finder :");
    private final Slider distanceSlider = new Slider();
    private final Button close = new Button("Close");
    private final Button search = new Button("Search");
    private final Container c = new Container();
    private final Location location = new Location();
    private final HashMap<Association,MultiButton> hashAssToUi = new HashMap<>(); 
    
    
    private InfiniteScrollAdapter createInfiniteScroll;
    private boolean isSearchingByGPS = false;
    private int page = 1;
    private String text;
    private boolean isSearching=false;
    private UITimer timer;
    private int gpsStatus;
    public ListAssociationsForm(Form previous) {
        super("List associations",previous);
        gpsStatus = Display.getInstance().getLocationManager().getStatus();
    }
    
   public void showForm(){
        Display.getInstance().getLocationManager().setLocationListener(new LocationListener() {
            @Override
            public void locationUpdated(Location location) {
                System.out.println("Current status update"+Display.getInstance().getLocationManager().getStatus() );
            }

            @Override
            public void providerStateChanged(int newState) {
                System.out.println("Current status provider"+Display.getInstance().getLocationManager().getStatus() );
            }
        });
        this.initFAB();
        this.initToolbar();
        this.show();
        this.fetchList();   
   }
   
    private void initFAB() {
        distanceSlider.setMaxValue(255);
        distanceSlider.setMinValue(2); 
        distanceSlider.setProgress(50); // Set  the starting value
        distanceSlider.setEditable(true);
        distanceSlider.setMaterialIcon(FontImage.MATERIAL_GPS_FIXED);
        radiusDialog.setLayout(new BorderLayout());
        radiusDialog.add(BorderLayout.CENTER, distanceSlider);
        search.setEnabled(false);
        distanceSlider.addActionListener(l->{
            distanceSlider.setText(distanceSlider.getProgress() +"KM");
            if(!search.isEnabled()){
                search.setEnabled(true);
            }
        });
        search.addActionListener(ee-> {
            fab.setMaterialIcon(FontImage.MATERIAL_LOCATION_SEARCHING);
            isSearchingByGPS = true;
            searchAndUpdateUI();
            radiusDialog.dispose();
    
        });            
        close.addActionListener((ee) -> resetFAB());
        c.add(search).add(close);
        radiusDialog.add(BorderLayout.SOUTH,c);
        fab.getStyle().setBgColor(0x99CCCC, true);
        fab.addActionListener(e -> {
            if(radiusDialog.isShowing()){
                resetFAB();
                ToastBar.showInfoMessage("GPS turned off, will no longer tracker distance");
            }
            else{
                fab.setMaterialIcon(FontImage.MATERIAL_LOCATION_ON);
                radiusDialog.showPopupDialog(this.getContentPane()); 
            }
        });
        fab.bindFabToContainer(this.getContentPane());
    }
    
    private void resetFAB(){
        fab.setMaterialIcon(FontImage.MATERIAL_LOCATION_OFF);
        radiusDialog.disposeToTheRight();
        isSearchingByGPS=false;
        searchAndUpdateUI();
    }
    
    private void initToolbar() {
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth()/2, this.getWidth(), 0xffffffff), true);
        URLImage background = URLImage.createToStorage(placeholder, "cover.png","https://pngimage.net/wp-content/uploads/2018/05/charity-images-png-2.png");
        background.fetch();
        Style stitle = this.getToolbar().getTitleComponent().getUnselectedStyle();
        stitle.setBgImage(background);
        stitle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        stitle.setPaddingUnit(Style.BACKGROUND_IMAGE_SCALED_FILL, Style.BACKGROUND_IMAGE_SCALED_FILL, Style.BACKGROUND_IMAGE_SCALED_FILL, Style.BACKGROUND_IMAGE_SCALED_FILL);
        stitle.setPaddingTop(15);
        // SEARCH
        this.getAnimationManager().onTitleScrollAnimation(this.getToolbar().getTitleComponent().createStyleAnimation("Title", 200));
        this.getToolbar().addSearchCommand(e -> {
            if(radiusDialog.isShowing()){
                radiusDialog.dispose();
            }
            this.text = (String)e.getSource();
            if(text == null || text.length() == 0) {
                this.isSearching=false;
            } else {
                this.text = text.toLowerCase();
                this.isSearching=true;
            }
            searchAndUpdateUI();
        }, 4);
    }
    private void searchAndUpdateUI() {
        if(!isSearching&&!isSearchingByGPS){
            resetSearch();
        }else{
            int count = 0;
            for(Component cmp : hashAssToUi.values() ){
                if(cmp.getClass().equals(MultiButton.class)){
                    boolean isNear=true;
                    boolean isSearchCriteria=true;
                    MultiButton mb = (MultiButton)cmp;
                    if(isSearching){
                        String line1 = mb.getTextLine1();
                        String line2 = mb.getTextLine2();
                        isSearchCriteria = line1 != null && line1.toLowerCase().contains(text) || line2 != null && line2.toLowerCase().contains(text);
                    }
                    if(isSearchingByGPS){
                        isNear = (Double.valueOf(mb.getClientProperty("distance").toString())/1000 - distanceSlider.getProgress()) < 0;
                    }
                    boolean found = isSearchCriteria&&isNear;
                    mb.setHidden(!found);
                    mb.setVisible(found);
                    if(found){
                        count++;
                    }
                }
            } 
            if(count==0){
                ToastBar.showInfoMessage("No matches found for specified "+ (isSearching?"text":"")+(isSearchingByGPS? ""+(isSearching?" and":"")+" distance":""));
            }
            this.getContentPane().animateLayout(200);
            this.revalidate();
        }
    }
    
    private void resetSearch(){
        for(Component cmp : this.getContentPane()) {
            if(cmp.getClass().equals(MultiButton.class)){
                cmp.setHidden(false);
                cmp.setVisible(true);
            }
        }
        this.getContentPane().animateLayout(150);
        this.revalidate();
    }
    
    private InfiniteScrollAdapter fetchList(){
       return createInfiniteScroll= InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
            try {
                List<Association> paginatedAssociations = AssociationService.getInstance().readPaginated(page);
                boolean whateverImBored = true;
                int size = paginatedAssociations.size();
                if(size>0){
                    MultiButton[] cmps = new MultiButton[size];
                    for (int iter = 0; iter < cmps.length; iter++) {
                        Association current = paginatedAssociations.get(iter);
                        if(!hashAssToUi.containsKey(current)){
                            cmps[iter]=assToMultiButton(current);
                        }else {
                            InfiniteScrollAdapter.addMoreComponents(this.getContentPane(), new Component[0], false);
                            page--;
                            whateverImBored=false;
                            break;
                        }
                    }
                    if(whateverImBored){
                        InfiniteScrollAdapter.addMoreComponents(this.getContentPane(), cmps, true);
                        page++;
                    }
                }else{
                    // Synchro
                    timer = timer(3000);
                    timer.schedule(20000, true, this);
                }
            } catch (IOException ex) {
                // TODO : Error fetching
                if(timer!=null)
                    timer.cancel();
                System.out.println("Error : "+ex);
                InfiniteScrollAdapter.addMoreComponents(this.getContentPane(), new Component[0], false);
            } 
        }, true); 
    }
    
    private UITimer timer(int millis){
        return UITimer.timer(millis, true, () -> {
            try {
                System.out.println("Triggering background runnable!");
                List<Association> fetched = AssociationService.getInstance().readAll();
                List<Association> toBeDeletedHolder = new ArrayList(this.hashAssToUi.keySet());
                if(toBeDeletedHolder.removeAll(fetched)){
                    for(Association toBeDeleted: toBeDeletedHolder){
                        this.removeComponent(this.hashAssToUi.get(toBeDeleted));
                        this.hashAssToUi.remove(toBeDeleted);
                        this.revalidate();
                    }
                }
                for(Association a : fetched){
                    if (!this.hashAssToUi.containsKey(a)) {
                        addToList(a, false);
                    }else{
                        MultiButton toCheck = this.hashAssToUi.get(a);
                        boolean changed = false;
                        // Searching for ville change
                        if(!toCheck.getClientProperty("ville").equals(a.getVille())){
                            this.hashAssToUi.get(a).setTextLine2(a.getVille());
                            if(isSearching)
                                searchAndUpdateUI();
                            changed = true;
                        }
                        // Checking for distance change !
                        if(!toCheck.getClientProperty("lat").equals(a.getLat())||!toCheck.getClientProperty("lon").equals(a.getLon())){                            
                            changed = true;
                        }
                        // Checking if profile picture changed
                        if(!toCheck.getClientProperty("profile_picture").equals(a.getPhotoAgence())){
                            this.hashAssToUi.get(a).setIcon(URLImage.createToStorage(PLACEHOLDER,"association"+a.getPhotoAgence(),Statics.BASE_URL+"/api/association/image/download/"+a.getPhotoAgence(),URLImage.RESIZE_SCALE));
                            changed = true;
                        }
                        // If changed
                        if(changed){
                            System.out.println("Detected change in :"+a.getNom());
                            this.hashAssToUi.get(a).clearClientProperties();
                            this.hashAssToUi.get(a).putClientProperty("profile_picture", a.getPhotoAgence());
                            this.hashAssToUi.get(a).putClientProperty("ville", a.getVille());
                            location.setLatitude(a.getLat());
                            location.setLongitude(a.getLon());
                            double distanceTo = Display.getInstance().getLocationManager().getCurrentLocation().getDistanceTo(location);
                            this.hashAssToUi.get(a).setTextLine4(L10NManager.getInstance().format(distanceTo/1000,2)+"Km away");
                            this.hashAssToUi.get(a).putClientProperty("distance", distanceTo);
                            this.hashAssToUi.get(a).putClientProperty("lat", a.getLat());
                            this.hashAssToUi.get(a).putClientProperty("lon", a.getLon());
                            if(isSearchingByGPS||isSearching){
                                searchAndUpdateUI();
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }
    
    public void addToList(Association ass, boolean isManager){
        MultiButton[] cmps= new MultiButton[1];
        cmps[0]=assToMultiButton(ass);
        if(isManager)
            cmps[0].getStyle().setBgColor(ColorUtil.GREEN);
        createInfiniteScroll.addMoreComponents(cmps, false);
        this.revalidate();
    }
    private MultiButton assToMultiButton(Association ass){
        MultiButton uiMultiButton = new MultiButton(ass.getNom());
        uiMultiButton.putClientProperty("id",ass.getId());
        uiMultiButton.setTextLine2(ass.getVille());
        uiMultiButton.setEmblem(BUTTON_RIGHT);
        uiMultiButton.setIcon(URLImage.createToStorage(PLACEHOLDER,"association"+ass.getPhotoAgence(),Statics.BASE_URL+"/api/association/image/download/"+ass.getPhotoAgence(),URLImage.RESIZE_SCALE));

        uiMultiButton.putClientProperty("profile_picture", ass.getPhotoAgence());
        uiMultiButton.putClientProperty("ville", ass.getVille());
        uiMultiButton.putClientProperty("lat", ass.getLat());
        uiMultiButton.putClientProperty("lon", ass.getLon());

        uiMultiButton.addActionListener(e -> {
            if(radiusDialog.isShowing())
                radiusDialog.dispose();
            AssociationProfileAssociationForm m = new AssociationProfileAssociationForm(this,ass);
            try {
                m.showForm();
            } catch (IOException ex) {
                Dialog.show("Error fetching data!", "This is probably due to no internet connection...", "OK", null);
            }
        });
        
        if(uS.getUser().getId()==ass.getManager()){
            uiMultiButton.getStyle().setBgColor(ColorUtil.BLUE);
        }
        else if(mC.isMember(ass.getId())){
           switch (mC.getStatusByAss(ass.getId())) {
                case (Membership.ACCEPTED) : uiMultiButton.getStyle().setBgColor(ColorUtil.GREEN);;
                case (Membership.INVITE_PENDING) : uiMultiButton.getStyle().setBgColor(ColorUtil.MAGENTA);;
                case (Membership.REQUEST_PENDING) : uiMultiButton.getStyle().setBgColor(ColorUtil.MAGENTA);;
                case (Membership.DENIED_BY_ASS) : uiMultiButton.getStyle().setBgColor(ColorUtil.YELLOW);;
                case (Membership.DENIED_BY_USER) : uiMultiButton.getStyle().setBgColor(ColorUtil.YELLOW);;
            } 
        }
            

        boolean show=true;
        if(isSearching){
            if(!ass.getNom().toLowerCase().contains(text)||!ass.getVille().toLowerCase().contains(text)){
                show=false;
            }
        }
        try {
            location.setLatitude(ass.getLat());
            location.setLongitude(ass.getLon());
            double distance=0;
            distance = Display.getInstance().getLocationManager().getCurrentLocation().getDistanceTo(location);
            uiMultiButton.setTextLine4(L10NManager.getInstance().format(distance/1000,2)+"Km away");
            uiMultiButton.putClientProperty("distance", distance);
            if(isSearchingByGPS){
                show=show&&distance/1000 - distanceSlider.getProgress() < 0;
            }
        } catch (IOException ex) {}
        uiMultiButton.setVisible(show);
        uiMultiButton.setHidden(!show);
        hashAssToUi.put(ass, uiMultiButton);
        return uiMultiButton;
    }
    
    public static ListAssociationsForm instance=null;
    public static ListAssociationsForm getInstance(Form previous) {
        if (instance == null) {
            instance = new ListAssociationsForm(previous);
        }
        return instance;
    }
}
