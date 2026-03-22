package gui.mobiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe qui représente une liste d'actions 
 * 
 * @author Alexandre
 * @version 1.0
 */
public class Actions implements Iterable<Action> {
	
	// Attributs 
	private final List<Action> actions = new ArrayList<>();
	private int indiceEnCours = 0;

	/**
	 * Constructeur de la classe Actions
	 */
	public Actions() {
		super();
	}
	
	/**
	 * Getter de indiceEnCours
	 * @return L'indice de l'action en cours
	 */
	public int getIndiceEnCours() {
		return indiceEnCours;
	}
	
	/**
	 * Getter de actions 
	 * @return La liste des actions 
	 */
	public List<Action> getActions() {
		return new ArrayList<>(actions);
	}
	
	/**
	 * Méthode qui retourne le nombre d'actions 
	 * @return Le nombre d'actions 
	 */
	public int getNbActions() {
		return this.actions.size();
	}
	
	/**
	 * Retourne l'action en cours 
	 * @return L'action en cours 
	 */
	public Action getActionEnCours() {
		if (aActionRestante()) {
			return this.actions.get(indiceEnCours);
		}
		
		return null;
	}
	
	/**
	 * Marque l'action en cours comme exécutée
	 */
	public void prochaineActionExecuter() {
		if (aActionRestante()) {
	        indiceEnCours++;
	    }
	}
	
	/**
	 * Renitialise l'indice à 0
	 */
	public void resetIndice() {
		this.indiceEnCours = 0;
	}
	
	/**
	 * Vérifie si la liste contient une action 
	 * @param action : Action à vérifier 
	 * @return true si la liste contient une action, false sinon 
	 */
	public boolean contientAction(Action action) {
		return this.actions.contains(action);
	}
	
	/**
	 * Vérifie si il reste des actions 
	 * @return true si il reste des actions, false sinon 
	 */
	public boolean aActionRestante() {
	    return indiceEnCours < actions.size();
	}
	
	/**
	 * Ajoute une action à la liste 
	 * @param action : Nouvelle action 
	 */
	public void ajouterAction(Action action) {
		this.actions.add(action);
	}
	
	/**
	 * Supprime une action 
	 * @param action : Action à supprimer 
	 */
	public void supprimerAction(Action action) {
		if (this.contientAction(action)) {
			this.actions.remove(action);
		}
	}

	@Override
	public Iterator<Action> iterator() {
		// TODO Auto-generated method stub
		return actions.iterator();
	}

	/**
	 * Affiche les informations sur les actions 
	 * 
	 * @return l'affichage des informations
	 */
	@Override
	public String toString() {
		return "Actions [actions=" + actions + ", indiceEnCours=" + indiceEnCours + "]";
	}
	
}
