
package projO_Classi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Necessaria per la costruzione del DataSet del grafico LineChart.
 *  In questo caso il dataset corrisponde alla normalizzazione dei giorni di votazione, calcolati rispetto alla data di inizio.
 *  Per esempio: il giorno corrispondente alla data di inizio delle elezioni sarà "Giorno 1", quello successivo sarà "Giorno 2"...
 * @author Team
 */

public class Affluenza implements Comparable<Affluenza>{
    
    private int data;
    private int dato;
    
    /**
     * @param data Affluenza nel giorno di votazione
     * @param dato Giorno normalizzato (inseririto sull'asse delle ascisse del LineChart)
     */
    public Affluenza(String data, int dato) {
       try {
           
        Calendar _data = Calendar.getInstance();
        Calendar _dataI = Calendar.getInstance();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        _data.setTime(sdf.parse(data));
        
        String dataInizio = Votazione.readDataInizio();
        _dataI.setTime(sdf.parse(dataInizio));
        
        this.data = _data.get(Calendar.DAY_OF_YEAR) - _dataI.get(Calendar.DAY_OF_YEAR) +1; 
       } catch (ParseException se) {se.printStackTrace();}
        

        this.dato = dato;
    }
//______________________________GETTER/SETTER___________________________________    
    /**
     * @return Data del giorno di votazione in esame
     */
    public int getData() {
        return data;
    }

    /**
     * @return Giorno normalizzato,  (p.e. "giorno 1", "giorno 2"..)
     */
    public int getDato() {
        return dato;
    }

//______________________________COMPARE TO_______________________________________    
    @Override
    public int compareTo(Affluenza obj){
        return this.data - obj.data;     //Sorts the objects in ascending order
    }

    
}