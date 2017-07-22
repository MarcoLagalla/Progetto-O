/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
                ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpclient.enterLocalPassiveMode();
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

           InputStream input = new FileInputStream(new File(localPath));
            return ftpclient.storeFile(remotePath, input);
       } catch(java.io.IOException ex) {
           System.out.print("FTP - Error uploading file " + ex.getMessage());
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
