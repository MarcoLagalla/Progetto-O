package projO_Connettività;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.util.ArrayList;

// imports per database
import java.sql.*;
import java.net.URL;

// imports interni
import projO_Classi.Persone;
import projO_Classi.Candidati;
import projO_Classi.Votanti;
// </editor-fold>

//______________________________________________________________________________

/** 
 *  Classe per la gestione di una connessione ad un database MySQL
 *  La classe utilizza i driver JDBC per gestire la connessione.
 */
public class MySQlConnection {
    
    /**
     * Stringa (costante) di localizzazione del driver JDBC
     * @see MySQlConnection#MySQlConnection() 
     */
    // JDBC Driver and db URL
    static final String jdbcDriver = "com.mysql.jdbc.Driver";
    /**
     * Stringa (costante) che identifica l' indirizzo del database
     * per questo progetto si è scelto di utilizzare una base di dati
     * remota montata su una VPS all' indirizzo specificato
     * @see MySQlConnection#MySQlConnection() 
     */
    static final String dbUrl = "jdbc:mysql://91.134.138.244/db"; // 91.134.138.244 remote url db and 'db' database name
    
    /**
     * Stringa che contiene l' userName associato alla connesione
     * @see MySQlConnection#MySQlConnection() 
     * @see MySQlConnection#password
     */
    // database connection
    static final String userName = "root";
     /**
     * Stringa che contiene la password associata alla connesione
     * @see MySQlConnection#MySQlConnection() 
     * @see MySQlConnection#userName
     */
    static final String password = "progettoO";
    
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
    
    static final String dbPERSONE = "PERSONE";
    static final String dbVOTANTI = "VOTANTI";
    static final String dbCANDIDATI = "CANDIDATI";
    static final String dbVOTAZIONI = "VOTAZIONI";
   
    
    /*
        Costanti con i nomi delle colonne della base di dati
    */
    static final String tabCodiceFiscale = "CodiceFiscale";
    static final String tabNome = "Nome";
    static final String tabCognome = "Cognome";
    static final String tabSesso = "Sesso";
    static final String tabDataDiNascita = "DataNascita";
    static final String tabComune = "Comune";
    static final String tabCodiceTessera = "CodiceTessera";
    static final String tabPartito = "Partito";
    static final String tabVoti = "Voti";
    static final String tabImmagine = "Immagine";
    static final String tabFlagVotato = "FlagVotato";
    
//______________________________________________________________________________
    
    /**
     * Metodo costruttore, inizializza la connessione e il caricamento del driver
     * @see MySQlConnection#jdbcDriver
     * @see MySQlConnection#MySQlConnectionClose() 
     */
    public MySQlConnection() {
        try{ 
            
        Class.forName(jdbcDriver); // register JDBC driver
        objConnessione = DriverManager.getConnection(dbUrl,userName,password);
       
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }
    }
 
//______________________________________________________________________________
    
    /**
     * Metodo che chiude la connessione e rilascia tutte le risorse utilizzate
     * @see MySQlConnection#MySQlConnection() 
     */
    // close connection
    public void MySQlConnectionClose(){
        try{
           objConnessione.close();
           statement.close();
           resultSet.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }
    }    

//______________________________________________________________________________
    
    /**
     * Funzione che legge tutti i campi della tabella PERSONE nel db e restituisce un ArrayList di oggetti 'Persone' per ogni record estratto.                                    
     * @return Oggetto della classe Persone
     */

    public  ArrayList<Persone> ReadPersoneColumns() {
       
        String QUERY = String.format("SELECT * FROM %s;" , dbPERSONE );
        ArrayList<Persone> pers = new ArrayList();
        try{
            
            statement = objConnessione.createStatement();
            resultSet = statement.executeQuery(QUERY); //submit query    
            
            while(resultSet.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                Persone p;
          
                String CF = resultSet.getString(tabCodiceFiscale);
                String Nome = resultSet.getString(tabNome);
                String Cognome = resultSet.getString(tabCognome);
                String Sesso = resultSet.getString(tabSesso);
                String DataNascita = resultSet.getString(tabDataDiNascita);
                String Comune = resultSet.getString(tabComune);
                
                p = new Persone(CF, Nome, Cognome, Sesso, DataNascita, Comune);
                pers.add(p); // inserimento nell' ArrayList del nuovo record p
              
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
        
        return pers;
    }

//______________________________________________________________________________
    
    /**
     * Funzione che legge tutti i campi della tabella VOTANTI nel db e restituisce un ArrayList di oggetti 'Votanti' per ogni record estratto.
     * @return Oggetto della classe Persone
     */

    public ArrayList<Votanti> ReadVotantiColumns() {
       
        
        String QUERY = "SELECT PERSONE.CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune, CodiceTessera, FlagVotato FROM db.PERSONE\n" +
                       "JOIN db.VOTANTI on PERSONE.CodiceFiscale = VOTANTI.CodiceFiscale;";
        
        ArrayList<Votanti> vot = new ArrayList();
        try
        {
            
            statement = objConnessione.createStatement();
            resultSet = statement.executeQuery(QUERY); //submit query    
            
            while(resultSet.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                Votanti v;

                String CF = resultSet.getString(tabCodiceFiscale);
                String Nome = resultSet.getString(tabNome);
                String Cognome = resultSet.getString(tabCognome);
                String Sesso = resultSet.getString(tabSesso);
                String DataNascita = resultSet.getString(tabDataDiNascita);
                String Comune = resultSet.getString(tabComune);
                String CodiceTessera = resultSet.getString(tabCodiceTessera);
                int FlagVotato = resultSet.getInt(tabFlagVotato);
                
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

    public ArrayList<Candidati> ReadCandidatiColumns() {
       
        String QUERY = "SELECT PERSONE.CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune, Partito, Voti, Immagine FROM db.PERSONE\n" +
                       "JOIN db.CANDIDATI on PERSONE.CodiceFiscale = CANDIDATI.CodiceFiscale;";
        
        ArrayList<Candidati> can = new ArrayList();
        try
        {
            
            statement = objConnessione.createStatement();
            resultSet = statement.executeQuery(QUERY); //submit query    
            
            while(resultSet.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                Candidati c;

                String CF = resultSet.getString(tabCodiceFiscale);
                String Nome = resultSet.getString(tabNome);
                String Cognome = resultSet.getString(tabCognome);
                String Sesso = resultSet.getString(tabSesso);
                String DataNascita = resultSet.getString(tabDataDiNascita);
                String Comune = resultSet.getString(tabComune);
                String Partito = resultSet.getString(tabPartito);
                int Voti = resultSet.getInt(tabVoti);
                URL Immagine = resultSet.getURL(tabImmagine);
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
     * @param QUERY contiene la query da eseguire
     * @return Restituisce un oggetto ResultSet
     */
    public ResultSet ExecuteQuery(String QUERY) {
        try {
            
            if (!QUERY.endsWith(";")) {  // se la query non termina con ; lo aggiungo
                QUERY = QUERY.concat(";"); 
            }
            statement = objConnessione.createStatement();
            resultSet = statement.executeQuery(QUERY); //submit query   
        }catch(SQLException se){
            se.printStackTrace();
        }
        return resultSet;
    }

//______________________________________________________________________________
    
    /**
     *
     * @param QUERY
     * @return
     */
    public int UpdateQuery(String QUERY) {
        try {
            if (!QUERY.endsWith(";")) {
                QUERY = QUERY.concat(";");
            }
            statement = objConnessione.createStatement();
            return statement.executeUpdate(QUERY);
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
     * @param CodiceFiscale Codice Fiscale del candidato
     * @param Partito Partito di appartenenza
     * @param Voti Numero Voti
     * @param Immagine URL Immagine profilo
     * @return 
     */
    public int WriteCandidatiColumns(String CodiceFiscale, String Partito, int Voti, String Immagine) {
        String QUERY = "INSERT INTO CANDIDATI (CodiceFiscale, Partito, Voti, Immagine)\n" +
                "VALUES ('" + CodiceFiscale + "','" + Partito + "'," + Voti + ",'" + Immagine + "');";

        try {
            statement = objConnessione.createStatement();
           return statement.executeUpdate(QUERY);
        }catch(SQLException se) {
            se.printStackTrace();
        }finally{
            try {
                statement.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            
        }
        return 0;
    }

//______________________________________________________________________________
    
    /**
     * Metodo che popola la tabella PERSONE
     * @param CodiceFiscale Codice Fiscale della persona
     * @param Nome nome persona
     * @param Cognome cognome persona
     * @param Sesso sesso persona
     * @param DataNascita data di nascita
     * @param Comune comune di orgine
     * @return 
     */
    public int WritePersoneColumns(String CodiceFiscale, String Nome, String Cognome, String Sesso, String DataNascita, String Comune) {
        String QUERY = "INSERT INTO PERSONE (CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune)\n" +
                "VALUES ('" + CodiceFiscale + "','" + Nome + "','" + Cognome + "','" + Sesso + "','" + DataNascita + "','" + Comune + "');";
              
        try {
            statement = objConnessione.createStatement();
            return statement.executeUpdate(QUERY);
        }catch(SQLException se) {
            se.printStackTrace();
        }finally{
            try {
                statement.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            
        }
        return 0;
    }

//______________________________________________________________________________
    
    /**
     * Metodo che inserisce il codice tessera elettorale di chi ha votato nella rispettiva colonna indicante la votazione
     * Questo sistema impedisce voti multipli dalla stessa persona.
     * 
     * @param Votazione Identifica un turno elettorale o una votazione
     * @param CodiceTessera Identifica in maniera univoca una persona e tiene traccia dell' avvenuto voto.
     * @return 
     */
    public int WriteVotazioniColumns(String Votazione, String CodiceTessera) {
        String QUERY = "INSERT INTO VOTAZIONI (" + Votazione +  ") VALUES ('" + CodiceTessera + "');";
              
        try {
            statement = objConnessione.createStatement();
            return statement.executeUpdate(QUERY);
        }catch(SQLException se) {
            se.printStackTrace();
        }finally{
            try {
                statement.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            
        }
        return 0;
    }   
    
    /**
     *
     * @param TableName
     * @return
     */
    public int CountRows(String TableName) {
        String QUERY = "select count(*) from " + TableName + ";" ;
        try {
                statement = objConnessione.createStatement();
                resultSet = statement.executeQuery(QUERY);
                while (resultSet.next()) {
                return resultSet.getInt(1);
 }
        } catch (SQLException se) {}
        return 0;
    }
}
