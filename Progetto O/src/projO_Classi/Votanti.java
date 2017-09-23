/**
 * Rappresenta i Votanti nel Comune
*/

package projO_Classi;


/**
 *
 * @author Team
 */
public class Votanti extends Persone {
    
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
     * @param CodiceTessera
     * @param Votato
     */
    public Votanti(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune, String CodiceTessera, int Votato) {
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
     * @return
     */
    public boolean getVotato() {return votato; }
    
    /**
     *
     * @param Votato
     */
    public void setVotato(boolean Votato) { this.votato = Votato; }
}
