/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projO_Classi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Affluenza implements Comparable<Affluenza>{
    
    private int data;
    private int dato;
    
    public Affluenza(String data, int dato) {
       try {
           
        Calendar _data = Calendar.getInstance();
        Calendar _dataI = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        _data.setTime(sdf.parse(data));
        
        String dataInizio = Votazione.readDataInizio();
        _dataI.setTime(sdf.parse(dataInizio));
        
        this.data = _data.get(Calendar.DAY_OF_YEAR) - _dataI.get(Calendar.DAY_OF_YEAR) +1;
       } catch (ParseException se) {}
        

        this.dato = dato;
    }
    
    public int getData() {
        return data;
    }
    public int getDato() {
        return dato;
    }
    
     @Override
    public int compareTo(Affluenza s)
    {
        return this.data - s.data;     //Sorts the objects in ascending order
         
        // return s.id - this.id;    //Sorts the objects in descending order
    }
     
}