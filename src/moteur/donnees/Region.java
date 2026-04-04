package moteur.donnees;

/**
 * Représente une région géographique dans la simulation.
 *
 * <p>
 * Une région est caractérisée par un nom et un chef qui en assure
 * la gestion ou la gouvernance.
 * </p>
 *
 * @author Massinissa
 * @version 1.1
 */
public class Region {

    /**
     * Nom de la région.
     */
    private String nom;

    /**
     * Nom du chef de la région.
     */
    private String chef;

    /**
     * Construit une région avec les informations spécifiées.
     *
     * @param nom nom de la région
     * @param chef nom du chef de la région
     */
    public Region(String nom, String chef) {
        this.nom = nom;
        this.chef = chef;
    }

    /**
     * Retourne le nom de la région.
     *
     * @return le nom de la région
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le chef de la région.
     *
     * @return le nom du chef
     */
    public String getChef() {
        return chef;
    }

    /**
     * Définit un nouveau nom pour la région.
     *
     * @param nom nouveau nom de la région
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Définit un nouveau chef pour la région.
     *
     * @param chef nom du nouveau chef
     */
    public void setChef(String chef) {
        this.chef = chef;
    }

    /**
     * Retourne une représentation textuelle de la région.
     *
     * @return une chaîne décrivant la région
     */
    @Override
    public String toString() {
        return "\nRégion : " + this.getNom()
                + "\nChef : " + this.getChef();
    }
}