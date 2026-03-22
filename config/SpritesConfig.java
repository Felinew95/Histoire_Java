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
	private final File dossier;

	/**
	 * Constructeur de la classe SpriteConfig
	 * 
	 * @param chemin : Chemin du dossier avec les images
	 */
	public SpritesConfig(String chemin) {
		dossier = new File(chemin);
		creerSpritesSet();
	}

	/**
	 * Méthode qui extrait les images
	 */
	private void creerSpritesSet() {
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
	 * @param fichier : Un fichier image
	 * @param nomFichier : Nom du fichier
	 */
	private void ajouterSprite(File fichier) {
		String nomFichier = fichier.getName();
		if (fichier.isFile() && fichier != null && (nomFichier.endsWith(".png") || nomFichier.endsWith(".jpeg") || nomFichier.endsWith(".jpg"))) {
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
	 * Méthode qui permet d'obtenir une image
	 * 
	 * @param nom : Nom de l'image
	 * @return L'image associée
	 */
	public BufferedImage getImage(String nom) {
		return spritesSet.get(nom);
	}

}
