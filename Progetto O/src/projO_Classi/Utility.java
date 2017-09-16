package projO_Classi;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Team
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
     * Immagine Refresh
     */
    final static public String IMG_REFRESH =  "http://91.134.138.244/progettoO/img/Refresh_icon.png";
    
    /**
     * Immagine Loading_GIF
     */
    final static public String IMG_GIF = "http://91.134.138.244/progettoO/img/page-loader.gif";  
    
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
    
    final static public String URL_IMG_REMOTE = "http://91.134.138.244/progettoO/img/";
    
    /**
     * Path INIFile
     */
    final static public String INI_PATH = System.getenv("APPDATA") + "\\progettoO.ini";
    
    /**
     * Remote Path BackUp INI
     */
    final static public String URL_REMOTE_INI_PATH = "http://91.134.138.244/progettoO/ini/progettoO.ini";
    
    final static public String REMOTE_INI_PATH = "/var/www/progettoO/ini/";

//_____________________________METODI UTILITY___________________________________
    /**
     * @param remoteURL url dell'immagine
     * @return image as icon
     */
    public static Icon setUrlIcon(String remoteURL ) {
        ImageIcon img;
        try {
            img = new ImageIcon(new URL(remoteURL));
        } catch (MalformedURLException ex) {
            img = null;
        }
        return img;
    }

    /**
     * Metodo SetUrlICon per prendere url Immagine da Inserire con impostazione per altezza e larghezza
     * @param remoteURL
     * @param resizedHeight
     * @param resizedWidth
     * @return image as icon (resized)
     */
    
    public static Icon setUrlIcon(String remoteURL , int resizedWidth, int resizedHeight ) {
        ImageIcon img;
        Image resizedImage = null;
        try {
            img = new ImageIcon(new URL(remoteURL));
            Image img_ = img.getImage();
            resizedImage = img_.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        } catch (MalformedURLException ex) {
            img = null;
        }
        return new ImageIcon(resizedImage);
    }
    /**
     * Metodo ResizeIcon per ridimensionare l'Immagine con l'elemento Swing al quale verrà applicata
     * @param icon immagine
     * @param resizedWidth nuova larghezza
     * @param resizedHeight nuova altezza
     * @return image as icon (resized)
     */
    
    public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {     // resize foto dei Candidati (nei pannelli di createPan) per fit jButton
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
    }

    public static boolean downloadINI() {
        File f = new File(Utility.INI_PATH);
        if (!f.exists() && !f.isDirectory()) { 
            try {
                URL website = new URL(Utility.URL_REMOTE_INI_PATH);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(Utility.INI_PATH);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                } catch (MalformedURLException ex) { return false; } 
                  catch (IOException ex) { return false; }
            }
        return true;
        }
    
    
//______________________________________________________________________________
    
    // Costuttore privato
    
    private Utility() {};
}
