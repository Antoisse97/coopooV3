
import javax.swing.SwingUtilities;

/**
 * Classes de lancement et de vérification du bon fonctionnement.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */

public class App {
    public static void main(String[] args) {
        // 1. Initialisation de la Carte (ex: 10x10) 
        Carte carte = new Carte(10, 10);
        
        // 2. Création du Monde [cite: 174, 175]
        Monde monde1 = new Monde(carte);
        
        // 3. Création du Robot (Wall-E) en position (0,0) 
        RobotEmotion robot1 = new RobotEmotion("Wall-E", 100, 50, 1);
        robot1.setPosition(carte.getCellule(0, 0)); 
        monde1.ajouterRobot(robot1);
        
        // 4. Configuration du contenu du monde (Pièces, Monstres, Souvenirs) 
        monde1.initialiserMonde();
        
        // 5. Lancement de l'interface graphique 
        SwingUtilities.invokeLater(() -> {
            new JeuVue(monde1, robot1);
        });
    }
}

