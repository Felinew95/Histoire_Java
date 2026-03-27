package moteur.traitement.management.managers;

import config.SimConfig;
import gui.management.ChartManager;
import moteur.carte.Carte;
import moteur.donnees.*;

/**
 * Classe qui représente le traitement des données de la simulation 
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Civilisation
 * @see MobileManager
 * @see Carte
 * @see EvenementHistoriqueManager
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

	private void avancerAnneeActuelle() {
		long tempsInstantAnnee = System.currentTimeMillis();
		if (tempsInstantAnnee - dernierTempsAnnee < intervalleAnneeMS) {
			return;
		}
		
		dernierTempsAnnee = tempsInstantAnnee;
		this.anneeActuelle++;
	}

	private void updateMobiles() {
		long tempsInstantMobile = System.currentTimeMillis();
		if (tempsInstantMobile - dernierTempsMobile < intervalleDeplacementMobileMS) {
			return;
		}
		
		dernierTempsMobile = tempsInstantMobile;
		mobileManager.updateAllMobiles(carte, anneeActuelle);
	}
	
	public void nextRound() {
		updateMobiles();
		evenementManager.verifierEvenementsHistoriques(anneeActuelle, civilisation);


		updateCivilisation();

		avancerAnneeActuelle();
	}

	private void updateCivilisation() {
		long tempsInstantAnnee = System.currentTimeMillis();
		if (tempsInstantAnnee - dernierTempsAnnee < intervalleAnneeMS) {
			return ;
		}

		Armee armee = civilisation.getArmee();
		Economie economie = civilisation.getEconomie();
		Population population = civilisation.getPopulation();

		int nvNbEvenements = civilisation.getNbEvenements();
		Evenement evenement = getEvenementActuel(civilisation, anneeActuelle, nvNbEvenements);

		int nbHabitants = population.getNbHabitants();
		PopulationManager.updatePopulation(population, evenement);
		int nvNbHabitants = population.getNbHabitants();
		chartManager.ajouterPointLineChart(anneeActuelle, nvNbHabitants);

		EconomieManager.updateEconomie(economie, nvNbHabitants, nbHabitants);
	}

	/**
	 * Méthode qui retourne l'événement actuel
	 *
	 * @param civilisation : Civilisation
	 * @param anneeSim : Année de la simulation
	 * @param nvNbEvenements : Nombre d'événements
	 *
	 * @return L'événement actuel
	 */
	private static Evenement getEvenementActuel(Civilisation civilisation, int anneeSim, int nvNbEvenements) {
		Evenement evenement = null;

		boolean trouve = false;
		for (int i = 0; i < nvNbEvenements && !trouve; i++) {
			Evenement e = civilisation.getEvenement(i);
			if (e.getAnneeDebut() <= anneeSim && e.getAnneeFin() >= anneeSim) {
				evenement = e;
			}
		}
		return evenement;
	}

}
