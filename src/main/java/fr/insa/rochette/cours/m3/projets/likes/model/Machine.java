/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model;

import fr.insa.rochette.utils.ConsoleFdB;
import fr.insa.rochette.utils.database.ConnectionSGBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarah
 */
public class Machine {
    private int id;
    private String nom;
    private String description;
    private int puissance;
    private int couthoraire;
    private String operation;  

    public Machine(int id, String nom, String description, int puissance, int couthoraire, String operation) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.puissance = puissance;
        this.couthoraire = couthoraire;
        this.operation = operation;
    }
    
    public Machine(String nom, String description, int puissance, int couthoraire, String operation) {
     this(-1,nom, description, puissance, couthoraire, operation);
    }
    
    public void sauvegarde(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "insert into machine (nom,description,puissance, coutHoraire,operation) values (?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1,this. nom);
            st.setString(2, this.description);
            st.setInt(3,this.puissance);
            st.setInt(4, this.couthoraire);
            st.setString(5, this.operation);
            st.executeUpdate();
            try (ResultSet ids= st.getGeneratedKeys()){
                ids.next();
                this.id=ids.getInt(1);
            }
        }
    }
    
    public void delete(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "delete from machine where id=?")){
            st.setInt(1,this. id);
            st.executeUpdate();
         }
    }
    
    public static Machine demande(ConnectionSGBD connSGBD)throws SQLException{
        String nom = ConsoleFdB.entreeString("nom de la machine : ");
        String description = ConsoleFdB.entreeString("description :");
        int puissance = ConsoleFdB.entreeInt("puissance : ");
        int couthoraire = ConsoleFdB.entreeInt("couthoraire : ");
        String operation= ConsoleFdB.entreeString("operation :");        
        return new Machine(nom, description,puissance, couthoraire, operation);
    }
    
    public static List<Machine> toutesLesMachines(ConnectionSGBD connSGBD) throws SQLException{
        List<Machine> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(" select id,nom,description,puissance,couthoraire,operation from machine")){
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             String nom=res.getString("nom");
             String description= res.getString("description");
             int puissance = res.getInt("puissance");
             int couthoraire = res.getInt("couthoraire");
             String operation= res.getString("operation");
             alls.add(new Machine(id,nom,description,puissance,couthoraire,operation));
         }
     } 
    return alls;
    }
    
    @Override
    public String toString() {
        return "Machine{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", puissance=" + puissance + ", couthoraire=" + couthoraire + ", operation=" + operation + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public int getCouthoraire() {
        return couthoraire;
    }

    public void setCouthoraire(int couthoraire) {
        this.couthoraire = couthoraire;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    
}

