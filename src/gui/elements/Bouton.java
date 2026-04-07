package gui.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import config.SimConfig;

import utilitaire.DessinUtilitaire;

/**
 * <b>Classe représentant un bouton graphique personnalisé.</b>
 *
 * <p>
 * Cette classe étend {@link AbstractFigure} pour créer un bouton avec :
 * </p>
 *
 * <ul>
 *   <li>Une forme arrondie ({@link RoundRectangle2D})</li>
 *   <li>Un texte affiché au centre</li>
 *   <li>Une couleur qui change lorsqu'on survole le bouton avec la souris</li>
 * </ul>
 *
 * <p>
 * Le rendu graphique est géré par la méthode {@link #construire(Graphics)}, qui utilise
 * {@link DessinUtilitaire} pour le dessin, le remplissage et l'affichage du texte.
 * </p>
 *
 * @author Tauseef
 * @version 2.0
 */
public class Bouton extends AbstractFigure {

	/**
	 * Indique si la souris survole la zone du bouton
	 */
	private boolean survoleZone = false;

	/**
	 * Constructeur du bouton personnalisé.
	 *
	 * <p>
	 * Initialise un bouton avec une position, des dimensions, un rayon d'arrondi,
	 * un texte et une couleur.
	 * </p>
	 *
	 * @param x        Position X du rectangle du bouton
	 * @param y        Position Y du rectangle du bouton
	 * @param longueur Longueur du bouton
	 * @param largeur  Largeur du bouton
	 * @param arcW     Largeur de l'arrondi des coins
	 * @param arcH     Hauteur de l'arrondi des coins
	 * @param texte    Texte affiché sur le bouton
	 * @param couleur  Couleur initiale du bouton
	 */
	public Bouton(int x, int y, int longueur, int largeur, int arcW, int arcH, String texte, Color couleur) {
		super(new RoundRectangle2D.Float(x, y, longueur, largeur, arcW, arcH), texte, couleur);
	}

	/**
	 * Dessine le bouton sur l'environnement graphique fourni.
	 *
	 * <p>
	 * Appelle les méthodes internes pour définir la couleur et dessiner la forme
	 * ainsi que le texte centré sur le bouton.
	 * </p>
	 *
	 * @param g Environnement graphique {@link Graphics} pour dessiner le bouton
	 */
	@Override
	public void construire(Graphics g) {
		setCouleurBouton(g);
		Graphics2D g2D = (Graphics2D) g;
		dessinerBouton(g2D);
	}

	/**
	 * Définit la couleur du bouton selon qu'il est survolé ou non.
	 *
	 * <p>
	 * Si la souris survole la zone du bouton, on applique {@link SimConfig#COULEUR_SURVOL_BOUTON}.
	 * Sinon, le bouton est blanc.
	 * </p>
	 *
	 * @param g Environnement graphique {@link Graphics} utilisé pour le dessin
	 */
	private void setCouleurBouton(Graphics g) {
		if (isSurvoleZone()) {
			this.setCouleur(SimConfig.COULEUR_SURVOL_BOUTON);
		} else {
			this.setCouleur(Color.WHITE);
		}
		DessinUtilitaire.definirCouleur((Graphics2D) g, getCouleur());
	}

	/**
	 * Dessine le bouton avec sa forme, son contour et son texte centré.
	 *
	 * @param g Environnement graphique {@link Graphics2D} utilisé pour dessiner
	 */
	private void dessinerBouton(Graphics2D g) {
		setCouleurBouton(g);
		DessinUtilitaire.remplirForme(g, getForme());

		DessinUtilitaire.definirCouleur(g, Color.BLACK); // contour noir
		DessinUtilitaire.dessinerForme(g, getForme());

		Rectangle2D dimension = this.getForme().getBounds2D();
		g.setFont(SimConfig.POLICE_BOUTON);

		Point positionTexte = new Point(
				(int) dimension.getCenterX(),
				(int) dimension.getCenterY() - SimConfig.DECALAGE_TEXTE_BOUTON
		);

		DessinUtilitaire.afficherTexte(g, SimConfig.POLICE_BOUTON, getTexte(), positionTexte);
	}

	/**
	 * Vérifie si la souris survole le bouton.
	 *
	 * @return {@code true} si la souris est au-dessus du bouton, {@code false} sinon
	 */
	public boolean isSurvoleZone() {
		return survoleZone;
	}

	/**
	 * Définit si la souris survole la zone du bouton.
	 *
	 * @param survoleZone Nouvelle valeur de survol
	 */
	public void setSurvoleZone(boolean survoleZone) {
		this.survoleZone = survoleZone;
	}

	/**
	 * Vérifie si un point donné (coordonnées de la souris) se trouve dans le bouton.
	 *
	 * @param mx Coordonnée X de la souris
	 * @param my Coordonnée Y de la souris
	 * @return {@code true} si le point est dans le bouton, {@code false} sinon
	 */
	public boolean contient(int mx, int my) {
		return getForme().contains(mx, my);
	}

}