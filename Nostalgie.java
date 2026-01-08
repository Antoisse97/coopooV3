
/**
 * Décrivez votre classe Nostalgie ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Nostalgie extends Emotion {
    public Nostalgie() {
        // Nom: Nostalgie, Bonus: 1, Couleur: Marron
        super("Nostalgie", 1, "Marron"); 
    }

    @Override
    public void seDeplacer(RobotEmotion robot, Monde monde) {
        // Nostalgie se déplace automatiquement vers les souvenirs
        System.out.println(robot.getNom() + " cherche le chemin vers les souvenirs...");
        // Logique : Le robot pourrait identifier la pièce 'Souvenirs' dans le Monde
    }

    @Override
    public void appliquerEffet(RobotEmotion robot) {
        System.out.println("Nostalgie débloque l'accès à la pièce des souvenirs."); 
    }
}

