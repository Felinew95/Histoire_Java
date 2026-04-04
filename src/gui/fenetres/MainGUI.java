package gui.fenetres;

import java.io.File;
import java.io.Serial;

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
 * Classe qui gère l'affichage graphique
 *
 * @author Alexandre
 * @version 1.1
 */
public class MainGUI extends JFrame implements Runnable {

	// Attributs
	@Serial
    private static final long serialVersionUID = 1L;

    private final PanelMainGUI panelMainGUI;
	private final LoadingPanelGUI panelLoad;

	private boolean estQuitter = false;
	private boolean estCharge = false;
	private boolean estLancer = false;

	private int nbEvenements;
	private final SimulationManager sim;
	private final Civilisation civilisation;
	private final MapManager mapManager;

	public MainGUI() {
		super(SimConfig.NOM_FENETRE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(SimulationUtility.readImage(new File("src/images/favicon.png")));

        PanelMenuGUI panelMenuGUI = new PanelMenuGUI(this);
		this.panelMainGUI = new PanelMainGUI(this);
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

	public void lancerJeu() {
		this.setContentPane(panelMainGUI);
		this.estCharge = true;
		this.estLancer = true;
		this.revalidate();
	}

	public void quitterJeu() {
		this.estQuitter = true;
		System.exit(0);
	}

	public void afficherCredits() {
		JOptionPane.showMessageDialog(null, "Auteurs : Alexandre BURIN, Tauseef AHMED, Massinissa LOMANI",
				"Crédits", JOptionPane.INFORMATION_MESSAGE);
	}

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

		while (!estQuitter) {
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

			this.repaint();
			pause(100);
		}
	}

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
	 * Met à jour les stats de religion dans le GUI
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
	 * Met à jour les stats de politique dans le GUI
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