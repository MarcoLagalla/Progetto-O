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
public class persone {
    
    private int id;
    private String CF;
    private String Nome;
    private String Cognome;
    private String Sesso;
    private String DataNascita;
    private String Comune;
    
    
    public persone() {
        
    }
    
    public persone(int id, String CF, String Nome, String Cognome, String Sesso, String DataNascita, String Comune){
        this.id = id;
        this.CF = CF;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Sesso = Sesso;
        this.DataNascita = DataNascita;
        this.Comune = Comune;
    }
    
    // metodi getter
    
    public int getID() { return id; }
    public String getCF() { return CF; }
    public String getNome() { return Nome; }
    public String getCognome() { return Cognome; }
    public String getSesso() { return Sesso; }
    public String getDataNascita() { return DataNascita; }
    public String getComune() { return Comune; }
    
    // metodi setter
    
    public void setID(int id) { this.id = id; }
    public void setCF(String CF) { this.CF = CF; }
    public void setNome(String Nome) { this.Nome = Nome; } 
    public void setCognome(String Cognome) { this.Cognome = Cognome; }
    public void setSesso(String Sesso) { this.Sesso = Sesso; }
    public void setDataNascita(String DataNascita) { this.DataNascita = DataNascita; }
    public void setComune(String Comune) { this.Comune = Comune; }
}
