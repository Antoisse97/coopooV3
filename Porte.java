
/**
 * Décrivez votre classe Porte ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Porte {
    private Piece destination;
    private Amelioration effet; // Amélioration reçue en passant la porte
    private int requiertEmotions; // Nombre d'émotions nécessaires ou type

    public Porte(Piece destination) {
        this.destination = destination;
    }

    // Vérifie si le robot a les capacités pour passer [cite: 138]
    public boolean estAccessible(RobotEmotion robot) {
        // Logique : par exemple, vérifier si le robot a assez d'émotions débloquées
        return true; 
    }

    // Action d'ouvrir la porte [cite: 91, 138]
    public void ouvrir(RobotEmotion robot) {
        if (estAccessible(robot)) {
            System.out.println("La porte vers " + destination.getNom() + " s'ouvre !");
            appliquerEffet(robot);
        }
    }

    public void appliquerEffet(RobotEmotion robot) {
        if (effet != null) {
            effet.appliquer(robot); // 
        }
    }

    // Getters et Setters
    public Piece getDestination() { return destination; }
    public void setEffet(Amelioration effet) { this.effet = effet; }
}

