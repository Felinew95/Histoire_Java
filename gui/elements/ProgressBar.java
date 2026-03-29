package gui.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import gui.management.DessinUtilitaire;

/**
 * Classe qui représente une barre de progression 
 * 
 * @author Alexandre et Tauseef
 * @version 1.0
 */
public class ProgressBar extends AbstractFigure {
	
	/**
	 * Constructeur de la classe ProgressBar
	 * 
	 * @param x           : la position x du rectangle
     * @param y           : la position y du rectangle
     * @param longueur    : la longueur du rectangle
     * @param largeur     : la largueur du rectangle
	 * @param arcW        : Longueur de l'arc
	 * @param arcH        : Hauteur de l'arc
	 * @param texte       : Texte à afficher
	 * @param couleur     : Couleur du progressBar
	 */
	public ProgressBar(int x, int y, int longueur, int largeur, int arcW, int arcH, String texte, Color couleur) {
		super(new RoundRectangle2D.Float(x, y, longueur, largeur, arcW, arcH), texte, couleur);
	}

	/**
	 * Méthode qui construit la barre de progression
	 * 
	 * @param g : l'environnement graphique pour dessiner la barre de progression
	 */
	@Override
	public void construire(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(9f));
		
		DessinUtilitaire.definirCouleur(g2D, Color.white);
		DessinUtilitaire.dessinerForme(g2D, getForme());
	}
	
}
