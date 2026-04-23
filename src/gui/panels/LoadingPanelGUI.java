package gui.panels;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import gui.elements.ProgressBar;
import utilitaire.DessinUtilitaire;

/**
 * Panneau graphique représentant un écran de chargement avec une barre de progression.
 *
 * <p>
 * Ce panneau affiche :
 * <ul>
 *     <li>Une barre de progression dynamique</li>
 *     <li>Le pourcentage de chargement</li>
 *     <li>Un texte animé "Chargement..."</li>
 * </ul>
 * </p>
 *
 * <p>
 * Le pourcentage évolue progressivement dans le temps afin de simuler un chargement fluide.
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 * @version 3.0
 */
public class LoadingPanelGUI extends JPanel {

	/**
	 * Indentification de sérialisation  
	 */
	private static final long serialVersionUID = 3276608937765319605L;

	/**
	 * Largeur par défaut de la barre.
	 */
	private static final int BAR_WIDTH_DEFAULT = 300;

	/**
	 * Hauteur par défaut de la barre.
	 */
	private static final int BAR_HEIGHT = 100;

	/**
	 * Taille du panneau.
	 */
	private static final Dimension PANEL_SIZE = new Dimension(1000, 1000);

	/**
	 * Couleur de fond.
	 */
	private static final Color BACKGROUND_COLOR = Color.BLACK;

	/**
	 * Couleur du texte.
	 */
	private static final Color TEXT_COLOR = Color.WHITE;

	/**
	 * Couleur de la barre.
	 */
	private static final Color BAR_COLOR = Color.RED;

	/**
	 * Intervalle entre chaque mise à jour du pourcentage (ms).
	 */
	private static final int UPDATE_INTERVAL_MS = 1000;

	/**
	 * Intervalle pour l'animation du texte (ms).
	 */
	private static final int TEXT_ANIMATION_SPEED = 500;

	/**
	 * Police du pourcentage.
	 */
	private static final Font FONT_PERCENT = new Font(Font.DIALOG, Font.BOLD, 20);

	/**
	 * Police du texte "Chargement".
	 */
	private static final Font FONT_LOADING = new Font(Font.DIALOG, Font.ITALIC, 75);

	/**
	 * Rayon des coins arrondis.
	 */
	private static final int ARC_SIZE = 100;

	/**
	 * Pourcentage actuel de progression.
	 */
	private double pourcentage = 0;

	/**
	 * Largeur actuelle de la barre.
	 */
	private int largeurBarre = BAR_WIDTH_DEFAULT;

	/**
	 * Dernier instant de mise à jour.
	 */
	private long dernierTemps = System.currentTimeMillis();

	/**
	 * Barre de progression graphique.
	 */
	private final ProgressBar bar;

	/**
	 * Constructeur du panneau de chargement.
	 */
	public LoadingPanelGUI() {
		setPreferredSize(PANEL_SIZE);
		setBackground(BACKGROUND_COLOR);

		this.bar = new ProgressBar(
				0,
				0,
				BAR_WIDTH_DEFAULT,
				BAR_HEIGHT,
				ARC_SIZE,
				ARC_SIZE,
				Double.toString(pourcentage),
				BAR_COLOR
		);
	}

	/**
	 * Méthode de rendu du panneau.
	 *
	 * @param g1 contexte graphique
	 */
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;

		activerAntialiasing(g);

		largeurBarre = (int) (getWidth() * 0.75);

		afficherBar(g);
		bar.construire(g);

		afficherPourcentage(g);
		afficherTexteChargement(g);

		bar.setTexte(Double.toString(pourcentage));
	}

	/**
	 * Active les options de rendu pour améliorer la qualité graphique.
	 *
	 * @param g contexte graphique
	 */
	private void activerAntialiasing(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	}

	/**
	 * Affiche la barre de progression.
	 */
	private void afficherBar(Graphics2D g) {
		float x = getWidth() / 7f;
		float y = (getHeight() - BAR_HEIGHT) / 2f;

		((RoundRectangle2D) bar.getForme()).setFrame(x, y, largeurBarre, BAR_HEIGHT);

		DessinUtilitaire.definirCouleur(g, bar.getCouleur());

		int remplissage = (int) (pourcentage * largeurBarre / 100.0);

		DessinUtilitaire.remplirRoundRectangle(
				g,
				new Point((int) x, (int) y),
				remplissage,
				BAR_HEIGHT,
				ARC_SIZE,
				ARC_SIZE
		);
	}

	/**
	 * Affiche le pourcentage centré sur la barre.
	 */
	private void afficherPourcentage(Graphics2D g) {
		DessinUtilitaire.definirCouleur(g, TEXT_COLOR);

		DessinUtilitaire.afficherTexte(g, FONT_PERCENT, bar.getTexte() + "%",new Point((int) ((RoundRectangle2D) bar.getForme()).getCenterX(),(int) ((RoundRectangle2D) bar.getForme()).getCenterY() + 100)
		);
	}

	/**
	 * Affiche le texte animé "Chargement...".
	 */
	private void afficherTexteChargement(Graphics2D g) {
		long temps = System.currentTimeMillis() / TEXT_ANIMATION_SPEED;
		String texte = "Chargement";
		if (incrementer(10)) {
			long cycle = temps % 4;
			if (cycle == 3) texte += "...";
			else if (cycle == 2) texte += "..";
			else if (cycle == 1) texte += ".";
		}

		DessinUtilitaire.afficherTexte(g, FONT_LOADING, texte,new Point((int) bar.getForme().getBounds2D().getCenterX(),(int) bar.getForme().getBounds2D().getCenterY() - 140));
	}

	/**
	 * Incrémente le pourcentage de progression.
	 *
	 * @param duree durée totale du chargement (en secondes)
	 * @return true si la progression continue, false sinon
	 */
	public boolean incrementer(int duree) {
		long tempsActuel = System.currentTimeMillis();

		if (tempsActuel - dernierTemps >= UPDATE_INTERVAL_MS) {
			if (pourcentage < 100) {
				double increment = 100.0 / duree;
				pourcentage = Math.min(100, pourcentage + increment);
				dernierTemps = tempsActuel;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Retourne le pourcentage actuel.
	 *
	 * @return progression (0 à 100)
	 */
	public double getPourcentage() {
		return pourcentage;
	}

}