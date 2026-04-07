package moteur.donnees;

/**
 * Représente une armée dans la simulation.
 *
 * <p>
 * Une armée est caractérisée par un chef (hersir), un effectif de guerriers,
 * une flotte de navires, un niveau de développement militaire et un état global.
 * </p>
 *
 * <ul>
 *   <li>L'état est exprimé en pourcentage entre 0 et 100.</li>
 *   <li>Les techniques militaires représentent le niveau d'évolution stratégique.</li>
 * </ul>
 *
 * @author Alexandre
 * @author Massinissa
 *
 * @version 1.2
 *
 * @see Kersir
 */
public class Armee {

    /**
     * Nom du hersir (chef de l'armée).
     */
    private Kersir hersir;

    /**
     * Nombre total de guerriers composant l'armée.
     */
    private int nombreGuerriers;

    /**
     * Nombre de navires disponibles pour l'armée.
     */
    private int nombreNavires;

    /**
     * Nombre de techniques militaires maîtrisées.
     */
    private int techniquesMilitaire;

    /**
     * État global de l'armée en pourcentage (0 à 100).
     * Représente la condition générale (moral, santé, organisation, etc.).
     */
    private float etat;

    /**
     * Construit une nouvelle armée avec les caractéristiques spécifiées.
     *
     * @param hersir nom du chef de l'armée
     * @param nombreGuerriers nombre de guerriers (doit être positif)
     * @param nombreNavires nombre de navires (doit être positif)
     * @param techniquesMilitaire nombre de techniques militaires acquises (>= 0)
     * @param etat état de l'armée en pourcentage (entre 0 et 100)
     */
    public Armee(Kersir hersir, int nombreGuerriers, int nombreNavires, int techniquesMilitaire, float etat) {
        this.hersir = hersir;
        this.nombreGuerriers = nombreGuerriers;
        this.nombreNavires = nombreNavires;
        this.techniquesMilitaire = techniquesMilitaire;
        this.etat = etat;
    }

    /**
     * Retourne le nom du hersir (chef de l'armée).
     *
     * @return le nom du hersir
     */
    public Kersir getHersir() {
        return hersir;
    }

    /**
     * Retourne le nombre de guerriers.
     *
     * @return le nombre de guerriers
     */
    public int getNombreGuerriers() {
        return nombreGuerriers;
    }

    /**
     * Retourne le nombre de navires.
     *
     * @return le nombre de navires
     */
    public int getNombreNavires() {
        return nombreNavires;
    }

    /**
     * Retourne le nombre de techniques militaires maîtrisées.
     *
     * @return le niveau de techniques militaires
     */
    public int getTechniquesMilitaire() {
        return techniquesMilitaire;
    }

    /**
     * Retourne l'état global de l'armée.
     *
     * @return l'état en pourcentage (0 à 100)
     */
    public float getEtat() {
        return etat;
    }

    /**
     * Définit un nouveau hersir pour l'armée.
     *
     * @param hersir nom du nouveau chef
     */
    public void setHersir(Kersir hersir) {
        this.hersir = hersir;
    }

    /**
     * Définit le nombre de guerriers.
     *
     * @param nombreGuerriers nouveau nombre de guerriers
     */
    public void setNombreGuerriers(int nombreGuerriers) {
        this.nombreGuerriers = nombreGuerriers;
    }

    /**
     * Définit le nombre de navires.
     *
     * @param nombreNavires nouveau nombre de navires
     */
    public void setNombreNavires(int nombreNavires) {
        this.nombreNavires = nombreNavires;
    }

    /**
     * Définit le nombre de techniques militaires.
     *
     * @param techniquesMilitaire nouveau nombre de techniques militaires
     */
    public void setTechniquesMilitaire(int techniquesMilitaire) {
        this.techniquesMilitaire = techniquesMilitaire;
    }

    /**
     * Définit l'état global de l'armée.
     *
     * @param etat nouvel état en pourcentage (0 à 100)
     */
    public void setEtat(float etat) {
        this.etat = etat;
    }

    /**
     * Retourne une représentation textuelle de l'armée.
     *
     * @return une chaîne décrivant les caractéristiques de l'armée
     */
    @Override
    public String toString() {
        return "Hersir : " + this.getHersir()
                + ", Nombre de guerriers : " + this.getNombreGuerriers()
                + ", Nombre de navires : " + this.getNombreNavires()
                + ", Techniques militaires : " + this.getTechniquesMilitaire()
                + ", État : " + this.getEtat() + "%";
    }
}