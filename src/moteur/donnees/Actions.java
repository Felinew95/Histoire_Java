package moteur.donnees;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe qui représente une liste d'actions 
 * 
 * @author Alexandre
 * @version 1.0
 */
public class Actions implements Iterable<Action> {
	
	// Attributs 
	private final ArrayList<Action> actions = new ArrayList<>();

	/**
	 * Constructeur de la classe Actions
	 */
	public Actions() {
		super();
	}

	/**
	 * Getter de actions 
	 * @return La liste des actions 
	 */
	public ArrayList<Action> getActions() {
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
	 * Ajoute une action à la liste 
	 * @param action : Nouvelle action 
	 */
	public void ajouterAction(Action action) {
		if (action != null && !this.actions.contains(action)) {
			this.actions.add(action);
		}
	}

	@Override
	public Iterator<Action> iterator() {
		// TODO Auto-generated method stub
		return actions.iterator();
	}
	
}
