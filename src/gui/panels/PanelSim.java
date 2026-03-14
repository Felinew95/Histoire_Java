package gui.panels;

import java.awt.Graphics;

import javax.swing.JPanel;

import gui.PaintStrategy;
import moteur.carte.Carte;
import moteur.traitement.MobileManager;

public class PanelSim extends JPanel {

	private static final long serialVersionUID = 1426082937885460232L;
	private final Carte carte;
    private final PaintStrategy peindre;
    private final MobileManager mobileManager;
    
    public PanelSim(Carte carte, PaintStrategy peindre, MobileManager mobileManager) {
        this.carte = carte;
        this.peindre = peindre;
        this.mobileManager = mobileManager;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.peindre.peindreCarte(carte, g);
        this.peindre.peindreMobiles(mobileManager, g);
    }

}
