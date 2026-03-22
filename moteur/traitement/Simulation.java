package moteur.traitement;

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
	
	private long dernierTemps = 0;
	private static final long INTERVALLE_DEPLACEMENT_MOBILE_MS = SimConfig.INTERVALLE_DEPLACEMENT_MS;
	
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
	
	public void avancerAnneeActuelle() {
		this.anneeActuelle++;
	}
	
	public void nextRound() {
		long tempsInstant = System.currentTimeMillis();
		if (tempsInstant - dernierTemps < INTERVALLE_DEPLACEMENT_MOBILE_MS) {
			return;
		}
		
		dernierTemps = tempsInstant;
		mobileManager.updateAllMobiles(carte);
		
		evenementManager.verifierEvenementsHistoriques(anneeActuelle, civilisation);
		avancerAnneeActuelle();
	}
	
}
