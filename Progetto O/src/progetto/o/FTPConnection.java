/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
/**
 *
 * @author marcolagalla
 */
public class FTPConnection {
    
    static String SERVER = "91.134.138.244";
    static int PORT = 21;
    
    static String USERNAME = "root";
    static String PASSWORD = "marco7539";
    
    FTPClient ftpclient;
    
   public FTPConnection() {
       
       try {
                  
       ftpclient = new FTPClient();
       ftpclient.connect(SERVER, PORT);
       
            try {
                ftpclient.login(USERNAME, PASSWORD);
            }catch(java.io.IOException ex) {
                System.out.print("FTP - Unable to login");
            }
       
       }catch(Exception se){
           System.out.print("FTP - Unable to connect to server");
       }

   }
    
   
   public boolean loadFile(String localPath, String remotePath, String fileName) {
       
       try {
            
           if (localPath.endsWith("/")) {                 // controlla che la path inserita termini con /
               localPath = localPath.concat(fileName);    // diversamente aggiunge lo slash
           } else {
               localPath = localPath.concat("/" + fileName);
           }
           
           if (remotePath.endsWith("/")) {                 // controlla che la path inserita termini con /
               remotePath = remotePath.concat(fileName);    // diversamente aggiunge lo slash
           } else {
               remotePath = remotePath.concat("/" + fileName);
           }

            java.io.File downloadFile = new java.io.File(localPath);
            java.io.OutputStream outputStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(downloadFile));
            boolean success = ftpclient.retrieveFile(remotePath, outputStream);
            outputStream.close();
            return success;
       } catch(java.io.IOException ex) {
           System.out.print("FTP - Error uploading file");
           return false;
       }

   }
   
   
   public void FTPConnectionClose() {
       try {
            ftpclient.disconnect();
       } catch(java.io.IOException ex) {
           System.out.print("FTP - Error closing connection");
       }
        
   }
   
   
}
