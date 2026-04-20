package gui.panels;

import java.awt.Graphics;

import javax.swing.JPanel;

import gui.management.PaintStrategy;

import moteur.carte.Carte;
import gui.management.MobileManager;
import moteur.traitement.management.managers.civilisation.SimulationManager;

/**
 * Panneau de simulation graphique.
 *
 * <p>
 * Cette classe étend {@link JPanel} et sert à afficher la carte et les entités mobiles
 * de la simulation. Elle utilise une stratégie de peinture {@link PaintStrategy} pour
 * rendre la carte et les objets mobiles, ce qui permet de séparer la logique de dessin
 * de la logique de simulation.
 * </p>
 *
 * <p>
 * La méthode {@link #paintComponent(Graphics)} est surchargée pour dessiner la carte
 * ainsi que les entités mobiles à chaque mise à jour du panneau.
 * </p>
 *
 * @author Massinissa
 * @version 1.0
 */
public class PanelSim extends JPanel {

    /**
     * Identifiant de sérialisation pour la compatibilité entre versions
     */
    private static final long serialVersionUID = 1426082937885460232L;

    /**
     * La carte principale de la simulation à afficher
     */
    private final Carte carte;

    /**
     * Stratégie de peinture utilisée pour dessiner la carte et les mobiles
     */
    private final PaintStrategy peindre;

    /**
     * Gestionnaire principal de la simulation
     */
    private final SimulationManager simulation;

    /**
     * Constructeur du panneau de simulation.
     *
     * <p>
     * Initialise les références à la carte, à la stratégie de peinture et au gestionnaire de simulation.
     * </p>
     *
     * @param carte      La carte de la simulation à afficher
     * @param peindre    La stratégie de peinture utilisée pour le rendu graphique
     * @param simulation Le gestionnaire central de la simulation contenant les mobiles et l'état actuel
     */
    public PanelSim(Carte carte, PaintStrategy peindre, SimulationManager simulation) {
        this.carte = carte;
        this.peindre = peindre;
        this.simulation = simulation;
    }

    /**
     * Surcharge de la méthode paintComponent pour dessiner le contenu du panneau.
     *
     * <p>
     * Cette méthode appelle d'abord {@code super.paintComponent(g)} pour effacer l'ancien dessin,
     * puis utilise la stratégie de peinture pour dessiner :
     * <ul>
     *   <li>La carte principale</li>
     *   <li>Les entités mobiles gérées par le {@link MobileManager}</li>
     * </ul>
     * </p>
     *
     * @param g Composant graphique utilisé pour dessiner sur le JPanel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.peindre.paint(carte, g);
        this.peindre.paint(simulation.getMobileManager(), simulation.getAnneeActuelle(), g);
    }

}