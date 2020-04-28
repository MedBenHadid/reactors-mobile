package tn.esprit.reactors.chihab.forms;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.ComponentAnimation;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.Statics;


public class AssociationHomeForm extends ReactorsForm{
    private final FontImage PERSON = FontImage.createMaterial(FontImage.MATERIAL_PERSON_PIN_CIRCLE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage SORT = FontImage.createMaterial(FontImage.MATERIAL_SORT, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getWidth() /4, 0xffff0000), true);
    private final URLImage background = URLImage.createToStorage(placeholder, "banner-ass-home.jpeg","https://mpng.subpng.com/20181205/gv/kisspng-volunteering-clip-art-computer-icons-non-profit-or-flashforward-reel-asian-spring-fundraising-drive-5c07b5a363e513.1166421615440091234092.jpg");
    private final Style stitle = this.getToolbar().getTitleComponent().getUnselectedStyle();
    private AssociationHomeForm(Form previous) {
        super(null,previous);
        background.fetch();
        
        stitle.setBgImage(background);
        stitle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        stitle.setPaddingUnit(Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS);
        stitle.setPaddingTop(15);
        SpanButton credit = new SpanButton("You can also check out our website!");
        credit.addActionListener((e) -> Display.getInstance().execute(Statics.BASE_URL));
        this.add(new SpanLabel("C’est un fait : les associations tunisiennes ont de plus en plus de mal à trouver des bénévoles qui souhaitent s’investir sur le long terme, mettant ainsi en péril la stabilité et la longévité de la plupart de ces structures. Cet article s’adresse donc aux personnes souhaitant rejoindre les rangs du milieu associatif et bénévole français. En voilà une bonne résolution pour entamer l’automne !")).
        add(new Label("Call to action :", "Heading")).add(new SpanLabel("S’engager dans une association en tant que bénévole est une décision importante, pleine de promesses et qui relève souvent d’un bien-fondé. Alors, pourquoi s’engager maintenant dans le bénévolat et faire partie d’une association ?")).
        add(credit);
        ComponentAnimation title = this.getToolbar().getTitleComponent().createStyleAnimation("Home", 200);
        this.getAnimationManager().onTitleScrollAnimation(title);

        MultiButton btnSignUp = new MultiButton("Sign up");
        MultiButton btnListAsses = new MultiButton("Discover more");
        btnSignUp.addActionListener(e-> new SignUpAssociation(this,new TextModeLayout(3, 2)));
        btnListAsses.addActionListener(e-> ListAssociationsForm.getInstance(this).showBack());
        btnSignUp.setIcon(PERSON);
        btnListAsses.setIcon(SORT);
        this.addAll(btnSignUp,btnListAsses);
    }
    private static AssociationHomeForm instance=null;
    public static AssociationHomeForm getInstance(Form previous) {
        if (instance == null) {
            instance = new AssociationHomeForm(previous);
        }
        return instance;
    }
    
}
