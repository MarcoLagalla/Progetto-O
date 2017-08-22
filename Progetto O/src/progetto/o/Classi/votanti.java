/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o.Classi;

/**
 *
 * @author marcolagalla
 */
public class Votanti extends Persone {
    
    private String CodiceTessera;
    private boolean Votato;
 
    public Votanti(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune, String CodiceTessera, int Votato) {
        super(CF, Nome, Cognome, Sesso, DataNascita, Comune);
        this.CodiceTessera = CodiceTessera;
        if (Votato == 0) {
            this.Votato = false;
        }
        else if (Votato == 1) {
            this.Votato = true;
        }
    }
    
    //metodo getter
    public String getCodiceTessera() { return CodiceTessera; }
    
    //metodo setter
    public void setCodiceTessera(String CodiceTessera) { this.CodiceTessera = CodiceTessera; }
    
    public boolean getVotato() {return Votato; }
    
    public void setVotato(boolean Votato) { this.Votato = Votato; }
}
