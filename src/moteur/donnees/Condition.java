package moteur.donnees;

import java.util.Objects;

/**
 * Classe qui représente une condition 
 * 
 * @author Alexandre 
 * @version 1.0
 */
public class Condition {
	
	// Attributs 
	private final String type;
	private final int valeur;
	
	/**
	 * Constructeur de la classe Condition
	 * @param type : Type de la condition 
	 * @param valeur : Valeur de la condition 
	 */
	public Condition(String type, int valeur) {
		this.type = type;
		this.valeur = valeur;
	}

	/**
	 * Getter de type 
	 * @return Le type de la condition 
	 */
	public String getType() {
		return type;
	}

	/**
	 * Getter de valeur 
	 * @return La valeur de la condition
	 */
	public int getValeur() {
		return valeur;
	}

	/**
	 * Retourne le code de hachage de la classe Condition 
	 * @return Le code de hachage 
	 */
	@Override
	public int hashCode() {
		return Objects.hash(type, valeur);
	}

	/**
	 * Vérifie si deux conditions sont identiques 
	 * @return true si deux conditions sont identiques, false sinon 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Condition)) {
			return false;
		}
		
		Condition other = (Condition) obj;
		return Objects.equals(type, other.type) && valeur == other.valeur;
	}

}
