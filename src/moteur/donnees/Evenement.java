package moteur.donnees;

import java.util.Objects;

/**
 * Classe qui représente un événement
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Objects
 */
public class Evenement {

    // Attributs
    private final String nom;
    private final int anneeDebut;
    private final int anneeFin;
    
    private final Region region;
    private final String narration;
    private final String type;

    /**
     * Constructeur de la classe Evenement
     * 
     * @param nom         : Nom de l'événement
     * @param anneeDebut  : Année du début de l'événement
     * @param anneeFin    : Année de fin de l'événement 
     * @param region      : Région de l'évenement
     * @param narration   : Narration de l'événement
     * @param type        : Type d'événement
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
     * Getter de nom
     * 
     * @return Le nom de l'événement
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Getter de anneeDebut
     * 
     * @return Le debut de l'événement 
     */
    public int getAnneeDebut() {
		return anneeDebut;
	}

    /**
     * Getter de anneeFin 
     * 
     * @return La fin de l'événement 
     */
	public int getAnneeFin() {
		return anneeFin;
	}

	/**
     * Getter de region
     * 
     * @return La région de l'événement
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Getter de narration
     * 
     * @return La narration de l'événement
     */
    public String getNarration() {
        return narration;
    }

    /**
     * Getter de type
     * 
     * @return Le type de l'événement
     */
    public String getType() {
        return type;
    }

    /**
     * Code de hachage de la classe Evenement
     * 
     * @return Le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, anneeDebut, anneeFin, region, narration);
    }

    /**
     * Vérifie si deux événements sont identiques
     * 
     * @param obj : Objet à comparer
     * @return true si deux événements sont identiques, false sinon
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
        return Objects.equals(this.nom, even.nom) && this.anneeDebut == even.anneeFin && this.anneeFin == even.anneeFin &&
                Objects.equals(this.region, even.region) && Objects.equals(this.narration, even.narration);
    }

    /**
     * Affiche les informations de l'événement
     * 
     * @return L'affichage des informations
     */
    @Override
    public String toString() {
        return "Événement : \nNom : " + this.getNom() + "\nType : " + this.getType() + "\nAnnées  : " + this.getAnneeDebut() + "-" 
        		+ this.getAnneeFin() + "\nRégion : " + this.getRegion() + "\n";
    }

}