package projO_Frames;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import javax.imageio.*;
import java.io.File;
import java.awt.Image;
import javax.swing.*;
import javax.swing.filechooser.*;
import projO_Classi.Utility;

// imports interni
import projO_Connettività.FTPConnection;
import projO_Connettività.MySQlConnection;
// </editor-fold>


/**
 *
 * @author Team
 */
public class AddCandidatiFrame extends javax.swing.JFrame {

    MySQlConnection mysql = new MySQlConnection();
    FTPConnection myftp = new FTPConnection();

    /**
     *
     */
    final public String IMG_REMOTE_FOLDER = "/var/www/progettoO/img";
    String path_img = "";
    
    
    static String SERVER = "http://91.134.138.244/progettoO/img/";
    /**
     * Creates new form addCandidati_frame
     */
    public AddCandidatiFrame() {
        initComponents();
    }

//______________________________________________________________________________
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_AllContainer = new java.awt.Panel();
        lb_NomeCandidato = new javax.swing.JLabel();
        tf_NomeCandidato = new javax.swing.JTextField();
        tf_CognomeCandidato = new javax.swing.JTextField();
        lb_CognomeCandidato = new javax.swing.JLabel();
        tf_PartitoAppartenenza = new javax.swing.JTextField();
        lb_Partito = new javax.swing.JLabel();
        tf_Comune = new javax.swing.JTextField();
        lb_Comune = new javax.swing.JLabel();
        lb_Sesso = new javax.swing.JLabel();
        lb_CodiceFiscale = new javax.swing.JLabel();
        tf_CodiceFiscale = new javax.swing.JTextField();
        lb_DataNascita = new javax.swing.JLabel();
        tf_DataNascita = new javax.swing.JTextField();
        lb_FotoProfilo = new javax.swing.JLabel();
        bt_Sfoglia = new javax.swing.JButton();
        bt_Reset = new javax.swing.JButton();
        bt_Conferma = new javax.swing.JButton();
        lb_Intestazione = new javax.swing.JLabel();
        cBox_InputSesso = new javax.swing.JComboBox<>();
        lb_Foto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Creazione nuovo candidato");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setResizable(false);

        lb_NomeCandidato.setText("Nome:");

        tf_NomeCandidato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_NomeCandidatoActionPerformed(evt);
            }
        });

        lb_CognomeCandidato.setText("Cognome:");

        lb_Partito.setText("Partito:");

        lb_Comune.setText("Comune:");

        lb_Sesso.setText("Sesso:");

        lb_CodiceFiscale.setText("CodiceFiscale:");

        lb_DataNascita.setText("Data nascita:");

        lb_FotoProfilo.setText("Foto Profilo:");

        bt_Sfoglia.setText("Sfoglia");
        bt_Sfoglia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SfogliaActionPerformed(evt);
            }
        });

        bt_Reset.setText("Reset");
        bt_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ResetActionPerformed(evt);
            }
        });

        bt_Conferma.setText("Conferma");
        bt_Conferma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ConfermaActionPerformed(evt);
            }
        });

        lb_Intestazione.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_Intestazione.setText("Creazione nuovo candidato");

        cBox_InputSesso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));

        lb_Foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lb_Foto.setMaximumSize(new java.awt.Dimension(200, 200));
        lb_Foto.setMinimumSize(new java.awt.Dimension(200, 200));

        javax.swing.GroupLayout panel_AllContainerLayout = new javax.swing.GroupLayout(panel_AllContainer);
        panel_AllContainer.setLayout(panel_AllContainerLayout);
        panel_AllContainerLayout.setHorizontalGroup(
            panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_AllContainerLayout.createSequentialGroup()
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bt_Conferma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lb_Partito)
                                    .addComponent(lb_CognomeCandidato)
                                    .addComponent(lb_NomeCandidato)
                                    .addComponent(lb_Comune)
                                    .addComponent(lb_DataNascita)
                                    .addComponent(lb_CodiceFiscale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb_FotoProfilo)
                                    .addComponent(bt_Sfoglia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_AllContainerLayout.createSequentialGroup()
                                .addGap(226, 226, 226)
                                .addComponent(bt_Reset))
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                            .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tf_DataNascita, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(tf_Comune, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lb_Sesso)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cBox_InputSesso, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tf_NomeCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tf_CognomeCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tf_PartitoAppartenenza, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(tf_CodiceFiscale, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_Foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addComponent(lb_Intestazione)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_AllContainerLayout.setVerticalGroup(
            panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                .addComponent(lb_Intestazione)
                .addGap(5, 5, 5)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_NomeCandidato)
                    .addComponent(tf_NomeCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_CognomeCandidato)
                    .addComponent(tf_CognomeCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_Partito)
                    .addComponent(tf_PartitoAppartenenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_Comune, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_Comune))
                    .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_Sesso)
                        .addComponent(cBox_InputSesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_DataNascita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_DataNascita))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_CodiceFiscale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_CodiceFiscale))
                .addGap(35, 35, 35)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addComponent(lb_FotoProfilo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_Sfoglia))
                    .addComponent(lb_Foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Reset)
                    .addComponent(bt_Conferma))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_AllContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_AllContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_NomeCandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_NomeCandidatoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_NomeCandidatoActionPerformed
//______________________________________________________________________________
    
    private void bt_SfogliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SfogliaActionPerformed
        // TODO add your handling code here:  
        JFileChooser fc = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fc.addChoosableFileFilter(imageFilter);
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showDialog(this, "Attach");
        
         if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
             path_img = file.getAbsolutePath();
             
             ImageIcon img = new ImageIcon(path_img);
            int offset = lb_Foto.getInsets().left;
            lb_Foto.setIcon(Utility.resizeIcon(img, lb_Foto.getWidth() - offset, lb_Foto.getHeight() - offset));
            } 
    }//GEN-LAST:event_bt_SfogliaActionPerformed
//______________________________________________________________________________
    
    private void bt_ConfermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ConfermaActionPerformed

      myftp.loadFile(path_img,IMG_REMOTE_FOLDER + "/" + tf_CodiceFiscale.getText() + ".jpg");
      int ret = mysql.WritePersoneColumns(tf_CodiceFiscale.getText(), tf_NomeCandidato.getText(), tf_CognomeCandidato.getText(), cBox_InputSesso.getSelectedItem().toString(), tf_DataNascita.getText(), tf_Comune.getText());
      
      int ret2 = mysql.WriteCandidatiColumns(tf_CodiceFiscale.getText(), tf_PartitoAppartenenza.getText(), 0, SERVER + tf_CodiceFiscale.getText() + ".jpg");
      if (ret != 0 && ret2 != 0) {
          JOptionPane.showMessageDialog(null,"Inserimento completato.\nDB Aggiornato.", "Conferma", JOptionPane.OK_OPTION);
          
          new ServerFrame().setVisible(true);
          this.dispose();
      } else {
           JOptionPane.showMessageDialog(null,"Inserimento non completato.", "Errore", JOptionPane.ERROR);
      }
    }//GEN-LAST:event_bt_ConfermaActionPerformed
//______________________________________________________________________________
    private void bt_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ResetActionPerformed
        // TODO add your handling code here:
        int reply = JOptionPane.showConfirmDialog(null, "Sei sicuro? Questa operazione svuoterà tutti i campi.", "Richiesta conferma", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
            {
                clear();
            }
    }//GEN-LAST:event_bt_ResetActionPerformed
//______________________________________________________________________________
    
    private void clear() {
        tf_NomeCandidato.setText(null);
        tf_CognomeCandidato.setText(null);
        tf_CodiceFiscale.setText(null);
        tf_PartitoAppartenenza.setText(null);
        tf_Comune.setText(null);
        tf_DataNascita.setText(null);
        lb_Foto.setIcon(null);
    }
    
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
            java.util.logging.Logger.getLogger(AddCandidatiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCandidatiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCandidatiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCandidatiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddCandidatiFrame().setVisible(true);
            }
        });
    }
//______________________________________________________________________________
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Conferma;
    private javax.swing.JButton bt_Reset;
    private javax.swing.JButton bt_Sfoglia;
    private javax.swing.JComboBox<String> cBox_InputSesso;
    private javax.swing.JLabel lb_CodiceFiscale;
    private javax.swing.JLabel lb_CognomeCandidato;
    private javax.swing.JLabel lb_Comune;
    private javax.swing.JLabel lb_DataNascita;
    private javax.swing.JLabel lb_Foto;
    private javax.swing.JLabel lb_FotoProfilo;
    private javax.swing.JLabel lb_Intestazione;
    private javax.swing.JLabel lb_NomeCandidato;
    private javax.swing.JLabel lb_Partito;
    private javax.swing.JLabel lb_Sesso;
    private java.awt.Panel panel_AllContainer;
    private javax.swing.JTextField tf_CodiceFiscale;
    private javax.swing.JTextField tf_CognomeCandidato;
    private javax.swing.JTextField tf_Comune;
    private javax.swing.JTextField tf_DataNascita;
    private javax.swing.JTextField tf_NomeCandidato;
    private javax.swing.JTextField tf_PartitoAppartenenza;
    // End of variables declaration//GEN-END:variables
}
