/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.o;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
// Imports needed from JFREECHART_LIBRARY
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
//______________________________________________________________________________

public class serverFrame_ extends javax.swing.JFrame {

    

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public serverFrame_() {
            super("SERVER");
            setExtendedState(MAXIMIZED_BOTH);
            initComponents(); // Provenienti dal precedente Costruttore
            loadCandidati(); //
            
            JPanel chartPanel = createChartPanel();
            Pannello_LineChart.add(chartPanel,BorderLayout.CENTER); // Aggiungo il LineChart al Pannello Designato
            Pannello_LineChart.validate();
            //add(chartPanel, BorderLayout.CENTER);
            
     //       setSize(653, 579); // Definisco la Size uguale a quella del Pannello dove andrà sopra
            
            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
        }

        // CREAZIONE CHART PANEL (Pannello del Grafico)
    
        private JPanel createChartPanel() {
            String chartTitle = "Andamento Votazioni a chiusura Elezioni"; // NOME GRAFICO
            String xAxisLabel = "VOTANTI"; // NOME ASSE ASCISSE
            String yAxisLabel = "GIORNI"; // NOME ASSE ORDINATE
            
            /* 
            QUESTO è quello che ha dentro la Classe CREATE_CHART_PANEL:
            
            createXYLineChart(title, categoryAxisLabel, valueAxisLabel, dataset)
            createXYLineChart(title, categoryAxisLabel, valueAxisLabel, dataset, orientation, legend, tooltips, urls)
            
            */
            
            XYDataset dataset = createDataset();

            JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,xAxisLabel, yAxisLabel, dataset);
            
            // CUSTOM GRAPHICS
            
            XYPlot plot = chart.getXYPlot();
            
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            plot.setRenderer(renderer);
            
            // sets paint color for each series
            renderer.setSeriesPaint(0, Color.RED);

            // sets thickness for series (using strokes)
            renderer.setSeriesStroke(0, new BasicStroke(2.0f));

           
            
            plot.setRenderer(renderer);

            // BackGround Color
            plot.setBackgroundPaint(Color.DARK_GRAY);
            
            // BackGround Grid Color
            plot.setRangeGridlinesVisible(true);
            plot.setRangeGridlinePaint(Color.GRAY);
            
            plot.setDomainGridlinesVisible(true);
            plot.setDomainGridlinePaint(Color.GRAY);
            
            /*
            // SAVING CHART AS IMAGE
            File imageFile = new File("XYLineChart.png");
            int width = 640;
            int height = 480;

            try {
                ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
            } catch (IOException ex) {
                System.err.println(ex);
            }
            */
            
            
            return new ChartPanel(chart);
        }

        // CREAZIONE DATASET:__________________________DA RIEMPIRE CON DATI VERI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        private XYDataset createDataset() {
                XYSeriesCollection dataset = new XYSeriesCollection(); // DataSet e Series fanno parte dell' IMPORT
                XYSeries series1 = new XYSeries("Object 1"); // DataSet
                
                // Qui ci vogliono i GET
                series1.add(1.0, 2.0); 
                series1.add(2.0, 3.0);
                series1.add(3.0, 2.5);
                series1.add(3.5, 2.8);
                series1.add(4.2, 6.0);
                
                // Questa di Default aggiunge in ordine di Sort quindi se voglio metterli in ordine che deicido, ovvero di Aggiunta devo fare:
               /* 
                
                boolean autoSort = false;
                XYSeries series = new XYSeries("Object 1", autoSort);
                
                */
                
                dataset.addSeries(series1);


                return dataset;
            
        }                                                                                                                        

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    MySQlConnection mysql = new MySQlConnection();
    /**
     * Creates new form serverFrame_
     */
    /*public serverFrame_() {
        initComponents();
        loadCandidati();                // popola la jList con l' elenco dei candidati
    }*/
    
    private void loadCandidati() {
        ArrayList<candidati> can = mysql.ReadCandidatiColumns();
        javax.swing.DefaultListModel listModel;
        listModel = new javax.swing.DefaultListModel();
        for (candidati object: can) {
            String str = String.format("%s %s - %s",object.getNome().toString(), object.getCognome().toString(), object.getCF().toString());
            listModel.addElement(str);
        }
        Candidati_list.setModel(listModel);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Intestazione = new javax.swing.JLabel();
        Avvia_Vot = new javax.swing.JButton();
        Logo01 = new javax.swing.JLabel();
        Logo02 = new javax.swing.JLabel();
        DataAvvio = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Candidati_list = new javax.swing.JList<>();
        Aggiungi_Candidato = new javax.swing.JButton();
        Rimuovi_Candidato = new javax.swing.JButton();
        Modifica_Candidato = new javax.swing.JButton();
        LabelAffluenza = new javax.swing.JLabel();
        LabelVoti = new javax.swing.JLabel();
        Vot_Status = new javax.swing.JLabel();
        Vot_Status_Lab = new javax.swing.JLabel();
        DataAvvio_Lab = new javax.swing.JLabel();
        DataChiusura_Lab = new javax.swing.JLabel();
        DataChiusura = new javax.swing.JTextField();
        Pannello_ColumnChart = new javax.swing.JPanel();
        Avvia_Vot1 = new javax.swing.JButton();
        LabelVoti1 = new javax.swing.JLabel();
        Pannello_LineChart = new javax.swing.JPanel();
        Pannello_CakeChart = new javax.swing.JPanel();
        LabelVoti2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PANNELLO GESTIONE");
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);
        getContentPane().setLayout(null);

        Intestazione.setFont(new java.awt.Font("Calibri", 0, 30)); // NOI18N
        Intestazione.setText("PANNELLO AMMINISTRAZIONE ELEZIONI");
        getContentPane().add(Intestazione);
        Intestazione.setBounds(690, 100, 500, 63);

        Avvia_Vot.setText("Stop");
        Avvia_Vot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Avvia_VotActionPerformed(evt);
            }
        });
        getContentPane().add(Avvia_Vot);
        Avvia_Vot.setBounds(430, 330, 201, 43);

        Logo01.setIcon(new ImageIcon("Immagini/Logo_Server.png")); // RELATIVE PATH
        getContentPane().add(Logo01);
        Logo01.setBounds(490, 31, 190, 190);

        Logo02.setIcon(new ImageIcon("Immagini/Logo_Server.png")); // RELATIVE PATH
        getContentPane().add(Logo02);
        Logo02.setBounds(1216, 31, 190, 190);

        DataAvvio.setEditable(false);
        DataAvvio.setText("GG - MM - YYYY");
        DataAvvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataAvvioActionPerformed(evt);
            }
        });
        getContentPane().add(DataAvvio);
        DataAvvio.setBounds(63, 452, 246, 48);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("Gestione candidati");

        Candidati_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(Candidati_list);

        Aggiungi_Candidato.setText("Aggiungi");
        Aggiungi_Candidato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Aggiungi_CandidatoActionPerformed(evt);
            }
        });

        Rimuovi_Candidato.setText("Rimuovi");
        Rimuovi_Candidato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rimuovi_CandidatoActionPerformed(evt);
            }
        });

        Modifica_Candidato.setText("Modifica");
        Modifica_Candidato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Modifica_CandidatoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(Aggiungi_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Modifica_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Rimuovi_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(152, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addGap(29, 29, 29))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aggiungi_Candidato)
                    .addComponent(Rimuovi_Candidato)
                    .addComponent(Modifica_Candidato))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(70, 670, 503, 372);

        LabelAffluenza.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelAffluenza.setText("Affluenza Votanti nelle relative Giornate");
        getContentPane().add(LabelAffluenza);
        LabelAffluenza.setBounds(1380, 260, 390, 40);

        LabelVoti.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti.setText("VINCITORE");
        getContentPane().add(LabelVoti);
        LabelVoti.setBounds(970, 260, 110, 43);

        Vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        getContentPane().add(Vot_Status);
        Vot_Status.setBounds(40, 280, 352, 84);

        Vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        Vot_Status_Lab.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        Vot_Status_Lab.setText("Status Votazioni :");
        getContentPane().add(Vot_Status_Lab);
        Vot_Status_Lab.setBounds(63, 239, 171, 30);

        Vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        DataAvvio_Lab.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        DataAvvio_Lab.setText("Data Inizio Elezioni :");
        getContentPane().add(DataAvvio_Lab);
        DataAvvio_Lab.setBounds(63, 400, 198, 43);

        Vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        DataChiusura_Lab.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        DataChiusura_Lab.setText("Data Chiusura Elezioni :");
        getContentPane().add(DataChiusura_Lab);
        DataChiusura_Lab.setBounds(63, 527, 232, 43);

        DataChiusura.setEditable(false);
        DataChiusura.setText("GG - MM - YYYY");
        DataChiusura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataChiusuraActionPerformed(evt);
            }
        });
        getContentPane().add(DataChiusura);
        DataChiusura.setBounds(63, 579, 246, 48);

        Pannello_ColumnChart.setLayout(new java.awt.BorderLayout());
        getContentPane().add(Pannello_ColumnChart);
        Pannello_ColumnChart.setBounds(1280, 330, 550, 330);

        Avvia_Vot1.setText("Avvia");
        getContentPane().add(Avvia_Vot1);
        Avvia_Vot1.setBounds(430, 280, 201, 43);

        LabelVoti1.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti1.setText("Voti relativi ai Candidati");
        getContentPane().add(LabelVoti1);
        LabelVoti1.setBounds(1450, 680, 220, 43);

        Pannello_LineChart.setLayout(new java.awt.BorderLayout());
        getContentPane().add(Pannello_LineChart);
        Pannello_LineChart.setBounds(1280, 730, 550, 330);

        Pannello_CakeChart.setLayout(new java.awt.BorderLayout());
        getContentPane().add(Pannello_CakeChart);
        Pannello_CakeChart.setBounds(720, 730, 550, 330);

        LabelVoti2.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti2.setText("Percentuale Uomini/Donne");
        getContentPane().add(LabelVoti2);
        LabelVoti2.setBounds(880, 690, 260, 43);

        jLabel1.setText("FOTO VINCITORE");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(900, 340, 250, 250);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Aggiungi_CandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aggiungi_CandidatoActionPerformed
        // TODO add your handling code here:
        new addCandidati_frame().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Aggiungi_CandidatoActionPerformed

    private void Rimuovi_CandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rimuovi_CandidatoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rimuovi_CandidatoActionPerformed

    private void Modifica_CandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Modifica_CandidatoActionPerformed
        // TODO add your handling code here:
        String candidato = Candidati_list.getSelectedValue();
        if (!(candidato.equals("")) ) {
            String[] tokens = candidato.split("-");    // slitta per ottenere il CF
             String _cf = tokens[1];
             _cf = _cf.replace(" ", ""); // rimuove gli spazi bianchi dal CF
          new editCandidati_frame(_cf).setVisible(true);
         this.setVisible(false);
            
        }
    }//GEN-LAST:event_Modifica_CandidatoActionPerformed

    private void DataAvvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataAvvioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DataAvvioActionPerformed

    private void DataChiusuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataChiusuraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DataChiusuraActionPerformed

    private void Avvia_VotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Avvia_VotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Avvia_VotActionPerformed

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
            java.util.logging.Logger.getLogger(serverFrame_.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(serverFrame_.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(serverFrame_.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(serverFrame_.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new serverFrame_().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aggiungi_Candidato;
    private javax.swing.JButton Avvia_Vot;
    private javax.swing.JButton Avvia_Vot1;
    private javax.swing.JList<String> Candidati_list;
    private javax.swing.JTextField DataAvvio;
    private javax.swing.JLabel DataAvvio_Lab;
    private javax.swing.JTextField DataChiusura;
    private javax.swing.JLabel DataChiusura_Lab;
    private javax.swing.JLabel Intestazione;
    private javax.swing.JLabel LabelAffluenza;
    private javax.swing.JLabel LabelVoti;
    private javax.swing.JLabel LabelVoti1;
    private javax.swing.JLabel LabelVoti2;
    private javax.swing.JLabel Logo01;
    private javax.swing.JLabel Logo02;
    private javax.swing.JButton Modifica_Candidato;
    private javax.swing.JPanel Pannello_CakeChart;
    private javax.swing.JPanel Pannello_ColumnChart;
    private javax.swing.JPanel Pannello_LineChart;
    private javax.swing.JButton Rimuovi_Candidato;
    private javax.swing.JLabel Vot_Status;
    private javax.swing.JLabel Vot_Status_Lab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
