/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.forms;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Issam
 */
public class HomeForm extends Form {
    // Form ListDemandeForm = new ListDemandeForm(this);

    ListDonForm ListDonForm = new ListDonForm(this);
    Form AddDonForm = new AddDonForm(this);
    Form AddDemandeForm = new AddDemandeForm(this);
    Button addDonBtn;
    Button addDemandeBtn;
    Button listDonBtn;
    Button listDemandeBtn;

    public HomeForm() {
        Toolbar.setGlobalToolbar(true);
        this.setTitle("donnation space");
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        setupStyle();
        setupToolbar();

        Container buttonsContainer = createButtonsContainer();
        setupButtonsContainerActions();
        this.add(buttonsContainer);

        SpanLabel description = createDescription();

    }

    private void setupToolbar() {
        this.getToolbar().addMaterialCommandToSideMenu("Accueil", FontImage.MATERIAL_HOME, (evt) -> {
            this.show();
        });
        this.getToolbar().addMaterialCommandToSideMenu("Ajouter une donnation", FontImage.MATERIAL_ADD, (evt) -> {
            AddDonForm.show();
        });

        this.getToolbar().addMaterialCommandToSideMenu("demander de l'aide", FontImage.MATERIAL_ADD, (evt) -> {
            AddDemandeForm.show();
        });
        this.getToolbar().addMaterialCommandToSideMenu("Liste complete des dons", FontImage.MATERIAL_INFO, (evt) -> {
            ListDonForm.show();
        });

        /**
         * this.getToolbar().addMaterialCommandToSideMenu("Liste complete des
         * demnandes", FontImage.MATERIAL_INFO, (evt) -> {
         * ListDemandeForm.show(); });
         */
    }

    private void setupStyle() {
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        this.getStyle().setFont(font);
    }

    private Container createButtonsContainer() {
        Container buttonsContainer = new Container();

        addDonBtn = new Button("Ajouter une  donnation");

        styleButtonsContainer(buttonsContainer);
        buttonsContainer.addAll(addDonBtn);

        return buttonsContainer;

    }

    private void setupButtonsContainerActions() {
        addDonBtn.addActionListener((evt)
                -> {
            AddDonForm.show();
        });
    }

    private SpanLabel createDescription() {
        SpanLabel description = new SpanLabel();
        description.setText("Si vous êtes actuellement réfugié et que vous cherchez un endroit où séjourner, "
                + "veuillez jeter un coup d'œil à notre vaste collection "
                + "d'hébergements offerts par la communauté \n"
                + "Vous pouvez aussi parcourir "
                + "et choisir le réfugié que vous souhaitez soutenir");
        description.getStyle().setAlignment(CENTER);

        return description;

    }

    private void styleButtonsContainer(Container buttonsContainer) {
        buttonsContainer.setLayout(BoxLayout.x());
        buttonsContainer.getStyle().setPadding(10, 10, 10, 10);
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
        addDonBtn.getStyle().setFgColor(0x2541CD);
        addDonBtn.getStyle().setFont(font);
    }
}
