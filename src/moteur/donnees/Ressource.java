package moteur.donnees;

import java.util.Objects;

/**
 * Représente une ressource utilisée dans la simulation.
 *
 * <p>
 * Une ressource est définie par un nom, un type, un coût unitaire
 * et une quantité disponible.
 * </p>
 *
 * <p>
 * Les types de ressources peuvent être :
 * humaines, financières, matérielles, immatérielles ou technologiques.
 * </p>
 *
 * @author Alexandre
 * @author Massinissa
 *
 * @version 1.2
 */
public class Ressource {

    /**
     * Nom de la ressource.
     */
    private String nom;

    /**
     * Type de la ressource (humaine, financière, matérielle, immatérielle, technologique).
     */
    private String type;

    /**
     * Coût unitaire de la ressource.
     */
    private float cout;

    /**
     * Quantité disponible de la ressource.
     */
    private int quantite;

    /**
     * Construit une ressource avec les caractéristiques spécifiées.
     *
     * @param nom nom de la ressource
     * @param type type de la ressource
     * @param cout coût unitaire (doit être positif)
     * @param quantite quantité disponible (>= 0)
     */
    public Ressource(String nom, String type, float cout, int quantite) {
        this.nom = nom;
        this.type = type;
        this.cout = cout;
        this.quantite = quantite;
    }

    /**
     * Retourne le nom de la ressource.
     *
     * @return le nom de la ressource
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le type de la ressource.
     *
     * @return le type de la ressource
     */
    public String getType() {
        return type;
    }

    /**
     * Retourne la quantité disponible.
     *
     * @return la quantité de la ressource
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Retourne le coût unitaire de la ressource.
     *
     * @return le coût de la ressource
     */
    public float getPrix() {
        return cout;
    }

    /**
     * Définit un nouveau coût unitaire.
     *
     * @param cout nouveau coût (doit être positif)
     */
    public void setCout(float cout) {
        this.cout = cout;
    }

    /**
     * Définit le type de la ressource.
     *
     * @param type nouveau type de la ressource
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Définit le nom de la ressource.
     *
     * @param nom nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Définit la quantité de la ressource.
     *
     * @param quantite nouvelle quantité (>= 0)
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Calcule le code de hachage de la ressource.
     *
     * <p>
     * Basé sur le nom, le type et la quantité.
     * </p>
     *
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, type, quantite);
    }

    /**
     * Compare cette ressource à un autre objet.
     *
     * <p>
     * Deux ressources sont considérées égales si elles ont le même nom
     * et le même type (la quantité et le coût ne sont pas pris en compte).
     * </p>
     *
     * @param obj objet à comparer
     * @return true si les ressources sont égales, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ressource)) {
            return false;
        }
        Ressource res = (Ressource) obj;
        return Objects.equals(this.nom, res.nom)
                && Objects.equals(this.type, res.type);
    }

    /**
     * Retourne une représentation textuelle de la ressource.
     *
     * @return une chaîne décrivant la ressource
     */
    @Override
    public String toString() {
        return "Nom : " + this.getNom()
                + ", Type : " + this.getType()
                + ", Quantité : " + this.getQuantite();
    }
}