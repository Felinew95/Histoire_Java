package config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;

import log.LoggerUtility;
import utilitaire.SimulationUtility;

/**
 * <b>Classe de configuration des sprites.</b>
 *
 * <p>
 * Cette classe gère le chargement et le stockage des images (sprites) utilisées
 * dans l'application. Elle suit le pattern <i>singleton</i> pour ne créer qu'une
 * seule instance accessible globalement via {@link #getInstance()}.
 * </p>
 *
 * <p>
 * Les images sont extraites à partir d'un dossier donné et ajoutées dans un
 * {@link HashMap} avec le nom du fichier comme clé et l'image {@link BufferedImage}
 * comme valeur. Les formats pris en charge sont PNG, JPEG et JPG.
 * </p>
 *
 * @author Tauseef
 * @version 2.0
 */
public class SpritesRepository {

	/**
	 * Logger pour les messages d'information ou d'erreur
	 */
	private final static Logger logger = LoggerUtility.getLogger(SpritesRepository.class, "html");

	/**
	 * Ensemble des sprites chargés, clé = nom du fichier, valeur = image
	 */
	private final HashMap<String, BufferedImage> spritesSet = new HashMap<>();

	/**
	 * Instance unique de la classe (singleton)
	 */
	private static final SpritesRepository instance = new SpritesRepository("src/images");

	/**
	 * Constructeur privé pour le singleton.
	 *
	 * <p>
	 * Charge les images du dossier spécifié.
	 * </p>
	 *
	 * @param chemin Chemin du dossier contenant les images
	 */
	private SpritesRepository(String chemin) {
		creerSpritesSet(new File(chemin));
	}

	/**
	 * Crée l'ensemble des sprites à partir d'un dossier.
	 *
	 * @param dossier Dossier contenant les images
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
	 * Ajoute un sprite à l'ensemble si le fichier est une image valide.
	 *
	 * @param fichier Fichier image
	 */
	private void ajouterSprite(File fichier) {
		String nomFichier = fichier.getName();
		if (fichier.isFile() && (nomFichier.endsWith(".png") || nomFichier.endsWith(".jpeg") || nomFichier.endsWith(".jpg"))) {
			ajouterImage(fichier, nomFichier);
		}
	}

	/**
	 * Ajoute une image dans le {@link HashMap} des sprites.
	 *
	 * @param fichier Fichier image
	 * @param nomFichier Nom du fichier
	 */
	private void ajouterImage(File fichier, String nomFichier) {
		BufferedImage image = SimulationUtility.readBufferedImage(fichier);
		if (image != null) {
			spritesSet.put(nomFichier, image);
		} else {
			logger.warn("L'image n'a pas pu être ajoutée : " + nomFichier);
		}
	}

	/**
	 * Retourne l'instance unique de {@link SpritesRepository}.
	 *
	 * @return Instance unique de la classe
	 */
	public static SpritesRepository getInstance() {
		if (instance == null) {
			return new SpritesRepository("src/images");
		}
		return instance;
	}

	/**
	 * Retourne l'image associée au nom de fichier donné.
	 *
	 * @param nomFichier Nom du fichier de l'image
	 * @return {@link BufferedImage} correspondant, ou {@code null} si non trouvé
	 */
	public BufferedImage getImage(String nomFichier) {
		return spritesSet.get(nomFichier);
	}

}