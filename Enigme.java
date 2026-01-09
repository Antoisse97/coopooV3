
/**
 * Un défi intellectuel lié à une émotion que le robot doit résoudre pour progresser.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */
public class Enigme {
    private String question;
    private String solution;
    private Emotion emotionADebloquer;

    public Enigme(String question, String solution, Emotion emotionADebloquer) {
        this.question = question;
        this.solution = solution;
        this.emotionADebloquer = emotionADebloquer;
    }

    // Vérifie la réponse et met à jour le robot si c'est juste
    public boolean tenter(String saisie, RobotEmotion robot) {
        if (solution.equalsIgnoreCase(saisie)) {
            robot.ajouterEmotion(emotionADebloquer);
            robot.changerEmotion(emotionADebloquer);
            return true;
        }
        return false;
    }

    public String getQuestion() { return question; }
}

