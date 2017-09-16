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
 *
 * @author Team
 */
public class FTPConnection {
    
    static String serverIp = "91.134.138.244";
    static int port = 21;
    
    static String userName = "root";
    static String password = "marco7539";
    
    FTPClient ftpClient;

//________________________________COSTRUTTORE___________________________________

    public FTPConnection() {
       
       try {
                  
       ftpClient = new FTPClient();
       ftpClient.connect(serverIp, port);

            try {
                ftpClient.login(userName, password);
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
    public void FTPConnectionClose() {
       try {
            ftpClient.disconnect();
       } catch(java.io.IOException ex) {
           System.out.print("FTP - Error closing connection");
       }
        
   }
    
}
