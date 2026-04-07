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
import moteur.traitement.management.factory.MapFactory;
import moteur.traitement.management.factory.SimFactory;
import moteur.traitement.management.managers.civilisation.ChefRepository;
import moteur.traitement.management.managers.civilisation.SimulationManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Panneau principal de l'interface graphique de la simulation.
 *
 * <p>
 * Cette classe centralise l'affichage et l'initialisation complète de la simulation.
 * Elle assemble les différents sous-panneaux :
 * </p>
 *
 * <ul>
 *     <li>Carte de simulation</li>
 *     <li>Informations générales</li>
 *     <li>Statistiques</li>
 *     <li>Narration des événements</li>
 * </ul>
 *
 * <p>
 * Elle fait le lien entre la logique métier ({@link SimulationManager})
 * et l'affichage graphique.
 * </p>
 *
 * @author Alexandre
 * @version 2.0
 */
public class PanelMainGUI extends JPanel {

    /**
     * Identifiant de sérialisation
     */
    @Serial
    private static final long serialVersionUID = -2755589757560579734L;

    /**
     * Gestionnaire des sprites
     */
    private final SpritesConfig images = SpritesConfig.getInstance();

    /**
     * Gestionnaire principal de simulation
     */
    private SimulationManager simulation;

    /**
     * Stratégie de dessin de la carte
     */
    private PaintStrategy paintStrategy;

    /**
     * Panneau de simulation (carte)
     */
    private PanelSim panelSim;

    /**
     * Panneau d'informations générales
     */
    private InfosPanel panelInfos;

    /**
     * Panneau de statistiques
     */
    private StatsPanel panelStats;

    /**
     * Panneau de narration
     */
    private NarrationPanel panelNarationRelation;

    /**
     * Gestionnaire de graphiques
     */
    private ChartManager chartManager;

    /**
     * Repository des chefs
     */
    private final ChefRepository chefRepository = ChefRepository.getInstance();

    private static final Font FONT_STATS_PANEL = new Font(Font.SANS_SERIF, Font.ITALIC, 20);

    /**
     * Initialise le panneau principal de la simulation.
     */
    public PanelMainGUI() {
        this.setLayout(new BorderLayout());
        this.initVariables();
        this.initPanel();
    }

    /**
     * Ajoute les sous-panneaux à l'interface.
     */
    private void initPanel() {
        this.add(panelSim, BorderLayout.CENTER);
        this.add(panelInfos, BorderLayout.SOUTH);
        this.add(panelNarationRelation, BorderLayout.EAST);
    }

    /**
     * Initialise toutes les données et composants de la simulation.
     */
    private void initVariables() {
        this.chartManager = new ChartManager();

        int anneeDebutSim = SimConfig.ANNEE_DEBUT_SIM;

        Civilisation civilisation = createCivilisation(anneeDebutSim);
        addRelations(civilisation);

        this.simulation = new SimulationManager(
                anneeDebutSim,
                MapFactory.buildCarte(),
                civilisation,
                MobileBuilder.buildMobileManagerEurope(),
                EvenementHistoriqueBuilder.buildEvenementManagerViking(),
                chartManager
        );

        this.paintStrategy = new PaintStrategy();

        Continent europe = CarteBuilder.buildEurope();
        this.simulation.getCarte().setContinent(europe);

        this.panelSim = new PanelSim(this.simulation.getCarte(), paintStrategy, this.simulation);
        this.panelSim.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        this.paintStrategy.setTailleBloc(SimConfig.TAILLE_BLOC_EUROPE);

        this.panelStats = new StatsPanel();
        this.panelInfos = new InfosPanel(this);
        this.panelNarationRelation = new NarrationPanel();
    }

    /**
     * Initialise les relations diplomatiques de la civilisation.
     *
     * @param civilisation civilisation à configurer
     */
    private void addRelations(Civilisation civilisation) {
        civilisation.ajouterRelation(SimFactory.buildRelation("Northumbrie", 10f));
        civilisation.ajouterRelation(SimFactory.buildRelation("Irlande", 15f));
        civilisation.ajouterRelation(SimFactory.buildRelation("Royaume des Francs", 20f));
        civilisation.ajouterRelation(SimFactory.buildRelation("Empire Byzantin", 50f));
        civilisation.ajouterRelation(SimFactory.buildRelation("Califat Abbasside", 50f));
    }

    /**
     * Crée une civilisation complète.
     */
    private Civilisation createCivilisation(int annee) {
        Population pop = SimFactory.buildPopulation(
                SimConfig.NB_HABITANT_DEBUT_SIM,
                SimConfig.AGE_MOYEN_DEBUT_SIM,
                SimConfig.SEXE_MAJORITAIRE_DEBUT_SIM
        );

        Religion religion = SimFactory.buildReligion(
                SimConfig.NOM_RELIGION_DEBUT,
                SimConfig.CROYANCE_RELIGION_DEBUT,
                SimConfig.INFLUENCE_RELIGION_DEBUT
        );

        Politique politique = SimFactory.buildPolitique(
                SimConfig.TYPE_REGIME_DEBUT,
                SimConfig.STABILITE_POLITIQUE_DEBUT
        );

        Armee armee = SimFactory.buildArmee(
                chefRepository.getKersirActuel(annee),
                10000,
                300,
                10,
                80f
        );

        return SimFactory.buildCivilisation(
                chefRepository.getKonungrActuel(annee),
                SimFactory.buildEconomie(),
                armee,
                pop,
                religion,
                politique,
                SimConfig.NOM_CIVILISATION
        );
    }

    /**
     * @return simulation
     */
    public SimulationManager getSimulation() { return simulation; }

    /**
     * @return panneau carte
     */
    public PanelSim getPanelSim() { return panelSim; }

    /**
     * @return stratégie de dessin
     */
    public PaintStrategy getPaintStrategy() { return paintStrategy; }

    /**
     * @return texte narration
     */
    public JTextPane getTexteNarration() { return panelNarationRelation.texteNarration; }

    /**
     * @return habitants
     */
    public JLabel getLabelValeurNombreHabitants() { return panelStats.labelValeurNombreHabitants; }

    /**
     * @return barre richesse
     */
    public JProgressBar getBarRichesse() { return panelStats.barRichesse; }

    /** @return barre armée */
    public JProgressBar getBarEtatArmee() { return panelStats.barEtatArmee; }

    /**
     * Retourne le label affichant le nom de la religion
     */
    public JLabel getLabelValeurNomReligion() {
        return panelStats.labelValeurNomRegion;
    }

    /**
     * Retourne la barre de progression de l'influence de la religion
     */
    public JProgressBar getBarInfluenceReligion() {
        return panelStats.barInfluenceReligion;
    }

    /**
     * Retourne le label affichant le type de régime politique
     */
    public JLabel getLabelValeurTypeRegime() {
        return panelStats.labelValeurTypeRegime;
    }

    /**
     * Retourne le label affichant le nombre de guerriers
     */
    public JLabel getLabelValeurNombreGuerriers() {
        return panelStats.labelValeurNombreGuerriers;
    }

    /**
     * Retourne le label affichant le nombre de navires
     */
    public JLabel getLabelValeurNombreNavires() {
        return panelStats.labelValeurNombreNavires;
    }

    /**
     * Retourne le label affichant l'année de la simulation
     */
    public JLabel getLabelValeurAnnee() {
        return panelInfos.labelValeurAnnee;
    }

    /**
     * Panneau interne affichant les informations principales de la simulation.
     *
     * <p>
     * Cette classe étend {@link JPanel} et sert à afficher :
     * <ul>
     *   <li>Le bouton "Stats" permettant d'ouvrir la fenêtre des statistiques.</li>
     *   <li>L'année actuelle de la simulation.</li>
     *   <li>Le panneau de statistiques {@link PanelMainGUI.StatsPanel} associé.</li>
     * </ul>
     * </p>
     *
     * @author Tauseef
     * @version 2.0
     */
    private class InfosPanel extends JPanel {

        private final PanelMainGUI panelMainGUI;
        /**
         * Bouton déclenchant l'affichage des statistiques
         */
        private final JButton boutonStats = new JButton("Stats");

        /**
         * Label statique affichant "Année : "
         */
        private final JLabel labelTexteAnnee = new JLabel("Année : ");

        /**
         * Label dynamique affichant l'année actuelle de la simulation
         */
        private final JLabel labelValeurAnnee;

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
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 90, 20));

            labelValeurAnnee = new JLabel(String.valueOf(panelMainGUI.simulation.getAnneeActuelle()));

            this.buildBoutonStats();
            this.buildTexteAnnee();
            this.buildValeurAnnee();

            this.add(panelMainGUI.panelStats);
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
         * Dessine le panneau et son arrière-plan.
         *
         * @param g1 Objet {@link Graphics} fourni par Swing pour le rendu graphique
         */
        @Override
        protected void paintComponent(Graphics g1) {
            super.paintComponent(g1);
            Graphics2D g = (Graphics2D) g1;
            g.drawImage(panelMainGUI.images.getImage("infos.png"), 0, 0, this.getWidth(), this.getHeight(), null);
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

        // Constantes pour la configuration visuelle du panneau
        private static final int GRID_ROWS = 4;
        private static final int GRID_COLS = 4;
        private static final int GRID_HGAP = 4;
        private static final int GRID_VGAP = 1;

        private static final int MAX_RICHESSE = 100000;
        private static final int MAX_ETAT_ARMEE = 100;
        private static final int MAX_INFLUENCE_RELIGION = 100;

        // Labels et barres de progression
        private final JLabel labelRichesse = new JLabel("Richesse : ");
        private final JProgressBar barRichesse = new JProgressBar(0, MAX_RICHESSE);

        private final JLabel labelEtatArmee = new JLabel("État de l'armée : ");
        private final JProgressBar barEtatArmee = new JProgressBar(0, MAX_ETAT_ARMEE);

        private final JLabel labelNombreGuerriers = new JLabel("Nombre de guerriers : ");
        private final JLabel labelValeurNombreGuerriers = new JLabel("0");

        private final JLabel labelNombreNavires = new JLabel("Nombre de navires : ");
        private final JLabel labelValeurNombreNavires = new JLabel("0");

        private final JLabel labelTypeRegime = new JLabel("Type de régime :");
        private final JLabel labelValeurTypeRegime = new JLabel("Indéfini");

        private final JLabel labelNombreHabitants = new JLabel("Nombre d'habitants : ");
        private final JLabel labelValeurNombreHabitants = new JLabel("0");

        private final JLabel labelNomReligion = new JLabel("Religion : ");
        private final JLabel labelValeurNomRegion = new JLabel("Indéfini");

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
        private static final Color COLOR_NARRATION = new Color(80, 40, 20);

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
            Thread stats = new Thread(new StatsGUI(chartManager, simulation));
            stats.start();
        }

    }

}