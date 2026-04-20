package gui.panels;

import config.SimConfig;
import config.SpritesConfig;
import gui.fenetres.StatsGUI;
import gui.management.ChartManager;
import gui.management.PaintStrategy;
import moteur.carte.Continent;
import moteur.donnees.*;
import moteur.traitement.builders.CarteBuilder;
import moteur.traitement.builders.EvenementHistoriqueBuilder;
import moteur.traitement.builders.MobileBuilder;
import moteur.traitement.management.factory.CivilisationFactory;
import moteur.traitement.management.factory.MapFactory;
import moteur.traitement.management.managers.civilisation.SimulationManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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
	private SimulationManager simulation;

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
	private NarrationPanel panelNarationRelation;

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
	private List<SimulationManager> simulationManagersAdverses;
	
	/**
	 * Images pour l'affichage 
	 */
	private SpritesConfig images = SpritesConfig.getInstance();

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
	    this.add(panelNarationRelation, BorderLayout.EAST);
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

        this.simulation = new SimulationManager(
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
        this.panelNarationRelation = new NarrationPanel();

        this.panelStats.mettreAJourPov(vikings);
    }

    /**
     * Retourne le gestionnaire principal de simulation.
     *
     * @return la SimulationManager principale
     */
    public SimulationManager getSimulation() { return simulation; }

    /**
     * Retourne le panneau d'affichage de la carte.
     *
     * @return le PanelSim
     */
    public PanelSim getPanelSim() { return panelSim; }

    /**
     * Retourne la stratégie de rendu graphique utilisée pour la carte.
     *
     * @return la PaintStrategy
     */
    public PaintStrategy getPaintStrategy() { return paintStrategy; }

    /**
     * Retourne la zone de texte affichant la narration de la simulation.
     *
     * @return le JTextPane de narration
     */
    public JTextPane getTexteNarration() {
        return panelNarationRelation.texteNarration;
    }

    /**
     * Retourne le label affichant le nombre d'habitants.
     *
     * @return JLabel du nombre d'habitants
     */
    public JLabel getLabelValeurNombreHabitants() {
        return panelStats.labelValeurNombreHabitants;
    }

    /**
     * Retourne la barre de progression de la richesse.
     *
     * @return JProgressBar de richesse
     */
    public JProgressBar getBarRichesse() {
        return panelStats.barRichesse;
    }

    /**
     * Retourne la barre de progression de l'état de l'armée.
     *
     * @return JProgressBar de l'état de l'armée
     */
    public JProgressBar getBarEtatArmee() {
        return panelStats.barEtatArmee;
    }

    /**
     * Retourne le label affichant le nom de la religion.
     *
     * @return JLabel du nom de la religion
     */
    public JLabel getLabelValeurNomReligion() {
        return panelStats.labelValeurNomRegion;
    }

    /**
     * Retourne la barre d'influence de la religion.
     *
     * @return JProgressBar de l'influence religieuse
     */
    public JProgressBar getBarInfluenceReligion() {
        return panelStats.barInfluenceReligion;
    }

    /**
     * Retourne le label du régime politique.
     *
     * @return JLabel du type de régime
     */
    public JLabel getLabelValeurTypeRegime() {
        return panelStats.labelValeurTypeRegime;
    }

    /**
     * Retourne le label du nombre de guerriers.
     *
     * @return JLabel du nombre de guerriers
     */
    public JLabel getLabelValeurNombreGuerriers() {
        return panelStats.labelValeurNombreGuerriers;
    }

    /**
     * Retourne le label du nombre de navires.
     *
     * @return JLabel du nombre de navires
     */
    public JLabel getLabelValeurNombreNavires() {
        return panelStats.labelValeurNombreNavires;
    }

    /**
     * Retourne le label affichant l'année actuelle de la simulation.
     *
     * @return JLabel de l'année
     */
    public JLabel getLabelValeurAnnee() {
        return panelInfos.labelValeurAnnee;
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
    public Civilisation getPovActuel() {
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
    public List<SimulationManager> getSimulationManagersAdverses() {
        return simulationManagersAdverses;
    }

    /**
     * Panneau d'informations générales situé en bas de l'interface.
     *
     * <p>
     * Ce panneau regroupe les éléments de contrôle et d'affichage global :
     * <ul>
     *     <li>Bouton d'accès aux statistiques</li>
     *     <li>Affichage de l'année actuelle de simulation</li>
     *     <li>Sélecteur de civilisation (POV)</li>
     * </ul>
     * </p>
     *
     * <p>
     * Il permet également à l'utilisateur de changer dynamiquement
     * la civilisation observée sans interrompre la simulation.
     * </p>
     * 
     * @author Alexandre
     * @author Tauseef
     * 
     * @version 2.0
     */
    private class InfosPanel extends JPanel {
		
    	/**
		 * Identification de sérialisation 
		 */
		private static final long serialVersionUID = 8131526906863775177L;

		/** 
    	 * Référence vers le panneau principal pour accéder à la simulation et aux données. 
    	 */
    	private final PanelMainGUI panelMainGUI;

    	/** 
    	 * Bouton ouvrant la fenêtre de statistiques détaillées. 
    	 */
    	private final JButton boutonStats = new JButton("Stats");

    	/** 
    	 * Label affichant le texte statique "Année : ". 
    	 */
    	private final JLabel labelTexteAnnee = new JLabel("Année : ");

    	/** 
    	 * Label affichant la valeur actuelle de l'année de simulation. 
    	 */
    	private final JLabel labelValeurAnnee;

    	/** 
    	 * Sélecteur de civilisation permettant de changer le POV. 
    	 */
    	private final JComboBox<String> comboPov;
    	
    	/**
    	 * Police des stats 
    	 */
        private final Font FONT_STATS_PANEL = new Font(Font.SANS_SERIF, Font.ITALIC, 20);

    	/**
         * Constructeur de InfosPanel.
         *
         * <p>
         * Configure la couleur de fond, le layout, et initialise les composants :
         * le bouton "Stats", le texte et la valeur de l'année, ainsi que le panneau de statistiques.
         * </p>
         */
        public InfosPanel(PanelMainGUI panelMainGUI) {
            this.panelMainGUI = panelMainGUI;
            this.setBackground(Color.LIGHT_GRAY);
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

            labelValeurAnnee = new JLabel(
                    String.valueOf(panelMainGUI.simulation.getAnneeActuelle()));

            buildBoutonStats();
            buildTexteAnnee();
            buildValeurAnnee();
 
            this.add(panelMainGUI.panelStats);

            comboPov = buildSelectionneurCivilisation();
            buildSelectorPov(comboPov);
        }

        /**
         * Initialise et configure le bouton "Stats"
         */
        private void buildBoutonStats() {
            boutonStats.setFocusable(false);
            boutonStats.addActionListener(new AfficherStats());
            this.add(boutonStats);
        }

        /**
         * Initialise et configure le label statique de l'année
         */
        private void buildTexteAnnee() {
            labelTexteAnnee.setFont(FONT_STATS_PANEL);
            this.add(labelTexteAnnee);
        }

        /**
         * Initialise et configure le label dynamique de l'année
         */
        private void buildValeurAnnee() {
            labelValeurAnnee.setFont(FONT_STATS_PANEL);
            this.add(labelValeurAnnee);
        }

        /**
         * Construit le sélecteur de civilisation (POV).
         *
         * <p>
         * Ce composant permet à l'utilisateur de changer dynamiquement
         * la civilisation observée sans interrompre la simulation.
         * </p>
         *
         * @return JComboBox configuré avec les civilisations
         */
        private JComboBox<String> buildSelectionneurCivilisation() {
            String[] noms = panelMainGUI.toutesLesCivs.stream()
                    .map(Civilisation::getNomAffiche)
                    .toArray(String[]::new);

            JComboBox<String> combo = new JComboBox<>(noms);
            combo.setSelectedIndex(0); // Vikings par défaut
            panelMainGUI.civilisationActuelle =
                    panelMainGUI.toutesLesCivs.get(0);
            
            combo.setFont(new Font("Serif", Font.BOLD, 13));
            combo.setPreferredSize(new Dimension(160, 30));
            combo.setFocusable(false);

            // Renderer custom pour colorier chaque entrée avec la couleur de sa civ
            combo.setRenderer(new RenduBarre(combo));

            combo.addActionListener(e -> majCivilisation(combo));
            return combo;
        }

        /**
         * Met à jour la civilisation actuellement sélectionnée (POV)
         * en fonction de l'élément choisi dans le {@link JComboBox}.
         *
         * <p>
         * Cette méthode récupère l'index sélectionné dans le composant,
         * puis met à jour :
         * <ul>
         *     <li>La civilisation active dans {@code panelMainGUI}</li>
         *     <li>Les statistiques affichées via {@code StatsPanel}</li>
         * </ul>
         * </p>
         *
         * @param combo le sélecteur contenant les différentes civilisations
         */
        private void majCivilisation(JComboBox<String> combo) {
            int idx = combo.getSelectedIndex();
            if (idx >= 0 && idx < panelMainGUI.toutesLesCivs.size()) {
                Civilisation civ = panelMainGUI.toutesLesCivs.get(idx);
                panelMainGUI.civilisationActuelle = civ;
                panelMainGUI.panelStats.mettreAJourPov(civ);
            }
        }

        /**
         * Ajoute au panneau le sélecteur de civilisation (POV)
         * composé d’un séparateur, d’un label et du {@link JComboBox}.
         *
         * @param combo le sélecteur de civilisations
         */
        private void buildSelectorPov(JComboBox<String> combo) {
            JSeparator sep = new JSeparator(JSeparator.VERTICAL);
            sep.setPreferredSize(new Dimension(2, 40));
            this.add(sep);

            JLabel labelPov = new JLabel("POV :");
            labelPov.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));
            labelPov.setForeground(new Color(0x2F2016));
            this.add(labelPov);

            this.add(combo);
        }

        /**
         * Dessine le panneau et son arrière-plan.
         *
         * @param g1 Objet {@link Graphics} fourni par Swing pour le rendu graphique
         */
        @Override
        protected void paintComponent(Graphics g1) {
            super.paintComponent(g1);
            Graphics2D g = (Graphics2D) g1;
            g.drawImage(panelMainGUI.images.getImage("infos.png"),
                    0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
    
    /**
     * Rendu personnalisé pour les éléments de la JComboBox des civilisations.
     * 
     * <p>
     * Cette classe permet de colorer l'arrière-plan de chaque cellule en fonction 
     * de la couleur spécifique de la civilisation correspondante.
     * </p>
     * 
     * @author Tauseef
     * @version 1.0
     */
    private class RenduBarre extends DefaultListCellRenderer {
        
        /**
		 * Identification de sérialisation
		 */
		private static final long serialVersionUID = -2306104941478010089L;
		
		/** 
         * Référence à la combo box pour déterminer l'élément sélectionné. 
         */
        private JComboBox<String> combo;
        
        /**
         * Construit un nouveau moteur de rendu.
         * * @param combo La {@link JComboBox} associée, utilisée pour récupérer l'index sélectionné.
         */
        private RenduBarre(JComboBox<String> combo) {
            super();
            this.combo = combo;
        }

        /**
         * Retourne le composant configuré pour afficher la cellule de la liste.
         * 
         * <p>
         * La couleur d'arrière-plan est définie par la couleur de la civilisation. 
         * Si l'élément est sélectionné, la couleur est assombrie pour créer un contraste.
         * </p>
         *
         * @param list         La JList que nous peignons.
         * @param value        La valeur retournée par list.getModel().getElementAt(index).
         * @param index        L'index de la cellule. L'index -1 correspond au bouton de la combo (item sélectionné).
         * @param isSelected   Vrai si la cellule est sélectionnée.
         * @param cellHasFocus Vrai si la cellule a le focus.
         * @return Le composant (this) configuré pour dessiner la cellule.
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            int idx = (index == -1) ? combo.getSelectedIndex() : index;
            
            if (idx >= 0 && idx < toutesLesCivs.size()) {
                Color couleur = toutesLesCivs.get(idx).getCouleur();
                
                // Applique la couleur (plus sombre si sélectionné)
                setBackground(isSelected ? couleur.darker() : couleur);
                setForeground(Color.WHITE);
                setFont(new Font("Serif", Font.BOLD, 13));
            }
            
            return this;
        }
    }
    
    /**
     * Panneau interne affichant les statistiques de la civilisation.
     *
     * <p>
     * Ce panneau utilise une grille pour présenter les indicateurs principaux
     * tels que la richesse, l'état de l'armée, le nombre de guerriers et de navires,
     * le type de régime, la population et les informations religieuses.
     * </p>
     *
     * @author Alexandre
     * @author Massinissa
     *
     * @version 2.0
     */
    private class StatsPanel extends JPanel {

    	/** 
    	 * Identifiant de sérialisation unique pour cette classe. 
    	 */
        private static final long serialVersionUID = 1516484116813051384L;

        /** 
         * Nombre de lignes dans la grille de mise en page. 
         */
        private static final int GRID_ROWS = 4;
        
        /** 
         * Nombre de colonnes dans la grille de mise en page. 
         */
        private static final int GRID_COLS = 4;
        
        /** 
         * Espacement horizontal (en pixels) entre les composants de la grille. 
         */
        private static final int GRID_HGAP = 4;
        
        /** 
         * Espacement vertical (en pixels) entre les composants de la grille. 
         */
        private static final int GRID_VGAP = 1;

        /** 
         * Valeur maximale autorisée pour la barre de richesse ({@value #MAX_RICHESSE}). 
         */
        private static final int MAX_RICHESSE = 100000;
        
        /** 
         * Valeur maximale autorisée pour l'état de l'armée ({@value #MAX_ETAT_ARMEE}%). 
         */
        private static final int MAX_ETAT_ARMEE = 100;
        
        /** 
         * Valeur maximale autorisée pour l'influence religieuse ({@value #MAX_INFLUENCE_RELIGION}%). 
         */
        private static final int MAX_INFLUENCE_RELIGION = 100;

        /** 
         * Étiquette statique pour la section Richesse. 
         */
        private final JLabel labelRichesse = new JLabel("Richesse : ");
        
        /** 
         * Indicateur visuel du niveau de richesse actuel par rapport au maximum. 
         */
        private final JProgressBar barRichesse = new JProgressBar(0, MAX_RICHESSE);

        /** 
         * Étiquette statique pour l'état de santé/entretien de l'armée. 
         */
        private final JLabel labelEtatArmee = new JLabel("État de l'armée : ");
        
        /** 
         * Indicateur visuel de l'état opérationnel de l'armée. 
         */
        private final JProgressBar barEtatArmee = new JProgressBar(0, MAX_ETAT_ARMEE);

        /** 
         * Étiquette statique pour le décompte des unités militaires terrestres. 
         */
        private final JLabel labelNombreGuerriers = new JLabel("Nombre de guerriers : ");
        
        /** 
         * Affiche dynamiquement le nombre total de guerriers actifs. 
         */
        private final JLabel labelValeurNombreGuerriers = new JLabel("0");

        /** 
         * Étiquette statique pour le décompte des unités militaires navales. 
         */
        private final JLabel labelNombreNavires = new JLabel("Nombre de navires : ");
        
        /** 
         * Affiche dynamiquement le nombre total de navires de guerre. 
         */
        private final JLabel labelValeurNombreNavires = new JLabel("0");

        /** 
         * Étiquette statique pour le système politique en place. 
         */
        private final JLabel labelTypeRegime = new JLabel("Type de régime :");
        
        /** 
         * Affiche le nom du régime politique actuel (ex: Monarchie, Démocratie). 
         */
        private final JLabel labelValeurTypeRegime = new JLabel("Indéfini");

        /** 
         * Étiquette statique pour la population totale. 
         */
        private final JLabel labelNombreHabitants = new JLabel("Nombre d'habitants : ");
        
        /** 
         * Affiche dynamiquement le nombre total d'habitants de la civilisation. 
         */
        private final JLabel labelValeurNombreHabitants = new JLabel("0");

        /** 
         * Étiquette statique pour la confession religieuse. 
         */
        private final JLabel labelNomReligion = new JLabel("Religion : ");
        
        /** 
         * Affiche le nom de la religion dominante. 
         */
        private final JLabel labelValeurNomRegion = new JLabel("Indéfini");

        /** 
         * Indicateur visuel du taux d'influence ou de ferveur religieuse. 
         */
        private final JProgressBar barInfluenceReligion = new JProgressBar(0, MAX_INFLUENCE_RELIGION);

        /**
         * Constructeur du panneau des statistiques.
         *
         * <p>
         * Initialise le layout en grille, configure les couleurs et ajoute tous
         * les composants graphiques via {@link #buildStatsPanel()}.
         * </p>
         */
        public StatsPanel() {
            this.setLayout(new GridLayout(GRID_ROWS, GRID_COLS, GRID_HGAP, GRID_VGAP));
            this.setBackground(Color.LIGHT_GRAY);
            this.setOpaque(false);
            buildStatsPanel();
        }

        /**
         * Construit et ajoute tous les composants graphiques du panneau.
         */
        private void buildStatsPanel() {
            buildRichesse();
            buildEtatArmee();
            buildNombreGuerriers();
            buildNombreNavires();
            buildTypeRegime();
            buildNombreHabitants();
            buildReligion();
            buildInfluenceReligion();
        }

        /**
         * Configure et ajoute le label et la barre de richesse.
         */
        private void buildRichesse() {
            labelRichesse.setFont(SimConfig.FONT_STATS);
            this.add(labelRichesse);

            barRichesse.setStringPainted(true);
            barRichesse.setString("0");
            barRichesse.setValue(0);
            barRichesse.setFont(SimConfig.FONT_STATS);
            this.add(barRichesse);
        }

        /**
         * Configure et ajoute le label et la barre de l'état de l'armée.
         */
        private void buildEtatArmee() {
            labelEtatArmee.setFont(SimConfig.FONT_STATS);
            this.add(labelEtatArmee);

            barEtatArmee.setStringPainted(true);
            barEtatArmee.setString("0%");
            barEtatArmee.setValue(0);
            barEtatArmee.setFont(SimConfig.FONT_STATS);
            this.add(barEtatArmee);
        }

        /**
         * Configure et ajoute le label et la valeur du nombre de guerriers.
         */
        private void buildNombreGuerriers() {
            labelNombreGuerriers.setFont(SimConfig.FONT_STATS);
            this.add(labelNombreGuerriers);

            labelValeurNombreGuerriers.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurNombreGuerriers);
        }

        /**
         * Configure et ajoute le label et la valeur du nombre de navires.
         */
        private void buildNombreNavires() {
            labelNombreNavires.setFont(SimConfig.FONT_STATS);
            this.add(labelNombreNavires);

            labelValeurNombreNavires.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurNombreNavires);
        }

        /**
         * Configure et ajoute le label et la valeur du type de régime politique.
         */
        private void buildTypeRegime() {
            labelTypeRegime.setFont(SimConfig.FONT_STATS);
            this.add(labelTypeRegime);

            labelValeurTypeRegime.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurTypeRegime);
        }

        /**
         * Configure et ajoute le label et la valeur du nombre d'habitants.
         */
        private void buildNombreHabitants() {
            labelNombreHabitants.setFont(SimConfig.FONT_STATS);
            this.add(labelNombreHabitants);

            labelValeurNombreHabitants.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurNombreHabitants);
        }

        /**
         * Configure et ajoute le label et la valeur de la religion.
         */
        private void buildReligion() {
            labelNomReligion.setFont(SimConfig.FONT_STATS);
            this.add(labelNomReligion);

            labelValeurNomRegion.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurNomRegion);
        }

        /**
         * Configure et ajoute le label et la barre d'influence de la religion.
         */
        private void buildInfluenceReligion() {
            JLabel labelInfluenceReligion = new JLabel("Influence de la religion : ");
            labelInfluenceReligion.setFont(SimConfig.FONT_STATS);
            this.add(labelInfluenceReligion);

            barInfluenceReligion.setStringPainted(true);
            barInfluenceReligion.setValue(0);
            barInfluenceReligion.setString("0%");
            barInfluenceReligion.setFont(SimConfig.FONT_STATS);
            this.add(barInfluenceReligion);
        }

        /**
         * Met à jour les statistiques affichées en fonction de la civilisation sélectionnée.
         *
         * <p>
         * Cette méthode est appelée lors d'un changement de POV via le {@link JComboBox}.
         * Elle met à jour dynamiquement :
         * <ul>
         *     <li>La richesse (barre de progression)</li>
         *     <li>L'état de l'armée</li>
         *     <li>Les effectifs militaires</li>
         *     <li>La population</li>
         *     <li>La religion</li>
         *     <li>Le régime politique</li>
         * </ul>
         * </p>
         * 
         * @param civ civilisation à afficher
         */
        public void mettreAJourPov(Civilisation civ) {
            if (civ == null) return;

            // Économie
            int stycas = (int) civ.getEconomie().getStycas();
            if (barRichesse.getMaximum() <= stycas) barRichesse.setMaximum(stycas * 2);
            barRichesse.setValue(stycas);
            barRichesse.setString(stycas + " S");

            // Armée
            Armee armee = civ.getArmee();
            if (armee != null) {
                labelValeurNombreGuerriers.setText(String.valueOf(armee.getNombreGuerriers()));
                labelValeurNombreNavires.setText(String.valueOf(armee.getNombreNavires()));
                barEtatArmee.setValue((int) armee.getEtat());
                barEtatArmee.setString((int) armee.getEtat() + "%");
            }

            // Population
            if (civ.getPopulation() != null)
                labelValeurNombreHabitants.setText(
                        String.valueOf(civ.getPopulation().getNbHabitants()));

            // Religion
            if (civ.getReligion() != null) {
                labelValeurNomRegion.setText(civ.getReligion().getNom());
                barInfluenceReligion.setValue((int) civ.getReligion().getInfluence());
                barInfluenceReligion.setString((int) civ.getReligion().getInfluence() + "%");
            }

            // Politique
            if (civ.getPolitique() != null)
                labelValeurTypeRegime.setText(
                        civ.getPolitique().getTypeRegime()
                        + " — " + civ.getPolitique().getEtatStabilite());

            repaint();
        }
    }

    /**
     * Panneau interne affichant la narration de la simulation.
     *
     * <p>
     * Cette classe étend {@link JPanel} et sert à afficher le texte narratif de l'évolution
     * de la simulation dans un {@link JTextPane} justifié. Le texte est décoratif et
     * non éditable par l'utilisateur. L'arrière-plan est une image ("zone_par.png").
     * </p>
     *
     * @author Tauseef
     * @version 2.0
     */
    private class NarrationPanel extends JPanel {

        /**
		 * Identifiant de sérialisation
		 */
		private static final long serialVersionUID = 4507733367365335456L;

		/**
         * Zone de texte affichant la narration de la simulation
         */
        private final JTextPane texteNarration = new JTextPane();

        /**
         * Largeur fixe du panneau de narration
         */
        private static final int NARRATION_WIDTH = 200;

        /**
         * Espacement du layout principal (horizontal et vertical)
         */
        private static final int GAP = 10;

        /**
         * Taille de la police de la narration
         */
        private static final int FONT_NARRATION_SIZE = 13;

        /**
         * Couleur du texte de la narration
         */
        private final Color COLOR_NARRATION = new Color(80, 40, 20);

        /**
         * Constructeur du panneau de narration.
         *
         * <p>
         * Initialise les dimensions, le layout, l'image de bordure et le texte narratif.
         * </p>
         */
        public NarrationPanel() {
            Dimension dimPanelNarration = new Dimension(NARRATION_WIDTH, SimConfig.TAILLE_FENETRE_Y);
            this.setMaximumSize(dimPanelNarration);
            this.setMinimumSize(dimPanelNarration);
            this.setPreferredSize(dimPanelNarration);

            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 100, GAP));

            this.buildTextePanel();

            texteNarration.setOpaque(false);
            this.add(texteNarration);
        }

        /**
         * Initialise et configure le {@link JTextPane} pour la narration.
         *
         * <p>
         * La zone de texte est non éditable, justifiée, avec des marges et une couleur personnalisée.
         * </p>
         */
        private void buildTextePanel() {
            Dimension dimTexteNarration = new Dimension(NARRATION_WIDTH, SimConfig.TAILLE_FENETRE_Y);
            this.texteNarration.setPreferredSize(dimTexteNarration);
            this.texteNarration.setMaximumSize(dimTexteNarration);
            this.texteNarration.setMinimumSize(dimTexteNarration);

            this.texteNarration.setEditable(false);
            this.texteNarration.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.texteNarration.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            this.texteNarration.setFont(new Font("Serif", Font.ITALIC, FONT_NARRATION_SIZE));
            this.texteNarration.setForeground(COLOR_NARRATION);

            StyledDocument doc = texteNarration.getStyledDocument();
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_JUSTIFIED);
            StyleConstants.setLineSpacing(style, 0.2f);
            StyleConstants.setLeftIndent(style, 10);
            StyleConstants.setRightIndent(style, 10);
            doc.setParagraphAttributes(0, doc.getLength(), style, false);

            this.texteNarration.setText("");
        }

        /**
         * Dessine le panneau et son arrière-plan avec l'image "zone_par.png".
         *
         * @param g1 Objet {@link Graphics} fourni par Swing pour le rendu graphique
         */
        @Override
        protected void paintComponent(Graphics g1) {
            super.paintComponent(g1);
            Graphics2D g = (Graphics2D) g1;
            g.drawImage(images.getImage("zone_par.png"), 0, 0, this.getWidth(), this.getHeight(), null);
        }

    }

    /**
     * Redessine le panneau principal.
     *
     * <p>
     * Cette méthode est appelée automatiquement par Swing lors du rafraîchissement
     * du panneau. Elle redessine le panneau principal et force la mise à jour
     * du panneau de narration {@link #panelNarationRelation}.
     * </p>
     *
     * @param g l'objet {@link Graphics} fourni par Swing pour le rendu graphique
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        panelNarationRelation.repaint();
    }

    /**
     * Classe interne responsable de l'affichage des statistiques de la simulation.
     *
     * <p>
     * Cette classe implémente l'interface {@link ActionListener} et est
     * déclenchée lorsqu'un utilisateur clique sur le bouton "Stats".
     * Elle crée et lance un nouveau thread pour ouvrir la fenêtre {@link StatsGUI}
     * afin de ne pas bloquer le thread principal de l'interface graphique.
     * </p>
     *
     * @author Massinissa
     * @version 2.0
     */
    private class AfficherStats implements ActionListener {

        /**
         * Méthode appelée lorsque le bouton "Stats" est cliqué.
         *
         * <p>
         * Elle crée un nouveau thread pour afficher la fenêtre des statistiques,
         * ce qui permet de garder l'interface réactive et de ne pas bloquer
         * le thread d'affichage principal.
         * </p>
         *
         * @param e l'événement généré par l'action (clic sur le bouton)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
        	Thread stats = new Thread(new StatsGUI(chartManager, PanelMainGUI.this));
            stats.start();
        }

    }

}