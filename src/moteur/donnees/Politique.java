package moteur.donnees;

/**
 * Représente la politique d'une civilisation.
 *
 * <p>
 * Une politique est définie par un type de régime, un niveau de stabilité
 * ainsi que différentes orientations (militaire, économique et diplomatique).
 * </p>
 *
 * <p>
 * La stabilité est exprimée en pourcentage (0 à 100) et permet d'évaluer
 * la solidité du système politique.
 * </p>
 *
 * @author Massinissa
 * @version 1.1
 */
public class Politique {

    /**
     * Type de régime politique (ex : Monarchie, République).
     */
    private String typeRegime;

    /**
     * Stabilité politique en pourcentage (0 à 100).
     */
    private float stabilite;

    /**
     * Orientation militaire (Offensive, Défensive, Neutre).
     */
    private String politiqueMilitaire;

    /**
     * Orientation économique (Libre-échange, Protectionnisme, etc.).
     */
    private String politiqueEconomique;

    /**
     * Orientation diplomatique (Expansionniste, Pacifique, etc.).
     */
    private String politiqueDiplomatique;

    /**
     * Construit une politique complète.
     *
     * @param typeRegime type de régime politique
     * @param stabilite stabilité politique (0 à 100)
     * @param politiqueMilitaire orientation militaire
     * @param politiqueEconomique orientation économique
     * @param politiqueDiplomatique orientation diplomatique
     */
    public Politique(String typeRegime, float stabilite, String politiqueMilitaire,
                     String politiqueEconomique, String politiqueDiplomatique) {
        this.typeRegime = typeRegime;
        this.stabilite = stabilite;
        this.politiqueMilitaire = politiqueMilitaire;
        this.politiqueEconomique = politiqueEconomique;
        this.politiqueDiplomatique = politiqueDiplomatique;
    }

    /**
     * Construit une politique avec des valeurs par défaut.
     *
     * @param typeRegime type de régime politique
     * @param stabilite stabilité politique (0 à 100)
     */
    public Politique(String typeRegime, float stabilite) {
        this(typeRegime, stabilite, "Neutre", "Mixte", "Équilibrée");
    }

    /**
     * Retourne le type de régime politique.
     *
     * @return le type de régime
     */
    public String getTypeRegime() {
        return typeRegime;
    }

    /**
     * Retourne la stabilité politique.
     *
     * @return la stabilité (0 à 100)
     */
    public float getStabilite() {
        return stabilite;
    }

    /**
     * Retourne la politique militaire.
     *
     * @return l'orientation militaire
     */
    public String getPolitiqueMilitaire() {
        return politiqueMilitaire;
    }

    /**
     * Retourne la politique économique.
     *
     * @return l'orientation économique
     */
    public String getPolitiqueEconomique() {
        return politiqueEconomique;
    }

    /**
     * Retourne la politique diplomatique.
     *
     * @return l'orientation diplomatique
     */
    public String getPolitiqueDiplomatique() {
        return politiqueDiplomatique;
    }

    /**
     * Définit le type de régime politique.
     *
     * @param typeRegime nouveau type de régime
     */
    public void setTypeRegime(String typeRegime) {
        this.typeRegime = typeRegime;
    }

    /**
     * Définit la stabilité politique.
     *
     * @param stabilite nouvelle stabilité (0 à 100)
     */
    public void setStabilite(float stabilite) {
        this.stabilite = stabilite;
    }

    /**
     * Définit la politique militaire.
     *
     * @param politiqueMilitaire nouvelle orientation militaire
     */
    public void setPolitiqueMilitaire(String politiqueMilitaire) {
        this.politiqueMilitaire = politiqueMilitaire;
    }

    /**
     * Définit la politique économique.
     *
     * @param politiqueEconomique nouvelle orientation économique
     */
    public void setPolitiqueEconomique(String politiqueEconomique) {
        this.politiqueEconomique = politiqueEconomique;
    }

    /**
     * Définit la politique diplomatique.
     *
     * @param politiqueDiplomatique nouvelle orientation diplomatique
     */
    public void setPolitiqueDiplomatique(String politiqueDiplomatique) {
        this.politiqueDiplomatique = politiqueDiplomatique;
    }

    /**
     * Retourne une description qualitative de la stabilité politique.
     *
     * @return état de stabilité ("Très stable", "Stable", "Instable", "Très instable")
     */
    public String getEtatStabilite() {
        if (stabilite >= 75) {
            return "Très stable";
        } else if (stabilite >= 50) {
            return "Stable";
        } else if (stabilite >= 25) {
            return "Instable";
        } else {
            return "Très instable";
        }
    }

    /**
     * Retourne une représentation textuelle de la politique.
     *
     * @return une chaîne décrivant la politique
     */
    @Override
    public String toString() {
        return "Régime : " + this.getTypeRegime()
                + ", Stabilité : " + this.getStabilite() + "% (" + this.getEtatStabilite() + ")"
                + "\nPolitique Militaire : " + this.getPolitiqueMilitaire()
                + ", Politique Économique : " + this.getPolitiqueEconomique()
                + ", Politique Diplomatique : " + this.getPolitiqueDiplomatique();
    }
}