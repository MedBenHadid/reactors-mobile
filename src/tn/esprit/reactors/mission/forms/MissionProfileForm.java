/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.mission.forms;

import tn.esprit.reactors.chihab.forms.*;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.Storage;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.util.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.chihab.models.Membership;
import tn.esprit.reactors.malek.models.User;
import tn.esprit.reactors.chihab.services.MembershipService;
import tn.esprit.reactors.malek.services.UserService;
import tn.esprit.reactors.mission.models.Mission;
import tn.esprit.reactors.mission.services.MissionService;

/**
 *
 * @author Chihab
 */
public class MissionProfileForm extends ReactorsForm {

    private final FontImage MORE = FontImage.createMaterial(FontImage.MATERIAL_MORE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PLACE = FontImage.createMaterial(FontImage.MATERIAL_PLACE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage PEOPLE = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE_OUTLINE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage DESC = FontImage.createMaterial(FontImage.MATERIAL_LOCAL_LIBRARY, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage CAMERA = FontImage.createMaterial(FontImage.MATERIAL_PHOTO_CAMERA, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    private final FontImage p = FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, s);
    private final EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 3), false);
    
    private int page = 1;
    private final MissionProfileForm thisHolder;
    private final Mission mission;

    private User user;

    public MissionProfileForm(Form previous, Mission m) throws IOException {
        super(m.getTitleMission(), previous);
        this.user = UserService.getInstance().getUser();
        InputStream is = null;
        this.thisHolder = this;
        this.mission = m;
        try {
            is = Storage.getInstance().createInputStream(String.valueOf(m.getId()));
            EncodedImage i = EncodedImage.create(is, is.available());
            ImageViewer imageViewer = new ImageViewer(i);
            this.addComponent(imageViewer);
        } catch (IOException ex) {
        }

        SpanLabel sp = new SpanLabel(m.getDescription());
        sp.setIcon(DESC);
        this.add(new Label("Descripton :")).add(ComponentGroup.enclose(sp));
        //            SpanLabel Datedeb = new SpanLabel(m.getDateCreation().toString());
        //    this.add(new Label("Date début :")).add(ComponentGroup.enclose(Datedeb));

        MultiButton locationButton = new MultiButton();
        locationButton.setIcon(PLACE);
        locationButton.setTextLine1("show location");

        locationButton.addActionListener(e -> {
            new MapForm(this, m);
        });
        Toolbar.setGlobalToolbar(true);

        Toolbar tb = this.getToolbar();
        tb.addCommandToRightBar("Update ", null, ev -> {
            System.out.println(m.getDateFin());
            new updateMission(this.thisHolder, new TextModeLayout(3, 2), m).show();

        });
        tb.addCommandToLeftBar("Delete ", null, ev -> {
            Dialog.show("Delete", "are you sure to delete this mission !.", "OK", "Cancel");
            MissionService.getInstance().delete(m.getId());
            try {
                new ListMissionForm(previous).show();
            } catch (IOException ex) {
            }

        });

        this.add("Location :").add(ComponentGroup.enclose(locationButton));
        Button phone = new Button("Donate Now", CAMERA);
        //phone.addActionListener(e -> {
        //  Display.getInstance().dial(String.valueOf(m.get()));
        // });=
        this.add("Contact info :").add(ComponentGroup.enclose(phone));

        InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
            final Button showPopup = new Button("Show Popup");

            java.util.List<Membership> data;
            try {
                data = MembershipService.getInstance().fetchMemberships(user.getId(), 0, Membership.ACCEPTED);
                
                MultiButton[] cmps = new MultiButton[data.size()];
                for (int iter = 0; iter < cmps.length; iter++) {
                    Membership currentListing = data.get(iter);
                    if (iter==data.size()) {
                        InfiniteScrollAdapter.addMoreComponents(getContentPane(), new Component[0], false);
                        return;
                    } else {
                        cmps[iter] = memberToButton(currentListing);

                    }
                }
                page++;
                InfiniteScrollAdapter.addMoreComponents(getContentPane(), cmps, data.size()!=cmps.length);
            } catch (IOException ex) {
            }
        }, true);

        this.show();
    }

    private MultiButton memberToButton(Membership currentListing) throws IOException {
        MultiButton cmps = new MultiButton();
        User member = UserService.getInstance().getUser(currentListing.getMemberId());
        int tel = member.getTelephone();
        cmps.setTextLine1(member.getNom() + " " + member.getPrenom());
        cmps.setTextLine2(member.getEmail());
        cmps.setTextLine3(String.valueOf(tel));
        cmps.setIcon(URLImage.createToStorage(placeholder,"user"+member.getImage(),Statics.BASE_URL+"/api/user/profileimage/"+member.getImage(),URLImage.RESIZE_SCALE));
        cmps.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog("Send SMS to " + member.getPrenom());
                TextField popupBody = new TextField("", "Message", 50, TextArea.ANY);
                Button SendBtn = new Button("Send");
                Button CloseBtn = new Button("Close");
                Button CallBtn = new Button("Call");
                CheckBox cb1 = new CheckBox("Send Email");
                CheckBox cb2 = new CheckBox("Send SMS");
                popupBody.setUIID("SendSMS or Email");
                popupBody.setEditable(true);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.EAST, cb1);
                d.add(BorderLayout.WEST, cb2);
                Container flowCenter = FlowLayout.
                        encloseCenter(CloseBtn, SendBtn, CallBtn);
                d.add(BorderLayout.SOUTH, flowCenter);
                d.add(BorderLayout.NORTH, popupBody);
                int h = Display.getInstance().getDisplayHeight();
                CallBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Display.getInstance().dial(String.valueOf(member.getTelephone()));
                    }
                });
                CloseBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        d.dispose();
                    }
                });
                SendBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (popupBody.getText() == "") {
                            System.out.println("error");
                            Dialog.show("Please check your massage!", "", "OK", null);

                        } else {
                            if (cb1.isSelected()) {
                                System.out.println("Email");
                                Message m = new Message(popupBody.getText());
                                String textAttachmentUri = null;
                                String imageAttachmentUri = null;
                                m.getAttachments().put(textAttachmentUri, "text/plain");
                                m.getAttachments().put(imageAttachmentUri, "image/png");
                                Display.getInstance().sendMessage(new String[]{UserService.getInstance().getUser(currentListing.getMemberId()).getEmail()}, "Subject of message", m);

                            }

                            if (cb2.isSelected()) {
                                try {
                                    System.out.println("sms");
                                    String accountSID = "AC4df69979a4d9384e1f0dcb8ea46cc49e";
                                    String authToken = "92122839754e3ffc0eb4cd5ba5e29ad3";
                                    String fromPhone = "+12076067855";

                                    Response<Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                                            queryParam("To", String.valueOf(member.getTelephone())).
                                            queryParam("From", fromPhone).
                                            queryParam("Body", popupBody.getText()).
                                            header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                                            getAsJsonMap();

                                    Display.getInstance().sendSMS("+21621820764", "+hetha");
                                } catch (IOException ex) {
                                }

                            }
                            d.dispose();
                        }
                    }
                });
                d.show(h / 8 * 6, 0, 0, 0);
            }
        });

        return cmps;
    }

}
