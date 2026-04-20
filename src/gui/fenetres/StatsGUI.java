package gui.fenetres;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import config.SimConfig;
import gui.management.ChartManager;
import gui.panels.PanelMainGUI;

import moteur.donnees.*;
import moteur.traitement.management.managers.armee.ArmeeManager;

import static config.SimConfig.*;
import static utilitaire.SimulationUtility.getEvenementActuel;
import static utilitaire.SimulationUtility.pause;

/**
 * Fenêtre graphique de statistiques dynamiques d'une civilisation.
 *
 * <p>
 * Cette interface permet d’afficher en temps réel les données principales
 * d’une civilisation dans la simulation : économie, armée, population,
 * politique, religion et événements historiques.
 * </p>
 *
 * <p>
 * La classe fonctionne en mode observateur et est mise à jour en continu
 * via un thread dédié. Elle est conçue pour être robuste face aux valeurs nulles
 * afin d’éviter les erreurs lors des changements de POV ou d’initialisation
 * partielle de la simulation.
 * </p>
 *
 * <p>
 * Elle s’appuie sur {@link PanelMainGUI} pour récupérer la civilisation active
 * et sur {@link ChartManager} pour afficher les graphiques statistiques.
 * </p>
 *
 * @author Alexandre
 * @author Massinissa
 * @author Tauseef
 * 
 * @version 3.1
 * 
 * @see Runnable
 * @see JFrame
 */
public class StatsGUI extends JFrame implements Runnable {

    /** Identifiant de sérialisation de la fenêtre Swing. */
    private static final long serialVersionUID = 6756112902171644539L;

    /**
     * Civilisation actuellement affichée dans l'interface.
     * Peut correspondre au POV sélectionné ou à une civilisation par défaut.
     */
    private Civilisation civ;
    
    /**
     * Gestionnaire des graphiques statistiques (histogrammes, courbes, secteurs).
     */
    private final ChartManager chartManager;

    /**
     * Référence vers l'interface principale permettant d'accéder
     * à la simulation et au POV actuel.
     */
    private final PanelMainGUI panelMainGUI;

    // --- Labels d'identité ---
    /** Label affichant le nom de la civilisation. */
    private JLabel labelNomCivilisation;
    /** Label affichant le nom du chef (Konungr). */
    private JLabel labelKonungr;
    /** Label affichant le nombre de régions possédées. */
    private JLabel labelNbRegions;
    /** Label affichant le nombre de relations diplomatiques actives. */
    private JLabel labelNbRelations;
    /** Label affichant le compteur d'événements historiques. */
    private JLabel labelNbEvenements;

    // --- Labels d'événements ---
    /** Label affichant le nom de l'événement actuel. */
    private JLabel labelEvenementNom;
    /** Label affichant la date ou durée de l'événement. */
    private JLabel labelEvenementAnnee;
    /** Label affichant la région impactée par l'événement. */
    private JLabel labelEvenementRegion;

    // --- Labels de religion ---
    /** Label affichant le nom de la religion. */
    private JLabel labelReligionNom;
    /** Label affichant la croyance associée. */
    private JLabel labelReligionCroyance;
    /** Label affichant le taux d'influence religieuse. */
    private JLabel labelReligionInfluence;

    // --- Labels de démographie ---
    /** Label affichant le nombre d'habitants. */
    private JLabel labelPopulationHabitants;
    /** Label affichant l'âge moyen calculé. */
    private JLabel labelPopulationAgeMoyen;
    /** Label affichant le sexe prédominant. */
    private JLabel labelPopulationSexeMajoritaire;

    // --- Labels de politique ---
    /** Label affichant le régime politique actuel. */
    private JLabel labelPolitiqueRegime;
    /** Label affichant le score de stabilité. */
    private JLabel labelPolitiqueStabilite;
    /** Label affichant la doctrine militaire. */
    private JLabel labelPolitiqueMilitaire;
    /** Label affichant la doctrine économique. */
    private JLabel labelPolitiqueEconomie;
    /** Label affichant la doctrine diplomatique. */
    private JLabel labelPolitiqueDiplomatie;

    // --- Économie ---
    /** Barre de progression visuelle pour la richesse en Stycas. */
    private JProgressBar barRichesse;
    /** Label affichant les gains annuels. */
    private JLabel labelEconomieRevenu;
    /** Label affichant les pertes annuelles. */
    private JLabel labelEconomieDepense;

    // --- Armée ---
    /** Label affichant le nom du Hersir (général). */
    private JLabel labelArmeeHersir;
    /** Label affichant le nombre de troupes au sol. */
    private JLabel labelArmeeGuerriers;
    /** Label affichant le nombre de drakkars/navires. */
    private JLabel labelArmeeNavires;
    /** Label affichant le niveau technologique militaire. */
    private JLabel labelArmeeTechniques;
    /** Label décrivant l'état moral/matériel de l'armée. */
    private JLabel labelEtatArmee;
    /** Barre de progression visuelle de la condition de l'armée. */
    private JProgressBar barEtatArmee;
    
    /**
     * Initialise une nouvelle fenêtre de statistiques.
     * Tente de récupérer la civilisation sélectionnée dans l'interface principale.
     * * @param chartManager Gestionnaire pour la génération des graphiques JFreeChart.
     * @param panelMainGUI Référence vers le panneau principal pour la synchronisation des données.
     */
    public StatsGUI(ChartManager chartManager, PanelMainGUI panelMainGUI) {
        super(SimConfig.NOM_FENETRE_STATS);
        this.chartManager = chartManager;
        this.panelMainGUI = panelMainGUI;
        
        // Initialisation sécurisée de la civilisation
        this.civ = panelMainGUI.getPovActuel();
        if (this.civ == null) {
            this.civ = panelMainGUI.getSimulation().getCivilisation();
        }
        
        initFrame();
        initComponents();
    }
    
    /**
     * Configure les propriétés structurelles de la JFrame (taille, fermeture, centrage).
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
     * Instancie et organise les composants graphiques dans la fenêtre.
     */
    private void initComponents() {
        JPanel root = (JPanel) getContentPane();
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBackground(COLOR_BG_MAIN);
        centerPanel.add(createGraphPanel(), BorderLayout.CENTER);
        centerPanel.add(createCivilisationPanel(), BorderLayout.SOUTH);
        root.add(centerPanel, BorderLayout.CENTER);
        root.add(createBottomPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Crée le panneau d'en-tête contenant les informations d'identité de la civilisation.
     * @return Un JPanel configuré.
     */
    private JPanel createCivilisationPanel() {
        JPanel panel = createHistoricPanel();
        panel.setLayout(new GridLayout(1, 6, 5, 5));
        panel.setBorder(createCardBorderWithShadowReducedTop("📜 CIVILISATION"));

        String nom = (civ != null) ? civ.getNom() : "Inconnu";
        String chef = (civ != null && civ.getKonungr() != null) ? civ.getKonungr().getNom() : "Aucun";

        labelNomCivilisation = createHistoricLabel("Nom : " + nom);
        labelKonungr         = createHistoricLabel("Chef : " + chef);
        labelNbRegions       = createHistoricLabel("Régions : 0");
        labelNbRelations     = createHistoricLabel("Relations : 0");
        labelNbEvenements    = createHistoricLabel("Événements : 0");
        JLabel empireLabel   = createHistoricLabel("Empire : Nordique");

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
     * Crée le panneau regroupant les différentes cartes de statistiques (Économie, Armée, etc.).
     * @return Un JPanel organisé en grille.
     */
    private JPanel createBottomPanel() {
        JPanel panelBas = new JPanel(new GridLayout(2, 3, 5, 5));
        panelBas.setBackground(COLOR_BG_MAIN);
        panelBas.add(createEconomiePanel());
        panelBas.add(createArmeePanel());
        panelBas.add(createPopulationPanel());
        panelBas.add(createPolitiquePanel());
        panelBas.add(createReligionPanel());
        panelBas.add(createEvenementPanel());
        return panelBas;
    }

    /**
     * Crée le panneau central contenant les trois graphiques analytiques.
     * @return Un JPanel contenant les graphiques du ChartManager.
     */
    private JPanel createGraphPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 12, 0));
        panel.setBackground(COLOR_BG_MAIN);
        panel.add(createChartWrapper(chartManager.createBarChart(civ.getNom(), CHART_DIM),  "📊 STATS"));
        panel.add(createChartWrapper(chartManager.createLineChart(civ.getNom(),CHART_DIM), "📈 FLUX"));
        panel.add(createChartWrapper(chartManager.createPieChart(civ.getNom(),CHART_DIM),  "🎯 RÉPARTITION"));
        return panel;
    }

    /**
     * Encapsule un graphique JComponent dans un panneau stylisé.
     * @param chart Le composant graphique à intégrer.
     * @param title Le titre à afficher au-dessus du graphique.
     * @return Le panneau conteneur.
     */
    private JPanel createChartWrapper(JComponent chart, String title) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_BG_CARD);
        wrapper.setBorder(createGraphBorder(title));
        if(chart != null) wrapper.add(chart, BorderLayout.CENTER);
        return wrapper;
    }

    /**
     * Génère une bordure titrée et stylisée pour les graphiques.
     * @param title Texte du titre.
     * @return Une Border complexe.
     */
    private Border createGraphBorder(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_ACCENT, 2), title, TitledBorder.CENTER, TitledBorder.TOP, new Font("Serif", Font.BOLD, 13), COLOR_ACCENT);
        return BorderFactory.createCompoundBorder(tb, BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Initialise le panneau dédié aux finances.
     * @return Un JPanel configuré pour l'économie.
     */
    private JPanel createEconomiePanel() {
        JPanel p = createHistoricPanel();
        p.setBorder(createCardBorderWithShadowReducedTop("💰 ÉCONOMIE"));
        barRichesse = createProgressBar((int) SimConfig.MAX_STYCAS, "0S", COLOR_SUCCESS);
        labelEconomieRevenu = createHistoricLabel("Revenu : 0");
        labelEconomieDepense = createHistoricLabel("Dépense : 0");
        p.add(createHistoricLabel("Richesse :"));
        p.add(barRichesse);
        p.add(labelEconomieRevenu);
        p.add(labelEconomieDepense);
        return p;
    }

    /**
     * Initialise le panneau dédié aux forces militaires.
     * @return Un JPanel configuré pour l'armée.
     */
    private JPanel createArmeePanel() {
        JPanel p = createHistoricPanel();
        p.setBorder(createCardBorderWithShadowReducedTop("⚔️ ARMÉE"));
        labelArmeeHersir = createHistoricLabel("Hersir : Aucun");
        labelArmeeGuerriers = createHistoricLabel("Guerriers : 0");
        labelArmeeNavires = createHistoricLabel("Navires : 0");
        labelArmeeTechniques = createHistoricLabel("Techniques : 0");
        labelEtatArmee = createHistoricLabel("État : Inconnu");
        barEtatArmee = createProgressBar(100, "0%", COLOR_ACCENT_RED);
        p.add(labelArmeeHersir); p.add(labelArmeeGuerriers); p.add(labelArmeeNavires);
        p.add(labelArmeeTechniques); p.add(labelEtatArmee); p.add(barEtatArmee);
        return p;
    }

    /**
     * Initialise le panneau dédié à l'orientation politique.
     * @return Un JPanel configuré pour la politique.
     */
    private JPanel createPolitiquePanel() {
        JPanel p = createHistoricPanel();
        p.setBorder(createCardBorderWithShadowReducedTop("👑 POLITIQUE"));
        labelPolitiqueRegime = createHistoricLabel("Régime : —");
        labelPolitiqueStabilite = createHistoricLabel("Stabilité : 0%");
        labelPolitiqueMilitaire = createHistoricLabel("Militaire : —");
        labelPolitiqueEconomie = createHistoricLabel("Économie : —");
        labelPolitiqueDiplomatie = createHistoricLabel("Diplomatie : —");
        p.add(labelPolitiqueRegime); p.add(labelPolitiqueStabilite); p.add(labelPolitiqueMilitaire);
        p.add(labelPolitiqueEconomie); p.add(labelPolitiqueDiplomatie);
        return p;
    }

    /**
     * Initialise le panneau dédié aux informations démographiques.
     * @return Un JPanel configuré pour la population.
     */
    private JPanel createPopulationPanel() {
        JPanel p = createHistoricPanel();
        p.setBorder(createCardBorderWithShadowReducedTop("👥 POPULATION"));
        labelPopulationHabitants = createHistoricLabel("Habitants : 0");
        labelPopulationAgeMoyen = createHistoricLabel("Âge moyen : 0");
        labelPopulationSexeMajoritaire = createHistoricLabel("Sexe : —");
        p.add(labelPopulationHabitants); p.add(labelPopulationAgeMoyen); p.add(labelPopulationSexeMajoritaire);
        return p;
    }

    /**
     * Initialise le panneau dédié à l'influence religieuse.
     * @return Un JPanel configuré pour la religion.
     */
    private JPanel createReligionPanel() {
        JPanel p = createHistoricPanel();
        p.setBorder(createCardBorderWithShadowReducedTop("⛪ RELIGION"));
        labelReligionNom = createHistoricLabel("Nom : —");
        labelReligionCroyance = createHistoricLabel("Croyance : —");
        labelReligionInfluence = createHistoricLabel("Influence : 0%");
        p.add(labelReligionNom); p.add(labelReligionCroyance); p.add(labelReligionInfluence);
        return p;
    }

    /**
     * Initialise le panneau dédié aux événements historiques en cours.
     * @return Un JPanel configuré pour l'actualité.
     */
    private JPanel createEvenementPanel() {
        JPanel p = createHistoricPanel();
        p.setBorder(createCardBorderWithShadowReducedTop("🔥 ACTUALITÉ"));
        labelEvenementNom = createHistoricLabel("Nom : Aucun");
        labelEvenementAnnee = createHistoricLabel("Année : 0");
        labelEvenementRegion = createHistoricLabel("Région : —");
        p.add(labelEvenementNom); p.add(labelEvenementAnnee); p.add(labelEvenementRegion);
        return p;
    }

    /**
     * Boucle principale du thread de mise à jour.
     * Rafraîchit les données toutes les 200 ms.
     */
    @Override
    public void run() {
        while (true) {
            Civilisation cible = panelMainGUI.getPovActuel();
            if (cible == null) cible = this.civ;

            if (cible != null) {
                updateUI(cible);
            }
                        
            pause(200);
        }
    }

	/**
     * Met à jour l'ensemble des labels et barres de progression de l'interface.
     * Gère les calculs dynamiques comme l'événement actuel.
     * * @param c La civilisation dont les données doivent être affichées.
     */
    private void updateUI(Civilisation c) {
    	this.civ = c;
        labelNomCivilisation.setText("Nom : " + c.getNom());
        
        int annee = panelMainGUI.getSimulation().getAnneeActuelle();
        Konungr k = c.getKonungr();
        if(k != null && k.getAnneeDebut() <= annee && k.getAnneeFin() >= annee) {
            labelKonungr.setText("Chef : " + c.getKonungr().getNom());
        } else {
            labelKonungr.setText("Chef : Inconnu");
        }
        
        labelNbRegions.setText("Régions : " + c.getNombreRegions());
        labelNbRelations.setText("Relations : " + c.getNombreRelations());
        labelNbEvenements.setText("Événements : " + c.getNbEvenements());

        updateEconomie(c.getEconomie());
        updateArmee(c.getArmee());
        updatePopulation(c.getPopulation());
        updatePolitique(c.getPolitique());
        updateReligion(c.getReligion());
        
        Evenement ev = getEvenementActuel(c, panelMainGUI.getSimulation().getAnneeActuelle(), c.getNbEvenements());
        if(ev != null) {
            labelEvenementNom.setText("Nom : " + ev.getNom());
            labelEvenementAnnee.setText("Année : " + ev.getAnneeDebut());
            if(ev.getRegion() != null) labelEvenementRegion.setText("Région : " + ev.getRegion().getNom());
        }
    }

    /**
     * Rafraîchit les données économiques.
     * @param e Instance des données financières.
     */
    private void updateEconomie(Economie e) {
        if (e == null) return;
        barRichesse.setValue((int) e.getStycas());
        barRichesse.setString((int) e.getStycas() + " S");
        labelEconomieRevenu.setText("Revenu : " + e.getGainsAnnuel());
        labelEconomieDepense.setText("Dépense : " + e.getPertesAnnuel());
    }

    /**
     * Rafraîchit les données militaires.
     * @param a Instance des données de l'armée.
     */
    private void updateArmee(Armee a) {
        if (a == null) return;
        
        int annee = panelMainGUI.getSimulation().getAnneeActuelle();
        Kersir k = a.getHersir();
        if(k != null && k.getAnneeDebut() <= annee && k.getAnneeFin() >= annee) {
            labelArmeeHersir.setText("Général : " + a.getHersir().getNom());
        } else {
            labelArmeeHersir.setText("Général : Inconnu");
        }
        
        labelArmeeGuerriers.setText("Guerriers : " + a.getNombreGuerriers());
        labelArmeeNavires.setText("Navires : " + a.getNombreNavires());
        labelArmeeTechniques.setText("Techniques : " + a.getTechniquesMilitaire());
        labelEtatArmee.setText("État : " + ArmeeManager.getDescriptionEtat(a));
        barEtatArmee.setValue((int) a.getEtat());
        barEtatArmee.setString((int) a.getEtat() + "%");
    }

    /**
     * Rafraîchit les données démographiques.
     * @param p Instance des données de population.
     */
    private void updatePopulation(Population p) {
        if (p == null) return;
        labelPopulationHabitants.setText("Habitants : " + p.getNbHabitants());
        labelPopulationAgeMoyen.setText("Âge moyen : " + (int) p.getAgeMoyen());
        labelPopulationSexeMajoritaire.setText("Sexe : " + p.getSexeMajoritaire());
    }

    /**
     * Rafraîchit les données politiques.
     * @param p Instance des données de politique.
     */
    private void updatePolitique(Politique p) {
        if (p == null) return;
        labelPolitiqueRegime.setText("Régime : " + p.getTypeRegime());
        labelPolitiqueStabilite.setText("Stabilité : " + p.getStabilite() + "%");
        labelPolitiqueEconomie.setText("Économie : " + p.getPolitiqueEconomique());
        labelPolitiqueDiplomatie.setText("Diplomatie : " + p.getPolitiqueDiplomatique());
        labelPolitiqueMilitaire.setText("Militaire : " + p.getPolitiqueMilitaire());
    }

    /**
     * Rafraîchit les données religieuses.
     * @param r Instance des données de religion.
     */
    private void updateReligion(Religion r) {
        if (r == null) return;
        labelReligionNom.setText("Nom : " + r.getNom());
        labelReligionInfluence.setText("Influence : " + r.getInfluence() + "%");
        labelReligionCroyance.setText("Croyance : " + r.getCroyance());
    }

    /**
     * Helper pour créer un panneau stylisé avec un fond spécifique.
     * @return Un JPanel configuré en BoxLayout vertical.
     */
    private JPanel createHistoricPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(COLOR_BG_CARD);
        return p;
    }

    /**
     * Crée une bordure titrée et décorative pour les cartes de statistiques.
     * @param title Le titre de la section.
     * @return Une Border composée.
     */
    private Border createCardBorderWithShadowReducedTop(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_ACCENT, 2), title, TitledBorder.LEFT, TitledBorder.TOP, new Font("Serif", Font.BOLD, 14), COLOR_TITLE);
        return BorderFactory.createCompoundBorder(tb, BorderFactory.createEmptyBorder(4, 14, 12, 14));
    }

    /**
     * Helper pour créer des labels uniformes au style "Historique".
     * @param text Texte initial du label.
     * @return Un JLabel configuré.
     */
    private JLabel createHistoricLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(COLOR_TEXT);
        label.setFont(new Font("Serif", Font.PLAIN, 13));
        label.setBorder(new EmptyBorder(0, 0, 3, 0));
        return label;
    }

    /**
     * Helper pour créer des barres de progression stylisées.
     * @param max Valeur maximale de la barre.
     * @param text Texte à afficher sur la barre.
     * @param progressColor Couleur de la jauge.
     * @return Une JProgressBar configurée.
     */
    private JProgressBar createProgressBar(int max, String text, Color progressColor) {
        JProgressBar bar = new JProgressBar(0, max);
        bar.setStringPainted(true);
        bar.setString(text);
        bar.setPreferredSize(new Dimension(180, 22));
        bar.setForeground(progressColor);
        bar.setBackground(COLOR_BG_INNER);
        return bar;
    }
}