package projO_Interfacce;


/**
 *
 * @author Team
 */
public interface InterfacciaPrincipale {
    
    /**
     * Immagini Profilo
     */
    final String IMG_PROFILO = "http://91.134.138.244/progettoO/img/profilo.jpg";

    /**
     * Immagine Votazioni Aperte
     */
    final String IMG_VOTAZIONI_APERTE = "http://91.134.138.244/progettoO/img/Vot_Aperte.png";

    /**
     * Immagine Votazioni Chiuse
     */
    final String IMG_VOTAZIONI_CHIUSE = "http://91.134.138.244/progettoO/img/Vot_Chiuse.png";

    /**
     * Immagine LogoRepubblica
     */
    final String IMG_LOGO = "http://91.134.138.244/progettoO/img/Logo.png";    

    /** Immagine LogoRepubblica usato nel Server
     *
     */
    final String IMG_LOGO_SERVER = "http://91.134.138.244/progettoO/img/Logo_Server.png";      

    /**
     * Immagine Votazioni Chiuse
     */
    final String IMG_REGISTRAZIONE_DISABLED =  "http://91.134.138.244/progettoO/img/Button_Registrazione_Disabled.png";

    /**
     * Immagine Votazioni Aperte
     */
    final String IMG_REGISTRAZIONE_ENABLED =  "http://91.134.138.244/progettoO/img/Button_Registrazione.png.png";
    
    /**
     * Immagine Anno per Calendar
     */
    final int YEAR = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

    /**
     * Immagine Mese per Calendar
     */
    final int MONTH = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);

    /**
     * Immagine Giorno per Calendar
     */
    final int DAY = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
    
    /**
     * Immagine IMG Remote Folder
     */
    final public String IMG_REMOTE_FOLDER = "/var/www/progettoO/img";
     
}
