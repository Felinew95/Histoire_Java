package gui.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import config.SimConfig;
import gui.management.DessinUtilitaire;

/**
 * Classe qui représente un bouton personnalisé
 * 
 * @author Tauseef
 * @version 2.0 
 */
public class Bouton extends AbstractFigure {
	
	// Attributs
    private boolean survoleZone = false;
    
    /**
     * Constructeur de la classe Bouton
     * 
     * @param x           : la position x du rectangle
     * @param y           : la position y du rectangle
     * @param longueur    : la longueur du rectangle
     * @param largeur     : la largueur du rectangle
     * @param texte 	  : Texte du bouton
     * @param couleur     : Couleur du bouton  
     */
    public Bouton(int x, int y, int longueur, int largeur, int arcW, int arcH, String texte, Color couleur) {
        super(new RoundRectangle2D.Float(x, y, longueur, largeur, arcW, arcH), texte, couleur);
    }
    
    
    /**
     * Méthode qui construit un bouton
     * 
     * @param g : l'environnement graphique pour dessiner les boutons
     */
    @Override
    public void construire(Graphics g) {
        setCouleurBouton(g);
        Graphics2D g2D = (Graphics2D) g;
        dessinerBouton(g2D);
    }

    /**
     * Méthode qui définie la couleur du bouton 
     * 
     * @param g : l'environnement graphique pour dessiner les boutons
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
	 * Méthode qui dessine le bouton
	 * 
	 * @param g : l'environnement graphique pour dessiner les boutons
	 */
	private void dessinerBouton(Graphics2D g) {
		setCouleurBouton(g);
        DessinUtilitaire.remplirForme(g, getForme());
        
        DessinUtilitaire.definirCouleur(g, Color.BLACK); // contour noir
        DessinUtilitaire.dessinerForme(g, getForme());
        Rectangle2D dimension = this.getForme().getBounds2D();

        g.setFont(SimConfig.POLICE_BOUTON);
        Point positionTexte = new Point((int)dimension.getCenterX(),(int)dimension.getCenterY()-SimConfig.DECALAGE_TEXTE_BOUTON);
        DessinUtilitaire.afficherTexte(g, SimConfig.POLICE_BOUTON, getTexte(),positionTexte);
	}

	/**
	 * Getter de survoleZone
	 * 
	 * @return si la souris survole la zone du bouton
	 */
	public boolean isSurvoleZone() {
		return survoleZone;
	}

	/**
	 * Setter de survoleZone
	 * 
	 * @param survoleZone : Nouvelle vérification si la souris survole la zone du bouton
	 */
	public void setSurvoleZone(boolean survoleZone) {
		this.survoleZone = survoleZone;
	}


	/**
     *  Vérife si la souris survole sur le rectangle représentant le bouton
     *  
     *  @param mx : la coordonnées x de la souris
     *  @param my : la coordonnées y de la souris
     *  @return retourne un boolean indiquant si la souris survole
     */
    public boolean contient(int mx, int my){
        return getForme().contains(mx,my);
    }
    
}
