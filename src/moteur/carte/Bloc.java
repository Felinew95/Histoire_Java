package moteur.carte;

import gui.mobiles.Mobile;

/**
 * Classe qui représente un bloc sur une carte.
 *
 * <p>
 * Un bloc correspond à une unité de la grille de la carte et peut être occupé
 * par un {@link Mobile}. Chaque bloc possède un type (terrain, eau, route, etc.)
 * et un état d'accessibilité qui indique si un mobile peut s'y déplacer.
 * </p>
 *
 * @author Tauseef
 * @author Alexandre
 * @author Massinissa
 * @author Tianxiao.Liu@u-cergy.fr
 *
 * @version 2.0
 */
public class Bloc {

	/**
	 * Ligne du bloc
	 */
	private final int ligne;

	/**
	 * Colonne du bloc
	 */
	private final int colonne;

	/**
	 * Type du bloc
	 */
	private String typeBloc;

	/**
	 * Mobile qui occupe le bloc
	 */
	private Mobile occupant = null;

	/**
	 * Vérifie si le bloc est accessible ou non
	 */
	private boolean accessible = true;

	/**
	 * Constructeur de la classe Bloc.
	 *
	 * @param ligne     La position en ligne du bloc sur la grille.
	 * @param colonne   La position en colonne du bloc sur la grille.
	 * @param typeBloc  Le type de bloc (ex : "eau", "terre", "route").
	 */
	public Bloc(int ligne, int colonne, String typeBloc) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.typeBloc = typeBloc;
	}

	/**
	 * Retourne la position en ligne du bloc.
	 *
	 * @return La position du bloc sur l'axe des lignes.
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * Retourne la position en colonne du bloc.
	 *
	 * @return La position du bloc sur l'axe des colonnes.
	 */
	public int getColonne() {
		return colonne;
	}

	/**
	 * Retourne le type du bloc.
	 *
	 * @return Une chaîne de caractères représentant le type du bloc.
	 */
	public String getTypeBloc() {
		return typeBloc;
	}

	/**
	 * Définit le type du bloc.
	 *
	 * @param typeBloc La nouvelle valeur pour le type du bloc.
	 */
	public void setTypeBloc(String typeBloc) {
		this.typeBloc = typeBloc;
	}

	/**
	 * Retourne le mobile occupant ce bloc, s'il y en a un.
	 *
	 * @return Le {@link Mobile} occupant le bloc, ou {@code null} si le bloc est libre.
	 */
	public Mobile getOccupant() {
		return occupant;
	}

	/**
	 * Définit le mobile occupant ce bloc.
	 *
	 * <p>
	 * Met automatiquement à jour l'état d'accessibilité du bloc :
	 * si le bloc est occupé, il devient inaccessible.
	 * </p>
	 *
	 * @param occupant Le {@link Mobile} qui occupe ce bloc, ou {@code null} pour libérer le bloc.
	 */
	public void setOccupant(Mobile occupant) {
		this.occupant = occupant;
		setAccessible(occupant);
	}

	/**
	 * Retourne l'état d'accessibilité du bloc.
	 *
	 * <p>
	 * Un bloc est accessible si aucun mobile ne l'occupe.
	 * </p>
	 *
	 * @return {@code true} si le bloc est libre et accessible, {@code false} sinon.
	 */
	public boolean isAccessible() {
		return accessible;
	}

	/**
	 * Met à jour l'accessibilité du bloc en fonction de l'occupant.
	 *
	 * @param occupant Le {@link Mobile} occupant ce bloc, ou {@code null} pour libérer le bloc.
	 */
	private void setAccessible(Mobile occupant) {
		this.accessible = (occupant == null);
	}

	/**
	 * Retourne une représentation textuelle du bloc.
	 *
	 * <p>
	 * Affiche uniquement les coordonnées du bloc pour le debug ou le logging.
	 * </p>
	 *
	 * @return Une chaîne de caractères contenant la ligne et la colonne du bloc.
	 */
	@Override
	public String toString() {
		return "Bloc [ligne=" + ligne + ", colonne=" + colonne + "]";
	}

}