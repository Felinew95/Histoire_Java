package gui.management;

import config.SimConfig;
import gui.panels.PanelSim;
import moteur.carte.Continent;
import moteur.donnees.Evenement;
import moteur.traitement.builders.CarteBuilder;
import moteur.traitement.builders.MobileBuilder;
import moteur.traitement.management.managers.civilisation.SimulationManager;

import java.util.Objects;

/**
 * Classe responsable de la gestion de l'affichage des cartes dans la simulation.
 *
 * <p>
 * MapManager gère :
 * </p>
 *
 * <ul>
 *     <li>Le chargement des cartes des différentes régions selon l'année de simulation et les événements.</li>
 *     <li>La liaison avec le {@link SimulationManager} pour placer les mobiles sur la carte.</li>
 *     <li>L'actualisation de l'affichage via le {@link PanelSim} et la stratégie graphique {@link PaintStrategy}.</li>
 * </ul>
 *
 * <p>
 * Cette classe encapsule la logique pour sélectionner automatiquement la carte à afficher
 * et gérer le placement des mobiles selon la région active.
 * </p>
 *
 * @author Massinissa
 * @author Alexandre
 * @version 1.0
 */
public class MapManager {

	/**
	 * Manager de la simulation
	 * */
	private final SimulationManager simulation;

	/**
	 * Panel graphique pour l'affichage de la simulation
	 */
	private final PanelSim panelSim;

	/**
	 * Stratégie de peinture utilisée pour afficher la carte
	 */
	private final PaintStrategy paintStrategy;

	/**
	 * Nom de la dernière région affichée *
	 */
	private String derniereRegion = "";

	/**
	 * Constructeur de la classe MapManager.
	 *
	 * @param simulation     Le manager central de la simulation
	 * @param panelSim       Panel graphique de la simulation pour l'affichage
	 * @param paintStrategy  Stratégie d'affichage graphique de la carte
	 */
	public MapManager(SimulationManager simulation, PanelSim panelSim, PaintStrategy paintStrategy) {
		this.simulation = simulation;
		this.panelSim = panelSim;
		this.paintStrategy = paintStrategy;
	}

	/**
	 * Charge la carte correspondant à la région active.
	 *
	 * <p>
	 * Cette méthode vérifie si la région a changé par rapport à la dernière carte affichée.
	 * Si c'est le cas, elle met à jour l'affichage avec la nouvelle carte.
	 * </p>
	 *
	 * @param anneeSim         Année actuelle de la simulation
	 * @param evenementActuel  Événement en cours pouvant influencer la région
	 */
	public void chargerCarteRegion(int anneeSim, Evenement evenementActuel) {
		String nouvelleRegion = getNouvelleRegion(anneeSim, evenementActuel);

		if (nouvelleRegion.equals(derniereRegion)) return;
		derniereRegion = nouvelleRegion;

		choixRegion();
	}

	/**
	 * Détermine la région à afficher selon l'année et l'événement.
	 * <p>
	 * Si aucun événement n'est actif, la région par défaut est "EuropeDuNord".
	 * </p>
	 *
	 * @param anneeSim         Année actuelle de la simulation
	 * @param evenementActuel  Événement actuel
	 * @return Nom de la région à afficher
	 */
	private String getNouvelleRegion(int anneeSim, Evenement evenementActuel) {
		String nouvelleRegion;
		if (evenementActuel == null || anneeSim < evenementActuel.getAnneeDebut() || anneeSim > evenementActuel.getAnneeFin()) {
			nouvelleRegion = "EuropeDuNord";
			simulation.setIntervalleAnneeMS(SimConfig.INTERVALLE_TEMPS_ANNEE_EUROPE_MS);
		} else {
			nouvelleRegion = evenementActuel.getRegion().getNom();
			simulation.setIntervalleAnneeMS(SimConfig.INTERVALLE_TEMPS_ANNEE_CARTE_MS);
		}
		return nouvelleRegion;
	}

	/**
	 * Choisit la carte à charger selon le nom de la région courante.
	 */
	private void choixRegion() {
		switch (derniereRegion) {
			case "Angleterre":
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
			case "Groenland":
				chargerEurope();
				break;
		}
	}

	/**
	 * Charge une carte générique avec un continent, un MobileManager et une taille de bloc.
	 *
	 * @param continent      Continent à afficher
	 * @param mobileManager  Manager des mobiles à placer sur la carte
	 * @param tailleBloc     Taille de chaque bloc graphique de la carte
	 * @param estEurope      Indique si la carte correspond à l'Europe
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
	 * Charge la carte de l'Europe
	 */
	private void chargerEurope() {
		chargerCarte(CarteBuilder.buildEurope(), Objects.requireNonNull(MobileBuilder.buildMobileManagerEurope()), SimConfig.TAILLE_BLOC_EUROPE, true);
	}

	/**
	 * Charge la carte de Lindsfarne
	 */
	private void chargerLindsfarne() {
		chargerCarte(CarteBuilder.buildLindsfarne(), Objects.requireNonNull(MobileBuilder.buildMobileManagerLindsfarne()), SimConfig.TAILLE_BLOC_CARTE, false);
	}

	/**
	 * Charge la carte de la Grande-Bretagne
	 */
	private void chargerGrandeBretagne() {
		chargerCarte(CarteBuilder.buildGrandeBretagne(), Objects.requireNonNull(MobileBuilder.buildMobileManagerGrandeBretagne()), SimConfig.TAILLE_BLOC_CARTE, false);
	}

	/**
	 * Charge la carte de l'Islande
	 */
	private void chargerIslande() {
		chargerCarte(CarteBuilder.buildIslande(), Objects.requireNonNull(MobileBuilder.buildMobileManagerIslande()), SimConfig.TAILLE_BLOC_CARTE, false);
	}

	/**
	 * Charge la carte de la Normandie
	 */
	private void chargerNormandie() {
		chargerCarte(CarteBuilder.buildNormandie(), Objects.requireNonNull(MobileBuilder.buildMobileManagerNormandie()), SimConfig.TAILLE_BLOC_CARTE, false);
	}

	/**
	 * Charge la carte de la Scandinavie
	 */
	private void chargerScandinave() {
		chargerCarte(CarteBuilder.buildScandinave(), Objects.requireNonNull(MobileBuilder.buildMobileManagerScandinavie()), SimConfig.TAILLE_BLOC_CARTE, false);
	}

	/**
	 * Charge la carte du Vinland
	 */
	private void chargerVinland() {
		chargerCarte(CarteBuilder.buildVinland(), Objects.requireNonNull(MobileBuilder.buildMobileManagerVinland()), SimConfig.TAILLE_BLOC_CARTE, false);
	}

}