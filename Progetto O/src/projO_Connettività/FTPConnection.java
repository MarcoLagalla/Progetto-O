
package projO_Connettività;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

// imports per database
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
// </editor-fold>

//______________________________________________________________________________
/**
 * Necessaria per la Connessione al server tramite protocollo FTP
 * @author Team
 */
public class FTPConnection {
    
    private static final String SERVER_IP = "91.134.138.244";
    private static final int PORT = 21;
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "marco7539";
    
    FTPClient ftpClient;

//________________________________COSTRUTTORE___________________________________

    public FTPConnection() {
       
       try {
                  
       ftpClient = new FTPClient();
       ftpClient.connect(SERVER_IP, PORT);

            try {
                ftpClient.login(USERNAME, PASSWORD);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                
                ftpClient.enterLocalPassiveMode();
            }catch(java.io.IOException ex) {
                System.out.print("FTP - Unable to login");
            }
       
       }catch(Exception se){
           System.out.print("FTP - Unable to connect to server");
       }

   }
    
//______________________________________________________________________________
    
    /**
     * Metodo per caricamento File
     * @param localPath Percorso dal quale vengono prelevati i file
     * @param remotePath    Percorso sul quale vengono salvati i file
     * @return
     */
    public boolean loadFile(String localPath, String remotePath) {
       
       try {
        InputStream input = new FileInputStream(new File(localPath));
        boolean done = ftpClient.storeFile(remotePath, input);
        
        return done;
       }catch(java.io.IOException ex) {
            System.out.print("FTP - Error uploading file " + ex.getMessage());
            return false;
       }
   }
    
//______________________________________________________________________________
    
    /**
     * Metodo di Chiusura dell' FTPConnection
     */
    public void close() {
       try {
            ftpClient.disconnect();
       } catch(java.io.IOException ex) {
           System.out.print("FTP - Error closing connection");
       }
        
   }
    
}
