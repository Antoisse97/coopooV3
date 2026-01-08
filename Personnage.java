/**
 * Classe de base abstraite pour tous les personnages du jeu.
 * Elle regroupe le nom et la gestion de la santé.
 */
public abstract class Personnage {
    // Attributs privés pour respecter l'encapsulation 
    private String nom;
    private int pointsDeVie;

    public Personnage(String nom, int pointsDeVie) {
        this.nom = nom;
        this.pointsDeVie = pointsDeVie;
    }

    // Méthode pour diminuer la vie 
    public void perdrePV(int valeur) {
        this.pointsDeVie -= valeur;
        if (this.pointsDeVie < 0) this.pointsDeVie = 0;
    }

    // Méthode pour soigner le personnage 
    public void gagnerPV(int valeur) {
        this.pointsDeVie += valeur;
    }

    // Vérifie si le personnage est encore en vie 
    public boolean estVivant() {
        return this.pointsDeVie > 0;
    }

    // Getters et Setters nécessaires
    public String getNom() { return nom; }
    public int getPointsDeVie() { return pointsDeVie; }
}