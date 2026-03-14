package gui.fenetres;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.SimConfig;
import gui.panels.LoadingPanelGUI;
import gui.panels.PanelMainGUI;
import gui.panels.PanelMenuGUI;

import moteur.traitement.Simulation;

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

	public MainGUI() {
		super("Histoire");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setImage();
				
		this.panelMenuGUI = new PanelMenuGUI(this);
		this.panelMainGUI = new PanelMainGUI(this);
		this.panelLoad = new LoadingPanelGUI();
		
		this.setContentPane(panelMenuGUI);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(SimConfig.TAILLE_FENETRE_X, SimConfig.TAILLE_FENETRE_Y);
	}
	
	private void setImage() {
		try {
			this.setIconImage(ImageIO.read(new File("src/images/favicon.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
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
		}
		
		while (!estQuitter) {
			Simulation s = this.panelMainGUI.getSimulation();
			this.panelMainGUI.getLabelValeurAnnee().setText(String.valueOf(s.getAnneeActuelle()));
			s.nextRound();
			
			this.repaint();
		}
	}
	
}
