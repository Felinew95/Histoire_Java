package gui.mobiles;

import java.util.Objects;

/**
 * Classe représentant une action effectuée par un mobile.
 *
 * <p>
 * Une action est définie par :
 * </p>
 *
 * <ul>
 *   <li>Son type (déplacement, attaque, etc.)</li>
 *   <li>Sa distance en ligne (axe vertical)</li>
 *   <li>Sa distance en colonne (axe horizontal)</li>
 * </ul>
 *
 * Cette classe est immuable : ses attributs sont finals et définis au constructeur.
 *
 * @author Alexandre
 * @version 2.0
 */
public class Action {

	/**
	 * Type d'action à faire
	 */
	private final String type;

	/**
	 * Distance en ligne
	 */
	private final int distanceLigne;

	/**
	 * Distance en colonne
	 */
	private final int distanceColonne;

	/**
	 * Constructeur de la classe Action.
	 *
	 * @param type           Type de l'action (ex. "déplacement", "attaque")
	 * @param distanceLigne  Distance en lignes (axe vertical)
	 * @param distanceColonne Distance en colonnes (axe horizontal)
	 */
	public Action(String type, int distanceLigne, int distanceColonne) {
		this.type = type;
		this.distanceLigne = distanceLigne;
		this.distanceColonne = distanceColonne;
	}

	/**
	 * Retourne le type de l'action.
	 *
	 * @return Le type de l'action
	 */
	public String getType() {
		return type;
	}

	/**
	 * Retourne la distance en ligne (axe vertical).
	 *
	 * @return La distance en lignes
	 */
	public int getDistanceLigne() {
		return distanceLigne;
	}

	/**
	 * Retourne la distance en colonne (axe horizontal).
	 *
	 * @return La distance en colonnes
	 */
	public int getDistanceColonne() {
		return distanceColonne;
	}

	/**
	 * Retourne le code de hachage de cette action.
	 *
	 * @return Un code de hachage basé sur le type et les distances
	 */
	@Override
	public int hashCode() {
		return Objects.hash(distanceLigne, distanceColonne, type);
	}

	/**
	 * Vérifie si deux actions sont identiques.
	 *
	 * @param obj L'objet à comparer
	 * @return true si les deux actions ont le même type et les mêmes distances, false sinon
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Action)) return false;
		
		Action other = (Action) obj;
        return distanceLigne == other.distanceLigne &&
				distanceColonne == other.distanceColonne &&
				Objects.equals(type, other.type);
	}

	/**
	 * Retourne une représentation textuelle de l'action.
	 *
	 * @return Une chaîne de caractères avec le type et les distances
	 */
	@Override
	public String toString() {
		return "Action [type=" + type + ", distanceLigne=" + distanceLigne + ", distanceColonne=" + distanceColonne + "]";
	}

}