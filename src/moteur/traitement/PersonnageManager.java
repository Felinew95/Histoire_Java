package moteur.traitement;

import java.util.HashMap;

import moteur.donnees.Action;
import moteur.donnees.Actions;
import moteur.donnees.Personnage;

/**
 * Classe qui gère les personnages 
 * 
 * @author Alexandre 
 * @version 1.0
 * 
 * @see Personnage
 * @see Actions
 * @see Action
 */
public class PersonnageManager {
	
	// Attributs 
	private final HashMap<Personnage, Actions> manager = new HashMap<>();

	/**
	 * Constructeur de la classe PersonnageManager
	 */
	public PersonnageManager() {
		super();
	}

	/**
	 * Getter de manager
	 * @return Le gestionnaire 
	 */
	public HashMap<Personnage, Actions> getManager() {
		return manager;
	}
	
	/**
	 * Méthode qui calcul le nombre de personnages 
	 * @return Le nombre de personnages
	 */
	public int getNbPersonnages() {
		return this.manager.size();
	}
	
	/**
	 * Ajoute un personnage et ses actions 
	 * @param personnage : Un personnage 
	 * @param actions : Les actions associées 
	 */
	public void ajouterPersonnageEtActions(Personnage personnage, Actions actions) {
		if (personnage != null && actions != null && !this.manager.containsKey(personnage)) {
			this.manager.put(personnage, actions);
		}
	}
	
	/**
	 * Supprime un personnage et ses actions associées 
	 * @param personnage : Un personnage 
	 */
	public void supprimerPersonnageEtActions(Personnage personnage) {
		this.manager.remove(personnage);
	}
	
	/**
	 * Ajoute une action à un personnage 
	 * @param personnage : Un personnage 
	 * @param action : Une action 
	 */
	public void ajouterActionAPersonnage(Personnage personnage, Action action) {
		if (this.manager.containsKey(personnage)) {
			this.manager.get(personnage).ajouterAction(action);
		}
	}
	
	
}
