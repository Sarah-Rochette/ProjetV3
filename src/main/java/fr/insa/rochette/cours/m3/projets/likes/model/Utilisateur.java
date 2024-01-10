/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package fr.insa.rochette.cours.m3.projets.likes.model;

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

    private Utilisateur(int id, String login, String password, String description, int idrole) {
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
    
    public List<Utilisateur> apprecie(ConnectionSGBD connSGBD) throws SQLException{
        return cherche(connSGBD,"select id,login,password,description,idrole from utilisateur join apprecie on apprecie.u2=utilisateur.id where apprecie.u1 = ?" );
    }
    
    public List<Utilisateur> appreciePar(ConnectionSGBD connSGBD) throws SQLException{
        return cherche(connSGBD,"select id,login,password,description,idrole from utilisateur join apprecie on apprecie.u1=utilisateur.id where apprecie.u2 = ?" );
    }
    /**
     * {@code u1 ami u2 <==> u1 appreci u2 et u2 apprecie u1}
     * @param connSGBD
     * @return
     * @throws SQLException 
     */
    
    public List<Utilisateur> amis(ConnectionSGBD connSGBD) throws SQLException{
        return cherche(connSGBD,"select id,login,password,description,idrole from apprecie as a1 join apprecie as a2 on a1.u2=a2.u1 join utilisateur on utilisateur.id = a1.u2 where a1.u1= ? and a1.u1 = a2.u2" );
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
            
            
    public static void menuUtilisateur(ConnectionSGBD connSGBD){
        int rep=-1;
        while (rep!=0){
            int i=1;
            System.out.println("Menu Utilisateur");
            System.out.println("===============");
            System.out.println((i++)+")lister tous les utilisateurs");
            System.out.println((i++)+")créer un nouvel utilisateur");
            System.out.println((i++)+")créer des utilisateurs test");
            System.out.println((i++)+")supprimer un utilisateur");
            System.out.println((i++)+")login");
            System.out.println("0) Fin");
            rep= ConsoleFdB.entreeEntier("Votre choix :");
            try{
                int j=1;
                if (rep==j++){
                    System.out.println(ListUtils.enumerateList(Utilisateur.tousLesUtilisateurs(connSGBD)));
                }else if (rep== j++){
                    Utilisateur nouveau = Utilisateur.demande(connSGBD);
                    nouveau.sauvegarde(connSGBD);
                    System.out.println("utilisateur N°"+ nouveau.getId()+"crée");
                }else if (rep== j++){
                    String noms = ConsoleFdB.entreeString("nom de base");
                    int nbr = ConsoleFdB.entreeInt("nombre d'utilisateur à créer");
                    Utilisateur.creeUtilisateursTest(connSGBD, noms, nbr);
                }else if (rep== j++){
                    Optional<Utilisateur> choix= ListUtils.selectOneOrCancel("--- selectionnez un utiliasteur à supprimer",
                            Utilisateur.tousLesUtilisateurs(connSGBD), Utilisateur::toString);
                    if (choix.isPresent()){
                        choix.get().delete(connSGBD);
                    }
                }else if (rep== j++){
                    String login= ConsoleFdB.entreeString("login:");
                    String pass = ConsoleFdB.entreeString("pass:");
                    Optional<Utilisateur> user = Utilisateur.login(connSGBD, login, pass);
                    if (user.isPresent()){
                        System.out.println("user" + user.get()+ "connected"); 
                        user.get().menuUtilisateurConnecte(connSGBD);
                    }else{
                        System.out.println("login ou pass incorrects");
                    }
                }
            }catch (SQLException ex){
                System.out.println(ExceptionsUtils.messageEtPremiersAppelsDansPackage(ex,"fr.insa.rochette",5));
            }
        }
    }

    public void menuUtilisateurConnecte(ConnectionSGBD connSGBD) throws SQLException{
        int rep=-1;
        while (rep!=0){
            int i=1;
            System.out.println("Utilisateur"+ this.login);
            System.out.println("=====================");
            System.out.println((i++)+")afficher les utilisateurs appréciés");
            System.out.println((i++)+")définir les utilisateurs appréciés");
            System.out.println((i++)+")afficher les utilisateurs qui m'apprécient");
            System.out.println((i++)+")afficher mes amis");
            System.out.println("0) Fin");
            rep= ConsoleFdB.entreeEntier("Votre choix :");
            try{
                int j=1;
                if (rep==j++){
                    List<Utilisateur> apprecies = this.apprecie(connSGBD);
                    System.out.println("--- utilisateurs appréciés");
                    
                    if (apprecies.isEmpty()){
                        System.out.println("Aucun");
                    }else{
                        System.out.println(ListUtils.enumerateList(apprecies));                        
                    }
                }else if (rep== j++){
                    List<Utilisateur> cur = this.apprecie(connSGBD);
                    List<Utilisateur> tous = Utilisateur.tousLesUtilisateurs(connSGBD);
                    tous.removeAll(cur);
                    List<Utilisateur> apprecies = ListUtils.selectMultiple(
                            "------ choisissez les utilisateurs que vous appreciez", this.apprecie(connSGBD), 
                            tous,
                            Utilisateur:: toString);
                    this.saveApprecies(connSGBD, apprecies);
                }else if (rep==j++){
                        List<Utilisateur> appreciePar = this.appreciePar(connSGBD);
                    System.out.println("--- utilisateurs qui m'apprécient");
                    
                    if (appreciePar.isEmpty()){
                        System.out.println("Aucun");
                    }else{
                        System.out.println(ListUtils.enumerateList(appreciePar));
                    }
                }else if (rep==j++){
                        List<Utilisateur> amis = this.amis(connSGBD);
                    System.out.println("--- mes amis");
                    
                    if (amis.isEmpty()){
                        System.out.println("Aucun");
                    }else{
                        System.out.println(ListUtils.enumerateList(amis));                        
                    }
            }
                
            }catch (SQLException ex){
                System.out.println(ExceptionsUtils.messageEtPremiersAppelsDansPackage(ex,"fr.insa.rochette",5));
            } 
        }
    }
    
    public void saveApprecies(ConnectionSGBD connSGBD, List<Utilisateur> apprecies) throws SQLException {
        Connection conn=connSGBD.getCon();
        conn.setAutoCommit(false);
        try (Statement st= conn.createStatement()){
            //...
            try(PreparedStatement supp = conn.prepareStatement(
            "delete from apprecie where u1 = ?")){
                supp.setInt(1, this.id);// on supprime tous les anciennes relations pour l'utilisateur courant
                supp.executeUpdate();
            }
            try(PreparedStatement ajout = conn.prepareStatement(
            "insert into apprecie (u1,u2) values (?,?)")){
                for (Utilisateur u2 : apprecies){
                    ajout.setInt(1,this.id);
                    ajout.setInt(2, u2.id);
                    ajout.executeUpdate();
                }
            }
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
