
/**
 * Classes spécifiques qui modifient les capacités du robot selon son état actuel.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */
public class Anxiete extends Emotion {
    public Anxiete() {
        // Nom: Anxiété, Bonus: 2, Couleur: Orange
        super("Anxiété", 2, "Orange");
    }

    @Override
    public void seDeplacer(RobotEmotion robot, Monde monde) {
        // Le déplacement est géré par l'interface utilisateur (Clic)
        System.out.println("Le joueur contrôle " + robot.getNom() + " (Anxiété)."); 
    }

    @Override
    public void appliquerEffet(RobotEmotion robot) {
        System.out.println("Anxiété permet d'anticiper les obstacles.");
    }
}

