/*

MAIN CLASS

*/
package progetto.o;

import java.util.*;
import java.awt.*;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;



/*____________________________________STATO INTERNO________________________________________*/

public class ProgettoO {
    // Elementi Grafici Swing per MAINFRAME
    private JFrame mainFrame;
    private JTextField CF;
    private JTextField CT;
    private JLabel CodiceFis;
    private JLabel CodiceTes;
    private JLabel Image_Icon;
    private static JButton registrazione; // dichiaro statico per poterlo chiamare da altre classi
    private JLabel Intestazione;
    private JPanel background_panel;
    
    // Elementi Grafici Swing per CLIENT_FRAME
    public static JFrame clientFrame;
    
    private JLabel Client_Label;
    private JPanel client_panel; //GRIGLIA
     
    
    // Elementi grafici per Login Admin
    private JFrame Admin_Login;
    private JLabel AdmLog_ErrPwd;
    private JPasswordField AdmLog_pwd;
    private JButton AdmLog_button;
    private JLabel AdmLog_title;


    // Istanzio Oggetti Utili
    
    private  char[] admin_pwd;                          
    final public String IMG_REMOTE_FOLDER = "/var/www/progettoO/img";
    MySQlConnection mysql; 
    
    // Istanzio serverFrame_ creato con JFrame Form
    serverFrame_ prepareServerGUI;
    
    // Definisco HotKey
    private KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK);       // hotkey per l'azione call_AdmLogin (CTRL+A)

/*____________________________________COSTRUTTORI__________________________________________*/

    public ProgettoO() {
        if ( netIsAvailable() ) {
            mysql = new MySQlConnection();
            prepareServerGUI = new serverFrame_();
            prepareGUI();
            
        }
        else {
            JOptionPane.showMessageDialog(null,"Non è stata rilevata alcuna connessione a internet.\nPer il funzionamento del programma è necessaria la connesiona a internet.\nVerificare la connessione di rete e riprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
/*_______________________________________METODI____________________________________________*/
  
///////////////// MAIN ///////////////////
    public static void main(String[] args) {
       ProgettoO SwingControl = new ProgettoO();
    }
///////////////////////////////////////////

    public static JButton getRegistrazione() {
        return registrazione;
    }

    private void prepareGUI() {         // Creazione finestra principale (login user)
    
        int c = JComponent.WHEN_IN_FOCUSED_WINDOW;      // la shortcut per chiamare la finistra AdminLogin è applicabile solo se MainFrame è FOCUSED
    
        if ( clientFrame != null) {
            clientFrame.dispose();
        }
        if (mainFrame != null) {
            mainFrame.dispose();
        }
        
        mainFrame = null;
        
        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setPreferredSize(screenSize);
        mainFrame.setSize(screenSize);
        mainFrame.setExtendedState(MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        
        background_panel = new JPanel(null);
        background_panel.setBackground(Color.white);
        background_panel.setSize(screenSize);
        mainFrame.add(background_panel,BorderLayout.CENTER);
        
        Image_Icon = new JLabel();
        Image_Icon.setIcon(new ImageIcon("Immagini/Logo.png")); // RELATIVE PATH
        Image_Icon.setBounds(860,180, 350, 395);
        background_panel.add(Image_Icon, BorderLayout.CENTER);
        
        Intestazione = new JLabel();
        Intestazione.setFont(new Font("Intestazione", Font.ITALIC,35));
        Intestazione.setText("SISTEMA ELETTORALE ELETTRONICO");
        Intestazione.setBounds(330, 10, 1000, 30);
        background_panel.add(Intestazione, BorderLayout.NORTH);
//______________________________________________________________________________        
        CodiceFis = new JLabel("Inserire CODICE FISCALE");
        CodiceFis.setFont(new Font("CF",Font.BOLD,18));
        CodiceFis.setBounds(200, 280, 250, 30);
        background_panel.add(CodiceFis , BorderLayout.CENTER);
        
        CF = new JTextField();
        CF.setText("GRRFRC94S23M109E");                                         // PER TEST DA RIMUOVERE
        CF.setFont(new Font("CF_Field",Font.ROMAN_BASELINE,14));
        CF.setBounds(460, 280, 300, 30);
        background_panel.add(CF);
        
        CodiceTes = new JLabel("Inserire CODICE TESSERA");
        CodiceTes.setFont(new Font("CT",Font.BOLD,18));
        CodiceTes.setBounds(200, 340, 255, 30);
        background_panel.add(CodiceTes);
        
        CT = new JTextField();
        CT.setText("231119994");                                               // PER TEST DA RIMUOVERE
        CT.setFont(new Font("CT_Field",Font.ROMAN_BASELINE,14));
        CT.setBounds(460, 340, 300, 30);
        background_panel.add(CT);
//______________________________________________________________________________

        registrazione = new JButton("");   // COMPLETARE  RESIZE
        registrazione.setActionCommand("Registrazione");
        registrazione.setBounds(460, 400, 300, 90);
        registrazione.setEnabled(false);
        ImageIcon icon = new ImageIcon("Immagini/Button_Registrazione_Disabled.png");
        registrazione.setIcon(resizeIcon(icon, registrazione.getWidth() , registrazione.getHeight() ));

        registrazione.addActionListener(new ButtonClickListener());

        background_panel.add(registrazione);
        
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
        Admin_Login.setLocationRelativeTo(null);
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
        Admin_Login.add(AdmLog_ErrPwd);
        
        Admin_Login.setVisible(true);
    }      


private void prepareClientGUI(){            // Creazione finestra votazione (dopo user login)

        clientFrame = new JFrame("SISTEMA ELETTORALE ELETTRONICO");
        
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        clientFrame.setPreferredSize(screenSize);
        clientFrame.setSize(screenSize);
        clientFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        clientFrame.setResizable(false);
        
        
        clientFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
               prepareGUI();
            }
        });
        
        Client_Label = new JLabel("SCEGLIERE CANDIDATO",Client_Label.CENTER);
        Client_Label.setFont(new Font("Intestazione", Font.ITALIC,25));
        Client_Label.setBounds(50, 10, 1000, 50);
        clientFrame.add(Client_Label,BorderLayout.PAGE_START);
        
        JPanel contPane = new JPanel();
        contPane.setBackground(Color.WHITE);
        clientFrame.setContentPane(contPane);
        GridLayout experimentLayout = new GridLayout(0,4,8,20);  // SETTA SPAZIATURE TRA COLONNE E RIGHE
        
      client_panel = new JPanel(experimentLayout); 
      client_panel.setBackground(Color.WHITE);
        ArrayList<candidati> can = mysql.ReadCandidatiColumns();
       
       for (candidati object: can) {
          
         schedaCandidato scheda = new schedaCandidato(object.getCF(), object.getNome(),object.getCognome(),object.getPartito(),object.getImmagine());
         client_panel.add(scheda);
        
        }
       
       client_panel.setVisible(true);
        
       JScrollPane scrollable = new JScrollPane(client_panel);
       scrollable.setViewportView(client_panel);
       scrollable.setPreferredSize(clientFrame.getPreferredSize());
       scrollable.setSize(clientFrame.getSize());
       scrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       clientFrame.getContentPane().add(scrollable);
       clientFrame.setVisible(true);
       clientFrame.pack();
    }       

/*_________________________BUTTON LISTENER____________________________________*/

public class ButtonClickListener implements ActionListener{

       public void actionPerformed(ActionEvent e){
           String command = e.getActionCommand();
           
           String CF_regex = "[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}";
           String CF_INV_regex = "^(?!.*([A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1})).*$"; // DA PROVARE
           

          String CT_regex = "[0-9]{9}"; 
          String CT_INV_regex = "^(?!.*([0-9]{9})).*$";           

           
//______________________________________________________________________________     

           switch(command) {
               
               case "Registrazione": 
               {
                if(CF.getText().matches(CF_regex) && CT.getText().matches(CT_regex)){
                        boolean founded_CF = canVoteCF(CF.getText()); // Booleano definito dal Metodo
                        boolean founded_CT = canVoteCT(CT.getText()); // Booleano definito dal Metodo
                        
                        if (founded_CF==true && founded_CT==true) { // Se VERE sia CF sia CT allora Spawna la ClientGUI
                            prepareClientGUI();
                            mainFrame.dispose();
                            
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
                   admin_pwd = new char[] {'q', 'w', 'e', '1', '2', '3'};
              
                   if (AdmLog_pwd.getPassword().length == admin_pwd.length) // se la lunghezza è diversa, evito il controllo
                        if (Arrays.equals(AdmLog_pwd.getPassword(), admin_pwd)) { 
                               prepareServerGUI.setVisible(true);                           
                               Admin_Login.dispose();
                               break; 
                        }
                   AdmLog_ErrPwd.setText("Password Errata: Accesso Negato");
                   AdmLog_ErrPwd.setForeground(Color.red);
                   break;
               }
//______________________________________________________________________________ 
               case "Vota": // è necessario un metodo che salva Numero Votanti e Giorno in modo da poi venir GETTATO dal metodo "createDataSet" in "serverFrame_"
               {

                   prepareGUI();    // ricrea la home e killa la clientGUI 
                   clientFrame.dispose();
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

private static boolean netIsAvailable() {
    try {
        final URL url = new URL("http://www.google.com");
        final URLConnection conn = url.openConnection();
        conn.connect();
        return true;
    } catch (MalformedURLException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        return false;
    }
}


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



