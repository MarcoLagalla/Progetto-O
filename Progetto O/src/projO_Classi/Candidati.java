package projO_Classi;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.net.URL;
// </editor-fold>


/**
 * @author Team
 */
public class Candidati extends Persone {
    
    private String Partito;
    private URL Immagine;
    private int Voti;
    
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
        this.Partito = Partito;
        this.Voti = Voti;
        this.Immagine = Immagine;
    }
//__________________________________GETTER/SETTER__________________________________   

    public String getPartito() { return Partito; }

    public URL getImmagine() { return Immagine; }

    public int getVoti() { return Voti; }
    
    public void setPartito(String Partito) { this.Partito = Partito; }

    public void setImmagine(URL Immagine) { this.Immagine = Immagine; }

    public void setVoti(int Voti) { this.Voti = Voti; }
    
}
