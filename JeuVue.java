import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Décrivez votre classe JeuVue ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */


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
    
        am.put("haut", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(0, -1); } });
        am.put("bas", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(0, 1); } });
        am.put("gauche", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(-1, 0); } });
        am.put("droite", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(1, 0); } });
    }

    private void tenterDeplacement(int dx, int dy) {

    int ligneActuelle = -1;
    int colonneActuelle = -1;

    for (int i = 0; i < taille; i++) {
        for (int j = 0; j < taille; j++) {
            if (monde.getCarte().getCellule(i, j) == robot.getPosition()) {
                ligneActuelle = i;
                colonneActuelle = j;
            }
        }
    }

    int nouvelleLigne = ligneActuelle + dy;
    int nouvelleColonne = colonneActuelle + dx;

    if (nouvelleLigne < 0 || nouvelleLigne >= taille ||
        nouvelleColonne < 0 || nouvelleColonne >= taille) return;

    Cellule cible = monde.getCarte().getCellule(nouvelleLigne, nouvelleColonne);
    cible.decouvrir();

    /* ===== MUR ===== */
    if (cible.getType() == CellType.MUR) {

        Enigme enigme = Enigme.ENIGME_COLERE; // exemple

        if (!robot.possedeEmotion(enigme.getEmotion().getClass())) {
            boolean succes = enigme.poser(robot);
            if (!succes) {
                mettreAJour();
                return;
            }
        }
    }

    /* ===== PORTE ===== */
    if (cible.getType() == CellType.PORTE) {

        Enigme enigme = Enigme.ENIGME_JOIE; // ou TRISTESSE selon ta carte

        if (!robot.possedeEmotion(enigme.getEmotion().getClass())) {
            boolean succes = enigme.poser(robot);
            if (!succes) {
                mettreAJour();
                return;
            }
        }
    }

    /* ===== MONSTRE ===== */
    if (!cible.getMonstres().isEmpty()) {
        Monstre m = cible.getMonstres().get(0);
        m.attaquer(robot);

        if (!robot.estVivant()) {
            JOptionPane.showMessageDialog(this, "Le robot a succombé...");
            System.exit(0);
        }

        robot.debloquerEmotion(new Nostalgie());
    }

    robot.setPosition(cible);
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

    private void examinerSouvenirs(Piece piece) {
        for (Souvenir s : piece.getSouvenirs()) {
            JOptionPane.showMessageDialog(this, "Examen : " + s.getDescription());
            if (s.estLeSouvenirPerdu()) {
                JOptionPane.showMessageDialog(this, "VICTOIRE ! Clés trouvées !");
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
            labelLieu.setText(" Lieu : Cerveau de Fousse");
        }
    
        // Mise à jour de la grille
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
    
                Cellule cell = monde.getCarte().getCellule(i, j);
                JButton btn = boutons[i][j];
    
                // Case vide par défaut
                btn.setText("");
                btn.setBackground(Color.WHITE);
    
                // Si la case n'a jamais été découverte, on n'affiche rien
                if (!cell.estDecouverte()) {
                    continue;
                }
    
                // Mur découvert
                if (cell.getType() == CellType.MUR) {
                    btn.setBackground(Color.BLACK);
                }
    
                // Porte découverte
                if (cell.getType() == CellType.PORTE) {
                    btn.setText("🚪");
                    btn.setBackground(new Color(200, 150, 100));
                }
    
                // Monstre découvert
                if (!cell.getMonstres().isEmpty()) {
                    btn.setText("👾");
                }
    
                // Robot (toujours visible)
                if (cell == cellRobot) {
                    btn.setText("🤖");
    
                    if (robot.getEmotion() instanceof Colere)
                        btn.setBackground(Color.RED);
                    else if (robot.getEmotion() instanceof Tristesse)
                        btn.setBackground(Color.BLUE);
                    else if (robot.getEmotion() instanceof Joie)
                        btn.setBackground(Color.YELLOW);
                    else if (robot.getEmotion() instanceof Nostalgie)
                        btn.setBackground(new Color(139, 69, 19));
                }
            }
        }
    }
}

