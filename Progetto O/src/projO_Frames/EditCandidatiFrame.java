package projO_Frames;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import javax.imageio.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.*;

// imports interni
import projO_Connettività.FTPConnection;
import projO_Connettività.MySQlConnection;
import projO_Classi.Candidato;
import projO_Classi.Persona;
import projO_Classi.Utility;
// </editor-fold>


/**
 * Frame per Edit delle informazioni di un candidato attraverso TextField per modifica dati
 * @author Team
 */
public class EditCandidatiFrame extends javax.swing.JFrame {

    private MySQlConnection mySQL = new MySQlConnection();      // oggetto per connessione a base di dati
    private FTPConnection myFTP = new FTPConnection();          // oggetto per upload tramite FTP
    private String pathImage = "";                              // local path dell' immagine profilo del candidato (restituita da JFileChooser)
    private String candidatoCodiceFiscale;                      // attributo privato per il passaggio del codice fiscale del candidato come arg del costruttore
    
//______________________________________________________________________________   
    /**
     * Creates new form editCandidati_frame
     * @param candidatoCF Codice fiscale del candidato di cui si desidera modificare l' anagrafica.
     */
    public EditCandidatiFrame(String candidatoCF) {
        initComponents();
        this.candidatoCodiceFiscale = candidatoCF;
        bt_Conferma.setEnabled(true);
        fill();
    }
    
    public EditCandidatiFrame(String candidatoCF, boolean editable) {
        initComponents();
        this.candidatoCodiceFiscale = candidatoCF;
        bt_Conferma.setEnabled(editable);
        fill();
    }
    
//______________________________________________________________________________
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_AllContainer = new java.awt.Panel();
        lb_NomeCandidato = new javax.swing.JLabel();
        tf_NomeCandidato = new javax.swing.JTextField();
        tf_Cognome = new javax.swing.JTextField();
        lb_Cognome = new javax.swing.JLabel();
        tf_Partito = new javax.swing.JTextField();
        lb_Partito = new javax.swing.JLabel();
        tf_Comune = new javax.swing.JTextField();
        lb_Comune = new javax.swing.JLabel();
        lb_Sesso = new javax.swing.JLabel();
        cBox_Sesso = new javax.swing.JComboBox<>();
        lb_CF = new javax.swing.JLabel();
        tf_CF = new javax.swing.JTextField();
        lb_DataNascita = new javax.swing.JLabel();
        tf_DataNascita = new javax.swing.JTextField();
        lb_Foto = new javax.swing.JLabel();
        bt_Sfoglia = new javax.swing.JButton();
        bt_Reset = new javax.swing.JButton();
        bt_Conferma = new javax.swing.JButton();
        lb_Intestazione = new javax.swing.JLabel();
        lb_FotoCandidato = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modifica angrafica candidato");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        lb_NomeCandidato.setText("Nome:");

        lb_Cognome.setText("Cognome:");

        lb_Partito.setText("Partito:");

        lb_Comune.setText("Comune:");

        lb_Sesso.setText("Sesso:");

        cBox_Sesso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));

        lb_CF.setText("CodiceFiscale:");

        lb_DataNascita.setText("Data nascita:");

        lb_Foto.setText("Foto Profilo:");

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
        lb_Intestazione.setText("Modifica anagrafica candidato");

        lb_FotoCandidato.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lb_FotoCandidato.setMaximumSize(new java.awt.Dimension(200, 200));
        lb_FotoCandidato.setMinimumSize(new java.awt.Dimension(200, 200));

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
                                .addComponent(bt_Conferma, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lb_Partito)
                                    .addComponent(lb_Cognome)
                                    .addComponent(lb_NomeCandidato)
                                    .addComponent(lb_Comune)
                                    .addComponent(lb_DataNascita)
                                    .addComponent(lb_CF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb_Foto)
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
                                            .addComponent(cBox_Sesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tf_NomeCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tf_Cognome, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tf_Partito, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(tf_CF, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_FotoCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(lb_Cognome)
                    .addComponent(tf_Cognome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_Partito)
                    .addComponent(tf_Partito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Comune)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_Comune, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lb_Sesso)
                                .addComponent(cBox_Sesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_DataNascita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_DataNascita))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_CF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_CF))
                .addGap(35, 35, 35)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addComponent(lb_Foto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_Sfoglia))
                    .addComponent(lb_FotoCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Reset)
                    .addComponent(bt_Conferma))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_AllContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_AllContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//______________________________________________________________________________
    
    private void bt_SfogliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SfogliaActionPerformed
        JFileChooser fc = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fc.addChoosableFileFilter(imageFilter);
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showDialog(this, "Attach");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            pathImage = file.getAbsolutePath();
            ImageIcon img = new ImageIcon(pathImage);
            int offset = lb_FotoCandidato.getInsets().left;
            lb_FotoCandidato.setIcon(Utility.resizeIcon(img, lb_FotoCandidato.getWidth() - offset, lb_FotoCandidato.getHeight() - offset));
        }
    }//GEN-LAST:event_bt_SfogliaActionPerformed
//______________________________________________________________________________
    
    private void bt_ConfermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ConfermaActionPerformed
    editCandidato();
    }//GEN-LAST:event_bt_ConfermaActionPerformed
//______________________________________________________________________________
    
    private void bt_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ResetActionPerformed
        int reply = JOptionPane.showConfirmDialog(null, "Sei sicuro? Questa operazione svuoterà tutti i campi.", "Richiesta conferma", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION){
            clear();
        }
    }//GEN-LAST:event_bt_ResetActionPerformed
//______________________________________________________________________________
    
    private void fill() {
        ArrayList<Candidato> can = mySQL.readCandidatiColumns();
        ArrayList<Persona> pers = mySQL.readPersoneColumns();
           
        for (Candidato object: can) {
            if (object.getCF().equals(candidatoCodiceFiscale)) {  // match 
               ImageIcon img;
               tf_Partito.setText(object.getPartito());
               tf_CF.setText(object.getCF());

                try {
                    img = new ImageIcon(object.getImmagine());
                }catch (Exception ex) {
                    img = new ImageIcon(".\\Immagini\\img_not_found.jpg");
                }
            
            int offset = lb_FotoCandidato.getInsets().left;
            lb_FotoCandidato.setIcon(Utility.resizeIcon(img, lb_FotoCandidato.getWidth() - offset, lb_FotoCandidato.getHeight() - offset));
            
            break;
           }
        }
       
        for (Persona object: pers) {
            if (object.getCF().equals(candidatoCodiceFiscale)) {  // match 
                tf_NomeCandidato.setText(object.getNome());
                tf_Cognome.setText(object.getCognome());
                tf_Comune.setText(object.getComune());
                tf_DataNascita.setText(object.getDataNascita());
            
                if ( object.getSesso().equals("M") )   // hard coded, non mi piace molto
                    cBox_Sesso.setSelectedIndex(0);
                else
                    cBox_Sesso.setSelectedIndex(1);
            }
        }
    }
    
//______________________________________________________________________________
// Metodo per svuotare i TextField
    private void clear() {
        tf_NomeCandidato.setText(null);
        tf_Cognome.setText(null);
        tf_CF.setText(null);
        tf_Partito.setText(null);
        tf_Comune.setText(null);
        tf_DataNascita.setText(null);
        lb_FotoCandidato.setIcon(null);
    }
    
//_________________________________MAIN_________________________________________
    
    private void editCandidato() {
        myFTP.loadFile(pathImage,Utility.imgRemoteFolder + "/" + tf_CF.getText() + ".jpg"); // upload immagine tramite FTP
        mySQL.updateQuery("DELETE FROM CANDIDATI WHERE CodiceFiscale='" + tf_CF.getText() + "';"); // elimina il record in CANDIDATI
        mySQL.updateQuery("DELETE FROM PERSONE WHERE CodiceFiscale='" + tf_CF.getText() + "';"); // elimina il record in PERSONE sul CF
        
        int ret = mySQL.writePersoneColumns(tf_CF.getText(), tf_NomeCandidato.getText(), tf_Cognome.getText(), cBox_Sesso.getSelectedItem().toString(), tf_DataNascita.getText(), tf_Comune.getText());
        int ret2 = mySQL.writeCandidatiColumns(tf_CF.getText(), tf_Partito.getText(), 0, Utility.urlImageRemote + "/" + tf_CF.getText() + ".jpg");
        
        if ( ( ret != 0 ) && ( ret2 != 0) ) {
            JOptionPane.showMessageDialog(null,"Inserimento completato.\nDB Aggiornato.", "Conferma", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null,"Inserimento non completato.", "Errore", JOptionPane.ERROR);
        }
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
            java.util.logging.Logger.getLogger(EditCandidatiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditCandidatiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditCandidatiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditCandidatiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditCandidatiFrame("").setVisible(true);
              
            }
        });
    }
//______________________________________________________________________________
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton bt_Conferma;
    private javax.swing.JButton bt_Reset;
    private javax.swing.JButton bt_Sfoglia;
    private javax.swing.JComboBox<String> cBox_Sesso;
    private javax.swing.JLabel lb_CF;
    private javax.swing.JLabel lb_Cognome;
    private javax.swing.JLabel lb_Comune;
    private javax.swing.JLabel lb_DataNascita;
    private javax.swing.JLabel lb_Foto;
    private javax.swing.JLabel lb_FotoCandidato;
    private javax.swing.JLabel lb_Intestazione;
    private javax.swing.JLabel lb_NomeCandidato;
    private javax.swing.JLabel lb_Partito;
    private javax.swing.JLabel lb_Sesso;
    private java.awt.Panel panel_AllContainer;
    private javax.swing.JTextField tf_CF;
    private javax.swing.JTextField tf_Cognome;
    private javax.swing.JTextField tf_Comune;
    private javax.swing.JTextField tf_DataNascita;
    private javax.swing.JTextField tf_NomeCandidato;
    private javax.swing.JTextField tf_Partito;
    // End of variables declaration//GEN-END:variables
}
