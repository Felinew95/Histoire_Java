package utilitaire;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;

/**
 * Classe utilitaire regroupant différentes méthodes pour le dessin graphique.
 *
 * <p>
 * Fournit des méthodes statiques pour :
 * <ul>
 *   <li>Dessiner ou remplir des formes</li>
 *   <li>Dessiner et centrer du texte</li>
 *   <li>Définir la couleur et la police</li>
 * </ul>
 * </p>
 *
 * <p>
 * Cette classe ne peut pas être instanciée.
 * </p>
 *
 * @author Tauseef
 * @author Alexandre
 *
 * @version 1.0
 */
public class DessinUtilitaire {

	/**
	 * Constructeur privé pour empêcher l'instanciation de la classe.
	 */
	private DessinUtilitaire() {
		super();
	}

	/**
	 * Dessine le contour d'une forme.
	 *
	 * @param g     Composant graphique utilisé pour dessiner
	 * @param forme Forme à dessiner
	 */
	public static void dessinerForme(Graphics2D g, Shape forme) {
		g.draw(forme);
	}

	/**
	 * Remplit une forme avec la couleur courante.
	 *
	 * @param g     Composant graphique utilisé pour dessiner
	 * @param forme Forme à remplir
	 */
	public static void remplirForme(Graphics2D g, Shape forme) {
		g.fill(forme);
	}

	/**
	 * Remplit un rectangle arrondi à une position donnée.
	 *
	 * @param g        Composant graphique utilisé pour dessiner
	 * @param cord     Coordonnées du coin supérieur gauche
	 * @param longueur Largeur du rectangle
	 * @param largueur Hauteur du rectangle
	 * @param arcW     Largeur de l'arc pour les coins arrondis
	 * @param arcH     Hauteur de l'arc pour les coins arrondis
	 */
	public static void remplirRoundRectangle(Graphics2D g, Point cord, int longueur, int largueur, int arcW, int arcH) {
		g.fillRoundRect(cord.x, cord.y, longueur, largueur, arcW, arcH);
	}

	/**
	 * Définit la couleur du pinceau graphique.
	 *
	 * @param g       Composant graphique utilisé pour dessiner
	 * @param couleur Couleur à appliquer
	 */
	public static void definirCouleur(Graphics2D g, Color couleur) {
		g.setColor(couleur);
	}

	/**
	 * Définit la police d'écriture pour le composant graphique.
	 *
	 * @param g      Composant graphique utilisé pour dessiner
	 * @param police Police à appliquer
	 */
	public static void definirPolice(Graphics2D g, Font police) {
		g.setFont(police);
	}

	/**
	 * Affiche un texte centré autour d'un point donné.
	 * <p>
	 * Cette méthode calcule automatiquement la position centrée du texte par rapport
	 * au point fourni et applique la police spécifiée.
	 * </p>
	 *
	 * @param g             Composant graphique utilisé pour dessiner
	 * @param police        Police du texte
	 * @param texte         Texte à afficher
	 * @param positionTexte Point autour duquel le texte doit être centré
	 */
	public static void afficherTexte(Graphics2D g, Font police, String texte, Point positionTexte) {
		Point positionCalibree = calculerPositionTexte(g, positionTexte, texte, police);
		definirPolice(g, police);
		dessinerTexte(g, texte, positionCalibree);
	}

	/**
	 * Calcule la position centrée pour un texte donné autour d'un point.
	 *
	 * @param g             Composant graphique utilisé pour mesurer le texte
	 * @param positionTexte Point autour duquel centrer le texte
	 * @param texte         Texte à afficher
	 * @param police        Police du texte
	 * @return Nouveau point ajusté pour centrer le texte
	 */
	private static Point calculerPositionTexte(Graphics2D g, Point positionTexte, String texte, Font police) {
		FontMetrics fm = g.getFontMetrics(police);
		int positionTexte_x = positionTexte.x - fm.stringWidth(texte) / 2;
		int positionTexte_y = positionTexte.y + fm.getAscent() / 2;
		return new Point(positionTexte_x, positionTexte_y);
	}

	/**
	 * Dessine un texte à une position donnée sans centrage.
	 *
	 * @param g     Composant graphique utilisé pour dessiner
	 * @param texte Texte à afficher
	 * @param point Position du texte
	 */
	private static void dessinerTexte(Graphics2D g, String texte, Point point) {
		g.drawString(texte, point.x, point.y);
	}

}