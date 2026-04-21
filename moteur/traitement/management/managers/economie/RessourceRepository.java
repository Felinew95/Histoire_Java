package moteur.traitement.management.managers.economie;

import log.LoggerUtility;
import org.apache.log4j.Logger;

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
 * Classe représentant un répertoire de ressources.
 *
 * <p>
 * Cette classe charge les ressources depuis un fichier CSV et fournit
 * un accès centralisé à leurs types via un singleton.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public class RessourceRepository {

    /**
     * Map associant le nom d'une ressource à son type.
     */
    private final Map<String, String> map = new HashMap<>();

    /**
     * Logger pour enregistrer les messages d'erreur ou d'information.
     */
    private static final Logger logger = LoggerUtility.getLogger(RessourceRepository.class, "html");

    /**
     * Instance unique de RessourceRepository (Singleton) initialisée depuis le fichier CSV.
     */
    private static final RessourceRepository instance = new RessourceRepository("src/csv/civilisation/ressources.csv");

    /**
     * Constructeur privé de la classe RessourceRepository.
     *
     * <p>
     * Charge les ressources depuis un fichier CSV. Chaque ligne doit
     * contenir au moins deux colonnes séparées par un point-virgule :
     * le nom de la ressource et son type.
     * </p>
     *
     * @param fichier chemin du fichier CSV contenant les ressources
     */
    private RessourceRepository(String fichier) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 2) {
                    addElement(data[0].trim(), data[1].trim(), map);
                    logger.info("Ressource crée : nom=" + data[0].trim() + ", type=" + data[1].trim());
                }
            }
        } catch (IOException e) {
            logger.error("Erreur dans le chargement des ressources depuis " + fichier, e);
        }
    }

    /**
     * Retourne l'instance unique de RessourceRepository (singleton).
     *
     * @return l'instance unique de RessourceRepository
     */
    public static RessourceRepository getInstance() {
        return instance;
    }

    /**
     * Retourne le type d'une ressource en fonction de son nom.
     *
     * @param nomRessource le nom de la ressource
     * @return le type associé à la ressource, ou {@code null} si elle n'existe pas
     */
    public String getTypeRessource(String nomRessource) {
        return map.get(nomRessource);
    }

    /**
     * Retourne l'ensemble des noms de toutes les ressources.
     *
     * @return un {@link Set} contenant tous les noms de ressources
     */
    public Set<String> getAllNomRessources() {
        return map.keySet();
    }

}