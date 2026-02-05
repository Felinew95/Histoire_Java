package moteur.donnees;

/**
 * Classe qui représente une armée 
 * @author Massinissa
 * @version 1.0
 */
public class Armee {

    // Attributs 
    private String hersir;
    private int nombreGuerriers;
    private int nombreNavires;
    private int techniquesMilitaire;
    
    /**
     * Constructeur de la classe Armee
     * @param nombreGuerriers : Nombre de guerriers 
     * @param nombreNavires : Nombre de navires 
     * @param chef : Chef principal de l'armée 
     * @param techniquesMilitaire : Nombre de techniques militaires acquises
     */
    public Armee(String hersir, int nombreGuerriers, int nombreNavires, int techniquesMilitaire) {
        this.hersir=hersir;
        this.nombreGuerriers=nombreGuerriers;
        this.nombreNavires=nombreNavires;
        this.techniquesMilitaire=techniquesMilitaire;
    }

    /**
     * Getter de hersir
     * @return
     */
    public String getHersir(){
        return hersir;
    }

    /**
     * Getter de nombreGuerriers
     * @return Le nombre de guerriers
     */
    public int getNombreGuerriers(){
        return nombreGuerriers;
    }
    
    /**
     * Getter nombreNavires
     * @return le nombre de navires
     */
    public int getNombreNavires() {
        return nombreNavires;
    }

    /**
     * Getter des techniquesmilitaire
     * @return le nombre de techniquesmilitaire
     */
    public int getTechniquesMilitaire() {
        return techniquesMilitaire;
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
     * @param hersir : le nombre de techniqueMilitaire acquises
     */
    public void setTechniquesMilitaire(int techniquesMilitaire) {
        this.techniquesMilitaire = techniquesMilitaire;
    }

    /**
     * Ajoute des guerriers 
     * @param nombre : Nombre de nouveaux guerriers 
     */
    public void ajouterGuerriers(int nombre) {
        this.nombreGuerriers+=nombre;
    }

    /**
     * Ajoute des navires
     * @param nombre : Nombre de nouveaux navires 
     */
    public void ajouterNavires(int nombre) {
        this.nombreNavires+=nombre;
    }
    
    /**
     * Ajoute des techniques acquises
     * @param nombre : Nombre de nouvelles techniques acquises 
     */
    public void ajouterTechniquesMilitaires(int nombre) {
        this.techniquesMilitaire += nombre;
    }

    /**
     * Affiche les informations sur l'armée 
     * @return L'affichage des informations
     */
    @Override
    public String toString() {
        return "Hersir : " + this.getHersir() + ", Nombre de guerriers : " + this.getNombreGuerriers() + 
            ", Nombre de navires : " + this.getNombreNavires() + ", Techniques militaire acquises : " + 
            this.getTechniquesMilitaire();
    }

}
