package moteur.donnees;

/**
 * Représente la population d'une civilisation.
 *
 * <p>
 * Une population est caractérisée par un nombre d'habitants,
 * un âge moyen, un sexe majoritaire ainsi que des taux de naissance
 * et de décès permettant de modéliser son évolution.
 * </p>
 *
 * <p>
 * Contraintes :
 * <ul>
 *   <li>Le nombre d'habitants est supérieur ou égal à 0</li>
 *   <li>L'âge moyen est strictement positif</li>
 *   <li>Les taux sont compris entre 0 et 1</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @version 1.1
 */
public class Population {

    /**
     * Nombre total d'habitants (>= 0).
     */
    private int nbHabitants;

    /**
     * Âge moyen de la population (> 0).
     */
    private float ageMoyen;

    /**
     * Sexe majoritaire (ex : "HOMME" ou "FEMME").
     */
    private String sexeMajoritaire;

    /**
     * Taux de naissance (entre 0 et 1).
     */
    private float tauxNaissance = 0.1f;

    /**
     * Taux de décès (entre 0 et 1).
     */
    private float tauxDeces = 0.1f;

    /**
     * Construit une population avec les caractéristiques spécifiées.
     *
     * @param nbHabitants nombre d'habitants (>= 0)
     * @param ageMoyen âge moyen (> 0)
     * @param sexeMajoritaire sexe majoritaire
     */
    public Population(int nbHabitants, float ageMoyen, String sexeMajoritaire) {
        this.nbHabitants = nbHabitants;
        this.ageMoyen = ageMoyen;
        this.sexeMajoritaire = sexeMajoritaire;
    }

    /**
     * Retourne le nombre d'habitants.
     *
     * @return le nombre d'habitants
     */
    public int getNbHabitants() {
        return nbHabitants;
    }

    /**
     * Retourne l'âge moyen de la population.
     *
     * @return l'âge moyen
     */
    public float getAgeMoyen() {
        return ageMoyen;
    }

    /**
     * Retourne le sexe majoritaire.
     *
     * @return le sexe majoritaire
     */
    public String getSexeMajoritaire() {
        return sexeMajoritaire;
    }

    /**
     * Retourne le taux de naissance.
     *
     * @return le taux (entre 0 et 1)
     */
    public float getTauxNaissance() {
        return tauxNaissance;
    }

    /**
     * Retourne le taux de décès.
     *
     * @return le taux (entre 0 et 1)
     */
    public float getTauxDeces() {
        return tauxDeces;
    }

    /**
     * Définit le nombre d'habitants.
     *
     * @param nbHabitants nouveau nombre (>= 0)
     */
    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    /**
     * Définit l'âge moyen.
     *
     * @param ageMoyen nouvel âge moyen (> 0)
     */
    public void setAgeMoyen(float ageMoyen) {
        this.ageMoyen = ageMoyen;
    }

    /**
     * Définit le sexe majoritaire.
     *
     * @param sexeMajoritaire nouveau sexe majoritaire
     */
    public void setSexeMajoritaire(String sexeMajoritaire) {
        this.sexeMajoritaire = sexeMajoritaire;
    }

    /**
     * Définit le taux de naissance.
     *
     * @param tauxNaissance nouveau taux (entre 0 et 1)
     */
    public void setTauxNaissance(float tauxNaissance) {
        this.tauxNaissance = tauxNaissance;
    }

    /**
     * Définit le taux de décès.
     *
     * @param tauxDeces nouveau taux (entre 0 et 1)
     */
    public void setTauxDeces(float tauxDeces) {
        this.tauxDeces = tauxDeces;
    }

    /**
     * Retourne une représentation textuelle de la population.
     *
     * @return une chaîne décrivant la population
     */
    @Override
    public String toString() {
        return "Population : " + this.getNbHabitants()
                + " habitants, Âge moyen : " + this.getAgeMoyen()
                + ", Sexe majoritaire : " + this.getSexeMajoritaire();
    }
}