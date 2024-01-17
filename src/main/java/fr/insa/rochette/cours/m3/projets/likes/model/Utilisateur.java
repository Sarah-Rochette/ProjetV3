/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model;

import static fr.insa.rochette.cours.m3.projets.likes.model.GestionBdD.creeSchema;
import static fr.insa.rochette.cours.m3.projets.likes.model.GestionBdD.initialise;
import static fr.insa.rochette.cours.m3.projets.likes.model.GestionBdD.razBdD;
import static fr.insa.rochette.cours.m3.projets.likes.model.GestionBdD.supprimeSchema;
import fr.insa.rochette.utils.ConsoleFdB;
import fr.insa.rochette.utils.database.ConnectionSGBD;
import fr.insa.rochette.utils.exceptions.ExceptionsUtils;
import fr.insa.rochette.utils.list.ListUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author sarah
 */
public class Utilisateur {
    private int id;
    private String login;
    private String password;
    private String description;
    private int idrole;
    private Role role;


    private Utilisateur(int id, String login, String password, String description, int idrole ) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.description = description;
        this.idrole = idrole;
       
    }
 
    
    public Utilisateur(String login, String password, String description, int idrole) {
        this(-1,login,password,description,idrole);
    }
  
    
    public void sauvegarde(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "insert into utilisateur (login,password,description,idrole) values (?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1,this. login);
            st.setString(2, this.password);
            st.setString(3,this.description);
            st.setInt(4, this.idrole);
            st.executeUpdate();
            try (ResultSet ids= st.getGeneratedKeys()){
                ids.next();
                this.id=ids.getInt(1);
            }
        }
    }
    
    public void delete(ConnectionSGBD connSGBD)throws SQLException{
        try (PreparedStatement st= connSGBD.getCon().prepareStatement(
                 "delete from utilisateur where id=?")){
            st.setInt(1,this. id);
            st.executeUpdate();
         }
    }
    
    public static Optional<Utilisateur> login (ConnectionSGBD connSGBD, String login, String pass) throws SQLException{
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(
                "select id,login,password,description,idrole from utilisateur where login = ? and password = ?")){
         st.setString(1,login);
         st.setString(2, pass);
         ResultSet res = st.executeQuery();
        if(res.next()){
             int id = res.getInt("id");
             String description= res.getString("description");
             int idrole = res.getInt("idrole");
             return Optional.of(new Utilisateur(id,login, pass,description,idrole));
         }else{
            return Optional.empty();
        }
    }
    }
    
    public static Utilisateur demande(ConnectionSGBD connSGBD)throws SQLException{
        String login = ConsoleFdB.entreeString("login de l'utilisateur : ");
        String pass = ConsoleFdB.entreeString("password");
        String description = ConsoleFdB.entreeTexte("description : ");
        Role choix =ListUtils.selectOne("--- selectionnez un role",Role.tousLesRoles(connSGBD), Role::toString);
        return new Utilisateur(login, pass, description, choix.getId());
    }
    
    public static List<Utilisateur> tousLesUtilisateurs(ConnectionSGBD connSGBD) throws SQLException{
        List<Utilisateur> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(" select id,login,password,description,idrole from utilisateur")){
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             String login=res.getString("login");
             String password=res.getString("password");
             String description= res.getString("description");
             int idrole = res.getInt("idrole");
             alls.add(new Utilisateur(id,login, password,description,idrole));
         }
     } 
     return alls;
    }
    
    
    
    private List<Utilisateur> cherche(ConnectionSGBD connSGBD, String requeteSQL) throws SQLException{
        List<Utilisateur> alls = new ArrayList<>();
        try (PreparedStatement st = connSGBD.getCon().prepareStatement(
                requeteSQL)){
         st.setInt(1,this.id);
         ResultSet res = st.executeQuery();
         while(res.next()){
             int id = res.getInt("id");
             String login=res.getString("login");
             String password=res.getString("password");
             String description= res.getString("description");
             int idrole = res.getInt("idrole");
             alls.add(new Utilisateur(id,login, password,description,idrole));
         }
     } 
     return alls;
    }
    
    public static List<Utilisateur> utilisateursPourTest(String nomBase, int nbr){
        return Stream.iterate(1,i->i<=nbr, i->i+1)
                .map(i-> nomBase+i)
                .map(nom -> new Utilisateur (nom, "pass", "utilisateur test",2))
                .toList();// stream est une séquence d'élément de 1 jusqu'à nbr inclus  
        // on peut faire un for inch à la place de toList pour faire la sauvegarde dans la même méthode
    }
    
    public static void creeUtilisateursTest(ConnectionSGBD connSGBD,String nomBase, int nbr) throws SQLException{
        for (Utilisateur u : utilisateursPourTest(nomBase,nbr)){
            u.sauvegarde(connSGBD);
        }
    }
    // on fait une méthode pour créer la liste et une pour la sauvegarder car c'est compliqué quand on a un "for inch" avec une méthode qui 
    // renvoi des exceptions
            
            
    public void menuUtilisateurConnecte(ConnectionSGBD connSGBD) throws SQLException{
        int rep=-1;
        while (rep!=0){
            int i=1;
            System.out.println("Utilisateur"+ this.login);
            System.out.println("=====================");
            System.out.println((i++)+")voir tous les produits");
            System.out.println((i++)+")voir toutes les machines");
            System.out.println((i++)+")voir toutes les opérations");
            System.out.println("0) Fin");
            rep= ConsoleFdB.entreeEntier("Votre choix :");
            try{
                int j=1;
                if (rep==j++){
                    System.out.println(ListUtils.enumerateList(produit.tousLesProduits(connSGBD)));
                }else if (rep== j++){
                    System.out.println(ListUtils.enumerateList(Machine.toutesLesMachines(connSGBD)));
                }else if (rep==j++){
                    System.out.println(ListUtils.enumerateList(typeop.tousLesTypeop(connSGBD)));
  
            }
                
            }catch (SQLException ex){
                System.out.println(ExceptionsUtils.messageEtPremiersAppelsDansPackage(ex,"fr.insa.rochette",10));
            } 
        }
    }


    public void menuAdminConnecte(ConnectionSGBD connSGBD) throws SQLException{
        int rep=-1;
        while (rep!=0){
            int i=1;
            System.out.println("Admin"+ this.login);
            System.out.println("=====================");
            System.out.println((i++)+")voir tous les produits");
            System.out.println((i++)+")voir toutes les machines");
            System.out.println((i++)+")voir tous les types opérations");
            System.out.println((i++)+")ajouter une machine");
            System.out.println((i++)+")ajouter un produit");
            System.out.println((i++)+")ajouter un produit brut");
            System.out.println((i++)+")ajouter un type d'opération");
            System.out.println((i++)+")ajouter une opération");
            System.out.println((i++)+")ajouter le temps de réalisation d'une opération");
            System.out.println((i++)+")lister tous les utilisateurs");
            System.out.println((i++)+")supprimer un utilisateur");
            System.out.println((i++)+")créer des utilisateurs test");
            System.out.println((i++)+")créer un nouvel utilisateur");
            System.out.println("0) Fin");
            rep= ConsoleFdB.entreeEntier("Votre choix :");
            try{
                int j=1;
                if (rep==j++){
                    System.out.println(ListUtils.enumerateList(produit.tousLesProduits(connSGBD)));
                }else if (rep== j++){
                    System.out.println(ListUtils.enumerateList(Machine.toutesLesMachines(connSGBD)));
                }else if (rep==j++){
                     System.out.println(ListUtils.enumerateList(typeop.tousLesTypeop(connSGBD)));
                
                }else if (rep==j++){
                    Machine nouveau = Machine.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("machine N°"+ nouveau.getId()+"crée");
                }else if (rep==j++){
                    produit nouveau = produit.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("produit N°"+ nouveau.getId()+"crée");
                }else if (rep==j++){
                    ProduitBrut nouveau = ProduitBrut.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("produit brut N°"+ nouveau.getId()+"crée");
                }else if (rep==j++){
                    typeop nouveau = typeop.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("type d'opération N°"+ nouveau.getId()+"crée");
                }else if (rep==j++){
                    operation nouveau = operation.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("opération N°"+ nouveau.getId()+"crée");
                }else if (rep==j++){
                    realise nouveau = realise.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("realise N°"+ nouveau.getId()+"crée");
                }else if (rep==j++){
                    System.out.println(ListUtils.enumerateList(Utilisateur.tousLesUtilisateurs(connSGBD)));
                }else if (rep== j++){
                    Optional<Utilisateur> choix= ListUtils.selectOneOrCancel("--- selectionnez un utilisateur à supprimer",
                            Utilisateur.tousLesUtilisateurs(connSGBD), Utilisateur::toString);
                    if (choix.isPresent()){
                        choix.get().delete(connSGBD);
                    }
                }else if (rep== j++){
                    String noms = ConsoleFdB.entreeString("nom de base");
                    int nbr = ConsoleFdB.entreeInt("nombre d'utilisateur à créer");
                    Utilisateur.creeUtilisateursTest(connSGBD, noms, nbr);
                }else if (rep== j++){
                    Utilisateur nouveau = Utilisateur.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("utilisateur N°"+ nouveau.getId()+"crée");
                }
                
            }catch (SQLException ex){
                System.out.println(ExceptionsUtils.messageEtPremiersAppelsDansPackage(ex,"fr.insa.rochette",20));
            } 
        }
    }

 

    @Override
    public int hashCode() {
        if (this.id ==-1){
            return super.hashCode();
        }else{
            return this.id;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilisateur other = (Utilisateur) obj;
        if(this.id == -1 || other.id ==-1){
            return false;
        }else {
            return this.id == other.id;
        }
        
    }
    
    
    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", login=" + login + ", password=" + password + ", description=" + description + ", idrole=" + idrole + '}';
    }

    public String getNomRole(){
        return this.role.getNom();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdrole() {
        return idrole;
    }

    public void setIdrole(int idrole) {
        this.idrole = idrole;
    }
    
  
}  
    

