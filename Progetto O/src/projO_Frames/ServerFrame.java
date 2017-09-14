package projO_Frames;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

// imports per JFREECHART_LIBRARY
import org.jfree.chart.ChartPanel;
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

// imports per DatePicker
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

// imports interni
import projO_Classi.DatePicker;
import projO_Connettività.MySQlConnection;
import projO_Classi.Votazione;
import projO_Classi.Candidati;
import projO_Classi.Votanti;
import projO_Classi.INIFile;
import projO_Classi.Utility;

// </editor-fold>


/**
 *
 * @author Team
 */
public class ServerFrame extends javax.swing.JFrame {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    
    MySQlConnection mysql = new MySQlConnection();
    INIFile myINI = new INIFile(Utility.INI_PATH);
    
    JPanel tortona_UominiDonne;
    JPanel line_Affluenza;
    JPanel istogramma_Voti;
    
    XYSeriesCollection dataset = new XYSeriesCollection(); // DataSet e Series LineChart
    XYSeries series1 = new XYSeries(""); // DataSet LineChart
    
    DefaultPieDataset resultPie = new DefaultPieDataset(); // Dataset PieChart
    
    DefaultCategoryDataset datasetBarChart = new DefaultCategoryDataset( ); //Dataset BarChart 

//______________________________________________________________________________

    /**
     *
     */

    public ServerFrame() {
            super("SERVER");
            setExtendedState(MAXIMIZED_BOTH);
            initComponents(); // Provenienti dal precedente Costruttore
            
            // FULLSCREEN MODE
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setPreferredSize(screenSize);
            this.setSize(screenSize);
            this.setMinimumSize(screenSize);
            this.setMaximumSize(screenSize);
    
            loadCandidati(); 



            istogramma_Voti = createLineChart();
            panel_ColumnChart.add(istogramma_Voti,BorderLayout.CENTER); // Aggiungo il LineChart al Pannello Designato
            panel_ColumnChart.validate();
            
            line_Affluenza = createBarChart("");
            panel_LineChart.add(line_Affluenza,BorderLayout.CENTER);
            panel_LineChart.validate();
            
            tortona_UominiDonne = createPieChart("");
            panel_CakeChart.add(tortona_UominiDonne,BorderLayout.CENTER);
            panel_CakeChart.validate();
            
            Logo01.setIcon(setUrlIcon(Utility.IMG_LOGO_SERVER)); // RELATIVE PATH
            Logo02.setIcon(setUrlIcon(Utility.IMG_LOGO_SERVER)); // RELATIVE PATH
            
            lb_FotoWinner.setIcon(setUrlIcon(Utility.IMG_PROFILO)); // RELATIVE PATH
            
            if (ProgettoO.StatoVotazioni) {
                vot_Status.setIcon(setUrlIcon(Utility.IMG_VOTAZIONI_APERTE));
                //refreshGrafici();
                avvia_Vot.setEnabled(false);
                stop_Vot.setEnabled(true);
                id_elezione.setEditable(false);
                openDatePicker.setEnabled(false);
                dataAvvio.setText(myINI.getStringProperty("Votazione", "DataInizio"));
                dataChiusura.setText(myINI.getStringProperty("Votazione", "DataFine"));
                id_elezione.setText(myINI.getStringProperty("Votazione", "ID"));
                
            } else {
                vot_Status.setIcon(setUrlIcon(Utility.IMG_VOTAZIONI_CHIUSE));
                avvia_Vot.setEnabled(true);
                stop_Vot.setEnabled(false);
                id_elezione.setEditable(true);
                openDatePicker.setEnabled(true);
                dataAvvio.setText("");
                dataChiusura.setText("");
                id_elezione.setText("");
            }
        }
    
/*____________________________METODI PER GRAFICI _____________________________*/
        
    private ChartPanel createBarChart( String chartTitle ) {       
      JFreeChart istogramma_Voti = ChartFactory.createBarChart(chartTitle, "Candidato","Voti",createBarChartDataset(), PlotOrientation.VERTICAL, true, true, false);
      return new ChartPanel( istogramma_Voti ); 
       }
    
    private CategoryDataset createBarChartDataset( ) {  
        ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
        for (Candidati object: can) {
            datasetBarChart.addValue( object.getVoti(), object.getNome() +  " " + object.getCognome(), "" ); 
        }

        return datasetBarChart; 
    }
//______________________________________________________________________________
    
    private ChartPanel createLineChart() {
            String chartTitle = ""; // NOME GRAFICO
            String xAxisLabel = "GIORNATA NUMERO"; // NOME ASSE ASCISSE
            String yAxisLabel = "AFFLUENZA"; // NOME ASSE ORDINATE
            
            
            XYDataset dataset = createLineChartDataset();

            JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,xAxisLabel, yAxisLabel, dataset);
            chart.removeLegend();
            
            XYPlot plot = chart.getXYPlot();
            
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            plot.setRenderer(renderer);
           
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesStroke(0, new BasicStroke(2.0f));
   
            plot.setBackgroundPaint(Color.LIGHT_GRAY);
            plot.setRangeGridlinesVisible(true);
            plot.setRangeGridlinePaint(Color.GRAY);
            
            plot.setDomainGridlinesVisible(true);
            plot.setDomainGridlinePaint(Color.GRAY);
            
            return new ChartPanel(chart);
    }


    
    private XYDataset createLineChartDataset() {
                // Lettura e Creazione DataSet dalla Tabella con Attributi Affluenza e Data
                // Qui ci vogliono i GET
               
                for(int i=1;i<=Votazione.getlenghtEle();i++){
                    //series1.add(i,Votazione.getAffluenza()); 
                }
                
                // Questa di Default aggiunge in ordine di Sort quindi se voglio metterli in ordine che deicido, ovvero di Aggiunta devo fare:
     
                boolean autoSort = false;
                XYSeries series = new XYSeries("Object 1", autoSort);
                
                dataset.addSeries(series1);

                return dataset;
            
        }                                                                                                                          

//______________________________________________________________________________

    
    private ChartPanel createPieChart(String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,                  // grafico_affluenza title
            createPieChartDataset(),                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return new ChartPanel (chart);

    }


    
    private  PieDataset createPieChartDataset() {

        ArrayList<Votanti> vot = mysql.ReadVotantiColumns();
        
        int maschi = 0;
        int femmine = 0;
        for (Votanti object: vot) {

            if (object.getVotato()) {
                if ( object.getSesso().equals("M")) {
                    maschi++;
                } else if (object.getSesso().equals("F")) {
                   femmine++;
                }
            }
        }
        
        resultPie.setValue("Uomini", maschi);
        resultPie.setValue("Donne", femmine);
        
        return resultPie;

    }

//______________________________________________________________________________  
 
    private void loadCandidati() {
        ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
        javax.swing.DefaultListModel listModel;
        listModel = new javax.swing.DefaultListModel();
        listModel.removeAllElements();
        for (Candidati object: can) {
            String str = String.format("%s %s - %s",object.getNome().toString(), object.getCognome().toString(), object.getCF().toString());
            listModel.addElement(str);
        }
        Candidati_list.setModel(listModel);
    }

//______________________________________________________________________________
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_AllContainer = new javax.swing.JPanel();
        panel_ColumnChart = new javax.swing.JPanel();
        panel_GestioneCandidati = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Candidati_list = new javax.swing.JList<>();
        Aggiungi_Candidato = new javax.swing.JButton();
        Rimuovi_Candidato = new javax.swing.JButton();
        Modifica_Candidato = new javax.swing.JButton();
        panel_SituazioneEle = new javax.swing.JPanel();
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
        panel_Intestazione = new javax.swing.JPanel();
        Intestazione = new javax.swing.JLabel();
        Logo01 = new javax.swing.JLabel();
        Logo02 = new javax.swing.JLabel();
        lb_AndamentoVoti = new javax.swing.JLabel();
        lb_Vincitore = new javax.swing.JLabel();
        panel_LineChart = new javax.swing.JPanel();
        lb_AffluenzaUrne = new javax.swing.JLabel();
        lb_FotoWinner = new javax.swing.JLabel();
        lb_CF = new javax.swing.JLabel();
        lb_PercentualeSesso = new javax.swing.JLabel();
        lb_NomeVincitore = new javax.swing.JLabel();
        lb_CognomeVincitore = new javax.swing.JLabel();
        panel_CakeChart = new javax.swing.JPanel();
        panel_BotContainer = new javax.swing.JPanel();
        lb_Bot = new javax.swing.JLabel();
        bt_AvviaBot = new javax.swing.JButton();
        avanzaGG = new javax.swing.JButton();
        bt_Refresh = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PANNELLO GESTIONE");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panel_AllContainer.setBackground(new java.awt.Color(255, 255, 255));
        panel_AllContainer.setMaximumSize(new java.awt.Dimension(1920, 1080));
        panel_AllContainer.setMinimumSize(new java.awt.Dimension(1920, 1080));
        panel_AllContainer.setPreferredSize(new java.awt.Dimension(1920, 1080));
        panel_AllContainer.setRequestFocusEnabled(false);

        panel_ColumnChart.setBackground(new java.awt.Color(255, 255, 255));
        panel_ColumnChart.setLayout(new java.awt.BorderLayout());

        panel_GestioneCandidati.setBackground(new java.awt.Color(255, 255, 255));
        panel_GestioneCandidati.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel6.setText("Gestione candidati");

        Candidati_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Candidati_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Candidati_listMouseClicked(evt);
            }
        });
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

        javax.swing.GroupLayout panel_GestioneCandidatiLayout = new javax.swing.GroupLayout(panel_GestioneCandidati);
        panel_GestioneCandidati.setLayout(panel_GestioneCandidatiLayout);
        panel_GestioneCandidatiLayout.setHorizontalGroup(
            panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_GestioneCandidatiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(panel_GestioneCandidatiLayout.createSequentialGroup()
                        .addGroup(panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_GestioneCandidatiLayout.createSequentialGroup()
                                .addComponent(Aggiungi_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(Modifica_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Rimuovi_Candidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 316, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_GestioneCandidatiLayout.setVerticalGroup(
            panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_GestioneCandidatiLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Rimuovi_Candidato)
                    .addComponent(Modifica_Candidato)
                    .addComponent(Aggiungi_Candidato))
                .addContainerGap())
        );

        panel_SituazioneEle.setBackground(new java.awt.Color(255, 255, 255));
        panel_SituazioneEle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        vot_Status.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        vot_Status_Lab.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        vot_Status_Lab.setText("Status Votazioni :");

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
        error_msg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panel_SituazioneEleLayout = new javax.swing.GroupLayout(panel_SituazioneEle);
        panel_SituazioneEle.setLayout(panel_SituazioneEleLayout);
        panel_SituazioneEleLayout.setHorizontalGroup(
            panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataAvvio_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dataChiusura_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DataAvvio_Lab1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_elezione, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dataAvvio)
                                    .addComponent(dataChiusura, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openDatePicker))
                            .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                                .addComponent(avvia_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stop_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(error_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_SituazioneEleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(vot_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(vot_Status_Lab)
                    .addGap(529, 529, 529)))
        );
        panel_SituazioneEleLayout.setVerticalGroup(
            panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(vot_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataAvvio_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataAvvio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataChiusura_Lab, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataChiusura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openDatePicker))
                .addGap(18, 18, 18)
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DataAvvio_Lab1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(id_elezione, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(avvia_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stop_Vot, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(error_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_SituazioneEleLayout.createSequentialGroup()
                    .addContainerGap(31, Short.MAX_VALUE)
                    .addComponent(vot_Status_Lab)
                    .addGap(300, 300, 300)))
        );

        panel_Intestazione.setBackground(new java.awt.Color(255, 255, 255));

        Intestazione.setFont(new java.awt.Font("Calibri", 0, 30)); // NOI18N
        Intestazione.setText("PANNELLO AMMINISTRAZIONE ELEZIONI");

        Logo02.setMaximumSize(new java.awt.Dimension(145, 145));
        Logo02.setMinimumSize(new java.awt.Dimension(145, 145));
        Logo02.setPreferredSize(new java.awt.Dimension(145, 145));

        javax.swing.GroupLayout panel_IntestazioneLayout = new javax.swing.GroupLayout(panel_Intestazione);
        panel_Intestazione.setLayout(panel_IntestazioneLayout);
        panel_IntestazioneLayout.setHorizontalGroup(
            panel_IntestazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_IntestazioneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Logo01, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Intestazione)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Logo02, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_IntestazioneLayout.setVerticalGroup(
            panel_IntestazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_IntestazioneLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Intestazione, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_IntestazioneLayout.createSequentialGroup()
                .addGroup(panel_IntestazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Logo02, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Logo01, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lb_AndamentoVoti.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        lb_AndamentoVoti.setText("Andamento voti");

        lb_Vincitore.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        lb_Vincitore.setForeground(new java.awt.Color(255, 0, 0));
        lb_Vincitore.setText("VINCITORE");

        panel_LineChart.setBackground(new java.awt.Color(255, 255, 255));
        panel_LineChart.setLayout(new java.awt.BorderLayout());

        lb_AffluenzaUrne.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        lb_AffluenzaUrne.setText("Affluenza alle urne");

        lb_CF.setText("Cod.Fiscale");

        lb_PercentualeSesso.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        lb_PercentualeSesso.setText("Percentuale uomini/donne");

        lb_NomeVincitore.setText("NomeVincitore");

        lb_CognomeVincitore.setText("Cognome");

        panel_CakeChart.setBackground(new java.awt.Color(255, 255, 255));
        panel_CakeChart.setLayout(new java.awt.BorderLayout());

        panel_BotContainer.setBackground(new java.awt.Color(255, 255, 255));

        lb_Bot.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        lb_Bot.setText("BOT Votazioni");

        bt_AvviaBot.setText("Avvia");
        bt_AvviaBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AvviaBotActionPerformed(evt);
            }
        });

        avanzaGG.setText("avanza gg");
        avanzaGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avanzaGGActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_BotContainerLayout = new javax.swing.GroupLayout(panel_BotContainer);
        panel_BotContainer.setLayout(panel_BotContainerLayout);
        panel_BotContainerLayout.setHorizontalGroup(
            panel_BotContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_BotContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_BotContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_BotContainerLayout.createSequentialGroup()
                        .addComponent(lb_Bot)
                        .addGap(18, 18, 18)
                        .addComponent(bt_AvviaBot, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(avanzaGG))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        panel_BotContainerLayout.setVerticalGroup(
            panel_BotContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_BotContainerLayout.createSequentialGroup()
                .addComponent(avanzaGG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(panel_BotContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_Bot)
                    .addComponent(bt_AvviaBot, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        bt_Refresh.setText("jButton2");
        bt_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_RefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_AllContainerLayout = new javax.swing.GroupLayout(panel_AllContainer);
        panel_AllContainer.setLayout(panel_AllContainerLayout);
        panel_AllContainerLayout.setHorizontalGroup(
            panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_SituazioneEle, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_GestioneCandidati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panel_BotContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                        .addGap(77, 77, 77)
                                        .addComponent(lb_Vincitore))
                                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(lb_FotoWinner, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(65, 65, 65)
                                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lb_CognomeVincitore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lb_NomeVincitore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lb_CF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(panel_ColumnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(lb_AffluenzaUrne, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panel_CakeChart, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_PercentualeSesso, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_AndamentoVoti, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(panel_LineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addGap(533, 533, 533)
                        .addComponent(panel_Intestazione, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_AllContainerLayout.setVerticalGroup(
            panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_Intestazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_PercentualeSesso)
                            .addComponent(lb_Vincitore, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel_CakeChart, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_SituazioneEle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(lb_NomeVincitore, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_CognomeVincitore, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_CF, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_AllContainerLayout.createSequentialGroup()
                                .addComponent(lb_FotoWinner, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(panel_BotContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_AffluenzaUrne, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_AndamentoVoti, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_LineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_ColumnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panel_GestioneCandidati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        getContentPane().add(panel_AllContainer, new java.awt.GridBagConstraints());

        jMenu2.setText("Refresh");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Bot votazioni");

        jMenuItem1.setText("Avanza giornata");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Lancia BOT");
        jMenu3.add(jMenuItem2);

        jMenuBar1.add(jMenu3);

        jMenu1.setText("Storico");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Aggiungi_CandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aggiungi_CandidatoActionPerformed
        // TODO add your handling code here:
        new AddCandidatiFrame().setVisible(true);
    }//GEN-LAST:event_Aggiungi_CandidatoActionPerformed
//______________________________________________________________________________
    
    private void Rimuovi_CandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rimuovi_CandidatoActionPerformed

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
//______________________________________________________________________________
    
    private void Modifica_CandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Modifica_CandidatoActionPerformed

        String candidato = Candidati_list.getSelectedValue();
        if (!(candidato.equals("")) ) {
            String[] tokens = candidato.split("-");    // slitta per ottenere il CF
             String _cf = tokens[1];
             _cf = _cf.replace(" ", ""); // rimuove gli spazi bianchi dal CF
          new EditCandidatiFrame(_cf).setVisible(true);     
        }
    }//GEN-LAST:event_Modifica_CandidatoActionPerformed
//______________________________________________________________________________
    
    private void dataAvvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataAvvioActionPerformed

    }//GEN-LAST:event_dataAvvioActionPerformed
//______________________________________________________________________________
    
    private void dataChiusuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataChiusuraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataChiusuraActionPerformed
//______________________________________________________________________________
    
    private void stop_VotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stop_VotActionPerformed
        
        ProgettoO.StatoVotazioni = false;
        myINI.setBooleanProperty("Votazione", "VotazioneAperta", false, "Stato votazioni");
        myINI.save();
        Votazione.chiudiVotazione();
        avvia_Vot.setEnabled(true);
        stop_Vot.setEnabled(false);
        id_elezione.setEditable(true);
        id_elezione.setText(null);
        dataChiusura.setText(null);
        dataAvvio.setText(null);
        openDatePicker.setEnabled(true);
        vot_Status.setIcon(setUrlIcon(Utility.IMG_VOTAZIONI_CHIUSE));
        ProgettoO.getRegistrazione().setEnabled(false);
        ProgettoO.getRegistrazione().setIcon(setUrlIcon(Utility.IMG_REGISTRAZIONE_DISABLED));
     //   lb_FotoWinner.setIcon(new ImageIcon()); // deve gettare il vincitore dalla classe Votazione, cercare la sua foto DAL SERVER (che ha come nome il CF) e settarla come ImageIcon.
    }//GEN-LAST:event_stop_VotActionPerformed
//______________________________________________________________________________
    
    private void openDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDatePickerActionPerformed
         //create frame new object  f
         final JFrame f = new JFrame();
         //set text which is collected by date picker i.e. set date 
         dataChiusura.setText(new DatePicker(f).setPickedDate());
    }//GEN-LAST:event_openDatePickerActionPerformed
//______________________________________________________________________________
    
    private void avvia_VotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avvia_VotActionPerformed
        if (!(dataChiusura.getText().equals(""))) {
            if (checkDate(dataChiusura.getText())) {
                if (!(id_elezione.getText().equals(""))) {
                    if (!Votazione.existsVotazione(id_elezione.getText())) {
                    
                   //////////// Aggiungere controllo che il nome scelto non esista già
                    
                   Votazione.inizioVotazione(id_elezione.getText(), dataChiusura.getText());
                    
                    /*
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.set(Utility.YEAR, month, Utility.DAY);
                    */
                    dataAvvio.setText(Votazione.getF().format(Votazione.getDataInizioVot().getTime()));
                    
                    error_msg.setText(""); 
                    avvia_Vot.setEnabled(false);    // una volta avviata la votazione, il pulsante di avvio viene disattivato fin quando la votazione non finisce
                    stop_Vot.setEnabled(true);
                    id_elezione.setEditable(false);
                    openDatePicker.setEnabled(false);
                    ProgettoO.getRegistrazione().setEnabled(true);
                    ProgettoO.getRegistrazione().setIcon(setUrlIcon(Utility.IMG_REGISTRAZIONE_ENABLED));
                    vot_Status.setIcon(setUrlIcon(Utility.IMG_VOTAZIONI_APERTE));
                    
                    } else { error_msg.setText("Errore: l' identificativo scelto non è ammissibile, cambiare ID.");}
                }   else  { error_msg.setText("Errore: è necessario scegliere un identificativo per la votazione!"); }
            } else  { error_msg.setText("Errore: la data di fine elezioni non può essere precedente a quella di inizio!"); }
        } else { error_msg.setText("Errore: è necessario selezionare una data per la chiusura delle votazioni!"); }
        
        
        
    }//GEN-LAST:event_avvia_VotActionPerformed
//______________________________________________________________________________
    
    private void id_elezioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_elezioneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_elezioneActionPerformed
//______________________________________________________________________________
    
    private void Candidati_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Candidati_listMouseClicked
        // TODO add your handling code here:
        
        if (evt.getClickCount() == 2) {

            String candidato = Candidati_list.getSelectedValue();
        if (!(candidato.equals("")) ) {
            String[] tokens = candidato.split("-");    // slitta per ottenere il CF
             String _cf = tokens[1];
             _cf = _cf.replace(" ", ""); // rimuove gli spazi bianchi dal CF
          new EditCandidatiFrame(_cf).setVisible(true);     
        }
         
        }

        
    }//GEN-LAST:event_Candidati_listMouseClicked

    private void avanzaGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avanzaGGActionPerformed

        Votazione.AvanzaGiornata();
        if(Votazione.getDataCorrente().after(Votazione.getDataFineVot())) {
            JOptionPane.showMessageDialog(panel_AllContainer, "Votazioni concluse");
        }
    }//GEN-LAST:event_avanzaGGActionPerformed

    private void bt_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_RefreshActionPerformed
        refreshGrafici();
        
        
        
        
        
        
        
    }//GEN-LAST:event_bt_RefreshActionPerformed

    private void bt_AvviaBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AvviaBotActionPerformed
        // TODO add your handling code here:
      ArrayList<Integer> dati = Votazione.getAffluenza();
     for (Integer obj: dati) {
         JOptionPane.showMessageDialog(null,obj,"", 0);
     }
    }//GEN-LAST:event_bt_AvviaBotActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

//______________________________________________________________________________
    
    /**
     *
     * @param args
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
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerFrame().setVisible(true);
                
            }
        });
    }
//______________________________________________________________________________
    
    private boolean checkDate(String Date) {
        
        if ( Date.length() != 10 ) {
            return false;
        }
        if ( Date.substring(0,1).equals("")) {   // giorno non nullo
            return false;
        }
        if ( Date.substring(3,4).equals("")) { // mese non nullo 
            return false;
        }
        if ( Date.substring(6,9).equals("")) {  // anno non nullo
            return false;
        }
        if (( Date.substring(2,2).equals("-") ) && ( Date.substring(5,5).equals("-") )){  // controlla la presenza dello splitter
            return false;
        }
        
        int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        int day = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
        
        String tokens[] = Date.split("-");
        
        int _year =  Integer.parseInt(tokens[2]);
        int _month = Integer.parseInt(tokens[1]);
        int _day = Integer.parseInt(tokens[0]);
        
        if (_year >= year) {
            if (_month >= month) {
                if (_day >= day) {
                    return true;
                }
            }
        }
        return false;
    }  // il parametro Date deve contenere la data di FINE
//______________________________________________________________________________
    
    private Icon setUrlIcon(String remoteURL ) {
        ImageIcon img;
        try {
            img = new ImageIcon(new URL(remoteURL));
        } catch (MalformedURLException ex) {
            img = null;
        }
        return img;
    }
//______________________________________________________________________________    
    private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {     // resize foto dei Candidati (nei pannelli di createPan) per fit jButton
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
    }
//______________________________________________________________________________
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aggiungi_Candidato;
    private javax.swing.JList<String> Candidati_list;
    private javax.swing.JLabel DataAvvio_Lab1;
    private javax.swing.JLabel Intestazione;
    private javax.swing.JLabel Logo01;
    private javax.swing.JLabel Logo02;
    private javax.swing.JButton Modifica_Candidato;
    private javax.swing.JButton Rimuovi_Candidato;
    private javax.swing.JButton avanzaGG;
    private javax.swing.JButton avvia_Vot;
    private javax.swing.JButton bt_AvviaBot;
    private javax.swing.JButton bt_Refresh;
    private javax.swing.JTextField dataAvvio;
    private javax.swing.JLabel dataAvvio_Lab;
    private javax.swing.JTextField dataChiusura;
    private javax.swing.JLabel dataChiusura_Lab;
    private javax.swing.JLabel error_msg;
    private javax.swing.JTextField id_elezione;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_AffluenzaUrne;
    private javax.swing.JLabel lb_AndamentoVoti;
    private javax.swing.JLabel lb_Bot;
    public static javax.swing.JLabel lb_CF;
    public static javax.swing.JLabel lb_CognomeVincitore;
    public static javax.swing.JLabel lb_FotoWinner;
    public static javax.swing.JLabel lb_NomeVincitore;
    private javax.swing.JLabel lb_PercentualeSesso;
    private javax.swing.JLabel lb_Vincitore;
    private javax.swing.JButton openDatePicker;
    private javax.swing.JPanel panel_AllContainer;
    private javax.swing.JPanel panel_BotContainer;
    private javax.swing.JPanel panel_CakeChart;
    private javax.swing.JPanel panel_ColumnChart;
    private javax.swing.JPanel panel_GestioneCandidati;
    private javax.swing.JPanel panel_Intestazione;
    private javax.swing.JPanel panel_LineChart;
    private javax.swing.JPanel panel_SituazioneEle;
    private javax.swing.JButton stop_Vot;
    private javax.swing.JLabel vot_Status;
    private javax.swing.JLabel vot_Status_Lab;
    // End of variables declaration//GEN-END:variables
//______________________________________________________________________________
// Metodo REFRESH Grafici
    
    private void refreshGrafici(){   
        istogramma_Voti = createLineChart();
        //panel_ColumnChart.add(istogramma_Voti,BorderLayout.CENTER); 
        panel_ColumnChart.validate();

        line_Affluenza = createBarChart("");
        //panel_LineChart.add(line_Affluenza,BorderLayout.CENTER);
        panel_LineChart.validate();

        tortona_UominiDonne = createPieChart("");
        //panel_CakeChart.add(tortona_UominiDonne,BorderLayout.CENTER);
        panel_CakeChart.validate();
            
    }





}
