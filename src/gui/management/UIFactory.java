package gui.management;

import java.awt.Color;

import org.apache.log4j.Logger;

import log.LoggerUtility;

import gui.elements.Bouton;
import gui.mobiles.Action;
import gui.mobiles.Actions;
import gui.mobiles.Mobile;

/**
 * Classe utilitaire permettant de construire les éléments graphiques et objets
 * métiers de la simulation.
 *
 * <p>
 * UIFactory fournit des méthodes statiques pour créer :
 * </p>
 *
 * <ul>
 *     <li>Des {@link Mobile} avec leur position, type et période de vie</li>
 *     <li>Des {@link Actions} qui représentent la liste d'actions d'un mobile</li>
 *     <li>Des {@link Action} individuelles pour les mobiles</li>
 *     <li>Des {@link Bouton} pour l'interface graphique</li>
 * </ul>
 *
 * <p>
 * Chaque méthode logge la création de l'objet via {@link Logger} pour faciliter
 * le suivi et le débogage.
 * </p>
 *
 * @author Alexandre
 * @version 2.0
 */
public class UIFactory {

	/**
	 * Logger pour suivre la création des éléments UI et objets métiers
	 */
	private static Logger logger = LoggerUtility.getLogger(UIFactory.class, "html");

	/**
	 * Construit un mobile pour la simulation.
	 *
	 * @param id               Identifiant unique du mobile
	 * @param type             Type ou catégorie du mobile
	 * @param ligne            Position initiale sur la ligne de la grille
	 * @param colonne          Position initiale sur la colonne de la grille
	 * @param anneeApparition  Année à partir de laquelle le mobile est actif
	 * @param anneeDisparition Année jusqu'à laquelle le mobile est actif
	 *
	 * @return Un objet {@link Mobile} initialisé avec les paramètres fournis
	 */
	public static Mobile buildMobile(int id, String type, int ligne, int colonne, int anneeApparition, int anneeDisparition) {
		logger.info("Construction d'un mobile : id=" + id + ", type=" + type + ", ligne=" + ligne + ", colonne=" +
				colonne + ", anneeApparition=" + anneeApparition + ", anneeDisparition=" + anneeDisparition);
		return new Mobile(id, type, ligne, colonne, anneeApparition, anneeDisparition);
	}

	/**
	 * Construit une nouvelle liste d'actions pour un mobile.
	 *
	 * @return Un objet {@link Actions} vide
	 */
	public static Actions buildActions() {
		logger.info("Construction d'une nouvelle liste d'actions");
		return new Actions();
	}

	/**
	 * Construit une action individuelle pour un mobile.
	 *
	 * @param type            Type d'action (ex : "Déplacer", "Attaquer", "Récolter")
	 * @param distanceLigne   Distance à parcourir sur la ligne
	 * @param distanceColonne Distance à parcourir sur la colonne
	 *
	 * @return Un objet {@link Action} initialisé avec les paramètres fournis
	 */
	public static Action buildAction(String type, int distanceLigne, int distanceColonne) {
		logger.debug("Construction d'une action : type=" + type + ", distanceLigne=" + distanceLigne +
				", distanceColonne=" + distanceColonne);
		return new Action(type, distanceLigne, distanceColonne);
	}

	/**
	 * Construit un bouton pour l'interface graphique.
	 *
	 * @param x        Position x du coin supérieur gauche du bouton
	 * @param y        Position y du coin supérieur gauche du bouton
	 * @param longueur Longueur du bouton
	 * @param largeur  Largeur du bouton
	 * @param arcW     Largeur de l'arc pour les coins arrondis
	 * @param arcH     Hauteur de l'arc pour les coins arrondis
	 * @param texte    Texte affiché sur le bouton
	 * @param couleur  Couleur du bouton
	 *
	 * @return Un objet {@link Bouton} initialisé avec les paramètres fournis
	 */
	public static Bouton buildBouton(int x, int y, int longueur, int largeur, int arcW, int arcH, String texte, Color couleur) {
		logger.info("Construction d'un bouton : texte=\"" + texte + "\", position=(" + x + ", " + y +
				"), taille=(" + longueur + "x" + largeur + "), couleur=" + couleur);
		return new Bouton(x, y, longueur, largeur, arcW, arcH, texte, couleur);
	}

}