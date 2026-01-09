/**
 * Classe parente définissant les attributs de base (nom, points de vie) pour les entités vivantes.
 * 
 * @author (Groupe 7)
 * @version (Version finale 09/01)
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