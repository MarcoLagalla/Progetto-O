/*

MAIN CLASS

*/
package progetto.o;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.net.MalformedURLException;
import javax.swing.BoxLayout;

////////////////////////////////////////////////////////////////////////////////

public class ProgettoO {
    // Elementi Grafici Swing per MAINFRAME
    private JFrame mainFrame;
    private JCheckBox Admin;
    private JButton Admin_Log;
    private JLabel Admin_Pass;
    private JTextField CF;
    private JTextField CT;
    private JLabel CodiceFis;
    private JLabel CodiceTes;
    private JButton Enter;
    private JLabel Intestazione;
    private JPasswordField Pass_A;
    private JPanel background_panel;
    
    // Elementi Grafici Swing per CLIENT_FRAME
    private JFrame clientFrame;
    private JPanel client_panel; //GRIGLIA
    private JLabel Client_Label;
    private JButton Vote_Button;
    private JPanel Candidato_panel;
    private JButton foto; 
    private JTextField nome;
    private JTextField cognome;
    private JTextField partito;
    
    // Elementi Grafici per SERVER_FRAME
    private JFrame serverFrame;
    private JButton Close_Vot_Button;
    private JLabel Num_Partiti;
    private JTextField Num_Var;
    private JLabel Num_Votanti;
    private JLabel Server_Label;
    private JButton Set_Num_Partiti_Button;
    private JTextField Num_Partiti_Inseriti; //Si riferirà al Database
    private JPanel server_background_panel;
    
    
    final String IMG_REMOTE_FOLDER = "/var/www/progettoO/img";
    
    MySQlConnection mysql = new MySQlConnection();
    
    public ProgettoO() {
        prepareGUI();
    }
///////////////// MAIN /////////////////////////////////////////////////////////
    public static void main(String[] args) {
       ProgettoO SwingControl = new ProgettoO();

      
    }
////////////////////////////////////////////////////////////////////////////////  

    private void prepareGUI() {
        // MAIN FRAME
        mainFrame = new JFrame();
        mainFrame.setLayout(null);
        mainFrame.setSize(1276, 802);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        
        background_panel = new JPanel(null);
        background_panel.setBackground(Color.white);
        background_panel.setSize(1276, 802);
        mainFrame.add(background_panel);
        
        Intestazione = new JLabel();
        Intestazione.setFont(new Font("Intestazione", Font.ITALIC,25));
        Intestazione.setText("SISTEMA ELETTORALE ELETTRONICO");
        Intestazione.setBounds(440, 10, 1000, 30);
        background_panel.add(Intestazione);
        
        CodiceFis = new JLabel("Inserire CODICE FISCALE");
        CodiceFis.setBounds(30, 100, 200, 30);
        background_panel.add(CodiceFis);
        
        CF = new JTextField();
        CF.setText("LGLMRC94D20L304U");                                         // PER TEST DA RIMUOVERE
        CF.setBounds(240, 100, 300, 30);
        background_panel.add(CF);
        
        CodiceTes = new JLabel("Inserire TESSERA ELETTORALE");
        CodiceTes.setBounds(30, 140, 200, 30);
        background_panel.add(CodiceTes);
        
        CT = new JTextField();
        CT.setText("AT12349876");                                               // PER TEST DA RIMUOVERE
        CT.setBounds(240, 140, 300, 30);
        background_panel.add(CT);
        
        Enter = new JButton("REGISTRAZIONE");
        Enter.setActionCommand("Registrazione");
        Enter.addActionListener(new ButtonClickListener());
        Enter.setBounds(560, 110, 200, 50);
        background_panel.add(Enter);
        
        Admin = new JCheckBox("ADMINISTRATOR");
        Admin.setBounds(650, 500, 200, 20);
        background_panel.add(Admin);
        
        Admin_Pass = new JLabel("ADMIN_PASSWORD: ");
        Admin_Pass.setBounds(650, 530, 200, 20);
        background_panel.add(Admin_Pass);
        
        Pass_A = new JPasswordField();
        Pass_A.setBounds(860, 530, 200, 20);
        background_panel.add(Pass_A);
        
        Admin_Log = new JButton("ADMIN_LOGIN");
        Admin_Log.setActionCommand("Admin_Log");
        Admin_Log.addActionListener(new ButtonClickListener());
        Admin_Log.setBounds(760, 580, 150, 40);
        background_panel.add(Admin_Log);
        
        // Spawn MAINFRAME
        mainFrame.setVisible(true);
    }

////////////////////////////////////////////////////////////////////////////////    
    private void prepareClientGUI(){
        // CLIENT FRAME
        clientFrame = new JFrame("SISTEMA ELETTORALE ELETTRONICO");
       
        clientFrame.setLayout(null);
        clientFrame.setSize(1276, 802);
        clientFrame.setResizable(false);
        
        Client_Label = new JLabel("SCEGLIERE CANDIDATO",Client_Label.CENTER);
        Client_Label.setFont(new Font("Intestazione", Font.ITALIC,25));
        Client_Label.setBounds(50, 10, 1000, 50);
        clientFrame.add(Client_Label,BorderLayout.PAGE_START);
        
        
                GridLayout experimentLayout = new GridLayout(0,4);  // SETTA SPAZIATURE TRA COLONNE E RIGHE
                experimentLayout.setHgap(150);                      // GRIDLAYOUT
                experimentLayout.setVgap(30);
        client_panel = new JPanel(experimentLayout); 
        client_panel.setBounds(50,100,1000,1000);   // dimensioni pannello
        
       // TODO for()
       
       ArrayList<candidati> can = mysql.ReadCandidatiColumns();
       
       for (candidati object: can) {
           client_panel.add(createPan(object.getImmagine(),object.getNome(), object.getPartito()));
        }
        
        clientFrame.add(client_panel,BorderLayout.CENTER);
    
        client_panel.setVisible(true);
        
        Vote_Button = new JButton("VOTA");
        Vote_Button.setActionCommand("Vota");
        Vote_Button.addActionListener(new ButtonClickListener());
        Vote_Button.setBounds(890, 700, 300, 50);
        Vote_Button.setVisible(false);
        clientFrame.add(Vote_Button);
        
        clientFrame.setVisible(true);
        
    }


//////////////////////////////////////////////////////////////////////////////// 

    private JPanel createPan(URL Immagine, String Nome, String Partito){
        Candidato_panel = new JPanel();

       Candidato_panel.setLayout(new BoxLayout(Candidato_panel,BoxLayout.Y_AXIS));
       
        ImageIcon img = new ImageIcon(Immagine);
        
        foto = new JButton(img);
        foto.setBounds(0, 0, 200, 200);
        int offset = foto.getInsets().left;
        foto.setIcon(resizeIcon(img, foto.getWidth() - offset, foto.getHeight() - offset));
        
        
       
        Candidato_panel.add(foto);
        
        nome = new JTextField();
        nome.setText(Nome);
        nome.setBounds(10, 220 , 200, 25);
        Candidato_panel.add(nome);
        
        cognome = new JTextField();
        cognome.setText(Partito);
        cognome.setBounds(10, 250, 200, 25 );
        Candidato_panel.add(cognome);
        
        partito = new JTextField();
        partito.setText("");
        partito.setBounds(10, 280, 200, 25 );
        Candidato_panel.add(partito);
        Candidato_panel.setVisible(true);
      
        return Candidato_panel;
        
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    private void prepareServerGUI(){
        // SERVER FRAME
        serverFrame = new JFrame("GESTIONE SISTEMA ELETTORALE ELETTRONICO");
        serverFrame.setLayout(null);
        serverFrame.setSize(1276, 802);
        serverFrame.setResizable(false);
        
        server_background_panel = new JPanel(null);
        server_background_panel.setBackground(Color.white);
        server_background_panel.setSize(1276, 802);
        serverFrame.add(server_background_panel);
        
        Server_Label = new JLabel();
        Server_Label.setFont(new Font("Intestazione", Font.ITALIC,25));
        Server_Label.setText("GESTIONE SISTEMA ELETTORALE");
        Server_Label.setBounds(440, 10, 800, 30);
        server_background_panel.add(Server_Label);
        
        Num_Votanti = new JLabel("NUMERO VOTANTI: ");
        Num_Votanti.setBounds(20, 50, 150, 30);
        server_background_panel.add(Num_Votanti);
        
        Num_Var = new JTextField();
        Num_Var.setEditable(false);
        Num_Var.setBounds(165, 55, 150, 20);
        server_background_panel.add(Num_Var);
        
        Num_Partiti = new JLabel("NUMERO PARTITI: ");
        Num_Partiti.setBounds(20, 84, 150, 30);
        server_background_panel.add(Num_Partiti);
        
        Num_Partiti_Inseriti = new JTextField();
        Num_Partiti_Inseriti.setEditable(false);
        Num_Partiti_Inseriti.setBounds(165, 89, 150, 20);
        server_background_panel.add(Num_Partiti_Inseriti);
        
        Close_Vot_Button = new JButton("CHIUSURA IMMEDIATA VOTAZIONI");
        Close_Vot_Button.setActionCommand("Close_Vot");
        Close_Vot_Button.addActionListener(new ButtonClickListener());
        Close_Vot_Button.setBounds(1000, 690, 260, 50);
        server_background_panel.add(Close_Vot_Button);
        
        serverFrame.setVisible(true);

    }


////////////////////////////////////////////////////////////////////////////////

    public class ButtonClickListener implements ActionListener{

       public void actionPerformed(ActionEvent e){
           String command = e.getActionCommand();
           String CF_regex = "[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}";
           String CT_regex = "[0-9]{9}";
           
           switch(command) {
               case "Registrazione": 
               {
                   //  if(CF.getText().matches(CF_regex) && CT.getText().matches(CT_regex)){
                    if(CF.getText().matches(CF_regex)){    // DA FINIRE
                        ArrayList<votanti> vot = mysql.ReadVotantiColumns();
                       
                        boolean founded = false;
                        for (votanti v: vot){
                            if(v.getCF().toString().equals(CF.getText())){  
                                founded = true;
                                prepareClientGUI(); break; 
                            }
                        }   
                        if (!founded) {
                            JOptionPane.showMessageDialog(null,"Codice Fiscale non Trovato,se corretto è possibile che lei non sia residente nel comune dove si vuole Votare","ERRORE",JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                      JOptionPane.showMessageDialog(null,"Il codice fiscale inserito non sembra avere un formato corretto.\nRiprova.", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    }
                   
                    break;
                    
               }
               
               case "Admin_Log":
               {
                  prepareServerGUI();break;
               }
               case "Vota":
               {
                   // aggiungere
                   break;
               }
               case "Close_Vot":
               {
                   //
                   break;
               }
               default: break;
                   
           }

 
       }
    }

  
    
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {     // resize immagini per fit jButton
    Image img = icon.getImage();  
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
    return new ImageIcon(resizedImage);
}
}
