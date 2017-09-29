
package projO_Classi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Necessaria per costruzione DataSet del grafico LineChart per ottenere i dati relativi alla X e Y del grafico 
 * @author Team
 */

public class Affluenza implements Comparable<Affluenza>{
    
    private int data;
    private int dato;
    
    /**
     * 
     * @param data Affluenza nel Giorno di Votazione
     * @param dato Giorno Normalizzato da inserire sull'Asse X del LineChart
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
     * @return Data ovvero la Data del Giorno di Votazione
     */
    public int getData() {
        return data;
    }

    /**
     *
     * @return Dato ovvero il Giorno Normalizzato
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