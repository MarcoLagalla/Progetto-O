// Classe per il Bot di Votazione Automatica

package bot;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import projO_Classi.Candidati;
import projO_Connettività.MySQlConnection;
//______________________________________________________________________________
/**
 *
 * @author Team
 */
public class MainFrameBot extends javax.swing.JFrame {
    
    String log = "";
    MySQlConnection mysql;
    ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
    javax.swing.DefaultListModel listModel = new javax.swing.DefaultListModel();
    
    /**
     * Creates new form mainFrame
     */
    public MainFrameBot() {
        initComponents();
        mysql = new MySQlConnection();
        
    }
//______________________________________________________________________________
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bt_Start = new javax.swing.JButton();
        bt_Stop = new javax.swing.JButton();
        logger_ScrollPane = new javax.swing.JScrollPane();
        logger = new javax.swing.JList<>();
        sp_Fidget = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        bt_Start.setText("Start");
        bt_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_StartActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 15, 0, 0);
        getContentPane().add(bt_Start, gridBagConstraints);

        bt_Stop.setText("Stop");
        bt_Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_StopActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 354, 0, 15);
        getContentPane().add(bt_Stop, gridBagConstraints);

        logger.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        logger_ScrollPane.setViewportView(logger);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 559;
        gridBagConstraints.ipady = 98;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 15, 16, 15);
        getContentPane().add(logger_ScrollPane, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 15, 0, 0);
        getContentPane().add(sp_Fidget, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_StartActionPerformed
        try {
            sp_Fidget.commitEdit();
        } catch ( java.text.ParseException e ) {e.printStackTrace();}
        
        int fidget_Value = (Integer) sp_Fidget.getValue(); // Valore Int dello Spinner
        int canditato_random = randomRange(getNumCandidati());
        int voti;
        String cf_cand = can.get(canditato_random).getCF();
        ResultSet voti_ = mysql.ExecuteQuery("SELECT Voti FROM CANDIDATI WHERE CodiceFiscale='" + cf_cand + "';");
        try {
            while (voti_.next()) {
               voti = voti_.getInt("Voti");
               voti += fidget_Value;
               int res = mysql.UpdateQuery("UPDATE CANDIDATI SET Voti='" + voti + "' WHERE CodiceFiscale='" + cf_cand + "';");
               log = "Aggiunti "+ voti +" voti al candidato "+ cf_cand;
               listModel.addElement(log); 
            }
        } catch (SQLException ex) {ex.printStackTrace();}
        
    }//GEN-LAST:event_bt_StartActionPerformed

    private void bt_StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_StopActionPerformed
        
    }//GEN-LAST:event_bt_StopActionPerformed
//______________________________________________________________________________
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrameBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrameBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrameBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrameBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrameBot().setVisible(true);
            }
        });
    }
//______________________________________________________________________________    
       
    private int getNumCandidati() {

       return can.size();  // Utilizzo Funzione Interna ad ArrayList per ottenere la Size
    }
//______________________________________________________________________________    
    private int randomRange(int High) {    // High numero Candidati
        Random r = new Random(System.currentTimeMillis());  // Uso un SEED per evitare errori di porting
        int Low = 0;
        int Result = r.nextInt(High-Low) + Low;
        return Result;
    }
    
//______________________________________________________________________________   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Start;
    private javax.swing.JButton bt_Stop;
    private javax.swing.JList<String> logger;
    private javax.swing.JScrollPane logger_ScrollPane;
    private javax.swing.JSpinner sp_Fidget;
    // End of variables declaration//GEN-END:variables
}
