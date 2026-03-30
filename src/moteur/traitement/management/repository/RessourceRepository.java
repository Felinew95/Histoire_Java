package moteur.traitement.management.repository;

import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe qui représente un répertoire qui contient des ressources
 *
 * @author Alexandre
 * @version 1.0
 */
public class RessourceRepository {

    // Attributs
    private final HashMap<String, String> map = new HashMap<String, String>();
    private static Logger logger = LoggerUtility.getLogger(RessourceRepository.class, "html");
    private static RessourceRepository instance = new RessourceRepository("src/csv/ressources.csv");

    /**
     * Constructeur de la classe RessourceRepository
     *
     * @param fichier : Nom du fichier
     */
    private RessourceRepository(String fichier) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 2) {
                    map.put(data[0].trim(), data[1].trim());
                }
            }
        } catch (IOException e) {
            logger.error("Erreur dans le chargement des ressources depuis " + fichier, e);
        }
    }

    /**
     * Getter de instance
     *
     * @return Retourne l'instance
     */
    public static RessourceRepository getInstance() {
        return instance;
    }

    /**
     * Méthode qui retourne le type de ressource en fonction de son nom
     *
     * @param nomRessource : Le nom de la ressource
     * @return Le type de ressource
     */
    public String getTypeRessource(String nomRessource) {
        return map.get(nomRessource);
    }

    /**
     *
     *
     * @return
     */
    public Set<String> getAllNomRessources() {
        return map.keySet();
    }

}
