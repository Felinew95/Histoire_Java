package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import java.io.Serial;

import javax.swing.JPanel;

import gui.elements.ProgressBar;
import utilitaire.DessinUtilitaire;

/**
 * Panneau graphique représentant un écran de chargement avec une barre de progression.
 *
 * <p>
 * Cette classe étend JPanel et utilise un objet ProgressBar pour afficher visuellement
 * l'avancement en pourcentage. Le panneau affiche également un texte animé "Chargement"
 * qui change de points de suspension pour simuler un effet de chargement.
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 *
 * @version 1.0
 */
public class LoadingPanelGUI extends JPanel {

	/**
	 * Identifiant de sérialisation pour la compatibilité entre versions
	 */
	@Serial
    private static final long serialVersionUID = 3276608937765319605L;

	/**
	 * Pourcentage de progression de la barre
	 */
	private double pourcentage = 0;

	/**
	 * Taille par défaut de la barre en largeur
	 */
	private int taille_defaut_x = 300;

	/**
	 * Taille par défaut de la barre en hauteur
	 */
	private int taille_defaut_y = 100;

	/**
	 * Stocke le dernier instant où le pourcentage a été incrémenté
	 */
	private long dernierTemps = System.currentTimeMillis();

	/**
	 * Objet ProgressBar représentant la barre de chargement
	 */
	private final ProgressBar bar;

	/**
	 * Constructeur de LoadingPanelGUI.
	 * Configure les dimensions du panneau, la couleur de fond et initialise la barre de progression.
	 */
	public LoadingPanelGUI() {
		super();
		this.setPreferredSize(new Dimension(1000, 1000));
		this.setBackground(Color.BLACK);

		// Initialisation de la barre de progression avec ses coordonnées et dimensions par défaut
		this.bar = new ProgressBar(
				(int) (9f / 2),
				(this.getHeight() - taille_defaut_y) / 2,
				taille_defaut_x,
				taille_defaut_y,
				100,
				100,
				Double.toString(pourcentage),
				Color.RED
		);
	}

	/**
	 * Méthode de peinture du panneau.
	 *
	 * <p>
	 * Affiche la barre de progression et le texte animé de chargement.
	 * </p>
	 *
	 * @param g1 Objet Graphics fourni par Swing pour le dessin
	 */
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;

		// Activation de l'anticrénelage et des interpolations pour un rendu plus lisse
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// Mise à jour de la taille de la barre
		taille_defaut_x = this.getWidth();
		taille_defaut_y = 100;

		// Affichage de la barre de progression
		afficherBar(g);

		// Construction de la barre (bordure et remplissage)
		this.bar.construire(g);

		// Affichage du pourcentage en texte centré sur la barre
		DessinUtilitaire.definirCouleur(g, Color.WHITE);
		DessinUtilitaire.afficherTexte(
				g,
				new Font(Font.DIALOG, Font.BOLD, 20),
				bar.getTexte() + "%",
				new Point(
						(int) ((RoundRectangle2D) bar.getForme()).getCenterX(),
						(int) ((RoundRectangle2D) bar.getForme()).getCenterY() + 100
				)
		);

		// Gestion de l'animation du texte "Chargement..."
		long temps = System.currentTimeMillis() / 500;
		String texte = "Chargement";

		if (incrementer(10)) {
			long cycle = temps % 4;
			if (cycle == 3) {
				texte = "Chargement...";
			} else if (cycle == 2) {
				texte = "Chargement..";
			} else if (cycle == 1) {
				texte = "Chargement.";
			}
		}

		// Affichage du texte animé
		DessinUtilitaire.afficherTexte(
				g,
				new Font(Font.DIALOG, Font.ITALIC, 75),
				texte,
				new Point(
						(int) bar.getForme().getBounds2D().getCenterX(),
						(int) bar.getForme().getBounds2D().getCenterY() - 140
				)
		);

		// Mise à jour du texte de la barre avec le pourcentage actuel
		bar.setTexte(Double.toString(pourcentage));
	}

	/**
	 * Affiche graphiquement la barre de progression.
	 *
	 * @param g Objet Graphics2D pour le dessin
	 */
	private void afficherBar(Graphics2D g) {
		float x = (this.getWidth() / 7f - 15);
		float y = (this.getHeight() - taille_defaut_y) / 2f;
		float largeurBarre = this.getWidth() * 3f / 4f;

		// Mise à jour de la position et taille de la barre
		((RoundRectangle2D) bar.getForme()).setFrame(x, y, largeurBarre, taille_defaut_y);
		DessinUtilitaire.definirCouleur(g, bar.getCouleur());

		// Calcul du remplissage en fonction du pourcentage
		int remplissageBar = (int) (pourcentage * largeurBarre / 100.0);
		DessinUtilitaire.remplirRoundRectangle(
				g,
				new Point((int) x, (int) y),
				remplissageBar,
				taille_defaut_y,
				100,
				100
		);
	}

	/**
	 * Incrémente le pourcentage de progression à intervalles réguliers.
	 *
	 * @param temps Intervalle total de mise à jour (en secondes)
	 * @return true si le pourcentage peut encore être incrémenté, false sinon
	 */
	public boolean incrementer(int temps) {
		long tempsActuel = System.currentTimeMillis();

		if (tempsActuel - dernierTemps >= 1000) {
			if (pourcentage < 100) {
				if (pourcentage + ((double) 100 / temps) > 100) {
					pourcentage += 100;
				} else {
					pourcentage += (double) 100 / temps;
				}
				dernierTemps = tempsActuel;
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * Retourne le pourcentage actuel de la barre de progression.
	 *
	 * @return pourcentage de progression (0 à 100)
	 */
	public double getPourcentage() {
		return this.pourcentage;
	}

}