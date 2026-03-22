package gui.fenetres;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import gui.management.ChartManager;

/**
 * Classe d'affichage graphique des statistiques 
 * 
 * @author Massinissa 
 * @version 1.0
 */
public class StatsGUI extends JFrame implements Runnable {

	// Attributs 
	private static final long serialVersionUID = 6756112902171644539L;
	
	private JProgressBar barRichesse;
    private JProgressBar barEtatArmee;
    private JProgressBar barEconomie;
    
    private JLabel labelValeurNombreGuerriers;
    private JLabel labelValeurNombreNavires;
    private JLabel labelValeurNombreHabitants;
    private JLabel labelValeurTypeRegime;

    private ChartManager chartManager;
    
    public StatsGUI() {
        super("Histoire : Statistiques");
        setSizeFenetre();
        
        // Les 3 graphiques en haut
        JPanel panelGraphiques = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelGraphiques.setBackground(Color.DARK_GRAY);
        
        setChart(panelGraphiques);

        // Panel du bas
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setBackground(Color.GRAY);
        panelBas.setBorder(BorderFactory.createTitledBorder(null, "Statistiques", 0, 0, new Font(Font.SERIF, Font.BOLD, 12), Color.BLACK));

        // Bouton Quit à gauche
        JButton boutonQuit = new JButton("Quit");
        boutonQuit.setBackground(Color.GREEN);
        boutonQuit.setFocusable(false);
        boutonQuit.addActionListener(e -> this.dispose());
        
        JPanel panelGauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        panelGauche.setBackground(Color.GRAY);
        panelGauche.add(boutonQuit);

        // Année au centre
        JLabel labelAnnee = new JLabel("-   Année   +");
        labelAnnee.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        labelAnnee.setForeground(Color.WHITE);
        JPanel panelCentre = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panelCentre.setBackground(Color.GRAY);
        panelCentre.add(labelAnnee);

        // Stats à droite
        JPanel panelDroite = new JPanel(new GridLayout(7, 2, 5, 5));
        panelDroite.setBackground(Color.GRAY);
        panelDroite.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelRichesse = new JLabel("Richesse : ");
        this.barRichesse = new JProgressBar(0, 100000);
        barRichesse.setStringPainted(true);
        barRichesse.setString("0");
        barRichesse.setValue(0);

        JLabel labelArmee = new JLabel("État de l'armée : ");
        this.barEtatArmee = new JProgressBar(0, 100);
        barEtatArmee.setStringPainted(true);
        barEtatArmee.setString("0%");
        barEtatArmee.setValue(0);

        JLabel labelEconomie = new JLabel("Economie : ");
        this.barEconomie = new JProgressBar(0, 100);
        barEconomie.setStringPainted(true);
        barEconomie.setString("0%");
        barEconomie.setValue(0);

        JLabel labelNombreGuerriers = new JLabel("Nombre de guerriers : ");
        this.labelValeurNombreGuerriers = new JLabel("0");

        JLabel labelNombreNavires = new JLabel("Nombre de navires : ");
        this.labelValeurNombreNavires = new JLabel("0");

        JLabel labelNombreHabitants = new JLabel("Nombre d'habitants : ");
        this.labelValeurNombreHabitants = new JLabel("0");

        JLabel labelTypeRegime = new JLabel("Type de régime : ");
        this.labelValeurTypeRegime = new JLabel("Indéfini");

        panelDroite.add(labelRichesse);
        panelDroite.add(barRichesse);
        panelDroite.add(labelArmee);
        panelDroite.add(barEtatArmee);
        panelDroite.add(labelEconomie);
        panelDroite.add(barEconomie);
        panelDroite.add(labelNombreGuerriers);
        panelDroite.add(labelValeurNombreGuerriers);
        panelDroite.add(labelNombreNavires);
        panelDroite.add(labelValeurNombreNavires);
        panelDroite.add(labelNombreHabitants);
        panelDroite.add(labelValeurNombreHabitants);
        panelDroite.add(labelTypeRegime);
        panelDroite.add(labelValeurTypeRegime);

        panelBas.add(panelGauche, BorderLayout.WEST);
        panelBas.add(panelCentre, BorderLayout.CENTER);
        panelBas.add(panelDroite, BorderLayout.EAST);

        this.add(panelGraphiques, BorderLayout.CENTER);
        this.add(panelBas, BorderLayout.SOUTH);
    }

	private void setChart(JPanel panelGraphiques) {
		Dimension dim = new Dimension(350, 350);
        this.chartManager = new ChartManager();
        panelGraphiques.add(chartManager.createBarChart(dim));
        panelGraphiques.add(chartManager.createLineChart(dim));
        panelGraphiques.add(chartManager.createPieChart(dim));
	}

	private void setSizeFenetre() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1200, 700);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}

	@Override
	public void run() {
		
	}
	
}
