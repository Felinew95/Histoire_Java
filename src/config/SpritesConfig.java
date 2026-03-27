package config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import log.LoggerUtility;

/**
 * Classe de configuration des sprites
 * 
 * @author Tauseef
 * @version 1.0
 */
public class SpritesConfig {

	// Attributs
	private static Logger logger = LoggerUtility.getLogger(SpritesConfig.class, "html");
	private final HashMap<String, BufferedImage> spritesSet = new HashMap<>();
	private static final SpritesConfig instance = new SpritesConfig("src/images");

	/**
	 * Constructeur de la classe SpriteConfig
	 * 
	 * @param chemin : Chemin du dossier avec les images
	 */
	private SpritesConfig(String chemin) {
		creerSpritesSet(new File(chemin));
	}

	/**
	 * Méthode qui extrait les images
	 */
	private void creerSpritesSet(File dossier) {
		File[] fichiers = dossier.listFiles();
		if (fichiers != null) {
			for (File fichier : fichiers) {
				ajouterSprite(fichier);
			}
		}
	}

	/**
	 * Ajoute un sprite au manager
	 *
	 * @param fichier : Nom du fichier
	 */
	private void ajouterSprite(File fichier) {
		String nomFichier = fichier.getName();
		if (fichier.isFile() && (nomFichier.endsWith(".png") || nomFichier.endsWith(".jpeg") || nomFichier.endsWith(".jpg"))) {
			ajouterImage(fichier, nomFichier);
		}
	}

	/**
	 * Méthode qui ajoute une image
	 * 
	 * @param fichier : Un fichier
	 * @param nomFichier : Nom du fichier 
	 */
	private void ajouterImage(File fichier, String nomFichier) {
		try {
			BufferedImage image = ImageIO.read(fichier);
			spritesSet.put(nomFichier, image);
		} catch (IOException e) {
			logger.error("L'image " + nomFichier + " n'a pas pu être chargé");
		}
	}

	/**
	 * Getter de instance
	 *
	 * @return Retourne l'instance actuel
	 */
	public static SpritesConfig getInstance() {
		if (instance == null) {
			return new SpritesConfig("src/images");
		}

		return instance;
	}

	/**
	 * Méthode qui donne l'image associé au nom du fichier
	 *
	 * @param nomFichier : Nom du fichier
	 * @return L'image associé au nom du fichier
	 */
	public BufferedImage getImage(String nomFichier) {
		return spritesSet.get(nomFichier);
	}

}
