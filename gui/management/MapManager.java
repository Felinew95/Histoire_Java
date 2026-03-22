package gui.management;

import config.SimConfig;

import gui.panels.PanelSim;

import moteur.carte.Continent;
import moteur.donnees.Evenement;
import moteur.traitement.builders.CarteBuilder;
import moteur.traitement.builders.MobileBuilder;
import moteur.traitement.management.MobileManager;
import moteur.traitement.management.Simulation;

/**
 * Classe qui permet de gérer l'affichage des cartes
 * 
 * @author Massinissa et Alexandre
 * @version 1.0
 */
public class MapManager {
	
	// Attributs 
	private final Simulation simulation;
	private final PanelSim panelSim;
	private final PaintStrategy paintStrategy;
	
	private String derniereRegion = "";
	
	/**
	 * Constructeur de la classe MapManager
	 * 
	 * @param simulation : Le coeur de la simulation
	 * @param panelSim : Panel graphique de la simulation
	 * @param paintStrategy : Méthodes d'affichage de la carte
	 */
	public MapManager(Simulation simulation, PanelSim panelSim, PaintStrategy paintStrategy) {
		this.simulation = simulation;
		this.panelSim = panelSim;
		this.paintStrategy = paintStrategy;
	}

	/**
	 * Méthode qui permet de charger la carte d'une région 
	 * 
	 * @param anneeSim : Année actuelle de la simulation 
	 * @param evenementActuel : Evénement actuel de la simulation 
	 */
	public void chargerCarteRegion(int anneeSim, Evenement evenementActuel) {
		String nouvelleRegion;
		if (evenementActuel == null || anneeSim < evenementActuel.getAnneeDebut() || anneeSim > evenementActuel.getAnneeFin()) {
	        nouvelleRegion = "EuropeDuNord";
	        simulation.setIntervalleAnneeMS(SimConfig.INTERVALLE_TEMPS_ANNEE_EUROPE_MS);
	    } else {
	        nouvelleRegion = evenementActuel.getRegion().getNom();
	        simulation.setIntervalleAnneeMS(SimConfig.INTERVALLE_TEMPS_ANNEE_CARTE_MS);
	    }
		
		if (nouvelleRegion.equals(derniereRegion)) {
	        return;
	    }
		
		derniereRegion = nouvelleRegion;
		switch (derniereRegion) {
			case "Angleterre":
				chargerGrandeBretagne();
				break;
			case "Irlande":
				chargerGrandeBretagne();
				break;
			case "Islande":
				chargerIslande();
				break;
			case "Scandinavie":
				chargerScandinave();
				break;
			case "Normandie":
				chargerNormandie();
				break;
			case "Vinland":
				chargerVinland();
				break;
			case "Lindsfarne":
				chargerLindsfarne();
				break;
			case "EuropeDuNord":
			    chargerEurope();
			    break;
			case "Groenland":
				chargerEurope();
				break;
		}
	}
		
	/**
	 * Méthode qui permet de charger une carte 
	 * 
	 * @param continent : Un continent 
	 * @param mobileManager : Un mobile manager 
	 * @param tailleBloc : Taille des blocs de la map
	 * @param estEurope : Etat si la carte est l'europe ou non
	 */
	private void chargerCarte(Continent continent, MobileManager mobileManager, int tailleBloc, boolean estEurope) {
	    this.simulation.getCarte().viderContinent();
	    this.simulation.getCarte().setContinent(continent);
	    
	    this.simulation.getMobileManager().setManager(mobileManager.getManager());
	    this.simulation.getCarte().placerMobiles(this.simulation.getMobileManager());
	    
	    this.paintStrategy.setTailleBloc(tailleBloc);
	    this.paintStrategy.setEstEurope(estEurope);
	    
	    this.panelSim.repaint();
	}

	/**
	 * Méthode qui permet de charger la carte de l'europe
	 */
	private void chargerEurope() {
	    chargerCarte(CarteBuilder.buildEurope(), MobileBuilder.buildMobileManagerEurope(), SimConfig.TAILLE_BLOC_EUROPE, true);
	}
	
	/**
	 * Méthode qui permet de charger la carte de lindsfarne
	 */
	private void chargerLindsfarne() {
	    chargerCarte(CarteBuilder.buildLindsfarne(), MobileBuilder.buildMobileManagerLindsfarne(), SimConfig.TAILLE_BLOC_CARTE, false);
	}
	
	/**
	 * Méthode qui permet de charger la carte de la grande bretagne
	 */
	private void chargerGrandeBretagne() {
		chargerCarte(CarteBuilder.buildGrandeBretagne(), MobileBuilder.buildMobileManagerGrandeBretagne(), SimConfig.TAILLE_BLOC_CARTE, false);
	}
	
	/**
	 * Méthode qui permet de charger la carte de l'islande
	 */
	private void chargerIslande() {
		chargerCarte(CarteBuilder.buildIslande(), MobileBuilder.buildMobileManagerIslande(), SimConfig.TAILLE_BLOC_CARTE, false);
	}
	
	/**
	 * Méthode qui permet de charger la carte de la normandie
	 */
	private void chargerNormandie() {
		chargerCarte(CarteBuilder.buildNormandie(), MobileBuilder.buildMobileManagerNormandie(), SimConfig.TAILLE_BLOC_CARTE, false);
	}
	
	/**
	 * Méthode qui permet de charger la carte de la scandinavie
	 */
	private void chargerScandinave() {
		chargerCarte(CarteBuilder.buildScandinave(), MobileBuilder.buildMobileManagerScandinavie(), SimConfig.TAILLE_BLOC_CARTE, false);
	}
	
	/**
	 * Méthode qui permet de charger la carte du vinland
	 */
	private void chargerVinland() {
		chargerCarte(CarteBuilder.buildVinland(), MobileBuilder.buildMobileManagerVinland(), SimConfig.TAILLE_BLOC_CARTE, false);
	}
	
}
