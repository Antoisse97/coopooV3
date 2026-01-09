
/**
 * Décrivez votre classe Monde ici.
 *
 * @author (caleb)
 * @version (un numéro de version ou une date)
 */
import java.util.ArrayList;
import java.util.List;

public class Monde {
    private List<Piece> pieces;
    private List<RobotEmotion> robots;
    private Carte carte;

    public Monde(Carte carte) {
        this.carte = carte;
        this.pieces = new ArrayList<>();
        this.robots = new ArrayList<>();
    }
    
    public void initialiserMonde() {
        // --- 1. CRÉATION DES PIÈCES PRINCIPALES ---
        Piece tourControle = new Piece("Tour de contrôle");
        Piece chambreColere = new Piece("Chambre de la Colère");
        Piece chambreTristesse = new Piece("Chambre de la Tristesse");
        Piece chambrePeur = new Piece("Chambre de la Peur");
        Piece salleSouvenirs = new Piece("Salle des Souvenirs");

        // --- 2. INSTALLATION DES PIÈCES SUR LA CARTE ---
        this.installerPiece(tourControle, 0, 0, 2, 2);      // Départ
        this.installerPiece(chambreColere, 2, 2, 1, 1);     // Zone Colère
        this.installerPiece(chambreTristesse, 5, 7, 1, 1);  // Zone Tristesse
        this.installerPiece(chambrePeur, 1, 7, 1, 1);       // Zone Joie
        this.installerPiece(salleSouvenirs, 8, 8, 2, 2);    // Arrivée finale

        // --- 3. PLACEMENT DES 8 ÉNIGMES ---
        // Énigmes Émotions Primaires
        this.carte.getCellule(2, 2).setEnigme(new Enigme("Je suis un feu qui brûle quand l'injustice frappe. Qui suis-je ?", "Colère", new Colere()));
        this.carte.getCellule(5, 1).setEnigme(new Enigme("Je suis une larme qui tombe et un cœur qui s'alourdit. Qui suis-je ?", "Tristesse", new Tristesse()));
        // this.carte.getCellule(1, 5).setEnigme(new Enigme("Je fais trembler tes mains devant le danger. Qui suis-je ?", "Peur", new Peur()));
        // this.carte.getCellule(3, 7).setEnigme(new Enigme("Je fronce ton nez devant un plat de brocolis. Qui suis-je ?", "Dégoût", new Degout()));

        // Énigmes Transition et Joie
        this.carte.getCellule(6, 3).setEnigme(new Enigme("Je suis un rire éclatant et un soleil dans ta poitrine. Qui suis-je ?", "Joie", new Joie()));
        // this.carte.getCellule(4, 8).setEnigme(new Enigme("J'ouvre tes yeux tout grands par surprise. Qui suis-je ?", "Surprise", new Surprise()));

        // Énigmes Finales
        this.carte.getCellule(7, 6).setEnigme(new Enigme("Je suis le parfum d'un vieux souvenir. Qui suis-je ?", "Nostalgie", new Nostalgie()));
        this.carte.getCellule(9, 7).setEnigme(new Enigme("Je suis la bibliothèque de ta vie. Qui suis-je ?", "Mémoire", new Joie()));

        // --- 4. OBSTACLES ET MONSTRES ---
        // Le Monstre des Secrets garde l'accès à la zone finale (8,8)
        Monstre monstreSecrets = new Monstre("Monstre des Secrets", 50, 10); 
        this.carte.getCellule(7, 8).ajouterMonstre(monstreSecrets); 

        // --- 5. CONDITION DE VICTOIRE ---
        Souvenir cles = new Souvenir("Une vieille boîte en métal", "Clés de voiture");
        salleSouvenirs.ajouterSouvenir(cles); 

        // --- 6. GÉNÉRATION DU LABYRINTHE (Murs) ---
        genererMurs();
    }

    private void genererMurs() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // On place des murs sur les cases vides pour créer des couloirs, 
                // mais on laisse le chemin libre pour les énigmes et les pièces.
                Cellule c = carte.getCellule(i, j);
                if (c.getPiece() == null && !c.aEnigme() && (i + j) % 4 == 0) {
                    c.setType(CellType.MUR);
                }
            }
        }
    }

    public void installerPiece(Piece piece, int xDebut, int yDebut, int largeur, int hauteur) {
        this.pieces.add(piece);
        for (int i = xDebut; i < xDebut + largeur; i++) {
            for (int j = yDebut; j < yDebut + hauteur; j++) {
                Cellule cell = carte.getCellule(i, j);
                if (cell != null) {
                    cell.setPiece(piece);
                    cell.setType(CellType.PORTE); // Les entrées de pièces sont des portes
                }
            }
        }
    }

    public Piece trouverPiece(String nom) {
        for (Piece p : pieces) {
            if (p.getNom().equalsIgnoreCase(nom)) return p;
        }
        return null;
    }

    public void ajouterRobot(RobotEmotion r) {
        this.robots.add(r);
    }
    
    public Carte getCarte() { return carte; }
}

