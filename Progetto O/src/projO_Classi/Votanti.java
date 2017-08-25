package projO_Classi;


/**
 *
 * @author Team
 */
public class Votanti extends Persone {
    
    private String CodiceTessera;
    private boolean Votato;
 
    /**
     *
     * @param CF
     * @param Nome
     * @param Cognome
     * @param Sesso
     * @param DataNascita
     * @param Comune
     * @param CodiceTessera
     * @param Votato
     */
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
//______________________________________________________________________________
    
    // Metodo Getter

    /**
     *
     * @return Codice Tessera
     */
    public String getCodiceTessera() { return CodiceTessera; }

//______________________________________________________________________________
    
    // Metodo Setter

    /**
     *
     * @param CodiceTessera
     */
    public void setCodiceTessera(String CodiceTessera) { this.CodiceTessera = CodiceTessera; }
    
    /**
     *
     * @return
     */
    public boolean getVotato() {return Votato; }
    
    /**
     *
     * @param Votato
     */
    public void setVotato(boolean Votato) { this.Votato = Votato; }
}
