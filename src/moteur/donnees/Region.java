package moteur.donnees;

/**
 * Classe qui représente une région géographique
 * 
 * @author Alexandre et Massinissa
 * @version 1.0
 */
public class Region {
    
    // Attributs
    private String nom; // Nom de la région
    private String croyance; // Croyance dominante dans la région
    private float influence; // Influence de la région (0 à 100%)
    
    /**
     * Constructeur de la classe Region
     * 
     * @param nom : Nom de la région
     * @param croyance : Croyance dominante
     * @param influence : Influence de la région
     */
    public Region(String nom, String croyance, float influence) {
        this.nom = nom;
        this.croyance = croyance;
        this.influence = influence;
    }
    
    /**
     * Constructeur simplifié de la classe Region
     * 
     * @param nom : Nom de la région
     */
    public Region(String nom) {
        this(nom, "Aucune", 50.0f);
    }
    
    /**
     * Getter de nom
     * @return Le nom de la région
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Getter de croyance
     * @return La croyance dominante
     */
    public String getCroyance() {
        return croyance;
    }
    
    /**
     * Getter de influence
     * @return L'influence de la région
     */
    public float getInfluence() {
        return influence;
    }
    
    /**
     * Setter de nom
     * @param nom : Nouveau nom de la région
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Setter de croyance
     * @param croyance : Nouvelle croyance
     */
    public void setCroyance(String croyance) {
        this.croyance = croyance;
    }
    
    /**
     * Setter de influence
     * @param influence : Nouvelle influence
     */
    public void setInfluence(float influence) {
        this.influence = influence;
    }
    
    /**
     * Augmente l'influence de la région
     * @param valeur : Valeur à ajouter
     */
    public void augmenterInfluence(float valeur) {
        this.influence += valeur;
        if (this.influence > 100) {
            this.influence = 100;
        }
    }
    
    /**
     * Diminue l'influence de la région
     * @param valeur : Valeur à retirer
     */
    public void diminuerInfluence(float valeur) {
        this.influence -= valeur;
        if (this.influence < 0) {
            this.influence = 0;
        }
    }
    
    /**
     * Affiche les informations de la région
     * @return L'affichage des informations
     */
    @Override
    public String toString() {
        return "Région : " + this.getNom() + ", Croyance : " + this.getCroyance() + 
               ", Influence : " + this.getInfluence() + "%";
    }
}