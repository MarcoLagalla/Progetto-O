import bot.MainFrameBot;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import projO_Classi.Affluenza;
import projO_Classi.Candidato;
import projO_Classi.Votazione;
import projO_Connettività.MySQlConnection;

/**
 * Test per la Creazione del Turno(tab nel db), delle letture delle Date necessarie ad istanziare l'Elezione e dei metodi necessari a gestirla.
 * @author Team
 */
public class VotazioneTest {
    final static String idVotTest = "TestJUnit19";
    static String testDataInizio; 
    static String testDataFine;
    static String testDataCorrente;
    
    static MySQlConnection mySQL = new MySQlConnection();
    static ArrayList<Candidato> candidatiArray = mySQL.readCandidatiColumns();
    
    public VotazioneTest() {
    }
    
    /**
     * Si asserisce che il Test inizi dopo che la Votazione è stata Avviata da un amministratore comunale.
     */
    @BeforeClass
    public static void setUpClass() {
        Votazione.inizioVotazione(idVotTest, "04-10-2017");
        testDataInizio = Votazione.getF().format(Votazione.getDataInizioVot().getTime());
        testDataFine = Votazione.getF().format(Votazione.getDataFineVot().getTime());
        testDataCorrente =  Votazione.getF().format(Calendar.getInstance().getTime());
        
        // VOTO UN CANDIDATO 
        try{ 
            int voti;
            String cf_cand = candidatiArray.get(0).getCF(); 
            ResultSet voti_ = mySQL.executeQuery("SELECT Voti FROM CANDIDATI WHERE CodiceFiscale='" + cf_cand + "';");
            voti = voti_.getInt("Voti") + 1;  // voti ++
            int res = mySQL.updateQuery("UPDATE CANDIDATI SET Voti='" + voti + "' WHERE CodiceFiscale='" + cf_cand + "';");
            Votazione.addAffluenza();
        
        }catch (SQLException ex) {ex.printStackTrace();} 

        Votazione.chiudiVotazione();
    }
    
    /**
     * Test of getIdVotazione method, of class Votazione, necessario per verificare la corretta c reazione nel db del Turno.
     */
    @Test
    public void testGetIdVotazione() {
        System.out.println("getIdVotazione");
        String expResult = idVotTest; // Metto lo stesso Id dichiarato in beforeClass
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

    }
    
    @Test
    public void testAddAffluenza() {
        System.out.println("addAffluenza");
        Votazione.addAffluenza();

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
     * Test of findWinner method, of class Votazione.
     */
    @Test
    
    public void testFindWinner() {
        System.out.println("findWinner");
        ArrayList<String> wins = new ArrayList<>();
        wins.add(Votazione.winner);
        ArrayList<String> expResult = wins;
        ArrayList<String> result = Votazione.findWinner();
        assertEquals(expResult, result);
    }


}
