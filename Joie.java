/**
 * Classes spécifiques qui modifient les capacités du robot selon son état actuel.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */
public class Joie extends Emotion {
    public Joie() { super("Joie", 3, "Jaune"); }
    @Override
    public void seDeplacer(RobotEmotion robot, Monde monde) {
        System.out.println(robot.getNom() + " sautille partout avec Joie !");
    }
    @Override
    public void appliquerEffet(RobotEmotion robot) { /* Effet spécial */ }
}

