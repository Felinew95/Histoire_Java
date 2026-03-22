package gui.fenetres;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import config.SimConfig;
import gui.management.MapManager;
import gui.panels.LoadingPanelGUI;
import gui.panels.PanelMainGUI;
import gui.panels.PanelMenuGUI;
import moteur.donnees.Civilisation;
import moteur.donnees.Evenement;
import moteur.traitement.management.Simulation;

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
	private Simulation sim;
	private Civilisation civilisation;
	private MapManager mapManager;

	public MainGUI() {
		super("Histoire");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setImage();
				
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
	
	private void setImage() {
		try {
			this.setIconImage(ImageIO.read(new File("src/images/favicon.png")));
		} catch (IOException e) {
			
		}
	}
	
	public void lancerJeu() {
	    this.setContentPane(panelLoad);
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
			pause();
		}
		
		while (!estQuitter) {
			this.panelMainGUI.getLabelValeurAnnee().setText(String.valueOf(sim.getAnneeActuelle()));
			sim.nextRound();
			
			int nvNbEvenements = civilisation.getNbEvenements();
			Evenement evenement = (nvNbEvenements > 0) ? civilisation.getEvenement(nvNbEvenements - 1) : null;
			setTexteNarration(nvNbEvenements, evenement);
			this.mapManager.chargerCarteRegion(sim.getAnneeActuelle(), evenement);
			
			this.repaint();
			pause();
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

	private void pause() {
		try {
		    Thread.sleep(100); 
		} catch (InterruptedException e) {
		    Thread.currentThread().interrupt();
		}
	}
	
}
