package gui.mobiles;

import java.util.Objects;

/**
 * Classe qui représente un mobile
 *
 * @author Alexandre
 * @version 1.0
 */
public class Mobile {

	// Attributs
	private final int id;
	private final String type;
	
	private int ligne;
	private int colonne;
	
	private final int anneeApparition;
	private final int anneeDisparition;

	/**
	 * Constructeur de la classe Mobile
	 * 
	 * @param id      : Identifiant unique du mobile
	 * @param type    : Type du mobile
	 * @param ligne   : Position en ligne
	 * @param colonne : Position en colonne
	 * @param anneeApparition : Année d'apparition du mobile
	 * @param anneeDisparition : Année de disparition du mobile
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
	 * Getter de id
	 * 
	 * @return L'identifiant du mobile
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter de type
	 * 
	 * @return Le type du mobile
	 */
	public String getType() {
		return type;
	}

	/**
	 * Getter de ligne
	 * 
	 * @return La ligne
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * Getter de colonne
	 * 
	 * @return La colonne
	 */
	public int getColonne() {
		return colonne;
	}
	
	/**
	 * Getter de anneeApparition 
	 * 
	 * @return L'annee d'appartion du mobile 
	 */
	public int getAnneeApparition() {
		return anneeApparition;
	}

	/**
	 * Getter de anneeDisparition 
	 * 
	 * @return L'année de disparition 
	 */
	public int getAnneeDisparition() {
		return anneeDisparition;
	}
	
	/**
	 * Méthode qui vérifie si un mobile est actif 
	 * 
	 * @param anneeActuelle : Année actuelle
	 * @return true si un mobile est actif, false sinon 
	 */
	public boolean estActif(int anneeActuelle) {
	    return anneeActuelle >= anneeApparition && anneeActuelle <= anneeDisparition;
	}

	/**
	 * Setter de ligne 
	 * 
	 * @param ligne : Nouvelle position en ligne 
	 */
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	/**
	 * Setter de colonne 
	 * 
	 * @param colonne : Nouvelle position en colonne 
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	/**
	 * Retourne le code de hachage de la classe Mobile
	 * 
	 * @return Le code de hachage
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Vérifie si deux mobiles sont identiques
	 * 
	 * @param obj : Objet à comparer
	 * @return true si deux mobiles sont identiques, false sinon
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Mobile)) {
			return false;
		}

		Mobile other = (Mobile) obj;
		return this.id == other.id;
	}

	/**
	 * Affiche les informations du mobile 
	 * 
	 * @return L'affichage des informations
	 */
	@Override
	public String toString() {
		return "Mobile [id=" + id + ", type=" + type + ", ligne=" + ligne + ", colonne=" + colonne + "]";
	}
	
}
