/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o.Interfacce;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author marco
 */
public interface InterfacciaPrincipale {
    
   final String IMG_PROFILO = "http://91.134.138.244/progettoO/img/profilo.jpg";
    final String IMG_VOTAZIONI_APERTE = "http://91.134.138.244/progettoO/img/Vot_Aperte.png";
    final String IMG_VOTAZIONI_CHIUSE = "http://91.134.138.244/progettoO/img/Vot_Chiuse.png";
    final String IMG_LOGO = "http://91.134.138.244/progettoO/img/Logo.png";    
    final String IMG_LOGO_SERVER = "http://91.134.138.244/progettoO/img/Logo_Server.png";      
    final String IMG_REGISTRAZIONE_DISABLED =  "http://91.134.138.244/progettoO/img/Button_Registrazione_Disabled.png";
    final String IMG_REGISTRAZIONE_ENABLED =  "http://91.134.138.244/progettoO/img/Button_Registrazione.png.png";
    
    final int YEAR = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    final int MONTH = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    final int DAY = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
    
    
    
     final public String IMG_REMOTE_FOLDER = "/var/www/progettoO/img";
     final public String SERVER = "http://91.134.138.244/progettoO/img/";
     
}
