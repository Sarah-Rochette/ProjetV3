/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model;

import fr.insa.rochette.utils.database.ConnectionSGBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarah
 */
public class Role {
    public static final Role ADMIN_ROLE = new Role(1,"admin","administrateur");
    public static final Role USER_ROLE = new Role(2,"user","utilisateur");
    
    
    private int id;
    private String nom;
    private String description;
    


    public Role(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", nom=" + nom + ", description=" + description + '}';
    }
    
    public void sauvegarde(ConnectionSGBD connSGBD) throws SQLException{
    try (PreparedStatement st= connSGBD.getCon().prepareStatement(
        "insert into role (id,nom,description) values(?,?,?)")) {// les points d'interogations renplace.objet, on les met s'il y a de la ponctuation dans le texte introduit dans le sql
        st.setInt(1, this.id);// fixer la valeur du point d'interogation (num du point, var)
        st.setString(2, this.nom);
        st.setString(3, this.description);
        st.executeUpdate();
    }
    }
    
    public static List<Role> tousLesRoles(ConnectionSGBD connSGBD) throws SQLException{
        List<Role> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(" select id,nom,description from role")){
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             String nom=res.getString("nom");
             String description= res.getString("description");
             alls.add(new Role(id, nom, description));
         }
     } 
     return alls;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
