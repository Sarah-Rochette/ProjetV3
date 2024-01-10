package fr.insa.rochette.cours.m3.projets.likes.model.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 *
 * @author sarah
 */
@PageTitle("Main")
@Route(value = "Main")
public class MainView extends HorizontalLayout{

    private TextField name;
    private Button sayHello;
    
    

    public MainView() {
//        Button button = new Button("I'm a button");
//        HorizontalLayout layout = new HorizontalLayout(button, new DatePicker("Pick a date"));
//
//        layout.setDefaultVerticalComponentAlignment(Alignment.END);
//        add(layout);
//        button.addClickListener(clickEvent -> add(new Text("Clicked!")));
//        name = new TextField("Your name");
//        sayHello = new Button("Say hello");
//        sayHello.addClickListener(e -> {
//            Notification.show("Hello " + name.getValue());
//        });
//        sayHello.addClickShortcut(Key.ENTER);
//
//        setMargin(true);
//        setVerticalComponentAlignment(Alignment.END, name, sayHello);
//
//        add(name, sayHello);
//        
//        PasswordField passwordField = new PasswordField();
//        passwordField.setLabel("Password");
//        //passwordField.setValue("Ex@mplePassw0rd");
//        add(passwordField);        
//     

    }

}
