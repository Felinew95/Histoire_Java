package gui.mobiles;

import java.util.Objects;

/**
 * Classe représentant un mobile dans la simulation.
 *
 * <p>
 * Un mobile est une entité pouvant se déplacer ou interagir dans une grille.
 * La classe fournit des méthodes pour consulter et modifier la position du mobile,
 * vérifier son statut actif pour une année donnée, ainsi que pour comparer des mobiles.
 * </p>
 *
 * @author Alexandre
 * @version 2.0
 */
public class Mobile {

	/**
	 * Identifiant unique du mobile.
	 */
	private final int id;

	/**
	 * Type du mobile.
	 */
	private final String type;

	/**
	 * Position verticale du mobile sur la grille.
	 */
	private int ligne;

	/**
	 * Position horizontale du mobile sur la grille.
	 */
	private int colonne;

	/**
	 * Année d'apparition du mobile.
	 */
	private final int anneeApparition;

	/**
	 * Année de disparition du mobile.
	 */
	private final int anneeDisparition;

	/**
	 * Crée un nouveau mobile avec ses caractéristiques.
	 *
	 * @param id               Identifiant unique du mobile
	 * @param type             Type ou catégorie du mobile
	 * @param ligne            Position initiale sur la ligne de la grille
	 * @param colonne          Position initiale sur la colonne de la grille
	 * @param anneeApparition  Année à partir de laquelle le mobile devient actif
	 * @param anneeDisparition Année jusqu'à laquelle le mobile reste actif
	 */
	public Mobile(int id, String type, int ligne, int colonne, int anneeApparition, int anneeDisparition) {
		this.id = id;
		this.type = type;
		this.ligne = ligne;
		this.colonne = colonne;
		this.anneeApparition = anneeApparition;
		this.anneeDisparition = anneeDisparition;
	}

	/**
	 * Retourne l'identifiant unique du mobile.
	 *
	 * @return Identifiant du mobile
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retourne le type ou la catégorie du mobile.
	 *
	 * @return Type du mobile
	 */
	public String getType() {
		return type;
	}

	/**
	 * Retourne la position actuelle du mobile sur la ligne.
	 *
	 * @return Ligne du mobile
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * Retourne la position actuelle du mobile sur la colonne.
	 *
	 * @return Colonne du mobile
	 */
	public int getColonne() {
		return colonne;
	}

	/**
	 * Retourne l'année d'apparition du mobile.
	 *
	 * @return Année à partir de laquelle le mobile est actif
	 */
	public int getAnneeApparition() {
		return anneeApparition;
	}

	/**
	 * Retourne l'année de disparition du mobile.
	 *
	 * @return Année après laquelle le mobile n'est plus actif
	 */
	public int getAnneeDisparition() {
		return anneeDisparition;
	}

	/**
	 * Modifie la position du mobile sur la ligne.
	 *
	 * @param ligne Nouvelle position en ligne
	 */
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	/**
	 * Modifie la position du mobile sur la colonne.
	 *
	 * @param colonne Nouvelle position en colonne
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	/**
	 * Vérifie si le mobile est actif pour une année donnée.
	 *
	 * <p>
	 * Un mobile est actif si l'année actuelle est comprise entre
	 * {@link #anneeApparition} et {@link #anneeDisparition} inclus.
	 * </p>
	 *
	 * @param anneeActuelle Année à vérifier
	 * @return true si le mobile est actif, false sinon
	 */
	public boolean estActif(int anneeActuelle) {
		return anneeActuelle >= getAnneeApparition() && anneeActuelle <= getAnneeDisparition();
	}

	/**
	 * Retourne le code de hachage basé sur l'identifiant.
	 *
	 * <p>
	 * Tous les mobiles ayant le même identifiant auront le même hashCode.
	 * </p>
	 *
	 * @return Code de hachage du mobile
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Vérifie l'égalité entre deux mobiles.
	 *
	 * <p>
	 * Deux mobiles sont considérés égaux s'ils possèdent le même identifiant.
	 * </p>
	 *
	 * @param obj Objet à comparer
	 * @return true si les deux objets représentent le même mobile, false sinon
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Mobile other)) return false;
        return this.id == other.id;
	}

	/**
	 * Retourne une représentation textuelle complète du mobile.
	 *
	 * <p>
	 * Affiche l'identifiant, le type, la position (ligne, colonne), et peut être étendu
	 * pour inclure la période de vie.
	 * </p>
	 *
	 * @return Chaîne représentant le mobile
	 */
	@Override
	public String toString() {
		return "Mobile [id=" + id + ", type=" + type + ", ligne=" + ligne + ", colonne=" + colonne +
				", anneeApparition=" + anneeApparition + ", anneeDisparition=" + anneeDisparition + "]";
	}
}