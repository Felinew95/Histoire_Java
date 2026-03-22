package gui.panels;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import gui.management.PaintStrategy;
import moteur.carte.Carte;
import moteur.traitement.Simulation;

/**
 * Panneau de simulation — affiche la carte et gère les clics de navigation.
 *
 * Sur la carte Europe, chaque région est matérialisée par un label cartographique
 * médiéval (voir PaintStrategy.peindreLabelsRegions). Les zones cliquables sont
 * centrées sur la même position (col, lig) que le label correspondant, avec
 * une tolérance de ±2 blocs pour faciliter la sélection.
 *
 * Positions des labels (col, lig en blocs, TAILLE_BLOC_EUROPE = 10) :
 *   Vinland         — col  3, lig  3
 *   Islande         — col  9, lig  3
 *   Scandinavie     — col 95, lig  8
 *   Grande-Bretagne — col 52, lig 35
 *   Lindisfarne     — col 62, lig 30
 *   Normandie       — col 70, lig 43
 *
 * Sur une sous-carte, un clic sur l'image retour_europe.png (coin haut-gauche)
 * ramène vers la carte Europe.
 */
public class PanelSim extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1426082937885460232L;

	private final Carte carte;
    private final PaintStrategy peindre;
    private final PanelMainGUI panelMainGUI;
    private final Simulation simulation;
    
    public PanelSim(Carte carte, PaintStrategy peindre, PanelMainGUI panelMainGUI, Simulation simulation) {
        this.carte = carte;
        this.peindre = peindre;
        this.panelMainGUI = panelMainGUI;
        this.simulation = simulation;
        this.addMouseListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.peindre.peindreCarte(carte, g);
        this.peindre.peindreMobiles(simulation.getMobileManager(), g);
    }

    /**
     * Vérifie si le clic (en blocs) tombe dans la zone d'un label.
     * Tolérance : ±2 blocs en col et en lig autour de la position du label.
     */
    private boolean estDansZone(int col, int lig, int colLabel, int ligLabel) {
        return col >= colLabel - 2 && col <= colLabel + 2
            && lig >= ligLabel - 2 && lig <= ligLabel + 2;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
	    int taille_bloc = peindre.getTailleBloc();

	    // --- Navigation depuis la carte Europe vers les régions ---
	    if (!peindre.estLindsfarne()) {
	        int col = e.getX() / taille_bloc;
	        int lig = e.getY() / taille_bloc;

	        // Vinland — col 3, lig 3
	        if (estDansZone(col, lig, 3, 3)) {
	            panelMainGUI.chargerVinland();
	            return;
	        }
	        // Islande — col 9, lig 3
	        if (estDansZone(col, lig, 9, 3)) {
	            panelMainGUI.chargerIslande();
	            return;
	        }
	        // Scandinavie — col 95, lig 8
	        if (estDansZone(col, lig, 95, 8)) {
	            panelMainGUI.chargerScandinave();
	            return;
	        }
	        // Grande-Bretagne — col 52, lig 35
	        if (estDansZone(col, lig, 52, 35)) {
	            panelMainGUI.chargerGrandeBretagne();
	            return;
	        }
	        // Lindisfarne — col 62, lig 30
	        if (estDansZone(col, lig, 62, 30)) {
	            panelMainGUI.chargerLindsfarne();
	            return;
	        }
	        // Normandie — col 70, lig 43
	        if (estDansZone(col, lig, 70, 43)) {
	            panelMainGUI.chargerNormandie();
	            return;
	        }
	    }

	    // --- Retour vers l'Europe depuis n'importe quelle sous-carte ---
	    if (peindre.estLindsfarne()) {
	        int largeurImage = taille_bloc * 8;
	        int hauteurImage = taille_bloc * 5;
	        if (e.getX() >= 0 && e.getX() <= largeurImage
	                && e.getY() >= 0 && e.getY() <= hauteurImage) {
	            panelMainGUI.chargerEurope();
	        }
	    }
	}

	@Override public void mousePressed(MouseEvent e)  {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e)  {}
	@Override public void mouseExited(MouseEvent e)   {}
}
 