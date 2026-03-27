package gui.management;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import config.SimConfig;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Classe qui gère les graphes 
 * 
 * @author Massinissa
 * @version 1.0
 */
public class ChartManager {

    // Attributs
    private XYSeries xySeriesPopulation = new XYSeries("Nombre d'habitants");

    /**
     * Constructeur de la classe ChartManager
     */
    public ChartManager() {

    }

	/**
	 * Méthode qui crée un graphique sous forme de barres
	 * 
	 * @param dim : Dimension du graphe
	 * @return Un graphique sous forme de barres
	 */
	public ChartPanel createBarChart(Dimension dim) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Relations Internes (800-900)", "Années", "Niveau de stabilité",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(dim);
        return panel;
    }

	/**
	 * Méthode qui crée un graphique sous forme de lignes
	 * 
	 * @param dim : Dimension du graphe
	 * @return Un graphique sous forme de lignes
	 */
    public ChartPanel createLineChart(Dimension dim) {
        XYSeriesCollection dataset = new XYSeriesCollection(xySeriesPopulation);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Évolution du nombre d'habitants", "Années", "Nombre d'habitants",
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
        DefaultPieDataset dataset = new DefaultPieDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                "Répartition des Ressources Vikings",
                dataset, true, true, false);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(dim);
        return panel;
    }

    /**
     * Ajoute un point sur le graphique du nombre d'habitants
     *
     * @param annee : Année de la simulation
     * @param nbHabitants : Nombre d'habitants actuel
     */
    public void ajouterPointLineChart(int annee, int nbHabitants) {
        xySeriesPopulation.add(annee, nbHabitants);
    }

}
