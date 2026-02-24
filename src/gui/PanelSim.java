package gui;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;
import moteur.carte.Carte;

public class PanelSim extends JPanel {

	private static final long serialVersionUID = 1426082937885460232L;
	
	private final Carte carte;
    private final PaintStrategy peindre;
    Deplacement lol = new Deplacement();
    
    public PanelSim(Carte carte, PaintStrategy peindre) {
        this.carte = carte;
        this.peindre = peindre;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.peindre.peindreEau(carte, g);
        this.peindre.peindreTerres(carte, g);
        try {
			lol.paint(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
