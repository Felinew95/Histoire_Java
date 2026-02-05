package moteur.donnees;

/**
 * Classe qui représente une population 
 * @author Alexandre 
 * @version 1.0 
 */
public class Population {
    
    // Attributs 
    private int nb_habitants;
    private float age_moyen;
    private String sexe_majoritaire;

    /**
     * Constructeur de la classe Population 
     * @param nb_habitants : Nombre d'habitant 
     * @param age_moyen : Age moyen de la population
     * @param sexe_majoritaire : Sexe majoritaire de la population 
     */
    public Population(int nb_habitants, float age_moyen, String sexe_majoritaire) {
        this.nb_habitants = nb_habitants;
        this.age_moyen = age_moyen;
        this.sexe_majoritaire = sexe_majoritaire;
    }

    /**
     * Getter de nb_habitants
     * @return Le nombre d'habitants 
     */
    public int getNb_habitants() {
        return nb_habitants;
    }

    /**
     * Getter de age_moyen
     * @return l'age moyen de la population 
     */
    public float getAge_moyen() {
        return age_moyen;
    }

    /**
     * Getter de sexe_majoritaire
     * @return Le sexe majoritaire
     */
    public String getSexe_majoritaire() {
        return sexe_majoritaire;
    }

    /**
     * Setter de nb_habitants
     * @param nb_habitants : Nouveau nombre d'habitants 
     */
    public void setNb_habitants(int nb_habitants) {
        this.nb_habitants = nb_habitants;
    }

    /**
     * Setter de age_moyen
     * @param age_moyen : Nouvel age moyen de la population 
     */
    public void setAge_moyen(float age_moyen) {
        this.age_moyen = age_moyen;
    }

    /**
     * Setter de sexe_majoritaire 
     * @param sexe_majoritaire : Nouveau sexe majoritaire 
     */
    public void setSexe_majoritaire(String sexe_majoritaire) {
        this.sexe_majoritaire = sexe_majoritaire;
    }

    /**
     * Ajoute des habitants 
     * @param nombre : Le nombre d'habitants à ajouter
     */
    public void ajouterHabitants(int nombre) {
        this.nb_habitants+=nombre;
    }

    /**
     * Affiche les informations de la population 
     * @return l'affichage des informations
     */
    @Override
    public String toString() {
        return "Nombre d'habitants : " + this.getNb_habitants() + ", Âge moyen : " + this.getAge_moyen()
            + ", Sexe majoritaire : "+ this.getSexe_majoritaire();
    }

}
