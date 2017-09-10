/*      MAIN CLASS      */

package projO_Frames;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.util.*;
import java.io.IOException;
import java.awt.*;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.*;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_A;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
// imports per database
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

// imports interni
import projO_Connettività.MySQlConnection;
import projO_Classi.Candidati;
import projO_Classi.Votanti;
import projO_Classi.Utility;
import projO_Classi.INIFile;
import projO_Connettività.FTPConnection;
// </editor-fold>


//______________________________________________________________________________
/**
 *
 * @author Team
 */


public class ProgettoO {
    // Elementi Grafici Swing per MAINFRAME
    private JFrame mainFrame;
    private JTextField CF;
    private JTextField CT;
    private JLabel CodiceFis;
    private JLabel CodiceTes;
    private JLabel Image_Icon;
    private JLabel Image_Icon_2;
    private JPanel r1,r2,r3,r4,r5,r6,r7,r8; //Pannelli di Riempimento Grid
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
   
    MySQlConnection mysql; 
    INIFile myINI;
    FTPConnection myftp;
    public static Boolean StatoVotazioni = false;
    // Istanzio ServerFrame creato con JFrame Form
    public static ServerFrame prepareServerGUI;

    
    private KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK); 
//______________________________________________________________________________
    
    public ProgettoO() {
        if ( netIsAvailable() ) {
            
            File f = new File(Utility.INI_PATH);
                if (!f.exists() && !f.isDirectory()) { 
                    try {
                     URL website = new URL(Utility.REMOTE_INI_PATH);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream(Utility.INI_PATH);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    } catch (MalformedURLException ex) {} 
                   catch (IOException ex) {}

            }

            myINI = new INIFile(Utility.INI_PATH);
            StatoVotazioni = myINI.getBooleanProperty("Votazione","VotazioneAperta");
           
            mysql = new MySQlConnection();
            prepareServerGUI = new ServerFrame();
            prepareGUI();

        }
        else {
            JOptionPane.showMessageDialog(null,"Non è stata rilevata alcuna connessione a internet.\nPer il funzionamento del programma è necessaria la connesiona a internet.\nVerificare la connessione di rete e riprovare.", "ERRORE", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
//______________________________________________________________________________

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
       ProgettoO SwingControl = new ProgettoO();
    }
//______________________________________________________________________________

    // Metodo Getter
    
    /**
     *
     * @return registrazione
     */
    public static JButton getRegistrazione() {
        return registrazione;
    }
//______________________________________________________________________________
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
            "Are you sure to close this window?", "Really Closing?", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            myftp = new FTPConnection();
            myftp.loadFile(Utility.INI_PATH,Utility.REMOTE_INI_PATH);
            System.exit(0);
        }
    }
});
        Image_Icon = new JLabel();

        Image_Icon.setIcon(setUrlIcon(Utility.IMG_LOGO)); // RELATIVE PATH
        Image_Icon.setSize(350,395);
        mainFrame.add(Image_Icon,BorderLayout.EAST);                            //BorderLayout EAST
        
        Image_Icon_2 = new JLabel();
        Image_Icon_2.setIcon(setUrlIcon(Utility.IMG_LOGO));
        Image_Icon_2.setSize(350,395);
        mainFrame.add(Image_Icon_2,BorderLayout.WEST);
        
        Intestazione = new JLabel("SISTEMA ELETTORALE ELETTRONICO",SwingConstants.CENTER);
        Intestazione.setFont(new Font("Intestazione", Font.BOLD,45));
        mainFrame.add(Intestazione,BorderLayout.NORTH);                         //BorderLayout NORTH
        
//______________________________________________________________________________

        background_panel = new JPanel();
        background_panel.setLayout(new GridLayout(0, 2, 0, 0));
        background_panel.setBackground(Color.white);
        background_panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        mainFrame.add(background_panel,BorderLayout.CENTER);                    //BorderLayout CENTER
        
        // ADD degli Elementi Interni al BackGround Panel
        
        r1=new JPanel();
        r1.setSize(450,55);
        r1.setBackground(Color.WHITE);
        background_panel.add(r1);
        
        r2=new JPanel();
        r2.setSize(450,55);
        r2.setBackground(Color.WHITE);
        background_panel.add(r2);
        
        r3=new JPanel();
        r3.setSize(450,55);
        r3.setBackground(Color.WHITE);
        background_panel.add(r3);
        
        r4=new JPanel();
        r4.setSize(450,55);
        r4.setBackground(Color.WHITE);
        background_panel.add(r4);

   
        CodiceFis = new JLabel("Inserire CODICE FISCALE",SwingConstants.CENTER);
        CodiceFis.setFont(new Font("CF",Font.BOLD,25));
        CodiceFis.setSize(450,5);
        background_panel.add(CodiceFis);

        CF = new JTextField();
        CF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        CF.setText("Inserire qui il Codice Fiscale");
        CF.setFont(new Font("CF_Field",Font.ITALIC,20));
        CF.setForeground(Color.LIGHT_GRAY);
        CF.setSize(450, 20);
        CF.addFocusListener(new FocusListener() {
        @Override
            public void focusGained(FocusEvent e) {
                if (CF.getText().equals("Inserire qui il Codice Fiscale")) {
                    CF.setText("");
                    CF.setFont(new Font("CF_Field",Font.ROMAN_BASELINE,20));
                    CF.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(CF.getText().equals("")) {
                    CF.setText("Inserire qui il Codice Fiscale");
                    CF.setFont(new Font("CT_Field",Font.ITALIC,20));
                    CF.setForeground(Color.LIGHT_GRAY);
                }
            }
         });
        background_panel.add(CF);

        
        CodiceTes = new JLabel("Inserire CODICE TESSERA",SwingConstants.CENTER);
        CodiceTes.setFont(new Font("CT",Font.BOLD,25));
        CodiceTes.setSize(450, 5);
        background_panel.add(CodiceTes);
        
        CT = new JTextField();
        CT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        CT.setText("Inserire qui il codice della Tessera Elettorale");
        CT.setFont(new Font("CT_Field",Font.ITALIC,20));
        CT.setForeground(Color.LIGHT_GRAY);
        CT.setSize(450, 20);
        CT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (CT.getText().equals("Inserire qui il codice della Tessera Elettorale")) {
                    CT.setText("");
                    CT.setFont(new Font("CF_Field",Font.ROMAN_BASELINE,20));
                    CT.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(CT.getText().equals("")) {
                    CT.setText("Inserire qui il codice della Tessera Elettorale");
                    CT.setFont(new Font("CT_Field",Font.ITALIC,20));
                    CT.setForeground(Color.LIGHT_GRAY);
                }
            }
         });
        background_panel.add(CT);
        
        r5=new JPanel();
        r5.setSize(450,35);
        r5.setBackground(Color.WHITE);
        background_panel.add(r5);
        
        r6=new JPanel();
        r6.setSize(450,35);
        r6.setBackground(Color.WHITE);
        background_panel.add(r6);
        
        r7=new JPanel();
        r7.setSize(450,55);
        r7.setBackground(Color.WHITE);
        background_panel.add(r7);
        
        r8=new JPanel();
        r8.setSize(450,55);
        r8.setBackground(Color.WHITE);
        background_panel.add(r8);
           
//______________________________________________________________________________

        registrazione = new JButton("");   
        registrazione.setActionCommand("Registrazione");
        registrazione.setSize(600, 200);
        
        if (StatoVotazioni) {
        registrazione.setEnabled(true);
        registrazione.setIcon(setUrlIcon(Utility.IMG_REGISTRAZIONE_ENABLED));            
        } else {
        registrazione.setEnabled(false);
        registrazione.setIcon(setUrlIcon(Utility.IMG_REGISTRAZIONE_DISABLED));            
        }

        registrazione.addActionListener(new ButtonClickListener());

        mainFrame.add(registrazione,BorderLayout.SOUTH);                        //BorderLayout SOUTH
//______________________________________________________________________________

// Creo la SHORTCUT (CTRL+A) che apre la finestra di Admin Login

        JButton AdmLog_Button = new JButton();
        AdmLog_Button.setAction(new AbstractAction("call AdmLogin") {
        @Override
        public void actionPerformed(ActionEvent call_AdmLog) {
            prepareAdminLoginGUI();
        }
        }); 

        background_panel.getInputMap().put(key, "call_Action");
        background_panel.getActionMap().put("call_Action", AdmLog_Button.getAction());

//______________________________________________________________________________

// Spawn MAINFRAME

    mainFrame.setVisible(true);
    
    }       
    
//______________________________________________________________________________

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

//______________________________________________________________________________
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
        ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
       
        for (Candidati object: can) {  
            SchedaCandidatoFrame scheda = new SchedaCandidatoFrame(object.getCF(), object.getNome(),object.getCognome(),object.getPartito(),object.getImmagine());
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

//______________________________________________________________________________

// Button Listener

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
                               new ServerFrame().setVisible(true);
                               Admin_Login.dispose();
                               break; 
                        }
                   AdmLog_ErrPwd.setText("Password Errata: Accesso Negato");
                   AdmLog_ErrPwd.setForeground(Color.red);
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
                   //
                   break;
               }
               
               default: break;
                   
           }

 
       }
    }
    
//______________________________________________________________________________
    
// Metodo AvoidDoubleReg    
/*    public boolean avoidDoubleReg(){
        
    }
*/
    
//______________________________________________________________________________

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

//______________________________________________________________________________

// Metodo Grafico per definire dimensioni Immagine

    private Icon setUrlIcon(String remoteURL ) {
        ImageIcon img;
        try {
            img = new ImageIcon(new URL(remoteURL));
        } catch (MalformedURLException ex) {
            img = null;
        }
        return img;
    }
    
    private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {     // resize foto dei Candidati (nei pannelli di createPan) per fit jButton
    Image img = icon.getImage();  
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
    return new ImageIcon(resizedImage);
    }

//______________________________________________________________________________
    
// Metodo di Ricerca Dati Elettorali

    private boolean canVoteCF(String CF) {

            ArrayList<Votanti> vot = mysql.ReadVotantiColumns();

            for (Votanti v: vot){
                if(v.getCF().equals(CF)){  
                         return true; // Vuol dire che il CF del Votante Esiste ed è Abilitato
                }

            }
            return false;
    }

//______________________________________________________________________________

    private boolean canVoteCT(String CT) {

            ArrayList<Votanti> vot = mysql.ReadVotantiColumns();

            for (Votanti v: vot){
                if(v.getCodiceTessera().equals(CT)){  
                         return true; // Vuol dire che il CT del Votante Esiste ed è Abilitato
                }

            }
            return false;
        }

//______________________________________________________________________________

// Metodo Timer

    public class MyTask extends TimerTask {
        @Override
        public void run() {
            AdmLog_ErrPwd.setText(null);
            AdmLog_pwd.setText(null);
        }
    }

}



