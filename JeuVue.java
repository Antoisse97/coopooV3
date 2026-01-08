
/**
 * Décrivez votre classe JeuVue ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
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
    
        am.put("haut", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(0, -1); } });
        am.put("bas", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(0, 1); } });
        am.put("gauche", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(-1, 0); } });
        am.put("droite", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { tenterDeplacement(1, 0); } });
    }

    private void tenterDeplacement(int dx, int dy) {
        int ligneActuelle = -1, colonneActuelle = -1;
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

        if (dx >= 0 && dx < taille && dy >= 0 && dy < taille) {
            Cellule cible = monde.getCarte().getCellule(dx, dy);

            if (monde.getCarte().estAccessible(dx, dy)) {
                
                if (!cible.getMonstres().isEmpty()) {
                    Monstre m = cible.getMonstres().get(0);
                    int choix = JOptionPane.showConfirmDialog(this, "Un " + m.getNom() + " bloque le passage ! Combattre ?");
                    if (choix == JOptionPane.YES_OPTION) {
                        m.attaquer(robot);
                        if (!robot.estVivant()) {
                            JOptionPane.showMessageDialog(this, "Le robot a succombé...");
                            System.exit(0);
                        }
                    } else { return; }
                }

                robot.setPosition(cible);

                // Énigme Colère en (2,2)
                if (dx == 2 && dy == 2 && !(robot.getEmotion() instanceof Colere)) {
                    String rep = JOptionPane.showInputDialog(this, "Énigme : Quelle émotion bouillonne face à l'injustice ?");
                    if (rep != null && robot.verifierReponse(rep)) {
                        JOptionPane.showMessageDialog(this, "La Colère vous envahit !");
                    }
                }

                if (cible.getPiece() != null && !cible.getPiece().getSouvenirs().isEmpty()) {
                    examinerSouvenirs(cible.getPiece());
                }

                mettreAJour();
            }
        }
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

