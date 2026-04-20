package moteur.donnees;

import java.util.Objects;

/**
 * Représente un événement survenant dans la simulation.
 *
 * <p>
 * Un événement est caractérisé par un nom, une période (année de début et de fin),
 * une région concernée, une narration descriptive et un type.
 * </p>
 *
 * <p>
 * Les événements permettent de simuler des faits historiques ou dynamiques
 * (guerre, découverte, crise, etc.).
 * </p>
 *
 * @author Alexandre
 * @version 1.1
 */
public class Evenement {

    /**
     * Nom de l'événement.
     */
    private final String nom;

    /**
     * Année de début de l'événement.
     */
    private final int anneeDebut;

    /**
     * Année de fin de l'événement.
     */
    private final int anneeFin;

    /**
     * Région concernée par l'événement.
     */
    private final Region region;

    /**
     * Description narrative de l'événement.
     */
    private final String narration;

    /**
     * Type d'événement (ex : guerre, économie, religion).
     */
    private final String type;

    /**
     * Construit un événement avec les caractéristiques spécifiées.
     *
     * @param nom nom de l'événement
     * @param anneeDebut année de début
     * @param anneeFin année de fin
     * @param region région concernée
     * @param narration description de l'événement
     * @param type type d'événement
     */
    public Evenement(String nom, int anneeDebut, int anneeFin, Region region, String narration, String type) {
        this.nom = nom;
        this.anneeDebut = anneeDebut;
        this.anneeFin = anneeFin;
        this.region = region;
        this.narration = narration;
        this.type = type;
    }

    /**
     * Retourne le nom de l'événement.
     *
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne l'année de début.
     *
     * @return l'année de début
     */
    public int getAnneeDebut() {
        return anneeDebut;
    }

    /**
     * Retourne l'année de fin.
     *
     * @return l'année de fin
     */
    public int getAnneeFin() {
        return anneeFin;
    }

    /**
     * Retourne la région concernée.
     *
     * @return la région
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Retourne la narration de l'événement.
     *
     * @return la description
     */
    public String getNarration() {
        return narration;
    }

    /**
     * Retourne le type d'événement.
     *
     * @return le type
     */
    public String getType() {
        return type;
    }

    /**
     * Calcule le code de hachage de l'événement.
     *
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, anneeDebut, anneeFin, region, type);
    }

    /**
     * Compare cet événement à un autre objet.
     * <p>
     * Deux événements sont considérés égaux s'ils ont le même nom,
     * la même période et la même région.
     * </p>
     *
     * @param obj objet à comparer
     * @return true si les événements sont équivalents
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Evenement)) {
            return false;
        }
        
        Evenement even = (Evenement) obj;
        return Objects.equals(this.nom, even.nom)
                && this.anneeDebut == even.anneeDebut
                && this.anneeFin == even.anneeFin
                && Objects.equals(this.region, even.region)
                && Objects.equals(this.type, even.type);
    }

    /**
     * Retourne une représentation textuelle de l'événement.
     *
     * @return une chaîne décrivant l'événement
     */
    @Override
    public String toString() {
        return "Événement : " + this.getNom()
                + "\nType : " + this.getType()
                + "\nPériode : " + this.getAnneeDebut() + " - " + this.getAnneeFin()
                + "\n" + this.getRegion()
                + "\nDescription : " + this.getNarration();
    }
}