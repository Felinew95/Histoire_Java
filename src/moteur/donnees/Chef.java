package moteur.donnees;

import java.util.Objects;

/**
 * Classe abstraite représentant un chef au sein d'une civilisation.
 *
 * <p>
 * Un chef est défini par son nom ainsi que par sa période de règne,
 * caractérisée par une année de début et une année de fin.
 * </p>
 *
 * <p>
 * Cette classe est destinée à être étendue par des types de chefs
 * spécifiques.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public abstract class Chef {

    /**
     * Nom du chef
     */
    private final String nom;

    /**
     * Année de début du règne
     */
    private final int anneeDebut;

    /**
     * Année de fin du règne
     */
    private final int anneeFin;

    /**
     * Constructeur d'un chef.
     *
     * @param nom nom du chef
     * @param anneeDebut année de début du règne
     * @param anneeFin année de fin du règne
     */
    public Chef(String nom, int anneeDebut, int anneeFin) {
        this.nom = nom;
        this.anneeDebut = anneeDebut;
        this.anneeFin = anneeFin;
    }

    /**
     * Retourne le nom du chef.
     *
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne l'année de début du règne.
     *
     * @return l'année de début
     */
    public int getAnneeDebut() {
        return anneeDebut;
    }

    /**
     * Retourne l'année de fin du règne.
     *
     * @return l'année de fin
     */
    public int getAnneeFin() {
        return anneeFin;
    }

    /**
     * Compare ce chef avec un autre objet.
     * Deux chefs sont considérés égaux s'ils ont le même nom
     * et la même période de règne.
     *
     * @param o objet à comparer
     * @return true si les chefs sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Chef)) return false;
        Chef chef = (Chef) o;
        return anneeDebut == chef.anneeDebut
                && anneeFin == chef.anneeFin
                && Objects.equals(nom, chef.nom);
    }

    /**
     * Calcule le code de hachage du chef.
     *
     * @return le hashCode basé sur le nom et la période de règne
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, anneeDebut, anneeFin);
    }

    /**
     * Retourne une représentation textuelle du chef.
     *
     * @return une chaîne décrivant le chef
     */
    @Override
    public String toString() {
        return "Chef{ nom='" + nom + "', anneeDebut=" + anneeDebut
                + ", anneeFin=" + anneeFin + '}';
    }

}