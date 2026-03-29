package moteur.traitement.management.managers;

import config.SimConfig;
import gui.management.ChartManager;
import moteur.carte.Carte;
import moteur.donnees.*;

/**
 * Classe qui représente le traitement des données de la simulation 
 *
 * @author Alexandre
 * @version 1.1
 */
public class SimulationManager {

	// Attributs
	private int anneeActuelle;
	private final Carte carte;
	private final Civilisation civilisation;
	private final MobileManager mobileManager;
	private final EvenementHistoriqueManager evenementManager;
	private final ChartManager chartManager;

	private long dernierTempsMobile = 0;
	private long dernierTempsAnnee = 0;

	private final long intervalleDeplacementMobileMS = SimConfig.INTERVALLE_DEPLACEMENT_MS;
	private long intervalleAnneeMS = SimConfig.INTERVALLE_TEMPS_ANNEE_EUROPE_MS;

	public SimulationManager(int anneeActuelle, Carte carte, Civilisation civilisation, MobileManager mobileManager,
							 EvenementHistoriqueManager evenementManager, ChartManager chartManager) {
		this.anneeActuelle = anneeActuelle;
		this.carte = carte;
		this.civilisation = civilisation;
		this.mobileManager = mobileManager;
		this.evenementManager = evenementManager;
		this.chartManager = chartManager;

		this.dernierTempsMobile = System.currentTimeMillis();
		this.dernierTempsAnnee = System.currentTimeMillis();
	}

	public int getAnneeActuelle() {
		return anneeActuelle;
	}

	public Carte getCarte() {
		return carte;
	}

	public Civilisation getCivilisation() {
		return civilisation;
	}

	public MobileManager getMobileManager() {
		return mobileManager;
	}

	public EvenementHistoriqueManager getEvenementManager() {
		return evenementManager;
	}

	public void setAnneeActuelle(int anneeActuelle) {
		this.anneeActuelle = anneeActuelle;
	}

	public void setIntervalleAnneeMS(long intervalleAnneeMS) {
		this.intervalleAnneeMS = intervalleAnneeMS;
	}

	/**
	 * Avance l'année et retourne true si l'année a changé
	 */
	private boolean shouldUpdateYear() {
		long tempsInstantAnnee = System.currentTimeMillis();
		if (tempsInstantAnnee - dernierTempsAnnee >= intervalleAnneeMS) {
			dernierTempsAnnee = tempsInstantAnnee;
			anneeActuelle++;
			return true;  // L'année a changé
		}
		return false;  // L'année n'a pas changé
	}

	/**
	 * Met à jour les mobiles si le temps le permet
	 */
	private void updateMobiles() {
		long tempsInstantMobile = System.currentTimeMillis();
		if (tempsInstantMobile - dernierTempsMobile < intervalleDeplacementMobileMS) {
			return;
		}

		dernierTempsMobile = tempsInstantMobile;
		mobileManager.updateAllMobiles(carte, anneeActuelle);
	}

	/**
	 * Boucle principale de simulation
	 */
	public void nextRound() {
		updateMobiles();
		evenementManager.verifierEvenementsHistoriques(anneeActuelle, civilisation);
		boolean yearChanged = shouldUpdateYear();

		if (yearChanged) {
			updateCivilisation();
		}
	}

	/**
	 * Met à jour tous les systèmes de la civilisation
	 * Appelé UNE FOIS par an (quand l'année change)
	 */
	private void updateCivilisation() {
		// Récupère les données actuelles
		Armee armee = civilisation.getArmee();
		Economie economie = civilisation.getEconomie();
		Population population = civilisation.getPopulation();

		int nvNbEvenements = civilisation.getNbEvenements();
		Evenement evenement = getEvenementActuel(civilisation, anneeActuelle, nvNbEvenements);

		// Update population
		int nbHabitants = population.getNbHabitants();
		PopulationManager.updatePopulation(population, evenement);
		int nvNbHabitants = population.getNbHabitants();

		// Update charts
		chartManager.ajouterPointLineChart(anneeActuelle, nvNbHabitants);

		// Update économie
		EconomieManager.updateEconomie(economie, nbHabitants, evenement);
		chartManager.updateRessourcesPieChart(economie);
	}

	/**
	 * Retourne l'événement actif durant l'année donnée
	 *
	 * @param civilisation : Civilisation
	 * @param anneeSim : Année de la simulation
	 * @param nvNbEvenements : Nombre d'événements
	 *
	 * @return L'événement actuel (null si aucun)
	 */
	private static Evenement getEvenementActuel(Civilisation civilisation, int anneeSim, int nvNbEvenements) {
		for (int i = 0; i < nvNbEvenements; i++) {
			Evenement e = civilisation.getEvenement(i);
			if (e != null && e.getAnneeDebut() <= anneeSim && e.getAnneeFin() >= anneeSim) {
				return e;  // Retourne le premier événement actif trouvé
			}
		}
		return null;  // Aucun événement actif
	}
}