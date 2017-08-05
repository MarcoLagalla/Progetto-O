/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Votazione {

/*____________________________________STATO INTERNO________________________________________*/
    
    private String idVotazione;
    private final DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
    private String dataCorrente;
    private String dataInizioVot;
    private String dataFineVot;
    private MySQlConnection mysql = new MySQlConnection();
    
/*____________________________________COSTRUTTORI__________________________________________*/
    
    public Votazione(String _idVotazione, String dataFine) { // il costruttore crea una tabella nel db, rileva la data corrente e definisce lo stato interno
        this.idVotazione = _idVotazione;
        
        try {   // 
            int res = mysql.UpdateQuery("CREATE TABLE " + idVotazione + "(Data String, NumeroVoti int)");
                       if (res == 0 ) {
                           System.out.println("errore query");
                       }
            Calendar cal = Calendar.getInstance();
            String data = f.format(cal);
            this.dataInizioVot = dataCorrente;
            this.dataFineVot = dataFine;
        } catch (Exception ex) {ex.printStackTrace();}
    }
    
/*_______________________________________METODI____________________________________________*/
    
    public void chiudiVotazione() { // chiude il turno delle votazioni.
        resetVoti();
    } 
    public void addVoto() { // incrementa il numero dei voti nella giornata corrente, nella tabella VOTAZIONI
        int voti;
        ResultSet voti_ = mysql.ExecuteQuery("SELECT Voti FROM VOTAZIONI WHERE Data = '" + dataCorrente + "';");
        try {
            voti = voti_.getInt("Voti");
            voti++;
            mysql.UpdateQuery("UPDATE VOTAZIONI SET Voti=" + voti + ";");
        } catch (SQLException ex) {
            Logger.getLogger(Votazione.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void terminaGiornata() { // incrementa la data corrente
        // 1) incrementa la data
        // 2) aggiungi una riga in VOTAZIONI, con la data corrente (cioè di domani)
    }

    private void resetVoti() {      // setta tutti i voti nella tabella votanti a 0. Private perchè viene usato solo in questa classe
            mysql.UpdateQuery("UPDATE VOTANTI SET Voti=" + 0 + ";");
    }
}
