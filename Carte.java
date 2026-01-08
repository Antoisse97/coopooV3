
/**
 * Décrivez votre classe Carte ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Carte {
    // Tableau à deux dimensions représentant la grille du cerveau de Fousse
    private Cellule[][] grille;
    private int largeur;
    private int hauteur;

    public Carte(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.grille = new Cellule[largeur][hauteur];
        initialiserGrilleVide();
    }

    // Remplit la grille de cellules vides par défaut
    private void initialiserGrilleVide() {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                grille[i][j] = new Cellule(CellType.VIDE);
            }
        }
    }

    // Vérifie si une coordonnée est dans les limites et marchable [cite: 49, 126]
    public boolean estAccessible(int x, int y) {
        // 1. Vérification des limites de la grille
        if (x < 0 || x >= largeur || y < 0 || y >= hauteur) {
            return false;
        }
        // 2. Vérification si la cellule n'est pas un mur [cite: 158]
        return grille[x][y].estLibre();
    }

    // Récupère une cellule spécifique [cite: 49, 126]
    public Cellule getCellule(int x, int y) {
        if (x >= 0 && x < largeur && y >= 0 && y < hauteur) {
            return grille[x][y];
        }
        return null;
    }

    // Permet de modifier le type d'une cellule (ex: placer un MUR) 
    public void setCellule(int x, int y, Cellule c) {
        if (x >= 0 && x < largeur && y >= 0 && y < hauteur) {
            this.grille[x][y] = c;
        }
    }
}

