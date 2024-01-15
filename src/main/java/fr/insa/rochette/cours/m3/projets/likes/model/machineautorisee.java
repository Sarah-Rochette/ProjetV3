/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model;

import fr.insa.rochette.utils.database.ConnectionSGBD;
import fr.insa.rochette.utils.list.ListUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarah
 */
public class machineautorisee {
    private int id;
    private int idutilisateur;
    private int idmachine;
    
    public machineautorisee(int id, int idutilisateur, int idmachine) {
        this.id = id;
        this.idutilisateur =idutilisateur ;
        this.idmachine =idmachine ;
    }
    
    public machineautorisee( int idutilisateur, int idmachine) {
     this(-1,idutilisateur, idmachine);
    }
    
    public void sauvegarde(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "insert into machineautorisee (idutilisateur,idmachine) values (?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setInt(1,this.idutilisateur);
            st.setInt(2,this.idmachine);
            st.executeUpdate();
            try (ResultSet ids= st.getGeneratedKeys()){
                ids.next();
                this.id=ids.getInt(1);
            }
        }
    }
    public void delete(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "delete from machineautorisee where id=?")){
            st.setInt(1,this. id);
            st.executeUpdate();
         }
    }
     public static realise demande(ConnectionSGBD connSGBD)throws SQLException{
        Utilisateur choix1 =ListUtils.selectOne("--- selectionnez un utilisateur",Utilisateur.tousLesUtilisateurs(connSGBD), Utilisateur::toString);
        Machine choix2 =ListUtils.selectOne("--- selectionnez une machine",Machine.toutesLesMachines(connSGBD), Machine::toString);
        return new realise(choix1.getId(),choix2.getId());
    }
     public static List<machineautorisee> toutesLesMachinesAutorisees(ConnectionSGBD connSGBD) throws SQLException{
        List<machineautorisee> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(" select id,idutilisateur,idmachine")){
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             int idutilisateur = res.getInt("idutilisateur");
             int idmachine = res.getInt("idmachine");
             alls.add(new machineautorisee(id,idutilisateur,idmachine));
         }
     } 
    return alls;
    }

    @Override
    public String toString() {
        return "machineautorisee{" + "id=" + id + ", idutilisateur=" + idutilisateur + ", idmachine=" + idmachine + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(int idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public int getIdmachine() {
        return idmachine;
    }

    public void setIdmachine(int idmachine) {
        this.idmachine = idmachine;
    }
    
    }
    