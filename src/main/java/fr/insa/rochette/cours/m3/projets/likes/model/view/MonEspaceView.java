/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model.view;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import fr.insa.rochette.cours.m3.projets.likes.model.Machine;
import fr.insa.rochette.cours.m3.projets.likes.model.Machine.MachineService;

/**
 *
 * @author cecil
 */

@Route("mon_espace")
@PageTitle("Mon Espace | Gestion Production")

public class MonEspaceView extends VerticalLayout {
    public MonEspaceView() {
        add(new H1("Bienvenue dans Mon Espace"));
        ComboBox<fr.insa.rochette.cours.m3.projets.likes.model.Machine> comboBox = new ComboBox<>("Votre choix");
        comboBox.setItems(MachineService.getMachines());
        comboBox.setItemLabelGenerator(Machine::getNom);
        add(comboBox);
    }

}
