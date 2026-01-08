/**
 * Décrivez votre classe Joie ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
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

