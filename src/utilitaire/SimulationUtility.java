package utilitaire;

import log.LoggerUtility;
import moteur.donnees.Civilisation;
import moteur.donnees.Evenement;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * Classe utilitaire pour la simulation.
 *
 * <p>
 * Fournit des méthodes pratiques pour :
 * <ul>
 *     <li>Lire des images et des BufferedImages depuis des fichiers</li>
 *     <li>Ajouter ou supprimer des éléments dans des maps de manière sécurisée</li>
 * </ul>
 * </p>
 *
 * <p>
 * Toutes les méthodes sont statiques et peuvent être utilisées sans instancier la classe.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 */
public class SimulationUtility {

    /**
     * Logger pour enregistrer les événements et erreurs
     */
    private static final Logger logger = LoggerUtility.getLogger(SimulationUtility.class, "html");

    /**
     * Lit une image depuis un fichier.
     *
     * @param fichier le fichier image à lire
     * @return l'image lue sous forme de {@link Image}, ou {@code null} si impossible
     */
    public static Image readImage(File fichier) {
        return readBufferedImage(fichier);
    }

    /**
     * Lit une image depuis un fichier et retourne un {@link BufferedImage}.
     *
     * @param fichier le fichier image à lire
     * @return l'image lue sous forme de {@link BufferedImage}, ou {@code null} si une erreur survient
     */
    public static BufferedImage readBufferedImage(File fichier) {
        try {
            return ImageIO.read(fichier);
        } catch (IOException e) {
            logger.error("Impossible de charger l'image : " + fichier, e);
            return null;
        }
    }

    /**
     * Ajoute un élément dans une map si la clé n'existe pas déjà.
     *
     * @param key clé associée à l'élément
     * @param element élément à ajouter
     * @param map map cible
     * @param <T> type de l'élément
     */
    public static <T> void addElement(String key, T element, Map<String, T> map) {
        if (element != null && !map.containsKey(key)) {
            map.put(key, element);
        }
    }

    /**
     * Supprime un élément d'une map à partir de sa clé.
     *
     * @param map map cible
     * @param key clé de l'élément à supprimer
     * @param <T> type de l'élément
     */
    public static <T> void suppElement(Map<String, T> map, String key) {
        map.remove(key);
    }

    /**
     * Vérifie si une map contient une clé donnée.
     *
     * @param map la map dans laquelle chercher la clé
     * @param key la clé à vérifier
     * @param <T> le type des valeurs stockées dans la map
     * @return {@code true} si la clé est présente dans la map, {@code false} sinon
     */
    public static <T> boolean contientCle(Map<String, T> map, String key) {
        return map.containsKey(key);
    }

    /**
     * Ajoute un élément à une liste uniquement s'il est non nul
     * et absent de la liste.
     *
     * <p>
     * Cette méthode permet d'éviter les doublons et les valeurs nulles
     * dans une collection.
     * </p>
     *
     * @param <T>     type des éléments de la liste
     * @param liste   liste dans laquelle ajouter l'élément
     * @param element élément à ajouter
     */
    public static <T> void addElement(List<T> liste, T element) {
        if (element != null && !liste.contains(element)) {
            liste.add(element);
        }
    }

    /**
     * Met en pause le thread courant pendant une durée donnée.
     *
     * <p>
     * Si le thread est interrompu, son état d'interruption est restauré.
     * </p>
     *
     * @param temps durée de la pause en millisecondes
     */
    public static void pause(int temps) {
        try {
            Thread.sleep(temps);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Retourne l'événement actif durant l'année donnée
     *
     * @param civilisation : Civilisation
     * @param anneeSim : Année de la simulation
     * @param nvNbEvenements : Nombre d'événements
     *
     * @return L'événement actuel (null si aucun)
     */
    public static Evenement getEvenementActuel(Civilisation civilisation, int anneeSim, int nvNbEvenements) {
        for (int i = 0; i < nvNbEvenements; i++) {
            Evenement e = civilisation.getEvenement(i);
            if (e != null && e.getAnneeDebut() <= anneeSim && e.getAnneeFin() >= anneeSim) {
                return e;  // Retourne le premier événement actif trouvé
            }
        }
        return null;  // Aucun événement actif
    }

    /**
     * Limite une valeur entière dans un intervalle donné.
     *
     * @param val la valeur à limiter
     * @param min la valeur minimale autorisée
     * @param max la valeur maximale autorisée
     * @return la valeur limitée entre `min` et `max`
     */
    public static int clamp(int val, int min, int max) {
        return Math.min(Math.max(val, min), max);
    }

    /**
     * Limite une valeur flottante dans un intervalle donné.
     *
     * @param val la valeur à limiter
     * @param min la valeur minimale autorisée
     * @param max la valeur maximale autorisée
     * @return la valeur limitée entre `min` et `max`
     */
    public static float clamp(float val, float min, float max) {
        return Math.min(Math.max(val, min), max);
    }

}
