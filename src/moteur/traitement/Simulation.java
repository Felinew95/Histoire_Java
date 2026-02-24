package moteur.traitement;

import moteur.donnees.Civilisation;

/**
 * Classe qui représente le traitement des données de la simulation 
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Civilisation
 */
public class Simulation {

	// Attributs 
	private int anneeActuelle;
	private final Civilisation civilisation;

	/**
	 * Constructeur de la classe Simulation 
	 * @param annee_actuelle
	 * @param civilisation
	 */
	public Simulation(int annee_actuelle, Civilisation civilisation) {
		this.anneeActuelle = annee_actuelle;
		this.civilisation = civilisation;
	}

	public int getAnneeActuelle() {
		return anneeActuelle;
	}

	public void setAnneeActuelle(int annee_actuelle) {
		this.anneeActuelle = annee_actuelle;
	}

	public Civilisation getCivilisation() {
		return civilisation;
	}

}
