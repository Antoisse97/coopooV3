
/**
 * Un adversaire situé dans une cellule qui peut attaquer le robot.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
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

