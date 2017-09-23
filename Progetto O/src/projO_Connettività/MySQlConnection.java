/** 
 *  Classe per la gestione di una connessione ad un database MySQL
 *  La classe utilizza i driver JDBC per gestire la connessione.
 */

package projO_Connettività;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.util.ArrayList;

// imports per database
import java.sql.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

// imports interni
import projO_Classi.Persone;
import projO_Classi.Candidati;
import projO_Classi.Votanti;
// </editor-fold>

//______________________________________________________________________________
public class MySQlConnection {
    
    /**
     * Stringa (costante) di localizzazione del driver JDBC
     * @see MySQlConnection#MySQlConnection() 
     */
    // JDBC Driver and db URL
    static final String JDBCDRIVER = "com.mysql.jdbc.Driver";
    /**
     * Stringa (costante) che identifica l' indirizzo del database
     * per questo progetto si è scelto di utilizzare una base di dati
     * remota montata su una VPS all' indirizzo specificato
     * @see MySQlConnection#MySQlConnection() 
     */
    static final String DBURL = "jdbc:mysql://91.134.138.244/db"; // 91.134.138.244 remote url db and 'db' database name
    
    /**
     * Stringa che contiene l' USERNAME associato alla connesione
     * @see MySQlConnection#MySQlConnection() 
     * @see MySQlConnection#PASSWORD
     */
    // database connection
    static final String USERNAME = "root";
     /**
     * Stringa che contiene la PASSWORD associata alla connesione
     * @see MySQlConnection#MySQlConnection() 
     * @see MySQlConnection#USERNAME
     */
    static final String PASSWORD = "progettoO";
    
    // mysql objects for connection and query
    /**
     * Oggetto Connection per la connessione
     * @see java.sql.Connection
     */
    Connection objConnessione = null;
    /**
     * Oggetto Statement per l' invio delle query SQL
     * @see java.sql.Statement
     */
    Statement statement = null;
    /**
     * Oggetto ResultSet per il fetching dei dati
     * @see java.sql.ResultSet
     */
    ResultSet resultSet = null;
    
//______________________________________________________________________________
    
   /*
     Costanti con i nomi delle tabelle della base di dati
    */
    
    static final String DB_PERSONE = "PERSONE";
    static final String DB_VOTANTI = "VOTANTI";
    static final String DB_CANDIDATI = "CANDIDATI";
    static final String DB_VOTAZIONI = "VOTAZIONI";
   
    
    /*
        Costanti con i nomi delle colonne della base di dati
    */
    static final String TAB_CODICEFISCALE = "CodiceFiscale";
    static final String TAB_NOME = "Nome";
    static final String TAB_COGNOME = "Cognome";
    static final String TAB_SESSO = "Sesso";
    static final String TAB_DATANASCITA = "DataNascita";
    static final String TAB_COMUNE = "Comune";
    static final String TAB_CODICETESSERA = "CodiceTessera";
    static final String TAB_PARTITO = "Partito";
    static final String TAB_VOTI = "Voti";
    static final String TAB_IMMAGINE = "Immagine";
    static final String TAB_FLAGVOTATO = "FlagVotato";
    
//______________________________________________________________________________
    
    /**
     * Metodo costruttore, inizializza la connessione e il caricamento del driver
     * @see MySQlConnection#JDBCDRIVER
     * @see MySQlConnection#close() 
     */
    public MySQlConnection() {
        try{ 
            
        Class.forName(JDBCDRIVER); // register JDBC driver
        objConnessione = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
       
        }catch(SQLException | ClassNotFoundException se){
        }
    }
 
//______________________________________________________________________________
    
    /**
     * Metodo che chiude la connessione e rilascia tutte le risorse utilizzate
     * @see MySQlConnection#MySQlConnection() 
     */
    // close connection
    public void close(){
        try{
           objConnessione.close();
           statement.close();
           resultSet.close();
        }catch(SQLException se){
        }catch(Exception e){
            //handles error for Class.forName
                    }
    }    

//______________________________________________________________________________
    
    /**
     * Funzione che legge tutti i campi della tabella PERSONE nel db e restituisce un ArrayList di oggetti 'Persone' per ogni record estratto.                                    
     * @return Oggetto della classe Persone
     */

    public  ArrayList<Persone> readPersoneColumns() {
       
        String QUERY = String.format("SELECT * FROM %s;" , DB_PERSONE );
        ArrayList<Persone> pers = new ArrayList();
        try{
            
            statement = objConnessione.createStatement();
            resultSet = statement.executeQuery(QUERY); //submit query    
            
            while(resultSet.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                Persone p;
          
                String CF = resultSet.getString(TAB_CODICEFISCALE);
                String Nome = resultSet.getString(TAB_NOME);
                String Cognome = resultSet.getString(TAB_COGNOME);
                String Sesso = resultSet.getString(TAB_SESSO);
                String DataNascita = resultSet.getString(TAB_DATANASCITA);
                String Comune = resultSet.getString(TAB_COMUNE);
                
                p = new Persone(CF, Nome, Cognome, Sesso, DataNascita, Comune);
                pers.add(p); // inserimento nell' ArrayList del nuovo record p
              
            }
            
        }catch(SQLException se){
        }catch(Exception e){
            //handles error for Class.forName
                    }finally{
            try {
                // rilascia le risorse
            statement.close();   
            resultSet.close();
            } catch (SQLException se) {
            }
        }
        
        return pers;
    }

//______________________________________________________________________________
    
    /**
     * Funzione che legge tutti i campi della tabella VOTANTI nel db e restituisce un ArrayList di oggetti 'Votanti' per ogni record estratto.
     * @return Oggetto della classe Persone
     */

    public ArrayList<Votanti> readVotantiColumns() {
       
        
        String QUERY = "SELECT PERSONE.CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune, CodiceTessera, FlagVotato FROM db.PERSONE\n" +
                       "JOIN db.VOTANTI on PERSONE.CodiceFiscale = VOTANTI.CodiceFiscale;";
        
        ArrayList<Votanti> vot = new ArrayList();
        try
        {
            
            statement = objConnessione.createStatement();
            resultSet = statement.executeQuery(QUERY); //submit query    
            
            while(resultSet.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                Votanti v;

                String CF = resultSet.getString(TAB_CODICEFISCALE);
                String Nome = resultSet.getString(TAB_NOME);
                String Cognome = resultSet.getString(TAB_COGNOME);
                String Sesso = resultSet.getString(TAB_SESSO);
                String DataNascita = resultSet.getString(TAB_DATANASCITA);
                String Comune = resultSet.getString(TAB_COMUNE);
                String CodiceTessera = resultSet.getString(TAB_CODICETESSERA);
                int FlagVotato = resultSet.getInt(TAB_FLAGVOTATO);
                
                v = new Votanti(CF,Nome, Cognome, Sesso, DataNascita, Comune, CodiceTessera, FlagVotato);
                vot.add(v);

            }
            
        }catch(SQLException se){
            se.printStackTrace();   
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }finally{
            try {
            // rilascia le risorse
            statement.close();   
            resultSet.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        
        return vot;
    }
    
//______________________________________________________________________________   
     /**
     * Funzione che legge tutti i campi della tabella CANDIDATI nel db e restituisce un ArrayList di oggetti 'Candidati' per ogni record estratto.
     * @return Oggetto della classe Persone
     */

    public ArrayList<Candidati> readCandidatiColumns() {
       
        String QUERY = "SELECT PERSONE.CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune, Partito, Voti, Immagine FROM db.PERSONE\n" +
                       "JOIN db.CANDIDATI on PERSONE.CodiceFiscale = CANDIDATI.CodiceFiscale;";
        
        ArrayList<Candidati> can = new ArrayList();
        try
        {
            
            statement = objConnessione.createStatement();
            resultSet = statement.executeQuery(QUERY); //submit query    
            
            while(resultSet.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                Candidati c;

                String CF = resultSet.getString(TAB_CODICEFISCALE);
                String Nome = resultSet.getString(TAB_NOME);
                String Cognome = resultSet.getString(TAB_COGNOME);
                String Sesso = resultSet.getString(TAB_SESSO);
                String DataNascita = resultSet.getString(TAB_DATANASCITA);
                String Comune = resultSet.getString(TAB_COMUNE);
                String Partito = resultSet.getString(TAB_PARTITO);
                int Voti = resultSet.getInt(TAB_VOTI);
                URL Immagine = resultSet.getURL(TAB_IMMAGINE);
                c = new Candidati(CF,Nome, Cognome, Sesso, DataNascita, Comune, Partito, Voti, Immagine);
                can.add(c);

            }
            
        }catch(SQLException se){
            se.printStackTrace();   
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }finally{
            try {
                // rilascia le risorse
            statement.close();   
            resultSet.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
       
        return can;
    }
    
//______________________________________________________________________________
    
    /**
     * Esegue una query SQL generica
     * @param query contiene la query da eseguire
     * @return Restituisce un oggetto ResultSet
     */
    public ResultSet executeQuery(String query) {
        try {
            
            if (!query.endsWith(";")) {  // se la query non termina con ; lo aggiungo
                query = query.concat(";"); 
            }
            statement = objConnessione.createStatement();
            resultSet = statement.executeQuery(query); //submit query   
        }catch(SQLException se){
            se.printStackTrace();
        }
        return resultSet;
    }

//______________________________________________________________________________
    
    /**
     *
     * @param query
     * @return
     */
    public int updateQuery(String query) {
        try {
            if (!query.endsWith(";")) {
                query = query.concat(";");
            }
            statement = objConnessione.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return 0;
    }

//______________________________________________________________________________
    
    /**
     * Metodo che popola la tabella CANDIDATI
     * @param codiceFiscale Codice Fiscale del candidato
     * @param partito Partito di appartenenza
     * @param voti Numero Voti
     * @param immagine URL Immagine profilo
     * @return 
     */
    public int writeCandidatiColumns(String codiceFiscale, String partito, int voti, String immagine) {
        String query = "INSERT INTO CANDIDATI (CodiceFiscale, Partito, Voti, Immagine)\n" +
                "VALUES ('" + codiceFiscale + "','" + partito + "'," + voti + ",'" + immagine + "');";

        try {
            statement = objConnessione.createStatement();
           return statement.executeUpdate(query);
        }catch(SQLException se) {
        }finally{
            try {
                statement.close();
            }catch(SQLException se){
            }
            
        }
        return 0;
    }

//______________________________________________________________________________
    
    /**
     * Metodo che popola la tabella PERSONE
     * @param codiceFiscale Codice Fiscale della persona
     * @param nome nome persona
     * @param cognome cognome persona
     * @param sesso sesso persona
     * @param dataNascita data di nascita
     * @param comune comune di orgine
     * @return 
     */
    public int writePersoneColumns(String codiceFiscale, String nome, String cognome, String sesso, String dataNascita, String comune) {
        String query = "INSERT INTO PERSONE (CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune)\n" +
                "VALUES ('" + codiceFiscale + "','" + nome + "','" + cognome + "','" + sesso + "','" + dataNascita + "','" + comune + "');";
              
        try {
            statement = objConnessione.createStatement();
            return statement.executeUpdate(query);
        }catch(SQLException se) {
        }finally{
            try {
                statement.close();
            }catch(SQLException se){
            }
            
        }
        return 0;
    }

//______________________________________________________________________________
    
    /**
     * Metodo che inserisce il codice tessera elettorale di chi ha votato nella rispettiva colonna indicante la votazione
     * Questo sistema impedisce voti multipli dalla stessa persona.
     * 
     * @param votazione Identifica un turno elettorale o una votazione
     * @param codiceTessera Identifica in maniera univoca una persona e tiene traccia dell' avvenuto voto.
     * @return 
     */
    public int writeVotazioniColumns(String votazione ,String codiceTessera) {
        String QUERY = "INSERT INTO VOTAZIONI (" + votazione +  ") VALUES ('" + codiceTessera + "');";
              
        try {
            statement = objConnessione.createStatement();
            return statement.executeUpdate(QUERY);
        }catch(SQLException se) {
        }finally{
            try {
                statement.close();
            }catch(SQLException se){
            }
            
        }
        return 0;
    }   
    
    /**
     *
     * @param tableName
     * @return
     */
    public int countRows(String tableName) {
        String query = "select count(*) from " + tableName + ";" ;
        try {
                statement = objConnessione.createStatement();
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                return resultSet.getInt(1);
 }
        } catch (SQLException se) {}
        return 0;
    }
}
