
/**
 * Représente la grille logique du jeu composée d'un ensemble de cellules.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
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
    public boolean estAccessible(int ligne, int colonne) {
        // 1. Vérification des limites de la grille
        if (ligne < 0 || ligne >= hauteur || colonne < 0 || colonne >= largeur) {
            return false;
        }
        // 2. Vérification si la cellule n'est pas un mur [cite: 158]
        return grille[ligne][colonne].estLibre();
    }

    // Récupère une cellule spécifique [cite: 49, 126]
    public Cellule getCellule(int ligne, int colonne) {
        if (ligne >= 0 && ligne < hauteur && colonne >= 0 && colonne < largeur) {
            return grille[ligne][colonne];
        }
        return null;
    }

    // Permet de modifier le type d'une cellule (ex: placer un MUR) 
    public void setCellule(int ligne, int colonne, Cellule c) {
        if (ligne >= 0 && ligne < hauteur && colonne >= 0 && colonne < largeur) {
            this.grille[ligne][colonne] = c;
        }
    }
}

