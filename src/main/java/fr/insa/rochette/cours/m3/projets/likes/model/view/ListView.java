/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.rochette.cours.m3.projets.likes.model.produit;
import jakarta.annotation.security.PermitAll;
import java.util.List;

/**
 *
 * @author sarah
 */
@PermitAll
@Route(value="produit", layout = MainLayout.class)
@PageTitle("Produits | Gestion Production")
public class ListView extends Div {

    public ListView() {
        Grid<produit> grid = new Grid<>(produit.class);
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.produit::getId).setHeader("Id");
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.produit::getRef).setHeader("Nom");
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.produit::getDescription).setHeader("Description");

        List<produit> produits = produit.produitService.getProduits();
        grid.setItems(produits);

        add(grid);
    }
}
