
package progetto.o.Classi;

import java.net.URL;
//______________________________________________________________________________
/**
 *
 * @author Team
 */
public class Candidati extends Persone {
    
    private String Partito;
    private URL Immagine;
    private int Voti;
    
    /**
     *
     * @param CF
     * @param Nome
     * @param Cognome
     * @param Sesso
     * @param DataNascita
     * @param Comune
     * @param Partito
     * @param Voti
     * @param Immagine
     */
    public Candidati(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune,  String Partito, int Voti,URL Immagine) {
        super(CF, Nome, Cognome,Sesso, DataNascita, Comune);
        this.Partito = Partito;
        this.Voti = Voti;
        this.Immagine = Immagine;
    }
//______________________________________________________________________________   
    // Metodi Getter

    /**
     *
     * @return Partito
     */
    
    public String getPartito() { return Partito; }

    /**
     *
     * @return Immagine
     */
    public URL getImmagine() { return Immagine; }

    /**
     *
     * @return Voti
     */
    public int getVoti() { return Voti; }
    
//______________________________________________________________________________
    
    // Metodi Setter

    /**
     *
     * @param Partito
     */
    
    public void setPartito(String Partito) { this.Partito = Partito; }

    /**
     *
     * @param Immagine
     */
    public void setImmagine(URL Immagine) { this.Immagine = Immagine; }

    /**
     *
     * @param Voti
     */
    public void setVoti(int Voti) { this.Voti = Voti; }
    
}
