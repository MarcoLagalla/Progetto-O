/*

MAIN CLASS

*/
package progetto.o;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.net.URL;
import java.net.MalformedURLException;


/*____________________________________STATO INTERNO________________________________________*/

public class ProgettoO{
    // Elementi Grafici Swing per MAINFRAME
    private JFrame mainFrame;
    private JTextField CF;
    private JTextField CT;
    private JLabel CodiceFis;
    private JLabel CodiceTes;
    private JButton Enter;
    private JLabel Intestazione;
    private JPanel background_panel;
    
    // Elementi Grafici Swing per CLIENT_FRAME
    private JFrame clientFrame;
    private JPanel client_panel; //GRIGLIA
    private JLabel Client_Label;
    private JButton Vote_Button;
    private JPanel Candidato_panel;
    private JButton foto; 
    private JLabel nome;
    private JLabel cognome;
    private JLabel partito;
    
    // Elementi grafici per login Admin
    private JFrame Admin_Login;
    private JLabel AdmLog_ErrPwd;
    private JPasswordField AdmLog_pwd;
    private JButton AdmLog_button;
    private JLabel AdmLog_title;

    // Elementi Grafici per SERVER_FRAME 
    private JFrame serverFrame;
    private JButton Close_Vot_Button;
    private JLabel Num_Partiti;
    private JTextField Num_Var;
    private JLabel Num_Votanti;
    private JLabel Server_Label;
    private JButton Set_Num_Partiti_Button;
    private JTextField Num_Partiti_Inseriti;      //  Si riferirà al Database
    private JPanel server_background_panel;

    
    // Istanzio Oggetti Utili
    
    private final String admin_pwd = "abc123";
    final String IMG_REMOTE_FOLDER = "/var/www/progettoO/img";
    
    java.util.Timer timer = new java.util.Timer(); // timer usato per la scomparsa del JLabel AdmLog_ErrPwd
    TimerTask task = new MyTask();
    MySQlConnection mysql = new MySQlConnection();
    
    // Istanzio serverFrame_ creato con JFrame Form
    serverFrame_ prepareServerGUI = new serverFrame_();
    
    // Definisco HotKey
    
    private KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK);       // hotkey per l'azione call_AdmLogin (CTRL+A)

/*____________________________________COSTRUTTORI__________________________________________*/

    public ProgettoO() {
        prepareGUI();
    }
    
/*_______________________________________METODI____________________________________________*/
  
///////////////// MAIN ///////////////////
    public static void main(String[] args) {
       ProgettoO SwingControl = new ProgettoO();
    }
///////////////////////////////////////////
    

private void prepareGUI() {         // Creazione finestra principale (login user)
    
    int c = JComponent.WHEN_IN_FOCUSED_WINDOW;      // la shortcut per chiamare la finistra AdminLogin è applicabile solo se MainFrame è FOCUSED
    
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
        
        // Creo la shortcut (CTRL+A) che apre la finestra di Admin Login
         JButton AdmLog_Button = new JButton();
         AdmLog_Button.setAction(new AbstractAction("call AdmLogin") {
            @Override
            public void actionPerformed(ActionEvent call_AdmLog) {
                prepareAdminLoginGUI();
            }
        }); 

         background_panel.add(AdmLog_Button);
        
        background_panel.getInputMap(c).put(key, "call_Action");
        background_panel.getActionMap().put("call_Action", AdmLog_Button.getAction());

        // Spawn MAINFRAME
        mainFrame.setVisible(true);
    }       
    
////////////////////////////////////////////////////////////////////////////////

private void prepareAdminLoginGUI() {        // Creazione finestra Login per Admin (accede alla finestra Server)
        Admin_Login = new JFrame("ADMINISTRATOR LOGIN");
        Admin_Login.setLayout(null);
        Admin_Login.setSize(500, 300);
        Admin_Login.setResizable(false);
        
        AdmLog_title = new JLabel("Admin password: ");
        AdmLog_title.setBounds(70, 70, 120, 25);
        Admin_Login.add(AdmLog_title);
        
        AdmLog_pwd = new JPasswordField();
        AdmLog_pwd.setBounds(210, 70, 200, 25);
        Admin_Login.add(AdmLog_pwd);
        
        AdmLog_button = new JButton("LOGIN");
        AdmLog_button.setActionCommand("Admin_Log");
        AdmLog_button.addActionListener(new ButtonClickListener());
        AdmLog_button.setBounds(170, 150, 150, 50);
        Admin_Login.add(AdmLog_button);
        
        AdmLog_ErrPwd = new JLabel();
        AdmLog_ErrPwd.setBounds(150, 220, 200, 25);
       //error_AdminLogin.setBorder(BorderFactory.createLineBorder(Color.black));       -> attivare per vedere la posizione del JLabel sulla finestra, anche se non ha testo
        Admin_Login.add(AdmLog_ErrPwd);
        
        Admin_Login.setVisible(true);
    }      

////////////////////////////////////////////////////////////////////////////////

/*private void prepareServerGUI(){        // Creazione finestra Server (dopo Admin Login)
    

    }        
*/

////////////////////////////////////////////////////////////////////////////////

private void prepareClientGUI(){            // Creazione finestra votazione ( dopo user login)

        clientFrame = new JFrame("SISTEMA ELETTORALE ELETTRONICO");
       
        clientFrame.setLayout(null);
        clientFrame.setSize(1276, 802);
        clientFrame.setResizable(false);
        
        Client_Label = new JLabel("SCEGLIERE CANDIDATO",Client_Label.CENTER);
        Client_Label.setFont(new Font("Intestazione", Font.ITALIC,25));
        Client_Label.setBounds(50, 10, 1000, 50);
        clientFrame.add(Client_Label,BorderLayout.PAGE_START);
        
        
                GridLayout experimentLayout = new GridLayout(0,4,8,8);  // SETTA SPAZIATURE TRA COLONNE E RIGHE
        client_panel = new JPanel(experimentLayout); 
        client_panel.setBounds(50,100,500,500);   // dimensioni pannello
        
       // TODO for()
       
       ArrayList<candidati> can = mysql.ReadCandidatiColumns();
       
       for (candidati object: can) {
           client_panel.add(createPan(object.getImmagine(),object.getNome(), object.getCognome(), object.getPartito()));
        }
        
        clientFrame.add(client_panel,BorderLayout.CENTER);
    
        client_panel.setVisible(true);
        
        Vote_Button = new JButton("VOTA");
        Vote_Button.setActionCommand("Vota");
        Vote_Button.addActionListener(new ButtonClickListener());
        Vote_Button.setBounds(890, 700, 300, 50);
        Vote_Button.setVisible(false);
        clientFrame.add(Vote_Button);
        clientFrame.setContentPane(client_panel);
        clientFrame.setVisible(true);
        
    }       

////////////////////////////////////////////////////////////////////////////////

private JPanel createPan(URL Immagine, String Nome, String Cognome, String Partito){        // Restituisce un pannello con i dati del candidato.
    
        Candidato_panel = new JPanel();

       Candidato_panel.setLayout(new BoxLayout(Candidato_panel,BoxLayout.Y_AXIS));
       
        ImageIcon img = new ImageIcon(Immagine);
        
        foto = new JButton(img);
        foto.setBounds(0, 0, 200, 200);
        int offset = foto.getInsets().left;
        foto.setIcon(resizeIcon(img, foto.getWidth() - offset, foto.getHeight() - offset));

        Candidato_panel.add(foto);
        
        nome = new JLabel(Nome, SwingConstants.RIGHT);
        nome.setFont(new Font(nome.getFont().getName(), Font.BOLD, 25));
        nome.setBounds(10, 220 , 200, 25);
        Candidato_panel.add(nome);
        
        cognome = new JLabel(Cognome, SwingConstants.CENTER);
        cognome.setFont(new Font(nome.getFont().getName(), Font.BOLD, 25));
        cognome.setBounds(10, 250, 200, 25 );
        Candidato_panel.add(cognome);
        
        partito = new JLabel(Partito, SwingConstants.CENTER);
        partito.setFont(new Font(nome.getFont().getName(), Font.BOLD, 20));
        partito.setBounds(10, 280, 200, 20);
        Candidato_panel.add(partito);
        Candidato_panel.setVisible(true);
      
        return Candidato_panel;
        
    }

/*_________________________BUTTON LISTENER____________________________________*/

public class ButtonClickListener implements ActionListener{

       public void actionPerformed(ActionEvent e){
           String command = e.getActionCommand();
           String CF_regex = "[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}";
          // String CT_regex = "[0-9]{9}";
          String CT_regex = "[A-Z]{2}[0-9]{8}";
           String CF_INV_regex = "(?![A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1})"; // DA PROVARE
          // String CT_INV_regex = "(?![0-9]{9})";
          String CT_INV_regex = "(?![A-Z]{2}[0-9]{8})";
           
//______________________________________________________________________________     

           switch(command) {
               
               case "Registrazione": 
               {
                if(CF.getText().matches(CF_regex) && CT.getText().matches(CT_regex)){
                        boolean founded_CF = canVoteCF(CF.getText()); // Booleano definito dal Metodo
                        boolean founded_CT = canVoteCT(CT.getText()); // Booleano definito dal Metodo
                        
                        if (founded_CF==true && founded_CT==true) { // Se VERE sia CF sia CT allora Spawna la ClientGUI
                            prepareClientGUI();
                        }
                        else if(founded_CF==true && founded_CT==false) {
                            JOptionPane.showMessageDialog(null,"Codice Tessera Elettorale non Trovato,se corretto è possibile che lei non sia residente nel comune dove si vuole Votare\nRiprovare","ERRORE",JOptionPane.ERROR_MESSAGE);
                            CT.setText("");
                        }
                        else if(founded_CF==false && founded_CT==true){
                            JOptionPane.showMessageDialog(null,"Codice Fiscale non Trovato,se corretto è possibile che lei non sia residente nel comune dove si vuole Votare.\nRiprovare","ERRORE",JOptionPane.ERROR_MESSAGE);
                            CF.setText("");
                        }
                        else if(founded_CF==false && founded_CT==false){
                           JOptionPane.showMessageDialog(null,"Codice Fiscale e Codice Tessera non Trovati.\nRiprovare","ERRORE",JOptionPane.ERROR_MESSAGE);
                           CF.setText("");
                           CT.setText("");
                        }
                        
                } else if(CF.getText().matches(CF_INV_regex) && CT.getText().matches(CT_regex)) {
                      JOptionPane.showMessageDialog(null,"Il Codice Fiscale inserito non sembra avere un formato corretto.\nRiprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
                  }
                else if(CF.getText().matches(CF_regex) && CT.getText().matches(CT_INV_regex)) {
                      JOptionPane.showMessageDialog(null,"Il Codice della Tessera Elettorale inserito non sembra avere un formato corretto.\nRiprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
                  } 
                else if(CF.getText().matches(CF_INV_regex) && CT.getText().matches(CT_INV_regex)) {
                      JOptionPane.showMessageDialog(null,"Entrabi i Dati non sono nel Formato Corretto.\nRiprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
                  }
                    break;
               }

//______________________________________________________________________________               
               case "Admin_Log":
               {
                  if (AdmLog_pwd.getPassword().equals(admin_pwd)) {  // Usato getPassword() in quanto getText() è deprecato poichè lascia la Pass in memoria
                         prepareServerGUI.setVisible(true);
                         AdmLog_pwd.setText(null);
                         AdmLog_ErrPwd.setText(null);
                         break; 
                  }
                  else { 
                      AdmLog_ErrPwd.setText("Password Errata: Accesso Negato");
                      AdmLog_ErrPwd.setForeground(Color.red);
                      timer.schedule( task, 2000 );
                      break;
                  }
               }
//______________________________________________________________________________ 
               case "Vota":
               {
                   // aggiungere
                   break;
               }
//______________________________________________________________________________                
               case "Close_Vot":
               {
                   //
                   break;
               }
               default: break;
                   
           }

 
       }
    }

////////////////////////////////////////////////////////////////////////////////

// Metodo Grafico per definire dimensioni Immagine

private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {     // resize foto dei candidati (nei pannelli di createPan) per fit jButton
    Image img = icon.getImage();  
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
    return new ImageIcon(resizedImage);
}

// Metodo di Ricerca Dati Elettorali

private boolean canVoteCF(String CF) {
    
        ArrayList<votanti> vot = mysql.ReadVotantiColumns();
                       
        for (votanti v: vot){
            if(v.getCF().toString().equals(CF)){  
                     return true; // Vuol dire che il CF del Votante Esiste ed è Abilitato
            }
            
        }
        return false;
    }

//______________________________________________________________________________

private boolean canVoteCT(String CT) {
    
        ArrayList<votanti> vot = mysql.ReadVotantiColumns();
                       
        for (votanti v: vot){
            if(v.getCodiceTessera().toString().equals(CT)){  
                     return true; // Vuol dire che il CT del Votante Esiste ed è Abilitato
            }
            
        }
        return false;
    }

// Metodo Timer

public class MyTask extends TimerTask {
    @Override
    public void run() {
        AdmLog_ErrPwd.setText(null);
        AdmLog_pwd.setText(null);
    }
    }

}
