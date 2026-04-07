package moteur.traitement.builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import log.LoggerUtility;

import moteur.donnees.Evenement;
import moteur.donnees.Region;
import moteur.traitement.management.managers.evenement.EvenementHistoriqueManager;
import moteur.traitement.management.factory.SimFactory;

/**
 * Classe utilitaire pour la construction des événements historiques.
 *
 * <p>
 * Cette classe lit un fichier CSV contenant les événements historiques et
 * les transforme en objets {@link Evenement} associés à des {@link Region}.
 * Les événements sont ensuite ajoutés à un {@link EvenementHistoriqueManager}.
 * </p>
 *
 * <p>
 * La classe utilise un logger HTML {@link Logger} pour suivre le chargement
 * des événements et détailler chaque ajout d'événement.
 * </p>
 *
 * @author Alexandre
 * @version 1.1
 *
 * @see EvenementHistoriqueManager
 * @see Evenement
 * @see Region
 */
public class EvenementHistoriqueBuilder {

	/**
	 * Logger HTML pour le suivi du chargement des événements.
	 */
	private static final Logger logger = LoggerUtility.getLogger(EvenementHistoriqueBuilder.class, "html");

	/**
	 * Construit un {@link EvenementHistoriqueManager} en chargeant les événements
	 * depuis le fichier CSV par défaut ("src/csv/evenements.csv").
	 *
	 * @return le manager des événements historiques
	 */
	private static EvenementHistoriqueManager buildEvenementManager() {
		logger.info("Début du chargement des événements historiques depuis : src/csv/civilisation/evenements.csv");
		EvenementHistoriqueManager manager = new EvenementHistoriqueManager();

		try (BufferedReader br = new BufferedReader(new FileReader("src/csv/civilisation/evenements.csv"))) {
			br.readLine(); // Ignorer la première ligne (entêtes)
			String ligne;
			String[] valeurs;
			while ((ligne = br.readLine()) != null) {
				valeurs = ligne.split(";");
				ajouterEvenement(manager, valeurs);
			}
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des événements historiques vikings : " + e.getMessage());
		}

		logger.info("Chargement terminé : " + manager.getNbEvenementsHistoriques() + " événements chargés");
		return manager;
	}

	/**
	 * Ajoute un {@link Evenement} au manager à partir des valeurs extraites
	 * d'une ligne CSV.
	 *
	 * @param manager le manager des événements historiques
	 * @param valeurs tableau de valeurs extraites du CSV
	 */
	private static void ajouterEvenement(EvenementHistoriqueManager manager, String[] valeurs) {
		String nomRegion = valeurs[0];
		String chefRegion = valeurs[1];
		Region region = SimFactory.buildRegion(nomRegion, chefRegion);

		String nomEvenement = valeurs[2];
		String typeEvenement = valeurs[3];
		String narration = valeurs[4];
		int anneeDebut = Integer.parseInt(valeurs[5]);
		int anneeFin = Integer.parseInt(valeurs[6]);

		Evenement evenement = SimFactory.buildEvenement(nomEvenement, anneeDebut, anneeFin, region, narration, typeEvenement);
		manager.ajouterEvenementHistorique(evenement);

		logger.debug("Événement ajouté : nom=" + nomEvenement + ", anneeDebut=" + anneeDebut +
				", anneeFin=" + anneeFin + ", type=" + typeEvenement + ", region=" + nomRegion);
	}

	/**
	 * Construit le {@link EvenementHistoriqueManager} spécifique à la civilisation viking.
	 *
	 * @return le manager des événements historiques vikings
	 */
	public static EvenementHistoriqueManager buildEvenementManagerViking() {
		logger.info("Chargement des événements historiques vikings");
		return buildEvenementManager();
	}

}