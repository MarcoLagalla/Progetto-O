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
    private int Voti;
    
    public candidati(String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune, String Partito, int Voti) {
        super( CF,  Nome,  Cognome,  Sesso,  DataNascita,  Comune);
        this.Partito = Partito;
        this.Voti = Voti;
    }
    
    // metodi getter
    
    public String getPartito() { return Partito; }
    public int getVoti() { return Voti; }
    
    // metodi setter
    
    public void setPartito(String Partito) { this.Partito = Partito; }
    public void setVoti(int Voti) { this.Voti = Voti; }
    
}
