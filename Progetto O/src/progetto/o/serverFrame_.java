package progetto.o;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

// imports needed for DatePicker
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import static progetto.o.ProgettoO.clientFrame;


public class serverFrame_ extends javax.swing.JFrame {

    MySQlConnection mysql = new MySQlConnection();

/*____________________________________COSTRUTTORI__________________________________________*/
    
    public serverFrame_() {
            super("SERVER");
            setExtendedState(MAXIMIZED_BOTH);
            initComponents(); // Provenienti dal precedente Costruttore
            

            // FULLSCREEN MODE
            
            Toolkit screen = Toolkit.getDefaultToolkit();
                int xsize = (int) screen.getScreenSize().getWidth();
                int ysize = (int) screen.getScreenSize().getHeight();
                this.setSize(xsize, ysize);
            
            
                
                
            loadCandidati(); 
            
            JPanel chartPanel = createChartPanel();
            Pannello_LineChart.add(chartPanel,BorderLayout.CENTER); // Aggiungo il LineChart al Pannello Designato
            Pannello_LineChart.validate();
            //add(chartPanel, BorderLayout.CENTER);
            
            JPanel barC = BarChart_AWT("Andamento voti");
            Pannello_ColumnChart.add(barC,BorderLayout.CENTER);
            Pannello_ColumnChart.validate();
     //       setSize(653, 579); // Definisco la Size uguale a quella del Pannello dove andrà sopra
            
            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            
            ImageIcon icon = new ImageIcon("Immagini/Logo_Server.png");
            Logo01.setIcon(resizeIcon(icon, 145,145)); // RELATIVE PATH
            Logo02.setIcon(resizeIcon(icon, 145,145)); // RELATIVE PATH
        }
    
/*_______________________________________METODI____________________________________________*/
        
    
    
    
      private ChartPanel BarChart_AWT( String chartTitle ) {       
      JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Candidato","Voti",_createDataset(), PlotOrientation.VERTICAL, true, true, false);
      return new ChartPanel( barChart ); 
   }
      private CategoryDataset _createDataset( ) {
      
      final String  speed = "Nome";            
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
        
      ArrayList<candidati> can = mysql.ReadCandidatiColumns();
       for (candidati object: can) {
          
          dataset.addValue( object.getVoti(), object.getNome() +  " " + object.getCognome(), "" ); 
        
        }
             
              
              

      return dataset; 
   }
        // CREAZIONE CHART PANEL (Pannello del Grafico)
    
        private JPanel createChartPanel() {
            String chartTitle = ""; // NOME GRAFICO
            String xAxisLabel = "VOTANTI"; // NOME ASSE ASCISSE
            String yAxisLabel = "GIORNI"; // NOME ASSE ORDINATE
            
            
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
            
            
            return new ChartPanel(chart);
        }

        // CREAZIONE DATASET:__________________________DA RIEMPIRE CON DATI VERI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        private XYDataset createDataset() {
                XYSeriesCollection dataset = new XYSeriesCollection(); // DataSet e Series fanno parte dell' IMPORT
                XYSeries series1 = new XYSeries("Affluenza"); // DataSet
                
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

        SuperContenitore_Panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Candidati_list = new javax.swing.JList<>();
        Aggiungi_Candidato = new javax.swing.JButton();
        Rimuovi_Candidato = new javax.swing.JButton();
        Modifica_Candidato = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        vot_Status_Lab = new javax.swing.JLabel();
        vot_Status = new javax.swing.JLabel();
        DataAvvio_Lab1 = new javax.swing.JLabel();
        id_elezione = new javax.swing.JTextField();
        dataAvvio = new javax.swing.JTextField();
        dataAvvio_Lab = new javax.swing.JLabel();
        dataChiusura_Lab = new javax.swing.JLabel();
        dataChiusura = new javax.swing.JTextField();
        openDatePicker = new javax.swing.JButton();
        avvia_Vot = new javax.swing.JButton();
        stop_Vot = new javax.swing.JButton();
        error_msg = new javax.swing.JLabel();
        Intestazione_Panel = new javax.swing.JPanel();
        Intestazione = new javax.swing.JLabel();
        Logo01 = new javax.swing.JLabel();
        Logo02 = new javax.swing.JLabel();
        LabelAffluenza = new javax.swing.JLabel();
        LabelVincitore = new javax.swing.JLabel();
        Pannello_ColumnChart = new javax.swing.JPanel();
        LabelVoti2 = new javax.swing.JLabel();
        fotoWinner = new javax.swing.JLabel();
        NomeVincitore = new javax.swing.JLabel();
        Pannello_LineChart = new javax.swing.JPanel();
        LabelVoti3 = new javax.swing.JLabel();
        NomeVincitore1 = new javax.swing.JLabel();
        NomeVincitore2 = new javax.swing.JLabel();
        Pannello_CakeChart = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PANNELLO GESTIONE");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        SuperContenitore_Panel.setBackground(new java.awt.Color(255, 255, 255));
        SuperContenitore_Panel.setMaximumSize(new java.awt.Dimension(1920, 1080));
        SuperContenitore_Panel.setMinimumSize(new java.awt.Dimension(1920, 1080));
        SuperContenitore_Panel.setPreferredSize(new java.awt.Dimension(1920, 1080));
        SuperContenitore_Panel.setRequestFocusEnabled(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
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
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(Aggiungi_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(Modifica_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Rimuovi_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 316, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Rimuovi_Candidato)
                    .addComponent(Modifica_Candidato)
                    .addComponent(Aggiungi_Candidato))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        vot_Status_Lab.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        vot_Status_Lab.setText("Status Votazioni :");

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        DataAvvio_Lab1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        DataAvvio_Lab1.setText("ID elezione:");

        id_elezione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_elezioneActionPerformed(evt);
            }
        });

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
        dataChiusura_Lab.setText("Data Fine Elezioni:");

        dataChiusura.setEditable(false);
        dataChiusura.setText(" Selezionare una data");
        dataChiusura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataChiusuraActionPerformed(evt);
            }
        });

        openDatePicker.setText("Scegli Data");
        openDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDatePickerActionPerformed(evt);
            }
        });

        avvia_Vot.setText("Avvia");
        avvia_Vot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avvia_VotActionPerformed(evt);
            }
        });

        stop_Vot.setText("Stop");
        stop_Vot.setEnabled(false);
        stop_Vot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stop_VotActionPerformed(evt);
            }
        });

        error_msg.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataAvvio_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dataChiusura_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DataAvvio_Lab1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_elezione, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dataAvvio)
                                    .addComponent(dataChiusura, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openDatePicker))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(avvia_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stop_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(vot_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(vot_Status_Lab)
                        .addComponent(error_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(vot_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataAvvio_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataAvvio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataChiusura_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataChiusura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openDatePicker))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DataAvvio_Lab1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(id_elezione, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(avvia_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stop_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vot_Status_Lab)
                    .addGap(378, 378, 378)
                    .addComponent(error_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        Intestazione_Panel.setBackground(new java.awt.Color(255, 255, 255));

        Intestazione.setFont(new java.awt.Font("Calibri", 0, 30)); // NOI18N
        Intestazione.setText("PANNELLO AMMINISTRAZIONE ELEZIONI");

        Logo02.setMaximumSize(new java.awt.Dimension(145, 145));
        Logo02.setMinimumSize(new java.awt.Dimension(145, 145));
        Logo02.setPreferredSize(new java.awt.Dimension(145, 145));

        javax.swing.GroupLayout Intestazione_PanelLayout = new javax.swing.GroupLayout(Intestazione_Panel);
        Intestazione_Panel.setLayout(Intestazione_PanelLayout);
        Intestazione_PanelLayout.setHorizontalGroup(
            Intestazione_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Intestazione_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Logo01, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Intestazione)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Logo02, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        Intestazione_PanelLayout.setVerticalGroup(
            Intestazione_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Intestazione_PanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Intestazione, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Intestazione_PanelLayout.createSequentialGroup()
                .addGroup(Intestazione_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Logo02, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Logo01, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        LabelAffluenza.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelAffluenza.setText("Affluenza Votanti nelle relative Giornate");

        LabelVincitore.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVincitore.setForeground(new java.awt.Color(255, 0, 0));
        LabelVincitore.setText("VINCITORE");

        Pannello_ColumnChart.setBackground(new java.awt.Color(255, 255, 255));
        Pannello_ColumnChart.setLayout(new java.awt.BorderLayout());

        LabelVoti2.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti2.setText("Andamento Votazioni a chiusura Elezioni");

        NomeVincitore.setText("Cod.Fiscale");

        Pannello_LineChart.setBackground(new java.awt.Color(255, 255, 255));
        Pannello_LineChart.setLayout(new java.awt.BorderLayout());

        LabelVoti3.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti3.setText("Percentuale Uomini/Donne");

        NomeVincitore1.setText("NomeVincitore");

        NomeVincitore2.setText("Cognome");

        Pannello_CakeChart.setBackground(new java.awt.Color(255, 255, 255));
        Pannello_CakeChart.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        jLabel1.setText("BOT Votazioni");

        jButton1.setText("Avvia");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SuperContenitore_PanelLayout = new javax.swing.GroupLayout(SuperContenitore_Panel);
        SuperContenitore_Panel.setLayout(SuperContenitore_PanelLayout);
        SuperContenitore_PanelLayout.setHorizontalGroup(
            SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelVincitore)
                            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                .addComponent(fotoWinner, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(NomeVincitore2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(NomeVincitore1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(NomeVincitore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(LabelAffluenza, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Pannello_ColumnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Pannello_LineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelVoti2)
                            .addComponent(Pannello_CakeChart, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelVoti3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addGap(533, 533, 533)
                        .addComponent(Intestazione_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 46, Short.MAX_VALUE))
        );
        SuperContenitore_PanelLayout.setVerticalGroup(
            SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Intestazione_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addComponent(LabelVoti3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Pannello_CakeChart, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                .addComponent(LabelVincitore, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                        .addComponent(NomeVincitore1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(NomeVincitore2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(NomeVincitore, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(fotoWinner, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)))
                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(70, Short.MAX_VALUE))
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelAffluenza, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelVoti2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Pannello_LineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Pannello_ColumnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        getContentPane().add(SuperContenitore_Panel, new java.awt.GridBagConstraints());

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

    private  Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {     // resize foto dei candidati (nei pannelli di createPan) per fit jButton
    Image img = icon.getImage();  
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
    return new ImageIcon(resizedImage);
}
/*____________________________________STATO INTERNO________________________________________*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aggiungi_Candidato;
    private javax.swing.JList<String> Candidati_list;
    private javax.swing.JLabel DataAvvio_Lab1;
    private javax.swing.JLabel Intestazione;
    private javax.swing.JPanel Intestazione_Panel;
    private javax.swing.JLabel LabelAffluenza;
    private javax.swing.JLabel LabelVincitore;
    private javax.swing.JLabel LabelVoti2;
    private javax.swing.JLabel LabelVoti3;
    private javax.swing.JLabel Logo01;
    private javax.swing.JLabel Logo02;
    private javax.swing.JButton Modifica_Candidato;
    private javax.swing.JLabel NomeVincitore;
    private javax.swing.JLabel NomeVincitore1;
    private javax.swing.JLabel NomeVincitore2;
    private javax.swing.JPanel Pannello_CakeChart;
    private javax.swing.JPanel Pannello_ColumnChart;
    private javax.swing.JPanel Pannello_LineChart;
    private javax.swing.JButton Rimuovi_Candidato;
    private javax.swing.JPanel SuperContenitore_Panel;
    private javax.swing.JButton avvia_Vot;
    private javax.swing.JTextField dataAvvio;
    private javax.swing.JLabel dataAvvio_Lab;
    private javax.swing.JTextField dataChiusura;
    private javax.swing.JLabel dataChiusura_Lab;
    private javax.swing.JLabel error_msg;
    private javax.swing.JLabel fotoWinner;
    private javax.swing.JTextField id_elezione;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton openDatePicker;
    private javax.swing.JButton stop_Vot;
    private javax.swing.JLabel vot_Status;
    private javax.swing.JLabel vot_Status_Lab;
    // End of variables declaration//GEN-END:variables
}
