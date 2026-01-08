
/**
 * Décrivez votre classe Tristesse ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Tristesse extends Emotion {
    public Tristesse() { super("Tristesse", 1, "Bleu"); }
    @Override
    public void seDeplacer(RobotEmotion robot, Monde monde) {
        System.out.println(robot.getNom() + " traîne les pieds avec Tristesse...");
    }
    @Override
    public void appliquerEffet(RobotEmotion robot) { /* Effet spécial */ }
}

