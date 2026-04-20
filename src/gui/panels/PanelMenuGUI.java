package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import config.SpritesConfig;

import gui.elements.Bouton;
import gui.fenetres.MainGUI;
import gui.management.UIFactory;

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
 *
 * @version 1.0
 */
public class PanelMenuGUI extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * Identifiant de sérialisation pour la compatibilité entre versions
	 */
	private static final long serialVersionUID = -1248105144588644477L;

	/**
	 * Bouton pour lancer le jeu
	 */
	private Bouton jouer;

	/**
	 * Bouton pour afficher les crédits
	 */
	private Bouton credits;

	/**
	 * Bouton pour quitter le jeu
	 */
	private Bouton quitter;

	/**
	 * Instance contenant les images et sprites du jeu
	 */
	private final SpritesConfig images = SpritesConfig.getInstance();

	/**
	 * Taille actuelle du texte du titre
	 */
	private int tailleTexte = 0;

	/**
	 * Position verticale du texte animé
	 */
	private int y = 0;

	/**
	 * Largeur par défaut des boutons
	 */
	private final int taille_defaut_x = 300;

	/**
	 * Hauteur par défaut des boutons
	 */
	private final int taille_defaut_y = 100;

	/**
	 * Marge verticale entre les boutons
	 */
	private final int marge = 150;

	/**
	 * Référence vers la fenêtre principale
	 */
	private final MainGUI mainGUI;

	/**
	 * Constructeur du panneau de menu.
	 *
	 * <p>
	 * Initialise les boutons et configure les écouteurs de souris.
	 * </p>
	 *
	 * @param mainGUI La fenêtre principale du jeu
	 */
	public PanelMenuGUI(MainGUI mainGUI) {
		super();
		this.setPreferredSize(new Dimension(1000, 1000));

		this.mainGUI = mainGUI;
		this.initBoutons();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * Méthode de dessin du menu principal.
	 * <p>
	 * Elle active l'anticrénelage pour un rendu lisse et appelle
	 * la méthode {@link #peindreMainMenu(Graphics)} pour dessiner le fond, le texte et les boutons.
	 * </p>
	 *
	 * @param g1 Composant graphique utilisé pour dessiner
	 */
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);

		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		peindreMainMenu(g);
	}

	/**
	 * Initialise les boutons du menu avec leurs positions et labels.
	 */
	private void initBoutons() {
		jouer = UIFactory.buildBouton(
				(this.getWidth() - taille_defaut_x) / 2,
				(this.getHeight() - taille_defaut_y) / 2,
				taille_defaut_x,
				taille_defaut_y,
				100,
				100,
				"Lancer",
				Color.WHITE
		);
		credits = UIFactory.buildBouton(
				(this.getWidth() - taille_defaut_x) / 2,
				(this.getHeight() - taille_defaut_y) / 2 + marge,
				taille_defaut_x,
				taille_defaut_y,
				100,
				100,
				"Crédits",
				Color.WHITE
		);
		quitter = UIFactory.buildBouton(
				(this.getWidth() - taille_defaut_x) / 2,
				(this.getHeight() - taille_defaut_y) / 2 + marge * 2,
				taille_defaut_x,
				taille_defaut_y,
				100,
				100,
				"Quitter",
				Color.WHITE
		);
	}

	/**
	 * Méthode interne pour peindre le menu principal.
	 *
	 * <p>
	 * Gère l'animation du texte et la construction des boutons lorsqu'ils sont visibles.
	 * </p>
	 *
	 * @param g Environnement graphique pour le dessin
	 */
	private void peindreMainMenu(Graphics g) {
		g.drawImage(images.getImage("viking_bg.jpg"), 0, 0, this.getWidth(), this.getHeight(), null);

		// Animation verticale du texte
		if (y < (this.getHeight() / 2)) {
			y += 5;
		} else if (tailleTexte < 200) {
			tailleTexte += 5;
		} else {
			y = (this.getHeight() / 2);
		}

		g.drawImage(images.getImage("viking_bg.jpg"), 0, 0, this.getWidth(), this.getHeight(), null);
		if (y == (this.getHeight() / 2)) {
			construireBoutons(g);
		}
		dessinerTexte(g, this.getWidth(), y, tailleTexte);
	}

	/**
	 * Met à jour et dessine les boutons sur le panneau.
	 *
	 * @param g Environnement graphique pour le dessin
	 */
	public void construireBoutons(Graphics g) {
		((RoundRectangle2D) jouer.getForme()).setFrame(
				(double) (this.getWidth() - taille_defaut_x) / 2,
				(double) (this.getHeight() - taille_defaut_y) / 2,
				taille_defaut_x,
				taille_defaut_y
		);

		((RoundRectangle2D) credits.getForme()).setFrame(
				(double) (this.getWidth() - taille_defaut_x) / 2,
				(double) (this.getHeight() - taille_defaut_y) / 2 + marge,
				taille_defaut_x,
				taille_defaut_y
		);

		((RoundRectangle2D) quitter.getForme()).setFrame(
				(double) (this.getWidth() - taille_defaut_x) / 2,
				(double) (this.getHeight() - taille_defaut_y) / 2 + marge * 2,
				taille_defaut_x,
				taille_defaut_y
		);

		jouer.construire(g);
		credits.construire(g);
		quitter.construire(g);
	}

	/**
	 * Dessine un texte centré avec une taille donnée.
	 *
	 * @param g      Environnement graphique pour le dessin
	 * @param x      Position horizontale de référence
	 * @param y      Position verticale de référence
	 * @param taille Taille de la police
	 */
	private void dessinerTexte(Graphics g, int x, int y, int taille) {
		g.setFont(new Font(Font.DIALOG, Font.BOLD, taille));
		FontMetrics fm = g.getFontMetrics();

		int tx = (x - fm.stringWidth("Histoire")) / 2;
		int ty = (y + fm.getAscent()) / 2;
		g.setColor(Color.black);

		g.drawString("Histoire", tx, ty);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (this.jouer.contient(mx, my)) {
			this.mainGUI.lancerJeu();
		}

		if (this.quitter.contient(mx, my)) {
			this.mainGUI.quitterJeu();
		}

		if (this.credits.contient(mx, my)) {
			this.mainGUI.afficherCredits();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Non utilisé
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		jouer.setSurvoleZone(jouer.contient(mx, my));
		credits.setSurvoleZone(credits.contient(mx, my));
		quitter.setSurvoleZone(quitter.contient(mx, my));

		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Non utilisé
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Non utilisé
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Non utilisé
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Non utilisé
	}

}