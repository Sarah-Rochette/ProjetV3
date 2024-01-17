package fr.insa.rochette.cours.m3.projets.likes.model.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 *
 * @author sarah
 */
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
        //LoginForm login = new LoginForm(); 
        

	MenuBar menuWithDefaultTheme = new MenuBar();
        addItem(menuWithDefaultTheme, "Machine");

        MenuItem produit = menuWithDefaultTheme.addItem("Produit");
        SubMenu produitSubMenu = produit.getSubMenu();
        produitSubMenu.addItem("volant");
        produitSubMenu.addItem("piston");

        MenuBar menuWithPrimaryTheme = new MenuBar();
        menuWithPrimaryTheme.addThemeVariants(MenuBarVariant.LUMO_PRIMARY);
        addItem(menuWithPrimaryTheme, "Opération");

        MenuBar menuWithSmallTheme = new MenuBar();
        menuWithSmallTheme.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        addItem(menuWithSmallTheme, "opérateur");

        setInlineBlock(menuWithDefaultTheme);
        setInlineBlock(menuWithPrimaryTheme);
        setInlineBlock(menuWithSmallTheme);

        add(menuWithDefaultTheme, produit, menuWithPrimaryTheme,
                menuWithSmallTheme);
        

    }

    private void addItem(MenuBar menuItem, String label) {
        menuItem.addItem(label).getSubMenu().addItem("Item");
    }

    private void setInlineBlock(MenuBar menuBar) {
        menuBar.getStyle().set("display", "inline-block");
       
    	
	}
    }

