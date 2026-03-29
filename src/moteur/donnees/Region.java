package moteur.donnees;

/**
 * Classe qui représente une région géographique
 * 
 * @author Massinissa
 * @version 1.0
 */
public class Region {

    // Attributs
    private String nom;
    private String chef;

    /**
     * Constructeur de la classe Region
     * 
     * @param nom      : Nom de la région
     * @param chef     : Chef de la région
     */
    public Region(String nom, String chef) {
        this.nom = nom;
        this.chef = chef;
    }

    /**
     * Getter de nom
     * 
     * @return Le nom de la région
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de chef
     * 
     * @return Le chef de la région
     */
    public String getChef() {
        return chef;
    }

    /**
     * Setter de nom
     * 
     * @param nom : Nouveau nom de la région
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter de chef
     * 
     * @param chef : Nouveau chef de la région
     */
    public void setChef(String chef) {
        this.chef = chef;
    }

    /**
     * Affiche les informations de la région
     * 
     * @return l'affichage des informations
     */
    @Override
    public String toString() {
        return "\n\tNom : " + this.getNom() + "\n\tChef : " + this.getChef();
    }

}