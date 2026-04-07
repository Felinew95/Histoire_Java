package gui.management;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import moteur.donnees.Economie;
import moteur.donnees.Relation;
import moteur.donnees.Ressource;
import moteur.traitement.management.managers.politique.RelationManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Classe qui gère les graphes de la simulation.
 *
 * <p>
 * Le BarChart affiche le niveau des relations diplomatiques avec chaque civilisation.
 * Le LineChart affiche l'évolution de la richesse au fil des années.
 * Le PieChart affiche la répartition des ressources importées.
 * </p>
 *
 * @author Massinissa
 * @author Alexandre
 *
 * @version 2.0
 */
public class ChartManager {

    // Attributs
    private final XYSeries xySeriesRichesse = new XYSeries("Richesse (Stycas)");
    private final DefaultPieDataset datasetPie = new DefaultPieDataset();

    // Dataset du BarChart des relations — attribut pour pouvoir le mettre à jour
    private final DefaultCategoryDataset datasetRelations = new DefaultCategoryDataset();

    /**
     * Constructeur de la classe ChartManager
     */
    public ChartManager() {
    }

    /**
     * Méthode qui crée un graphique sous forme de barres affichant
     * le niveau des relations diplomatiques avec les autres civilisations.
     *
     * @param dim : Dimension du graphe
     * @return Un graphique sous forme de barres
     */
    public ChartPanel createBarChart(Dimension dim) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Relations diplomatiques", "Civilisation", "Niveau (0-100)",
                datasetRelations, PlotOrientation.VERTICAL, false, true, false);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(dim);
        return panel;
    }

    /**
     * Met à jour le BarChart des relations avec les données actuelles.
     * Affiche le niveau numérique et le statut (Hostile/Tendue/Neutre/Alliance).
     *
     * @param relations : Liste des relations de la civilisation
     */
    public void updateRelationsBarChart(ArrayList<Relation> relations) {
        if (relations == null) {
            return;
        }

        datasetRelations.clear();
        for (Relation r : relations) {
            String statut = RelationManager.getDescriptionRelation(r);
            // La clé de série = statut, la clé de catégorie = nom de la civilisation
            datasetRelations.addValue(r.getNiveau(), statut, r.getNomCivilisation());
        }
    }

    /**
     * Méthode qui crée un graphique sous forme de lignes
     *
     * @param dim : Dimension du graphe
     * @return Un graphique sous forme de lignes
     */
    public ChartPanel createLineChart(Dimension dim) {
        XYSeriesCollection dataset = new XYSeriesCollection(xySeriesRichesse);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Évolution de la richesse", "Années", "Richesse (Stycas)",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(dim);
        return panel;
    }

    /**
     * Méthode qui crée un graphique sous forme de camembert
     *
     * @param dim : Dimension du graphe
     * @return Un graphique sous forme de camembert
     */
    public ChartPanel createPieChart(Dimension dim) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Répartition des Ressources Vikings",
                datasetPie, true, true, false);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(dim);
        return panel;
    }

    /**
     * Ajoute un point sur le graphique de la richesse
     *
     * @param annee  : Année de la simulation
     * @param stycas : Richesse actuelle
     */
    public void ajouterPointLineChart(int annee, float stycas) {
        xySeriesRichesse.add(annee, stycas);
    }

    /**
     * Met à jour le camembert des ressources
     *
     * @param economie : Economie de la civilisation
     */
    public void updateRessourcesPieChart(Economie economie) {
        HashMap<String, Ressource> ressourcesImportees = economie.getRessourcesImportees();

        datasetPie.clear();
        for (Ressource ressource : ressourcesImportees.values()) {
            datasetPie.setValue(ressource.getNom(), ressource.getQuantite());
        }
    }

}