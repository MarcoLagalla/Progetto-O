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
    
    public void chiudiVotazione() {} 
    public void addVoto() {}

}
