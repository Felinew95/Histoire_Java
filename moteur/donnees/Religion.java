package moteur.donnees;

/**
 * Représente une religion dans la simulation.
 *
 * <p>
 * Une religion est définie par un nom, une croyance principale
 * et un niveau d'influence sur la population.
 * </p>
 *
 * <p>
 * L'influence est exprimée en pourcentage entre 0 et 100
 * et représente l'importance ou la domination de cette religion.
 * </p>
 *
 * @author Alexandre
 * @version 1.2
 */
public class Religion {

    /**
     * Nom de la religion.
     */
    private String nom;

    /**
     * Description de la croyance principale.
     */
    private String croyance;

    /**
     * Influence de la religion en pourcentage (0 à 100).
     * Représente son importance dans la société.
     */
    private float influence;

    /**
     * Construit une religion avec les caractéristiques spécifiées.
     *
     * @param nom nom de la religion
     * @param croyance croyance principale associée
     * @param influence niveau d'influence (entre 0 et 100)
     */
    public Religion(String nom, String croyance, float influence) {
        this.nom = nom;
        this.croyance = croyance;
        this.influence = influence;
    }

    /**
     * Retourne le nom de la religion.
     *
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la croyance principale.
     *
     * @return la croyance
     */
    public String getCroyance() {
        return croyance;
    }

    /**
     * Retourne le niveau d'influence.
     *
     * @return l'influence en pourcentage (0 à 100)
     */
    public float getInfluence() {
        return influence;
    }

    /**
     * Définit un nouveau nom pour la religion.
     *
     * @param nom nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Définit une nouvelle croyance.
     *
     * @param croyance nouvelle croyance
     */
    public void setCroyance(String croyance) {
        this.croyance = croyance;
    }

    /**
     * Définit le niveau d'influence de la religion.
     *
     * @param influence nouvelle influence (entre 0 et 100)
     */
    public void setInfluence(float influence) {
        this.influence = influence;
    }

    /**
     * Retourne une représentation textuelle de la religion.
     *
     * @return une chaîne décrivant la religion
     */
    @Override
    public String toString() {
        return "Nom : " + this.getNom()
                + ", Croyance : " + this.getCroyance()
                + ", Influence : " + this.getInfluence() + "%";
    }
}