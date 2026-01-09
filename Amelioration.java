
/**
 * Objet ou capacité permettant d'augmenter les statistiques (énergie, vie) du robot.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */
public class Amelioration {
    private AmelioType type; // VITESSE, ENERGIE ou EMOTION 
    private int valeur;
    private Emotion nouvelleEmotion; // Utilisé si le type est EMOTION [cite: 95]

    public Amelioration(AmelioType type, int valeur) {
        this.type = type;
        this.valeur = valeur;
    }

    // Surcharge du constructeur pour le déblocage d'émotion
    public Amelioration(Emotion emotion) {
        this.type = AmelioType.EMOTION;
        this.nouvelleEmotion = emotion;
    }

    public void appliquer(RobotEmotion robot) {
        switch (this.type) {
            case VITESSE:
                augmenterVitesse(robot);
                break;
            case ENERGIE:
                augmenterEnergie(robot);
                break;
            case EMOTION:
                debloquerEmotion(robot);
                break;
        }
    }

    private void augmenterVitesse(RobotEmotion robot) {
        // Logique pour augmenter la vitesse du robot [cite: 172]
        System.out.println("Vitesse augmentée de " + valeur);
    }

    private void augmenterEnergie(RobotEmotion robot) {
        robot.gagnerEnergie(valeur); // 
    }

    private void debloquerEmotion(RobotEmotion robot) {
        if (nouvelleEmotion != null) {
            robot.ajouterEmotion(nouvelleEmotion); //
        }
    }
}

