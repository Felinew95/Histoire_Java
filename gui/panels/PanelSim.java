package gui.panels;

import java.awt.Graphics;

import javax.swing.JPanel;

import gui.management.PaintStrategy;

import moteur.carte.Carte;
import moteur.traitement.management.Simulation;

/**
 * Panneau de simulation — affiche la carte
 * 
 * @author Massinissa 
 * @version 1.0
 */
public class PanelSim extends JPanel {

	// Attributs
	private static final long serialVersionUID = 1426082937885460232L;
	private final Carte carte;
    private final PaintStrategy peindre;
    private final Simulation simulation;
    
    /**
     * Constructeur de la classe PanelSim
     * 
     * @param carte      : Carte de la simulation 
     * @param peindre    : Méthodes d'affichage de la carte 
     * @param simulation : Le coeur de la simulation
     */
    public PanelSim(Carte carte, PaintStrategy peindre, Simulation simulation) {
        this.carte = carte;
        this.peindre = peindre;
        this.simulation = simulation;
    }
    
    /**
     * Méthode qui permet d'afficher graphiquement les différents composants 
     * 
     * @param g : Composant graphique pour l'affichage
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.peindre.peindreCarte(carte, g);
        this.peindre.peindreMobiles(simulation.getMobileManager(), g, simulation.getAnneeActuelle());
    }

}
 