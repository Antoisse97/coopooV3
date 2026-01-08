
/**
 * Décrivez votre classe Souvenir ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */

public class Souvenir {
    private String description;
    private String cle; // Identifiant unique ou contenu du souvenir 

    public Souvenir(String description, String cle) {
        this.description = description;
        this.cle = cle;
    }

    // Vérifie si ce souvenir est celui qui contient les clés de voiture 
    public boolean estLeSouvenirPerdu() {
        return "Clés de voiture".equalsIgnoreCase(this.cle);
    }

    public String getDescription() {
        return description;
    }
}

