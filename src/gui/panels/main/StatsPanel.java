package gui.panels.main;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import config.SimConfig;
import moteur.donnees.Armee;
import moteur.donnees.Civilisation;

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
public class StatsPanel extends JPanel {
	
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
     * Retourne le label affichant le nombre d'habitants.
     *
     * @return JLabel du nombre d'habitants
     */
    public JLabel getLabelValeurNombreHabitants() {
        return labelValeurNombreHabitants;
    }

    /**
     * Retourne la barre de progression de la richesse.
     *
     * @return JProgressBar de richesse
     */
    public JProgressBar getBarRichesse() {
        return barRichesse;
    }

    /**
     * Retourne la barre de progression de l'état de l'armée.
     *
     * @return JProgressBar de l'état de l'armée
     */
    public JProgressBar getBarEtatArmee() {
        return barEtatArmee;
    }

    /**
     * Retourne le label affichant le nom de la religion.
     *
     * @return JLabel du nom de la religion
     */
    public JLabel getLabelValeurNomReligion() {
        return labelValeurNomRegion;
    }

    /**
     * Retourne la barre d'influence de la religion.
     *
     * @return JProgressBar de l'influence religieuse
     */
    public JProgressBar getBarInfluenceReligion() {
        return barInfluenceReligion;
    }

    /**
     * Retourne le label du régime politique.
     *
     * @return JLabel du type de régime
     */
    public JLabel getLabelValeurTypeRegime() {
        return labelValeurTypeRegime;
    }

    /**
     * Retourne le label du nombre de guerriers.
     *
     * @return JLabel du nombre de guerriers
     */
    public JLabel getLabelValeurNombreGuerriers() {
        return labelValeurNombreGuerriers;
    }

    /**
     * Retourne le label du nombre de navires.
     *
     * @return JLabel du nombre de navires
     */
    public JLabel getLabelValeurNombreNavires() {
        return labelValeurNombreNavires;
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
