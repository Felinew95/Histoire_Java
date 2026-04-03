package gui.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import utilitaire.DessinUtilitaire;

/**
 * <b>Classe représentant une barre de progression graphique.</b>
 *
 * <p>
 * Cette classe étend {@link AbstractFigure} et représente une barre de progression
 * visuelle avec une forme arrondie ({@link RoundRectangle2D}) et un texte optionnel.
 * Elle est dessinée sur un environnement graphique fourni via la méthode {@link #construire(Graphics)}.
 * </p>
 *
 * <p>
 * La barre peut être utilisée pour indiquer un état d'avancement dans l'interface
 * utilisateur, comme un chargement ou une progression de tâche.
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 *
 * @version 2.0
 */
public class ProgressBar extends AbstractFigure {

	/**
	 * Constructeur de la barre de progression.
	 *
	 * <p>
	 * Initialise la barre avec sa position, ses dimensions, son rayon d'arrondi,
	 * un texte affiché et une couleur.
	 * </p>
	 *
	 * @param x        Position X du rectangle représentant la barre
	 * @param y        Position Y du rectangle représentant la barre
	 * @param longueur Longueur de la barre
	 * @param largeur  Largeur de la barre
	 * @param arcW     Largeur de l'arrondi des coins
	 * @param arcH     Hauteur de l'arrondi des coins
	 * @param texte    Texte affiché sur la barre (optionnel)
	 * @param couleur  Couleur de la barre
	 */
	public ProgressBar(int x, int y, int longueur, int largeur, int arcW, int arcH, String texte, Color couleur) {
		super(new RoundRectangle2D.Float(x, y, longueur, largeur, arcW, arcH), texte, couleur);
	}

	/**
	 * Dessine la barre de progression sur l'environnement graphique fourni.
	 * <p>
	 * La barre est dessinée avec un contour blanc et un trait d'épaisseur défini
	 * par {@link BasicStroke}. Cette méthode peut être adaptée pour ajouter le
	 * remplissage correspondant à l'avancement.
	 * </p>
	 *
	 * @param g Environnement graphique {@link Graphics} utilisé pour dessiner la barre
	 */
	@Override
	public void construire(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(9f));

		DessinUtilitaire.definirCouleur(g2D, Color.white);
		DessinUtilitaire.dessinerForme(g2D, getForme());
	}

}