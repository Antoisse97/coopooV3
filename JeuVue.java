/**
 * Gère l'affichage graphique et l'interface utilisateur pour le joueur.
 *
 * @author (Groupe 7)
 * @version (Version finale 09/01)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JeuVue extends JFrame {
    private Monde monde;
    private RobotEmotion robot;
    private JButton[][] boutons;
    private int taille = 10; 

    // Nouveaux composants pour les infos
    private JLabel labelPV;
    private JLabel labelEmotion;
    private JLabel labelLieu;

    public JeuVue(Monde monde, RobotEmotion robot) {
        this.monde = monde;
        this.robot = robot;
        this.boutons = new JButton[taille][taille];

        setTitle("Vice-Versa : À la recherche du Souvenir perdu"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Panel central pour la grille
        JPanel grillePanel = new JPanel(new GridLayout(taille, taille));
        initialiserInterface(grillePanel);
        add(grillePanel, BorderLayout.CENTER);

        // 2. Panel de droite pour les statistiques (Panneau d'infos)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Statistiques"));
        infoPanel.setPreferredSize(new Dimension(200, 800));
        infoPanel.setBackground(new Color(240, 240, 240));

        labelPV = new JLabel(" PV : " + robot.getPointsDeVie());
        labelEmotion = new JLabel(" Émotion : " + robot.getEmotion().getNom());
        labelLieu = new JLabel(" Lieu : Tour de contrôle");

        // Style des étiquettes
        Font fontInfo = new Font("Arial", Font.BOLD, 14);
        labelPV.setFont(fontInfo);
        labelEmotion.setFont(fontInfo);
        labelLieu.setFont(fontInfo);

        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(labelPV);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(labelEmotion);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(labelLieu);
        
        add(infoPanel, BorderLayout.EAST);

        // 3. Configuration du Clavier
        configurerControlesClavier();
        setFocusable(true);
        requestFocusInWindow();

        mettreAJour();
        
        setSize(1000, 800); // Élargi pour faire de la place au panneau
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configurerControlesClavier() {
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();
    
        im.put(KeyStroke.getKeyStroke("UP"), "haut");
        im.put(KeyStroke.getKeyStroke("DOWN"), "bas");
        im.put(KeyStroke.getKeyStroke("LEFT"), "gauche");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "droite");
    
        am.put("haut", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(-1, 0); } });
        am.put("bas", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(1, 0); } });
        am.put("gauche", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(0, -1); } });
        am.put("droite", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(0, 1); } });
    }

    private void tenterDeplacement(int dx, int dy) {
    int ligneActuelle = -1, colonneActuelle = -1;

    // 1. Recherche de la position actuelle du robot
    for (int i = 0; i < taille; i++) {
        for (int j = 0; j < taille; j++) {
            if (monde.getCarte().getCellule(i, j) == robot.getPosition()) {
                ligneActuelle = i;
                colonneActuelle = j;
                break;
            }
        }
    }

    int nx = ligneActuelle + dx;
    int ny = colonneActuelle + dy;

    // 2. Limites et accessibilité
    if (nx < 0 || nx >= taille || ny < 0 || ny >= taille) return;
    if (!monde.getCarte().estAccessible(nx, ny)) return;

    Cellule cible = monde.getCarte().getCellule(nx, ny);

    // 3. Monstres simples (avant déplacement)
    if (!cible.getMonstres().isEmpty()) {
        Monstre m = cible.getMonstres().get(0);
        int choix = JOptionPane.showConfirmDialog(this,
                "Un " + m.getNom() + " bloque le passage ! Combattre ?");
        if (choix == JOptionPane.YES_OPTION) {
            m.attaquer(robot);
            if (!robot.estVivant()) {
                JOptionPane.showMessageDialog(this, "Le robot a succombé...");
                System.exit(0);
            }
        } else {
            return; // on ne se déplace pas
        }
    }

    // 4. ÉNIGMES DE PIÈCES — blocage AVANT déplacement
    // Colère
    if (nx == 2 && ny == 2 && !(robot.getEmotion() instanceof Colere)) {
        String rep = JOptionPane.showInputDialog(this,
                "Énigme : Quelle émotion bouillonne face à l'injustice ?");
        if (rep == null || !robot.verifierReponse(rep)) {
            JOptionPane.showMessageDialog(this,
                    "Mauvaise réponse... Vous restez dans la pièce.");
            return; // pas de déplacement
        } else {
            JOptionPane.showMessageDialog(this, "La Colère vous envahit !");
        }
    }

    // Joie
    if (nx == 1 && ny == 7 && !(robot.getEmotion() instanceof Joie)) {
        String rep = JOptionPane.showInputDialog(this,
                "Énigme : Quelle émotion te donne le sourire ?");
        if (rep == null || !robot.verifierReponse(rep)) {
            JOptionPane.showMessageDialog(this,
                    "Mauvaise réponse... Vous restez dans la pièce.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "La Joie vous envahit !");
        }
    }

    // Tristesse
    if (nx == 5 && ny == 7 && !(robot.getEmotion() instanceof Tristesse)) {
        String rep = JOptionPane.showInputDialog(this,
                "Énigme : Quelle émotion te fait pleurer ?");
        if (rep == null || !robot.verifierReponse(rep)) {
            JOptionPane.showMessageDialog(this,
                    "Mauvaise réponse... Vous restez dans la pièce.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "La Tristesse vous envahit !");
        }
    }

    // Nostalgie
    if (nx == 8 && ny == 8 && !(robot.getEmotion() instanceof Nostalgie)) {
        String rep = JOptionPane.showInputDialog(this,
                "Énigme : Quelle émotion te rappelle des souvenirs ?");
        if (rep == null || !robot.verifierReponse(rep)) {
            JOptionPane.showMessageDialog(this,
                    "Mauvaise réponse... Vous restez dans la pièce.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "La Nostalgie vous envahit !");
        }
    }

    // 5. Duel final (si monstre spécial)
    if (!cible.getMonstres().isEmpty()) {
        Monstre m = cible.getMonstres().get(0);
        int choix = JOptionPane.showConfirmDialog(this,
                "Le " + m.getNom() + " bloque le passage ! Duel ?");
        if (choix == JOptionPane.YES_OPTION) {
            if (executerDuelFinal(m)) {
                cible.getMonstres().clear(); // Victoire : passage libéré
            } else {
                return; // Échec : pas de déplacement
            }
        } else {
            return; // refus : pas de déplacement
        }
    }

    // 6. Déplacement effectif (UNE SEULE FOIS, à la fin)
    robot.setPosition(cible);

    // 7. Souvenirs
    if (cible.getPiece() != null && !cible.getPiece().getSouvenirs().isEmpty()) {
        examinerSouvenirs(cible.getPiece());
    }

    mettreAJour();
}

    
    private void initialiserInterface(JPanel panel) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                boutons[i][j] = new JButton();
                boutons[i][j].setEnabled(false);
                panel.add(boutons[i][j]);
            }
        }
    }
    
    private boolean executerDuelFinal(Monstre m) {
        int score = 0;
        
        // Définition des énigmes du monstre
        String[][] enigmes = {
            {"Je ne suis jamais là, mais je suis toujours à venir. Qui suis-je ?", "Le futur"},
            {"Plus j'en ai, moins tu en vois. Qui suis-je ?", "L'obscurité"},
            {"Si tu me nommes, je disparais. Qui suis-je ?", "Le silence"}
        };
    
        JOptionPane.showMessageDialog(this, "COMBAT FINAL : Répondez correctement à 2 questions sur 3 !");
    
        for (int i = 0; i < enigmes.length; i++) {
            String reponse = JOptionPane.showInputDialog(this, "Question " + (i + 1) + "/3 :\n" + enigmes[i][0]);
            
            // Vérification de la réponse (ignore la casse et les espaces)
            if (reponse != null && reponse.trim().equalsIgnoreCase(enigmes[i][1])) {
                score++;
                JOptionPane.showMessageDialog(this, "Correct ! (Score : " + score + ")");
            } else {
                String msgFaux = (reponse == null) ? "Abandon..." : "Faux ! La réponse était : " + enigmes[i][1];
                JOptionPane.showMessageDialog(this, msgFaux);
            }
        }
    
        return (score >= 2); // Renvoie vrai si 2 ou 3 bonnes réponses
    }

    private void examinerSouvenirs(Piece piece) {
        for (Souvenir s : piece.getSouvenirs()) {
            JOptionPane.showMessageDialog(this, "Examen : " + s.getDescription());
            if (s.estLeSouvenirPerdu()) {
                JOptionPane.showMessageDialog(this, "VICTOIRE ! Clés trouvées ! Elles sont dans la poche de Mr FOUSSE");
                System.exit(0);
            }
        }
    }

    public void mettreAJour() {
        // Mise à jour des labels (Infos à droite)
        labelPV.setText(" PV : " + robot.getPointsDeVie());
        labelEmotion.setText(" Émotion : " + robot.getEmotion().getNom());
        
        Cellule cellRobot = robot.getPosition();
        if (cellRobot.getPiece() != null) {
            labelLieu.setText(" Lieu : " + cellRobot.getPiece().getNom());
        } else {
            labelLieu.setText(" Lieu : Cerveau de FOUSSE");
        }

        // Mise à jour de la grille
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Cellule cell = monde.getCarte().getCellule(i, j);
                JButton btn = boutons[i][j];
                btn.setText("");
                btn.setBackground(Color.WHITE);

                if (cell.getType() == CellType.MUR) {
                    btn.setBackground(Color.DARK_GRAY);
                } else if (cell.getType() == CellType.PORTE) {
                    btn.setText("🚪");
                    btn.setBackground(new Color(200, 150, 100));
                }

                if (cellRobot == cell) {
                    btn.setText("🤖");
                                        // --- AJOUTS POUR MAC ---
                    btn.setOpaque(true); 
                    btn.setBorderPainted(false);
                    
                    if (robot.getEmotion() instanceof Anxiete) btn.setBackground(Color.ORANGE);
                    else if (robot.getEmotion() instanceof Colere) btn.setBackground(Color.RED);
                    else if (robot.getEmotion() instanceof Nostalgie) btn.setBackground(new Color(139, 69, 19));
                    else if (robot.getEmotion() instanceof Joie) btn.setBackground(Color.YELLOW);
                    else if (robot.getEmotion() instanceof Tristesse) btn.setBackground(Color.BLUE);
                }
                
                if (!cell.getMonstres().isEmpty()) {
                    btn.setText("👾");
                }
            }
        }
    }
}
