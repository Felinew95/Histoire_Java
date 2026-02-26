package moteur.traitement;

import java.util.HashMap;

import config.SimConfig;
import moteur.carte.Bloc;
import moteur.carte.Carte;
import moteur.donnees.Action;
import moteur.donnees.Actions;
import moteur.donnees.Mobile;

/**
 * Classe qui gère les mobiles 
 * 
 * @author Alexandre 
 * @version 1.0
 * 
 * @see Mobile
 * @see Actions
 * @see Action
 */
public class MobileManager {
	
	// Attributs 
	private final HashMap<Mobile, Actions> manager = new HashMap<>();

	/**
	 * Constructeur de la classe PersonnageManager
	 */
	public MobileManager() {
		super();
	}

	/**
	 * Getter de manager
	 * @return Le gestionnaire 
	 */
	public HashMap<Mobile, Actions> getManager() {
		return manager;
	}
	
	/**
	 * Méthode qui calcul le nombre de mobiles
	 * @return Le nombre de mobiles
	 */
	public int getNbMobile() {
		return this.manager.size();
	}
	
	/**
	 * Retourne la liste des actions d'un mobile 
	 * @param mobile : Un mobile 
	 * @return la liste des actions d'un mobile 
	 */
	public Actions getActions(Mobile mobile) {
		return this.manager.get(mobile);
	}
	
	/**
	 * Vérifie si il existe un mobile 
	 * @param mobile : Mobile à vérifier 
	 * @return true si il existe un mobile, false sinon
	 */
	public boolean contientMobile(Mobile mobile) {
		return this.manager.containsKey(mobile);
	}
	
	/**
	 * Ajoute un mobile et ses actions 
	 * @param personnage : Un mobile
	 * @param actions : Les actions associées 
	 */
	public void ajouterMobileEtActions(Mobile mobile, Actions actions) {
		if (mobile != null && actions != null && !this.contientMobile(mobile)) {
			this.manager.put(mobile, actions);
		}
	}
	
	/**
	 * Supprime un mobile 
	 * @param mobile : Mobile à supprimer 
	 */
	public void supprimerMobile(Mobile mobile) {
		this.manager.remove(mobile);
	}
	
	/**
	 * Ajoute une action à un mobile
	 * @param mobile : Un mobile
	 * @param action : Une action 
	 */
	public void ajouterActionAMobile(Mobile mobile, Action action) {
		if (this.contientMobile(mobile)) {
			this.manager.get(mobile).ajouterAction(action);
		}
	}
	
	/**
	 * Supprime une action à un mobile 
	 * @param mobile : Un mobile 
	 * @param action : Action à supprimer 
	 */
	public void supprimerActionAMobile(Mobile mobile, Action action) {
		if (this.contientMobile(mobile)) {
			this.manager.get(mobile).supprimerAction(action);
		}
	}
	
	/**
	 * Méthode qui déplace un mobile 
	 * @param carte : Carte de la simulation
	 * @param mobile : Mobile à déplacer 
	 * @param dx : Distance en X
	 * @param dy : Distance en Y
	 * @return true si le mouvement est bien realisé, false sinon
	 */
	private boolean moveMobile(Carte carte, Mobile mobile, int dx, int dy) {
		if (!this.contientMobile(mobile)) {
			return false; 
		}
		
		int nouveauX = mobile.getX() + dx;
		int nouveauY = mobile.getY() + dy;
		
		int nouvelleLigne = nouveauY/SimConfig.TAILLE_BLOC;
		int nouvelleColonne = nouveauX/SimConfig.TAILLE_BLOC;
		
		Bloc cible = carte.getBloc(nouvelleLigne, nouvelleColonne);
		if (cible == null || !cible.isAccessible() || cible.getOccupant() != null) {
			return false;
		}
		
		int ancienneLigne = mobile.getY() / SimConfig.TAILLE_BLOC;
	    int ancienneColonne = mobile.getX() / SimConfig.TAILLE_BLOC;
	    Bloc ancienBloc = carte.getBloc(ancienneLigne, ancienneColonne);
	    if (ancienBloc != null) {
	        ancienBloc.setOccupant(null);
	    }
	    
	    mobile.setX(nouveauX);
	    mobile.setY(nouveauY);
	    
	    cible.setOccupant(mobile);
		
		return true; 
	}
	
	/**
	 * Mise à jour d'un mobile 
	 * @param carte : Carte de la simulation 
	 * @param mobile : Mobile à mettre à jour
	 */
	public void updateMobile(Carte carte, Mobile mobile) {
		Actions actions = this.manager.get(mobile);
		if (!actions.aActionRestante()) {
			actions.resetIndice();
		}
		
		Action actionAFaire = actions.getActionEnCours();
		switch (actionAFaire.getType()) {
			case "DEPLACEMENT": 
				this.moveMobile(carte, mobile, actionAFaire.getDistanceX(), actionAFaire.getDistanceY());
				break;
			default:
				break;
		}
		
		actions.prochaineActionExecuter();
	}
	
	/**
	 * Mise à jour des emplacements des mobiles 
	 */
	public void updateAllMobiles(Carte carte) {
		for (Mobile mobile : this.manager.keySet()) {
			this.updateMobile(carte, mobile);
		}
	}
	
}
