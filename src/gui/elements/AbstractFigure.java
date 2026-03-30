package gui.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

/**
 * <b>Classe abstraite représentant une figure graphique.</b>
 *
 * <p>
 * Cette classe sert de base pour toutes les formes graphiques utilisées dans
 * l'application. Chaque figure possède une forme {@link Shape}, un texte associé
 * et une couleur {@link Color}.
 * </p>
 *
 * <p>
 * Les sous-classes doivent implémenter la méthode {@link #construire(Graphics)}
 * pour définir la manière dont la figure est dessinée sur l'interface graphique.
 * </p>
 *
 * @author Tauseef
 * @version 1.0
 */
public abstract class AbstractFigure {

	// Attributs

	/**
	 * Forme géométrique de la figure
	 */
	private final Shape forme;

	/**
	 * Texte associé à la figure
	 */
	private String texte;

	/**
	 * Couleur de la figure
	 */
	private Color couleur;

	/**
	 * Constructeur de la classe abstraite.
	 *
	 * <p>
	 * Initialise la figure avec une forme, un texte et une couleur donnés.
	 * </p>
	 *
	 * @param forme Forme géométrique de la figure
	 * @param texte Texte affiché sur la figure
	 * @param couleur Couleur de la figure
	 */
	public AbstractFigure(Shape forme, String texte, Color couleur) {
		this.forme = forme;
		this.texte = texte;
		this.couleur = couleur;
	}

	/**
	 * Retourne la forme géométrique de la figure.
	 *
	 * @return {@link Shape} représentant la forme
	 */
	public Shape getForme() {
		return forme;
	}

	/**
	 * Retourne le texte associé à la figure.
	 *
	 * @return Texte de la figure
	 */
	public String getTexte() {
		return texte;
	}

	/**
	 * Retourne la couleur de la figure.
	 *
	 * @return Couleur de la figure
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * Définit le texte associé à la figure.
	 *
	 * @param texte Nouveau texte de la figure
	 */
	public void setTexte(String texte) {
		this.texte = texte;
	}

	/**
	 * Définit la couleur de la figure.
	 *
	 * @param couleur Nouvelle couleur de la figure
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * Méthode abstraite qui dessine la figure sur l'environnement graphique fourni.
	 *
	 * <p>
	 * Chaque sous-classe doit implémenter cette méthode pour définir le rendu
	 * spécifique de la figure.
	 * </p>
	 *
	 * @param g Environnement graphique {@link Graphics} utilisé pour dessiner
	 */
	public abstract void construire(Graphics g);

}