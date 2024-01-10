/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model;

import fr.insa.rochette.utils.exceptions.ExceptionsUtils;
import fr.insa.rochette.utils.ConsoleFdB;
import fr.insa.rochette.utils.database.ConnectionSGBD;
import fr.insa.rochette.utils.list.ListUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author sarah
 */
public class GestionBdD {
    
     
//    public static Connection connectionMariaDB(String host, int port, String database, String user, String pass) throws SQLException{
//        Connection con = DriverManager.getConnection(
//                "jdbc:"+"mariadb://"+host+":"+port+"/"+database, user, pass);
//    con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//    return con;
//    }

    
    public static ConnectionSGBD defautCon() throws SQLException{
        return ConnectionSGBD.connect(ConnectionSGBD.SGBDConnus.MARIADB,
                "92.222.25.165",3306,"m3_srochette01","m3_srochette01","9d235468");
    }
    
    public static void creeSchema(ConnectionSGBD connSGBD) throws SQLException{
        Connection conn=connSGBD.getCon();
        conn.setAutoCommit(false);
        try (Statement st= conn.createStatement()){
            st.executeUpdate("create table role (\n"
                   +"id integer primary key,\n"
                   +"nom varchar(20),\n"
                   +"description text\n"
                   +")");
            st.executeUpdate("create table utilisateur (\n"
                   +connSGBD.getSgbd().sqlForGeneratedIntPKColumn("id")+",\n"
                   +"login varchar(50),\n"
                   +"password varchar(40),\n"
                   +"description text, \n"
                   +"idrole integer"
                   +")");
            st.executeUpdate("create table machine (\n"
                   +connSGBD.getSgbd().sqlForGeneratedIntPKColumn("id")+",\n"
                   +"nom varchar(20),\n"
                   +"description text,\n"
                   +"puissance integer,\n"
                   +"couthoraire integer,\n"
                   +"operation varchar(20)\n"
                   +")");
            st.executeUpdate("create table typeop (\n"
                   +connSGBD.getSgbd().sqlForGeneratedIntPKColumn("id")+",\n"
                   +"description text\n"
                   +")");
            st.executeUpdate("alter table utilisateur \n"
                   +" add constraint fk_utilisateur_idrole \n"
                   +"foreign key (idrole) references role(id)");
            st.executeUpdate("create table realise (\n"
                   +connSGBD.getSgbd().sqlForGeneratedIntPKColumn("id")+",\n"
                   +"puissance integer,\n"
                   +"idtype integer,\n"
                   +"duree integer\n"
                   +")");
            st.executeUpdate("create table produit (\n"
                   +connSGBD.getSgbd().sqlForGeneratedIntPKColumn("id")+",\n"
                   +"ref String,\n"
                   +"descritpion String\n"
                   +")");
            st.executeUpdate("alter table realise \n"
                   +" add constraint fk_realise_idtype \n"
                   +"foreign key (idtype) references typeop(id)");
            st.executeUpdate("create table produit (\n"
                   +connSGBD.getSgbd().sqlForGeneratedIntPKColumn("id")+",\n"
                   +"ref String,\n"
                   +"descritpion String\n"
                   +")");
             st.executeUpdate("create table operation (\n"
                   +connSGBD.getSgbd().sqlForGeneratedIntPKColumn("id")+",\n"
                   +"idtype integer,\n"
                   +"idproduit integer\n"
                   +")");
            st.executeUpdate("alter table operation \n"
                   +" add constraint fk_operation_idtype \n"
                   +"foreign key (idtype) references typeop(id)");
            st.executeUpdate("alter table operation \n"
                   +" add constraint fk_operation_idproduit \n"
                   +"foreign key (idproduit) references produit(id)");
           conn.commit();
        }
        catch(SQLException ex){
            conn.rollback();
            throw ex;
        }
        finally{
            conn.setAutoCommit(true);
        }
    }
    
    public static void initialise(ConnectionSGBD connSGBD) throws SQLException{
        Connection conn=connSGBD.getCon();
        Role radmin= new Role(1,"admin","administrateur");
        radmin.sauvegarde(connSGBD);
        Role user= new Role(2,"user","utilisateur de base");
        user.sauvegarde(connSGBD);
        Utilisateur admin= new Utilisateur("admin","admin","administrateur du site",1);
        admin.sauvegarde(connSGBD);
    }
    
    public static void razBdD(ConnectionSGBD connSGBD) throws SQLException{
        supprimeSchema(connSGBD);
        creeSchema(connSGBD);
        initialise(connSGBD);
    }
    
    public static void supprimeSchema(ConnectionSGBD connSGBD) throws SQLException{
        Connection conn=connSGBD.getCon();
        try (Statement st= conn.createStatement()){
            try{
            st.executeUpdate("alter table operation drop constraint fk_operation_idproduit");   
           }
           catch(SQLException ex){
           }
            try{
            st.executeUpdate("alter table operation drop constraint fk_operation_idtype");   
           }
           catch(SQLException ex){
           }
           try{
            st.executeUpdate("drop table operation ");
           }
           catch(SQLException ex){
           }
            try{
            st.executeUpdate("alter table utilisateur drop constraint fk_typeop_idtype");   
           }
           catch(SQLException ex){
           }
           try{
            st.executeUpdate("drop table produit ");
           }
           catch(SQLException ex){
           }
           try{
            st.executeUpdate("drop table realise ");
           }
           catch(SQLException ex){
           }
           try{
            st.executeUpdate("alter table utilisateur drop constraint fk_utilisateur_idrole");   
           }
           catch(SQLException ex){
           }
           try{
            st.executeUpdate("drop table typeop ");
           }
           catch(SQLException ex){
           }
           try{
            st.executeUpdate("drop table machine ");
           }
           catch(SQLException ex){
           }
           try{
            st.executeUpdate("drop table utilisateur");
           }
           catch(SQLException ex){
           }
           try{
            st.executeUpdate("drop table role");   
           }
           catch(SQLException ex){
           }     
               
        } 
    }
    
    public static void menuPrincipal(ConnectionSGBD connSGBD){
        int rep=-1;
        while (rep!=0){
            int i=1;
            System.out.println("Menu prinicipal");
            System.out.println("===============");
            System.out.println((i++)+")supprimer schéma");
            System.out.println((i++)+")créer schéma");
            System.out.println((i++)+") initialiser la BdD");
            System.out.println((i++)+") RAZ de la BdD = supprime + cree + init");
            System.out.println((i++)+") Menu utilisateur");
            System.out.println((i++)+") Ajouter une machine");
            System.out.println((i++)+") Afficher les machines");
            System.out.println((i++)+") Ajouter un type d'opération");
            System.out.println((i++)+") Afficher les types d'opérations");
            System.out.println("0) Fin");
            rep= ConsoleFdB.entreeEntier("Votre choix :");
            try{
                int j=1;
                if (rep==j++){
                    supprimeSchema(connSGBD);
                }else if (rep== j++){
                    creeSchema(connSGBD);
                }else if (rep== j++){
                    initialise(connSGBD);
                }else if (rep== j++){
                    razBdD(connSGBD);
                }else if (rep== j++){
                    Utilisateur.menuUtilisateur(connSGBD);
                }else if (rep==j++){
                    Machine nouveau = Machine.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("machine N°"+ nouveau.getId()+"crée");
                }else if (rep==j++){
                    System.out.println(ListUtils.enumerateList(Machine.toutesLesMachines(connSGBD)));
                }else if (rep==j++){
                    typeop nouveau = typeop.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("type d'opération N°"+ nouveau.getId()+"crée");
                }else if (rep == j++){
                    System.out.println(ListUtils.enumerateList(typeop.tousLesTypeop(connSGBD)));
                }else if (rep==j++){
                    produit nouveau = produit.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("Produit N°"+ nouveau.getId()+"crée");
                }
            }catch (SQLException ex){
                System.out.println(ExceptionsUtils.messageEtPremiersAppelsDansPackage(ex,"fr.insa.rochette",10));
            }
        }
    }
    
    
            
    public static void debut(){
        try{
            ConnectionSGBD connSGBD = defautCon();
            System.out.println("Connecte!!");
            menuPrincipal(connSGBD);
        }catch(SQLException ex){
            
            throw new Error(ex);
        }
    }
    
    public static void main(String[] args){
        debut();
    }
}
