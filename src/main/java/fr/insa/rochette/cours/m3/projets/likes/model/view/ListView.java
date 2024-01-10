/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

/**
 *
 * @author sarah
 */
@PermitAll
@Route(value="", layout = MainLayout.class)
@PageTitle("Contacts | Gestion Production")
public class ListView extends VerticalLayout {
    // omitted
}
