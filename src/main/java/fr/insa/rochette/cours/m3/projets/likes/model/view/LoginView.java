/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model.view;

/**
 *
 * @author sarah
 */
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.rochette.cours.m3.projets.likes.model.Utilisateur;
import java.util.ArrayList;

@Route("login") 


@PageTitle("Login | Gestion Production")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm(); 



	public LoginView(){
		addClassName("login-view");
		setSizeFull(); 


		setAlignItems(FlexComponent.Alignment.CENTER);
		setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

		this.login.setAction("login");
                 this.login.addLoginListener(event-> {
                     String mp = event.getPassword();
                     String login= event.getUsername();
               // ArrayList<Utilisateur> web = tousLesUtilisateurs(ConnectinSGBD connSGBD);
                
                     
                 });

		add(new H1("Gestion de Production"), login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()  


        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
}
