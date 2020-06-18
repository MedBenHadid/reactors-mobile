/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.forms;

import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import java.util.ArrayList;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.malek.services.*;

import tn.esprit.reactors.issam.models.Don;
import tn.esprit.reactors.issam.services.DonService;

/**
 *
 * @author Issam
 */
public class ListDonForm extends Form {

    private Form _parent;

    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine"));
    private final Button search = new Button("Search");
    private boolean isSearching = false;
    private final InteractionDialog radiusDialog = new InteractionDialog("Radius finder :");
    private final FontImage DELETE = FontImage.createMaterial(FontImage.MATERIAL_DELETE_FOREVER, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage UPDATE = FontImage.createMaterial(FontImage.MATERIAL_UPDATE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final Button close = new Button("Close");
    private String text;

    private final EncodedImage PLACEHOLDER = EncodedImage.createFromImage(PEOPLE.scaled(PEOPLE.getWidth() * 2, PEOPLE.getHeight() * 2), false);

    ArrayList<Don> dons;
    Container donsList;

    public ListDonForm(Form parent) {
        _parent = parent;

        setup();
        style();

    }

    private void setup() {
        init();
        fillDonsList();
        setupToolbar();
    }

    private void init() {
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, "Rechercher", 8);
        this.setTitle("List des offres d'hébérgement");
    }

    private void fillDonsList() {
        dons = DonService.getInstance().getAllDon();
        for (Don don : dons) {
            System.out.println("ok");
            MultiButton mb = new MultiButton();
            mb.setTextLine1(don.getTitle());
            mb.setTextLine2("Description " + don.getDescription());
            mb.setTextLine3("Telephone " + don.getPhone());
            mb.setTextLine4("disponible a " + don.getAddress());
            //donsList.add(mb);
            MultiButton deleteBtn;
            MultiButton updateBtn;
            deleteBtn = new MultiButton("Supprimer");
            deleteBtn.setHeight(1);
            deleteBtn.setWidth(456);
            deleteBtn.setEmblem(DELETE);

            updateBtn = new MultiButton("update");
            updateBtn.getStyle().setMargin(0, 80, 0, 0);

            updateBtn.setEmblem(UPDATE);

            Button detailsBtn = new Button("deialle");

            updateBtn.addActionListener((evt) -> {
                new UpdateDonForm(_parent, don).show();

            });

//            donsList.addAll(deleteBtn, updateBtn);
            mb.setIcon(URLImage.createToStorage(PLACEHOLDER, "don" + don.getImagePath(), Statics.BASE_URL + "/api/don/image/download/" + don.getImagePath(), URLImage.RESIZE_SCALE));
            mb.addActionListener(e -> {
                DonProfileForm m = new DonProfileForm(this, don);
                m.show();
            });

            

            System.out.println(UserService.getInstance().getUser().getId());
            if (UserService.getInstance().getUser().getId() == don.getUserId()) {
            ComponentGroup enclose = ComponentGroup.enclose(mb, updateBtn, deleteBtn);
            enclose.putClientProperty("title", don.getTitle());
            enclose.putClientProperty("ville", don.getAddress());
            this.add(enclose);
            deleteBtn.addActionListener((evt)
                    -> {
                if (Dialog.show("Confirmation", "Vous etes sure ?", "Ok", "Annuler")) {
                    DonService.getInstance().delete(don.getId());
                    enclose.remove();
                }
            });
                
            } else {
                ComponentGroup enclose;
                enclose = ComponentGroup.enclose(mb);
            deleteBtn.addActionListener((evt)
                    -> {
                if (Dialog.show("Confirmation", "Vous etes sure ?", "Ok", "Annuler")) {
                    DonService.getInstance().delete(don.getId());
                    enclose.remove();
                }
            });

            enclose.putClientProperty("title", don.getTitle());
            enclose.putClientProperty("ville", don.getAddress());
            this.add(enclose);
            }


        }

    }

    private void setupToolbar() {
        this.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            _parent.showBack();
        });

        this.getToolbar().addSearchCommand(e -> {
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

    private void resetFAB() {

        searchAndUpdateUI();
    }

    private void searchAndUpdateUI() {
        if (!isSearching) {
            resetSearch();
        } else {
            int count = 0;
            for (Component cmp : this.getContentPane()) {
                if (cmp.getClass().equals(ComponentGroup.class)) {
                    ComponentGroup donGroup = (ComponentGroup) cmp;
                    boolean isSearchCriteria = true;
                    String title = donGroup.getClientProperty("title").toString();
                    String ville = donGroup.getClientProperty("ville").toString();
                    isSearchCriteria = title != null && title.toLowerCase().contains(text) || ville != null && ville.toLowerCase().contains(text);
                    donGroup.setHidden(!isSearchCriteria);
                    donGroup.setVisible(isSearchCriteria);
                    if (isSearchCriteria) {
                        count++;
                    }

                }
            }
            if (count == 0) {
                ToastBar.showInfoMessage("No matches found for specified " + (isSearching ? "text" : ""));
            }
            this.getContentPane().animateLayout(200);
            this.revalidate();
        }
    }

    private void resetSearch() {
        for (Component cmp : this.getContentPane()) {
            if (cmp.getClass().equals(ComponentGroup.class)) {
                cmp.setHidden(false);
                cmp.setVisible(true);

            }
        }
        this.getContentPane().animateLayout(150);
        this.revalidate();
    }

    private void style() {
        this.setLayout(BoxLayout.y());
        this.getContentPane().forceRevalidate();
        this.setScrollableX(false);

    }
}
