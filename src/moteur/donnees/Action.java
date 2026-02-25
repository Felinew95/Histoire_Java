package moteur.donnees;

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
	private final Condition condition;
	
	private final int distanceX;
	private final int distanceY;
	
	/**
	 * Constructeur de la classe Action 
	 * @param type : Type de l'action 
	 * @param condition : Condition de déclenchement 
	 * @param distanceX : Distance en X
	 * @param distanceY : Distance en Y
	 */
	public Action(String type, Condition condition, int distanceX, int distanceY) {
		this.type = type;
		this.condition = condition;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
	}

	/**
	 * Getter de type 
	 * @return Le type d'action 
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Getter de condition 
	 * @return La condition de déclenchement 
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * Getter de distanceX
	 * @return La distance en X
	 */
	public int getDistanceX() {
		return distanceX;
	}
	
	/**
	 * Getter de distanceY
	 * @return La distance en Y 
	 */
	public int getDistanceY() {
		return distanceY;
	}

	/**
	 * Retourne le code de hachage de la classe Action 
	 * @return Le code de hachage 
	 */
	@Override
	public int hashCode() {
		return Objects.hash(condition, distanceX, distanceY, type);
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
		return Objects.equals(condition, other.condition) && distanceX == other.distanceX
				&& distanceY == other.distanceY && Objects.equals(type, other.type);
	}
	
}
