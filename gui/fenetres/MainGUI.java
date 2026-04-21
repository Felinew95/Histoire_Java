package gui.fenetres;

import java.io.File;

import javax.swing.*;

import config.SimConfig;
import gui.management.ChartManager;
import gui.management.MapManager;
import gui.panels.LoadingPanelGUI;
import gui.panels.PanelMenuGUI;
import gui.panels.main.PanelMainGUI;
import gui.panels.main.StatsPanel;
import gui.panels.PanelFinSimulation;

import moteur.donnees.*;
import moteur.traitement.management.managers.civilisation.CivilisationManager;

import utilitaire.SimulationUtility;

import static utilitaire.SimulationUtility.pause;

/**
 * Fenêtre principale de l'application graphique.
 *
 * <p>
 * Gère la boucle principale de simulation. À chaque tour :
 * <ol>
 * <li>La simulation Viking avance d'une année</li>
 * <li>Chaque simulation adverse (IA) avance d'une année</li>
 * <li>L'affichage est mis à jour selon le POV sélectionné</li>
 * </ol>
 * </p>
 *
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 2.0
 */
public class MainGUI extends JFrame implements Runnable {

	/**
	 * Identifiant de sérialisation
	 */
	private static final long serialVersionUID = -2795381101149584214L;

	/**
	 * Panneau principal contenant la simulation.
	 */
	private final PanelMainGUI panelMainGUI;

	/**
	 * Panneau affiché pendant le chargement.
	 */
	private final LoadingPanelGUI panelLoad;

	/**
	 * Indique si l'application doit être fermée.
	 */
	private boolean estQuitter = false;

	/**
	 * Indique si le chargement est terminé.
	 */
	private boolean estCharge = false;

	/**
	 * Indique si la simulation a été lancée.
	 */
	private boolean estLancer = false;

	/**
	 * Nombre d'événements déjà affichés dans la narration.
	 */
	private int nbEvenements;

	/**
	 * Gestionnaire principal de la simulation.
	 */
	private final CivilisationManager sim;

	/**
	 * Civilisation actuellement simulée.
	 */
	private final Civilisation civilisation;

	/**
	 * Gestionnaire de la carte et de son affichage.
	 */
	private final MapManager mapManager;

	/**
	 * Initialise la fenêtre principale et ses composants.
	 *
	 * <p>
	 * Configure :
	 * <ul>
	 *     <li>Le titre et l'icône de la fenêtre</li>
	 *     <li>Les différents panneaux (menu, simulation, chargement)</li>
	 *     <li>Les gestionnaires nécessaires (simulation, carte)</li>
	 * </ul>
	 * </p>
	 */
    public MainGUI() {
        super(SimConfig.NOM_FENETRE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(SimulationUtility.readImage(new File("src/images/favicon.png")));

        PanelMenuGUI panelMenuGUI = new PanelMenuGUI(this);
        this.panelMainGUI = new PanelMainGUI();
        this.panelLoad = new LoadingPanelGUI();

        this.sim= this.panelMainGUI.getSimulation();
        this.civilisation= this.sim.getCivilisation();
        this.nbEvenements = civilisation.getNbEvenements();

        this.mapManager = new MapManager(sim, panelMainGUI.getPanelSim(), panelMainGUI.getPaintStrategy());

        this.setContentPane(panelMenuGUI);
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(SimConfig.TAILLE_FENETRE_X, SimConfig.TAILLE_FENETRE_Y);
    }

    /**
	 * Lance la simulation.
	 *
	 * <p>
	 * Remplace le panneau courant par le panneau principal de simulation
	 * et active les indicateurs de chargement et de lancement.
	 * </p>
	 */
    public void lancerJeu() {
        this.setContentPane(panelLoad);
        this.estLancer = true;
        this.revalidate();
    }

	/**
	 * Quitte l'application.
	 *
	 * <p>
	 * Met à jour l'état interne puis termine le programme.
	 * </p>
	 */
    public void quitterJeu() {
        this.estQuitter = true;
        System.exit(0);
    }

    /**
	 * Affiche une boîte de dialogue contenant les crédits de l'application.
	 */
    public void afficherCredits() {
        JOptionPane.showMessageDialog(null,
                "Auteurs : Alexandre BURIN, Tauseef AHMED, Massinissa LOMANI",
                "Crédits", JOptionPane.INFORMATION_MESSAGE);
    }


	/**
	 * Boucle principale de l'application.
	 *
	 * <p>
	 * Cette méthode gère les différentes phases de l'application :
	 * </p>
	 *
	 * <ul>
	 *     <li>Attente du lancement du jeu</li>
	 *     <li>Phase de chargement</li>
	 *     <li>Boucle de simulation continue</li>
	 * </ul>
	 *
	 * <p>
	 * Durant la boucle principale :
	 * <ul>
	 *     <li>Mise à jour de l'année affichée</li>
	 *     <li>Exécution d'un cycle de simulation</li>
	 *     <li>Mise à jour de la narration</li>
	 *     <li>Actualisation de la carte</li>
	 *     <li>Mise à jour des statistiques (population, économie, armée, etc.)</li>
	 *     <li>Rafraîchissement de l'affichage</li>
	 * </ul>
	 * </p>
	 */
    @Override
    public void run() {
        while (!estLancer) {
            this.repaint();
        }

        while (!estCharge) {
            double pourcentage = this.panelLoad.getPourcentage();
            if (pourcentage == 100) {
                this.setContentPane(panelMainGUI);
                this.estCharge = true;
            }
            this.repaint();
        }
        
        setCharts();
        while (!estQuitter && sim.getAnneeActuelle() < SimConfig.ANNEE_FIN_SIM) {
            this.panelMainGUI.getPanelInfos().getLabelValeurAnnee().setText(String.valueOf(sim.getAnneeActuelle()));
            sim.nextRound();
            
            ChartManager chartManager = panelMainGUI.getChartManager();
            for (Civilisation civ : panelMainGUI.getToutesLesCivs()) {
                chartManager.ajouterPointLineChart(civ.getNom(), panelMainGUI.getSimulation().getAnneeActuelle(), civ.getEconomie().getStycas());
                chartManager.updateRelationsBarChart(civ.getNom(), civ.getRelations());
                chartManager.updateRessourcesPieChart(civ.getNom(), civ.getEconomie());
            }
            

            for (CivilisationManager simAdverse : panelMainGUI.getSimulationManagersAdverses()) {
                simAdverse.nextRound();
            }

            int nvNbEvenements = civilisation.getNbEvenements();
            Evenement evenement = (nvNbEvenements > 0) ? civilisation.getEvenement(nvNbEvenements - 1) : null;
            setTexteNarration(nvNbEvenements, evenement);
        
            this.mapManager.chargerCarteRegion(sim.getAnneeActuelle(), evenement);

            
            Civilisation civAffichee = panelMainGUI.getCivilisationActuelle();
            
            
            if (civAffichee == null) {
                civAffichee = this.sim.getCivilisation(); 
            }
            
            rafraichirStatsInterface(civAffichee);

            this.repaint();
            pause(100);
        }

        afficherFinSimulation();
    }

    /**
     * Initialise les graphiques de la simulation avec un point de départ commun.
     *
     * <p>
     * Cette méthode ajoute, pour chaque civilisation existante,
     * un premier point dans le graphique de richesse correspondant
     * à l'année de début de la simulation (définie dans {@link SimConfig#ANNEE_DEBUT_SIM}).
     * </p>
     *
     * <p>
     * Cette méthode doit être appelée une seule fois avant le démarrage
     * de la boucle principale de simulation afin d'éviter les doublons.
     * </p>
     */
    private void setCharts() {
        int anneeDebut = SimConfig.ANNEE_DEBUT_SIM;

        for (Civilisation civ : panelMainGUI.getToutesLesCivs()) {
            panelMainGUI.getChartManager().ajouterPointLineChart(
                civ.getNom(),
                anneeDebut,
                civ.getEconomie().getStycas()
            );
        }
    }

    /**
     * Rafraîchit les statistiques affichées dans l'interface graphique
     * pour la civilisation passée en paramètre Viking ou Adverse.
     */
    private void rafraichirStatsInterface(Civilisation civ) {
        if (civ == null) return;
        
        StatsPanel panelStats = panelMainGUI.getPanelStats();
        if (civ.getPopulation() != null) {
        	panelStats.getLabelValeurNombreHabitants().setText(
                    String.valueOf(civ.getPopulation().getNbHabitants()));
        }

   
        if (civ.getEconomie() != null) {
            int stycas = (int) civ.getEconomie().getStycas();
            JProgressBar barRichesse = panelStats.getBarRichesse();
            if (barRichesse.getMaximum() <= stycas) barRichesse.setMaximum(stycas * 2);
            barRichesse.setValue(stycas);
            barRichesse.setString(stycas + " S");
        }

       
        Armee armee = civ.getArmee();
        if (armee != null) {
        	panelStats.getLabelValeurNombreGuerriers().setText(String.valueOf(armee.getNombreGuerriers()));
        	panelStats.getLabelValeurNombreNavires().setText(String.valueOf(armee.getNombreNavires()));
        	panelStats.getBarEtatArmee().setValue((int) armee.getEtat());
        	panelStats.getBarEtatArmee().setString((int) armee.getEtat() + "%");
        }


        Religion religion = civ.getReligion();
        if (religion != null) {
        	panelStats.getLabelValeurNomReligion().setText(religion.getNom());
        	panelStats.getBarInfluenceReligion().setValue((int) religion.getInfluence());
        	panelStats.getBarInfluenceReligion().setString((int) religion.getInfluence() + "%");
        }

        
        Politique politique = civ.getPolitique();
        if (politique != null) {
        	panelStats.getLabelValeurTypeRegime().setText(
                    politique.getTypeRegime() + " — " + politique.getEtatStabilite());
        }
    }


    /**
     * Gère l'affichage de l'écran récapitulatif à la fin de la simulation.
     * 
     * <p>
     * Cette méthode remplace le contenu actuel de la fenêtre par un nouveau 
     * {@code PanelFinSimulation}, en lui passant les données de la civilisation 
     * ainsi que les bornes temporelles configurées. Elle force ensuite le 
     * recalcul de la mise en page et le rafraîchissement visuel du composant.
     * </p>
     */
    private void afficherFinSimulation() {
        PanelFinSimulation panelFin = new PanelFinSimulation(
                civilisation, SimConfig.ANNEE_DEBUT_SIM, SimConfig.ANNEE_FIN_SIM);
        this.setContentPane(panelFin);
        this.revalidate();
        this.repaint();
    }

    /**
	 * Met à jour le texte de narration affiché.
	 *
	 * <p>
	 * Le texte est mis à jour uniquement lorsqu'un nouvel événement apparaît.
	 * </p>
	 *
	 * @param nvNbEvenements nombre actuel d'événements
	 * @param evenement dernier événement survenu
	 */
    private void setTexteNarration(int nvNbEvenements, Evenement evenement) {
		if (nbEvenements < nvNbEvenements) {
			JTextPane textNarration = this.panelMainGUI.getPanelNarration().getTexteNarration();
			String infos = evenement.toString();
			String narration = evenement.getNarration();

			textNarration.setText(infos + "\n" + narration);
			nbEvenements = nvNbEvenements;
		}
	}
    
}