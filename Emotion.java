
/**
 * Classe abstraite définissant le comportement général d'un état émotionnel.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */
public abstract class Emotion {
    protected String nom; 
    protected int bonusVitesse;
    protected String couleur; 

    public Emotion(String nom, int bonusVitesse, String couleur) {
        this.nom = nom;
        this.bonusVitesse = bonusVitesse;
        this.couleur = couleur;
    }

    // Méthode abstraite : chaque émotion aura sa propre logique de mouvement 
    public abstract void seDeplacer(RobotEmotion robot, Monde monde);

    // Méthode pour appliquer un effet spécial lié à l'émotion 
    public abstract void appliquerEffet(RobotEmotion robot);
    
    public String getNom() {
        return nom;
    }
    
}

