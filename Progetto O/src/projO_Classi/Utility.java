/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projO_Classi;

/**
 *
 * @author marco
 */
public class Utility {
     
        /**
     * Immagini Profilo
     */
    final static public String IMG_PROFILO = "http://91.134.138.244/progettoO/img/profilo.jpg";

    /**
     * Immagine Votazioni Aperte
     */
    final static public String IMG_VOTAZIONI_APERTE = "http://91.134.138.244/progettoO/img/Vot_Aperte.png";

    /**
     * Immagine Votazioni Chiuse
     */
    final static public String IMG_VOTAZIONI_CHIUSE = "http://91.134.138.244/progettoO/img/Vot_Chiuse.png";

    /**
     * Immagine LogoRepubblica
     */
    final static public String IMG_LOGO = "http://91.134.138.244/progettoO/img/Logo.png";    

    /** Immagine LogoRepubblica usato nel Server
     *
     */
    final static public String IMG_LOGO_SERVER = "http://91.134.138.244/progettoO/img/Logo_Server.png";      

    /**
     * Immagine Votazioni Chiuse
     */
    final static public String IMG_REGISTRAZIONE_DISABLED =  "http://91.134.138.244/progettoO/img/Button_Registrazione_Disabled.png";

    /**
     * Immagine Votazioni Aperte
     */
    final static public String IMG_REGISTRAZIONE_ENABLED =  "http://91.134.138.244/progettoO/img/Button_Registrazione.png";
    
    /**
     * Anno per Calendar
     */
    final static public int YEAR = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

    /**
     * Mese per Calendar
     */
    final static public int MONTH = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;

    /**
     * Giorno per Calendar
     */
    final static public int DAY = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
    
    /**
     * Folder immagini IMG Remote Folder
     */
    final static public String IMG_REMOTE_FOLDER = "/var/www/progettoO/img";
    
    /**
     * Path INIFile
     */
    final static public String INI_PATH = System.getenv("APPDATA") + "\\progettoO.ini";
    
    /**
     * Remote Path BackUp INI
     */
    final static public String REMOTE_INI_PATH = "http://91.134.138.244/progettoO/ini/progettoO.ini";
    
    
    // costuttore privato
    
    private Utility() {};
}
