package gui;

import javax.swing.JFrame;

import config.SimConfig;
import moteur.carte.Carte;

public class MainGUI extends JFrame implements Runnable {

    private Carte carte;
    private PeindreCarte peindre;

    private PanelTest test;

    public MainGUI() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.carte = new Carte(SimConfig.TAILLE_X, SimConfig.TAILLE_Y);
        this.peindre = new PeindreCarte();
        this.test = new PanelTest(carte, peindre);

        this.setContentPane(test);

        this.setSize(SimConfig.TAILLE_X, SimConfig.TAILLE_Y);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void run() {
        while (true) {
            this.test.repaint();
        }
    }

}
