import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projO_Classi.Affluenza;
import projO_Classi.Votazione;

/**
 * Test per la Creazione del Turno(tab nel db), delle letture delle Date necessarie ad istanziare l'Elezione e dei metodi necessari a gestirla.
 * @author Team
 */
public class VotazioneTest {
    
    static String testDataInizio; 
    static String testDataFine;
    static String testDataCorrente;
    
    public VotazioneTest() {
    }
    
    /**
     * Si asserisce che il Test inizi dopo che la Votazione è stata Avviata da un amministratore comunale.
     */
    @BeforeClass
    public static void setUpClass() {
        Votazione.inizioVotazione("TestJUnit12", "04-10-2017");
        testDataInizio = Votazione.getF().format(Votazione.getDataInizioVot().getTime());
        testDataFine = Votazione.getF().format(Votazione.getDataFineVot().getTime());
        testDataCorrente =  Votazione.getF().format(Calendar.getInstance().getTime());
    }
    
    /**
     * Test of getIdVotazione method, of class Votazione, necessario per verificare la corretta c reazione nel db del Turno.
     */
    @Test
    public void testGetIdVotazione() {
        System.out.println("getIdVotazione");
        String expResult = "TestJUnit12"; // Metto lo stesso Id dichiarato in beforeClass
        String result = Votazione.getIdVotazione();
        assertEquals(expResult, result);
    }
    
    /**
     * Test 2 of getIdVotazione method, of class Votazione, necesssario per verificare l'inserimento errato dovuto ad un Id elezione già utilizzato.
     */
    @Test
    public void testGetIdVotazione2() {
        System.out.println("getIdVotazione");
        String expResult = "BabboNatale"; // metto un Id diverso da quello dichiarato in beforeClass
        String result = Votazione.getIdVotazione();
        assertNotEquals(expResult, result);
    }
                                                                                                                                            /*
        /**
     * Test of readDataCorrente method, of class Votazione.
     */
    @Test
    public void testReadDataCorrente() {
        System.out.println("readDataCorrente");
        String expResult = testDataCorrente;// la data letta dall'INI deve essere uguale a quella nello stato interno di Votazione
        String result = Votazione.readDataCorrente();
        assertEquals(expResult, result);
    }

    /**
     * Test of readDataFine method, of class Votazione.
     */
    @Test
    public void testReadDataFine() {
        System.out.println("readDataFine");
        String expResult = testDataFine ; // la data letta dall'INI deve essere uguale a quella nello stato interno di Votazione
        String result = Votazione.readDataFine();
        assertEquals(expResult, result);
    }
                                                                                                                            /*
    /**
     * Test of readDataInizio method, of class Votazione.
     */
    @Test
    public void testReadDataInizio() {
        System.out.println("readDataInizio");
        String expResult = testDataInizio;  // la data letta dall'INI deve essere uguale a quella nello stato interno di Votazione
        String result = Votazione.readDataInizio();
        assertEquals(expResult, result);
    }
//______________________________________________________________________________
    /**
     * Test of readStatoVotazione method, of class Votazione.
     */
    @Test
    public void testReadStatoVotazione() {
        System.out.println("readStatoVotazione");
        boolean expResult = true; // votazione istanziata in beforeClass
        boolean result = Votazione.readStatoVotazione();
        assertEquals(expResult, result);
    }
                                                                                                                    
    @Test
    public void testGetAffluenza() {
        System.out.println("getAffluenza");
        ArrayList<Affluenza> expResult = null;
        ArrayList<Affluenza> result = Votazione.getAffluenza();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
//______________________________________________________________________________
    /**
     * Test of chiudiVotazione method, of class Votazione.
     */
    @Test
    public void testChiudiVotazione() {
        System.out.println("chiudiVotazione");
        Votazione.chiudiVotazione();

    }
//______________________________________________________________________________
    
    @Test
    public void testAddAffluenza() {
        System.out.println("addAffluenza");
        Votazione.addAffluenza();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of avanzaGiornata method, of class Votazione.
     */
    @Test
    public void testAvanzaGiornata() {
        System.out.println("avanzaGiornata");
        Votazione.avanzaGiornata();
        Calendar expResult = Calendar.getInstance();
        Calendar result = Votazione.getDataCorrente();
        assertNotEquals(expResult, result); // Mi aspetto che la Data del sistema sia diversa da quella ottenuta avanzando la giornata
    }

    /**
     * Test of existsVotazione method, of class Votazione.
     */
    @Test
    public void testExistsVotazione() {
        System.out.println("existsVotazione");
        String idVotazione = "";
        boolean expResult = false;
        boolean result = Votazione.existsVotazione(idVotazione);
        assertEquals(expResult, result);    
    }

    /**
     * Test of printWinner method, of class Votazione.
     */
    @Test
    public void testPrintWinner() {
        System.out.println("printWinner");
        ArrayList<String> expResult = Votazione.findWinner();
        String result = Votazione.winner;
        assertEquals(expResult, result);    
    }

    /**
     * Test of stopVotazioniButton method, of class Votazione, Verifica che i seggi siano stati effettivamente chiusi.
     */
    @Test
    public void testStopVotazioniButton() {
        System.out.println("stopVotazioniButton");
        Votazione.stopVotazioniButton();
        boolean expResult = false;
        boolean result = Votazione.readStatoVotazione();
        assertEquals(expResult, result);

    }
    
}
