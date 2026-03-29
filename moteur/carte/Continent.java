package moteur.carte;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import moteur.donnees.Region;

/**
 * Classe qui représente une ile
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Region
 * @see Bloc
 * @see Iterable
 */
public class Continent implements Iterable<Bloc> {

    // Attributs
    private String nom;
    private final Region region;
    
    private final ArrayList<Bloc> blocs = new ArrayList<>();
    private final Point positionNom;

    /**
     * Constructeur de la classe Ile
     * 
     * @param nom      : Nom de l'ile
     * @param positionNom : Position de l'ile pour affichage du nom
     * @param region   : Région
     */
    public Continent(String nom, Point positionNom, Region region) {
        this.nom = nom;
        this.positionNom = positionNom;
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
     * Getter de position 
     * 
	 * @return La position
	 */
	public Point getPositionNom() {
		return positionNom;
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
     * Méthode qui calcul le nombre de blocs de l'ile 
     * 
     * @return Le nombre de blocs
     */
    public int getNbBlocs() {
    	return this.blocs.size();
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
    
    /**
     * Vérifie si un bloc existe dans l'ile 
     * 
     * @param bloc : Bloc à vérifier 
     * 
     * @return true si un bloc existe dans l'ile, false sinon 
     */
    public boolean contientBloc(Bloc bloc) {
    	return this.blocs.contains(bloc);
    }

    /**
     * Retourne le code de hachage de la classe Ile 
     * 
     * @return Le code de hachage 
     */
	@Override
	public int hashCode() {
		return Objects.hash(blocs, nom, region);
	}

	/**
	 * Vérifie si deux iles sont identiques 
	 * 
	 * @return true si deux iles sont identiques, false sinon
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Continent)) {
			return false;
		}
		
		Continent other = (Continent) obj;
		return Objects.equals(blocs, other.blocs) && Objects.equals(nom, other.nom)
				&& Objects.equals(region, other.region);
	}

	@Override
	public Iterator<Bloc> iterator() {
		return blocs.iterator();
	}
   
}
