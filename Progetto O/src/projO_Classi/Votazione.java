package projO_Classi;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

// imports interni
import projO_Connettività.MySQlConnection;
// </editor-fold>


/**
 *
 * @author Team
 */
public class Votazione {    
    private static String idVotazione;
    private static final DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
    private static Calendar dataCorrente;
    private static Calendar dataInizioVot;
    private static Calendar dataFineVot;
    private static MySQlConnection mysql = new MySQlConnection();
    private static int affluenza = 0;
    private static String winner = "";
    private static int lenghtEle;

    public static boolean VotazioneAperta = false;
    
    static INIFile myINI = new INIFile(Utility.INI_PATH);
        
    
    private Votazione(){} // Costruttore Privato in Quanto Classe di Metodi Statici
   
//______________________________________________________________________________    
    // Metodi Getter 
    
    /**
     *
     * @return idVotazione
     */


    public static String getIdVotazione() {    
        String res = myINI.getStringProperty("Votazione","ID");
        return res;
    }
    
    public static String readDataCorrente() {
        String res = myINI.getStringProperty("Votazione","DataCorrente");
        return res;
    }

//______________________________________________________________________________    
    
    /**
     *
     * @param _idVotazione
     * @param dataFine
     */

    
   public static void inizioVotazione(String _idVotazione, String dataFine) { // il costruttore di N_TURNO crea una tabella nel db, rileva la data corrente e definisce lo stato interno
        idVotazione = _idVotazione; // Nome Tabella (quindi N_TURNO)

        VotazioneAperta = true; 
        try {   
            int res = mysql.UpdateQuery("CREATE TABLE "+ idVotazione + " (Data VARCHAR(45) NULL DEFAULT NULL, Affluenza INT NULL DEFAULT 0, PRIMARY KEY (Data))");
                       if (res == 0 ) {
                           System.out.println("Errore Query");
                        }
            Calendar cal = Calendar.getInstance();
            System.out.println(cal.toString());
            dataCorrente = cal;

            dataInizioVot = dataCorrente;
            
            cal.setTime(f.parse(dataFine));
            dataFineVot = cal;
            
            lenghtEle = dataFineVot.get(java.util.Calendar.DAY_OF_YEAR)-dataFineVot.get(java.util.Calendar.DAY_OF_YEAR);
            
            winner = "";
        }catch(Exception ex){
            ex.printStackTrace();
        }   
    }    
//__________________________________________________________________________________________________________________________________________ 

    public static Calendar getDataCorrente() {
        return dataCorrente;
    }

    public static Calendar getDataFineVot() {
        return dataFineVot;
    }

    public static DateFormat getF() {
        return f;
    }

    public static int getlenghtEle(){
        return lenghtEle;
    }

    public static Calendar getDataInizioVot() {
        return dataInizioVot;
    }
    
    
/*    public static int getAffluenza(){
        
        
        
        
        
        return 
    }
*/        
   
    /**
     * Metodo per Chiudere il Turno di Votazioni
     */
    public static void chiudiVotazione() {       
        resetVoti();
        winner = findWinner();
        VotazioneAperta = false; 
    }
//__________________________________________________________________________________________________________________________________________      

    /**
     * Metodo che Incrementa il numero dei voti nella giornata corrente, nella tabella PRIMO TURNO(idVotazione) - chiamato da clientGUI
     */
    public static void addAffluenza() { 
        affluenza++;     
    }
//__________________________________________________________________________________________________________________________________________  

    /**
     * Metodo per effettuare Avanzamento della Giornata
     */
    public static void AvanzaGiornata() { // incrementa la data corrente. Questo verrà chiamato dal Bottone AvanzaGiorno   
        // Update dell'Attributo AFFLUENZA e Azzeramento

        try {

            mysql.UpdateQuery( "INSERT INTO db." + getIdVotazione() + " (Data,Affluenza) VALUES ('" + readDataCorrente() + "', " + affluenza +");" );
             //mysql.UpdateQuery("UPDATE db."+ idVotazione + "SET Affluenza=" + affluenza + " WHERE Data=" + dataCorrente + ";");
        } catch (Exception ex) {
            Logger.getLogger(Votazione.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        affluenza = 0;
        
        // Incrementa la data
        dataCorrente.add(Calendar.DATE, 1); 
}
//______________________________________________________________________________
    /**
     * Metodo per trovare il Vincitore una volta chiuse le Elezioni
     */
    private static String findWinner() {       // chiamato a votazione finita: trova nel db il candidato vincitore
        return ""; // ritorna il codice fiscale del vincitore
    }
    
//______________________________________________________________________________
    /**
     * Metodo per effettuare il Reset dei Voti nel DataBase
     */
    private static void resetVoti() {      // setta tutti i voti nella tabella votanti a 0. Private perchè viene usato solo in questa classe
        ArrayList<Persone> pers = mysql.ReadPersoneColumns();
        for (Persone obj: pers) {
            mysql.UpdateQuery("UPDATE VOTANTI SET Voti=0 WHERE CodiceFiscale='" + obj.getCF() + "';");
        }   
    }   
}
