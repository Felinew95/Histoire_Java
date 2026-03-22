package moteur.traitement;

import java.util.HashMap;
import java.util.Iterator;

import gui.mobiles.Action;
import gui.mobiles.Actions;
import gui.mobiles.Mobile;

import moteur.carte.Bloc;
import moteur.carte.Carte;

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
public class MobileManager implements Iterable<Mobile> {
	
	// Attributs 
	private HashMap<Mobile, Actions> manager = new HashMap<>();

	/**
	 * Constructeur de la classe MobileManager
	 */
	public MobileManager() {
		super();
	}

	/**
	 * Getter de manager
	 * @return Le gestionnaire 
	 */
	public HashMap<Mobile, Actions> getManager() {
		return new HashMap<Mobile, Actions>(manager);
	}
	
	public void setManager(HashMap<Mobile, Actions> manager) {
		this.manager = manager;
	}

	/**
	 * Méthode qui calcul le nombre de mobiles
	 * @return Le nombre de mobiles
	 */
	public int getNbMobiles() {
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
	 * @param id : Identifiant du mobile
	 * @param action : Une action 
	 */
	public void ajouterActionAMobile(int id, Action action) {
		boolean trouve = false;
		
		for (Iterator<Mobile> it = this.manager.keySet().iterator(); it.hasNext() && !trouve;) {
			Mobile m = it.next();
			
			if (m.getId() == id) {
				trouve = true;
				this.manager.get(m).ajouterAction(action);
			}
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
	 * @param dLigne : Distance en ligne
	 * @param dColonne : Distance en colonne
	 * @return true si le mouvement est bien realisé, false sinon
	 */
	private boolean moveMobile(Carte carte, Mobile mobile, int dLigne, int dColonne) {
		if (!this.contientMobile(mobile)) {
			return false; 
		}
		
		int ligne = mobile.getLigne();
		int colonne = mobile.getColonne();
		
		int nvLigne = ligne + dLigne;
		int nvColonne = colonne + dColonne;
		
		return setMoveMobile(carte, mobile, ligne, colonne, nvLigne, nvColonne);
	}

	/**
	 * Méthode qui change la position du mobile
	 * 
	 * @param carte : Carte de la simulation 
	 * @param mobile : Mobile à déplacer
	 * @param ligne : Ancienne ligne du mobile 
	 * @param colonne : Ancienne colonne du mobile 
	 * @param nvLigne : Nouvelle ligne du mobile 
	 * @param nvColonne : Nouvelle colonne du mobile 
	 * 
	 * @return true si le mouvement est bien realisé, false sinon
	 */
	private boolean setMoveMobile(Carte carte, Mobile mobile, int ligne, int colonne, int nvLigne, int nvColonne) {
		Bloc cible = carte.getBloc(nvLigne, nvColonne);
		if (cible == null || !cible.isAccessible() || cible.getOccupant() != null) {
			return false;
		}
	
	    Bloc ancienBloc = carte.getBloc(ligne, colonne);
	    if (ancienBloc != null) {
	        ancienBloc.setOccupant(null);
	    }
	    
	    mobile.setLigne(nvLigne);
	    mobile.setColonne(nvColonne);
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
		if (actions == null || actions.getNbActions() == 0) {
			return;
		}
		
		if (!actions.aActionRestante()) {
			actions.resetIndice();
		}
		
		this.updateAction(carte, mobile, actions);
	}

	/**
	 * Mise à jour d'une action d'un mobile
	 * @param carte : Carte de la simulation 
	 * @param mobile : Mobile à mettre à jour
	 * @param actions : Actions du mobile
	 */
	private void updateAction(Carte carte, Mobile mobile, Actions actions) {
		Action actionAFaire = actions.getActionEnCours();
		boolean succes = false;
		
		switch (actionAFaire.getType()) {
			case "DEPLACEMENT": 
				succes = this.moveMobile(carte, mobile, actionAFaire.getDistanceLigne(), actionAFaire.getDistanceColonne());
				break;
			default:
				succes = true;
				break;
		}
		
		if (succes) {
			actions.prochaineActionExecuter();
		}
	}
	
	/**
	 * Mise à jour des emplacements des mobiles 
	 * 
	 * @param carte : Carte de la simulation
	 */
	public void updateAllMobiles(Carte carte) {
		for (Mobile mobile : getManager().keySet()) {
			this.updateMobile(carte, mobile);
		}
	}

	@Override
	public Iterator<Mobile> iterator() {
		// TODO Auto-generated method stub
		return manager.keySet().iterator();
	}
	
}
