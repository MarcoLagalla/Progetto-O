package progetto.o;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

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

// imports needed for DatePicker
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;


public class serverFrame_ extends javax.swing.JFrame {

    MySQlConnection mysql = new MySQlConnection();

/*____________________________________COSTRUTTORI__________________________________________*/
    
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
    
/*_______________________________________METODI____________________________________________*/

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
                
                // Lettura e Creazione DataSet dalla Tabella con Attributi Affluenza e Data
                
                /*
                ResultSet data_ = mysql.ExecuteQuery("SELECT Data FROM" ++ "WHERE CodiceFiscale='" + CF.getText() + "';");
                
                try {
                    while (voti_.next()) {
                       voti = voti_.getInt("Voti");
                       voti = voti +1;
                       int res = mysql.UpdateQuery("UPDATE CANDIDATI SET Voti='" + voti + "' WHERE CodiceFiscale='" + CF.getText() + "';");
                       if (res != 0 ) {
                          JOptionPane.showMessageDialog(null,"Votazione andata a buon fine!", "Conferma", JOptionPane.INFORMATION_MESSAGE);
                          ProgettoO.clientFrame.dispose();
                       
                   }else{
                       JOptionPane.showMessageDialog(null,"Votazione non andata a buon fine!", "Errore", JOptionPane.ERROR_MESSAGE);
                   }
                }
                } catch (SQLException ex) {
               
                }
                */
                
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
        listModel.removeAllElements();
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
        Logo01 = new javax.swing.JLabel();
        Logo02 = new javax.swing.JLabel();
        LabelAffluenza = new javax.swing.JLabel();
        LabelVoti = new javax.swing.JLabel();
        vot_Status = new javax.swing.JLabel();
        vot_Status_Lab = new javax.swing.JLabel();
        Pannello_ColumnChart = new javax.swing.JPanel();
        LabelVoti1 = new javax.swing.JLabel();
        Pannello_LineChart = new javax.swing.JPanel();
        Pannello_CakeChart = new javax.swing.JPanel();
        LabelVoti2 = new javax.swing.JLabel();
        fotoWinner = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        openDatePicker = new javax.swing.JButton();
        avanzaGiorno = new javax.swing.JButton();
        stop_Vot = new javax.swing.JButton();
        avvia_Vot = new javax.swing.JButton();
        DataAvvio_Lab1 = new javax.swing.JLabel();
        dataChiusura = new javax.swing.JTextField();
        id_elezione = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Candidati_list = new javax.swing.JList<String>();
        Aggiungi_Candidato = new javax.swing.JButton();
        Rimuovi_Candidato = new javax.swing.JButton();
        Modifica_Candidato = new javax.swing.JButton();
        dataAvvio = new javax.swing.JTextField();
        dataAvvio_Lab = new javax.swing.JLabel();
        dataChiusura_Lab = new javax.swing.JLabel();
        error_msg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PANNELLO GESTIONE");
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);
        getContentPane().setLayout(null);

        Intestazione.setFont(new java.awt.Font("Calibri", 0, 28)); // NOI18N
        Intestazione.setText("PANNELLO AMMINISTRAZIONE ELEZIONI");
        getContentPane().add(Intestazione);
        Intestazione.setBounds(710, 100, 464, 63);

        Logo01.setIcon(new ImageIcon("Immagini/Logo_Server.png")); // RELATIVE PATH
        getContentPane().add(Logo01);
        Logo01.setBounds(490, 31, 190, 190);

        Logo02.setIcon(new ImageIcon("Immagini/Logo_Server.png")); // RELATIVE PATH
        getContentPane().add(Logo02);
        Logo02.setBounds(1216, 31, 190, 190);

        LabelAffluenza.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelAffluenza.setText("Affluenza Votanti nelle relative Giornate");
        getContentPane().add(LabelAffluenza);
        LabelAffluenza.setBounds(1380, 260, 390, 40);

        LabelVoti.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti.setText("VINCITORE");
        getContentPane().add(LabelVoti);
        LabelVoti.setBounds(970, 260, 110, 43);

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        getContentPane().add(vot_Status);
        vot_Status.setBounds(40, 280, 352, 84);

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        vot_Status_Lab.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        vot_Status_Lab.setText("Status Votazioni :");
        getContentPane().add(vot_Status_Lab);
        vot_Status_Lab.setBounds(63, 239, 171, 30);

        Pannello_ColumnChart.setBackground(new java.awt.Color(255, 255, 255));
        Pannello_ColumnChart.setLayout(new java.awt.BorderLayout());
        getContentPane().add(Pannello_ColumnChart);
        Pannello_ColumnChart.setBounds(1280, 330, 550, 330);

        LabelVoti1.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti1.setText("Voti relativi ai Candidati");
        getContentPane().add(LabelVoti1);
        LabelVoti1.setBounds(1450, 680, 220, 43);

        Pannello_LineChart.setBackground(new java.awt.Color(255, 255, 255));
        Pannello_LineChart.setLayout(new java.awt.BorderLayout());
        getContentPane().add(Pannello_LineChart);
        Pannello_LineChart.setBounds(1280, 730, 550, 330);

        Pannello_CakeChart.setBackground(new java.awt.Color(255, 255, 255));
        Pannello_CakeChart.setLayout(new java.awt.BorderLayout());
        getContentPane().add(Pannello_CakeChart);
        Pannello_CakeChart.setBounds(720, 730, 550, 330);

        LabelVoti2.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti2.setText("Percentuale Uomini/Donne");
        getContentPane().add(LabelVoti2);
        LabelVoti2.setBounds(880, 690, 260, 43);
        getContentPane().add(fotoWinner);
        fotoWinner.setBounds(900, 340, 250, 250);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        openDatePicker.setText("Scegli Data");
        openDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDatePickerActionPerformed(evt);
            }
        });

        avanzaGiorno.setFont(new java.awt.Font("28 Days Later", 0, 24)); // NOI18N
        avanzaGiorno.setForeground(new java.awt.Color(120, 29, 3));
        avanzaGiorno.setText("Avanza Giorno");
        avanzaGiorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avanzaGiornoActionPerformed(evt);
            }
        });

        stop_Vot.setText("Stop");
        stop_Vot.setEnabled(false);
        stop_Vot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stop_VotActionPerformed(evt);
            }
        });

        avvia_Vot.setText("Avvia");
        avvia_Vot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avvia_VotActionPerformed(evt);
            }
        });

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        DataAvvio_Lab1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        DataAvvio_Lab1.setText("ID elezione:");

        dataChiusura.setEditable(false);
        dataChiusura.setText(" Selezionare una data");
        dataChiusura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataChiusuraActionPerformed(evt);
            }
        });

        id_elezione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_elezioneActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

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
                        .addContainerGap(357, Short.MAX_VALUE))
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

        dataAvvio.setEditable(false);
        dataAvvio.setText(" ");
        dataAvvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataAvvioActionPerformed(evt);
            }
        });

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        dataAvvio_Lab.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        dataAvvio_Lab.setText("Data Inizio Elezioni:");

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        dataChiusura_Lab.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        dataChiusura_Lab.setText("Data Chiusura Elezioni:");

        error_msg.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataAvvio, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(dataAvvio_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(id_elezione, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(DataAvvio_Lab1)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dataChiusura, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dataChiusura_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(openDatePicker)
                            .addGap(119, 119, 119)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(avanzaGiorno, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                .addComponent(avvia_Vot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(stop_Vot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(error_msg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(1176, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(230, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(avanzaGiorno, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136)
                        .addComponent(dataAvvio_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(DataAvvio_Lab1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_elezione, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dataAvvio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(dataChiusura_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(openDatePicker)
                            .addComponent(dataChiusura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(avvia_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stop_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(error_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1940, 1100);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Aggiungi_CandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aggiungi_CandidatoActionPerformed
        // TODO add your handling code here:
        new addCandidati_frame().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Aggiungi_CandidatoActionPerformed

    private void Rimuovi_CandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rimuovi_CandidatoActionPerformed
        // TODO add your handling code here:
        String candidato = Candidati_list.getSelectedValue();
        if (!(candidato.equals("")) ) {
            String[] tokens = candidato.split("-");    // slitta per ottenere il CF
             String _cf = tokens[1];
             _cf = _cf.replace(" ", ""); // rimuove gli spazi bianchi dal CF
        int reply = JOptionPane.showConfirmDialog(null, "Sei sicuro? Questa operazione cancellerà in maniera definitiva il candidato " + tokens[0] + ".", "Richiesta conferma", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
            {
                try {
                    mysql.UpdateQuery("DELETE FROM CANDIDATI WHERE CodiceFiscale='" + _cf + "';");
                    loadCandidati();
                } catch (Exception ex) {}
            }
        }
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

    private void dataAvvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataAvvioActionPerformed

    }//GEN-LAST:event_dataAvvioActionPerformed

    private void dataChiusuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataChiusuraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataChiusuraActionPerformed

    private void stop_VotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stop_VotActionPerformed
        Votazione.chiudiVotazione();
        avvia_Vot.setEnabled(true);
        stop_Vot.setEnabled(false);
        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse_v2.png"));
        ProgettoO.getRegistrazione().setEnabled(false);
        ProgettoO.getRegistrazione().setIcon(new ImageIcon("Immagini/Button_Registrazione_Disabled.png"));
        fotoWinner.setIcon(new ImageIcon()); // deve gettare il vincitore dalla classe Votazione, cercare la sua foto DAL SERVER (che ha come nome il CF) e settarla come ImageIcon.
    }//GEN-LAST:event_stop_VotActionPerformed

    private void avanzaGiornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avanzaGiornoActionPerformed
        Votazione.AvanzaGiornata();
    }//GEN-LAST:event_avanzaGiornoActionPerformed

    private void openDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDatePickerActionPerformed
         //create frame new object  f
         final JFrame f = new JFrame();
         //set text which is collected by date picker i.e. set date 
         dataChiusura.setText(new DatePicker(f).setPickedDate());
    }//GEN-LAST:event_openDatePickerActionPerformed

    private void avvia_VotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avvia_VotActionPerformed
        if (!(dataChiusura.getText().equals(""))) {
            if (!(id_elezione.getText().equals(""))) {
                // aggiungere controllo che il nome scelto non esista già
                Votazione.inizioVotazione(id_elezione.getText(), dataChiusura.getText());
                dataAvvio.setText(Votazione.getDataInizioVot().toString());
                error_msg.setText("");
                avvia_Vot.setEnabled(false);    // una volta avviata la votazione, il pulsante di avvio viene disattivato fin quando la votazione non finisce
                stop_Vot.setEnabled(true);
                ProgettoO.getRegistrazione().setEnabled(true);
                ProgettoO.getRegistrazione().setIcon(new ImageIcon("Immagini/Button_Registrazione.png"));
                vot_Status.setIcon(new ImageIcon("Immagini/Vot_Aperte_v2.png"));
            }   else error_msg.setText("Errore: è necessario scegliere un identificativo per la votazione!");
        }   else error_msg.setText("Errore: è necessario selezionare una data per la chiusura delle votazioni!");
    }//GEN-LAST:event_avvia_VotActionPerformed

    private void id_elezioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_elezioneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_elezioneActionPerformed

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

/*____________________________________STATO INTERNO________________________________________*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aggiungi_Candidato;
    private javax.swing.JList<String> Candidati_list;
    private javax.swing.JLabel DataAvvio_Lab1;
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
    private javax.swing.JButton avanzaGiorno;
    private javax.swing.JButton avvia_Vot;
    private javax.swing.JTextField dataAvvio;
    private javax.swing.JLabel dataAvvio_Lab;
    private javax.swing.JTextField dataChiusura;
    private javax.swing.JLabel dataChiusura_Lab;
    private javax.swing.JLabel error_msg;
    private javax.swing.JLabel fotoWinner;
    private javax.swing.JTextField id_elezione;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton openDatePicker;
    private javax.swing.JButton stop_Vot;
    private javax.swing.JLabel vot_Status;
    private javax.swing.JLabel vot_Status_Lab;
    // End of variables declaration//GEN-END:variables
}
