package gui.panels.main;

import config.SimConfig;
import config.SpritesRepository;
import gui.management.ChartManager;
import gui.management.PaintStrategy;
import gui.panels.PanelSim;
import moteur.carte.Continent;
import moteur.donnees.*;
import moteur.traitement.builders.CarteBuilder;
import moteur.traitement.builders.EvenementHistoriqueBuilder;
import moteur.traitement.builders.MobileBuilder;
import moteur.traitement.management.factory.CivilisationFactory;
import moteur.traitement.management.factory.MapFactory;
import moteur.traitement.management.managers.civilisation.CivilisationManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Panneau principal de l'interface graphique de la simulation.
 *
 * <p>
 * Cette classe constitue le cœur de l'affichage de l'application. Elle regroupe
 * l'ensemble des composants visuels nécessaires au suivi et au contrôle de la simulation :
 * carte, statistiques, informations générales et narration des événements.
 * </p>
 *
 * <p>
 * Elle gère également la notion de <b>POV (Point Of View)</b>, permettant à l'utilisateur
 * de basculer dynamiquement entre différentes civilisations via un {@link JComboBox},
 * sans interrompre la simulation en cours.
 * </p>
 *
 * <p>
 * Le panneau agit comme un <b>point central de synchronisation</b> entre :
 * <ul>
 *     <li>La logique métier (simulation)</li>
 *     <li>L'affichage (Swing)</li>
 *     <li>Les interactions utilisateur (changement de POV, affichage stats)</li>
 * </ul>
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 * @author Massinisa
 *
 * @version 3.0
 */
public class PanelMainGUI extends JPanel {

	/**
	 * Identification de sérialisation
	 */
	private static final long serialVersionUID = -2076810318609743740L;

	/** 
	 * Gestionnaire principal de la simulation. 
	 */
	private CivilisationManager simulation;

	/** 
	 * Stratégie de rendu graphique de la carte. 
	 */
	private PaintStrategy paintStrategy;

	/** 
	 * Panneau central affichant la carte et les entités. 
	 */
	private PanelSim panelSim;

	/** 
	 * Panneau contenant les informations globales (année, POV, bouton stats). 
	 */
	private InfosPanel panelInfos;

	/** 
	 * Panneau affichant les statistiques rapides (économie, armée, etc.). 
	 */
	private StatsPanel panelStats;

	/** 
	 * Panneau affichant la narration des événements historiques. 
	 */
	private NarrationPanel panelNarration;

	/** 
	 * Gestionnaire des graphiques statistiques (courbes, barres, camemberts). 
	 */
	private ChartManager chartManager;

	/** 
	 * Civilisation actuellement sélectionnée (POV utilisateur). 
	 */
	private Civilisation civilisationActuelle;

	/** 
	 * Liste de toutes les civilisations disponibles dans la simulation. 
	 */
	private final List<Civilisation> toutesLesCivs = new ArrayList<>();

	/** 
	 * Liste des gestionnaires de simulation pour les civilisations adverses. 
	 */
	private List<CivilisationManager> simulationManagersAdverses;
	
	/**
	 * Images pour l'affichage 
	 */
	private SpritesRepository images = SpritesRepository.getInstance();
	
	/**
	 * Instance de la classe 
	 */
	private final PanelMainGUI instance = this;

	/**
	 * Construit le panneau principal de la simulation.
	 *
	 * <p>
	 * Initialise les composants graphiques ainsi que l'ensemble des
	 * structures nécessaires au fonctionnement de la simulation :
	 * données, carte, civilisations et interface.
	 * </p>
	 */
	public PanelMainGUI() {
	    this.setLayout(new BorderLayout());
	    this.initVariables();
	    this.initPanel();
	}
	
	/**
	 * Initialise l'organisation des panneaux graphiques dans l'interface.
	 *
	 * <p>
	 * Les panneaux sont disposés selon une structure BorderLayout :
	 * <ul>
	 *     <li>CENTER : carte de simulation</li>
	 *     <li>SOUTH : informations générales et statistiques</li>
	 *     <li>EAST : narration des événements</li>
	 * </ul>
	 * </p>
	 */
	private void initPanel() {
	    this.add(panelSim, BorderLayout.CENTER);
	    this.add(panelInfos, BorderLayout.SOUTH);
	    this.add(panelNarration, BorderLayout.EAST);
	}

	/**
	 * Initialise toutes les variables nécessaires au fonctionnement de la simulation.
	 *
	 * <p>
	 * Cette méthode met en place :
	 * <ul>
	 *     <li>Le gestionnaire de graphiques ({@link ChartManager})</li>
	 *     <li>La civilisation principale (Vikings)</li>
	 *     <li>Les civilisations adverses et leurs simulations</li>
	 *     <li>La liste globale des civilisations</li>
	 *     <li>Le moteur de simulation principal</li>
	 *     <li>La carte et sa configuration visuelle</li>
	 *     <li>Les différents panneaux de l'interface graphique</li>
	 * </ul>
	 * </p>
	 *
	 * <p>
	 * À la fin de l'initialisation, le POV est positionné sur la civilisation Viking
	 * et les statistiques initiales sont affichées.
	 * </p>
	 */
	private void initVariables() {

        this.chartManager = new ChartManager();
        int anneeDebutSim = SimConfig.ANNEE_DEBUT_SIM;

        Civilisation vikings = CivilisationFactory.buildVikings(anneeDebutSim);

        this.simulationManagersAdverses =
                CivilisationFactory.buildSimulationManagersAdverses(
                        anneeDebutSim,
                        MapFactory.buildCarte()
                );

        List<Civilisation> adverses =
                CivilisationFactory.getCivilisationsAdverses(simulationManagersAdverses);

      
        toutesLesCivs.clear();
        toutesLesCivs.add(vikings);
        toutesLesCivs.addAll(adverses);
        
        this.civilisationActuelle = vikings;

        this.simulation = new CivilisationManager(
                anneeDebutSim,
                MapFactory.buildCarte(),
                vikings,
                MobileBuilder.buildMobileManagerEurope(),
                EvenementHistoriqueBuilder.buildEvenementManagerViking(),
                chartManager
        );

        this.paintStrategy = new PaintStrategy();

        Continent europe = CarteBuilder.buildEurope();
        this.simulation.getCarte().setContinent(europe);

        this.panelSim = new PanelSim(
                this.simulation.getCarte(),
                paintStrategy,
                this.simulation
        );

        this.panelSim.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.paintStrategy.setTailleBloc(SimConfig.TAILLE_BLOC_EUROPE);

        this.panelStats = new StatsPanel();
        this.panelInfos = new InfosPanel(this);
        this.panelNarration = new NarrationPanel(this);

        this.panelStats.mettreAJourPov(vikings);
    }

    /**
     * Retourne le gestionnaire principal de simulation.
     *
     * @return la SimulationManager principale
     */
    public CivilisationManager getSimulation() { 
    	return simulation; 
    }

    /**
     * Retourne le panneau d'affichage de la carte.
     *
     * @return le PanelSim
     */
    public PanelSim getPanelSim() { 
    	return panelSim; 
    }

    /**
     * Retourne la stratégie de rendu graphique utilisée pour la carte.
     *
     * @return la PaintStrategy
     */
    public PaintStrategy getPaintStrategy() { 
    	return paintStrategy; 
    }

   
    /**
     * Fournit l'accès au gestionnaire de graphiques.
     *
     * <p>
     * Le {@link ChartManager} est responsable de la création et de la mise à jour
     * des graphiques (courbe de richesse, relations diplomatiques, ressources).
     * </p>
     *
     * @return le gestionnaire de graphiques
     */
    public ChartManager getChartManager() {
		return chartManager;
	}
    
    /**
     * Retourne le panneau dédié à l'affichage de la narration et des dialogues.
     * * @return L'instance de {@link NarrationPanel} utilisée pour le récit.
     */
    public NarrationPanel getPanelNarration() {
        return panelNarration;
    }

	/**
     * Retourne la liste de toutes les civilisations de la simulation.
     *
     * <p>
     * Inclut la civilisation principale ainsi que toutes les civilisations adverses.
     * Cette liste est utilisée notamment pour :
     * <ul>
     *     <li>Le sélecteur de POV</li>
     *     <li>L'initialisation des graphiques</li>
     *     <li>Les mises à jour globales</li>
     * </ul>
     * </p>
     *
     * @return liste des civilisations
     */
	public List<Civilisation> getToutesLesCivs() { 
		return toutesLesCivs; 
	}

	/**
	 * Retourne la civilisation actuellement sélectionnée par l'utilisateur.
	 *
	 * <p>
	 * Cette civilisation correspond au <b>POV (Point Of View)</b> actif,
	 * utilisé pour afficher les statistiques, la carte et les informations.
	 * </p>
	 *
	 * @return la civilisation actuellement affichée
	 */
    public Civilisation getCivilisationActuelle() {
        return civilisationActuelle;
    }
    
   /**
    * Retourne la liste des gestionnaires de simulation des civilisations adverses.
    *
    * <p>
    * Ces gestionnaires correspondent aux civilisations contrôlées par l’IA
    * et permettent de faire évoluer leur simulation indépendamment du joueur.
    * </p>
    *
    * @return la liste des SimulationManager des civilisations adverses
    */
    public List<CivilisationManager> getSimulationManagersAdverses() {
        return simulationManagersAdverses;
    }	
    
    /**
     * Retourne le panneau affichant les statistiques de la partie.
     * * @return L'instance de {@code StatsPanel} associée.
     */
    public StatsPanel getPanelStats() {
        return panelStats;
    }

    /**
     * Fournit l'accès au dépôt des ressources graphiques (sprites).
     * * @return L'objet {@link SpritesRepository} contenant les images du jeu.
     */
    public SpritesRepository getImages() {
        return images;
    }

    /**
     * Retourne l'instance actuelle de l'interface graphique principale.
     * Souvent utilisé dans le cadre d'un pattern Singleton ou pour accéder au conteneur parent.
     * * @return L'instance unique de {@link PanelMainGUI}.
     */
    public PanelMainGUI getInstance() {
        return instance;
    }

    /**
     * Retourne le panneau contenant les informations textuelles ou descriptives de l'interface.
     * * @return L'instance de {@code InfosPanel} actuellement utilisée.
     */
    public InfosPanel getPanelInfos() {
        return panelInfos;
    }

    /**
     * Définit la civilisation actuellement active ou sélectionnée par l'utilisateur.
     * * @param civilisationActuelle La nouvelle {@link Civilisation} à prendre en compte.
     */
    public void setCivilisationActuelle(Civilisation civilisationActuelle) {
        this.civilisationActuelle = civilisationActuelle;
    }	

    /**
     * Redessine le panneau principal.
     *
     * <p>
     * Cette méthode est appelée automatiquement par Swing lors du rafraîchissement
     * du panneau. Elle redessine le panneau principal et force la mise à jour
     * du panneau de narration {@link #panelNarration}.
     * </p>
     *
     * @param g l'objet {@link Graphics} fourni par Swing pour le rendu graphique
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        panelNarration.repaint();
    }

}