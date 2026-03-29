package utilitaire;

import log.LoggerUtility;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe utilitaire pour la simulation
 *
 * @author Alexandre
 * @version 1.0
 */
public class SimulationUtility {

    // Attributs
    private static Logger logger = LoggerUtility.getLogger(SimulationUtility.class, "html");

    /**
     * Méthode qui permet de lire une image
     *
     * @param fichier : Nom du fichier
     * @return une image
     */
    public static Image readImage(File fichier) {
        return readBufferedImage(fichier);
    }

    /**
     * Méthode qui permet de lire une image
     *
     * @param fichier : Nom du fichier
     * @return une image
     */
    public static BufferedImage readBufferedImage(File fichier) {
        try {
            return ImageIO.read(fichier);
        } catch (IOException e) {
            logger.error("Impossible de charger l'image : " + fichier);
            return null;
        }
    }

}
