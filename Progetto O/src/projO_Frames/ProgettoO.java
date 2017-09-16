/*      MAIN CLASS      */

package projO_Frames;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.util.*;
import java.io.IOException;
import java.awt.*;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.*;
import javax.swing.*;

// imports per database
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;

// imports interni
import projO_Connettività.MySQlConnection;
import projO_Classi.Candidati;
import projO_Classi.Votanti;
import projO_Classi.Utility;
import projO_Classi.INIFile;
import projO_Classi.Votazione;
import projO_Connettività.FTPConnection;
// </editor-fold>


//______________________________________________________________________________
/**
 *
 * @author Team
 */


public class ProgettoO {
    
    // <editor-fold defaultstate="collapsed" desc="DICHIARAZIONE VARIABILI">
    // Elementi Grafici Swing per MAINFRAME
    private JFrame mainFrame;
    private JTextField codiceFiscale;
    private JTextField codiceTessera;
    private JLabel lb_CodiceFiscale;
    private JLabel lb_CodiceTessera;
    private JLabel lb_ImageIcon;
    private JLabel lb_ImageIcon2;
    private JPanel panel_Riempimento1,panel_Riempimento2,panel_Riempimento3,panel_Riempimento4,panel_Riempimento5,panel_Riempimento6,panel_Riempimento7,panel_Riempimento8; //Pannelli di Riempimento Grid
    private static JButton bt_Registrazione; // dichiaro statico per poterlo chiamare da altre classi
    private JLabel lb_Intestazione;
    private JPanel panel_Background;
    
    // Elementi Grafici Swing per CLIENT_FRAME
    public static JFrame clientFrame;
    private JLabel lb_Client;
    private JPanel panel_Client; //GRIGLIA
     
    
    // Elementi grafici per Login Admin
    private JFrame adminFrame;
    private JLabel lb_ErrorPassword;
    private JPasswordField password_Admin;
    private JButton bt_AdminLogin;
    private JLabel lb_AdminIntestazione;


    // Istanzio Oggetti Utili
    private  char[] adminPassword;                          
    MySQlConnection mySQL; 
    INIFile myINI;
    FTPConnection myFTP;

    /**
     * Definisce lo Stato delle Votazioni attraverso un Boolean
     */
    public static Boolean statoVotazioni = false;
   
    // Istanzio ServerFrame creato con JFrame Form
    public static ServerFrame prepareServerGUI;
  
    private KeyStroke keyShortCut = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK); 
    // </editor-fold>
//___________________________________COSTRUTTORE___________________________________________

    public ProgettoO() {
        
        if ( netIsAvailable() ) {
            
            Utility.downloadINI();
            myINI = new INIFile(Utility.INIPath);
            statoVotazioni = myINI.getBooleanProperty("Votazione","VotazioneAperta");
            mySQL = new MySQlConnection();
            prepareGUI();

        }
        else {
            
            JOptionPane.showMessageDialog(null,"Non è stata rilevata alcuna connessione a internet.\nPer il funzionamento del programma è necessaria la connesiona a internet.\nVerificare la connessione di rete e riprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            
        }
    }
    
//_________________________________MAIN_____________________________________________

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
       ProgettoO SwingControl = new ProgettoO();
    }
//__________________________________GETTER____________________________________________

    
    /**
     *
     * @return reference bottone bt_Registrazione
     */
    public static JButton getRegistrazione() {
        return bt_Registrazione;
    }
    
    
//________________________________METODI GUI______________________________________________
    
// <editor-fold defaultstate="collapsed" desc="MAIN FRAME">
    private void prepareGUI() {         // Creazione finestra principale (login user)
     
        int c = JComponent.WHEN_FOCUSED;      // la shortcut per chiamare la finistra AdminLogin è applicabile solo se MainFrame è FOCUSED
    
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
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.getContentPane().setBackground(Color.WHITE);
        mainFrame.setResizable(true);
      
        
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        if (JOptionPane.showConfirmDialog(mainFrame, 
            "Sei sicuro di volere uscire da questa finestra?", "Richiesta conferma azione.", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            mainFrame.dispose();
            System.exit(0);
        }
    }
});
        lb_ImageIcon = new JLabel();

        lb_ImageIcon.setIcon(Utility.setUrlIcon(Utility.imgLogo)); // RELATIVE PATH
        lb_ImageIcon.setSize(350,395);
        mainFrame.add(lb_ImageIcon,BorderLayout.EAST);                            //BorderLayout EAST
        
        lb_ImageIcon2 = new JLabel();
        lb_ImageIcon2.setIcon(Utility.setUrlIcon(Utility.imgLogo));
        lb_ImageIcon2.setSize(350,395);
        mainFrame.add(lb_ImageIcon2,BorderLayout.WEST);
        
        lb_Intestazione = new JLabel("SISTEMA ELETTORALE ELETTRONICO",SwingConstants.CENTER);
        lb_Intestazione.setFont(new Font("Intestazione", Font.BOLD,45));
        mainFrame.add(lb_Intestazione,BorderLayout.NORTH);                         //BorderLayout NORTH
        
//______________________________________________________________________________

        panel_Background = new JPanel();
        panel_Background.setLayout(new GridLayout(0, 2, 0, 0));
        panel_Background.setBackground(Color.white);
        panel_Background.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        mainFrame.add(panel_Background,BorderLayout.CENTER);                    //BorderLayout CENTER
        
        // ADD degli Elementi Interni al BackGround Panel
        
        panel_Riempimento1=new JPanel();
        panel_Riempimento1.setSize(450,55);
        panel_Riempimento1.setBackground(Color.WHITE);
        panel_Background.add(panel_Riempimento1);
        
        panel_Riempimento2=new JPanel();
        panel_Riempimento2.setSize(450,55);
        panel_Riempimento2.setBackground(Color.WHITE);
        panel_Background.add(panel_Riempimento2);
        
        panel_Riempimento3=new JPanel();
        panel_Riempimento3.setSize(450,55);
        panel_Riempimento3.setBackground(Color.WHITE);
        panel_Background.add(panel_Riempimento3);
        
        panel_Riempimento4=new JPanel();
        panel_Riempimento4.setSize(450,55);
        panel_Riempimento4.setBackground(Color.WHITE);
        panel_Background.add(panel_Riempimento4);

   
        lb_CodiceFiscale = new JLabel("Inserire CODICE FISCALE",SwingConstants.CENTER);
        lb_CodiceFiscale.setFont(new Font("CF",Font.BOLD,25));
        lb_CodiceFiscale.setSize(450,5);
        panel_Background.add(lb_CodiceFiscale);

        codiceFiscale = new JTextField();
        codiceFiscale.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        codiceFiscale.setText("Inserire qui il Codice Fiscale");
        codiceFiscale.setFont(new Font("CF_Field",Font.ITALIC,20));
        codiceFiscale.setForeground(Color.LIGHT_GRAY);
        codiceFiscale.setSize(450, 20);
        codiceFiscale.addFocusListener(new FocusListener() {
        @Override
            public void focusGained(FocusEvent e) {
                if (codiceFiscale.getText().equals("Inserire qui il Codice Fiscale")) {
                    codiceFiscale.setText("");
                    codiceFiscale.setFont(new Font("CF_Field",Font.ROMAN_BASELINE,20));
                    codiceFiscale.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(codiceFiscale.getText().equals("")) {
                    codiceFiscale.setText("Inserire qui il Codice Fiscale");
                    codiceFiscale.setFont(new Font("CT_Field",Font.ITALIC,20));
                    codiceFiscale.setForeground(Color.LIGHT_GRAY);
                }
            }
         });
        panel_Background.add(codiceFiscale);

        
        lb_CodiceTessera = new JLabel("Inserire CODICE TESSERA",SwingConstants.CENTER);
        lb_CodiceTessera.setFont(new Font("CT",Font.BOLD,25));
        lb_CodiceTessera.setSize(450, 5);
        panel_Background.add(lb_CodiceTessera);
        
        codiceTessera = new JTextField();
        codiceTessera.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        codiceTessera.setText("Inserire qui il codice della Tessera Elettorale");
        codiceTessera.setFont(new Font("CT_Field",Font.ITALIC,20));
        codiceTessera.setForeground(Color.LIGHT_GRAY);
        codiceTessera.setSize(450, 20);
        codiceTessera.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (codiceTessera.getText().equals("Inserire qui il codice della Tessera Elettorale")) {
                    codiceTessera.setText("");
                    codiceTessera.setFont(new Font("CF_Field",Font.ROMAN_BASELINE,20));
                    codiceTessera.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(codiceTessera.getText().equals("")) {
                    codiceTessera.setText("Inserire qui il codice della Tessera Elettorale");
                    codiceTessera.setFont(new Font("CT_Field",Font.ITALIC,20));
                    codiceTessera.setForeground(Color.LIGHT_GRAY);
                }
            }
         });
        panel_Background.add(codiceTessera);
        
        panel_Riempimento5=new JPanel();
        panel_Riempimento5.setSize(450,35);
        panel_Riempimento5.setBackground(Color.WHITE);
        panel_Background.add(panel_Riempimento5);
        
        panel_Riempimento6=new JPanel();
        panel_Riempimento6.setSize(450,35);
        panel_Riempimento6.setBackground(Color.WHITE);
        panel_Background.add(panel_Riempimento6);
        
        panel_Riempimento7=new JPanel();
        panel_Riempimento7.setSize(450,55);
        panel_Riempimento7.setBackground(Color.WHITE);
        panel_Background.add(panel_Riempimento7);
        
        panel_Riempimento8=new JPanel();
        panel_Riempimento8.setSize(450,55);
        panel_Riempimento8.setBackground(Color.WHITE);
        panel_Background.add(panel_Riempimento8);

        bt_Registrazione = new JButton("");   
        bt_Registrazione.setActionCommand("Registrazione");
        bt_Registrazione.setSize(600, 200);
        
        if (statoVotazioni) {
        bt_Registrazione.setEnabled(true);
        bt_Registrazione.setIcon(Utility.setUrlIcon(Utility.imgRegistrazioneAbilitata));            
        } else {
        bt_Registrazione.setEnabled(false);
        bt_Registrazione.setIcon(Utility.setUrlIcon(Utility.imgRegistrazioneDisabilitata));            
        }

        bt_Registrazione.addActionListener(new ButtonClickListener());

        mainFrame.add(bt_Registrazione,BorderLayout.SOUTH);                        //BorderLayout SOUTH

// Creo la SHORTCUT (CTRL+A) che apre la finestra di Admin Login

        JButton AdmLog_Button = new JButton();
        AdmLog_Button.setAction(new AbstractAction("call AdmLogin") {
        @Override
        public void actionPerformed(ActionEvent call_AdmLog) {
            prepareAdminLoginGUI();
        }
        }); 

        panel_Background.getInputMap().put(keyShortCut, "call_Action");
        panel_Background.getActionMap().put("call_Action", AdmLog_Button.getAction());

    mainFrame.setVisible(true);
   
    }       
// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="ADMIN LOGIN FRAME">
    private void prepareAdminLoginGUI() {        // Creazione finestra Login per Admin (accede alla finestra Server)
        adminFrame = new JFrame("ADMINISTRATOR LOGIN");
        adminFrame.setLayout(null);
    
        adminFrame.setSize(500, 300);
        adminFrame.setResizable(false);
        adminFrame.setLocationRelativeTo(null);
        lb_AdminIntestazione = new JLabel("Admin password: ");
        lb_AdminIntestazione.setBounds(70, 70, 120, 25);
        adminFrame.add(lb_AdminIntestazione);
        
        password_Admin = new JPasswordField();
        password_Admin.setBounds(210, 70, 200, 25);
        adminFrame.add(password_Admin);
        
        bt_AdminLogin = new JButton("LOGIN");
        bt_AdminLogin.setActionCommand("Admin_Log");
        bt_AdminLogin.addActionListener(new ButtonClickListener());
        bt_AdminLogin.setBounds(170, 150, 150, 50);
        adminFrame.add(bt_AdminLogin);
        
        lb_ErrorPassword = new JLabel();
        lb_ErrorPassword.setBounds(150, 220, 200, 25);
        adminFrame.add(lb_ErrorPassword);
        
        adminFrame.setVisible(true);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="CLIENT FRAME">
    private void prepareClientGUI(){            // Creazione finestra votazione (dopo user login)

        clientFrame = new JFrame("SISTEMA ELETTORALE ELETTRONICO");
        
        clientFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        clientFrame.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        clientFrame.setPreferredSize(screenSize);
        clientFrame.setSize(screenSize);
        clientFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        clientFrame.setResizable(false);
        
        clientFrame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if(!(SchedaCandidatoFrame.isVotato()))
                    if (JOptionPane.showConfirmDialog(clientFrame, 
                        "Non è stata espressa alcuna preferenza.\nSei sicuro di voler uscire da questa finestra?\nNon sarà più possibile registrarsi con questi dati in futuro.", "Richiesta conferma azione.", 
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            clientFrame.dispose();
                            prepareGUI();
                    }
            }
        });
        
        lb_Client = new JLabel("SCEGLIERE CANDIDATO",lb_Client.CENTER);
        lb_Client.setFont(new Font("Intestazione", Font.ITALIC,25));
        lb_Client.setBounds(50, 10, 1000, 50);
        clientFrame.add(lb_Client,BorderLayout.PAGE_START);
        
        JPanel contPane = new JPanel();
        contPane.setBackground(Color.WHITE);
        clientFrame.setContentPane(contPane);
        GridLayout experimentLayout = new GridLayout(0,4,8,20);  // SETTA SPAZIATURE TRA COLONNE E RIGHE
        
        panel_Client = new JPanel(experimentLayout); 
        panel_Client.setBackground(Color.WHITE);
        ArrayList<Candidati> can = mySQL.ReadCandidatiColumns();
       
        for (Candidati object: can) {  
            SchedaCandidatoFrame scheda = new SchedaCandidatoFrame(object.getCF(), object.getNome(),object.getCognome(),object.getPartito(),object.getImmagine());
            panel_Client.add(scheda);       
        }
       
       panel_Client.setVisible(true);
        
       JScrollPane scrollable = new JScrollPane(panel_Client);
       scrollable.setViewportView(panel_Client);
       scrollable.setPreferredSize(clientFrame.getPreferredSize());
       scrollable.setSize(clientFrame.getSize());
       scrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       clientFrame.getContentPane().add(scrollable);
       clientFrame.setVisible(true);
       clientFrame.pack();
    } 
    
// </editor-fold>

//____________________________________LISTENER__________________________________________
    
// <editor-fold defaultstate="collapsed" desc="BUTTON LISTENER">
    /**
     * Button Listener
     */

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
                if(codiceFiscale.getText().matches(CF_regex) && codiceTessera.getText().matches(CT_regex)){
                        boolean founded_CF = canVoteCF(codiceFiscale.getText()); // Booleano definito dal Metodo
                        boolean founded_CT = canVoteCT(codiceTessera.getText()); // Booleano definito dal Metodo
                        
                        if (founded_CF==true && founded_CT==true) { // Se VERE sia codiceFiscale sia codiceTessera allora Spawna la ClientGUI
                            if (!avoidDoubleReg(codiceFiscale.getText(),codiceTessera.getText())) {
                                int res = mySQL.UpdateQuery("UPDATE VOTANTI SET FlagVotato='1' WHERE CodiceFiscale='" + codiceFiscale.getText() + "';");        // setta il flag votato --> impedisce doppio voto
                                if (res != 0) {
                                    Votazione.addAffluenza();
                                    prepareClientGUI(); 
                                }   
                            } else {
                                JOptionPane.showMessageDialog(null,"Sembra che risulti già espresso un voto dalla persona identificata dai seguenti dati:\nCodice Fiscale: " + codiceFiscale.getText() + "\nCodice Tessera: " + codiceTessera.getText(), "Errore" , JOptionPane.ERROR_MESSAGE);
                            }

                        }
                        else if(founded_CF==true && founded_CT==false) {
                            JOptionPane.showMessageDialog(null,"Codice Tessera Elettorale non Trovato,se corretto è possibile che lei non sia residente nel comune dove si vuole Votare\nRiprovare","ERRORE",JOptionPane.ERROR_MESSAGE);
                            codiceTessera.setText("");
                        }
                        else if(founded_CF==false && founded_CT==true){
                            JOptionPane.showMessageDialog(null,"Codice Fiscale non Trovato,se corretto è possibile che lei non sia residente nel comune dove si vuole Votare.\nRiprovare","ERRORE",JOptionPane.ERROR_MESSAGE);
                            codiceFiscale.setText("");
                        }
                        else if(founded_CF==false && founded_CT==false){
                           JOptionPane.showMessageDialog(null,"Codice Fiscale e Codice Tessera non Trovati.\nRiprovare","ERRORE",JOptionPane.ERROR_MESSAGE);
                           codiceFiscale.setText("");
                           codiceTessera.setText("");
                        }
                        
                } else if(codiceFiscale.getText().matches(CF_INV_regex) && codiceTessera.getText().matches(CT_regex)) {
                      JOptionPane.showMessageDialog(null,"Il Codice Fiscale inserito non sembra avere un formato corretto.\nRiprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
                  }
                else if(codiceFiscale.getText().matches(CF_regex) && codiceTessera.getText().matches(CT_INV_regex)) {
                      JOptionPane.showMessageDialog(null,"Il Codice della Tessera Elettorale inserito non sembra avere un formato corretto.\nRiprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
                  } 
                else if(codiceFiscale.getText().matches(CF_INV_regex) && codiceTessera.getText().matches(CT_INV_regex)) {
                      JOptionPane.showMessageDialog(null,"Entrabi i Dati non sono nel Formato Corretto.\nRiprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
                  }
                    break;
               }

//______________________________________________________________________________               
               case "Admin_Log":
               {
                   adminPassword = new char[] {'q', 'w', 'e', '1', '2', '3'};
              
                   if (password_Admin.getPassword().length == adminPassword.length) // se la lunghezza è diversa, evito il controllo
                        if (Arrays.equals(password_Admin.getPassword(), adminPassword)) { 
                               prepareServerGUI = new ServerFrame();
                               prepareServerGUI.setVisible(true);
                               adminFrame.dispose();
                               break; 
                        }
                   lb_ErrorPassword.setText("Password Errata: Accesso Negato");
                   lb_ErrorPassword.setForeground(Color.red);
                   break;
               }
//______________________________________________________________________________ 
               case "Vota": // è necessario un metodo che salva Numero Votanti e Giorno in modo da poi venir GETTATO dal metodo "createDataSet" in "ServerFrame"
               {
                   prepareGUI();    // ricrea la home e killa la clientGUI 
                   clientFrame.dispose();
                   break;
               }
//______________________________________________________________________________                
               case "Close_Vot":
               {
                   break;
               }
               
               default: break;
                   
           }

 
       }
    }
    
// </editor-fold>
    
//_____________________________________METODI_________________________________________
    
// Metodo AvoidDoubleReg    

    /**
     * Metodo per evitare doppia bt_Registrazione dell'Utente
     * @param CF Codice Fiscale Utente
     * @param CT Codice Tessera
     * @return Se l'utente risulta già registrato
     */
public boolean avoidDoubleReg(String CF, String CT){
    
    ArrayList<Votanti> vot = mySQL.ReadVotantiColumns();
    for (Votanti obj: vot) {
        if ((obj.getCodiceTessera().equals(CT))  && (obj.getCF().equals(CF)) ) {
            return obj.getVotato();
        }
    }
    return false;
}

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
    
// Metodo di Ricerca Dati Elettorali
private boolean canVoteCF(String CF) {

        ArrayList<Votanti> vot = mySQL.ReadVotantiColumns();

        for (Votanti v: vot){
            if(v.getCF().equals(CF)){  
                     return true; // Vuol dire che il codiceFiscale del Votante Esiste ed è Abilitato
            }

        }
        return false;
    }

//______________________________________________________________________________
private boolean canVoteCT(String CT) {

            ArrayList<Votanti> vot = mySQL.ReadVotantiColumns();

            for (Votanti v: vot){
                if(v.getCodiceTessera().equals(CT)){  
                         return true; // Vuol dire che il codiceTessera del Votante Esiste ed è Abilitato
                }

            }
            return false;
        }

    /**
     * Metodo Timer per AdminLogin
     */
public class MyTask extends TimerTask {
        @Override
        public void run() {
            lb_ErrorPassword.setText(null);
            password_Admin.setText(null);
        }
    }

}



