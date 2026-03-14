package config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * Classe de configuration des sprites
 * 
 * @author Tauseef
 * @version 1.0
 */
public class SpritesConfig {

	// Attributs
	private final HashMap<String, BufferedImage> spritesSet;
	private final File dossier;

	/**
	 * Constructeur de la classe SpriteConfig
	 * 
	 * @param chemin : Chemin du dossier avec les images
	 */
	public SpritesConfig(String chemin) {
		spritesSet = new HashMap<>();
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
			try {
				BufferedImage image = ImageIO.read(fichier);
				spritesSet.put(nomFichier, image);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
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
