/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model.view;

import fr.insa.rochette.cours.m3.projets.likes.model.Machine.MachineService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import fr.insa.rochette.cours.m3.projets.likes.model.Machine;
import java.util.List;


/**
 *
 * @author cecil
 */



@Route(value = "machine")
@PageTitle("Liste des machines | Gestion Production")
/**
 *
 * @author cecil
 */
public class MachineView extends Div{
    public MachineView() {
        Grid<Machine> grid = new Grid<>(Machine.class);
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.Machine::getId).setHeader("Id");
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.Machine::getNom).setHeader("Nom");
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.Machine::getDescription).setHeader("Description");
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.Machine::getPuissance).setHeader("Puissance");
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.Machine::getCouthoraire).setHeader("Coût horaire");
        grid.addColumn(fr.insa.rochette.cours.m3.projets.likes.model.Machine::getOperation).setHeader("Opération");
//        grid.addThemeVariants(Grid.LUMO_ROW_STRIPES);

        List<Machine> machines = MachineService.getMachines();
        grid.setItems(machines);

        add(grid);
    }
}

