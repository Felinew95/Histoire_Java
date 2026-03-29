package moteur.traitement.builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import log.LoggerUtility;

import moteur.donnees.Evenement;
import moteur.donnees.Region;
import moteur.traitement.management.managers.EvenementHistoriqueManager;
import moteur.traitement.management.factory.SimFactory;

/**
 * Classe qui construit les événements historiques 
 * 
 * @author Alexandre 
 * @version 1.0
 * 
 * @see EvenementHistoriqueManager
 */
public class EvenementHistoriqueBuilder {

	// Attributs
	private static Logger logger = LoggerUtility.getLogger(EvenementHistoriqueBuilder.class, "html");

	/**
	 * Méthode qui créer le manager des événements historiques 
	 * 
	 * @param fichier : Un fichier CSV
	 * @return le manager des événements historiques 
	 * 
	 * @throws IOException
	 */
	private static EvenementHistoriqueManager buildEvenementManager(String fichier) throws IOException {
		logger.info("Début du chargement des événements historiques depuis : " + fichier);
		EvenementHistoriqueManager manager = new EvenementHistoriqueManager();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            br.readLine(); 
            
            String ligne;
            String[] valeurs;
            while ((ligne = br.readLine()) != null) {
            	valeurs = ligne.split(";");
            	ajouterEvenement(manager, valeurs);
            }
        }
		
		logger.info("Chargement terminé : " + manager.getNbEvenementsHistoriques() + " événements chargés depuis " + fichier);
		return manager;
	}

	/**
	 * Méthode qui ajoute un événement au manager
	 * 
	 * @param manager : Manager des événements 
	 * @param valeurs : Valeurs 
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
		
		logger.debug("Événement ajouté : nom=" + nomEvenement + ", anneeDebut=" + anneeDebut + ", anneeFin=" + anneeFin + ", type=" + typeEvenement + ", region=" + nomRegion);
	}
	
	/**
	 * Méthode qui créer le manager des événements historiques de la civilisation viking
	 * 
	 * @return Le manager des événements historiques 
	 */
	public static EvenementHistoriqueManager buildEvenementManagerViking() {
		logger.info("Chargement des événements historiques vikings");
		
		try {
			return buildEvenementManager("src/csv/evenements.csv");
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des événements historiques vikings : " + e.getMessage());
		}
		
		return null;
	}
	
}