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

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Candidati_list = new javax.swing.JList<>();
        Aggiungi_Candidato = new javax.swing.JButton();
        Rimuovi_Candidato = new javax.swing.JButton();
        Modifica_Candidato = new javax.swing.JButton();
        Pannello_LineChart = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PANNELLO GESTIONE");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 255, 0));
        jLabel3.setText("VOTAZIONI APERTE");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton4.setText("Avvia");

        jButton5.setText("Stop");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 549, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Informazioni:");

        jLabel4.setText("Voti totali:");

        jTextField1.setEditable(false);

        jLabel5.setText("Numero candidati");

        jTextField2.setEditable(false);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setPreferredSize(new java.awt.Dimension(1, 100));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

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
                        .addContainerGap(177, Short.MAX_VALUE))
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
                .addContainerGap(201, Short.MAX_VALUE))
        );

        Pannello_LineChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Pannello_LineChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(96, 96, 96))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pannello_LineChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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
    private javax.swing.JList<String> Candidati_list;
    private javax.swing.JButton Modifica_Candidato;
    private javax.swing.JPanel Pannello_LineChart;
    private javax.swing.JButton Rimuovi_Candidato;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
