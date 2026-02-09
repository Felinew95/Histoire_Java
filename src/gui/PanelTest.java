package gui;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;

import moteur.carte.Carte;

public class PanelTest extends JPanel {

    private Carte carte;
    private PeindreCarte peindre;

    public PanelTest(Carte carte, PeindreCarte peindre) {
        this.carte = carte;
        this.peindre = peindre;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            this.peindre.peindreEau(carte, g);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
