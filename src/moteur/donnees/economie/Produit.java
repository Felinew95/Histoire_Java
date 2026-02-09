package moteur.donnees.economie;

import java.util.Objects;

/**
 * Classe qui représente un produit
 * 
 * @author Alexandre
 * @version 1.1
 * 
 * @see Objects
 */
public class Produit {

    // Attributs
    private String nom;
    private int quantite; // Quantité supérieure ou égale à 0
    private float prix; // Prix supérieur à 0

    /**
     * Constructeur de la classe Produit
     * 
     * @param nom      : Nom du produit
     * @param quantite : Quantité du produit
     * @param prix     : Prix du produit
     */
    public Produit(String nom, int quantite, float prix) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
    }

    /**
     * Getter de nom
     * 
     * @return Le nom du produit
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de quantite
     * 
     * @return La quantite du produit
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Getter de prix
     * 
     * @return Le prix du produit
     */
    public float getPrix() {
        return prix;
    }

    /**
     * Setter de nom
     * 
     * @param nom : Nouveau nom du produit
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter de quantite
     * 
     * @param quantite : Nouvelle quantite du produit
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Setter de prix
     * 
     * @param prix : Nouveau prix du produit
     */
    public void setPrix(float prix) {
        this.prix = prix;
    }

    /**
     * Code de hachage de la classe Produit
     * 
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, quantite, prix);
    }

    /**
     * Vérifie si deux produits sont identiques
     * 
     * @param obj : Objet à comparer
     * @return true si deux produits sont identiques, false sinon
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
        return Objects.equals(this.nom, prod.nom) && Objects.equals(this.prix, prod.prix);
    }

    /**
     * Affiche les informations sur le produit
     * 
     * @return l'affichage des informations
     */
    @Override
    public String toString() {
        return "Nom : " + this.getNom() + ", Prix : " + this.getPrix() +
                ", Quantite : " + this.getQuantite();
    }

}
