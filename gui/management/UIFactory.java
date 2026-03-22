package gui.management;

import java.awt.Color;

import org.apache.log4j.Logger;

import log.LoggerUtility;

import gui.elements.Bouton;
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

	private static Logger logger = LoggerUtility.getLogger(UIFactory.class, "html");

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
    	logger.info("Construction d'un mobile : id=" + id + ", type=" + type + ", ligne=" + ligne + ", colonne=" + colonne);
    	return new Mobile(id, type, ligne, colonne);
    }
    
    /**
     * Méthode qui construit la liste des actions pour un mobile 
     * 
     * @return La liste des actions pour un mobile 
     */
    public static Actions buildActions() {
    	logger.info("Construction d'une nouvelle liste d'actions");
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
    	logger.debug("Construction d'une action : type=" + type + ", distanceLigne=" + distanceLigne + ", distanceColonne=" + distanceColonne);
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
    	logger.info("Construction d'un bouton : texte=\"" + texte + "\", position=(" + x + ", " + y + "), taille=(" + longueur + "x" + largeur + "), couleur=" + couleur);
    	return new Bouton(x, y, longueur, largeur, arcW, arcH, texte, couleur);
    }
    
}