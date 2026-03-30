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
 * Classe qui permet de construire la carte
 *
 * @author Alexandre
 * @version 1.0
 */
public class MapFactory {

    // Attributs
    private final static Logger logger = LoggerUtility.getLogger(SimFactory.class, "html");

    /**
     * Méthode qui construit un bloc pour la carte
     *
     * @param ligne : Ligne du bloc
     * @param colonne : Colonne du bloc
     * @param typeBloc : Type du bloc
     * @return Un bloc pour la carte
     */
    public static Bloc buildBloc(int ligne, int colonne, String typeBloc) {
        logger.debug("Construction d'un bloc : ligne=" + ligne + ", colonne=" + colonne + ", type=" + typeBloc);
        return new Bloc(ligne, colonne, typeBloc);
    }

    /**
     * Méthode qui construit la carte de la simulation
     *
     * @return La carte de la simulation
     */
    public static Carte buildCarte() {
        logger.info("Construction de la carte : " + SimConfig.NOMBRE_LIGNES + " lignes x " + SimConfig.NOMBRE_COLONNES + " colonnes");
        return new Carte(SimConfig.NOMBRE_LIGNES, SimConfig.NOMBRE_COLONNES);
    }

    /**
     * Méthode qui construit une ile
     *
     * @param nom      : Nom de l'ile
     * @param position : Position de l'ile pour affichage du nom
     * @param region   : Région
     *
     * @return Une ile
     */
    public static Continent buildIle(String nom, Point position, Region region) {
        logger.info("Construction de l'île : nom=" + nom + ", position=" + position + ", région=" + region.getNom());
        return new Continent(nom, position, region);
    }

}
