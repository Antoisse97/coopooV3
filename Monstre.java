
/**
 * Décrivez votre classe Monstre ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */

public class Monstre extends Personnage {
    private int force;

    public Monstre(String nom, int pv, int force) {
        super(nom, pv); // Utilise le constructeur de Personnage 
        this.force = force;
    }

    // Méthode d'attaque contre un robot annexe
    public void attaquer(Personnage cible) {
        System.out.println(getNom() + " attaque " + cible.getNom() + " !");
        cible.perdrePV(this.force); 
    }
    
}

