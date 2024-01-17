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
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sarah
 */
public class produit {
    private int id;
    private String ref;
    private String description ;

    public produit(int id, String ref, String description) {
        this.id = id;
        this.ref = ref;
        this.description = description;
    }
    
    public produit(String ref, String description) {
        this(-1,ref,description);
    }
    
    public void sauvegarde(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "insert into machine (ref,description) values (?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1,this. ref);
            st.setString(2, this.description);
            st.executeUpdate();
            try (ResultSet ids= st.getGeneratedKeys()){
                ids.next();
                this.id=ids.getInt(1);
            }
        }
    }
    
    public void delete(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "delete from produit where id=?")){
            st.setInt(1,this. id);
            st.executeUpdate();
         }
    }
    
    public static produit demande(ConnectionSGBD connSGBD)throws SQLException{
        String ref = ConsoleFdB.entreeString("référence : ");
        String description = ConsoleFdB.entreeString("description :");        
        return new produit(ref,description);
    }
    
    public static List<produit> tousLesProduits(ConnectionSGBD connSGBD) throws SQLException{
        List<produit> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(" select ref,description")){
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             String ref=res.getString("ref");
             String description= res.getString("description");
             alls.add(new produit(id,ref,description));
         }
     } 
    return alls;
    }

    @Override
    public String toString() {
        return "produit{" + "id=" + id + ", ref=" + ref + ", description=" + description + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
        public static class produitService {

        public static List<produit> getProduits() {
            return Arrays.asList(
                new produit(1, "Arbre", "Cylindre"),
                new produit(2, "Cube", "Parallélépipède rectangle"),
                new produit(3, "Boulon", "Visserie"),
                new produit(4, "Piston", "Element moteur")
        );
        }
    }
}
