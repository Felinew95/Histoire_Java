package moteur.donnees;

import java.util.Objects;

/**
 * Classe qui représente un événement
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Objects
 */
public class Evenement {

    // Attributs
    private String nom;
    private int annee;
    private String region;// Classe Region
    private String narration;
    private String type;

    /**
     * Constructeur de la classe Evenement
     * 
     * @param nom       : Nom de l'événement
     * @param annee     : Année de l'événement
     * @param region    : Région de l'évenement
     * @param narration : Narration de l'événement
     * @param type      : Type d'événement
     */
    public Evenement(String nom, int annee, String region, String narration, String type) {
        this.nom = nom;
        this.annee = annee;
        this.region = region;
        this.narration = narration;
        this.type = type;
    }

    /**
     * Getter de nom
     * 
     * @return Le nom de l'événement
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de annee
     * 
     * @return L'année de l'événement
     */
    public int getAnnee() {
        return annee;
    }

    /**
     * Getter de region
     * 
     * @return La région de l'événement
     */
    public String getRegion() {
        return region;
    }

    /**
     * Getter de narration
     * 
     * @return La narration de l'événement
     */
    public String getNarration() {
        return narration;
    }

    /**
     * Getter de type
     * 
     * @return Le type de l'événement
     */
    public String getType() {
        return type;
    }

    /**
     * Setter de nom
     * 
     * @param nom : Nouveau nom de l'événement
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter de annee
     * 
     * @param annee : Nouvelle année de l'événement
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    /**
     * Setter de region
     * 
     * @param region : Nouvelle région de l'événement
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Setter de narration
     * 
     * @param narration : Nouvelle narration de l'événement
     */
    public void setNarration(String narration) {
        this.narration = narration;
    }

    /**
     * Setter de type
     * 
     * @param type : Nouveau type d'événement
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Code de hachage de la classe Evenement
     * 
     * @return Le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, annee, region, narration);
    }

    /**
     * Vérifie si deux événements sont identiques
     * 
     * @param obj : Objet à comparer
     * @return true si deux événements sont identiques, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Evenement)) {
            return false;
        }

        Evenement even = (Evenement) obj;
        return Objects.equals(this.nom, even.nom) && Objects.equals(this.annee, even.annee) &&
                Objects.equals(this.region, even.region) && Objects.equals(this.narration, even.narration);
    }

    /**
     * Affiche les informations de l'événement
     * 
     * @return L'affichage des informations
     */
    @Override
    public String toString() {
        return "Nom : " + this.getNom() + ", Type : " + this.getType() + ", Année : " + this.getAnnee() + ", Région : "
                + this.getRegion() + "\nNarration : " + this.getNarration();
    }

}