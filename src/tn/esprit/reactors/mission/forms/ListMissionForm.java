/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.mission.forms;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import tn.esprit.reactors.chihab.forms.*;
import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.l10n.L10NManager;
import com.codename1.location.Location;
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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.UITimer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tn.esprit.reactors.mission.services.MissionService;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.controllers.MembershipController;
import tn.esprit.reactors.chihab.models.Association;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.chihab.services.AssociationService;
import tn.esprit.reactors.malek.services.UserService;
import tn.esprit.reactors.mission.models.Mission;

/**
 *
 * @author chihab
 */
public class ListMissionForm extends ReactorsForm {

    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, s);
    private final FontImage BUTTON_RIGHT = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, s);
    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 1, PEOPLE.getHeight() * 1), false);
    private final Slider distanceSlider = new Slider();
    private final InteractionDialog radiusDialog = new InteractionDialog("Radius finder :");
    private final FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_LOCATION_OFF);
    private final Button close = new Button("Close");
    private final UserService uS = UserService.getInstance();
    private final MembershipController mC = MembershipController.getInstance();
    private final Button search = new Button("Search");
    private final Container c = new Container();
    private final Location location = new Location();
    private Form thisHolder;
    private int page = 1;
    Form Home = new Form("Menu");
    private boolean isSearchingByGPS = false;
    private boolean isSearching = false;
    private String text;
    private InfiniteScrollAdapter createInfiniteScroll;
    private UITimer timer;

    private final HashMap<Mission, MultiButton> hashMissionToUi = new HashMap<>();

    AddMission addmisison;

    ListMissionForm(Form previous) throws IOException {
        super("List Mission", previous);

        this.addmisison = new AddMission(this, new TextModeLayout(3, 2));

        Toolbar.setGlobalToolbar(true);

        Toolbar tb = this.getToolbar();
        tb.addCommandToLeftBar("Add Mission", null, ev -> {
            addmisison.show();
        });

        this.initFAB();
        this.initToolbar();
        this.thisHolder = this;

        InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
            try {
                java.util.List<Mission> paginatedMission = MissionService.getInstance().fetchMission("", "", page);
                boolean whateverImBored = true;
                int size = paginatedMission.size();
                if (size > 0) {
                    MultiButton[] cmps = new MultiButton[size];
                    for (int iter = 0; iter < cmps.length; iter++) {
                        Mission current = paginatedMission.get(iter);
                        if (!hashMissionToUi.containsKey(current)) {
                            cmps[iter] = MissionToMultiButton(current);
                        } else {
                            InfiniteScrollAdapter.addMoreComponents(this.getContentPane(), new Component[0], false);
                            page--;
                            whateverImBored = false;
                            break;
                        }
                        cmps[iter].addActionListener(e -> {
                            try {
                                new MissionProfileForm(thisHolder, current).showBack();
                            } catch (IOException ex) {
                            }
                        });

                    }
                    if (whateverImBored) {
                        InfiniteScrollAdapter.addMoreComponents(this.getContentPane(), cmps, true);
                        page++;
                    }
                } else {
                    System.out.println("Triggering background runnable!");
                    // Synchro
                    timer(20000).schedule(20000, true, this);
                }
            } catch (IOException ex) {
                // TODO : Error fetching
                if (timer != null) {
                    timer.cancel();
                }
                System.out.println("Here");
                InfiniteScrollAdapter.addMoreComponents(this.getContentPane(), new Component[0], false);
            }
        }, true);

//        this.thisHolder=this;
//        InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
//            try {
//                java.util.List<Mission> data = MissionService.getInstance().fetchMission("","",page);
//                MultiButton[] cmps = new MultiButton[data.size()];
//                for (int iter = 0; iter < cmps.length; iter++) {
//                    Mission currentListing = data.get(iter);
//                    cmps[iter] = new MultiButton(currentListing.getTitleMission());
//                    cmps[iter].addActionListener(e -> {
//                        try {
//                            new MissionProfileForm(thisHolder,currentListing).showBack();
//                        } catch (IOException ex) {
//                      //      Logger.getLogger(ListMissionForm.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    });
//                    cmps[iter].setEmblem(BUTTON_RIGHT);
//                    cmps[iter].setIcon(URLImage.createToStorage(PLACEHOLDER, String.valueOf(currentListing.getId()), Statics.BASE_URL+"/missionApi/Fetchimage/"+currentListing.getPicture()));
//                }
//                InfiniteScrollAdapter.addMoreComponents(ListMissionForm.this.getContentPane(), cmps, !data.isEmpty());
//                page++;
//            } catch (IOException ex) {
//                InfiniteScrollAdapter.addMoreComponents(ListMissionForm.this.getContentPane(), new Component[0], false);
//            }
//        }, true); 
    }

    private void initFAB() {
        distanceSlider.setMaxValue(10000);
        distanceSlider.setMinValue(2);
        distanceSlider.setProgress(50); // Set  the starting value
        distanceSlider.setEditable(true);
        distanceSlider.setMaterialIcon(FontImage.MATERIAL_GPS_FIXED);

        radiusDialog.setLayout(new BorderLayout());
        radiusDialog.add(BorderLayout.CENTER, distanceSlider);

        search.setEnabled(false);
        distanceSlider.addActionListener(l -> {
            distanceSlider.setText(distanceSlider.getProgress() + "KM");
            if (!search.isEnabled()) {
                search.setEnabled(true);
            }
        });
        search.addActionListener(ee -> {
            fab.setMaterialIcon(FontImage.MATERIAL_LOCATION_SEARCHING);
            isSearchingByGPS = true;
            showDistance();
            searchAndUpdateUI();
            radiusDialog.dispose();

        });
        close.addActionListener((ee) -> resetFAB());

        c.add(search).add(close);
        radiusDialog.add(BorderLayout.SOUTH, c);
        fab.getStyle().setBgColor(0x99CCCC, true);
        fab.addActionListener(e -> {
            if (radiusDialog.isShowing()) {
                resetFAB();
                ToastBar.showInfoMessage("GPS turned off, will no longer tracker distance");
            } else {
                fab.setMaterialIcon(FontImage.MATERIAL_LOCATION_ON);
                radiusDialog.showPopupDialog(this.getContentPane());
            }
        });
        fab.bindFabToContainer(this.getContentPane());
    }

    private void showDistance() {
        for (Component cmp : hashMissionToUi.values()) {
            if (cmp.getClass().equals(MultiButton.class)) {
                try {
                    MultiButton mb = (MultiButton) cmp;
                    location.setLatitude(Double.valueOf(mb.getClientProperty("lat").toString()));
                    location.setLongitude(Double.valueOf(mb.getClientProperty("lon").toString()));
                    double distance = Display.getInstance().getLocationManager().getCurrentLocation().getDistanceTo(location);
                    mb.setTextLine4(L10NManager.getInstance().format(distance / 1000, 2) + "Km away");
                    mb.putClientProperty("distance", distance);
                } catch (IOException ex) {
                    fab.setEnabled(false);
                    ToastBar.showErrorMessage("Error fetching current location");
                    resetFAB();
                }
            }
        }
        this.getContentPane().animateLayout(150);
        this.revalidate();
    }

    private void searchAndUpdateUI() {
        if (!isSearching && !isSearchingByGPS) {
            resetSearch();
        } else {
            int count = 0;
            for (Component cmp : hashMissionToUi.values()) {
                if (cmp.getClass().equals(MultiButton.class)) {
                    boolean isNear = true;
                    boolean isSearchCriteria = true;
                    MultiButton mb = (MultiButton) cmp;
                    if (isSearching) {
                        String line1 = mb.getTextLine1();
                        String line2 = mb.getTextLine2();
                        isSearchCriteria = line1 != null && line1.toLowerCase().contains(text) || line2 != null && line2.toLowerCase().contains(text);
                    }
                    if (isSearchingByGPS) {
                        isNear = (Double.valueOf(mb.getClientProperty("distance").toString()) / 1000 - distanceSlider.getProgress()) < 0;
                    }
                    boolean found = isSearchCriteria && isNear;
                    mb.setHidden(!found);
                    mb.setVisible(found);
                    if (found) {
                        count++;
                    }
                }
            }
            if (count == 0) {
                ToastBar.showInfoMessage("No matches found for specified " + (isSearching ? "text" : "") + (isSearchingByGPS ? "" + (isSearching ? " and" : "") + " distance" : ""));
            }
            this.getContentPane().animateLayout(200);
        }
    }

    private void resetFAB() {
        fab.setMaterialIcon(FontImage.MATERIAL_LOCATION_OFF);
        radiusDialog.disposeToTheRight();
        isSearchingByGPS = false;
    }

    private void resetSearch() {
        for (Component cmp : this.getContentPane()) {
            if (cmp.getClass().equals(MultiButton.class)) {
                cmp.setHidden(false);
                cmp.setVisible(true);
            }
        }
        this.getContentPane().animateLayout(150);
    }

    private void initToolbar() {
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth() / 2, this.getWidth(), 0xffffffff), true);
        URLImage background = URLImage.createToStorage(placeholder, "cover.png", "https://pngimage.net/wp-content/uploads/2018/05/charity-images-png-2.png");
        background.fetch();
        Style stitle = this.getToolbar().getTitleComponent().getUnselectedStyle();
        stitle.setBgImage(background);
        stitle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        stitle.setPaddingUnit(Style.BACKGROUND_IMAGE_SCALED_FILL, Style.BACKGROUND_IMAGE_SCALED_FILL, Style.BACKGROUND_IMAGE_SCALED_FILL, Style.BACKGROUND_IMAGE_SCALED_FILL);
        stitle.setPaddingTop(15);
        this.getAnimationManager().onTitleScrollAnimation(this.getToolbar().getTitleComponent().createStyleAnimation("Title", 200));
        this.getToolbar().addSearchCommand(e -> {
            if (radiusDialog.isShowing()) {
                radiusDialog.dispose();
            }
            this.text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                this.isSearching = false;
            } else {
                this.text = text.toLowerCase();
                this.isSearching = true;
            }
            searchAndUpdateUI();
        }, 4);
    }

    private MultiButton MissionToMultiButton(Mission m) {
        MultiButton uiMultiButton = new MultiButton(m.getTitleMission());
        uiMultiButton.putClientProperty("id", m.getId());
        uiMultiButton.setTextLine2(m.getLocation());
        uiMultiButton.addActionListener((ActionEvent e) -> {
            try {
                if (radiusDialog.isShowing()) {
                    radiusDialog.dispose();
                }
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                MissionProfileForm MP = new MissionProfileForm(this, m);
                ip.dispose();
                MP.show();
            } catch (IOException ex) {
            }

        });
        if (uS.isLoggedIn()) {
            if (mC.isMember(m.getId())) {
                switch (mC.getStatusByAss(m.getId())) {
                    case (Membership.ACCEPTED):
                        uiMultiButton.getStyle().setBgColor(ColorUtil.GREEN);
                        ;
                    case (Membership.INVITE_PENDING):
                        uiMultiButton.getStyle().setBgColor(ColorUtil.MAGENTA);
                        ;
                    case (Membership.REQUEST_PENDING):
                        uiMultiButton.getStyle().setBgColor(ColorUtil.MAGENTA);
                        ;
                    case (Membership.DENIED_BY_ASS):
                        uiMultiButton.getStyle().setBgColor(ColorUtil.YELLOW);
                        ;
                    case (Membership.DENIED_BY_USER):
                        uiMultiButton.getStyle().setBgColor(ColorUtil.YELLOW);
                        ;
                }
            }
        }
        uiMultiButton.setEmblem(BUTTON_RIGHT);
        //    uiMultiButton.setIcon(URLImage.createToStorage(PLACEHOLDER,"association"+String.valueOf(m.getId()),Statics.BASE_URL+"/api/mission/image/download/"+m.getPicture(),URLImage.RESIZE_SCALE));
        uiMultiButton.putClientProperty("lon", m.getLon());
        uiMultiButton.putClientProperty("lat", m.getLat());
        boolean show = true;
        if (isSearching) {
            if (!m.getTitleMission().toLowerCase().contains(text) || !m.getLocation().toLowerCase().contains(text)) {
                show = false;
            }
        }
        if (isSearchingByGPS) {
            location.setLatitude(m.getLat());
            location.setLongitude(m.getLon());
            double distance = 0;
            try {
                distance = Display.getInstance().getLocationManager().getCurrentLocation().getDistanceTo(location);
                uiMultiButton.setTextLine4(L10NManager.getInstance().format(distance / 1000, 2) + "Km away");
                uiMultiButton.putClientProperty("distance", distance);
                show = show && distance / 1000 - distanceSlider.getProgress() < 0;
            } catch (IOException ex) {
            }
        }
        uiMultiButton.setVisible(show);
        uiMultiButton.setHidden(!show);
        hashMissionToUi.put(m, uiMultiButton);
        return uiMultiButton;
    }

    private UITimer timer(int millis) {
        return UITimer.timer(millis, true, () -> {
            try {
                System.out.println("Listening for DB Changes!");
                System.out.println("Hashed map :" + hashMissionToUi);
                List<Mission> fetched = MissionService.getInstance().fetchMission("", "", page);

                List<Mission> toBeDeletedHolder = new ArrayList(this.hashMissionToUi.keySet());
                if (toBeDeletedHolder.removeAll(fetched)) {
                    for (Mission toBeDeleted : toBeDeletedHolder) {
                        this.removeComponent(this.hashMissionToUi.get(toBeDeleted));
                        this.hashMissionToUi.remove(toBeDeleted);
                        this.revalidate();
                    }
                }
                for (Mission a : fetched) {
                    if (!this.hashMissionToUi.containsKey(a)) {
                        addToList(a, false);
                        System.out.println("Added new association" + a.getTitleMission() + " and its MultiButton");
                    } else {
                        this.hashMissionToUi.get(a).setTextLine2(a.getLocation());
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }

    public void addToList(Mission m, boolean isManager) {
        MultiButton[] cmps = new MultiButton[1];
        cmps[0] = MissionToMultiButton(m);
        if (isManager) {
            cmps[0].getStyle().setBgColor(ColorUtil.GREEN);
        }
        createInfiniteScroll.addMoreComponents(cmps, false);
        this.revalidate();
    }

    private static ListMissionForm instance = null;

    public static ListMissionForm getInstance(Form previous) throws IOException {
        if (instance == null) {
            instance = new ListMissionForm(previous);
        }
        return instance;
    }

}
