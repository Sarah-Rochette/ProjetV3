/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model.views.main;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.router.Route;
/**
 *
 * @author sarah
 */


@Route(value="essai")
public class MenuBarStyles extends Div {
    public MenuBarStyles() {
//        Aligner les boutons à droite
//        MenuBar menuBar = new MenuBar();
//        menuBar.addThemeVariants(MenuBarVariant.LUMO_END_ALIGNED);
        
        
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