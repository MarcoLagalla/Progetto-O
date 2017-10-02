
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projO_Classi.Utility;
import projO_Frames.SchedaCandidatoFrame;

/**
 * Test sul Frame SchedaCandidato per verificare la corretta costruzione delle schede di ogni Candidato al seggio elettorale
 * @author Team
 */
public class SchedaCandidatoFrameTest {
    
    public SchedaCandidatoFrameTest() {
    }

    /**
     * Test of setImage method, of class SchedaCandidatoFrame.
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testSetImage() throws MalformedURLException {
        System.out.println("setImage");
        URL img_ = new URL(Utility.imgLogoServer);
        SchedaCandidatoFrame instance = new SchedaCandidatoFrame();
        instance.setImage(img_);
    }

    /**
     * Test of setCF method, of class SchedaCandidatoFrame.
     */
    @Test
    public void testSetCF() {
        System.out.println("setCF");
        String cf = "GRRFRC94S23M109E";
        SchedaCandidatoFrame instance = new SchedaCandidatoFrame();
        instance.setCF(cf);
        Assert.assertThat(cf, RegexMatcher.matches("[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}"));
        
    }
    
    /**
     * Test of setNome method, of class SchedaCandidatoFrame.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        String nome = "Luca";
        SchedaCandidatoFrame instance = new SchedaCandidatoFrame();
        instance.setNome(nome);
        Assert.assertThat(nome, RegexMatcher.matches("[a-zA-Z]+"));
    }

    /**
     * Test of setCognome method, of class SchedaCandidatoFrame.
     */
    @Test
    public void testSetCognome() {
        System.out.println("setCognome");
        String cognome = "Nervi";
        SchedaCandidatoFrame instance = new SchedaCandidatoFrame();
        instance.setCognome(cognome);
        Assert.assertThat(cognome, RegexMatcher.matches("[a-zA-Z]+"));
    }

    /**
     * Test of setPartito method, of class SchedaCandidatoFrame.
     */
    @Test
    public void testSetPartito() {
        System.out.println("setPartito");
        String partito = "Sindacato";
        SchedaCandidatoFrame instance = new SchedaCandidatoFrame();
        instance.setPartito(partito);
        Assert.assertThat(partito, RegexMatcher.matches("[a-zA-Z]+"));
    }

    /**
     * Test of isVotato method, of class SchedaCandidatoFrame.
     */
    @Test
    public void testIsVotato() {
        System.out.println("isVotato");
        boolean expResult = false;
        boolean result = SchedaCandidatoFrame.isVotato();
        assertEquals(expResult, result);
    }
    
}
