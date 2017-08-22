/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o.Classi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import progetto.o.Connettività.MySQlConnection;

public class Votazione {

/*____________________________________STATO INTERNO________________________________________*/
    
    private static String idVotazione;
    private static final DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
    private static Calendar dataCorrente;
    private static Calendar dataInizioVot;
    private static Calendar dataFineVot;
    private static MySQlConnection mysql = new MySQlConnection();
    private static int affluenza = 0;
    private static String winner = "";
 
/*____________________________________COSTRUTTORI__________________________________________*/
    
    public static boolean VotazioneAperta = false;
    
    private Votazione(){} // Costruttore Privato in Quanto Classe di Metodi Statici
    
/*____________________________________METODI GET SET________________________________________*/

    public static String getIdVotazione() {
        return idVotazione;
    }

    public static Calendar getDataInizioVot() {
        return dataInizioVot;
    }
    
/*_______________________________________METODI____________________________________________*/
    
   public static void inizioVotazione(String _idVotazione, String dataFine) { // il costruttore di N_TURNO crea una tabella nel db, rileva la data corrente e definisce lo stato interno
        idVotazione = _idVotazione; // Nome Tabella (quindi N_TURNO)
         VotazioneAperta = true; 
        try {   
            int res = mysql.UpdateQuery("CREATE TABLE `db`.`" + idVotazione+ "` ( ` Data` VARCHAR(45) NULL DEFAULT NULL,`Affluenza` INT NULL DEFAULT 0, PRIMARY KEY (`Data`)) ENGINE = InnoDB DEFAULT CHARACTER SET = latin1;");
                       if (res == 0 ) {
                           System.out.println("Errore Query");
                       }
            Calendar cal = Calendar.getInstance();
            String data = f.format(cal);
            dataCorrente = cal;
            
            dataInizioVot = dataCorrente;
            cal.setTime(f.parse(dataFine));
            dataFineVot = cal;
            
            winner = "";
        } catch (Exception ex) {ex.printStackTrace();}   
    }    
//__________________________________________________________________________________________________________________________________________ 
    public static void chiudiVotazione() { // chiude il turno delle votazioni.       
        resetVoti();
        winner = findWinner();
        VotazioneAperta = false; 
    }
//__________________________________________________________________________________________________________________________________________      
    public static void addAffluenza() { // incrementa il numero dei voti nella giornata corrente, nella tabella PRIMO TURNO(idVotazione) - chiamato da clientGUI
        affluenza++;     
    }
//__________________________________________________________________________________________________________________________________________  
    public static void AvanzaGiornata() { // incrementa la data corrente. Questo verrà chiamato dal Bottone AvanzaGiorno
        
        // Update dell'Attributo AFFLUENZA e Azzeramento
        try {
            mysql.UpdateQuery("UPDATE db."+ idVotazione + "SET Affluenza=" + affluenza + ";");
        } catch (Exception ex) {
            Logger.getLogger(Votazione.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        affluenza = 0;
        
        // Incrementa la data
        dataCorrente.add(Calendar.DATE, 1); 
        
        // Aggiungi una riga in N_TURNO, con la data corrente (cioè di domani)
        mysql.ExecuteQuery( "INSERT INTO 'db'." + idVotazione + "' ('Data','Affluenza') VALUES ('" + dataCorrente + "', '0');" );
    }
//__________________________________________________________________________________________________________________________________________
    private static String findWinner() {       // chiamato a votazione finita: trova nel db il candidato vincitore
        return ""; // ritorna il codice fiscale del vincitore
    }
    
//__________________________________________________________________________________________________________________________________________
    private static void resetVoti() {      // setta tutti i voti nella tabella votanti a 0. Private perchè viene usato solo in questa classe
            ArrayList<persone> pers = mysql.ReadPersoneColumns();
            for (persone obj: pers) {
                mysql.UpdateQuery("UPDATE VOTANTI SET Voti=0 WHERE CodiceFiscale='" + obj.getCF() + "';");
            }   
    }
    
}
