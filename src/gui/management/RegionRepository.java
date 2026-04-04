package gui.management;

import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.awt.Point;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static utilitaire.SimulationUtility.addElement;

/**
 * Repository des régions utilisées pour l'affichage graphique.
 *
 * <p>
 * Cette classe permet de charger les positions des régions à partir
 * d'un fichier CSV et de les stocker sous forme de paires (nom, position).
 * Chaque région est associée à un {@link Point} représentant ses coordonnées
 * (ligne, colonne) dans l'interface graphique lors de l'affichage de la carte
 * de l'europe.
 * </p>
 *
 * <p>
 * Cette classe suit le design pattern <b>Singleton</b> afin de garantir
 * un accès unique aux données des régions dans toute l'application.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public class RegionRepository {

    /**
     * Map contenant les régions avec leur nom en clé
     * et leur position graphique en valeur.
     */
    private final Map<String, Point> regions = new HashMap<>();

    /**
     * Logger pour enregistrer les messages d'erreur ou d'information.
     */
    private static final Logger logger =
            LoggerUtility.getLogger(RegionRepository.class, "html");

    /**
     * Instance unique du repository (Singleton).
     */
    private static final RegionRepository instance =
            new RegionRepository("src/csv/civilisation/regions.csv");

    /**
     * Constructeur privé qui charge les données depuis un fichier CSV.
     *
     * @param fichier chemin du fichier CSV contenant les régions
     */
    private RegionRepository(String fichier) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int ligne = Integer.parseInt(data[2]);
                int colonne = Integer.parseInt(data[1]);

                addElement(data[0], new Point(ligne, colonne), regions);
            }
        } catch (IOException e) {
            logger.error("Erreur dans le chargement des ressources depuis " + fichier, e);
        }
    }

    /**
     * Retourne l'instance unique du repository.
     *
     * @return instance de {@code RegionRepository}
     */
    public static RegionRepository getInstance() {
        return instance;
    }

    /**
     * Retourne la position graphique d'une région à partir de son nom.
     *
     * @param nom nom de la région
     * @return position sous forme de {@link Point} (ligne, colonne),
     *         ou {@code null} si la région n'existe pas
     */
    public Point getPosition(String nom) {
        return regions.get(nom);
    }

    /**
     * Retourne l'ensemble des noms des régions disponibles.
     *
     * @return un {@link Set} contenant tous les noms de régions
     */
    public Set<String> getAllNomRegions() {
        return regions.keySet();
    }

}