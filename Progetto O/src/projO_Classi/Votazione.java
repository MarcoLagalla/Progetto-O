package projO_Classi;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

// imports interni
import projO_Connettività.MySQlConnection;
import projO_Frames.ProgettoO;
import projO_Frames.ServerFrame;
// </editor-fold>


/**
 *
 * @author Team
 */
public class Votazione {    
    private static String idVotazione;
    private static final DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
    private static Calendar dataCorrente = Calendar.getInstance();
    private static Calendar dataInizioVot;
    private static Calendar dataFineVot;
    private static MySQlConnection mysql = new MySQlConnection();
    private static int affluenza = 0;
    private static String winner = "";
    private static int lenghtEle;

    public static boolean VotazioneAperta = false;
    
    static INIFile myINI;
        
    
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
        myINI = new INIFile(Utility.INI_PATH);
        if (!(existsVotazione(idVotazione))) { // controlla che esista la tabella

            VotazioneAperta = true; 
            try {   
                int res = mysql.UpdateQuery("CREATE TABLE " + idVotazione + " (Data VARCHAR(45) NULL DEFAULT NULL, Affluenza INT NULL DEFAULT 0, PRIMARY KEY (Data))");
                Calendar cal = Calendar.getInstance();
                dataCorrente = cal;
                dataInizioVot = dataCorrente;     
                dataFineVot = Calendar.getInstance();
                String[] parsing = dataFine.split("-");
                dataFineVot.set(Integer.parseInt(parsing[2]), Integer.parseInt(parsing[1]), Integer.parseInt(parsing[0]));
                ProgettoO.StatoVotazioni = true;
                
                // aggiornamento del file .INI               
                myINI.setBooleanProperty("Votazione", "VotazioneAperta", true, "VotazioneAperta");
                myINI.setStringProperty("Votazione", "ID", idVotazione, "ID" );
                myINI.setStringProperty("Votazione", "DataFine", dataFine, "DataFine" );
                myINI.setStringProperty("Votazione", "DataCorrente", f.format(dataCorrente.getTime()), "DataCorrente");
                myINI.setStringProperty("Votazione", "DataInizio", f.format(dataInizioVot.getTime()), "DataInizio");
                myINI.setIntegerProperty("Votazione", "AffluenzaOggi", 0 , "AffluenzaOggi");
                myINI.save();
                
                lenghtEle = dataFineVot.get(java.util.Calendar.DAY_OF_YEAR)-dataFineVot.get(java.util.Calendar.DAY_OF_YEAR);
            
                winner = "";
                
                // Resetta il campo FlagVotato nella tabella VOTANTI
                ArrayList<Votanti> vot = mysql.ReadVotantiColumns();
                for (Votanti obj: vot) {
                    mysql.UpdateQuery("UPDATE VOTANTI SET FlagVotato='0' WHERE CodiceFiscale='" + obj.getCF() + "';");        // setta il FlagVotato = 0 per tutti           
                }
                
                // Resetta i voti nella tabella CANDIDATI
                ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
                for (Candidati obj: can) {
                    mysql.UpdateQuery("UPDATE CANDIDATI SET Voti='0' WHERE CodiceFiscale='" + obj.getCF() + "';");        // setta i voti a 0           
                }                

            }catch(Exception ex){ ex.printStackTrace();}   
       } else { JOptionPane.showMessageDialog(null, "Errore, l' identificativo inserito non è ammissibile, provare un altro id.", "Errore", JOptionPane.ERROR_MESSAGE);}
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
    
    
 public static ArrayList<Integer> getAffluenza(){
     ArrayList<Integer> dati = new ArrayList();
     try {
         ResultSet res = mysql.ExecuteQuery("SELECT Affluenza from " + getIdVotazione() + ";");
         while (res.next()) {
             dati.add(res.getInt(1));
         }
     } catch (Exception ex) {}
     return dati;
}     
   
    /**
     * Metodo per Chiudere il Turno di Votazioni
     */
    public static void chiudiVotazione() {       
        resetVoti();
        ArrayList<String> vincitori = findWinner();
        if ( vincitori.size() > 1 ) {
            JOptionPane.showMessageDialog(null,"Necessario secondo turno!", "Attenzione", JOptionPane.WARNING_MESSAGE);
           int  res = JOptionPane.showConfirmDialog(null,"Vuoi procedere a impostare un secondo turno di elezioni?\nQuesta operazione cancellerà i candidati che non sono passati al secondo turno.\nQuesta operazione non può essere annullata.","ATTENZIONE", JOptionPane.YES_NO_OPTION);
           if ( res == JOptionPane.YES_OPTION ) {
               
               ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
               for (Candidati obj: can) {
                   if (!vincitori.contains(obj.getCF())) {
                       mysql.UpdateQuery("DELETE FROM db.CANDIDATI WHERE CodiceFiscale='" + obj.getCF() + "';");
                       
                   }
               }
               
           JOptionPane.showMessageDialog(null,"Per vedere le modifiche riavviare l' interfaccia di gestione.", "Necessario riavvio", JOptionPane.INFORMATION_MESSAGE);
           }
 
        }
        else if ( vincitori.size() == 1) {  // ho un vincitore con maggioranza assoluta
            JOptionPane.showMessageDialog(null,"Un candidato ha raggiunto la maggioranza assoluta!", "Elezione conclusa", JOptionPane.OK_OPTION);
            winner = vincitori.get(0).toString();

            
            ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
            
            for (Candidati obj: can) {
                
                if (obj.getCF().equals(winner)) {
                    
            ServerFrame.lb_FotoWinner.setIcon(setUrlIcon(obj.getImmagine().toString(), 150, 150)); 
            ServerFrame.lb_NomeVincitore.setText(obj.getNome());
            ServerFrame.lb_CognomeVincitore.setText(obj.getCognome());
            ServerFrame.lb_CF.setText(obj.getCF());
                }
            }
        }
        
        VotazioneAperta = false; 
    }
//__________________________________________________________________________________________________________________________________________      

    /**
     * Metodo che Incrementa il numero dei voti nella giornata corrente, nella tabella PRIMO TURNO(idVotazione) - chiamato da clientGUI
     */
    public static void addAffluenza() { 
                myINI = new INIFile(Utility.INI_PATH);
        int old = myINI.getIntegerProperty("Votazione", "AffluenzaOggi");
        old++; // aggiungo + 1 per l' affluenza
        myINI.setIntegerProperty("Votazione", "AffluenzaOggi", old, "AffluenzaOggi");
        myINI.save();
        
    }
//__________________________________________________________________________________________________________________________________________  

    /**
     * Metodo per effettuare Avanzamento della Giornata
     */
    public static void AvanzaGiornata() { // incrementa la data corrente. Questo verrà chiamato dal Bottone AvanzaGiorno   
        // Update dell'Attributo AFFLUENZA e Azzeramento
        myINI = new INIFile(Utility.INI_PATH);
        try {
            affluenza = myINI.getIntegerProperty("Votazione", "AffluenzaOggi");
            mysql.UpdateQuery( "INSERT INTO db." + getIdVotazione() + " (Data,Affluenza) VALUES ('" + readDataCorrente() + "', " + affluenza +");" );
             //mysql.UpdateQuery("UPDATE db."+ idVotazione + "SET Affluenza=" + affluenza + " WHERE Data=" + dataCorrente + ";");
        } catch (Exception ex) {}
        
        affluenza = 0;
        myINI.setIntegerProperty("Votazione", "AffluenzaOggi", 0, "AffluenzaOggi");
        dataCorrente.add(Calendar.DATE, 1);  // number of days to add
        myINI.setStringProperty("Votazione", "DataCorrente", f.format(dataCorrente.getTime()), "DataCorrente");
        myINI.save();
}
//______________________________________________________________________________
    /**
     * Metodo per trovare il Vincitore una volta chiuse le Elezioni
     * In caso di chiusura elezioni con candidati a pari merito ritorna un array con i 
     * codici fiscali di tutti i candidati che dovranno accedere al secondo turno.
     */
    private static ArrayList<String> findWinner() {       // chiamato a votazione finita: trova nel db il candidato vincitore
        ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
        int max = 0;
        ArrayList<String> CF = new ArrayList();
        for(Candidati obj: can) {
            if ( obj.getVoti() > max ) {
                max = obj.getVoti();
            }
        }
        for(Candidati obj: can) {
            if (obj.getVoti() == max) {
                CF.add(obj.getCF());
            }
        }
        return CF; // ritorna il codice fiscale del vincitore
    }
    
//______________________________________________________________________________
    /**
     * Metodo per effettuare il Reset dei Voti nel DataBase
     */
    private static void resetVoti() {      // setta tutti i voti nella tabella votanti a 0. Private perchè viene usato solo in questa classe
        ArrayList<Persone> pers = mysql.ReadPersoneColumns();
        for (Persone obj: pers) {
            mysql.UpdateQuery("UPDATE VOTANTI SET FlagVotato=0 WHERE CodiceFiscale='" + obj.getCF() + "';");
        }   
    }   
    
    /**
     * Metodo che scansiona tutte le tabelle per evitare doppioni
     * @param idVotazione Nome della votazione, costituisce anche il nome della tabella.
     * @return Restituisce vero/falso se la tabella esiste o non esiste.
     */
    public static boolean existsVotazione(String idVotazione) {
        boolean exists = false;
        try {
            ResultSet res = mysql.ExecuteQuery("SHOW TABLES FROM db;");
            while(res.next()){ 
                if (res.getString(1).equals(idVotazione)) { exists = true; }
            }
        } catch (Exception ex) {}
        return exists;
    }
    
    private static Icon setUrlIcon(String remoteURL , int resizedWidth, int resizedHeight ) {
        ImageIcon img;
        Image resizedImage = null;
        try {
            img = new ImageIcon(new URL(remoteURL));
            Image img_ = img.getImage();
            resizedImage = img_.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        } catch (MalformedURLException ex) {
            img = null;
        }
        return new ImageIcon(resizedImage);
    }
}
