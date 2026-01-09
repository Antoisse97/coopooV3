
/**
 * Classes spécifiques qui modifient les capacités du robot selon son état actuel.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
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

