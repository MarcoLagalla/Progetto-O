import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projO_Classi.Affluenza;
import projO_Classi.Votazione;

/**
 *
 * @author Team
 */
public class VotazioneTest {
    
    public VotazioneTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Votazione.inizioVotazione("giaoettr", "04-10-2017");
    }

    /**
     * Test of getIdVotazione method, of class Votazione.
     */
    @Test
    public void testGetIdVotazione() {
        System.out.println("getIdVotazione");
        String expResult = "giaoettr"; // metto lo stesso voto dichiarato in beforeClass
        String result = Votazione.getIdVotazione();
        assertEquals(expResult, result);
    }
    
    /**
     * Test 2 of getIdVotazione method, of class Votazione.
     */
    @Test
    public void testGetIdVotazione2() {
        System.out.println("getIdVotazione");
        String expResult = "Babbo Natale"; // metto un nome diverso da quello dichiarato in beforeClass
        String result = Votazione.getIdVotazione();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of readDataCorrente method, of class Votazione.
     */
    @Test
    public void testReadDataCorrente() {
        System.out.println("readDataCorrente");
        String expResult = Votazione.getF().format(Calendar.getInstance()); // la data letta dall'INI deve essere uguale a quella nello stato interno di Votazione
        String result = Votazione.readDataCorrente();
        assertEquals(expResult, result);
    }

    /**
     * Test of readDataFine method, of class Votazione.
     */
    @Test
    public void testReadDataFine() {
        System.out.println("readDataFine");
        String expResult = Votazione.getF().format(Votazione.getDataFineVot()); // la data letta dall'INI deve essere uguale a quella nello stato interno di Votazione
        String result = Votazione.readDataFine();
        assertEquals(expResult, result);
    }

    /**
     * Test of readDataInizio method, of class Votazione.
     */
    @Test
    public void testReadDataInizio() {
        System.out.println("readDataInizio");
        String expResult = Votazione.getF().format(Votazione.getDataInizioVot());; // la data letta dall'INI deve essere uguale a quella nello stato interno di Votazione
        String result = Votazione.readDataInizio();
        assertEquals(expResult, result);
    }

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

    /**
     * Test of getDataCorrente method, of class Votazione.
     */
    @Test
    public void testGetDataCorrente() {
        System.out.println("getDataCorrente");
        Calendar expResult = Calendar.getInstance();
        Calendar result = Votazione.getDataCorrente();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAffluenza method, of class Votazione.
     */
    @Test
    public void testGetAffluenza() {
        System.out.println("getAffluenza");
        ArrayList<Affluenza> expResult = null;
        ArrayList<Affluenza> result = Votazione.getAffluenza();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chiudiVotazione method, of class Votazione.
     */
    @Test
    public void testChiudiVotazione() {
        System.out.println("chiudiVotazione");
        Votazione.chiudiVotazione();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAffluenza method, of class Votazione.
     */
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
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printWinner method, of class Votazione.
     */
    @Test
    public void testPrintWinner() {
        System.out.println("printWinner");
        Votazione.printWinner();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopVotazioniButton method, of class Votazione.
     */
    @Test
    public void testStopVotazioniButton() {
        System.out.println("stopVotazioniButton");
        Votazione.stopVotazioniButton();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
