package gui.mobiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe représentant une liste ordonnée d'actions pour un mobile.
 *
 * <p>
 * Elle gère la liste des actions, l'indice de l'action en cours et fournit
 * des méthodes pour ajouter, supprimer, itérer et exécuter les actions séquentiellement.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public class Actions implements Iterable<Action> {

	/**
	 * Liste des actions à faire
	 */
	private final List<Action> actions = new ArrayList<>();

	/**
	 * Action en cours
	 */
	private int indiceEnCours = 0;

	/**
	 * Constructeur par défaut, crée une liste vide d'actions.
	 */
	public Actions() {
		super();
	}

	/**
	 * Retourne l'indice de l'action en cours.
	 *
	 * @return L'indice actuel de l'action
	 */
	public int getIndiceEnCours() {
		return indiceEnCours;
	}

	/**
	 * Retourne la liste complète des actions.
	 *
	 * @return Une nouvelle liste contenant toutes les actions
	 */
	public List<Action> getActions() {
		return new ArrayList<>(actions);
	}

	/**
	 * Retourne le nombre d'actions dans la liste.
	 *
	 * @return Le nombre d'actions
	 */
	public int getNbActions() {
		return this.actions.size();
	}

	/**
	 * Retourne l'action en cours.
	 *
	 * @return L'action à l'indice courant, ou null s'il n'y a plus d'action
	 */
	public Action getActionEnCours() {
		if (aActionRestante()) {
			return this.actions.get(indiceEnCours);
		}
		return null;
	}

	/**
	 * Passe à la prochaine action en l'exécutant.
	 * <p>
	 * Incrémente l'indice de l'action en cours si des actions restent.
	 * </p>
	 */
	public void prochaineActionExecuter() {
		if (aActionRestante()) {
			indiceEnCours++;
		}
	}

	/**
	 * Réinitialise l'indice de l'action en cours à 0.
	 */
	public void resetIndice() {
		this.indiceEnCours = 0;
	}

	/**
	 * Vérifie si une action est présente dans la liste.
	 *
	 * @param action Action à vérifier
	 * @return true si l'action est dans la liste, false sinon
	 */
	public boolean contientAction(Action action) {
		return this.actions.contains(action);
	}

	/**
	 * Vérifie s'il reste des actions à exécuter.
	 *
	 * @return true si l'indice courant est inférieur au nombre d'actions
	 */
	public boolean aActionRestante() {
		return indiceEnCours < actions.size();
	}

	/**
	 * Ajoute une nouvelle action à la liste.
	 *
	 * @param action Action à ajouter
	 */
	public void ajouterAction(Action action) {
		this.actions.add(action);
	}

	/**
	 * Supprime une action de la liste si elle est présente.
	 *
	 * @param action Action à supprimer
	 */
	public void supprimerAction(Action action) {
		this.actions.remove(action);
	}

	/**
	 * Retourne un itérateur sur les actions.
	 *
	 * @return Un {@link Iterator} pour parcourir les actions
	 */
	@Override
	public Iterator<Action> iterator() {
		return actions.iterator();
	}

	/**
	 * Retourne une représentation textuelle de la liste d'actions.
	 *
	 * @return Une chaîne avec toutes les actions et l'indice en cours
	 */
	@Override
	public String toString() {
		return "Actions [actions=" + actions + ", indiceEnCours=" + indiceEnCours + "]";
	}

}