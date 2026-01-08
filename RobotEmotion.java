import java.util.ArrayList;
import java.util.List;

public class RobotEmotion extends Personnage {
    // Attributs spécifiques au robot
    private int energie;
    private int vitesse;
    private Cellule position;
    // private boolean controleParJoueur;
    
    // Systèmes liés aux émotions et améliorations
    private Emotion emotionActuelle;
    private List<Emotion> emotionsDebloquees;

    public RobotEmotion(String nom, int pointsDeVie, int energie, int vitesse) {
        super(nom, pointsDeVie);
        this.energie = energie;
        this.vitesse = vitesse;
        this.emotionsDebloquees = new ArrayList<>();
        this.emotionActuelle = new Anxiete();
    }
    
    // Méthode principale de mouvement
     public void seDeplacer() {
        if (emotionActuelle != null) {
            // Le robot délègue le comportement de déplacement à l'émotion
            // C'est ici que le polymorphisme opère
            System.out.println(getNom() + " se déplace avec l'humeur : " + emotionActuelle.nom);
        }
    }

    // Gestion de l'énergie
    public void perdreEnergie(int valeur) {
        this.energie -= valeur;
        if (this.energie < 0) this.energie = 0;
    }

    public void gagnerEnergie(int valeur) {
        this.energie += valeur;
    }

        /* =========================
       GESTION DES ÉMOTIONS
       ========================= */

    public boolean possedeEmotion(Class<? extends Emotion> e) {
        for (Emotion em : emotionsDebloquees) {
            if (em.getClass().equals(e)) return true;
        }
        return false;
    }

    public void debloquerEmotion(Emotion e) {
        if (!possedeEmotion(e.getClass())) {
            emotionsDebloquees.add(e);
            emotionActuelle = e;
        }
    }

    public Emotion getEmotion() {
        return emotionActuelle;
    }

     /* =========================
       POSITION
       ========================= */

    public Cellule getPosition() {
        return position;
    }

    public void setPosition(Cellule position) {
        this.position = position;
    }
}

