import javax.swing.JOptionPane;

/**
 * Décrivez votre classe Enigme ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */

public class Enigme {

    private Emotion emotion;
    private String question;
    private String[] reponsesValides;

    public Enigme(Emotion emotion, String question, String... reponsesValides) {
        this.emotion = emotion;
        this.question = question;
        this.reponsesValides = reponsesValides;
    }

    public boolean poser(RobotEmotion robot) {
        String saisie = JOptionPane.showInputDialog(question);

        if (saisie == null) {
            JOptionPane.showMessageDialog(null, "Vous devez répondre pour avancer !");
            return false;
        }

        for (String rep : reponsesValides) {
            if (rep.equalsIgnoreCase(saisie.trim())) {
                robot.debloquerEmotion(emotion);
                JOptionPane.showMessageDialog(null,
                        "Bonne réponse ! Nouvelle émotion : " + emotion.getNom());
                return true;
            }
        }

        JOptionPane.showMessageDialog(null,
                "Mauvaise réponse.\nVous ne pouvez pas passer !");
        return false;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    /* =========================
       ENIGMES DU JEU
       ========================= */

    public static Enigme ENIGME_COLERE = new Enigme(
            new Colere(),
            "Quelle émotion bouillonne face à l'injustice ?",
            "colere", "colère", "COLERE", "COLÈRE"
    );

    public static Enigme ENIGME_TRISTESSE = new Enigme(
            new Tristesse(),
            "Quelle émotion surgit lors d'une perte ?",
            "tristesse", "TRISTESSE"
    );

    public static Enigme ENIGME_JOIE = new Enigme(
            new Joie(),
            "Quelle émotion illumine les moments heureux ?",
            "joie", "JOIE"
    );
}

    

