package gui.mobiles;

import java.util.Objects;

/**
 * Classe qui représente une action 
 * 
 * @author Alexandre 
 * @version 1.0
 * 
 * @see Condition
 */
public class Action {
	
	// Attributs 
	private final String type;
	private final int distanceLigne;
	private final int distanceColonne;
	
	/**
	 * Constructeur de la classe Action 
	 * @param type : Type de l'action 
	 * @param condition : Condition de déclenchement 
	 * @param distanceX : Distance en X
	 * @param distanceY : Distance en Y
	 */
	public Action(String type, int distanceLigne, int distanceColonne) {
		this.type = type;
		this.distanceLigne = distanceLigne;
		this.distanceColonne = distanceColonne;
	}

	/**
	 * Getter de type 
	 * @return Le type d'action 
	 */
	public String getType() {
		return type;
	}

	/**
	 * Getter de distanceX
	 * @return La distance en X
	 */
	public int getDistanceLigne() {
		return distanceLigne;
	}
	
	/**
	 * Getter de distanceY
	 * @return La distance en Y 
	 */
	public int getDistanceColonne() {
		return distanceColonne;
	}

	/**
	 * Retourne le code de hachage de la classe Action 
	 * @return Le code de hachage 
	 */
	@Override
	public int hashCode() {
		return Objects.hash(distanceLigne, distanceColonne, type);
	}

	/**
	 * Vérifie si deux actions sont identiques 
	 * @return true si deux actions sont identiques, false sinon 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Action)) {
			return false;
		}
		
		Action other = (Action) obj;
		return distanceLigne == other.distanceLigne && distanceColonne == other.distanceColonne && Objects.equals(type, other.type);
	}

	/**
	 * Affiche les informations d'une action 
	 * 
	 * @return l'affichage des informations 
	 */
	@Override
	public String toString() {
		return "Action [type=" + type + ", distanceLigne=" + distanceLigne + ", distanceColonne=" + distanceColonne
				+ "]";
	}
		
}
