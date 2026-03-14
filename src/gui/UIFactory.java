package gui;

import java.awt.Color;

import gui.mobiles.Action;
import gui.mobiles.Actions;
import gui.mobiles.Mobile;

/**
 * Classe qui permet de construire les éléments graphiques 
 * 
 * @author Alexandre
 * @version 1.0
 */
public class UIFactory {
	
	/**
     * Méthode qui construit un mobile pour la simulation 
     * 
     * @param id      : Identifiant du mobile 
     * @param type    : Type du mobile 
     * @param ligne   : Ligne du mobile 
     * @param colonne : Colonne du mobile 
     * 
     * @return Un mobile pour la simulation 
     */
    public static Mobile buildMobile(int id, String type, int ligne, int colonne) {
    	return new Mobile(id, type, ligne, colonne);
    }
    
    /**
     * Méthode qui construit la liste des actions pour un mobile 
     * 
     * @return La liste des actions pour un mobile 
     */
    public static Actions buildActions() {
    	return new Actions();
    }
    
    /**
     * Méthode qui construit une action pour un mobile 
     * 
     * @param type            : Type d'action
     * @param distanceLigne   : Distance en ligne
     * @param distanceColonne : Distance en colonne
     * 
     * @return Une action pour un mobile 
     */
    public static Action buildAction(String type, int distanceLigne, int distanceColonne) {
    	return new Action(type, distanceLigne, distanceColonne);
    }
    
    /**
     * Méthode qui construit un bouton pour l'UI
     * 
     * @param x        : la position x du rectangle
     * @param y        : la position y du rectangle
     * @param longueur : la longueur du rectangle
     * @param largeur  : la largueur du rectangle
     * @param texte    : Texte du bouton
     * 
     * @return Un bouton
     */
    public static Bouton buildBouton(int x, int y, int longueur, int largeur, int arcW, int arcH, String texte, Color couleur) {
    	return new Bouton(x, y, longueur, largeur, arcW, arcH, texte, couleur);
    }
    
}
