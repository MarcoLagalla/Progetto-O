/*

MAIN CLASS

*/
package project.o;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

////////////////////////////////////////////////////////////////////////////////

public class ProjectO {
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
    private JPanel client_background_panel;
    private JLabel Client_Label;
    private JButton Vote_Button;
    
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
    
    public ProjectO() {
        prepareGUI();
    }
///////////////// MAIN /////////////////////////////////////////////////////////
    public static void main(String[] args) {
       ProjectO SwingControl = new ProjectO();
      
    }
////////////////////////////////////////////////////////////////////////////////  

    private void prepareGUI() {
        // MAIN FRAME
        mainFrame = new JFrame();
        mainFrame.setLayout(null);
        mainFrame.setSize(1276, 802);
        mainFrame.setResizable(false);
        
        background_panel = new JPanel(null);
        background_panel.setBackground(Color.white);
        background_panel.setSize(1276, 802);
        mainFrame.add(background_panel);
        
        Intestazione = new JLabel();
        Intestazione.setFont(new Font("Intestazione", Font.ITALIC,25));
        Intestazione.setText("SISTEMA ELETTORALE ELETTRONICO");
        Intestazione.setBounds(420, 10, 1000, 30);
        background_panel.add(Intestazione);
        
        CodiceFis = new JLabel("Inserire CODICE FISCALE");
        CodiceFis.setBounds(30, 100, 200, 30);
        background_panel.add(CodiceFis);
        
        CF = new JTextField();
        CF.setBounds(240, 100, 300, 30);
        background_panel.add(CF);
        
        CodiceTes = new JLabel("Inserire TESSERA ELETTORALE");
        CodiceTes.setBounds(30, 140, 200, 30);
        background_panel.add(CodiceTes);
        
        CT = new JTextField();
        CT.setBounds(240, 140, 300, 30);
        background_panel.add(CT);
        
        Enter = new JButton("REGISTRAZIONE");
        Enter.setActionCommand("Enter");
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
    
    private void prepareClientGUI(){
        // CLIENT FRAME
        clientFrame = new JFrame("SISTEMA ELETTORALE ELETTRONICO");
        clientFrame.setLayout(null);
        clientFrame.setSize(1276, 802);
        clientFrame.setResizable(false);
        
        client_background_panel = new JPanel(null);
        client_background_panel.setSize(1276, 802);
        clientFrame.add(client_background_panel);
        
        Client_Label = new JLabel();
        Client_Label.setFont(new Font("Intestazione", Font.ITALIC,25));
        Client_Label.setText("SCEGLIERE CANDIDATO");
        Client_Label.setBounds(638, 10, 50, 30);
        client_background_panel.add(Client_Label);
        
        Vote_Button = new JButton("VOTA");
        Vote_Button.setActionCommand("Vota");
        Vote_Button.addActionListener(new ButtonClickListener());
        Vote_Button.setBounds(1100, 750, 100, 50);
        client_background_panel.add(Vote_Button);
    }
    
    private void prepareServerGUI(){
        // SERVER FRAME
        serverFrame = new JFrame("GESTIONE SISTEMA ELETTORALE ELETTRONICO");
        serverFrame.setLayout(null);
        serverFrame.setSize(1276, 802);
        serverFrame.setResizable(false);
        
        server_background_panel = new JPanel(null);
        server_background_panel.setSize(1276, 802);
        serverFrame.add(server_background_panel);
        
        Server_Label = new JLabel();
        Server_Label.setFont(new Font("Intestazione", Font.ITALIC,25));
        Server_Label.setText("GESTIONE SISTEMA ELETTORALE");
        Server_Label.setBounds(638, 10, 50, 30);
        server_background_panel.add(Server_Label);
        
        Num_Votanti = new JLabel("NUMERO VOTANTI: ");
        Num_Votanti.setBounds(20, 50, 40, 30);
        server_background_panel.add(Num_Votanti);
        
        Num_Var = new JTextField();
        Num_Var.setBounds(65, 50, 10, 20);
        server_background_panel.add(Num_Var);
        
        Num_Partiti = new JLabel("NUMERO PARTITI: ");
        Num_Partiti.setBounds(20, 84, 40, 30);
        server_background_panel.add(Num_Partiti);
        
        Num_Partiti_Inseriti = new JTextField();
        Num_Partiti_Inseriti.setBounds(65, 84, 10, 20);
        server_background_panel.add(Num_Partiti_Inseriti);
        
        Close_Vot_Button = new JButton("CHIUSURA IMMEDIATA VOTAZIONI");
        Close_Vot_Button.setActionCommand("Close_Vot");
        Close_Vot_Button.addActionListener(new ButtonClickListener());
        Close_Vot_Button.setBounds(1100, 750, 100, 50);
        server_background_panel.add(Close_Vot_Button);

    }
////////////////////////////////////////////////////////////////////////////////

    public class ButtonClickListener implements ActionListener{

       public void actionPerformed(ActionEvent e){
           String command = e.getActionCommand();
           
        // BUTTONS MAINFRAME
           if(command.equals("Enter")){
           
           
           
           }
           else{
           
           
           }
           
        // BUTTON CLIENT_FRAME
           if(command.equals("Vota")){
           
           
           
           }
           
        // BUTTON SERVER FRAME
            if(command.equals("Close_Vot")){
            
            
            }
           
 
       }
    }









}
