package gui.fenetres;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import config.SimConfig;
import gui.management.MapManager;
import gui.panels.LoadingPanelGUI;
import gui.panels.PanelMainGUI;
import gui.panels.PanelMenuGUI;
import moteur.donnees.Civilisation;
import moteur.donnees.Economie;
import moteur.donnees.Evenement;
import moteur.traitement.management.managers.SimulationManager;
import utilitaire.SimulationUtility;

/**
 * Classe qui gère l'affichage graphique 
 * 
 * @author Alexandre 
 * @version 1.0
 */
public class MainGUI extends JFrame implements Runnable {

	// Attributs 
	private static final long serialVersionUID = 1L;
	
	private final PanelMenuGUI panelMenuGUI;
	private final PanelMainGUI panelMainGUI;
	private final LoadingPanelGUI panelLoad;
	
	private boolean estQuitter = false;
	private boolean estCharge = false;
	private boolean estLancer = false;
	
	private int nbEvenements;
	private SimulationManager sim;
	private Civilisation civilisation;
	private MapManager mapManager;

	public MainGUI() {
		super(SimConfig.NOM_FENETRE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(SimulationUtility.readImage(new File("src/images/favicon.png")));
				
		this.panelMenuGUI = new PanelMenuGUI(this);
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

			JLabel labelNbHabitants = this.panelMainGUI.getLabelValeurNombreHabitants();
			labelNbHabitants.setText(String.valueOf(this.sim.getCivilisation().getPopulation().getNbHabitants()));

			Economie economie = this.sim.getCivilisation().getEconomie();

			int stycas = (int) economie.getStycas();
			JProgressBar barRichesse = this.panelMainGUI.getBarRichesse();
			barRichesse.setValue(stycas);
			barRichesse.setStringPainted(true);
			barRichesse.setString(economie.getStycas() + "S");

			if (barRichesse.getMaximum() <= stycas) {
				barRichesse.setMaximum(stycas*2);
			}

			this.repaint();
			pause(100);
		}
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

	private void pause(int temps) {
		try {
		    Thread.sleep(temps);
		} catch (InterruptedException e) {
		    Thread.currentThread().interrupt();
		}
	}
	
}
