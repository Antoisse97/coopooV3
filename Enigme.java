import javax.swing.JOptionPane;

/**
 * Décrivez votre classe Enigme ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */

public class Enigme {

    private String question;
    private String solution;
    private Emotion emotionADebloquer;

    /* =========================
       CONSTRUCTEUR UTILISÉ PAR MONDE
       (celui que ton code appelle)
       ========================= */
    public Enigme(String question, String solution, Emotion emotionADebloquer) {
        this.question = question;
        this.solution = solution;
        this.emotionADebloquer = emotionADebloquer;
    }

    /* =========================
       LOGIQUE DE L'ÉNIGME
       ========================= */
    public boolean poser(RobotEmotion robot) {
        String saisie = JOptionPane.showInputDialog(question);

        if (saisie == null) {
            JOptionPane.showMessageDialog(null,
                    "Vous devez répondre pour continuer !");
            return false;
        }

        if (verifierReponse(saisie)) {
            robot.debloquerEmotion(emotionADebloquer);
            JOptionPane.showMessageDialog(null,
                    "Bonne réponse !\nNouvelle émotion : "
                            + emotionADebloquer.getNom());
            return true;
        }

        JOptionPane.showMessageDialog(null,
                "Mauvaise réponse.\nVous ne pouvez pas passer !");
        return false;
    }

    /* =========================
       VÉRIFICATION STRICTE
       (accents + casse)
       ========================= */
    private boolean verifierReponse(String saisie) {

        String rep = saisie.trim();

        if (emotionADebloquer instanceof Colere) {
            return rep.equalsIgnoreCase("colere")
                || rep.equalsIgnoreCase("colère");
        }

        if (emotionADebloquer instanceof Tristesse) {
            return rep.equalsIgnoreCase("tristesse");
        }

        if (emotionADebloquer instanceof Joie) {
            return rep.equalsIgnoreCase("joie");
        }

        if (emotionADebloquer instanceof Nostalgie) {
            return rep.equalsIgnoreCase("nostalgie");
        }

        return rep.equalsIgnoreCase(solution);
    }

    public Emotion getEmotion() {
        return emotionADebloquer;
    }
}

    

