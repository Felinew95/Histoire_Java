package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import config.SpritesRepository;
import gui.elements.Bouton;
import gui.fenetres.MainGUI;
import gui.management.UIFactory;
import utilitaire.DessinUtilitaire;
import static utilitaire.MusicManager.*;


/**
 * Panneau représentant le menu principal du jeu.
 *
 * <p>
 * Ce panneau affiche le fond, le titre du jeu et trois boutons interactifs :
 * <ul>
 *   <li>Lancer le jeu</li>
 *   <li>Afficher les crédits</li>
 *   <li>Quitter le jeu</li>
 * </ul>
 * Il gère également les événements de souris pour le survol et le clic sur les boutons.
 * </p>
 *
 * @author Tauseef
 * @author Alexandre
 * @version 1.1
 */
public class PanelMenuGUI extends JPanel implements MouseListener, MouseMotionListener {

    /** 
     * Identifiant de sérialisation pour la compatibilité entre versions 
     */
    private static final long serialVersionUID = -1248105144588644477L;

    /** 
     * Taille préférée du panneau 
     */
    private static final Dimension PANEL_SIZE = new Dimension(1000, 1000);

    /** 
     * Largeur par défaut des boutons 
     */
    private static final int BOUTON_LARGEUR = 300;

    /** 
     * Hauteur par défaut des boutons 
     */
    private static final int BOUTON_HAUTEUR = 100;

    /** 
     * Marge verticale entre les boutons 
     */
    private static final int BOUTON_MARGE = 150;

    /** 
     * Rayon des coins arrondis des boutons 
     */
    private static final int BOUTON_ARC = 100;

    /** 
     * Taille maximale de la police du titre 
     */
    private static final int TITRE_TAILLE_MAX = 200;

    /** 
     * Pas d'incrément pour les animations 
     */
    private static final int ANIMATION_PAS = 5;

    /** 
     * Titre affiché dans le menu 
     */
    private static final String TITRE = "Histoire";

    /** 
     * Bouton pour lancer le jeu 
     */
    private final Bouton jouer;

    /** 
     * Bouton pour afficher les crédits 
     */
    private final Bouton credits;

    /** 
     * Bouton pour quitter le jeu 
     */
    private final Bouton quitter;

    /** 
     * Instance contenant les images et sprites du jeu 
     */
    private final SpritesRepository images = SpritesRepository.getInstance();

    /** 
     * Taille actuelle du texte du titre (animation) 
     */
    private int tailleTexte = 5;

    /** 
     * Référence vers la fenêtre principale 
     */
    private final MainGUI mainGUI;

    /**
     * Bouton actuellement survolé par la souris.
     * Utilisé pour gérer les effets de survol dans l'interface.
     */
    private Bouton boutonActuellementSurvole; 
    
    /**
     * Indique si l'audio de la simulation a déjà été lancé.
     * Permet d'éviter de relancer plusieurs fois le même son.
     */
    private boolean estLancerAudio = false;

    /**
     * Constructeur du panneau de menu.
     *
     * @param mainGUI La fenêtre principale du jeu
     */
    public PanelMenuGUI(MainGUI mainGUI) {
        super();
        this.setPreferredSize(PANEL_SIZE);
        this.mainGUI = mainGUI;

        this.jouer   = UIFactory.buildBouton(0, 0, BOUTON_LARGEUR, BOUTON_HAUTEUR, BOUTON_ARC, BOUTON_ARC, "Lancer",  Color.WHITE);
        this.credits = UIFactory.buildBouton(0, 0, BOUTON_LARGEUR, BOUTON_HAUTEUR, BOUTON_ARC, BOUTON_ARC, "Crédits", Color.WHITE);
        this.quitter = UIFactory.buildBouton(0, 0, BOUTON_LARGEUR, BOUTON_HAUTEUR, BOUTON_ARC, BOUTON_ARC, "Quitter", Color.WHITE);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Méthode de dessin du menu principal.
     *
     * @param g1 Composant graphique utilisé pour dessiner
     */
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(images.getImage("viking_bg.jpg"), 0, 0, getWidth(), getHeight(), null);

        mettreAJourAnimation();

        if (animationTerminee()) {
            repositionnerBoutons();
            jouer.construire(g);
            credits.construire(g);
            quitter.construire(g);
            if(!estLancerAudio) {
        		estLancerAudio = jouerAudio("src/audio/MainMenuTheme.wav", true, true); 
        		setVolumeMusiqueDeFond(30);
        	}
        }

        dessinerTitre(g);
    }

    /**
     * Fait progresser l'animation d'entrée du titre (descente puis grossissement).
     */
    private void mettreAJourAnimation() {
        if (tailleTexte < TITRE_TAILLE_MAX) {
            tailleTexte = Math.min(tailleTexte + ANIMATION_PAS * 2, TITRE_TAILLE_MAX);
        }
    }
    

    /**
     * Indique si l'animation d'entrée est terminée.
     *
     * @return {@code true} si le titre est en position finale et à taille maximale
     */
    private boolean animationTerminee() {
        return tailleTexte >= TITRE_TAILLE_MAX;
    }

    /**
     * Repositionne les trois boutons au centre du panneau.
     * Appelée à chaque paint pour s'adapter à la taille réelle du panneau.
     */
    private void repositionnerBoutons() {
        if (getWidth() == 0 || getHeight() == 0) return; // Taille pas encore connue
        
        int bx = (getWidth()  - BOUTON_LARGEUR) / 2;
        int by = (getHeight() - BOUTON_HAUTEUR) / 2;

        ((RoundRectangle2D) jouer.getForme())  .setFrame(bx, by,BOUTON_LARGEUR, BOUTON_HAUTEUR);
        ((RoundRectangle2D) credits.getForme()).setFrame(bx, by + BOUTON_MARGE,BOUTON_LARGEUR, BOUTON_HAUTEUR);
        ((RoundRectangle2D) quitter.getForme()).setFrame(bx, by + BOUTON_MARGE * 2, BOUTON_LARGEUR, BOUTON_HAUTEUR);
    }

    /**
     * Dessine le titre centré à la position d'animation courante,
     * en utilisant {@link DessinUtilitaire}.
     *
     * @param g Environnement graphique pour le dessin
     */
    private void dessinerTitre(Graphics2D g) {
        Font police = new Font(Font.DIALOG, Font.BOLD, tailleTexte);
        Point centre = new Point(getWidth() / 2, getHeight() / 4);
        DessinUtilitaire.definirCouleur(g, Color.BLACK);
        DessinUtilitaire.afficherTexte(g, police, TITRE, centre);
    }

    /**
     * Gère les clics sur les boutons du menu.
     *
     * @param e L'événement de souris
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!animationTerminee()) return; // Ignorer pendant l'animation
        
        int mx = e.getX();
        int my = e.getY();

        if (jouer.contient(mx, my)) {  
        	jouerAudio("src/audio/clickSound.wav", false, false);
        	arreterMusique();
        	mainGUI.lancerJeu();
        	
        }
        
        if (quitter.contient(mx, my)) mainGUI.quitterJeu();
        if (credits.contient(mx, my)) mainGUI.afficherCredits();
    }
    
    /**
     * Met à jour l'état de survol des boutons lorsque la souris se déplace.
     *
     * @param e L'événement de souris
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (!animationTerminee()) return;

        int mx = e.getX();
        int my = e.getY();

        Bouton boutonSousSouris = null;
        
        if (jouer.contient(mx, my)) boutonSousSouris = jouer;
        else if (credits.contient(mx, my)) boutonSousSouris = credits;
        else if (quitter.contient(mx, my)) boutonSousSouris = quitter;

        if (boutonSousSouris != boutonActuellementSurvole) {
            if (boutonSousSouris != null) {
                jouerAudio("src/audio/hover.wav", false, false);
            }
           
            
            boutonActuellementSurvole = boutonSousSouris;
            repaint();
        }
        
        jouer.setSurvoleZone(jouer.contient(mx, my));
        credits.setSurvoleZone(credits.contient(mx, my));
        quitter.setSurvoleZone(quitter.contient(mx, my));
        
      
    }

    @Override public void mouseDragged(MouseEvent e)  { /* Non utilisé */ }
    @Override public void mousePressed(MouseEvent e)  { /* Non utilisé */ }
    @Override public void mouseReleased(MouseEvent e) { /* Non utilisé */ }
    @Override public void mouseEntered(MouseEvent e)  { /* Non utilisé */ }
    @Override public void mouseExited(MouseEvent e)   { /* Non utilisé */ }
}