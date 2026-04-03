package moteur.traitement.management.managers;

import config.SimConfig;
import gui.management.ChartManager;
import gui.management.MobileManager;
import moteur.carte.Carte;
import moteur.donnees.*;

import moteur.traitement.management.managers.armee.ArmeeManager;
import moteur.traitement.management.managers.economie.EconomieManager;
import moteur.traitement.management.managers.evenement.EvenementHistoriqueManager;
import moteur.traitement.management.managers.politique.PolitiqueManager;
import moteur.traitement.management.managers.politique.ReligionManager;
import moteur.traitement.management.managers.population.PopulationManager;

import static utilitaire.SimulationUtility.getEvenementActuel;

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
	private int nvNbEvenements;

	private long dernierTempsMobile;
	private long dernierTempsAnnee;

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

	public void setIntervalleAnneeMS(long intervalleAnneeMS) {
		this.intervalleAnneeMS = intervalleAnneeMS;
	}

	public int getNvNbEvenements() {
		return nvNbEvenements;
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
        long intervalleDeplacementMobileMS = SimConfig.INTERVALLE_DEPLACEMENT_MS;
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

		nvNbEvenements = civilisation.getNbEvenements();
		Evenement evenement = getEvenementActuel(civilisation, anneeActuelle, nvNbEvenements);

		// Update population
		int nbHabitants = population.getNbHabitants();
		PopulationManager.updatePopulation(population, evenement);

        // Update économie
		if (economie != null) {
			EconomieManager.updateEconomie(economie, nbHabitants, evenement);
			chartManager.ajouterPointLineChart(anneeActuelle, economie.getStycas());
			chartManager.updateRessourcesPieChart(economie);
		}

		if (armee != null) {
			ArmeeManager.updateArmee(armee, evenement);
		}

		Religion religion = civilisation.getReligion();
		if (religion != null) {
			ReligionManager.updateReligion(religion, evenement);
		}

		Politique politique = civilisation.getPolitique();
		if (politique != null) {
			PolitiqueManager.updatePolitique(politique, evenement);
		}

	}

}