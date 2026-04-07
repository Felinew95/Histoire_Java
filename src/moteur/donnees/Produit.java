package moteur.donnees;

import java.util.Objects;

/**
 * Représente un produit dans la simulation.
 *
 * <p>
 * Un produit est défini par un nom, une quantité disponible
 * et un prix unitaire.
 * </p>
 *
 * <p>
 * Contraintes :
 * <ul>
 *   <li>La quantité est supérieure ou égale à 0</li>
 *   <li>Le prix est strictement positif</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @version 1.2
 */
public class Produit {

    /**
     * Nom du produit.
     */
    private String nom;

    /**
     * Quantité disponible (>= 0).
     */
    private int quantite;

    /**
     * Prix unitaire (> 0).
     */
    private float prix;

    /**
     * Construit un produit avec les caractéristiques spécifiées.
     *
     * @param nom nom du produit
     * @param quantite quantité disponible (>= 0)
     * @param prix prix unitaire (> 0)
     */
    public Produit(String nom, int quantite, float prix) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
    }

    /**
     * Retourne le nom du produit.
     *
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la quantité disponible.
     *
     * @return la quantité
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Retourne le prix unitaire.
     *
     * @return le prix
     */
    public float getPrix() {
        return prix;
    }

    /**
     * Définit un nouveau nom pour le produit.
     *
     * @param nom nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Définit une nouvelle quantité.
     *
     * @param quantite nouvelle quantité (>= 0)
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Définit un nouveau prix unitaire.
     *
     * @param prix nouveau prix (> 0)
     */
    public void setPrix(float prix) {
        this.prix = prix;
    }

    /**
     * Calcule le code de hachage du produit.
     *
     * <p>
     * Basé uniquement sur le nom, qui identifie le produit.
     * </p>
     *
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    /**
     * Compare ce produit à un autre objet.
     *
     * <p>
     * Deux produits sont considérés égaux s'ils ont le même nom,
     * indépendamment de la quantité ou du prix.
     * </p>
     *
     * @param obj objet à comparer
     * @return true si les produits ont le même nom
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Produit)) {
            return false;
        }
        Produit prod = (Produit) obj;
        return Objects.equals(this.nom, prod.nom);
    }

    /**
     * Retourne une représentation textuelle du produit.
     *
     * @return une chaîne décrivant le produit
     */
    @Override
    public String toString() {
        return "Produit : " + this.getNom()
                + ", Prix : " + this.getPrix()
                + ", Quantité : " + this.getQuantite();
    }
}