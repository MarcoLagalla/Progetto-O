/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o;

import java.sql.*;
import java.util.ArrayList;
import java.net.URL;


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
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    /**
     * Stringa (costante) che identifica l' indirizzo del database
     * per questo progetto si è scelto di utilizzare una base di dati
     * remota montata su una VPS all' indirizzo specificato
     * @see MySQlConnection#MySQlConnection() 
     */
    static final String DB_URL = "jdbc:mysql://91.134.138.244/db"; // 91.134.138.244 remote url db and 'db' database name
    
    /**
     * Stringa che contiene l' username associato alla connesione
     * @see MySQlConnection#MySQlConnection() 
     * @see MySQlConnection#password
     */
    // database connection
    static final String username = "root";
     /**
     * Stringa che contiene la password associata alla connesione
     * @see MySQlConnection#MySQlConnection() 
     * @see MySQlConnection#username
     */
    static final String password = "progettoO";
    
    // mysql objects for connection and query
    /**
     * Oggetto Connection per la connessione
     * @see java.sql.Connection
     */
    Connection conn = null;
    /**
     * Oggetto Statement per l' invio delle query SQL
     * @see java.sql.Statement
     */
    Statement stmt = null;
    /**
     * Oggetto ResultSet per il fetching dei dati
     * @see java.sql.ResultSet
     */
    ResultSet res = null;
    
    
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
    static final String TABCODICEFISCALE = "CodiceFiscale";
    static final String TABNOME = "Nome";
    static final String TABCOGNOME = "Cognome";
    static final String TABSESSO = "Sesso";
    static final String TABDATANASCITA = "DataNascita";
    static final String TABCOMUNE = "Comune";
    static final String TABCODICETESSERA = "CodiceTessera";
    static final String TABPARTITO = "Partito";
    static final String TABVOTI = "Voti";
    static final String TABIMMAGINE = "Immagine";
    
    
    /**
     * Metodo costruttore, inizializza la connessione e il caricamento del driver
     * @see MySQlConnection#JDBC_DRIVER
     * @see MySQlConnection#MySQlConnectionClose() 
     */
    public MySQlConnection() {
        try{ 
            
        Class.forName(JDBC_DRIVER); // register JDBC driver
        conn = DriverManager.getConnection(DB_URL,username,password);
       
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo che chiude la connessione e rilascia tutte le risorse utilizzate
     * @see MySQlConnection#MySQlConnection() 
     */
    // close connection
    public void MySQlConnectionClose(){
        try{
           conn.close();
           stmt.close();
           res.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }
    }    
    
    /**
     * Funzione che legge tutti i campi della tabella PERSONE nel db e restituisce 
     * un ArrayList di oggetti 'persone' per ogni record estratto.
     * @return Oggetto della classe persone
     */
    // submit a query
    public  ArrayList<persone> ReadPersoneColumns() {
       
        String QUERY = String.format("SELECT * FROM %s;" , DB_PERSONE );
        ArrayList<persone> pers = new ArrayList();
        try{
            
            stmt = conn.createStatement();
            res = stmt.executeQuery(QUERY); //submit query    
            
            while(res.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                persone p;
          
                String CF = res.getString(TABCODICEFISCALE);
                String Nome = res.getString(TABNOME);
                String Cognome = res.getString(TABCOGNOME);
                String Sesso = res.getString(TABSESSO);
                String DataNascita = res.getString(TABDATANASCITA);
                String Comune = res.getString(TABCOMUNE);
                
                p = new persone(CF, Nome, Cognome, Sesso, DataNascita, Comune);
                pers.add(p); // inserimento nell' ArrayList del nuovo record p
              
            }
            
        }catch(SQLException se){
            se.printStackTrace();   
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }finally{
            try {
                // rilascia le risorse
            stmt.close();   
            res.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        
        return pers;
    }
    
     /**
     * Funzione che legge tutti i campi della tabella VOTANTI nel db e restituisce 
     * un ArrayList di oggetti 'votanti' per ogni record estratto.
     * @return Oggetto della classe persone
     */
    // submit a query
    public ArrayList<votanti> ReadVotantiColumns() {
       
        
        String QUERY = "SELECT PERSONE.CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune, CodiceTessera FROM db.PERSONE\n" +
                       "JOIN db.VOTANTI on PERSONE.CodiceFiscale = VOTANTI.CodiceFiscale;";
        
        ArrayList<votanti> vot = new ArrayList();
        try
        {
            
            stmt = conn.createStatement();
            res = stmt.executeQuery(QUERY); //submit query    
            
            while(res.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                votanti v;

                String CF = res.getString(TABCODICEFISCALE);
                String Nome = res.getString(TABNOME);
                String Cognome = res.getString(TABCOGNOME);
                String Sesso = res.getString(TABSESSO);
                String DataNascita = res.getString(TABDATANASCITA);
                String Comune = res.getString(TABCOMUNE);
                String CodiceTessera = res.getString(TABCODICETESSERA);
               
                v = new votanti(CF,Nome, Cognome, Sesso, DataNascita, Comune, CodiceTessera);
                vot.add(v);

            }
            
        }catch(SQLException se){
            se.printStackTrace();   
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }finally{
            try {
                // rilascia le risorse
            stmt.close();   
            res.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        
        return vot;
    }
    
    
     /**
     * Funzione che legge tutti i campi della tabella CANDIDATI nel db e restituisce 
     * un ArrayList di oggetti 'candidati' per ogni record estratto.
     * @return Oggetto della classe persone
     */
    // submit a query
    public ArrayList<candidati> ReadCandidatiColumns() {
       
        String QUERY = "SELECT PERSONE.CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune, Partito, Voti, Immagine FROM db.PERSONE\n" +
                       "JOIN db.CANDIDATI on PERSONE.CodiceFiscale = CANDIDATI.CodiceFiscale;";
        
        ArrayList<candidati> can = new ArrayList();
        try
        {
            
            stmt = conn.createStatement();
            res = stmt.executeQuery(QUERY); //submit query    
            
            while(res.next()){  // cicla fino a che esiste una nuova riga da leggere
                
                candidati c;

                String CF = res.getString(TABCODICEFISCALE);
                String Nome = res.getString(TABNOME);
                String Cognome = res.getString(TABCOGNOME);
                String Sesso = res.getString(TABSESSO);
                String DataNascita = res.getString(TABDATANASCITA);
                String Comune = res.getString(TABCOMUNE);
                String Partito = res.getString(TABPARTITO);
                int Voti = res.getInt(TABVOTI);
                URL Immagine = res.getURL(TABIMMAGINE);
                c = new candidati(CF,Nome, Cognome, Sesso, DataNascita, Comune, Partito, Voti, Immagine);
                can.add(c);

            }
            
        }catch(SQLException se){
            se.printStackTrace();   
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }finally{
            try {
                // rilascia le risorse
            stmt.close();   
            res.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
       
        return can;
    }

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
            stmt = conn.createStatement();
            res = stmt.executeQuery(QUERY); //submit query   
        }catch(SQLException se){
            se.printStackTrace();
        }
        return res;
    }
    
    /**
     * Metodo che popola la tabella CANDIDATI
     * @param CodiceFiscale Codice Fiscale del candidato
     * @param Partito Partito di appartenenza
     * @param Voti Numero Voti
     * @param Immagine URL Immagine profilo
     */
    public void WriteCandidatiColumns(String CodiceFiscale, String Partito, String Voti, String Immagine) {
        String QUERY = "INSERT INTO CANDIDATI (CodiceFiscale, Partito, Voti, Immagine)\n" +
                "VALUES (" + CodiceFiscale + "," + Partito + "," + Voti + "," + Immagine + ");";
              
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery(QUERY);
        }catch(SQLException se) {
            se.printStackTrace();
        }finally{
            try {
                stmt.close();
                res.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            
        }
        
    }
    
        /**
     * Metodo che popola la tabella PERSONE
     * @param CodiceFiscale Codice Fiscale della persona
     * @param Nome nome persona
     * @param Cognome cognome persona
     * @param Sesso sesso persona
     * @param DataNascita data di nascita
     * @param Comune comune di orgine
     */
    public void WritePersoneColumns(String CodiceFiscale, String Nome, String Cognome, String Sesso, String DataNascita, String Comune) {
        String QUERY = "INSERT INTO PERSONE (CodiceFiscale, Nome, Cognome, Sesso, DataNascita, Comune)\n" +
                "VALUES (" + CodiceFiscale + "," + Nome + "," + Cognome + "," + Sesso + "," + DataNascita + "," + Comune + ");";
              
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery(QUERY);
        }catch(SQLException se) {
            se.printStackTrace();
        }finally{
            try {
                stmt.close();
                res.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            
        }
        
    }
    
    /**
     * Metodo che inserisce il codice tessera elettorale di chi ha votato nella rispettiva colonna indicante la votazione
     * Questo sistema impedisce voti multipli dalla stessa persona.
     * 
     * @param Votazione Identifica un turno elettorale o una votazione
     * @param CodiceTessera Identifica in maniera univoca una persona e tiene traccia dell' avvenuto voto.
     */
    public void WriteVotazioniColumns(String Votazione, String CodiceTessera) {
        String QUERY = "INSERT INTO VOTAZIONI (" + Votazione +  ") VALUES (" + CodiceTessera + ");";
              
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery(QUERY);
        }catch(SQLException se) {
            se.printStackTrace();
        }finally{
            try {
                stmt.close();
                res.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            
        }
        
    }
    
    
}
