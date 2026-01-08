
/**
 * Décrivez votre classe Colere ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
// Colère : Se déplace rapidement en ligne droite [cite: 36]
public class Colere extends Emotion {
    public Colere() { super("Colère", 5, "Rouge"); }
    @Override
    public void seDeplacer(RobotEmotion robot, Monde monde) {
        System.out.println(robot.getNom() + " fonce tout droit avec Colère !");
    }
    @Override
    public void appliquerEffet(RobotEmotion robot) { /* Effet spécial */ }
}

