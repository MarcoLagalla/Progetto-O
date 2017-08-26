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
import projO_Interfacce.InterfacciaPrincipale;

// </editor-fold>


/**
 *
 * @author Team
 */
public class ServerFrame extends javax.swing.JFrame implements InterfacciaPrincipale{

    MySQlConnection mysql = new MySQlConnection();
    INIFile myINI = new INIFile(INI_PATH);
    

//______________________________________________________________________________

    public ServerFrame() {
            super("SERVER");
            setExtendedState(MAXIMIZED_BOTH);
            initComponents(); // Provenienti dal precedente Costruttore
            
//______________________________________________________________________________
            // FULLSCREEN MODE
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setPreferredSize(screenSize);
            this.setSize(screenSize);
            this.setMinimumSize(screenSize);
            this.setMaximumSize(screenSize);
//______________________________________________________________________________
    
            loadCandidati(); 
            
            JPanel chartPanel = createLineChart();
            Pannello_ColumnChart.add(chartPanel,BorderLayout.CENTER); // Aggiungo il LineChart al Pannello Designato
            Pannello_ColumnChart.validate();
            
            JPanel barC = createBarChart("");
            Pannello_LineChart.add(barC,BorderLayout.CENTER);
            Pannello_LineChart.validate();
            
            JPanel chartPie = createPieChart("");
            Pannello_CakeChart.add(chartPie,BorderLayout.CENTER);
            Pannello_CakeChart.validate();
            
            Logo01.setIcon(setUrlIcon(IMG_LOGO_SERVER)); // RELATIVE PATH
            Logo02.setIcon(setUrlIcon(IMG_LOGO_SERVER)); // RELATIVE PATH
            
            fotoWinner.setIcon(setUrlIcon(IMG_PROFILO)); // RELATIVE PATH
            
            if (ProgettoO.StatoVotazioni) {
                vot_Status.setIcon(setUrlIcon(IMG_VOTAZIONI_APERTE));
                avvia_Vot.setEnabled(false);
                stop_Vot.setEnabled(true);
            } else {
                vot_Status.setIcon(setUrlIcon(IMG_VOTAZIONI_CHIUSE));
                avvia_Vot.setEnabled(true);
                stop_Vot.setEnabled(false);
            }
            
            dataAvvio.setText( DAY + "-" + MONTH + "-" + YEAR );
        }
    
/*_______________________________________METODI PER GRAFICI ____________________________________________*/
        
    private ChartPanel createBarChart( String chartTitle ) {       
      JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Candidato","Voti",createBarChartDataset(), PlotOrientation.VERTICAL, true, true, false);
      return new ChartPanel( barChart ); 
       }
    private CategoryDataset createBarChartDataset( ) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
        ArrayList<Candidati> can = mysql.ReadCandidatiColumns();
        for (Candidati object: can) {
            dataset.addValue( object.getVoti(), object.getNome() +  " " + object.getCognome(), "" ); 
        }

        return dataset; 
    }
//______________________________________________________________________________
    
    private ChartPanel createLineChart() {
            String chartTitle = ""; // NOME GRAFICO
            String xAxisLabel = "VOTANTI"; // NOME ASSE ASCISSE
            String yAxisLabel = "GIORNI"; // NOME ASSE ORDINATE
            
            
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

//______________________________________________________________________________
    
    private XYDataset createLineChartDataset() {
                XYSeriesCollection dataset = new XYSeriesCollection(); // DataSet e Series fanno parte dell' IMPORT
                XYSeries series1 = new XYSeries(""); // DataSet
                
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

//______________________________________________________________________________

    
    private ChartPanel createPieChart(String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,                  // chart title
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

//______________________________________________________________________________
    
    private  PieDataset createPieChartDataset() {
        DefaultPieDataset result = new DefaultPieDataset();

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
        
        result.setValue("Uomini", maschi);
        result.setValue("Donne", femmine);
        
        return result;

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

        SuperContenitore_Panel = new javax.swing.JPanel();
        Pannello_ColumnChart = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Candidati_list = new javax.swing.JList<String>();
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
        Pannello_LineChart = new javax.swing.JPanel();
        LabelVoti2 = new javax.swing.JLabel();
        fotoWinner = new javax.swing.JLabel();
        NomeVincitore = new javax.swing.JLabel();
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
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        SuperContenitore_Panel.setBackground(new java.awt.Color(255, 255, 255));
        SuperContenitore_Panel.setMaximumSize(new java.awt.Dimension(1920, 1080));
        SuperContenitore_Panel.setMinimumSize(new java.awt.Dimension(1920, 1080));
        SuperContenitore_Panel.setPreferredSize(new java.awt.Dimension(1920, 1080));
        SuperContenitore_Panel.setRequestFocusEnabled(false);

        Pannello_ColumnChart.setBackground(new java.awt.Color(255, 255, 255));
        Pannello_ColumnChart.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
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
                    .addComponent(error_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(vot_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(vot_Status_Lab)
                    .addGap(529, 529, 529)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(vot_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(error_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(31, Short.MAX_VALUE)
                    .addComponent(vot_Status_Lab)
                    .addGap(300, 300, 300)))
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
        LabelAffluenza.setText("Andamento voti");

        LabelVincitore.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVincitore.setForeground(new java.awt.Color(255, 0, 0));
        LabelVincitore.setText("VINCITORE");

        Pannello_LineChart.setBackground(new java.awt.Color(255, 255, 255));
        Pannello_LineChart.setLayout(new java.awt.BorderLayout());

        LabelVoti2.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti2.setText("Affluenza alle urne");

        NomeVincitore.setText("Cod.Fiscale");

        LabelVoti3.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        LabelVoti3.setText("Percentuale uomini/donne");

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
                .addContainerGap(194, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout SuperContenitore_PanelLayout = new javax.swing.GroupLayout(SuperContenitore_Panel);
        SuperContenitore_Panel.setLayout(SuperContenitore_PanelLayout);
        SuperContenitore_PanelLayout.setHorizontalGroup(
            SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelVincitore)
                                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                        .addComponent(fotoWinner, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(NomeVincitore2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(NomeVincitore1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(NomeVincitore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(Pannello_ColumnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(LabelVoti2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Pannello_CakeChart, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelVoti3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelAffluenza, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(Pannello_LineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addGap(533, 533, 533)
                        .addComponent(Intestazione_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 40, Short.MAX_VALUE))
        );
        SuperContenitore_PanelLayout.setVerticalGroup(
            SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Intestazione_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addComponent(LabelVoti3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Pannello_CakeChart, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SuperContenitore_PanelLayout.createSequentialGroup()
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelVoti2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelAffluenza, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SuperContenitore_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Pannello_LineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Pannello_ColumnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        getContentPane().add(SuperContenitore_Panel, new java.awt.GridBagConstraints());

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
        vot_Status.setIcon(setUrlIcon(IMG_VOTAZIONI_CHIUSE));
        ProgettoO.getRegistrazione().setEnabled(false);
        ProgettoO.getRegistrazione().setIcon(setUrlIcon(IMG_REGISTRAZIONE_DISABLED));
        fotoWinner.setIcon(new ImageIcon()); // deve gettare il vincitore dalla classe Votazione, cercare la sua foto DAL SERVER (che ha come nome il CF) e settarla come ImageIcon.
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
                    
                    ProgettoO.StatoVotazioni = true;
                    myINI.setBooleanProperty("Votazione", "VotazioneAperta", true, "VotazioneAperta");
                    myINI.save();
                    
                    // aggiungere controllo che il nome scelto non esista già
                    Votazione.inizioVotazione(id_elezione.getText(), dataChiusura.getText());
                    dataAvvio.setText(Votazione.getDataInizioVot().toString());
                    error_msg.setText(""); 
                    avvia_Vot.setEnabled(false);    // una volta avviata la votazione, il pulsante di avvio viene disattivato fin quando la votazione non finisce
                    stop_Vot.setEnabled(true);
                    ProgettoO.getRegistrazione().setEnabled(true);
                    ProgettoO.getRegistrazione().setIcon(setUrlIcon(IMG_REGISTRAZIONE_ENABLED));
                    vot_Status.setIcon(setUrlIcon(IMG_VOTAZIONI_APERTE));
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
