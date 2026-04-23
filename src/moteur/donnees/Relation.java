package moteur.donnees;

import java.util.Objects;

/**
 * Représente une relation entre la civilisation courante
 * et une autre civilisation.
 *
 * <p>
 * Une relation est définie par le nom de la civilisation cible
 * et un niveau de relation exprimé en pourcentage.
 * </p>
 *
 * <p>
 * Le niveau de relation est compris entre 0 et 100 :
 * <ul>
 *   <li>0 : relation hostile</li>
 *   <li>50 : relation neutre</li>
 *   <li>100 : alliance forte</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @author Massinissa
 *
 * @version 1.1
 */
public class Relation {

	/**
	 * Nom de la civilisation avec laquelle la relation est établie.
	 */
	private final String nomCivilisation;

	/**
	 * Niveau de relation en pourcentage (0 à 100).
	 * Représente la qualité des relations diplomatiques.
	 */
	private float niveau;

	/**
	 * Construit une relation avec une civilisation donnée.
	 *
	 * @param nomCivilisation nom de la civilisation cible
	 * @param niveau niveau de relation (entre 0 et 100)
	 */
	public Relation(String nomCivilisation, float niveau) {
		this.nomCivilisation = nomCivilisation;
		this.niveau = niveau;
	}

	/**
	 * Retourne le nom de la civilisation associée.
	 *
	 * @return le nom de la civilisation
	 */
	public String getNomCivilisation() {
		return nomCivilisation;
	}

	/**
	 * Retourne le niveau de relation.
	 *
	 * @return le niveau en pourcentage (0 à 100)
	 */
	public float getNiveau() {
		return niveau;
	}

	/**
	 * Définit un nouveau niveau de relation.
	 *
	 * @param niveau nouveau niveau (entre 0 et 100)
	 */
	public void setNiveau(float niveau) {
		this.niveau = niveau;
	}

	/**
	 * Calcule le code de hachage de la relation.
	 *
	 * <p>
	 * Basé uniquement sur le nom de la civilisation,
	 * car il identifie de manière unique la relation.
	 * </p>
	 *
	 * @return le code de hachage
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nomCivilisation);
	}

	/**
	 * Compare cette relation à un autre objet.
	 *
	 * <p>
	 * Deux relations sont considérées égales si elles concernent
	 * la même civilisation, indépendamment du niveau.
	 * </p>
	 *
	 * @param obj objet à comparer
	 * @return true si les relations concernent la même civilisation
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
		return Objects.equals(nomCivilisation, other.nomCivilisation);
	}

	/**
	 * Retourne une représentation textuelle de la relation.
	 *
	 * @return une chaîne décrivant la relation
	 */
	@Override
	public String toString() {
		return "Relation [civilisation=" + nomCivilisation
				+ ", niveau=" + niveau + "%]";
	}
}