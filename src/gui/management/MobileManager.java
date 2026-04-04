package gui.management;

import java.util.HashMap;
import java.util.Iterator;

import gui.mobiles.Action;
import gui.mobiles.Actions;
import gui.mobiles.Mobile;

import moteur.carte.Bloc;
import moteur.carte.Carte;

/**
 * Gestionnaire des {@link Mobile} de la simulation.
 *
 * <p>
 * Cette classe centralise les mobiles et leurs {@link Actions},
 * permet de les ajouter, mettre à jour, vérifier leur existence,
 * et gérer leur déplacement sur une {@link Carte}.
 * </p>
 *
 * @author Alexandre
 * @version 1.1
 *
 * @see Mobile
 * @see Actions
 * @see Action
 */
public class MobileManager implements Iterable<Mobile> {

	/**
	 * Association entre un mobile et ses actions
	 */
	private HashMap<Mobile, Actions> manager = new HashMap<>();

	/**
	 * Constructeur par défaut.
	 * Initialise un gestionnaire vide.
	 */
	public MobileManager() {
		super();
	}

	/**
	 * Retourne une copie du gestionnaire de mobiles et leurs actions.
	 *
	 * @return une {@link HashMap} contenant les mobiles et leurs actions
	 */
	public HashMap<Mobile, Actions> getManager() {
		return new HashMap<>(manager);
	}

	/**
	 * Remplace le gestionnaire de mobiles et actions par celui fourni.
	 *
	 * @param manager nouvelle {@link HashMap} de mobiles et actions
	 */
	public void setManager(HashMap<Mobile, Actions> manager) {
		this.manager = manager;
	}

	/**
	 * Retourne le nombre total de mobiles gérés.
	 *
	 * @return le nombre de mobiles
	 */
	public int getNbMobiles() {
		return this.manager.size();
	}

	/**
	 * Retourne les actions associées à un mobile donné.
	 *
	 * @param mobile mobile dont on veut récupérer les actions
	 * @return les {@link Actions} du mobile, ou {@code null} si le mobile n'existe pas
	 */
	public Actions getActions(Mobile mobile) {
		return this.manager.get(mobile);
	}

	/**
	 * Vérifie si un mobile est présent dans le gestionnaire.
	 *
	 * @param mobile mobile à vérifier
	 * @return {@code true} si le mobile est présent, {@code false} sinon
	 */
	public boolean contientMobile(Mobile mobile) {
		return this.manager.containsKey(mobile);
	}

	/**
	 * Ajoute un mobile avec ses actions au gestionnaire.
	 * <p>
	 * Ne fait rien si le mobile ou ses actions sont {@code null},
	 * ou si le mobile est déjà présent.
	 * </p>
	 *
	 * @param mobile mobile à ajouter
	 * @param actions actions associées au mobile
	 */
	public void ajouterMobileEtActions(Mobile mobile, Actions actions) {
		if (mobile != null && actions != null && !this.contientMobile(mobile)) {
			this.manager.put(mobile, actions);
		}
	}

	/**
	 * Ajoute une action à un mobile identifié par son ID.
	 *
	 * @param id identifiant du mobile
	 * @param action action à ajouter
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
	 * Déplace un mobile sur la carte selon un delta de ligne et de colonne.
	 *
	 * @param carte carte de la simulation
	 * @param mobile mobile à déplacer
	 * @param dLigne déplacement en ligne
	 * @param dColonne déplacement en colonne
	 * @return {@code true} si le déplacement est possible et effectué, {@code false} sinon
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
	 * Met à jour la position d'un mobile sur la carte.
	 * <p>
	 * Vérifie si le bloc cible est accessible et non occupé avant de déplacer le mobile.
	 * </p>
	 *
	 * @param carte carte de la simulation
	 * @param mobile mobile à déplacer
	 * @param ligne ancienne ligne du mobile
	 * @param colonne ancienne colonne du mobile
	 * @param nvLigne nouvelle ligne du mobile
	 * @param nvColonne nouvelle colonne du mobile
	 * @return {@code true} si le déplacement est réussi, {@code false} sinon
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
	 * Met à jour un mobile selon ses actions restantes.
	 *
	 * @param carte carte de la simulation
	 * @param mobile mobile à mettre à jour
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
	 * Exécute la prochaine action d'un mobile.
	 *
	 * @param carte carte de la simulation
	 * @param mobile mobile à mettre à jour
	 * @param actions actions du mobile
	 */
	private void updateAction(Carte carte, Mobile mobile, Actions actions) {
		Action actionAFaire = actions.getActionEnCours();
		boolean succes = false;

		if (actionAFaire.getType().equals("DEPLACEMENT")) {
			succes = this.moveMobile(carte, mobile, actionAFaire.getDistanceLigne(), actionAFaire.getDistanceColonne());
		} else {
			succes = true;
		}

		if (succes) {
			actions.prochaineActionExecuter();
		}
	}

	/**
	 * Met à jour tous les mobiles actifs sur la carte.
	 *
	 * @param carte carte de la simulation
	 * @param anneeSim année de la simulation pour vérifier l'activité des mobiles
	 */
	public void updateAllMobiles(Carte carte, int anneeSim) {
		for (Mobile mobile : getManager().keySet()) {
			if (mobile.estActif(anneeSim)) {
				this.updateMobile(carte, mobile);
			}
		}
	}

	/**
	 * Retourne un itérateur sur les mobiles du gestionnaire.
	 *
	 * @return itérateur sur les {@link Mobile}
	 */
	@Override
	public Iterator<Mobile> iterator() {
		return manager.keySet().iterator();
	}

}