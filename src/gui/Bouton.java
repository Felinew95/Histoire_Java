package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Classe qui représente un bouton personnalisée
 * 
 * @author Tauseef
 * @version 1.0 
 */
public class Bouton {
	//Attributs
    private Rectangle boite;
    private String texte;
    private boolean survole_zone = false;
    
    /**
     * Constructeur de la classe Bouton
     * 
     * @param x    : la position x du rectangle
     * @param y    : la position y du rectangle
     * @param longueur    : la longueur du rectangle
     * @param largeur    : la largueur du rectangle
     */

    public Bouton(int x, int y, int longueur, int largeur, String texte) {
        boite = new Rectangle(x, y, longueur, largeur);
        this.texte = texte;
    }
    
    
    /**
     *  @param g : l'environnement graphique pour dessiner les bouttons
     */

    public void faireBoutton(Graphics g) {
        if(survole_zone) {
        	g.setColor(new Color(200,200,255));
        } else {
        	g.setColor(new Color(250,250,250));
        }

        g.fillRoundRect(boite.x, boite.y, boite.width, boite.height, 100, 100);
        g.setColor(Color.BLACK);
        g.drawRoundRect(boite.x, boite.y, boite.width, boite.height, 100,100);
       
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 70));
        FontMetrics fm = g.getFontMetrics();
        int tx = boite.x + (boite.width - fm.stringWidth(texte))/2;
        int ty = boite.y + (boite.height + fm.getAscent() - 20)/2;
        
        
        
        g.drawString(texte, tx, ty);
    }
    
    public Rectangle getBoite() {
		return boite;
	}

	public void setBoite(Rectangle boite) {
		this.boite = boite;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public boolean isSurvole_zone() {
		return survole_zone;
	}

	public void setSurvole_zone(boolean survole_zone) {
		this.survole_zone = survole_zone;
	}


	/**
     *  Vérife si la souris survole sur le rectangle représentant le bouton
     *  @param mx : la cordonnées x de la souris
     *  @param my : la cordonnées y de la souris
     *  @return retourne un boolean indiquant si la souris survole
     */
    public boolean contient(int mx, int my){
        return boite.contains(mx,my);
    }
    
}
