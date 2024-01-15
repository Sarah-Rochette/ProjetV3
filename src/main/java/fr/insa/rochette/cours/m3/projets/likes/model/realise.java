/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model;

import fr.insa.rochette.utils.ConsoleFdB;
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
public class realise {
    
    private int id;
    private int idtype;
    private int duree; 

    public realise(int id, int idtype, int duree) {
        this.id = id;
        this.idtype = idtype;
        this.duree = duree;
    }
    
    public realise(int idtype, int duree) {
     this(-1,idtype, duree);
    }
    
    public void sauvegarde(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "insert into realise (idtype,duree) values (?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setInt(1,this.idtype);
            st.setInt(2,this.duree);
            st.executeUpdate();
            try (ResultSet ids= st.getGeneratedKeys()){
                ids.next();
                this.id=ids.getInt(1);
            }
        }
    }
    
    public void delete(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "delete from realise where id=?")){
            st.setInt(1,this. id);
            st.executeUpdate();
         }
    }
    
    public static realise demande(ConnectionSGBD connSGBD)throws SQLException{
        typeop choix1 =ListUtils.selectOne("--- selectionnez un type d'opération",typeop.tousLesTypeop(connSGBD), typeop::toString);
        int duree= ConsoleFdB.entreeInt("entrer la durée");
        return new realise(choix1.getId(),duree);
    }
    
    public static List<realise> tousrealiser(ConnectionSGBD connSGBD) throws SQLException{
        List<realise> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(" select id,idtype,duree")){
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             int idtype = res.getInt("idtype");
             int duree = res.getInt("duree");
             alls.add(new realise(id,idtype,duree));
         }
     } 
    return alls;
    }

    @Override
    public String toString() {
        return "realise{" + "id=" + id + ", idtype=" + idtype + ", duree=" + duree + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdtype() {
        return idtype;
    }

    public void setIdtype(int idtype) {
        this.idtype = idtype;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
    
}
