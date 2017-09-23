/**
 * Rappresenta le Personeinserite nel Database
 * 
*/

package projO_Classi;

/**
 *
 * @author Team
 */
public class Persone {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String sesso;
    private String dataNascita;
    private String comune;
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
        this.codiceFiscale = CF;
        this.nome = Nome;
        this.cognome = Cognome;
        this.sesso = Sesso;
        this.dataNascita = DataNascita;
        this.comune = Comune;
    }
//__________________________________GETTER/SETTER_______________________________
      
    /**
     *
     * @return CodiceFiscale
     */
    public String getCF() { return codiceFiscale; }

    /**
     *
     * @return nome
     */
    public String getNome() { return nome; }

    /**
     *
     * @return cognome
     */
    public String getCognome() { return cognome; }

    /**
     *
     * @return sesso
     */
    public String getSesso() { return sesso; }

    /**
     *
     * @return Data di Nascita
     */
    public String getDataNascita() { return dataNascita; }

    /**
     *
     * @return comune
     */
    public String getComune() { return comune; }
 

    /**
     *
     * @param CF
     */
    public void setCF(String CF) { this.codiceFiscale = CF; }

    /**
     *
     * @param Nome
     */
    public void setNome(String Nome) { this.nome = Nome; } 

    /**
     *
     * @param Cognome
     */
    public void setCognome(String Cognome) { this.cognome = Cognome; }

    /**
     *
     * @param Sesso
     */
    public void setSesso(String Sesso) { this.sesso = Sesso; }

    /**
     *
     * @param DataNascita
     */
    public void setDataNascita(String DataNascita) { this.dataNascita = DataNascita; }

    /**
     *
     * @param Comune
     */
    public void setComune(String Comune) { this.comune = Comune; }
}
