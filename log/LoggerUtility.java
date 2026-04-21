package log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Classe utilitaire pour la création et la configuration de loggers Log4j.
 *
 * <p>
 * Cette classe permet de générer des {@link Logger} configurés pour écrire des logs
 * soit dans un fichier texte, soit dans un fichier HTML, en fonction de la configuration
 * spécifiée.
 * </p>
 *
 * <p>
 * L'utilisation typique consiste à appeler {@link #getLogger(Class, String)}
 * en fournissant la classe pour laquelle le logger est créé et le type de fichier de log souhaité.
 * </p>
 *
 * @author Tianxiao.Liu@u-cergy.fr
 * @version 1.1
 */
public class LoggerUtility {

	/**
	 * Chemin vers le fichier de configuration Log4j pour les logs en texte.
	 */
	private static final String TEXT_LOG_CONFIG = "src/log/log4j-text.properties";

	/**
	 * Chemin vers le fichier de configuration Log4j pour les logs en HTML.
	 */
	private static final String HTML_LOG_CONFIG = "src/log/log4j-html.properties";

	/**
	 * Retourne un {@link Logger} configuré selon le type de fichier demandé.
	 *
	 * @param logClass classe pour laquelle le logger est créé
	 * @param logFileType type de fichier de log souhaité, soit "text" soit "html"
	 * @return un {@link Logger} configuré pour la classe et le type spécifiés
	 * @throws IllegalArgumentException si le type de log fourni n'est pas reconnu
	 */
	public static Logger getLogger(Class<?> logClass, String logFileType) {
		if ("text".equals(logFileType)) {
			PropertyConfigurator.configure(TEXT_LOG_CONFIG);
		} else if ("html".equals(logFileType)) {
			PropertyConfigurator.configure(HTML_LOG_CONFIG);
		} else {
			throw new IllegalArgumentException("Type de log inconnu : " + logFileType);
		}

		return Logger.getLogger(logClass.getName());
	}

}