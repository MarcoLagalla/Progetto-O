/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author marco
 */
public class Votazione {
    
    private String idVotazione;
    private final DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
    private String dataCorrente;
    private String dataInizioVot;
    private String dataFineVot;
    
    public Votazione(String _idVotazione, String dataFine) { // costruttore
        this.idVotazione = _idVotazione;
        Calendar cal = Calendar.getInstance();
        String data = f.format(cal);
        this.dataInizioVot = dataCorrente;
        this.dataFineVot = dataFine;
    }

}
