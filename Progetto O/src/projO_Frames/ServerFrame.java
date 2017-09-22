package projO_Frames;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import bot.MainFrameBot;
import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
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
import java.awt.Toolkit;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
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
import projO_Classi.Affluenza;
import projO_Connettività.FTPConnection;

// </editor-fold>


/**
 *
 * @author Team
 */

public class ServerFrame extends javax.swing.JFrame {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    
    MySQlConnection mySQL = new MySQlConnection();
    INIFile myINI = new INIFile(Utility.INIPath);
    FTPConnection myFTP = new FTPConnection();
    JPanel pieUominiDonne;
    JPanel lineAffluenza;
    JPanel istogrammaVoti;
    DefaultPieDataset resultPie = new DefaultPieDataset(); // Dataset PieChart 
    DefaultCategoryDataset datasetBarChart = new DefaultCategoryDataset( ); //Dataset BarChart 

//___________________________________COSTRUTTORE___________________________________________

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

            // Creazione Grafici
            
            istogrammaVoti = createLineChart();
            panel_ColumnChart.add(istogrammaVoti,BorderLayout.CENTER);
            panel_ColumnChart.validate();
            
            lineAffluenza = createBarChart("");
            panel_LineChart.add(lineAffluenza,BorderLayout.CENTER);
            panel_LineChart.validate();
            
            pieUominiDonne = createPieChart("");
            panel_CakeChart.add(pieUominiDonne,BorderLayout.CENTER);
            panel_CakeChart.validate();
            
            lb_Logo1.setIcon(Utility.setUrlIcon(Utility.imgLogoServer)); // RELATIVE PATH
            lb_Logo2.setIcon(Utility.setUrlIcon(Utility.imgLogoServer)); // RELATIVE PATH
            
            /*
            File f = new File(Utility.INIPath);
                if (!f.exists() && !f.isDirectory()) {
                    Calendar cal = Calendar.getInstance();   
                    DateFormat f1 = new SimpleDateFormat("dd-MM-yyyy");
                    lb_DataCorrente.setText("Data Corrente: " + f1.format(cal.getTime()));

                } else {
                    lb_DataCorrente.setText("Data Corrente: " + Votazione.readDataCorrente());
                }
            */
                    

            lb_Refresh.setIcon(Utility.resizeIcon((ImageIcon) Utility.setUrlIcon(Utility.imgRefresh), lb_Refresh.getWidth(), lb_Refresh.getHeight()));
            
            lb_FotoWinner.setIcon(Utility.setUrlIcon(Utility.imgProfilo)); // RELATIVE PATH
            
            
            
            if (Votazione.readStatoVotazione()) { // se le votazione sono aperte
                lb_ImmagineStatus.setIcon(Utility.setUrlIcon(Utility.imgVotazioniAperte));
                refreshGrafici();
                bt_AvvioElezioni.setEnabled(false);
                bt_StopElezioni.setEnabled(true);
                tf_IdElezione.setEditable(false);
                bt_ScegliData.setEnabled(false);
                tf_DataInizio.setText(myINI.getStringProperty("Votazione", "DataInizio"));
                tf_DataFine.setText(myINI.getStringProperty("Votazione", "DataFine"));
                tf_IdElezione.setText(myINI.getStringProperty("Votazione", "ID"));
                avanzaGiornataMenuItem.setEnabled(true);
                lanciaBotMenuItem.setEnabled(true);
                bt_AggiungiCandidato.setEnabled(false);
                bt_ModificaCandidato.setEnabled(false);
                bt_RimuoviCandidato.setEnabled(false);
                
                
            } else {    // se le votazioni sono chiuse
                lb_ImmagineStatus.setIcon(Utility.setUrlIcon(Utility.imgVotazioniChiuse));
                bt_AvvioElezioni.setEnabled(true);
                bt_StopElezioni.setEnabled(false);
                tf_IdElezione.setEditable(true);
                bt_ScegliData.setEnabled(true);
                tf_DataInizio.setText("");
                tf_DataFine.setText("");
                tf_IdElezione.setText("");
                avanzaGiornataMenuItem.setEnabled(false);
                lanciaBotMenuItem.setEnabled(false);
                bt_AggiungiCandidato.setEnabled(true);
                bt_ModificaCandidato.setEnabled(true);
                bt_RimuoviCandidato.setEnabled(true);
            }
        }
    
/*____________________________METODI PER GRAFICI _____________________________*/
        
    private ChartPanel createBarChart( String chartTitle ) {       
      JFreeChart istogramma_Voti = ChartFactory.createBarChart(chartTitle, "Candidato","Voti",createBarChartDataset(), PlotOrientation.VERTICAL, true, true, false);
      return new ChartPanel( istogramma_Voti ); 
       }
    
    private CategoryDataset createBarChartDataset( ) {  
        ArrayList<Candidati> can = mySQL.readCandidatiColumns();
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

        XYSeriesCollection dataset = new XYSeriesCollection(); // DataSet e Series LineChart
        boolean autoSort = false;
        XYSeries series1 = new XYSeries("",autoSort); // DataSet LineChart

        ArrayList<Affluenza> aff = Votazione.getAffluenza();
        Collections.sort(aff);
        
        int i = 1;
        for(Affluenza obj: aff){
            series1.add(obj.getData(),obj.getDato()); 
            i++;                        
        }

        dataset.addSeries(series1);

        return dataset;
            
    }                                                                                                                          
  
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

        ArrayList<Votanti> vot = mySQL.readVotantiColumns();
        
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
        ArrayList<Candidati> can = mySQL.readCandidatiColumns();
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
        lb_Gestione = new javax.swing.JLabel();
        scroll_Gestione = new javax.swing.JScrollPane();
        Candidati_list = new javax.swing.JList<>();
        bt_AggiungiCandidato = new javax.swing.JButton();
        bt_RimuoviCandidato = new javax.swing.JButton();
        bt_ModificaCandidato = new javax.swing.JButton();
        panel_SituazioneEle = new javax.swing.JPanel();
        lb_StatusEle = new javax.swing.JLabel();
        lb_ImmagineStatus = new javax.swing.JLabel();
        lb_IdElezione = new javax.swing.JLabel();
        tf_IdElezione = new javax.swing.JTextField();
        tf_DataInizio = new javax.swing.JTextField();
        lb_DataInizio = new javax.swing.JLabel();
        lb_DataFine = new javax.swing.JLabel();
        tf_DataFine = new javax.swing.JTextField();
        bt_ScegliData = new javax.swing.JButton();
        bt_AvvioElezioni = new javax.swing.JButton();
        bt_StopElezioni = new javax.swing.JButton();
        lb_ErroreAvvio = new javax.swing.JLabel();
        panel_Intestazione = new javax.swing.JPanel();
        lb_Intestazione = new javax.swing.JLabel();
        lb_Logo1 = new javax.swing.JLabel();
        lb_Logo2 = new javax.swing.JLabel();
        lb_AndamentoVoti = new javax.swing.JLabel();
        lb_Vincitore = new javax.swing.JLabel();
        panel_LineChart = new javax.swing.JPanel();
        lb_AffluenzaUrne = new javax.swing.JLabel();
        lb_FotoWinner = new javax.swing.JLabel();
        lb_partito = new javax.swing.JLabel();
        lb_PercentualeSesso = new javax.swing.JLabel();
        lb_NomeVincitore = new javax.swing.JLabel();
        lb_CognomeVincitore = new javax.swing.JLabel();
        panel_CakeChart = new javax.swing.JPanel();
        panel_BotContainer = new javax.swing.JPanel();
        lb_Refresh = new javax.swing.JLabel();
        lb_DataCorrente = new javax.swing.JLabel();
        menu_Bar = new javax.swing.JMenuBar();
        menu_Tools = new javax.swing.JMenu();
        avanzaGiornataMenuItem = new javax.swing.JMenuItem();
        lanciaBotMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        eseguiBackupMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        resetMenuItem = new javax.swing.JMenuItem();

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

        lb_Gestione.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        lb_Gestione.setText("Gestione candidati");

        Candidati_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Candidati_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Candidati_listMouseClicked(evt);
            }
        });
        scroll_Gestione.setViewportView(Candidati_list);

        bt_AggiungiCandidato.setText("Aggiungi");
        bt_AggiungiCandidato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AggiungiCandidatoActionPerformed(evt);
            }
        });

        bt_RimuoviCandidato.setText("Rimuovi");
        bt_RimuoviCandidato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_RimuoviCandidatoActionPerformed(evt);
            }
        });

        bt_ModificaCandidato.setText("Modifica");
        bt_ModificaCandidato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ModificaCandidatoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_GestioneCandidatiLayout = new javax.swing.GroupLayout(panel_GestioneCandidati);
        panel_GestioneCandidati.setLayout(panel_GestioneCandidatiLayout);
        panel_GestioneCandidatiLayout.setHorizontalGroup(
            panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_GestioneCandidatiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_Gestione)
                    .addGroup(panel_GestioneCandidatiLayout.createSequentialGroup()
                        .addGroup(panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_GestioneCandidatiLayout.createSequentialGroup()
                                .addComponent(bt_AggiungiCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(bt_ModificaCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_RimuoviCandidato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lb_Gestione, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 316, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_GestioneCandidatiLayout.setVerticalGroup(
            panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_GestioneCandidatiLayout.createSequentialGroup()
                .addComponent(lb_Gestione, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_Gestione, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_GestioneCandidatiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_RimuoviCandidato)
                    .addComponent(bt_ModificaCandidato)
                    .addComponent(bt_AggiungiCandidato))
                .addContainerGap())
        );

        panel_SituazioneEle.setBackground(new java.awt.Color(255, 255, 255));
        panel_SituazioneEle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lb_ImmagineStatus.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        lb_StatusEle.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lb_StatusEle.setText("Status Votazioni :");

        lb_ImmagineStatus.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        lb_IdElezione.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lb_IdElezione.setText("ID elezione:");

        tf_IdElezione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_IdElezioneActionPerformed(evt);
            }
        });

        tf_DataInizio.setEditable(false);
        tf_DataInizio.setText(" ");
        tf_DataInizio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_DataInizioActionPerformed(evt);
            }
        });

        lb_ImmagineStatus.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        lb_DataInizio.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lb_DataInizio.setText("Data Inizio Elezioni:");

        lb_ImmagineStatus.setIcon(new ImageIcon("Immagini/Vot_Chiuse.png"));
        lb_DataFine.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lb_DataFine.setText("Data Fine Elezioni:");

        tf_DataFine.setEditable(false);
        tf_DataFine.setText(" Selezionare una data");
        tf_DataFine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_DataFineActionPerformed(evt);
            }
        });

        bt_ScegliData.setText("Scegli Data");
        bt_ScegliData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ScegliDataActionPerformed(evt);
            }
        });

        bt_AvvioElezioni.setText("Avvia");
        bt_AvvioElezioni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AvvioElezioniActionPerformed(evt);
            }
        });

        bt_StopElezioni.setText("Stop");
        bt_StopElezioni.setEnabled(false);
        bt_StopElezioni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_StopElezioniActionPerformed(evt);
            }
        });

        lb_ErroreAvvio.setForeground(new java.awt.Color(255, 0, 0));
        lb_ErroreAvvio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panel_SituazioneEleLayout = new javax.swing.GroupLayout(panel_SituazioneEle);
        panel_SituazioneEle.setLayout(panel_SituazioneEleLayout);
        panel_SituazioneEleLayout.setHorizontalGroup(
            panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_DataInizio, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_DataFine, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_IdElezione))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_IdElezione, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_DataInizio)
                                    .addComponent(tf_DataFine, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_ScegliData))
                            .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                                .addComponent(bt_AvvioElezioni, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_StopElezioni, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lb_ErroreAvvio, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_SituazioneEleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lb_ImmagineStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(lb_StatusEle)
                    .addGap(529, 529, 529)))
        );
        panel_SituazioneEleLayout.setVerticalGroup(
            panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SituazioneEleLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(lb_ImmagineStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_DataInizio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_DataInizio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_DataFine, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_DataFine, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_ScegliData))
                .addGap(18, 18, 18)
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_IdElezione, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_IdElezione, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_AvvioElezioni, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_StopElezioni, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_ErroreAvvio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_SituazioneEleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_SituazioneEleLayout.createSequentialGroup()
                    .addContainerGap(31, Short.MAX_VALUE)
                    .addComponent(lb_StatusEle)
                    .addGap(300, 300, 300)))
        );

        panel_Intestazione.setBackground(new java.awt.Color(255, 255, 255));

        lb_Intestazione.setFont(new java.awt.Font("Calibri", 0, 30)); // NOI18N
        lb_Intestazione.setText("PANNELLO AMMINISTRAZIONE ELEZIONI");

        lb_Logo2.setMaximumSize(new java.awt.Dimension(145, 145));
        lb_Logo2.setMinimumSize(new java.awt.Dimension(145, 145));
        lb_Logo2.setPreferredSize(new java.awt.Dimension(145, 145));

        javax.swing.GroupLayout panel_IntestazioneLayout = new javax.swing.GroupLayout(panel_Intestazione);
        panel_Intestazione.setLayout(panel_IntestazioneLayout);
        panel_IntestazioneLayout.setHorizontalGroup(
            panel_IntestazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_IntestazioneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lb_Logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_Intestazione)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_Logo2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_IntestazioneLayout.setVerticalGroup(
            panel_IntestazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_IntestazioneLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lb_Intestazione, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_IntestazioneLayout.createSequentialGroup()
                .addGroup(panel_IntestazioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Logo2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_Logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        lb_partito.setText("Partito");

        lb_PercentualeSesso.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        lb_PercentualeSesso.setText("Percentuale uomini/donne");

        lb_NomeVincitore.setText("Nome");

        lb_CognomeVincitore.setText("Cognome");

        panel_CakeChart.setBackground(new java.awt.Color(255, 255, 255));
        panel_CakeChart.setLayout(new java.awt.BorderLayout());

        panel_BotContainer.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panel_BotContainerLayout = new javax.swing.GroupLayout(panel_BotContainer);
        panel_BotContainer.setLayout(panel_BotContainerLayout);
        panel_BotContainerLayout.setHorizontalGroup(
            panel_BotContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
        );
        panel_BotContainerLayout.setVerticalGroup(
            panel_BotContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );

        lb_Refresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lb_Refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lb_Refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_RefreshMouseClicked(evt);
            }
        });

        lb_DataCorrente.setText("Data Corrente: ");

        javax.swing.GroupLayout panel_AllContainerLayout = new javax.swing.GroupLayout(panel_AllContainer);
        panel_AllContainer.setLayout(panel_AllContainerLayout);
        panel_AllContainerLayout.setHorizontalGroup(
            panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addComponent(lb_DataCorrente)
                        .addGap(461, 461, 461)
                        .addComponent(panel_Intestazione, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
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
                                            .addComponent(lb_partito, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                        .addGap(0, 28, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_AllContainerLayout.setVerticalGroup(
            panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_AllContainerLayout.createSequentialGroup()
                .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_AllContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_Intestazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_AllContainerLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lb_DataCorrente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                                .addComponent(lb_partito, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(68, Short.MAX_VALUE))
        );

        getContentPane().add(panel_AllContainer, new java.awt.GridBagConstraints());

        menu_Tools.setText("Tools");

        avanzaGiornataMenuItem.setText("Avanza giornata");
        avanzaGiornataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avanzaGiornataMenuItemActionPerformed(evt);
            }
        });
        menu_Tools.add(avanzaGiornataMenuItem);

        lanciaBotMenuItem.setText("Lancia BOT");
        lanciaBotMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lanciaBotMenuItemActionPerformed(evt);
            }
        });
        menu_Tools.add(lanciaBotMenuItem);
        menu_Tools.add(jSeparator1);

        eseguiBackupMenuItem.setText("BackUp INI");
        eseguiBackupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eseguiBackupMenuItemActionPerformed(evt);
            }
        });
        menu_Tools.add(eseguiBackupMenuItem);
        menu_Tools.add(jSeparator2);

        resetMenuItem.setText("Reset");
        resetMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetMenuItemActionPerformed(evt);
            }
        });
        menu_Tools.add(resetMenuItem);

        menu_Bar.add(menu_Tools);

        setJMenuBar(menu_Bar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_AggiungiCandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AggiungiCandidatoActionPerformed
        new AddCandidatiFrame().setVisible(true);
    }//GEN-LAST:event_bt_AggiungiCandidatoActionPerformed
//______________________________________________________________________________
    
    private void bt_RimuoviCandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_RimuoviCandidatoActionPerformed

        String candidato = Candidati_list.getSelectedValue();
        if (!(candidato.equals("")) ) {
            String[] tokens = candidato.split("-");    // slitta per ottenere il CF
             String _cf = tokens[1];
             _cf = _cf.replace(" ", ""); // rimuove gli spazi bianchi dal CF
            int reply = JOptionPane.showConfirmDialog(null, "Sei sicuro? Questa operazione cancellerà in maniera definitiva il candidato " + tokens[0] + ".", "Richiesta conferma", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION){
                try {
                    mySQL.updateQuery("DELETE FROM CANDIDATI WHERE CodiceFiscale='" + _cf + "';");
                    loadCandidati();
                } catch (Exception ex) {ex.printStackTrace();}
            }
        }
    }//GEN-LAST:event_bt_RimuoviCandidatoActionPerformed
//______________________________________________________________________________
    
    private void bt_ModificaCandidatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ModificaCandidatoActionPerformed

        String candidato = Candidati_list.getSelectedValue();
        if (!(candidato.equals("")) ) {
            String[] tokens = candidato.split("-");    // slitta per ottenere il CF
             String _cf = tokens[1];
             _cf = _cf.replace(" ", ""); // rimuove gli spazi bianchi dal CF
          new EditCandidatiFrame(_cf).setVisible(true);     
        }
    }//GEN-LAST:event_bt_ModificaCandidatoActionPerformed
//______________________________________________________________________________
    
    private void tf_DataInizioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_DataInizioActionPerformed
    }//GEN-LAST:event_tf_DataInizioActionPerformed
//______________________________________________________________________________
    
    private void tf_DataFineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_DataFineActionPerformed
    }//GEN-LAST:event_tf_DataFineActionPerformed
//______________________________________________________________________________
    
    private void bt_StopElezioniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_StopElezioniActionPerformed
        
        ProgettoO.statoVotazioni = false;
        myINI.setBooleanProperty("Votazione", "VotazioneAperta", false, "Stato votazioni");
        myINI.save();
        Votazione.chiudiVotazione();
        bt_AvvioElezioni.setEnabled(true);
        bt_StopElezioni.setEnabled(false);
        avanzaGiornataMenuItem.setEnabled(false);
        lanciaBotMenuItem.setEnabled(false);
        tf_IdElezione.setEditable(true);
        tf_IdElezione.setText(null);
        tf_DataFine.setText(null);
        tf_DataInizio.setText(null);
        bt_ScegliData.setEnabled(true);
        lb_ImmagineStatus.setIcon(Utility.setUrlIcon(Utility.imgVotazioniChiuse));
        ProgettoO.getRegistrazione().setEnabled(false);
        ProgettoO.getRegistrazione().setIcon(Utility.setUrlIcon(Utility.imgRegistrazioneDisabilitata));
        bt_AggiungiCandidato.setEnabled(true);
        bt_ModificaCandidato.setEnabled(true);
        bt_RimuoviCandidato.setEnabled(true);
    }//GEN-LAST:event_bt_StopElezioniActionPerformed
//______________________________________________________________________________
    
    private void bt_ScegliDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ScegliDataActionPerformed
         final JFrame f = new JFrame();
         //set text which is collected by date picker i.e. set date 
         tf_DataFine.setText(new DatePicker(f).setPickedDate());
    }//GEN-LAST:event_bt_ScegliDataActionPerformed
//______________________________________________________________________________
    
    private void bt_AvvioElezioniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AvvioElezioniActionPerformed
        if (!(tf_DataFine.getText().equals(""))) { // controlla che sia impostata una data di fine
            if (checkDate(tf_DataFine.getText())) {    // preleva la data fine
                if (!(tf_IdElezione.getText().equals(""))) {  // controlla che sia impostato un id per la votazione
                    if (!Votazione.existsVotazione(tf_IdElezione.getText())) {
                        refreshGrafici();
                        Votazione.inizioVotazione(tf_IdElezione.getText(), tf_DataFine.getText());

                        tf_DataInizio.setText(Votazione.getF().format(Votazione.getDataInizioVot().getTime()));

                        lb_ErroreAvvio.setText(""); 
                        bt_AvvioElezioni.setEnabled(false);    // una volta avviata la votazione, il pulsante di avvio viene disattivato fin quando la votazione non finisce
                        bt_StopElezioni.setEnabled(true);
                        tf_IdElezione.setEditable(false);
                        bt_ScegliData.setEnabled(false);
                        
                        avanzaGiornataMenuItem.setEnabled(true);
                        lanciaBotMenuItem.setEnabled(true);
                        
                        ProgettoO.getRegistrazione().setEnabled(true);
                        ProgettoO.getRegistrazione().setIcon(Utility.setUrlIcon(Utility.imgRegistrazioneAbilitata));
                        lb_ImmagineStatus.setIcon(Utility.setUrlIcon(Utility.imgVotazioniAperte));
                        bt_AggiungiCandidato.setEnabled(false);
                        bt_ModificaCandidato.setEnabled(false);
                        bt_RimuoviCandidato.setEnabled(false);
                        refreshGrafici();
                        myFTP.loadFile(Utility.INIPath, Utility.remoteINIPath + "progettoO.ini");
                    }else { lb_ErroreAvvio.setText("Errore: l' identificativo scelto non è ammissibile, cambiare ID.");}
                }else  { lb_ErroreAvvio.setText("Errore: è necessario scegliere un identificativo per la votazione!"); }
            }else  { lb_ErroreAvvio.setText("Errore: la data di fine elezioni non può essere precedente a quella di inizio!"); }
        } else { lb_ErroreAvvio.setText("Errore: è necessario selezionare una data per la chiusura delle votazioni!"); }        
    }//GEN-LAST:event_bt_AvvioElezioniActionPerformed
//______________________________________________________________________________
    
    private void tf_IdElezioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_IdElezioneActionPerformed
    }//GEN-LAST:event_tf_IdElezioneActionPerformed
//______________________________________________________________________________
    
    private void Candidati_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Candidati_listMouseClicked
        if (evt.getClickCount() == 2) {
            String candidato = Candidati_list.getSelectedValue();
            if (!(candidato.equals("")) ) {
                String[] tokens = candidato.split("-");    // slitta per ottenere il CF
                String _cf = tokens[1];
                _cf = _cf.replace(" ", ""); // rimuove gli spazi bianchi dal CF
                new EditCandidatiFrame(_cf, !Votazione.readStatoVotazione()).setVisible(true);     
            }   
        }      
    }//GEN-LAST:event_Candidati_listMouseClicked

    private void avanzaGiornataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avanzaGiornataMenuItemActionPerformed
        Votazione.avanzaGiornata();
        if(Votazione.getDataCorrente().after(Votazione.getDataFineVot())) { // se la data corrente ha superato quella di fine, chiude le elezioni
            JOptionPane.showMessageDialog(panel_AllContainer, "Votazioni concluse");
            Votazione.chiudiVotazione();
        }
    }//GEN-LAST:event_avanzaGiornataMenuItemActionPerformed

    private void lb_RefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_RefreshMouseClicked
        refreshGrafici();
    }//GEN-LAST:event_lb_RefreshMouseClicked

    private void lanciaBotMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lanciaBotMenuItemActionPerformed
       MainFrameBot bot = new MainFrameBot();
       bot.setVisible(true);
    }//GEN-LAST:event_lanciaBotMenuItemActionPerformed

    private void eseguiBackupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eseguiBackupMenuItemActionPerformed
        myFTP.loadFile(Utility.INIPath, Utility.remoteINIPath + "progettoO.ini");
        JOptionPane.showMessageDialog(null,"Caricamento file di impostazioni riuscito.","Operazione completata", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_eseguiBackupMenuItemActionPerformed

    private void resetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetMenuItemActionPerformed
        int res = JOptionPane.showConfirmDialog(null,"Sei sicuro di voler resettare la macchina?\nQuesta operazione cancellerà il file di impostazioni.", "Richiesta conferma operazione", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            File f = new File(Utility.INIPath);
            if ( f.delete() ){ // elimina il file ini dal pc
                JOptionPane.showMessageDialog(null,"Operazione riuscita","", JOptionPane.INFORMATION_MESSAGE);
                Utility.downloadINI();  // scarica il file ini dal server
                
                refreshGrafici();
                Votazione.printWinner();
            } else {
                JOptionPane.showMessageDialog(null,"Errore nella eliminazione del file.", "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_resetMenuItemActionPerformed

//______________________________________________________________________________

    /**
     *
     * @param args
     */

    public static void main(String args[]) {
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
    if ( Date.substring(0,1).equals("")) { // giorno non nullo
        return false;
    }
    if ( Date.substring(3,4).equals("")) { // mese non nullo 
        return false;
    }
    if ( Date.substring(6,9).equals("")) { // anno non nullo
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
}  // Il parametro Date deve contenere la data di FINE

//______________________________________________________________________________

// Metodo REFRESH Grafici

private void refreshGrafici(){

    panel_ColumnChart.remove(istogrammaVoti);
    panel_LineChart.remove(lineAffluenza);
    panel_CakeChart.remove(pieUominiDonne);


    istogrammaVoti = createLineChart();
    panel_ColumnChart.add(istogrammaVoti,BorderLayout.CENTER); 
    panel_ColumnChart.validate();

    lineAffluenza = createBarChart("");
    panel_LineChart.add(lineAffluenza,BorderLayout.CENTER);
    panel_LineChart.validate();

    pieUominiDonne = createPieChart("");
    panel_CakeChart.add(pieUominiDonne,BorderLayout.CENTER);
    panel_CakeChart.validate();

}

//______________________________________________________________________________
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> Candidati_list;
    private javax.swing.JMenuItem avanzaGiornataMenuItem;
    private javax.swing.JButton bt_AggiungiCandidato;
    private javax.swing.JButton bt_AvvioElezioni;
    private javax.swing.JButton bt_ModificaCandidato;
    private javax.swing.JButton bt_RimuoviCandidato;
    private javax.swing.JButton bt_ScegliData;
    private javax.swing.JButton bt_StopElezioni;
    private javax.swing.JMenuItem eseguiBackupMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem lanciaBotMenuItem;
    private javax.swing.JLabel lb_AffluenzaUrne;
    private javax.swing.JLabel lb_AndamentoVoti;
    public static javax.swing.JLabel lb_CognomeVincitore;
    public static javax.swing.JLabel lb_DataCorrente;
    private javax.swing.JLabel lb_DataFine;
    private javax.swing.JLabel lb_DataInizio;
    private javax.swing.JLabel lb_ErroreAvvio;
    public static javax.swing.JLabel lb_FotoWinner;
    private javax.swing.JLabel lb_Gestione;
    private javax.swing.JLabel lb_IdElezione;
    private javax.swing.JLabel lb_ImmagineStatus;
    private javax.swing.JLabel lb_Intestazione;
    private javax.swing.JLabel lb_Logo1;
    private javax.swing.JLabel lb_Logo2;
    public static javax.swing.JLabel lb_NomeVincitore;
    private javax.swing.JLabel lb_PercentualeSesso;
    private javax.swing.JLabel lb_Refresh;
    private javax.swing.JLabel lb_StatusEle;
    private javax.swing.JLabel lb_Vincitore;
    public static javax.swing.JLabel lb_partito;
    private javax.swing.JMenuBar menu_Bar;
    private javax.swing.JMenu menu_Tools;
    private javax.swing.JPanel panel_AllContainer;
    private javax.swing.JPanel panel_BotContainer;
    private javax.swing.JPanel panel_CakeChart;
    private javax.swing.JPanel panel_ColumnChart;
    private javax.swing.JPanel panel_GestioneCandidati;
    private javax.swing.JPanel panel_Intestazione;
    private javax.swing.JPanel panel_LineChart;
    private javax.swing.JPanel panel_SituazioneEle;
    private javax.swing.JMenuItem resetMenuItem;
    private javax.swing.JScrollPane scroll_Gestione;
    private javax.swing.JTextField tf_DataFine;
    private javax.swing.JTextField tf_DataInizio;
    private javax.swing.JTextField tf_IdElezione;
    // End of variables declaration//GEN-END:variables

}
