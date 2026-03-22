package moteur.donnees;

import java.util.Objects;

/**
 * Classe qui représente une relation 
 * 
 * @author Alexandre et Massinissa
 * @version 1.0
 */
public class Relation {
	
	// Attributs 
	private final String nomCivilisation;
	private float niveau; // En pourcentage (0% à 100%)
	
	/**
	 * Constructeur de la classe Relation
	 *  
	 * @param nomCivilisation : Nom de la civilisation 
	 * @param niveau : Niveau de relation 
	 */
	public Relation(String nomCivilisation, float niveau) {
		this.nomCivilisation = nomCivilisation;
		this.niveau = niveau;
	}

	/**
	 * Getter de nomCivilisation 
	 * @return Le nom de la civilisation
	 */
	public String getNomCivilisation() {
		return nomCivilisation;
	}

	/**
	 * Getter de niveau 
	 * @return Le niveau de relation 
	 */
	public float getNiveau() {
		return niveau;
	}

	/**
	 * Setter de niveau 
	 * @param niveau : Nouveau niveau de relation 
	 */
	public void setNiveau(float niveau) {
		this.niveau = niveau;
	}
	
	/**
	 * Retourne le code de hachage de la classe Relation 
	 * @return Le code de hachage 
	 */
	@Override
	public int hashCode() {
		return Objects.hash(niveau, nomCivilisation);
	}

	/**
	 * Vérifie si deux relations sont identiques 
	 * @return true si deux relations sont identiques, false sinon 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Relation)) {
			return false;
		}
		
		Relation other = (Relation) obj;
		return Float.floatToIntBits(niveau) == Float.floatToIntBits(other.niveau)
				&& Objects.equals(nomCivilisation, other.nomCivilisation);
	}

	/**
	 * Affiche les informations de la relation 
	 * @return l'affichage des informations 
	 */
	@Override
	public String toString() {
		return "Relation [nomCivilisation=" + nomCivilisation + ", niveau=" + niveau + "]";
	}
	
}
