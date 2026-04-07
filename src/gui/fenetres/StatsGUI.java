package gui.fenetres;

import java.awt.*;
import java.io.Serial;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import config.SimConfig;
import gui.management.ChartManager;

import moteur.donnees.*;
import moteur.traitement.management.managers.armee.ArmeeManager;
import moteur.traitement.management.managers.civilisation.SimulationManager;

import static config.SimConfig.*;
import static utilitaire.SimulationUtility.getEvenementActuel;
import static utilitaire.SimulationUtility.pause;

/**
 * Fenêtre graphique principale affichant les statistiques et informations détaillées
 * d'une civilisation dans la simulation. Cette fenêtre affiche :
 *
 * <ul>
 *     <li>Des graphiques (barres, lignes, camembert) sur les tendances et statistiques.</li>
 *     <li>Les informations de la civilisation (nom, dirigeant, nombre de régions, etc.).</li>
 *     <li>Les données économiques, militaires, politiques, religieuses et démographiques.</li>
 *     <li>Les informations sur l'événement en cours.</li>
 * </ul>
 *
 * <p>
 * La classe implémente Runnable afin de mettre à jour dynamiquement l'affichage
 * toutes les 100ms avec les dernières données de simulation.
 * </p>
 *
 * @author Alexandre
 * @author Massinissa
 *
 * @version 2.0
 *
 * @see ChartManager
 * @see SimulationManager
 * @see Civilisation
 */
public class StatsGUI extends JFrame implements Runnable {

    @Serial
    private static final long serialVersionUID = 6756112902171644539L;

    // Labels pour les informations de la civilisation
    private JLabel labelKonungr;
    private JLabel labelNomCivilisation;
    private JLabel labelNbRegions;
    private JLabel labelNbRelations;
    private JLabel labelNbEvenements;

    // Labels pour l'événement en cours
    private JLabel labelEvenementNom;
    private JLabel labelEvenementAnnee;
    private JLabel labelEvenementRegion;

    // Labels pour la religion
    private JLabel labelReligionNom;
    private JLabel labelReligionCroyance;
    private JLabel labelReligionInfluence;

    // Labels pour la population
    private JLabel labelPopulationHabitants;
    private JLabel labelPopulationAgeMoyen;
    private JLabel labelPopulationSexeMajoritaire;

    // Labels pour la politique
    private JLabel labelPolitiqueRegime;
    private JLabel labelPolitiqueStabilite;
    private JLabel labelPolitiqueMilitaire;
    private JLabel labelPolitiqueEconomie;
    private JLabel labelPolitiqueDiplomatie;

    // Économie
    private JProgressBar barRichesse;
    private JLabel labelEconomieRevenu;
    private JLabel labelEconomieDepense;

    // Armée
    private JLabel labelArmeeHersir;
    private JLabel labelArmeeGuerriers;
    private JLabel labelArmeeNavires;
    private JLabel labelArmeeTechniques;
    private JLabel labelEtatArmee;
    private JProgressBar barEtatArmee;

    /**
     * Gestionnaire des graphiques
     */
    private final ChartManager chartManager;

    /**
     * Gestionnaire de simulation
     */
    private final SimulationManager simulationManager;

    /**
     * Constructeur principal de la fenêtre de statistiques.
     *
     * @param chartManager le gestionnaire de graphiques à utiliser
     * @param simulationManager le gestionnaire de simulation contenant les données
     */
    public StatsGUI(ChartManager chartManager, SimulationManager simulationManager) {
        super(SimConfig.NOM_FENETRE_STATS);

        this.chartManager = chartManager;
        this.simulationManager = simulationManager;

        initFrame();
        initComponents();
    }

    /**
     * Initialise les paramètres principaux de la fenêtre (taille, position, fermeture).
     */
    private void initFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(SimConfig.TAILLE_FENETRE_STATS_X, SimConfig.TAILLE_FENETRE_STATS_Y);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(5, 5));
        root.setBackground(COLOR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(root);
    }

    /**
     * Initialise tous les composants graphiques de la fenêtre.
     */
    private void initComponents() {
        JPanel root = (JPanel) getContentPane();

        // Panel central avec les graphiques
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBackground(COLOR_BG_MAIN);
        centerPanel.add(createGraphPanel(), BorderLayout.CENTER);

        // Panneau civilisation en haut du bas
        JPanel civPanel = createCivilisationPanel();
        centerPanel.add(civPanel, BorderLayout.SOUTH);

        root.add(centerPanel, BorderLayout.CENTER);

        // Panneau inférieur avec toutes les infos détaillées
        JPanel bottomPanel = createBottomPanel();
        root.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Crée le panneau affichant les informations de la civilisation.
     * @return JPanel configuré pour la civilisation
     */
    private JPanel createCivilisationPanel() {
        JPanel panel = createHistoricPanel();
        panel.setLayout(new GridLayout(1, 6, 5, 5)); // tout sur 1 ligne, 6 colonnes
        panel.setBorder(createCardBorderWithShadowReducedTop("📜 CIVILISATION"));

        labelNomCivilisation = createHistoricLabel("Nom : Inconnu");
        labelKonungr = createHistoricLabel("Konungr : Inconnu");
        labelNbRegions = createHistoricLabel("Régions : 0");
        labelNbRelations = createHistoricLabel("Relations : 0");
        labelNbEvenements = createHistoricLabel("Événements : 0");
        JLabel empireLabel = createHistoricLabel("Empire : Royaume nordique");

        labelKonungr.setForeground(COLOR_ACCENT);

        panel.add(labelNomCivilisation);
        panel.add(labelKonungr);
        panel.add(labelNbRegions);
        panel.add(labelNbRelations);
        panel.add(labelNbEvenements);
        panel.add(empireLabel);

        return panel;
    }

    /**
     * Crée le panneau inférieur contenant économie, armée, population, politique, religion et événement.
     * @return JPanel configuré pour le bas de la fenêtre
     */
    private JPanel createBottomPanel() {
        JPanel panelBas = new JPanel(new GridLayout(2, 3, 5, 5)); // 2 lignes x 3 colonnes
        panelBas.setBackground(COLOR_BG_MAIN);
        panelBas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panelBas.add(createEconomiePanel());
        panelBas.add(createArmeePanel());
        panelBas.add(createPopulationPanel());
        panelBas.add(createPolitiquePanel());
        panelBas.add(createReligionPanel());
        panelBas.add(createEvenementPanel());

        return panelBas;
    }

    /**
     * Crée le panneau central avec tous les graphiques.
     * @return JPanel contenant les graphiques
     */
    private JPanel createGraphPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 12, 0));
        panel.setBackground(COLOR_BG_MAIN);
        panel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        panel.add(createChartWrapper(chartManager.createBarChart(CHART_DIM), "📊 STATISTIQUES"));
        panel.add(createChartWrapper(chartManager.createLineChart(CHART_DIM), "📈 TENDANCES"));
        panel.add(createChartWrapper(chartManager.createPieChart(CHART_DIM), "🎯 RÉPARTITION"));

        return panel;
    }

    /**
     * Encapsule un graphique dans un panneau avec un titre et bordure stylisée.
     *
     * @param chart le composant graphique à afficher
     * @param title titre du panneau
     * @return JPanel contenant le graphique
     */
    private JPanel createChartWrapper(JComponent chart, String title) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_BG_CARD);
        wrapper.setBorder(createGraphBorder(title));
        wrapper.add(chart, BorderLayout.CENTER);
        return wrapper;
    }

    /**
     * Crée un panneau stylisé pour les cartes historiques (économie, armée, etc.).
     *
     * @param title titre du panneau
     * @return JPanel stylisé
     */
    private Border createGraphBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ACCENT, 2),
                title,
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 13),
                COLOR_ACCENT
        );
        return BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    /**
     * Crée le panneau affichant les informations de l'économie.
     * @return JPanel configuré pour l'économie
     */
    private JPanel createEconomiePanel() {
        JPanel panelEconomie = createHistoricPanel();
        panelEconomie.setBorder(createCardBorderWithShadowReducedTop("💰 ÉCONOMIE"));

        barRichesse = createProgressBar((int) SimConfig.MAX_STYCAS, "0", COLOR_SUCCESS);
        labelEconomieRevenu = createHistoricLabel("Revenu annuel : 0");
        labelEconomieDepense = createHistoricLabel("Dépense annuelle : 0");

        panelEconomie.add(createHistoricLabel("Richesse :"));
        panelEconomie.add(barRichesse);
        panelEconomie.add(labelEconomieRevenu);
        panelEconomie.add(labelEconomieDepense);

        return panelEconomie;
    }

    /**
     * Crée le panneau affichant les informations de l'armée
     * @return JPanel configuré pour l'armée
     */
    private JPanel createArmeePanel() {
        JPanel panelArmee = createHistoricPanel();
        panelArmee.setBorder(createCardBorderWithShadowReducedTop("⚔️ ARMÉE"));

        labelArmeeHersir = createHistoricLabel("Hersir : Inconnu");
        labelArmeeHersir.setForeground(COLOR_ACCENT_RED);
        labelArmeeHersir.setFont(new Font("Serif", Font.BOLD, 13));
        panelArmee.add(labelArmeeHersir);

        labelArmeeGuerriers = createHistoricLabel("Guerriers : 0");
        panelArmee.add(labelArmeeGuerriers);

        labelArmeeNavires = createHistoricLabel("Navires : 0");
        panelArmee.add(labelArmeeNavires);

        labelArmeeTechniques = createHistoricLabel("Techniques : 0");
        panelArmee.add(labelArmeeTechniques);

        labelEtatArmee = createHistoricLabel("État militaire :");
        panelArmee.add(labelEtatArmee);

        barEtatArmee = createProgressBar(100, "0%", COLOR_ACCENT_RED);
        panelArmee.add(barEtatArmee);

        return panelArmee;
    }

    /**
     * Crée le panneau affichant les informations de la politique.
     * @return JPanel configuré pour la politique
     */
    private JPanel createPolitiquePanel() {
        JPanel panelPolitique = createHistoricPanel();
        panelPolitique.setBorder(createCardBorderWithShadowReducedTop("👑 POLITIQUE"));

        labelPolitiqueRegime = createHistoricLabel("Régime : Indéfini");
        labelPolitiqueStabilite = createHistoricLabel("Stabilité : 0%");
        labelPolitiqueMilitaire = createHistoricLabel("Militaire : Neutre");
        labelPolitiqueEconomie = createHistoricLabel("Économie : Inconnue");
        labelPolitiqueDiplomatie = createHistoricLabel("Diplomatie : Neutre");

        panelPolitique.add(labelPolitiqueRegime);
        panelPolitique.add(labelPolitiqueStabilite);
        panelPolitique.add(labelPolitiqueMilitaire);
        panelPolitique.add(labelPolitiqueEconomie);
        panelPolitique.add(labelPolitiqueDiplomatie);

        return panelPolitique;
    }

    /**
     * Crée le panneau affichant les informations de la population.
     * @return JPanel configuré pour la population
     */
    private JPanel createPopulationPanel() {
        JPanel panelPopulation = createHistoricPanel();
        panelPopulation.setBorder(createCardBorderWithShadowReducedTop("👥 POPULATION"));

        labelPopulationHabitants = createHistoricLabel("Habitants : 0");
        labelPopulationAgeMoyen = createHistoricLabel("Âge moyen : 0");
        labelPopulationSexeMajoritaire = createHistoricLabel("Sexe majoritaire : Aucun");

        panelPopulation.add(labelPopulationHabitants);
        panelPopulation.add(labelPopulationAgeMoyen);
        panelPopulation.add(labelPopulationSexeMajoritaire);

        return panelPopulation;
    }

    /**
     * Crée le panneau affichant les informations de la religion.
     * @return JPanel configuré pour la religion
     */
    private JPanel createReligionPanel() {
        JPanel panelReligion = createHistoricPanel();
        panelReligion.setBorder(createCardBorderWithShadowReducedTop("⛪ RELIGION"));

        labelReligionNom = createHistoricLabel("Nom : Inconnue");
        labelReligionCroyance = createHistoricLabel("Croyance : Aucune");
        labelReligionInfluence = createHistoricLabel("Influence : 0%");

        panelReligion.add(labelReligionNom);
        panelReligion.add(labelReligionCroyance);
        panelReligion.add(labelReligionInfluence);

        return panelReligion;
    }

    /**
     * Crée le panneau affichant les informations de l'événement en cours.
     * @return JPanel configuré pour l'événement en cours
     */
    private JPanel createEvenementPanel() {
        JPanel panelEvenement = createHistoricPanel();
        panelEvenement.setBorder(createCardBorderWithShadowReducedTop("🔥 ÉVÉNEMENT EN COURS"));

        labelEvenementNom = createHistoricLabel("Nom : Aucun");
        labelEvenementNom.setFont(new Font("Serif", Font.BOLD, 15));
        labelEvenementNom.setForeground(COLOR_ACCENT);

        labelEvenementAnnee = createHistoricLabel("Année : 0 - 0");
        labelEvenementAnnee.setHorizontalAlignment(SwingConstants.CENTER);
        labelEvenementAnnee.setFont(new Font("Serif", Font.BOLD, 12));
        labelEvenementAnnee.setForeground(COLOR_WARNING);

        labelEvenementRegion = createHistoricLabel("Région : Inconnue");
        labelEvenementRegion.setForeground(COLOR_ACCENT_BLUE);

        panelEvenement.add(labelEvenementNom);
        panelEvenement.add(labelEvenementAnnee);
        panelEvenement.add(labelEvenementRegion);

        return panelEvenement;
    }

    /**
     * Crée un panneau stylisé pour les cartes historiques (économie, armée, etc.).
     *
     * @return JPanel stylisé
     */
    private JPanel createHistoricPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG_CARD);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);
        return panel;
    }

    /**
     * Crée une bordure pour un panneau avec un effet d'ombre réduit en haut.
     *
     * @param title titre de la carte
     * @return Border stylisée
     */
    private Border createCardBorderWithShadowReducedTop(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ACCENT, 2),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 14),
                COLOR_TITLE
        );
        return BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(4, 14, 12, 14) // top réduit
        );
    }

    /**
     * Crée un JLabel avec style historique pour affichage dans les cartes.
     *
     * @param text texte du label
     * @return JLabel stylisé
     */
    private JLabel createHistoricLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(COLOR_TEXT);
        label.setFont(new Font("Serif", Font.PLAIN, 13));
        label.setBorder(new EmptyBorder(0, 0, 3, 0)); // top à 0
        return label;
    }

    /**
     * Crée une barre de progression stylisée.
     *
     * @param max valeur maximale de la barre
     * @param text texte affiché sur la barre
     * @param progressColor couleur de la barre
     * @return JProgressBar configurée
     */
    private JProgressBar createProgressBar(int max, String text, Color progressColor) {
        JProgressBar bar = new JProgressBar(0, max);
        bar.setStringPainted(true);
        bar.setString(text);
        bar.setValue(0);
        bar.setPreferredSize(new Dimension(220, 26));
        bar.setForeground(progressColor);
        bar.setBackground(COLOR_BG_INNER);
        bar.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 2));
        bar.setFont(new Font("SansSerif", Font.BOLD, 12));
        return bar;
    }

    /**
     * Boucle principale de mise à jour de la fenêtre.
     * Met à jour toutes les sections toutes les 100ms.
     */
    @Override
    public void run() {
        while (true) {
            Civilisation civilisation = this.simulationManager.getCivilisation();
            Evenement evenementEnCours = getEvenementActuel(civilisation, simulationManager.getAnneeActuelle(), simulationManager.getNvNbEvenements());
            Religion religion = civilisation.getReligion();
            Population population = civilisation.getPopulation();
            Politique politique = civilisation.getPolitique();
            Economie economie = civilisation.getEconomie();
            Armee armee = civilisation.getArmee();

            updateCivilisationPanel(civilisation);
            updateEvenementPanel(evenementEnCours);
            updateReligionPanel(religion);
            updatePopulationPanel(population);
            updatePolitiquePanel(politique);
            updateEconomiePanel(economie);
            updateArmeePanel(armee);

            pause(100);
        }
    }

    /**
     * Met à jour le panneau de l'armée avec les dernières données.
     *
     * @param armee l'objet Armee contenant les informations
     */
    private void updateArmeePanel(Armee armee) {
        if (armee != null) {
            labelArmeeHersir.setText("Hersir : " + armee.getHersir().getNom());
            labelArmeeGuerriers.setText("Guerriers : " + armee.getNombreGuerriers());
            labelArmeeNavires.setText("Navires : " + armee.getNombreNavires());
            labelArmeeTechniques.setText("Techniques : " + armee.getTechniquesMilitaire());

            labelEtatArmee.setText("État armée : " + ArmeeManager.getDescriptionEtat(armee));
            barEtatArmee.setValue((int) armee.getEtat());
            barEtatArmee.setString((int) armee.getEtat() + "%");
        }
    }

    /**
     * Met à jour le panneau civilisation avec les dernières données.
     *
     * @param civilisation objet Civilisation à afficher
     */
    private void updateCivilisationPanel(Civilisation civilisation) {
        labelNomCivilisation.setText("Nom : " + civilisation.getNom());
        labelKonungr.setText("Konungr (Chef) : " + civilisation.getKonungr().getNom());
        labelNbEvenements.setText("Événements : " + civilisation.getNbEvenements());
        labelNbRegions.setText("Régions : " + civilisation.getNombreRegions());
        labelNbRelations.setText("Relations : " + civilisation.getNombreRelations());
    }

    /**
     * Met à jour le panneau économie avec les dernières données.
     *
     * @param economie objet Economie à afficher
     */
    private void updateEconomiePanel(Economie economie) {
        if (economie != null) {
            barRichesse.setValue((int) economie.getStycas());
            barRichesse.setString((int) economie.getStycas() + "S");
            labelEconomieRevenu.setText("Revenu annuel : " + economie.getGainsAnnuel());
            labelEconomieDepense.setText("Dépense annuelle : " + economie.getPertesAnnuel());
        }
    }

    /**
     * Met à jour le panneau politique avec les dernières données.
     *
     * @param politique objet Politique à afficher
     */
    private void updatePolitiquePanel(Politique politique) {
        if (politique != null) {
            labelPolitiqueRegime.setText("Régime : " + politique.getTypeRegime());
            labelPolitiqueStabilite.setText("Stabilité : " + politique.getStabilite() + "%");
            labelPolitiqueEconomie.setText("Économie : " + politique.getPolitiqueEconomique());
            labelPolitiqueMilitaire.setText("Militaire : " + politique.getPolitiqueMilitaire());
            labelPolitiqueDiplomatie.setText("Diplomatie : " + politique.getPolitiqueDiplomatique());
        }
    }

    /**
     * Met à jour le panneau population avec les dernières données.
     *
     * @param population objet Population à afficher
     */
    private void updatePopulationPanel(Population population) {
        if (population != null) {
            labelPopulationHabitants.setText("Habitants : " + population.getNbHabitants());
            labelPopulationAgeMoyen.setText("Âge moyen : " + (int) population.getAgeMoyen() + " ans");
            labelPopulationSexeMajoritaire.setText("Sexe majoritaire : " + population.getSexeMajoritaire());
        }
    }

    /**
     * Met à jour le panneau religion avec les dernières données.
     *
     * @param religion objet Religion à afficher
     */
    private void updateReligionPanel(Religion religion) {
        if (religion != null) {
            labelReligionNom.setText("Nom : " + religion.getNom());
            labelReligionCroyance.setText("Croyance : " + religion.getCroyance());
            labelReligionInfluence.setText("Influence : " + religion.getInfluence() + "%");
        }
    }

    /**
     * Met à jour le panneau événement en cours avec les dernières données.
     *
     * @param evenementEnCours objet Evenement en cours
     */
    private void updateEvenementPanel(Evenement evenementEnCours) {
        if (evenementEnCours != null) {
            labelEvenementNom.setText("Nom : " + evenementEnCours.getNom());
            labelEvenementAnnee.setText("Année : " + evenementEnCours.getAnneeDebut() + " - " + evenementEnCours.getAnneeFin());
            labelEvenementRegion.setText("Région : " + evenementEnCours.getRegion().getNom() + ", Chef : " + evenementEnCours.getRegion().getChef());
        }
    }

}