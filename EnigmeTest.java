import static org.junit.Assert.*;
import org.junit.Test;

public class EnigmeTest { 

    @Test
    public void testTenterBonneReponse() {
        // Préparation
        RobotEmotion robot = new RobotEmotion("Wall-E", 100, 10, 1);
        Colere colere = new Colere();
        Enigme enigme = new Enigme("Qui est rouge ?", "Colère", colere);
        
        // Action
        boolean succes = enigme.tenter("Colère", robot);
        
        // Vérification
        assertTrue("L'énigme devrait être réussie", succes);
        assertEquals("Le robot devrait être en colère", "Colère", robot.getEmotion().getNom());
    }

    @Test
    public void testTenterMauvaiseReponse() {
        // Préparation
        RobotEmotion robot = new RobotEmotion("Wall-E", 100, 10, 1);
        robot.changerEmotion(new Anxiete()); 
        
        Enigme enigme = new Enigme("Qui est rouge ?", "Colère", new Colere());
        
        // Action
        boolean succes = enigme.tenter("Joie", robot);
        
        // Vérification
        assertFalse("L'énigme devrait échouer", succes);
        assertEquals("L'émotion ne doit pas avoir changé", "Anxiété", robot.getEmotion().getNom());
    }
}