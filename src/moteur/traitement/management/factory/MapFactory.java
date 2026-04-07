package moteur.traitement.management.factory;

import config.SimConfig;
import log.LoggerUtility;
import moteur.carte.Bloc;
import moteur.carte.Carte;
import moteur.carte.Continent;
import moteur.donnees.Region;
import org.apache.log4j.Logger;

import java.awt.Point;

/**
 * Classe utilitaire pour la construction de la {@link Carte}, des {@link Continent} et
 * des {@link Bloc} de la simulation.
 *
 * <p>
 * Cette classe centralise la création des éléments de la carte et fournit
 * des méthodes statiques pour générer :
 * <ul>
 *   <li>Des blocs individuels via {@link #buildBloc(int, int, String)}</li>
 *   <li>La carte complète via {@link #buildCarte()}</li>
 *   <li>Des îles ({@link Continent}) avec position et région associée via {@link #buildIle(String, Point, Region)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * La classe utilise un {@link Logger} HTML pour tracer la création des blocs,
 * des îles et de la carte, facilitant le suivi du chargement lors de la simulation.
 * </p>
 *
 * @author Alexandre
 * @version 1.1
 *
 * @see Carte
 * @see Continent
 * @see Bloc
 * @see Region
 */
public class MapFactory {

    /**
     * Logger HTML pour suivre la construction des éléments de la carte
     */
    private final static Logger logger = LoggerUtility.getLogger(SimFactory.class, "html");

    /**
     * Construit un {@link Bloc} de la carte.
     *
     * @param ligne Ligne du bloc
     * @param colonne Colonne du bloc
     * @param typeBloc Type du bloc
     * @return Un bloc positionné à la ligne et colonne spécifiées
     */
    public static Bloc buildBloc(int ligne, int colonne, String typeBloc) {
        logger.debug("Construction d'un bloc : ligne=" + ligne + ", colonne=" + colonne + ", type=" + typeBloc);
        return new Bloc(ligne, colonne, typeBloc);
    }

    /**
     * Construit une {@link Carte} pour la simulation avec les dimensions
     * définies dans {@link SimConfig}.
     *
     * @return La carte initialisée avec le nombre de lignes et de colonnes configuré
     */
    public static Carte buildCarte() {
        logger.info("Construction de la carte : " + SimConfig.NOMBRE_LIGNES + " lignes x " + SimConfig.NOMBRE_COLONNES + " colonnes");
        return new Carte(SimConfig.NOMBRE_LIGNES, SimConfig.NOMBRE_COLONNES);
    }

    /**
     * Construit un {@link Continent} (île) pour la simulation.
     *
     * @param nom Nom de l'île
     * @param position Position de l'île pour l'affichage du nom sur la carte
     * @param region Région associée à l'île
     * @return L'île initialisée avec le nom, la position et la région
     */
    public static Continent buildIle(String nom, Point position, Region region) {
        logger.info("Construction de l'île : nom=" + nom + ", position=" + position + ", région=" + region.getNom());
        return new Continent(nom, position, region);
    }

}