package tn.esprit.reactors.chihab.forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanButton;
import com.codename1.components.SpanLabel;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.UITimer;
import java.util.Calendar;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.Statics;
import tn.esprit.reactors.malek.services.UserService;


public class AssociationHomeForm extends ReactorsForm{
    private final FontImage PERSON = FontImage.createMaterial(FontImage.MATERIAL_PERSON_PIN_CIRCLE, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final FontImage SORT = FontImage.createMaterial(FontImage.MATERIAL_SORT, UIManager.getInstance().getComponentStyle("MultiLine1"));
    private final EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getWidth() /4, 0xffff0000), true);
    private final URLImage background = URLImage.createToStorage(placeholder, "banner-ass-home.jpeg","https://mpng.subpng.com/20181205/gv/kisspng-volunteering-clip-art-computer-icons-non-profit-or-flashforward-reel-asian-spring-fundraising-drive-5c07b5a363e513.1166421615440091234092.jpg");
    private final Style stitle = this.getToolbar().getTitleComponent().getUnselectedStyle();
    private MyAssociationHomeForm m;
    private final Style sTitleCommand = UIManager.getInstance().getComponentStyle("TitleCommand");
    private final FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_PEOPLE, sTitleCommand);
        
    private AssociationHomeForm(Form previous) {
        super(null,previous);
        background.fetch();
        
        stitle.setBgImage(background);
        stitle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        stitle.setPaddingUnit(Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS);
        stitle.setPaddingTop(15);
        SpanButton credit = new SpanButton("You can also check out our website!");
        credit.addActionListener((e) -> Display.getInstance().execute(Statics.BASE_URL));
        this.add(new SpanLabel("C’est un fait : les associations tunisiennes ont de plus en plus de mal à trouver des bénévoles qui souhaitent s’investir sur le long terme, mettant ainsi en péril la stabilité et la longévité de la plupart de ces structures. Cet article s’adresse donc aux personnes souhaitant rejoindre les rangs du milieu associatif et bénévole français. En voilà une bonne résolution pour entamer l’automne !"));
        this.add(new Label("Call to action :", "Heading")).add(new SpanLabel("S’engager dans une association en tant que bénévole est une décision importante, pleine de promesses et qui relève souvent d’un bien-fondé. Alors, pourquoi s’engager maintenant dans le bénévolat et faire partie d’une association ?"));
        
        // Signup button
        MultiButton btnSignUp = new MultiButton("Sign up");
        btnSignUp.addActionListener(e-> {
            Dialog signUpBlocking = new InfiniteProgress().showInfiniteBlocking();
            SignUpAssociation signUpInstance = SignUpAssociation.getInstance(this,new TextModeLayout(3, 2), UserService.getInstance().getUser());
            signUpBlocking.dispose();
            signUpInstance.show();
        });
        btnSignUp.setIcon(PERSON);
        this.add(btnSignUp);
        
        
        this.add(credit);
        
        this.getAnimationManager().onTitleScrollAnimation(this.getToolbar().getTitleComponent().createStyleAnimation("Home", 200));
            
        

               
               
               
               
               
               
               
               MultiButton btnListAsses = new MultiButton("Discover more");
        
        
        
        
        
            btnListAsses.addActionListener(e-> {
                if(ListAssociationsForm.instance==null){ 
                    Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                    ListAssociationsForm.getInstance(this);
                    ip.dispose();
                    ListAssociationsForm.getInstance(this).showForm();
                }else{
                    ListAssociationsForm.getInstance(this).showBack();
                }
            });


        
        btnListAsses.setIcon(SORT);
        this.add(btnListAsses);
        if(UserService.getInstance().getUser().isMember()||UserService.getInstance().getUser().isAssociationAdmin()){
            this.getToolbar().addCommandToRightBar("My associations", icon, ev->{
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                m = MyAssociationHomeForm.getInstance(this);
                ip.dispose();
                m.showBack();
            });
        }
    }
    private static AssociationHomeForm instance=null;
    public static AssociationHomeForm getInstance(Form previous) {
        if (instance == null) {
            instance = new AssociationHomeForm(previous);
        }
        return instance;
    }
    
}
