import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RobotEmotion extends Personnage {
    // Attributs spécifiques au robot
    private int energie;
    private int vitesse;
    private Cellule position;
    private boolean controleParJoueur;
    
    // Systèmes liés aux émotions et améliorations
    private Emotion emotionActuelle;
    private List<Emotion> emotionsDebloquees;
    private List<Amelioration> ameliorations;

    public RobotEmotion(String nom, int pointsDeVie, int energie, int vitesse) {
        // Appel au constructeur de la classe parente (Personnage)
        super(nom, pointsDeVie);
        this.energie = energie;
        this.vitesse = vitesse;
        this.emotionsDebloquees = new ArrayList<>();
        this.ameliorations = new ArrayList<>();
        this.emotionActuelle = new Anxiete();
        this.controleParJoueur = false; // Par défaut, sauf pour Anxiété
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

    // Changement d'émotion (ex: suite à une énigme)
    public void changerEmotion(Emotion nouvelle) {
        this.emotionActuelle = nouvelle;
    }
    
    public void ajouterEmotion(Emotion e) {
        if (e != null && !emotionsDebloquees.contains(e)) {
            this.emotionsDebloquees.add(e);
            System.out.println("Nouveau sentiment débloqué : " + e.nom);
        }
    }
    
    public void ajouterAmelioration(Amelioration a) {
        this.ameliorations.add(a);
        a.appliquer(this); // Applique immédiatement l'effet (vitesse, énergie, etc.)
    }
    
   public void tenterReponse(String reponse) {
        // Vérification de la réponse (insensible à la casse) 
        if ("Colère".equalsIgnoreCase(reponse)) {
            System.out.println("BRAVO! Bonne réponse !"); 
            
            // Déblocage de l'émotion Colère [cite: 190]
            Emotion nouvelle = new Colere();
            this.ajouterEmotion(nouvelle);
            this.changerEmotion(nouvelle);
            
            JOptionPane.showMessageDialog(null, "BRAVO! Bonne réponse !\nLe robot ressent maintenant: Colère"); 
        } else {
            JOptionPane.showMessageDialog(null, "Ce n'est pas la bonne émotion... Réessayez !");
        }
    }
    
    public boolean verifierReponse(String reponse) {
        // On compare la réponse reçue avec la solution (Colère)
        if ("Colère".equalsIgnoreCase(reponse)) {
            // Si c'est juste, on débloque et on change l'émotion
            Emotion nouvelle = new Colere();
            this.ajouterEmotion(nouvelle); 
            this.changerEmotion(nouvelle);
            return true; 
        }
        if ("Joie".equalsIgnoreCase(reponse)) {
            // Si c'est juste, on débloque et on change l'émotion
            Emotion nouvelle = new Joie();
            this.ajouterEmotion(nouvelle);
            this.changerEmotion(nouvelle);
            return true; 
        }
        if ("Tristesse".equalsIgnoreCase(reponse)) {
            // Si c'est juste, on débloque et on change l'émotion
            Emotion nouvelle = new Tristesse();
            this.ajouterEmotion(nouvelle);
            this.changerEmotion(nouvelle);
            return true; 
        }
        
        // Si c'est faux, on retourne false pour que JeuVue affiche un message d'erreur
        return false;
    }

    // Getters et Setters pour la position
    public Cellule getPosition() { return position; }
    public void setPosition(Cellule position) { this.position = position; }
    
    public int getEnergie() { return energie; }
    public void setControleParJoueur(boolean controle) { this.controleParJoueur = controle; }
    
    public Emotion getEmotion () {
        return this.emotionActuelle;
    }
}

