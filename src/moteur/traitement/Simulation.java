package moteur.traitement;

import moteur.carte.Carte;
import moteur.donnees.simulation.Civilisation;

/**
 * Classe qui représente le traitement des données de la simulation 
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Civilisation
 * @see PersonnageManager
 * @see Carte
 */
public class Simulation {

	// Attributs 
	private int anneeActuelle;
	private final Carte carte;
	private final Civilisation civilisation;
	private final PersonnageManager personnageManager = new PersonnageManager();

	/**
	 * Constructeur de la classe Simulation 
	 * @param annee_actuelle
	 * @param civilisation
	 */
	public Simulation(int anneeActuelle, Carte carte, Civilisation civilisation) {
		this.anneeActuelle = anneeActuelle;
		this.carte = carte;
		this.civilisation = civilisation;
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

	public PersonnageManager getPersonnageManager() {
		return personnageManager;
	}

	public void setAnneeActuelle(int anneeActuelle) {
		this.anneeActuelle = anneeActuelle;
	}
	
	public void avancerAnneeActuelle() {
		this.anneeActuelle++;
	}
	
}
