package moteur.donnees;

/**
 * Classe qui représente une armée 
 * 
 * @author Alexandre et Massinissa
 * @version 1.1
 */
public class Armee {

    // Attributs 
    private String hersir; // Chef de l'armée 
    private int nombreGuerriers;
    private int nombreNavires;
    private int techniquesMilitaire; // Nombre de techniques 
    private float etat; // En pourcentage (0 à 100%)
    
    /**
     * Constructeur de la classe Armee
     * 
     * @param nombreGuerriers : Nombre de guerriers 
     * @param nombreNavires : Nombre de navires 
     * @param chef : Chef principal de l'armée 
     * @param techniquesMilitaire : Nombre de techniques militaires acquises
     */
    public Armee(String hersir, int nombreGuerriers, int nombreNavires, int techniquesMilitaire, float etat) {
        this.hersir=hersir;
        this.nombreGuerriers=nombreGuerriers;
        this.nombreNavires=nombreNavires;
        this.techniquesMilitaire=techniquesMilitaire;
        this.etat = etat;
    }

    /**
     * Getter de hersir
     * @return le nom du hersir
     */
    public String getHersir() {
        return hersir;
    }

    /**
     * Getter de nombreGuerriers
     * @return Le nombre de guerriers
     */
    public int getNombreGuerriers() {
        return nombreGuerriers;
    }
    
    /**
     * Getter nombreNavires
     * @return Le nombre de navires
     */
    public int getNombreNavires() {
        return nombreNavires;
    }

    /**
     * Getter des techniquesmilitaire
     * @return Le nombre de techniquesmilitaire
     */
    public int getTechniquesMilitaire() {
        return techniquesMilitaire;
    }

    /**
     * Getter de etat
     * @return L'état de l'armée
     */
    public float getEtat() {
        return etat;
    }
    
    /**
     * Setter de hersir
     * @param hersir : Nouveau hersir de l'armée
     */
    public void setHersir(String hersir) {
        this.hersir = hersir;
    }

    /**
     * Setter de nombreGuerriers
     * @param hersir : le nombre de de Guerriers
     */
    public void setNombreGuerriers(int nombreGuerriers) {
        this.nombreGuerriers = nombreGuerriers;
    }

    /**
     * Setter de nombreNavires
     * @param hersir : le nombre de navires
     */
    public void setNombreNavires(int nombreNavires) {
        this.nombreNavires = nombreNavires;
    }

    /**
     * Setter de techniquesMilitaire
     * @param hersir : le nombre de techniques militaire acquises
     */
    public void setTechniquesMilitaire(int techniquesMilitaire) {
        this.techniquesMilitaire = techniquesMilitaire;
    }

    /**
     * Setter de etat 
     * @param etat : Nouvel état de l'armée 
     */
    public void setEtat(float etat) {
        this.etat = etat;
    }

    /**
     * Affiche les informations sur l'armée 
     * @return L'affichage des informations
     */
    @Override
    public String toString() {
        return "Hersir : " + this.getHersir() + ", Nombre de guerriers : " + this.getNombreGuerriers() + 
            ", Nombre de navires : " + this.getNombreNavires() + ", Techniques militaire acquises : " + 
            this.getTechniquesMilitaire() + ", État : " + this.getEtat() + "%";
    }

}
