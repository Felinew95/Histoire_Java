package moteur.donnees;

import java.util.Objects;

/**
 * Classe qui représente une ressource
 * 
 * @author Alexandre et Massinissa
 * @version 1.1
 * 
 * @see Objects
 */
public class Ressource {

    // Attributs
    private String nom;
    private String type; // Types : humaines, financières, matérielles, immatérielles et technologiques
    private float cout;
    private int quantite;

    /**
     * Constructeur de la classe Ressource
     * 
     * @param nom      : Nom de la ressource
     * @param type     : Type de la ressource
     * @param cout     : Cout de la ressource
     * @param quantite : La quantité de cette ressource
     */
    public Ressource(String nom, String type, float cout, int quantite) {
        this.nom = nom;
        this.type = type;
        this.cout = cout;
        this.quantite = quantite;
    }

    /**
     * Getter de nom
     * 
     * @return Le nom de la ressource
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de type
     * 
     * @return Le type de la ressource
     */
    public String getType() {
        return type;
    }

    /**
     * Getter de quantite
     * 
     * @return La quantite de la ressource
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Getter de cout
     *
     * @return Le cout de la ressource
     */
    public float getPrix() {
        return cout;
    }

    /**
     * Setter de cout
     *
     * @param cout : Nouveau cout de la ressource
     */
    public void setCout(float cout) {
        this.cout = cout;
    }

    /**
     * Setter de type
     * 
     * @param type : Le type de la ressource
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter de nom
     * 
     * @param nom : Le nom de la ressource
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter de quantite
     * 
     * @param quantite : La quantite de la ressource
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Code de hachage de la classe Ressource
     * 
     * @return Le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, type, quantite);
    }

    /**
     * Vérifie si deux ressources sont identiques
     * 
     * @param obj : Objet à comparer
     * @return true si deux ressources sont identiques, false sinon
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
        return Objects.equals(this.nom, res.nom) && Objects.equals(this.type, res.type);
    }

    /**
     * Affiche les informations sur la ressource
     * 
     * @return L'affichage de la ressources
     */
    @Override
    public String toString() {
        return "Nom : " + this.getNom() + ", Type : " + this.getType()
                + ", Quantité : " + this.getQuantite();
    }

}
