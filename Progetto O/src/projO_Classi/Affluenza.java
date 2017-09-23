/**
 * Necessaria per costruzione DataSet del grafico LineChart
*/
package projO_Classi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Team
 */

public class Affluenza implements Comparable<Affluenza>{
    
    private int data;
    private int dato;
    
    /**
     * 
     * @param data
     * @param dato
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
     *
     * @return Data
     */
    public int getData() {
        return data;
    }

    /**
     *
     * @return Dato
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