package moteur.donnees;

/**
 * Classe qui représente une religion 
 *
 * @author Alexandre 
 * @version 1.1
 */
public class Religion {
    
    // Attributs 
    private String nom;
    private String croyance;
    private float influence; // En pourcentage (0 à 100%)

    /**
     * Constructeur de la classe Religion 
     * 
     * @param nom : Nom de la religion 
     * @param croyance : Croyance de la religion
     * @param influence : Influence de la religion 
     */
    public Religion(String nom, String croyance, float influence) {
        this.nom = nom;
        this.croyance = croyance;
        this.influence = influence;
    }

    /**
     * Getter de nom
     * @return Le nom de la religion
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de croyance 
     * @return La croyance de la religion 
     */
    public String getCroyance() {
        return croyance;
    }

    /**
     * Getter de influence 
     * @return L'influence de la religion 
     */
    public float getInfluence() {
        return influence;
    }

    /**
     * Setter de nom 
     * @param nom : Nouveau nom de la religion 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter de croyance 
     * @param croyance : Nouvelle croyance de la religion
     */
    public void setCroyance(String croyance) {
        this.croyance = croyance;
    }

    /**
     * Setter de influence 
     * @param influence : Nouvelle influence de la région
     */
    public void setInfluence(float influence) {
        this.influence = influence;
    }

    /**
     * Affiche les informations de la religion 
     * @return L'affichage des informations 
     */
    @Override
    public String toString() {
        return "Nom : " + this.getNom() + ", Croyance : " + this.getCroyance() + 
            ", Influence : " + this.getInfluence() + "%";
    }
    
}
