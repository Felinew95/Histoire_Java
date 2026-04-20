package moteur.traitement.management.managers.civilisation;

import config.SimConfig;
import gui.management.ChartManager;
import gui.management.MobileManager;
import log.LoggerUtility;
import moteur.carte.Carte;
import moteur.donnees.*;

import moteur.traitement.management.managers.armee.ArmeeManager;
import moteur.traitement.management.managers.economie.EconomieManager;
import moteur.traitement.management.managers.evenement.EvenementHistoriqueManager;
import moteur.traitement.management.managers.politique.PolitiqueManager;
import moteur.traitement.management.managers.politique.RelationManager;
import moteur.traitement.management.managers.politique.ReligionManager;
import moteur.traitement.management.managers.population.PopulationManager;
import org.apache.log4j.Logger;

import static utilitaire.SimulationUtility.getEvenementActuel;

/**
 * Gestionnaire principal de la simulation d'une civilisation.
 *
 * <p>
 * Cette classe orchestre l'évolution temporelle de la simulation en coordonnant
 * les différents systèmes : population, économie, armée, religion, politique
 * et relations diplomatiques.
 * </p>
 *
 * <p>
 * Le cycle de simulation est géré par {@link #nextRound()}, qui :
 * <ul>
 *     <li>Met à jour les déplacements des mobiles</li>
 *     <li>Vérifie les événements historiques</li>
 *     <li>Fait progresser le temps (année)</li>
 *     <li>Met à jour la civilisation à chaque changement d'année</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @version 2.0
 */
public class SimulationManager {

	/**
	 * Année courante de la simulation.
	 */
	private int anneeActuelle;

	/**
	 * Carte sur laquelle évolue la simulation.
	 */
	private final Carte carte;

	/**
	 * Civilisation simulée.
	 */
	private final Civilisation civilisation;

	/**
	 * Gestionnaire des entités mobiles.
	 */
	private final MobileManager mobileManager;

	/**
	 * Gestionnaire des événements historiques.
	 */
	private final EvenementHistoriqueManager evenementManager;

	/**
	 * Gestionnaire des graphiques et statistiques.
	 */
	private final ChartManager chartManager;

	/**
	 * Nombre d'événements pris en compte lors du dernier cycle.
	 */
	private int nvNbEvenements;

	/**
	 * Temps du dernier déplacement des mobiles.
	 */
	private long dernierTempsMobile;

	/**
	 * Temps de la dernière mise à jour annuelle.
	 */
	private long dernierTempsAnnee;

	/**
	 * Intervalle (en millisecondes) entre deux changements d'année.
	 */
	private long intervalleAnneeMS = SimConfig.INTERVALLE_TEMPS_ANNEE_EUROPE_MS;

	/**
	 * Référentiel des chefs (rois, dirigeants, etc.).
	 */
	private final ChefRepository chefRepository = ChefRepository.getInstance();

	/**
	 * Logger pour suivre le chargement et les erreurs.
	 */
	private static final Logger logger =
			LoggerUtility.getLogger(SimulationManager.class, "html");

	/**
	 * Initialise le gestionnaire de simulation.
	 *
	 * @param anneeActuelle année de départ
	 * @param carte carte de la simulation
	 * @param civilisation civilisation simulée
	 * @param mobileManager gestionnaire des mobiles
	 * @param evenementManager gestionnaire des événements historiques
	 * @param chartManager gestionnaire des graphiques
	 */
	public SimulationManager(int anneeActuelle, Carte carte, Civilisation civilisation,
	                         MobileManager mobileManager,
	                         EvenementHistoriqueManager evenementManager,
	                         ChartManager chartManager) {
		this.anneeActuelle = anneeActuelle;
		this.carte = carte;
		this.civilisation = civilisation;
		this.mobileManager = mobileManager;
		this.evenementManager = evenementManager;
		this.chartManager = chartManager;

		this.dernierTempsMobile = System.currentTimeMillis();
		this.dernierTempsAnnee = System.currentTimeMillis();
	}

	/**
	 * Retourne l'année actuelle de la simulation.
	 *
	 * @return l'année courante
	 */
	public int getAnneeActuelle() {
		return anneeActuelle;
	}

	/**
	 * Retourne la carte utilisée pour la simulation.
	 *
	 * @return la carte de la simulation
	 */
	public Carte getCarte() {
		return carte;
	}

	/**
	 * Retourne la civilisation simulée.
	 *
	 * @return la civilisation
	 */
	public Civilisation getCivilisation() {
		return civilisation;
	}

	/**
	 * Retourne le gestionnaire des entités mobiles.
	 *
	 * @return le gestionnaire de mobiles
	 */
	public MobileManager getMobileManager() {
		return mobileManager;
	}

	/**
	 * Définit l'intervalle de temps entre deux années simulées.
	 *
	 * <p>
	 * Un intervalle plus faible accélère la simulation,
	 * tandis qu'un intervalle plus élevé la ralentit.
	 * </p>
	 *
	 * @param intervalleAnneeMS nouvel intervalle en millisecondes
	 */
	public void setIntervalleAnneeMS(long intervalleAnneeMS) {
		this.intervalleAnneeMS = intervalleAnneeMS;
		logger.info("Intervalle d'année mis à jour : " + intervalleAnneeMS + " ms");
	}

	/**
	 * Retourne le nombre d'événements pris en compte lors du dernier cycle.
	 *
	 * @return le nombre d'événements récents
	 */
	public int getNvNbEvenements() {
		return nvNbEvenements;
	}
	
	
	/**
	 * Retourne le gestionnaire des graphiques.
	 *
	 * @return le gestionnaire des graphiques
	 */
	public ChartManager getChartManager() {
		return chartManager;
	}

	/**
	 * Détermine si une nouvelle année doit être simulée.
	 *
	 * <p>
	 * Si le temps écoulé dépasse l'intervalle défini, l'année est incrémentée.
	 * </p>
	 *
	 * @return {@code true} si l'année a changé, {@code false} sinon
	 */
	private boolean shouldUpdateYear() {
		long tempsInstantAnnee = System.currentTimeMillis();
		if (tempsInstantAnnee - dernierTempsAnnee >= intervalleAnneeMS) {
			dernierTempsAnnee = tempsInstantAnnee;
			anneeActuelle++;
			logger.info("Changement d'année : " + anneeActuelle);
			return true;
		}
		return false;
	}

	/**
	 * Met à jour les déplacements des mobiles.
	 *
	 * <p>
	 * La mise à jour est effectuée uniquement si l'intervalle de déplacement
	 * défini dans {@link SimConfig} est respecté.
	 * </p>
	 */
	private void updateMobiles() {
		long tempsInstantMobile = System.currentTimeMillis();
		long intervalleDeplacementMobileMS = SimConfig.INTERVALLE_DEPLACEMENT_MS;

		if (tempsInstantMobile - dernierTempsMobile < intervalleDeplacementMobileMS) {
			logger.debug("Pas de mise à jour des mobiles (intervalle non atteint)");
			return;
		}

		dernierTempsMobile = tempsInstantMobile;
		mobileManager.updateAllMobiles(carte, anneeActuelle);
		logger.info("Mobiles mis à jour pour l'année " + anneeActuelle);
	}

	/**
	 * Exécute un cycle de simulation.
	 *
	 * <p>
	 * Cette méthode doit être appelée en boucle pour faire évoluer la simulation.
	 * </p>
	 *
	 * <p>
	 * Étapes :
	 * <ol>
	 *     <li>Mise à jour des mobiles</li>
	 *     <li>Vérification des événements historiques</li>
	 *     <li>Mise à jour de l'année</li>
	 *     <li>Mise à jour de la civilisation si nécessaire</li>
	 * </ol>
	 * </p>
	 */
	public void nextRound() {
		logger.info("Début du cycle de simulation pour l'année " + anneeActuelle);

		updateMobiles();
		evenementManager.verifierEvenementsHistoriques(anneeActuelle, civilisation);
		logger.info("Événements historiques vérifiés");

		boolean yearChanged = shouldUpdateYear();

		if (yearChanged) {
			updateCivilisation();
		}

		logger.info("Fin du cycle de simulation pour l'année " + anneeActuelle);
	}

	/**
	 * Met à jour tous les systèmes de la civilisation pour une année donnée.
	 *
	 * <p>
	 * Cette méthode applique les effets des événements sur :
	 * <ul>
	 *     <li>La population</li>
	 *     <li>L'économie</li>
	 *     <li>L'armée</li>
	 *     <li>La religion</li>
	 *     <li>La politique</li>
	 *     <li>Les relations diplomatiques</li>
	 * </ul>
	 * </p>
	 *
	 * <p>
	 * Elle met également à jour les graphiques statistiques associés.
	 * </p>
	 */
	private void updateCivilisation() {
		logger.info("Mise à jour de la civilisation pour l'année " + anneeActuelle);

		Armee armee = civilisation.getArmee();
		Economie economie = civilisation.getEconomie();
		Population population = civilisation.getPopulation();
		Religion religion = civilisation.getReligion();
		Politique politique = civilisation.getPolitique();

		nvNbEvenements = civilisation.getNbEvenements();
		Evenement evenement = getEvenementActuel(civilisation, anneeActuelle, nvNbEvenements);

		int nbHabitants = population.getNbHabitants();
		PopulationManager.updatePopulation(population, evenement);
		logger.debug("Population mise à jour : " + population.getNbHabitants() + " habitants");

		updateEconomie(economie, nbHabitants, evenement);
		updateArmee(armee, evenement);
		updateReligion(religion, evenement);
		updatePolitique(politique, evenement);
		updateRelations(politique, evenement);

		setKonungrActuel();
		logger.info("Konungr actuel : " + (civilisation.getKonungr() != null ? civilisation.getKonungr().getNom() : "Aucun"));
	}

	/**
	 * Met à jour les relations de la civilisation en appliquant les effets de l'événement.
	 *
	 * @param politique l'objet Politique 
	 * @param evenement l'événement influençant 
	 */
	private void updateRelations(Politique politique, Evenement evenement) {
		RelationManager.updateRelations(civilisation.getRelations(), politique, evenement);
		logger.debug("Relations diplomatiques mises à jour");
	}

	/**
	 * Met à jour la politique de la civilisation en appliquant les effets de l'événement.
	 *
	 * @param politique l'objet Politique à mettre à jour
	 * @param evenement l'événement influençant la politique
	 */
	private void updatePolitique(Politique politique, Evenement evenement) {
		if (politique != null) {
			PolitiqueManager.updatePolitique(politique, evenement);
			logger.debug("Politique mise à jour");
		}
	}

	/**
	 * Met à jour la religion de la civilisation en appliquant les effets de l'événement.
	 *
	 * @param religion l'objet Religion à mettre à jour
	 * @param evenement l'événement influençant la religion
	 */
	private void updateReligion(Religion religion, Evenement evenement) {
		if (religion != null) {
			ReligionManager.updateReligion(religion, evenement);
			logger.debug("Religion mise à jour");
		}
	}

	/**
	 * Met à jour l'armée de la civilisation en appliquant les effets de l'événement.
	 *
	 * @param armee l'objet Armee à mettre à jour
	 * @param evenement l'événement influençant l'armée
	 */
	private void updateArmee(Armee armee, Evenement evenement) {
		if (armee != null) {
			ArmeeManager.updateArmee(anneeActuelle, armee, evenement, civilisation.getNom());
			logger.debug("Armée mise à jour");
		}
	}

	/**
	 * Met à jour l'économie de la civilisation en fonction du nombre d'habitants
	 * et des effets de l'événement. Met également à jour les graphiques associés.
	 *
	 * @param economie l'objet Economie à mettre à jour
	 * @param nbHabitants le nombre d'habitants influençant l'économie
	 * @param evenement l'événement influençant l'économie
	 */
	private void updateEconomie(Economie economie, int nbHabitants, Evenement evenement) {
		if (economie != null) {
			EconomieManager.updateEconomie(economie, nbHabitants, evenement);
			logger.debug("Économie mise à jour : " + economie.getStycas() + " stycas");
		}
	}
	
	

	/**
	 * Met à jour le dirigeant actuel de la civilisation.
	 *
	 * <p>
	 * Le dirigeant est récupéré depuis le {@link ChefRepository}
	 * en fonction de l'année courante.
	 * </p>
	 */
	private void setKonungrActuel() {
		Konungr konungr = chefRepository.getKonungrActuel(civilisation.getNom(), anneeActuelle);
		if (konungr != null) {
			civilisation.setKonungr(konungr);
		}
	}

}