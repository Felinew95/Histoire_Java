package gui.management;

import java.awt.Dimension;

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
        XYSeries series = new XYSeries("Indice économique");
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Évolution Économique Viking (800-900)", "Années", "Indice économique",
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
    
}
