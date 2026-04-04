package gui.management;

import java.awt.Dimension;
import java.util.HashMap;

import moteur.donnees.Economie;
import moteur.donnees.Ressource;

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
    private final XYSeries xySeriesRichesse = new XYSeries("Nombre d'habitants");
    private final DefaultPieDataset dataset = new DefaultPieDataset();

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
                dataset, true, true, false);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(dim);
        return panel;
    }

    /**
     * Ajoute un point sur le graphique du nombre d'habitants
     *
     * @param annee : Année de la simulation
     */
    public void ajouterPointLineChart(int annee, float stycas) {
        xySeriesRichesse.add(annee, stycas);
    }

    public void updateRessourcesPieChart(Economie economie) {
        HashMap<String, Ressource> ressourcesImportees = economie.getRessourcesImportees();

        dataset.clear();
        for (Ressource ressource : ressourcesImportees.values()) {
            dataset.setValue(ressource.getNom(), ressource.getQuantite());
        }
    }

}
