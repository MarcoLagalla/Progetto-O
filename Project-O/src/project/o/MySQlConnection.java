/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.o;
import java.sql.*;
import project.o.*;
import javax.swing.JOptionPane;
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
    
    
    
    // Numero massimo di persone gestibili
    static final int MAX = 1024;
    
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
    

    // submit a query
    public persone[] ReadPersoneColumns() {
       
        String QUERY = "SELECT * FROM PERSONE;";
        persone[] pers = new persone[MAX];
        try{
            
            stmt = conn.createStatement();
            res = stmt.executeQuery(QUERY); //submit query
            
            int i = 0;
            
            while(res.next()){
                
                persone p;
                int id = res.getInt("id");
                String CF = res.getString("CF");
                String Nome = res.getString("Nome");
                String Cognome = res.getString("Cognome");
                String Sesso = res.getString("Sesso");
                String DataNascita = res.getString("DataNascita");
                String Comune = res.getString("Comune");
                
                p = new persone(id, CF, Nome, Cognome, Sesso, DataNascita, Comune);
                pers[i] = p;
                i++;
            }
            
        }catch(SQLException se){
            se.printStackTrace();   
        }catch(Exception e){ //handles error for Class.forName
            e.printStackTrace();
        }
        
        return pers;
    }
    
    
}
