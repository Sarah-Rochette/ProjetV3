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


   public class ProduitBrut {
       private int id;
       private String description;
       private int nombre;
   
   public ProduitBrut(int id, String description, int nombre){
       this.id=id;
       this.description=description;
       this.nombre=nombre;
   }
   
   public ProduitBrut(String description, int nombre){
       this(-1,description,nombre);
   }
   
   public void sauvegarde(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "insert into produitbrut (description, nombre) values (?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, this.description);
            st.setInt(2,this.nombre);
            st.executeUpdate();
            try (ResultSet ids= st.getGeneratedKeys()){
                ids.next();
                this.id=ids.getInt(1);
            }
        }
    }
    
   public void delete(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "delete from produitbrut where id=?")){
            st.setInt(1,this. id);
            st.executeUpdate();
         }
    }
   
   
   public static ProduitBrut demande(ConnectionSGBD connSGBD)throws SQLException{
        String description = ConsoleFdB.entreeString("description :");
        int nombre = ConsoleFdB.entreeInt("nombre de produit brut : ");       
        return new ProduitBrut(description,nombre);
    }
   public static List<ProduitBrut> tousLesProduitsBruts(ConnectionSGBD connSGBD) throws SQLException{
        List<ProduitBrut> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(" select id,description,nombre from produitbrut")){
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             String description= res.getString("description");
             int nombre = res.getInt("nombre");
             alls.add(new ProduitBrut(id,description,nombre));
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

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ProduitBrut{" + "id=" + id + ", description=" + description + ", nombre=" + nombre + '}';
    }

    public String getString() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}
