package gui.management;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
 * @author Tauseef
 *
 * @version 2.1
 */
public class ChartManager {
	
	/**
     * Stocke les séries de données temporelles pour chaque civilisation.
     * La clé représente le nom de la civilisation et la valeur contient les points (X, Y) 
     * associés pour tracer des graphiques cartésiens.
     */
    private final Map<String, XYSeries> seriesParCiv = new HashMap<>();

    /**
     * Stocke les jeux de données pour graphiques circulaires (Pie Charts) par civilisation.
     * Permet de représenter la répartition proportionnelle de catégories spécifiques 
     * pour une civilisation donnée.
     */
    private final Map<String, DefaultPieDataset> datasetPieParCiv = new HashMap<>();

    /**
     * Stocke les jeux de données de catégories par civilisation, principalement 
     * utilisés pour modéliser les relations ou les comparaisons entre différentes entités.
     */
    private final Map<String, DefaultCategoryDataset> datasetRelationsParCiv = new HashMap<>();

    /**
     * Constructeur de la classe ChartManager
     */
    public ChartManager() {
    	super();
    }

    /**
     * Méthode qui crée un graphique sous forme de barres affichant
     * le niveau des relations diplomatiques avec les autres civilisations.
     *
     * @param dim : Dimension du graphe
     * @return Un graphique sous forme de barres
     */
    public ChartPanel createBarChart(String civName, Dimension dim) {
        DefaultCategoryDataset dataset = datasetRelationsParCiv.get(civName);

        if (dataset == null) {
            dataset = new DefaultCategoryDataset();
            datasetRelationsParCiv.put(civName, dataset);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Relations - " + civName,
                "Civilisation",
                "Niveau",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

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
    public ChartPanel createLineChart(String civName, Dimension dim) {
        XYSeries series = seriesParCiv.get(civName);

        if (series == null) {
            series = new XYSeries(civName);
            seriesParCiv.put(civName, series);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Richesse - " + civName,
                "Années",
                "Richesse",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

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
    public ChartPanel createPieChart(String civName, Dimension dim) {
        DefaultPieDataset dataset = datasetPieParCiv.get(civName);

        if (dataset == null) {
            dataset = new DefaultPieDataset();
            datasetPieParCiv.put(civName, dataset);
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Ressources - " + civName,
                dataset,
                true,
                true,
                false
        );

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
    public void ajouterPointLineChart(String civName, int annee, float stycas) {
        seriesParCiv.putIfAbsent(civName, new XYSeries(civName));
        seriesParCiv.get(civName).add(annee, stycas);
    }

    /**
     * Met à jour le camembert des ressources
     *
     * @param economie : Economie de la civilisation
     */
    public void updateRessourcesPieChart(String civName, Economie economie) {
        DefaultPieDataset dataset = datasetPieParCiv.computeIfAbsent(
                civName,
                k -> new DefaultPieDataset()
        );

        dataset.clear();

        for (Ressource r : economie.getRessourcesImportees().values()) {
            dataset.setValue(r.getNom(), r.getQuantite());
        }
    }
    
    /**
     * Met à jour le BarChart des relations avec les données actuelles.
     * Affiche le niveau numérique et le statut (Hostile/Tendue/Neutre/Alliance).
     *
     * @param relations : Liste des relations de la civilisation
     */
    public void updateRelationsBarChart(String civName, ArrayList<Relation> relations) {
        DefaultCategoryDataset dataset = datasetRelationsParCiv.computeIfAbsent(
                civName,
                k -> new DefaultCategoryDataset()
        );

        dataset.clear();

        for (Relation r : relations) {
            String statut = RelationManager.getDescriptionRelation(r);

            dataset.addValue(
                    r.getNiveau(),
                    statut,
                    r.getNomCivilisation()
            );
        }
    }

}