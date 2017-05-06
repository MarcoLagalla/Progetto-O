/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.o;

/**
 *
 * @author marcolagalla
 */
public class candidati extends persone {
    
    private String Partito;
    private String Immagine;
    private int Voti;
    
    public candidati(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune, String Partito, int Voti, String Immagine) {
        super( CF,  Nome,  Cognome,  Sesso,  DataNascita,  Comune);
        this.Partito = Partito;
        this.Voti = Voti;
        this.Immagine = Immagine;
    }
    
    // metodi getter
    
    public String getPartito() { return Partito; }
    public String getImmagine() { return Immagine; }
    public int getVoti() { return Voti; }
    
    // metodi setter
    
    public void setPartito(String Partito) { this.Partito = Partito; }
    public void setImmagine(String Immagine) { this.Immagine = Immagine; }
    public void setVoti(int Voti) { this.Voti = Voti; }
    
}
