package moteur.traitement.management;

import config.SimConfig;
import moteur.carte.Carte;
import moteur.donnees.Civilisation;

/**
 * Classe qui représente le traitement des données de la simulation 
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Civilisation
 * @see MobileManager
 * @see Carte
 */
public class Simulation {

	// Attributs 
	private int anneeActuelle;
	private final Carte carte;
	private final Civilisation civilisation;
	private final MobileManager mobileManager;
	private final EvenementHistoriqueManager evenementManager;
	
	private long dernierTempsMobile = 0;
	private long dernierTempsAnnee = 0;
	
	private final long intervalleDeplacementMobileMS = SimConfig.INTERVALLE_DEPLACEMENT_MS;
	private long intervalleAnneeMS = SimConfig.INTERVALLE_TEMPS_ANNEE_EUROPE_MS;
	
	public Simulation(int anneeActuelle, Carte carte, Civilisation civilisation, MobileManager mobileManager, EvenementHistoriqueManager evenementManager) {
		this.anneeActuelle = anneeActuelle;
		this.carte = carte;
		this.civilisation = civilisation;
		this.mobileManager = mobileManager;
		this.evenementManager = evenementManager;
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
		avancerAnneeActuelle();
	}
	
}
