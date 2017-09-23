/**
 * Rappresenta i Candidati alle Elezioni
*/

package projO_Classi;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.net.URL;
// </editor-fold>


/**
 * @author Team
 */
public class Candidati extends Persone {
    
    private String partito;
    private URL immagine;
    private int voti;
    
    /**
     *
     * @param CF codice fiscale del candidato
     * @param Nome
     * @param Cognome
     * @param Sesso
     * @param DataNascita
     * @param Comune
     * @param Partito
     * @param Voti voti ricevuti
     * @param Immagine foto del candidato
     */
    public Candidati(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune,  String Partito, int Voti,URL Immagine) {
        super(CF, Nome, Cognome,Sesso, DataNascita, Comune);
        this.partito = Partito;
        this.voti = Voti;
        this.immagine = Immagine;
    }
//__________________________________GETTER/SETTER__________________________________   

    public String getPartito() { return partito; }

    public URL getImmagine() { return immagine; }

    public int getVoti() { return voti; }
    
    public void setPartito(String Partito) { this.partito = Partito; }

    public void setImmagine(URL Immagine) { this.immagine = Immagine; }

    public void setVoti(int Voti) { this.voti = Voti; }
    
}
