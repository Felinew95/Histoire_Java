package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.SimConfig;

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
	
	private boolean estQuitter = false;

	public MainGUI() {
		super("Histoire");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setImage();
				
		this.panelMenuGUI = new PanelMenuGUI(this);
		this.panelMainGUI = new PanelMainGUI(this);
		
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
	    this.setContentPane(panelMainGUI);
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
		while (!estQuitter) {
			this.repaint();
		}
	}
	
}
