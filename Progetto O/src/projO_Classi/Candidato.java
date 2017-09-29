package projO_Classi;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.net.URL;
// </editor-fold>


/**
 * Rappresenta i Candidati alle Elezioni attraverso i dati anagrafici e l'immagine
 * @author Team
 */
public class Candidato extends Persona {
    
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
     * @param Voti rappresenta i voti ricevuti
     * @param Immagine rappresenta la foto del candidato
     */
    public Candidato(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune,  String Partito, int Voti,URL Immagine) {
        super(CF, Nome, Cognome,Sesso, DataNascita, Comune);
        this.partito = Partito;
        this.voti = Voti;
        this.immagine = Immagine;
    }
//__________________________________GETTER/SETTER__________________________________   

    /**
     *
     * @return partito di appartenenza
     */
    public String getPartito() { return partito; }

    /**
     *
     * @return immagine di profilo
     */
    public URL getImmagine() { return immagine; }

    /**
     *
     * @return i voti che ha ottenuto
     */
    public int getVoti() { return voti; }
    
    /**
     *
     * @param Partito di appartenenza
     */
    public void setPartito(String Partito) { this.partito = Partito; }

    /**
     *
     * @param Immagine di profilo
     */
    public void setImmagine(URL Immagine) { this.immagine = Immagine; }

    /**
     *
     * @param Voti
     */
    public void setVoti(int Voti) { this.voti = Voti; }
    
}
