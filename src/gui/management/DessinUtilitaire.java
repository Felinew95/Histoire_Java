package gui.management;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;

/**
 * Classe qui regroupe les différentes méthodes de dessin 
 * 
 * @author Tauseef et Alexandre
 * @version 1.0
 */
public class DessinUtilitaire {
	
	/**
	 * Constructeur de la classe DessinStrategy
	 */
	private DessinUtilitaire() {
		super();
	}

	/**
	 * Méthode qui permet de dessiner une forme 
	 * 
	 * @param g     : Composant graphique 
	 * @param forme : Forme à dessiner 
	 */
	public static void dessinerForme(Graphics2D g, Shape forme) {
		g.draw(forme);
	}
	
	/**
	 * Méthode qui permet de dessiner une image 
	 * 
	 * @param g             : Composant graphique
	 * @param image			: Une image 
	 * @param positionImage : Position de l'image 
	 */
	public static void dessinerImage(Graphics2D g, BufferedImage image, Point positionImage) {
		g.drawImage(image,null,positionImage.x,positionImage.y);
	}
	
	/**
	 * Méthode qui permet de remplir une forme 
	 * 
	 * @param g     : Composant graphique 
	 * @param forme : Une forme à remplir 
	 */
	public static void remplirForme(Graphics2D g, Shape forme) {
		g.fill(forme);
	}
	
	/**
	 * Méthode qui permet de remplir une rectangle 
	 * 
	 * @param g
	 * @param cord
	 * @param longueur
	 * @param largueur
	 * @param arcW
	 * @param arcH
	 */
	public static void remplirRoundRectangle(Graphics2D g,Point cord ,int longueur, int largueur,int arcW,int arcH) {
		g.fillRoundRect(cord.x, cord.y, longueur,largueur,arcW,arcH);
	}
	
	/**
	 * Méthode qui permet de définir une couleur 
	 * 
	 * @param g       : Composant graphique 
	 * @param couleur : Une couleur  
	 */
	public static void definirCouleur(Graphics2D g, Color couleur) {
		g.setColor(couleur);
	}
	
	/**
	 * Méthode qui permet d'afficher un texte 
	 * 
	 * @param g     : Composant graphique 
	 * @param texte : Un texte 
	 * @param point : Position du texte 
	 */
	private static void dessinerTexte(Graphics2D g,String texte,Point point) {
		g.drawString(texte,point.x,point.y);
	}
	
	/**
	 * Méthide qui permet de définir la police d'écriture 
	 * 
	 * @param g      : Composant graphique 
	 * @param police : Police d'écriture 
	 */
	public static void definirPolice(Graphics2D g,Font police) {
		g.setFont(police);
	}
	
	/**
	 * Méthode qui permet d'afficher un texte 
	 * 
	 * @param g
	 * @param police
	 * @param texte
	 * @param positionTexte
	 */
	public static void afficherTexte(Graphics2D g ,Font police,String texte,Point positionTexte) {
		Point positionCalibrer = calculerPositionTexte(g,positionTexte, texte,police);
		definirPolice(g, police);
		dessinerTexte(g,texte,positionCalibrer);	
	}
	
	/**
	 * Méthode qui permet de calculer la position d'un texte
	 * 
	 * @param g             : Composant graphique 
	 * @param positionTexte : Position du texte
	 * @param texte         : Un texte 
	 * @param police        : Police du texte 
	 * 
	 * @return La nouvelle position du texte 
	 */
	private static Point calculerPositionTexte(Graphics2D g, Point positionTexte, String texte, Font police) {
		FontMetrics policeConfig = g.getFontMetrics(police);
		
		int positionTexte_x = (positionTexte.x - policeConfig.stringWidth(texte)/2) ;
		int positionTexte_y = (positionTexte.y + policeConfig.getAscent()/2);
		
		return new Point(positionTexte_x,positionTexte_y);
	}
	
	/**
	 * Méthode qui permet de lisser une image 
	 * 
	 * @param g : Composant graphique 
	 */
	public static void lissageImage(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	}
	
	/**
	 * Méthode qui permet d'agrandir un texte 
	 * 
	 * @param cordTexte : Coordonnées du texte 
	 * @param seuilTaille : Seuil maximum à ne pas dépasser 
	 * @param coeffAgrandissement : Coefficient d'agrandissement 
	 * @param tailleTexte : Taille du texte 
	 * 
	 * @return La nouvelle taille du texte 
	 */
	public static int agrandirTexte(Point cordTexte, int seuilTaille, float coeffAgrandissement, int tailleTexte) {
		if (tailleTexte<seuilTaille) {
			tailleTexte = (int) (tailleTexte*coeffAgrandissement) + 5 ;
		}
		
		return tailleTexte;
	}
	
}