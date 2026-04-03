package gui.panels;

import config.SimConfig;
import config.SpritesConfig;
import gui.fenetres.MainGUI;
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
import moteur.traitement.management.managers.SimulationManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Classe de panneau d'affichage principal de la simulation
 *
 * @author Alexandre, Tauseef et Massinissa
 * @version 1.1
 */
public class PanelMainGUI extends JPanel {

    // Attributs
    @Serial
    private static final long serialVersionUID = -2755589757560579734L;

    private final SpritesConfig images = SpritesConfig.getInstance();
    private SimulationManager simulation;
    private PaintStrategy paintStrategy;

    private PanelSim panelSim;
    private InfosPanel panelInfos;
    private StatsPanel panelStats;
    private NarrationPanel panelNarationRelation;

    private ChartManager chartManager;

    /**
     * Constructeur de la classe PanelMainGUI
     *
     * @param mainGUI : La fenêtre principale
     */
    public PanelMainGUI(MainGUI mainGUI) {
        this.setLayout(new BorderLayout());
        this.initVariables();
        this.initPanel();
    }

    /**
     * Méthode qui initialise les panneaux
     */
    private void initPanel() {
        this.add(panelSim, BorderLayout.CENTER);
        this.add(panelInfos, BorderLayout.SOUTH);
        this.add(panelNarationRelation, BorderLayout.EAST);
    }

    private void initVariables() {
        this.chartManager = new ChartManager();

        int anneeDebutSim = SimConfig.ANNEE_DEBUT_SIM;
        Population pop = SimFactory.buildPopulation(SimConfig.NB_HABITANT_DEBUT_SIM, SimConfig.AGE_MOYEN_DEBUT_SIM, SimConfig.SEXE_MAJORITAIRE_DEBUT_SIM);

        Religion religion = SimFactory.buildReligion(
                SimConfig.NOM_RELIGION_DEBUT,
                SimConfig.CROYANCE_RELIGION_DEBUT,
                SimConfig.INFLUENCE_RELIGION_DEBUT);

        Politique politique = SimFactory.buildPolitique(
                SimConfig.TYPE_REGIME_DEBUT,
                SimConfig.STABILITE_POLITIQUE_DEBUT);
        politique.setPolitiqueMilitaire(SimConfig.POLITIQUE_MILITAIRE_DEBUT);
        politique.setPolitiqueEconomique(SimConfig.POLITIQUE_ECONOMIQUE_DEBUT);
        politique.setPolitiqueDiplomatique(SimConfig.POLITIQUE_DIPLOMATIQUE_DEBUT);

        Armee armee = SimFactory.buildArmee("chef", 10000, 300, 10, 80f);

        Civilisation civilisation = SimFactory.buildCivilisation("", SimFactory.buildEconomie(), armee, pop
                , religion, politique, SimConfig.NOM_CIVILISATION);

        this.simulation = new SimulationManager(anneeDebutSim, MapFactory.buildCarte(), civilisation, MobileBuilder.buildMobileManagerEurope(), EvenementHistoriqueBuilder.buildEvenementManagerViking(), chartManager);
        this.paintStrategy = new PaintStrategy();

        Continent Europe = CarteBuilder.buildEurope();
        this.simulation.getCarte().setContinent(Europe);
        this.panelSim = new PanelSim(this.simulation.getCarte(), paintStrategy, this.simulation);

        this.panelSim.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.paintStrategy.setTailleBloc(SimConfig.TAILLE_BLOC_EUROPE);

        this.panelStats = new StatsPanel();
        this.panelInfos = new InfosPanel();
        this.panelNarationRelation = new NarrationPanel();
    }

    public SimulationManager getSimulation() {
        return simulation;
    }

    public JLabel getLabelValeurAnnee() {
        return panelInfos.labelValeurAnnee;
    }

    public JTextPane getTexteNarration() {
        return panelNarationRelation.texteNarration;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.panelNarationRelation.repaint();
    }

    public PanelSim getPanelSim() {
        return panelSim;
    }

    public PaintStrategy getPaintStrategy() {
        return paintStrategy;
    }

    public JLabel getLabelValeurNombreHabitants() {
        return panelStats.labelValeurNombreHabitants;
    }

    public StatsPanel getPanelStats() {
        return panelStats;
    }

    public InfosPanel getPanelInfos() {
        return panelInfos;
    }

    public JProgressBar getBarRichesse() {
        return panelStats.barRichesse;
    }

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

    public JLabel getLabelValeurNombreGuerriers() {
        return panelStats.labelValeurNombreGuerriers;
    }

    public JLabel getLabelValeurNombreNavires() {
        return panelStats.labelValeurNombreNavires;
    }

    public JProgressBar getBarEtatArmee() {
        return panelStats.barEtatArmee;
    }

    /**
     * Classe interne qui permet d'afficher les statistiques
     *
     * @author Massinissa
     * @version 1.0
     */
    private class AfficherStats implements ActionListener {

        /**
         * Affiche une nouvelle fenêtre avec les statistiques
         *
         * @param e : Action déclenchée par le joueur
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Thread stats = new Thread(new StatsGUI(chartManager, simulation));
            stats.start();
        }

    }

    private class InfosPanel extends JPanel {
        private final JButton boutonStats = new JButton("Stats");
        private final JLabel labelTexteAnnee = new JLabel("Année : ");
        private final JLabel labelValeurAnnee = new JLabel(String.valueOf(simulation.getAnneeActuelle()));


        public InfosPanel() {
            this.setBackground(Color.LIGHT_GRAY);
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 90, 20));

            this.buildBoutonStats();
            this.buildTexteAnnee();
            this.buildValeurAnnee();

            this.add(panelStats);
        }

        private void buildBoutonStats() {
            boutonStats.setFocusable(false);
            boutonStats.addActionListener(new AfficherStats());
            this.add(boutonStats);
        }

        private void buildTexteAnnee() {
            labelTexteAnnee.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
            this.add(labelTexteAnnee);
        }

        private void buildValeurAnnee() {
            labelValeurAnnee.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
            this.add(labelValeurAnnee);
        }

        public JLabel getLabelValeurAnnee() {
            return labelValeurAnnee;
        }

        @Override
        protected void paintComponent(Graphics g1) {
            super.paintComponent(g1);
            Graphics2D g = (Graphics2D) g1;
            g.drawImage(images.getImage("infos.png"), 0, 0, this.getWidth(), this.getHeight(), null);
        }

    }

    private class StatsPanel extends JPanel {
        private final JLabel labelRichesse = new JLabel("Richesse : ");
        private final JLabel labelEtatArmee = new JLabel("État de l'armée : ");

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

        private final JProgressBar barRichesse = new JProgressBar(0, 100000);
        private final JProgressBar barEtatArmee = new JProgressBar(0, 100);
        private final JProgressBar barInfluenceReligion = new JProgressBar(0, 100);

        public StatsPanel() {
            this.setLayout(new GridLayout(4, 4, 4, 1));
            this.setBackground(Color.LIGHT_GRAY);
            this.setOpaque(false);

            buildStatsPanel();
        }

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

        private void buildRichesse() {
            labelRichesse.setFont(SimConfig.FONT_STATS);
            this.add(labelRichesse);

            barRichesse.setStringPainted(true);
            barRichesse.setString("0");
            barRichesse.setValue(0);
            barRichesse.setFont(SimConfig.FONT_STATS);
            this.add(barRichesse);
        }

        private void buildEtatArmee() {
            labelEtatArmee.setFont(SimConfig.FONT_STATS);
            this.add(labelEtatArmee);

            barEtatArmee.setStringPainted(true);
            barEtatArmee.setString("0%");
            barEtatArmee.setValue(0);
            barEtatArmee.setFont(SimConfig.FONT_STATS);
            this.add(barEtatArmee);
        }

        private void buildNombreGuerriers() {
            labelNombreGuerriers.setFont(SimConfig.FONT_STATS);
            this.add(labelNombreGuerriers);

            labelValeurNombreGuerriers.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurNombreGuerriers);
        }

        private void buildNombreNavires() {
            labelNombreNavires.setFont(SimConfig.FONT_STATS);
            this.add(labelNombreNavires);

            labelValeurNombreNavires.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurNombreNavires);
        }

        private void buildTypeRegime() {
            labelTypeRegime.setFont(SimConfig.FONT_STATS);
            this.add(labelTypeRegime);

            labelValeurTypeRegime.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurTypeRegime);
        }

        private void buildNombreHabitants() {
            labelNombreHabitants.setFont(SimConfig.FONT_STATS);
            this.add(labelNombreHabitants);

            labelValeurNombreHabitants.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurNombreHabitants);
        }

        private void buildReligion() {
            labelNomReligion.setFont(SimConfig.FONT_STATS);
            this.add(labelNomReligion);

            labelValeurNomRegion.setFont(SimConfig.FONT_STATS);
            this.add(labelValeurNomRegion);
        }

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

    private class NarrationPanel extends JPanel {
        private final JTextPane texteNarration = new JTextPane();

        public NarrationPanel() {
            Dimension dimPanelNarration = new Dimension(200, SimConfig.TAILLE_FENETRE_Y);
            this.setMaximumSize(dimPanelNarration);
            this.setMinimumSize(dimPanelNarration);
            this.setPreferredSize(dimPanelNarration);

            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));

            this.buildTextePanel();

            texteNarration.setOpaque(false);

            this.add(texteNarration);
        }

        private void buildTextePanel() {
            Dimension dimTexteNarration = new Dimension(200, SimConfig.TAILLE_FENETRE_Y);
            this.texteNarration.setPreferredSize(dimTexteNarration);
            this.texteNarration.setMaximumSize(dimTexteNarration);
            this.texteNarration.setMinimumSize(dimTexteNarration);

            this.texteNarration.setEditable(false);
            this.texteNarration.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.texteNarration.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            this.texteNarration.setFont(new Font("Serif", Font.ITALIC, 13));
            this.texteNarration.setForeground(new Color(80, 40, 20));

            StyledDocument doc = texteNarration.getStyledDocument();
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_JUSTIFIED);
            StyleConstants.setLineSpacing(style, 0.2f);
            StyleConstants.setLeftIndent(style, 10);
            StyleConstants.setRightIndent(style, 10);
            doc.setParagraphAttributes(0, doc.getLength(), style, false);

            this.texteNarration.setText("");
        }

        @Override
        protected void paintComponent(Graphics g1) {
            super.paintComponent(g1);
            Graphics2D g = (Graphics2D) g1;
            g.drawImage(images.getImage("zone_par.png"), 0, 0, this.getWidth(), this.getHeight(), null);
        }

        public JTextPane getTexteNarration() {
            return texteNarration;
        }

    }

}