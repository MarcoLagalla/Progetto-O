
package projO_Classi;

// <editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// </editor-fold>


/**
 * Strumento necessario per la creazione del Calendario nella selezione delle Date nel ServerFrame
 * @author DatePicker
 */

public class DatePicker  {
    private int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    private int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

    private final int monthAttuale = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    private final int yearAttuale = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    
    private JButton[] buttonMatrix = new JButton[49];
    private JLabel lb_Intestazione = new JLabel("", JLabel.CENTER);
    private String day;
    private JDialog dialogPanel;
   

    /**
     *
     * @param parent
     */  
    public DatePicker(JFrame parent)
    {
            
            day = "";
            dialogPanel = new JDialog();
            dialogPanel.setModal(true);
            String[] header = { "Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab" };
            JPanel p1 = new JPanel(new GridLayout(7, 7));
            p1.setPreferredSize(new Dimension(430, 120));
            for (int x = 0; x < buttonMatrix.length; x++) 
            {		
                    final int selection = x;
                    buttonMatrix[x] = new JButton();
                    buttonMatrix[x].setFocusPainted(false);
                    buttonMatrix[x].setBackground(Color.white);
                    if (x > 6)
                    buttonMatrix[x].addActionListener(new ActionListener() 
                    {
                             public void actionPerformed(ActionEvent ae) 
                             {
                                   day = buttonMatrix[selection].getActionCommand();
                                   dialogPanel.dispose();
                             }
                    });
                    if (x < 7)
                    {
                            buttonMatrix[x].setText(header[x]);
                            buttonMatrix[x].setForeground(Color.black);
                            buttonMatrix[x].setBackground(Color.LIGHT_GRAY);
                    }
                    p1.add(buttonMatrix[x]);
            }
            JPanel p2 = new JPanel(new GridLayout(1, 3));

            JButton previous = new JButton("<< Indietro");
            previous.addActionListener(new ActionListener() 
            {
                    public void actionPerformed(ActionEvent ae) 
                    {
                        if ( ( year >= yearAttuale) && (month > monthAttuale) ) {
                        month--;
                        displayDate();                                
                        }

                    }
            });
            p2.add(previous);
            p2.add(lb_Intestazione);
            JButton next = new JButton("Avanti >>");
            next.addActionListener(new ActionListener()
            {
                    public void actionPerformed(ActionEvent ae) 
                    {
                        month++;
                        displayDate();
                    }
            });
            p2.add(next);
            dialogPanel.add(p1, BorderLayout.CENTER);
            dialogPanel.add(p2, BorderLayout.SOUTH);
            dialogPanel.pack();
            dialogPanel.setLocationRelativeTo(parent);
            displayDate();
            dialogPanel.setVisible(true);
    }

//______________________________________________________________________________

    /**
     * Metodo per Visualizzare le Date
     */

    public void displayDate() 
    {
            for (int x = 7; x < buttonMatrix.length; x++)
            buttonMatrix[x].setText("");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");	
            java.util.Calendar cal = java.util.Calendar.getInstance();			
            cal.set(year, month, 1);
            int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
            int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
            for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
            buttonMatrix[x].setText("" + day);
            lb_Intestazione.setText(sdf.format(cal.getTime()));
            dialogPanel.setTitle("Selezionare una data");
    }
//______________________________________________________________________________
    /**
     *
     * @return Data Selezionata
     */
    public String setPickedDate() 
        {
            //if condition
            if (day.equals(""))
                return day;
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.set(year, month, Integer.parseInt(day));
                
                return sdf.format(cal.getTime());
        }
}
