package projO_Classi;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JTextField;

/**
 *
 * @author Team
 */
public class Utility {

    private Utility() {};     // Costuttore privato. La classe contiene solo variabili e metodi statici, non va istanziata.
    
//_______________________________________________________________________

    /**
     * Immagini Profilo
     */
    final static public String imgProfilo = "http://91.134.138.244/progettoO/img/profilo.jpg";

    /**
     * Immagine Votazioni Aperte
     */
    final static public String imgVotazioniAperte = "http://91.134.138.244/progettoO/img/Vot_Aperte.png";

    /**
     * Immagine Votazioni Chiuse
     */
    final static public String imgVotazioniChiuse = "http://91.134.138.244/progettoO/img/Vot_Chiuse.png";

    /**
     * Immagine LogoRepubblica
     */
    final static public String imgLogo = "http://91.134.138.244/progettoO/img/Logo.png";    

    /** 
     * Immagine LogoRepubblica usato nel Server
     */
    final static public String imgLogoServer = "http://91.134.138.244/progettoO/img/Logo_Server.png";      

    /**
     * Immagine Votazioni Chiuse
     */
    final static public String imgRegistrazioneDisabilitata =  "http://91.134.138.244/progettoO/img/Button_Registrazione_Disabled.png";

    /**
     * Immagine Votazioni Aperte
     */
    final static public String imgRegistrazioneAbilitata =  "http://91.134.138.244/progettoO/img/Button_Registrazione.png";
    
    /**
     * Immagine Refresh
     */
    final static public String imgRefresh =  "http://91.134.138.244/progettoO/img/Refresh_icon.png";
    
    /**
     * Anno per Calendar
     */
    final static public int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

    /**
     * Mese per Calendar 
     */
    final static public int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;

    /**
     * Giorno per Calendar
     */
    final static public int day = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
    
    /**
     * Indirizzo Web immagini IMG Remote Folder
     */
    final static public String imgRemoteFolder = "/var/www/progettoO/img";
    
    /**
     * Directory interna al server Remoto per Immagini
     */
    final static public String urlImageRemote = "http://91.134.138.244/progettoO/img/";
    
    /**
     * Path INIFile
     */
    final static public String INIPath = System.getenv("APPDATA") + "\\progettoO.ini";
    
    /**
     * Remote Path BackUp INI
     */
    final static public String urlRemoteINIPath = "http://91.134.138.244/progettoO/ini/progettoO.ini";
    
    /**
     * Indirizzo Web File INI
     */
    final static public String remoteINIPath = "/var/www/progettoO/ini/";

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
   
      /**
     * Metodo resetTestField per impostare i JTextField dell'interfaccia principale allo stato di default
     * @param textField textField da modificare
     * @param newText nuova testo del JTextField
     */
    public static void resetTextField (JTextField textField, String newText) {
            textField.setText(newText);
            textField.setFont(new Font("CT_Field",Font.ITALIC,20));
            textField.setForeground(Color.LIGHT_GRAY);
    }

    /**
     * Metodo per Download .ini File affinchè sia possibie una sincronizzazione tra le macchine
     * @return boolean che indica l'avvenuto Download o possibili problemi
     */
    public static boolean downloadINI() {
        File f = new File(Utility.INIPath);
        if (!f.exists() && !f.isDirectory()) { 
            try {
                URL website = new URL(Utility.urlRemoteINIPath);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(Utility.INIPath);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                } catch (MalformedURLException ex) { return false; } 
                  catch (IOException ex) { return false; }
            }
        return true;
        }
    
}
