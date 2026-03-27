package gui.elements;

import gui.management.DessinUtilitaire;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

/**
 * Classe qui représente un forme abstraite
 * 
 * @author Tauseef
 * @version 1.0 
 */
public abstract class AbstractFigure {
	 
	// Attributs 
	private Shape forme;
	private String texte;
	private Color couleur;
	
	/**
	 * Constructeur de la classe AbstractForme
	 * 
	 * @param forme : Une forme 
	 * @param texte : Texte de la forme 
	 */
	public AbstractFigure(Shape forme, String texte, Color couleur) {
		this.forme = forme;
		this.texte = texte;
		this.couleur = couleur;
	}

	/**
	 * @return the forme
	 */
	public Shape getForme() {
		return forme;
	}

	/**
	 * @return the texte
	 */
	public String getTexte() {
		return texte;
	}

	/**
	 * @return the couleur
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * @param forme the forme to set
	 */
	public void setForme(Shape forme) {
		this.forme = forme;
	}

	/**
	 * @param texte the texte to set
	 */
	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	/**
	 * @param couleur the couleur to set
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	/**
	 * Méthode qui construit la forme 
	 * 
	 * @param g : l'environnement graphique 
	 */
	public abstract void construire(Graphics g);

}
