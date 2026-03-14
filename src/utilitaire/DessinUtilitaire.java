package utilitaire;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;

public class DessinUtilitaire {
	
	public static void dessinerForme(Graphics2D g,Shape forme) {
		g.draw(forme);
	}
	
	public static void dessinerImage(Graphics2D g,BufferedImage image,Point positionImage) {
		g.drawImage(image,null,positionImage.x,positionImage.y);
	}
	
	public static void remplirForme(Graphics g, Shape forme) {
		((Graphics2D) g).fill(forme);
	}
	
	public static void remplirRoundRectangle(Graphics2D g,Point cord ,int longueur, int largueur,int arcW,int arcH) {
		g.fillRoundRect(cord.x, cord.y, longueur,largueur,arcW,arcH);
	}
	
	public static void definirCouleur(Graphics2D g,Color couleur) {
		g.setColor(couleur);
	}
	
	private static void dessinerTexte(Graphics2D g,String texte,Point point) {
		g.drawString(texte,point.x,point.y);
	}
	
	public static void definirPolice(Graphics2D g,Font police) {
		g.setFont(police);
	}
	
	public static void afficherTexte(Graphics2D g ,Font police,String texte,Point positionTexte) {
		Point positionCalibrer = calculerPositionTexte(g,positionTexte, texte,police);
		definirPolice(g, police);
		dessinerTexte(g,texte,positionCalibrer);	
	}
	
	private static Point calculerPositionTexte(Graphics2D g,Point positionTexte,String texte,Font police) {
		FontMetrics policeConfig = g.getFontMetrics(police);
		
		int positionTexte_x = (positionTexte.x - policeConfig.stringWidth(texte)/2) ;
		int positionTexte_y = (positionTexte.y + policeConfig.getAscent()/2);
		
		return new Point(positionTexte_x,positionTexte_y);
		
	}
	
	public static void lissageImage(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	}
	
	public static int agrandirTexte(Point cordTexte,int seuilTaille,float coeffAgrandissement,int tailleTexte) {
		if(tailleTexte<seuilTaille) {
			tailleTexte = (int) (tailleTexte*coeffAgrandissement) + 5 ;
		}
		
		return tailleTexte;
	}

}