package moteur.carte;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import moteur.donnees.Region;

/**
 * Classe qui représente un continent (ou île) sur une carte.
 *
 * <p>
 * Un continent est composé de plusieurs {@link Bloc} et appartient à une {@link Region}.
 * Il possède un nom, une position pour l'affichage du nom et peut-être parcouru comme une collection
 * grâce à l'implémentation de {@link Iterable}.
 * </p>
 *
 * @author Alexandre
 * @version 1.1
 */
public class Continent implements Iterable<Bloc> {

    /**
     * Nom du continent
     */
    private String nom;

    /**
     * Région du continent
     */
    private final Region region;

    /**
     * Blocs qui le composent
     */
    private final ArrayList<Bloc> blocs = new ArrayList<>();

    /**
     * Position du nom pour l'affichage graphique
     */
    private final Point positionNom;

    /**
     * Constructeur de la classe Continent.
     *
     * @param nom          Le nom du continent ou de l'île.
     * @param positionNom  La position (coordonnées x, y) où afficher le nom du continent.
     * @param region       La {@link Region} à laquelle appartient ce continent.
     */
    public Continent(String nom, Point positionNom, Region region) {
        this.nom = nom;
        this.positionNom = positionNom;
        this.region = region;
    }

    /**
     * Retourne le nom du continent.
     *
     * @return Le nom du continent.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom du continent.
     *
     * @param nom Nouveau nom du continent.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la région à laquelle appartient ce continent.
     *
     * @return La {@link Region} du continent.
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Retourne la position pour l'affichage du nom du continent.
     *
     * @return Un {@link Point} représentant la position d'affichage du nom.
     */
    public Point getPositionNom() {
        return positionNom;
    }

    /**
     * Retourne la liste des blocs constituant ce continent.
     *
     * @return Une {@link ArrayList} de {@link Bloc}.
     */
    public ArrayList<Bloc> getBlocs() {
        return blocs;
    }

    /**
     * Retourne le nombre de blocs dans le continent.
     *
     * @return Le nombre de blocs.
     */
    public int getNbBlocs() {
        return this.blocs.size();
    }

    /**
     * Ajoute un bloc au continent.
     *
     * @param bloc Le {@link Bloc} à ajouter.
     */
    public void ajouterBloc(Bloc bloc) {
        this.blocs.add(bloc);
    }

    /**
     * Vérifie si un bloc appartient au continent.
     *
     * @param bloc Le {@link Bloc} à vérifier.
     * @return {@code true} si le bloc est présent dans le continent, {@code false} sinon.
     */
    public boolean contientBloc(Bloc bloc) {
        return this.blocs.contains(bloc);
    }

    /**
     * Retourne le code de hachage du continent.
     *
     * <p>
     * Utilisé pour comparer des continents dans des collections de type {@link java.util.HashMap}.
     * </p>
     *
     * @return Un entier représentant le code de hachage.
     */
    @Override
    public int hashCode() {
        return Objects.hash(blocs, nom, region);
    }

    /**
     * Vérifie l'égalité entre deux continents.
     *
     * <p>
     * Deux continents sont considérés égaux si leur nom, leur région et leurs blocs sont identiques.
     * </p>
     *
     * @param obj L'objet à comparer
     * @return {@code true} si les deux continents sont identiques, {@code false} sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Continent other)) {
            return false;
        }

        return Objects.equals(blocs, other.blocs)
                && Objects.equals(nom, other.nom)
                && Objects.equals(region, other.region);
    }

    /**
     * Retourne un itérateur sur les blocs du continent.
     *
     * @return Un {@link Iterator} sur les {@link Bloc} du continent.
     */
    @Override
    public Iterator<Bloc> iterator() {
        return blocs.iterator();
    }
}