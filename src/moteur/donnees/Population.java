package moteur.donnees;

/**
 * Classe qui représente une population
 * 
 * @author Alexandre
 * @version 1.0
 */
public class Population {

    // Attributs
    private int nbHabitants; // Nombre supérieur ou égal à 0
    private float ageMoyen; // Age supérieur à 0
    private String sexeMajoritaire; // HOMME ou FEMME

    private float tauxNaissance = 0.1f;
    private float tauxDeces = 0.1f;

    /**
     * Constructeur de la classe Population
     * 
     * @param nbHabitants     : Nombre d'habitant
     * @param ageMoyen        : Age moyen de la population
     * @param sexeMajoritaire : Sexe majoritaire de la population
     */
    public Population(int nbHabitants, float ageMoyen, String sexeMajoritaire) {
        this.nbHabitants = nbHabitants;
        this.ageMoyen = ageMoyen;
        this.sexeMajoritaire = sexeMajoritaire;
    }

    /**
     * Getter de nb_habitants
     * 
     * @return Le nombre d'habitants
     */
    public int getNbHabitants() {
        return nbHabitants;
    }

    /**
     * Getter de age_moyen
     * 
     * @return l'age moyen de la population
     */
    public float getAgeMoyen() {
        return ageMoyen;
    }

    /**
     * Getter de sexe_majoritaire
     * 
     * @return Le sexe majoritaire
     */
    public String getSexeMajoritaire() {
        return sexeMajoritaire;
    }

    public float getTauxNaissance() {
        return tauxNaissance;
    }

    public float getTauxDeces() {
        return tauxDeces;
    }

    /**
     * Setter de nb_habitants
     * 
     * @param nb_habitants : Nouveau nombre d'habitants
     */
    public void setNbHabitants(int nb_habitants) {
        this.nbHabitants = nb_habitants;
    }

    /**
     * Setter de age_moyen
     * 
     * @param ageMoyen : Nouvel age moyen de la population
     */
    public void setAgeMoyen(float ageMoyen) {
        this.ageMoyen = ageMoyen;
    }

    /**
     * Setter de sexe_majoritaire
     * 
     * @param sexe_majoritaire : Nouveau sexe majoritaire
     */
    public void setSexeMajoritaire(String sexe_majoritaire) {
        this.sexeMajoritaire = sexe_majoritaire;
    }

    /**
     * Setter de tauxNaissance
     * 
     * @param tauxNaissance : Nouveau taux de naissance
     */
    public void setTauxNaissance(float tauxNaissance) {
        this.tauxNaissance = tauxNaissance;
    }

    /**
     * Setter de tauxDeces
     * 
     * @param tauxDeces : Le nouveau taux de décès de la population
     */
    public void setTauxDeces(float tauxDeces) {
        this.tauxDeces = tauxDeces;
    }

    /**
     * Affiche les informations de la population
     * 
     * @return l'affichage des informations
     */
    @Override
    public String toString() {
        return "Nombre d'habitants : " + this.getNbHabitants() + ", Âge moyen : " + this.getAgeMoyen()
                + ", Sexe majoritaire : " + this.getSexeMajoritaire();
    }

}
