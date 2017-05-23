/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.o;
import java.net.URL;

/**
 *
 * @author marcolagalla
 */
public class candidati extends persone {
    
    private String Partito;
    private URL Immagine;
    private int Voti;
    
    public candidati(String CF,  String Partito, int Voti,URL Immagine) {
        super(CF);
        this.Partito = Partito;
        this.Voti = Voti;
        this.Immagine = Immagine;
    }
    
    // metodi getter
    
    public String getPartito() { return Partito; }
    public URL getImmagine() { return Immagine; }
    public int getVoti() { return Voti; }
    
    
    // metodi setter
    
    public void setPartito(String Partito) { this.Partito = Partito; }
    public void setImmagine(URL Immagine) { this.Immagine = Immagine; }
    public void setVoti(int Voti) { this.Voti = Voti; }
    
}
