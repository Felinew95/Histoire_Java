package gui.fenetres;

import java.io.File;

import javax.swing.*;

import config.SimConfig;

import gui.management.MapManager;
import gui.panels.LoadingPanelGUI;
import gui.panels.PanelMainGUI;
import gui.panels.PanelMenuGUI;

import moteur.donnees.*;
import moteur.traitement.management.managers.civilisation.SimulationManager;

import utilitaire.SimulationUtility;

import static utilitaire.SimulationUtility.pause;

/**
 * Fenêtre principale de l'application graphique.
 *
 * <p>
 * Cette classe représente le point d'entrée de l'interface utilisateur.
 * Elle gère l'affichage des différents écrans (menu, chargement, simulation)
 * ainsi que la boucle principale de mise à jour du jeu.
 * </p>
 *
 * <p>
 * Elle implémente {@link Runnable} afin d'exécuter la simulation dans un thread
 * dédié, permettant une mise à jour continue de l'affichage et des données.
 * </p>
 *
 * @author Alexandre
 * @version 2.0
 */
public class MainGUI extends JFrame implements Runnable {

	/**
	 * Panneau principal contenant la simulation.
	 */
	private final PanelMainGUI panelMainGUI;

	/**
	 * Panneau affiché pendant le chargement.
	 */
	private final LoadingPanelGUI panelLoad;

	/**
	 * Indique si l'application doit être fermée.
	 */
	private boolean estQuitter = false;

	/**
	 * Indique si le chargement est terminé.
	 */
	private boolean estCharge = false;

	/**
	 * Indique si la simulation a été lancée.
	 */
	private boolean estLancer = false;

	/**
	 * Nombre d'événements déjà affichés dans la narration.
	 */
	private int nbEvenements;

	/**
	 * Gestionnaire principal de la simulation.
	 */
	private final SimulationManager sim;

	/**
	 * Civilisation actuellement simulée.
	 */
	private final Civilisation civilisation;

	/**
	 * Gestionnaire de la carte et de son affichage.
	 */
	private final MapManager mapManager;

	/**
	 * Initialise la fenêtre principale et ses composants.
	 *
	 * <p>
	 * Configure :
	 * <ul>
	 *     <li>Le titre et l'icône de la fenêtre</li>
	 *     <li>Les différents panneaux (menu, simulation, chargement)</li>
	 *     <li>Les gestionnaires nécessaires (simulation, carte)</li>
	 * </ul>
	 * </p>
	 */
	public MainGUI() {
		super(SimConfig.NOM_FENETRE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(SimulationUtility.readImage(new File("src/images/favicon.png")));

        PanelMenuGUI panelMenuGUI = new PanelMenuGUI(this);
		this.panelMainGUI = new PanelMainGUI();
		this.panelLoad = new LoadingPanelGUI();

		this.sim = this.panelMainGUI.getSimulation();
		this.civilisation = this.sim.getCivilisation();
		this.nbEvenements = civilisation.getNbEvenements();

		this.mapManager = new MapManager(sim, panelMainGUI.getPanelSim(), panelMainGUI.getPaintStrategy());

		this.setContentPane(panelMenuGUI);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(SimConfig.TAILLE_FENETRE_X, SimConfig.TAILLE_FENETRE_Y);
	}

	/**
	 * Lance la simulation.
	 *
	 * <p>
	 * Remplace le panneau courant par le panneau principal de simulation
	 * et active les indicateurs de chargement et de lancement.
	 * </p>
	 */
	public void lancerJeu() {
		this.setContentPane(panelLoad);
		this.estLancer = true;
		this.revalidate();
	}

	/**
	 * Quitte l'application.
	 *
	 * <p>
	 * Met à jour l'état interne puis termine le programme.
	 * </p>
	 */
	public void quitterJeu() {
		this.estQuitter = true;
		System.exit(0);
	}

	/**
	 * Affiche une boîte de dialogue contenant les crédits de l'application.
	 */
	public void afficherCredits() {
		JOptionPane.showMessageDialog(null, "Auteurs : Alexandre BURIN, Tauseef AHMED, Massinissa LOMANI",
				"Crédits", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Boucle principale de l'application.
	 *
	 * <p>
	 * Cette méthode gère les différentes phases de l'application :
	 * </p>
	 *
	 * <ul>
	 *     <li>Attente du lancement du jeu</li>
	 *     <li>Phase de chargement</li>
	 *     <li>Boucle de simulation continue</li>
	 * </ul>
	 *
	 * <p>
	 * Durant la boucle principale :
	 * <ul>
	 *     <li>Mise à jour de l'année affichée</li>
	 *     <li>Exécution d'un cycle de simulation</li>
	 *     <li>Mise à jour de la narration</li>
	 *     <li>Actualisation de la carte</li>
	 *     <li>Mise à jour des statistiques (population, économie, armée, etc.)</li>
	 *     <li>Rafraîchissement de l'affichage</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public void run() {
		while (!estLancer) {
			this.repaint();
		}

		while (!estCharge) {
			double pourcentage = this.panelLoad.getPourcentage();
			if (pourcentage == 100) {
				this.setContentPane(panelMainGUI);
				this.estCharge = true;
			}

			this.repaint();
		}

		while (!estQuitter && sim.getAnneeActuelle() < SimConfig.ANNEE_FIN_SIM) {
			this.panelMainGUI.getLabelValeurAnnee().setText(String.valueOf(sim.getAnneeActuelle()));

			sim.nextRound();

			int nvNbEvenements = civilisation.getNbEvenements();
			Evenement evenement = (nvNbEvenements > 0) ? civilisation.getEvenement(nvNbEvenements - 1) : null;
			setTexteNarration(nvNbEvenements, evenement);

			this.mapManager.chargerCarteRegion(sim.getAnneeActuelle(), evenement);

			// Mise à jour habitants
			JLabel labelNbHabitants = this.panelMainGUI.getLabelValeurNombreHabitants();
			labelNbHabitants.setText(String.valueOf(this.sim.getCivilisation().getPopulation().getNbHabitants()));

			// Mise à jour richesse
			updateStatsEconomie();

			// Mise à jour religion
			updateStatsReligion();

			// Mise à jour politique
			updateStatsPolitique();

			Armee armee = this.sim.getCivilisation().getArmee();
			updateStatsArmee(armee);

			this.repaint();
			pause(100);
		}
	}

	/**
	 * Met à jour les statistiques de l'armée dans l'interface graphique.
	 *
	 * @param armee armée à afficher
	 */
	private void updateStatsArmee(Armee armee) {
		float etat = armee.getEtat();
		int nbNavires = armee.getNombreNavires();
		int nbGuerriers = armee.getNombreGuerriers();

		JLabel labelValeurNombreNavires = this.panelMainGUI.getLabelValeurNombreNavires();
		labelValeurNombreNavires.setText(String.valueOf(nbNavires));

		JLabel labelValeurNombreGuerriers = this.panelMainGUI.getLabelValeurNombreGuerriers();
		labelValeurNombreGuerriers.setText(String.valueOf(nbGuerriers));

		JProgressBar barEtatArmee = this.panelMainGUI.getBarEtatArmee();
		barEtatArmee.setValue((int)etat);
		barEtatArmee.setString((int)etat + "%");
	}

	/**
	 * Met à jour les statistiques économiques dans l'interface graphique.
	 *
	 * <p>
	 * Ajuste dynamiquement la barre de progression si la richesse dépasse
	 * le maximum actuel.
	 * </p>
	 */
	private void updateStatsEconomie() {
		Economie economie = this.sim.getCivilisation().getEconomie();

		int stycas = (int) economie.getStycas();
		JProgressBar barRichesse = this.panelMainGUI.getBarRichesse();

		barRichesse.setValue(stycas);
		barRichesse.setStringPainted(true);
		barRichesse.setString(economie.getStycas() + "S");

		if (barRichesse.getMaximum() <= stycas) {
			barRichesse.setMaximum(stycas * 2);
		}
	}

	/**
	 * Met à jour les statistiques de la religion dans l'interface graphique.
	 *
	 * <p>
	 * Si aucune religion n'est définie, aucune mise à jour n'est effectuée.
	 * </p>
	 */
	private void updateStatsReligion() {
		Religion religion = this.sim.getCivilisation().getReligion();
		if (religion == null) {
			return;
		}

		// Nom de la religion
		this.panelMainGUI.getLabelValeurNomReligion().setText(religion.getNom());

		// Barre d'influence (0 à 100)
		JProgressBar barInfluence = this.panelMainGUI.getBarInfluenceReligion();
		int influence = (int) religion.getInfluence();
		barInfluence.setValue(influence);
		barInfluence.setString(influence + "%");
	}

	/**
	 * Met à jour les statistiques politiques dans l'interface graphique.
	 *
	 * <p>
	 * Affiche le type de régime ainsi que son niveau de stabilité.
	 * </p>
	 */
	private void updateStatsPolitique() {
		Politique politique = this.sim.getCivilisation().getPolitique();
		if (politique == null) {
			return;
		}

		// Type de régime
		this.panelMainGUI.getLabelValeurTypeRegime()
				.setText(politique.getTypeRegime() + " — " + politique.getEtatStabilite());
	}

	/**
	 * Met à jour le texte de narration affiché.
	 *
	 * <p>
	 * Le texte est mis à jour uniquement lorsqu'un nouvel événement apparaît.
	 * </p>
	 *
	 * @param nvNbEvenements nombre actuel d'événements
	 * @param evenement dernier événement survenu
	 */
	private void setTexteNarration(int nvNbEvenements, Evenement evenement) {
		if (nbEvenements < nvNbEvenements) {
			JTextPane textNarration = this.panelMainGUI.getTexteNarration();
			String infos = evenement.toString();
			String narration = evenement.getNarration();

			textNarration.setText(infos + "\n" + narration);
			nbEvenements = nvNbEvenements;
		}
	}

}