package moteur.traitement.builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import log.LoggerUtility;
import gui.management.MobileManager;
import gui.management.UIFactory;
import gui.mobiles.Action;
import gui.mobiles.Mobile;

/**
 * Classe utilitaire pour la construction des {@link Mobile} et de leur
 * {@link Action} pour chaque région.
 *
 * <p>
 * Cette classe lit un fichier CSV contenant la définition des mobiles et
 * des actions associées. Chaque mobile est créé via {@link UIFactory} et
 * ajouté à un {@link MobileManager} avec ses actions.
 * </p>
 *
 * <p>
 * Chaque méthode statique buildMobileManagerX() permet de charger les
 * mobiles pour une région spécifique depuis le fichier CSV correspondant.
 * </p>
 *
 * <p>
 * La classe utilise un logger HTML {@link Logger} pour suivre le chargement
 * des mobiles et détailler chaque ajout de mobile ou action.
 * </p>
 *
 * @author Alexandre
 * @version 1.1
 *
 * @see Mobile
 * @see Action
 * @see MobileManager
 * @see UIFactory
 */
public class MobileBuilder {

	/**
	 * Logger HTML pour le suivi du chargement des mobiles et actions.
	 */
	private static final Logger logger = LoggerUtility.getLogger(MobileBuilder.class, "html");

	/**
	 * Construit un {@link MobileManager} à partir d'un fichier CSV donné.
	 *
	 * @param fichier Chemin vers le fichier CSV contenant les mobiles et actions
	 * @return Le manager de mobiles rempli
	 * @throws IOException si le fichier ne peut pas être lu
	 */
	private static MobileManager buildMobileManager(String fichier) throws IOException {
		logger.info("Début du chargement des mobiles depuis : " + fichier);
		MobileManager manager = new MobileManager();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			String[] valeurs;
			
			while ((ligne = br.readLine()) != null) {
				valeurs = ligne.split(";");
				ajoutMobilesActions(manager, valeurs);
			}
        }
		
		logger.info("Chargement terminé : " + manager.getNbMobiles() + " mobiles chargés depuis " + fichier);
		return manager;
	}

	/**
	 * Ajoute des mobiles ou des actions au manager selon le type indiqué.
	 *
	 * @param manager Le manager de mobiles
	 * @param valeurs Valeurs extraites du CSV
	 */
	private static void ajoutMobilesActions(MobileManager manager, String[] valeurs) {
		switch (valeurs[0]) {
			case "MOBILE":
				ajouterMobile(manager, valeurs);
				break;
			case "ACTION":
				ajouterAction(manager, valeurs);
				break;
		}
	}

	/**
	 * Ajoute une {@link Action} à un {@link Mobile} existant dans le manager.
	 *
	 * @param manager Le manager de mobiles
	 * @param valeurs Valeurs extraites du CSV pour l'action
	 */
	private static void ajouterAction(MobileManager manager, String[] valeurs) {
		Action a = UIFactory.buildAction(valeurs[2], Integer.parseInt(valeurs[3]), Integer.parseInt(valeurs[4]));
		manager.ajouterActionAMobile(Integer.parseInt(valeurs[1]), a);
		logger.debug("Action ajoutée au mobile id=" + valeurs[1] + " : type=" + valeurs[2] + ", distanceLigne=" + valeurs[3] + ", distanceColonne=" + valeurs[4]);
	}

	/**
	 * Ajoute un {@link Mobile} au manager avec ses actions initialisées.
	 *
	 * @param manager Le manager de mobiles
	 * @param valeurs Valeurs extraites du CSV pour le mobile
	 */
	private static void ajouterMobile(MobileManager manager, String[] valeurs) {
		int id = Integer.parseInt(valeurs[1]);
		String type = valeurs[2];
		
		int ligneM = Integer.parseInt(valeurs[3]);
		int colonneM = Integer.parseInt(valeurs[4]);
		
		int anneeApparition = Integer.parseInt(valeurs[5]);
		int anneeDisparition = Integer.parseInt(valeurs[6]);
		
		Mobile m = UIFactory.buildMobile(id, type, ligneM, colonneM, anneeApparition, anneeDisparition);
		manager.ajouterMobileEtActions(m, UIFactory.buildActions());
		logger.debug("Mobile ajouté : id=" + id + ", type=" + type + ", ligne=" + ligneM + ", colonne=" + colonneM);
	}
	
	/**
	 * Méthode qui crée le manager de lindsfarne 
	 * 
	 * @return Le manager de lindsfarne
	 */
	public static MobileManager buildMobileManagerLindsfarne() {
		logger.info("Chargement des mobiles : Lindsfarne");
		
		try {
			return buildMobileManager("src/csv/mobiles/mobilesLindsfarne.csv");
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des mobiles de Lindsfarne : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Méthode qui crée le manager de l'europe
	 * 
	 * @return Le manager de l'europe 
	 */
	public static MobileManager buildMobileManagerEurope() {
		logger.info("Chargement des mobiles : Europe ");
	    
		try {
			return buildMobileManager("src/csv/mobiles/mobilesEurope.csv");
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des mobiles de Europe : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Méthode qui crée le manager de l'islande
	 * 
	 * @return Le manager de l'islande
	 */
	public static MobileManager buildMobileManagerIslande() {
		logger.info("Chargement des mobiles : Islande ");
	    
		try {
			return buildMobileManager("src/csv/mobiles/mobilesIslande.csv");
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des mobiles de l'Islande : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Méthode qui crée le manager de la grande bretagne
	 * 
	 * @return Le manager de la grande bretagne
	 */
	public static MobileManager buildMobileManagerGrandeBretagne() {
		logger.info("Chargement des mobiles : Grande Bretagne ");
	    
		try {
			return buildMobileManager("src/csv/mobiles/mobilesGB.csv");
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des mobiles de la grande bretagne : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Méthode qui crée le manager de la normandie
	 * 
	 * @return Le manager de la normandie
	 */
	public static MobileManager buildMobileManagerNormandie() {
		logger.info("Chargement des mobiles : Normandie ");
	    
		try {
			return buildMobileManager("src/csv/mobiles/mobilesNormandie.csv");
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des mobiles de la normandie : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Méthode qui crée le manager de la scandinavie
	 * 
	 * @return Le manager de la scandinavie
	 */
	public static MobileManager buildMobileManagerScandinavie() {
		logger.info("Chargement des mobiles : Scandinavie ");
	    
		try {
			return buildMobileManager("src/csv/mobiles/mobilesScandinavie.csv");
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des mobiles de la Scandinavie : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Méthode qui crée le manager du vinland
	 * 
	 * @return Le manager du vinland
	 */
	public static MobileManager buildMobileManagerVinland() {
		logger.info("Chargement des mobiles : Vinland ");
	    
		try {
			return buildMobileManager("src/csv/mobiles/mobilesVinland.csv");
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des mobiles du Vinland : " + e.getMessage());
		}
		
		return null;
	}
	
}