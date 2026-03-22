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

    /**
     * Constructeur de la classe Population
     * 
     * @param nb_habitants     : Nombre d'habitant
     * @param age_moyen        : Age moyen de la population
     * @param sexe_majoritaire : Sexe majoritaire de la population
     */
    public Population(int nb_habitants, float age_moyen, String sexe_majoritaire) {
        this.nbHabitants = nb_habitants;
        this.ageMoyen = age_moyen;
        this.sexeMajoritaire = sexe_majoritaire;
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

    /**
     * Setter de nb_habitants
     * 
     * @param nb_habitants : Nouveau nombre d'habitants
     */
    public void setNb_habitants(int nb_habitants) {
        this.nbHabitants = nb_habitants;
    }

    /**
     * Setter de age_moyen
     * 
     * @param age_moyen : Nouvel age moyen de la population
     */
    public void setAge_moyen(float age_moyen) {
        this.ageMoyen = age_moyen;
    }

    /**
     * Setter de sexe_majoritaire
     * 
     * @param sexe_majoritaire : Nouveau sexe majoritaire
     */
    public void setSexe_majoritaire(String sexe_majoritaire) {
        this.sexeMajoritaire = sexe_majoritaire;
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
