package projO_Classi;


/**
 *
 * @author Team
 */
public class Persone {
    private String CF;
    private String Nome;
    private String Cognome;
    private String Sesso;
    private String DataNascita;
    private String Comune;
//______________________________________________________________________________
    /**
     *
     * @param CF
     * @param Nome
     * @param Cognome
     * @param Sesso
     * @param DataNascita
     * @param Comune
     */
    public Persone(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune){
        this.CF = CF;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Sesso = Sesso;
        this.DataNascita = DataNascita;
        this.Comune = Comune;
    }
//______________________________________________________________________________
    
    // Metodi Getter
    
    /**
     *
     * @return CodiceFiscale
     */
    public String getCF() { return CF; }

    /**
     *
     * @return Nome
     */
    public String getNome() { return Nome; }

    /**
     *
     * @return Cognome
     */
    public String getCognome() { return Cognome; }

    /**
     *
     * @return Sesso
     */
    public String getSesso() { return Sesso; }

    /**
     *
     * @return Data di Nascita
     */
    public String getDataNascita() { return DataNascita; }

    /**
     *
     * @return Comune
     */
    public String getComune() { return Comune; }
 
//______________________________________________________________________________
    
    // Metodi Setter
    
    /**
     *
     * @param CF
     */
    public void setCF(String CF) { this.CF = CF; }

    /**
     *
     * @param Nome
     */
    public void setNome(String Nome) { this.Nome = Nome; } 

    /**
     *
     * @param Cognome
     */
    public void setCognome(String Cognome) { this.Cognome = Cognome; }

    /**
     *
     * @param Sesso
     */
    public void setSesso(String Sesso) { this.Sesso = Sesso; }

    /**
     *
     * @param DataNascita
     */
    public void setDataNascita(String DataNascita) { this.DataNascita = DataNascita; }

    /**
     *
     * @param Comune
     */
    public void setComune(String Comune) { this.Comune = Comune; }
}
