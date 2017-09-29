
package projO_Classi;


/**
 * Rappresenta le persone Votanti nel Comune del seggio
 * @author Team
 */
public class Votante extends Persona {
    
    private String codiceTessera;
    private boolean votato;
 
    /**
     *
     * @param CF
     * @param Nome
     * @param Cognome
     * @param Sesso
     * @param DataNascita
     * @param Comune
     * @param CodiceTessera rappresenta con un formato a 9 numeri il codice di riconoscimento della Tessera Elettorale di ogni persona, abbinata al Codice Fiscale
     * @param Votato rappresenta il flag del votato
     */
    public Votante(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune, String CodiceTessera, int Votato) {
        super(CF, Nome, Cognome, Sesso, DataNascita, Comune);
        this.codiceTessera = CodiceTessera;
        if (Votato == 0) {
            this.votato = false;
        }
        else if (Votato == 1) {
            this.votato = true;
        }
    }
//__________________________________GETTER/SETTER_______________________________
    
    /**
     *
     * @return Codice Tessera
     */
    public String getCodiceTessera() { return codiceTessera; }


    /**
     *
     * @param CodiceTessera
     */
    public void setCodiceTessera(String CodiceTessera) { this.codiceTessera = CodiceTessera; }
    
    /**
     *
     * @return votato ovvero un int che permette di pcapire se una persona ha votato o meno
     */
    public boolean getVotato() {return votato; }
    
    /**
     *
     * @param Votato
     */
    public void setVotato(boolean Votato) { this.votato = Votato; }
}
