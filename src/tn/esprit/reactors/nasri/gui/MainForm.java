/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;

/**
 *
 * @author nasri
 */
public class MainForm extends Form
{
    Form addOfferForm = new AddOfferForm(this);
    Form addRequestForm = new AddRequestForm(this);
    Form listOffersForm = new ListOffersForm(this);
    Form listRequestForm = new ListRequestsForm(this);
    
    Button addOfferBtn;
    Button addRequestBtn;
       
    public MainForm() {
        Toolbar.setGlobalToolbar(true);
        this.setTitle("Refugiées");
        
        setupStyle();
        setupToolbar();
        
        Container buttonsContainer = createButtonsContainer();
        setupButtonsContainerActions();
        this.add(buttonsContainer);
        
        SpanLabel description = createDescription();
        ImageViewer heroImage = createHeroImage();
        
        this.addAll(description, heroImage);
        
        this.show();
    }
    
    private SpanLabel createDescription()
    {
        SpanLabel description = new SpanLabel();
        description.setText("Si vous êtes actuellement réfugié et que vous cherchez un endroit où séjourner, "
                + "veuillez jeter un coup d'œil à notre vaste collection "
                + "d'hébergements offerts par la communauté \n"
                + "Vous pouvez aussi parcourir "
                + "et choisir le réfugié que vous souhaitez soutenir");
        return description;
    }
    
    private ImageViewer createHeroImage()
    {
        Resources theme = UIManager.initFirstTheme("/theme");
        return new ImageViewer(theme.getImage("solidarity.jpg"));
    }
    
    private Container createButtonsContainer()
    {
        Container buttonsContainer = new Container();
        
        addOfferBtn = new Button("Ajouter un offre d'hébérgement");
        addRequestBtn = new Button("Ajouter une demande d'hébérgement");
        
        styleButtonsContainer(buttonsContainer);
        buttonsContainer.addAll(addOfferBtn, addRequestBtn);
        
        
        return buttonsContainer;
    }
    
    
    private void styleButtonsContainer(Container buttonsContainer)
    {
        buttonsContainer.setLayout(BoxLayout.xCenter());
        buttonsContainer.getStyle().setPadding(10, 10, 10, 10);
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
        addOfferBtn.getStyle().setFgColor(0x2541CD);
        addOfferBtn.getStyle().setFont(font);
        addRequestBtn.getStyle().setFgColor(0x2541CD);
        addRequestBtn.getStyle().setFont(font);
    }
    
    private void setupButtonsContainerActions()
    {
        addOfferBtn.addActionListener((evt) -> 
        {
            addOfferForm.show();
        });
        
        addRequestBtn.addActionListener((evt) -> 
        {
            addRequestForm.show();
        });
    }
    
    private void setupStyle()
    {
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        this.getStyle().setFont(font);
    }
    
    private void setupToolbar()
    {
        this.getToolbar().addMaterialCommandToSideMenu("Accueil", FontImage.MATERIAL_HOME, (evt) -> {
            this.show();
        });
        
        this.getToolbar().addMaterialCommandToSideMenu("Ajouter un offre d'hébérgement", FontImage.MATERIAL_ADD, (evt) -> {
            addOfferForm.show();
        });
        
        this.getToolbar().addMaterialCommandToSideMenu("Ajouter une demande d'hébérgement", FontImage.MATERIAL_ADD, (evt) -> {
            addRequestForm.show();
        });
        
        this.getToolbar().addMaterialCommandToSideMenu("Liste complete des offres d'hébérgement", FontImage.MATERIAL_INFO, (evt) -> {
            listOffersForm.show();
        });
        
        this.getToolbar().addMaterialCommandToSideMenu("Liste complete des demandes d'hébérgement", FontImage.MATERIAL_INFO, (evt) -> {
            listRequestForm.show();
        });
        
        
        /*addOfferForm.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            this.showBack();
        });*/
        
        addRequestForm.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            this.showBack();
        });
        
        listOffersForm.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            this.showBack();
        });
        
        listRequestForm.getToolbar().addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_KEYBOARD_BACKSPACE, (evt) -> {
            this.showBack();
        });
    }
}
