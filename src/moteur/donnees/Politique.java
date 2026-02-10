package moteur.donnees;

public class Politique {
    
    // Attributs
    private String type_regime; // Type de régime politique (Monarchie, République, etc.)
    private float stabilite; // Stabilité politique en pourcentage (0 à 100%)
    private String politiqueMilitaire; // Politique militaire (Offensive, Défensive, Neutre)
    private String politiqueEconomique; // Politique économique (Libre-échange, Protectionnisme, etc.)
    private String politiqueDiplomatique; // Politique diplomatique (Expansionniste, Pacifique, etc.)
    
    /**
     * Constructeur de la classe Politique
     * 
     * @param type_regime : Type de régime politique
     * @param stabilite : Stabilité politique
     * @param politiqueMilitaire : Politique militaire
     * @param politiqueEconomique : Politique économique
     * @param politiqueDiplomatique : Politique diplomatique
     */
    public Politique(String type_regime, float stabilite, String politiqueMilitaire, 
                    String politiqueEconomique, String politiqueDiplomatique) {
        this.type_regime = type_regime;
        this.stabilite = stabilite;
        this.politiqueMilitaire = politiqueMilitaire;
        this.politiqueEconomique = politiqueEconomique;
        this.politiqueDiplomatique = politiqueDiplomatique;
    }
    
    /**
     * Constructeur simplifié de la classe Politique
     * 
     * @param type_regime : Type de régime politique
     * @param stabilite : Stabilité politique
     */
    public Politique(String type_regime, float stabilite) {
        this(type_regime, stabilite, "Neutre", "Mixte", "Équilibrée");
    }
    
    /**
     * Getter de type_regime
     * @return Le type de régime
     */
    public String getType_regime() {
        return type_regime;
    }
    
    /**
     * Getter de stabilite
     * @return La stabilité politique
     */
    public float getStabilite() {
        return stabilite;
    }
    
    /**
     * Getter de politiqueMilitaire
     * @return La politique militaire
     */
    public String getPolitiqueMilitaire() {
        return politiqueMilitaire;
    }
    
    /**
     * Getter de politiqueEconomique
     * @return La politique économique
     */
    public String getPolitiqueEconomique() {
        return politiqueEconomique;
    }
    
    /**
     * Getter de politiqueDiplomatique
     * @return La politique diplomatique
     */
    public String getPolitiqueDiplomatique() {
        return politiqueDiplomatique;
    }
    
    /**
     * Setter de type_regime
     * @param type_regime : Nouveau type de régime
     */
    public void setType_regime(String type_regime) {
        this.type_regime = type_regime;
    }
    
    /**
     * Setter de stabilite
     * @param stabilite : Nouvelle stabilité
     */
    public void setStabilite(float stabilite) {
        this.stabilite = stabilite;
    }
    
    /**
     * Setter de politiqueMilitaire
     * @param politiqueMilitaire : Nouvelle politique militaire
     */
    public void setPolitiqueMilitaire(String politiqueMilitaire) {
        this.politiqueMilitaire = politiqueMilitaire;
    }
    
    /**
     * Setter de politiqueEconomique
     * @param politiqueEconomique : Nouvelle politique économique
     */
    public void setPolitiqueEconomique(String politiqueEconomique) {
        this.politiqueEconomique = politiqueEconomique;
    }
    
    /**
     * Setter de politiqueDiplomatique
     * @param politiqueDiplomatique : Nouvelle politique diplomatique
     */
    public void setPolitiqueDiplomatique(String politiqueDiplomatique) {
        this.politiqueDiplomatique = politiqueDiplomatique;
    }
    
    /**
     * Augmente la stabilité politique
     * @param valeur : Valeur à ajouter
     */
    public void augmenterStabilite(float valeur) {
        this.stabilite += valeur;
        if (this.stabilite > 100) {
            this.stabilite = 100;
        }
    }
    
    /**
     * Diminue la stabilité politique
     * @param valeur : Valeur à retirer
     */
    public void diminuerStabilite(float valeur) {
        this.stabilite -= valeur;
        if (this.stabilite < 0) {
            this.stabilite = 0;
        }
    }
    
    /**
     * Retourne l'état de la stabilité
     * @return L'état de la stabilité
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
     * Affiche les informations de la politique
     * @return L'affichage des informations
     */
    @Override
    public String toString() {
        return "Régime : " + this.getType_regime() + 
               ", Stabilité : " + this.getStabilite() + "% (" + this.getEtatStabilite() + ")" +
               "\nPolitique Militaire : " + this.getPolitiqueMilitaire() +
               ", Politique Économique : " + this.getPolitiqueEconomique() +
               ", Politique Diplomatique : " + this.getPolitiqueDiplomatique();
    }
}
