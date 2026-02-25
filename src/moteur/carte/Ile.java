package moteur.carte;

import java.util.ArrayList;

import moteur.donnees.simulation.Region;

/**
 * Classe qui représente une ile
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Region
 * @see Bloc
 */
public class Ile {

    // Attributs
    private String nom;
    private final ArrayList<Bloc> blocs = new ArrayList<>();
    private final Region region;

    /**
     * Constructeur de la classe Ile
     * 
     * @param nom    : Nom de l'ile
     * @param region : Région
     */
    public Ile(String nom, Region region) {
        this.nom = nom;
        this.region = region;
    }

    /**
     * Getter de nom
     * 
     * @return Le nom de l'ile
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de region
     * 
     * @return La région
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Getter de blocs
     * 
     * @return La liste des blocs
     */
    public ArrayList<Bloc> getBlocs() {
        return blocs;
    }

    /**
     * Setter de nom
     * 
     * @param nom : Nouveau nom de l'ile
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Ajoute un bloc
     * 
     * @param bloc : Un bloc
     */
    public void ajouterBloc(Bloc bloc) {
        this.blocs.add(bloc);
    }

}
