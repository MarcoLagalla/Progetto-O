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
public class votanti extends persone {
    
    private String CodiceTessera;
    
    public votanti(String CF, String CodiceTessera) {
        super(CF);
        this.CodiceTessera = CodiceTessera;
    }
    
    //metodo getter
    public String getCodiceTessera() { return CodiceTessera; }
    
    //metodo setter
    public void setCodiceTessera(String CodiceTessera) { this.CodiceTessera = CodiceTessera; }
    
}
