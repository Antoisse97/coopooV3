import java.util.ArrayList;
import java.util.List;

/**
 * Regroupe un ensemble de cellules pour former un lieu nommé (comme une chambre ou une salle).
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */

public class Piece {
    private String nom; 
    private List<Porte> portes; 
    private List<Piece> sorties; 
    private List<Monstre> monstres; 
    private List<Souvenir> souvenirs; // Liste pour stocker les bulles de mémoire 

    public Piece(String nom) {
        this.nom = nom;
        this.portes = new ArrayList<>();
        this.sorties = new ArrayList<>();
        this.monstres = new ArrayList<>();
        this.souvenirs = new ArrayList<>(); // Initialisation de la liste 
    }

    // --- AJOUT : Méthode pour placer un souvenir dans la pièce ---
    public void ajouterSouvenir(Souvenir s) {
        if (s != null) {
            this.souvenirs.add(s);
            System.out.println("Le souvenir '" + s.getDescription() + "' a été ajouté à " + this.nom);
        }
    }

    // Méthode pour récupérer les souvenirs (utile pour l'interface graphique)
    public List<Souvenir> getSouvenirs() {
        return souvenirs;
    }
    // Ajoute une pièce voisine accessible
    public void ajouterSortie(Piece piece) {
        if (piece != null) {
            this.sorties.add(piece);
        }
    }

    // Ajoute une porte physique à la pièce
    public void ajouterPorte(Porte porte) {
        this.portes.add(porte); 
    }

    // Ajoute un monstre (ex: Monstre des secrets)
    public void ajouterMonstre(Monstre m) {
        this.monstres.add(m);
    }

    // Getters
    public String getNom() { return nom; }
    public List<Piece> getSorties() { return sorties; }
    public List<Monstre> getMonstres() { return monstres; }
}

