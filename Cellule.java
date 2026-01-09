
import java.util.ArrayList;
import java.util.List;

/**
 * Définit une case spécifique de la carte pouvant contenir un mur, une porte, un monstre ou une énigme.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */

public class Cellule {
    private CellType type;
    private Porte porte;
    private Piece piece;
    private Enigme enigme;
    private List<Monstre> monstres = new ArrayList<>();
    
    public Cellule(CellType type) {
        this.type = type;
    }

    // Permet d'ajouter un monstre spécifiquement sur cette case
    public void ajouterMonstre(Monstre m) {
        this.monstres.add(m);
    }

    public List<Monstre> getMonstres() {
        return monstres;
    }
    // Vérifie si le robot peut entrer sur cette case 
    public boolean estLibre() {
        return this.type != CellType.MUR;
    }

    // Vérifie s'il y a une porte sur cette case 
    public boolean contientPorte() {
        return this.type == CellType.PORTE && this.porte != null;
    }

    // Getters et Setters
    public CellType getType() { return type; }
    public void setType(CellType type) { this.type = type; }
    
    public Porte getPorte() { return porte; }
    public void setPorte(Porte porte) { this.porte = porte; }
    
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
    
    public void setEnigme(Enigme e) { this.enigme = e; }
    public Enigme getEnigme() { return enigme; }
    public boolean aEnigme() { return enigme != null; }
}

