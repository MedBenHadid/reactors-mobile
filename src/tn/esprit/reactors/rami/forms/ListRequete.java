/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.rami.forms;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.rami.models.Requete;
import tn.esprit.reactors.rami.models.Rponse;
import tn.esprit.reactors.rami.services.RequeteService;

/**
 *
 * @author LENOVO
 */
public class ListRequete extends ReactorsForm {

    private final FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_LOCATION_OFF);
    private final FontImage SUPPORT_ICON = FontImage.createMaterial(FontImage.MATERIAL_VIEW_AGENDA, UIManager.getInstance().getComponentStyle("MultiLine"));
    private final FontImage UPDATE_ICON = FontImage.createMaterial(FontImage.MATERIAL_UPDATE, UIManager.getInstance().getComponentStyle("MultiLine"));
    private final FontImage DELETE_ICON = FontImage.createMaterial(FontImage.MATERIAL_DELETE, UIManager.getInstance().getComponentStyle("MultiLine"));
    private final TextComponent titleTextComponent = new TextComponent().label("Sujet :");
    private final TextComponent subjecTextComponent = new TextComponent().label("Description :");
    private final Picker casePicker = new Picker();
    private final MultiButton addMultiButton = new MultiButton("Ajouter ");
    private final Dialog addDialog = new Dialog("Ajouter une requete");

    public ListRequete(Form previous) {
        super("List Mes Requetes", previous);

        this.casePicker.setType(Display.PICKER_TYPE_STRINGS);
        this.fab.getStyle().setBgColor(0x99CCCC, true);
        this.fab.setMaterialIcon(FontImage.MATERIAL_ADD);
        this.casePicker.setStrings("Besoin de renseignement sur le site", "Je veux reporter un spam", "J ai des problem technique");
        this.casePicker.setSelectedString("1");
        Validator validator = new Validator();
        validator.addConstraint(titleTextComponent, new LengthConstraint(12, "Veuillez introduire un titre"));
        validator.addConstraint(subjecTextComponent, new LengthConstraint(12, "Veuillez introduire un sujet"));
        validator.addSubmitButtons(addMultiButton);
        addMultiButton.addActionListener(e -> {
            Requete r = new Requete(titleTextComponent.getText(), subjecTextComponent.getText(), Integer.valueOf(casePicker.getValue().toString()));
            RequeteService.getInstance().addRequete(r);

            addDialog.dispose();
        });
        addDialog.add(ComponentGroup.enclose(titleTextComponent, subjecTextComponent, casePicker, addMultiButton));

        this.fab.addActionListener(e -> {
            addDialog.show();
        });
        this.fab.bindFabToContainer(this.getContentPane());
        MultiButton maillerbutton = new MultiButton("envoyer nous un mail");
        maillerbutton.addActionListener((evt) -> {
            Dialog mailerDialog = new Dialog();
            TextComponent subject = new TextComponent().label("subject");
            MultiButton mbb = new MultiButton("Envoyer");
            mbb.addActionListener((j) -> {

                Message m = new Message("Vous avez recu une nouvelle reclamation de sujet :"+ subject.getText());
                Display.getInstance().sendMessage(new String[]{"ramy.trunks@gmail.com"}, "Subject of message", m);
                mailerDialog.dispose();
            });
            mailerDialog.add(ComponentGroup.enclose(subject,mbb));
            
            mailerDialog.show();
        });
        this.add(new SpanLabel("si voous n'etes pas satisfais avec notre support"));
        this.add(maillerbutton);
        for (Requete r : RequeteService.getInstance().getAllRequests()) {
            MultiButton mb = new MultiButton(r.getsujet());
            mb.setTextLine2(r.getdescription());
            mb.setIcon(SUPPORT_ICON);
            MultiButton update = new MultiButton("Modifier");
            update.setIcon(UPDATE_ICON);
            MultiButton delete = new MultiButton("Supprimer");
            delete.setIcon(DELETE_ICON);
            update.addActionListener(e -> {
                Dialog updateDialog = new Dialog();
                Validator updateValidator = new Validator();
                TextComponent updatetitleTextComponent = new TextComponent().label("Sujet :");
                TextComponent updatesubjecTextComponent = new TextComponent().label("Description :");
                updatetitleTextComponent.text(r.getsujet());
                updatesubjecTextComponent.text(r.getsujet());
                updateValidator.addConstraint(updatetitleTextComponent, new LengthConstraint(12, "Veuillez introduire un titre"));
                updateValidator.addConstraint(updatesubjecTextComponent, new LengthConstraint(12, "Veuillez introduire un sujet"));
                updateValidator.addSubmitButtons(update);
                Button submtButton = new Button("Modifier");
                updateDialog.add(ComponentGroup.enclose(updatetitleTextComponent, updatesubjecTextComponent, submtButton));

                submtButton.addActionListener((evt) -> {
                    r.setsujet(updatetitleTextComponent.getText());
                    r.setdescription(updatesubjecTextComponent.getText());
                    System.out.println("Appel service update");
                    RequeteService.getInstance().updateRequete(r);
                    titleTextComponent.text(r.getsujet());
                    subjecTextComponent.text(r.getsujet());
                    updateDialog.dispose();
                });
                // ken thib tupdati fi dialog Dialog updateDialog = new Dialog();

                updateDialog.show();
            });
            
            MultiButton rp = new MultiButton("Reponse ");
            //Rponse ree = RequeteService.getInstance().getResponse(r.getid());
            //System.out.print("haha");
            rp.addActionListener((e) -> {
                Dialog uDialog = new Dialog();
                Validator updateValidator = new Validator();
                //RequeteService requeteService = RequeteService.getInstance().getReponseById(1);
                Rponse rep = RequeteService.getInstance().getResponse(r.getid());
                Label l1 = new Label(rep.getSujet());
                Label l2 = new Label(rep.getRep());
                Button closeButton = new Button("Fermer");
                uDialog.add(ComponentGroup.enclose(l1, l2,closeButton));
                System.out.println("i'm clicked");
                
                closeButton.addActionListener((evt) ->{
                    uDialog.dispose();
                
                });
                uDialog.show();
                
            });
            
            ComponentGroup enclose = ComponentGroup.enclose(mb, update, delete,rp);
            delete.addActionListener(e -> {
                // ken thib tdelti
                RequeteService.getInstance().deleteRequete(r);
                enclose.remove();

                ToastBar.showInfoMessage("Succesfully delete");
            });
            this.add(enclose);
        }
    }

}
