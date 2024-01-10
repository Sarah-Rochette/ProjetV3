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
public class typeop {
    private int id;
    private String description;
    
    public typeop(int id, String description){
        this.id=id;
        this.description=description;
    }
    
    public typeop(String description){
        this(-1,description);
    }
    
    public void sauvegarde(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "insert into typeop (description) values (?)",
                PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, this.description);
            st.executeUpdate();
            try (ResultSet ids= st.getGeneratedKeys()){
                ids.next();
                this.id=ids.getInt(1);
            }
        }
    }
    
    public void delete(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "delete from typeop where id=?")){
            st.setInt(1,this. id);
            st.executeUpdate();
         }
    }
    
    public static typeop demande(ConnectionSGBD connSGBD)throws SQLException{
        String description = ConsoleFdB.entreeString("description :");     
        return new typeop(description);
    }
    
    public static List<typeop> tousLesTypeop(ConnectionSGBD connSGBD) throws SQLException{
        List<typeop> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(" select id,description from typeop")){
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             String description= res.getString("description");
             alls.add(new typeop(id,description));
         }
     } 
    return alls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "typeop{" + "id=" + id + ", description=" + description + '}';
    }
    
    
    
}
