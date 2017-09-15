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
    
    static String SERVER = "91.134.138.244";
    static int PORT = 21;
    
    static String USERNAME = "root";
    static String PASSWORD = "marco7539";
    
    FTPClient ftpclient;

//______________________________________________________________________________

    public FTPConnection() {
       
       try {
                  
       ftpclient = new FTPClient();
       ftpclient.connect(SERVER, PORT);

            try {
                ftpclient.login(USERNAME, PASSWORD);
                ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
                
                ftpclient.enterLocalPassiveMode();
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
        boolean done = ftpclient.storeFile(remotePath, input);
        
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
            ftpclient.disconnect();
       } catch(java.io.IOException ex) {
           System.out.print("FTP - Error closing connection");
       }
        
   }
    
}
