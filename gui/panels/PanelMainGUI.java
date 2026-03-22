package gui.panels;

import config.SimConfig;

import gui.fenetres.MainGUI;
import gui.fenetres.StatsGUI;
import gui.management.PaintStrategy;

import moteur.carte.Continent;
import moteur.traitement.builders.CarteBuilder;
import moteur.traitement.builders.EvenementHistoriqueBuilder;
import moteur.traitement.builders.MobileBuilder;
import moteur.traitement.management.SimFactory;
import moteur.traitement.management.Simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Classe de panneau d'affichage principal de la simulation 
 * 
 * @author Alexandre, Tauseef et Massinissa
 * @version 1.0
 */
public class PanelMainGUI extends JPanel {

	// Attributs
	private static final long serialVersionUID = -2755589757560579734L;
    
	private Simulation simulation;
	private PaintStrategy paintStrategy;
	
    private PanelSim panelSim;
    private JPanel panelInfos;
    private JPanel panelStats;
    private JPanel panelNarationRelation;

    private JLabel labelValeurAnnee;
    private JLabel labelValeurNombreGuerriers;
    private JLabel labelValeurNombreNavires;
    private JLabel labelValeurTypeRegime;
    private JLabel labelValeurNombreHabitants;
    private JLabel labelValeurNomRegion;
    
    private JProgressBar barRichesse;
    private JProgressBar barEtatArmee;
    private JProgressBar barInfluenceReligion;
    
    private JTextPane texteNarration; 
    private JPanel panelRelations;
    
    private final Font fontStats = new Font(Font.SANS_SERIF, Font.ITALIC, 13);
    
    public PanelMainGUI(MainGUI mainGUI) {
        this.setLayout(new BorderLayout());
        
        this.initVariables();  
        this.initPanelSimulation();
        this.initPanelNarrationRelations();
        
        this.initPanel();
    }

	private void initPanel() {
		this.add(panelSim, BorderLayout.CENTER);
        this.add(panelInfos, BorderLayout.SOUTH);
        this.add(panelNarationRelation, BorderLayout.EAST);
	}
    
	private void initPanelNarrationRelations() {
		this.panelNarationRelation.setLayout(new BoxLayout(panelNarationRelation, BoxLayout.Y_AXIS));
		this.panelRelations.setBorder(BorderFactory.createTitledBorder(null, "Relations", 0, 0, fontStats, Color.BLACK));
		
        initPanelNarration();
        initPanelRelations();
        
        this.panelNarationRelation.add(texteNarration);
        this.panelNarationRelation.add(panelRelations);
	}

	private void initPanelNarration() {
		Dimension dimPanelNarration = new Dimension(200, SimConfig.TAILLE_FENETRE_Y);
        this.panelNarationRelation.setMaximumSize(dimPanelNarration);
        this.panelNarationRelation.setMinimumSize(dimPanelNarration);
        this.panelNarationRelation.setPreferredSize(dimPanelNarration);
        
        Dimension dimTexteNarration = new Dimension(180, 300);
        this.texteNarration = new JTextPane();
        
        this.texteNarration.setPreferredSize(dimTexteNarration);
        this.texteNarration.setMaximumSize(dimTexteNarration);
        this.texteNarration.setMinimumSize(dimTexteNarration);
        
        this.texteNarration.setEditable(true);
        this.texteNarration.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.texteNarration.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
   
        this.texteNarration.setBackground(Color.GRAY);
        this.texteNarration.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 13));
        this.texteNarration.setEditable(false);
        this.texteNarration.setSelectedTextColor(Color.WHITE);
        
        StyledDocument doc = texteNarration.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        this.texteNarration.setText("");
	}
	
	private void initPanelRelations() {
		Dimension dimRelations = new Dimension(180, 300);
        this.panelRelations.setPreferredSize(dimRelations);
        this.panelRelations.setMaximumSize(dimRelations);
        this.panelRelations.setMinimumSize(dimRelations);
        
        this.panelRelations.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.panelRelations.setBackground(Color.GRAY);
        
        this.panelNarationRelation.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.panelNarationRelation.setBackground(Color.GRAY);
        this.panelNarationRelation.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
	}

	private void initPanelSimulation() {
		initPanelInfos();
        initPanelStats();
    
        this.panelInfos.add(panelStats);
	}

	private void initPanelStats() {
		this.panelStats.setLayout(new GridLayout(4, 4, 4, 1));
        this.panelStats.setBackground(Color.LIGHT_GRAY);
        
        JLabel labelRichesse = new JLabel("Richesse : "); 
        labelRichesse.setFont(this.fontStats);
        this.panelStats.add(labelRichesse);
              
        this.barRichesse = new JProgressBar(0, 100000);
        barRichesse.setStringPainted(true);
        barRichesse.setString("0");
        barRichesse.setValue(0);
        barRichesse.setFont(this.fontStats);
        this.panelStats.add(barRichesse);
        
        JLabel labelEtatArmee = new JLabel("État de l'armée : ");
        labelEtatArmee.setFont(this.fontStats);
        this.panelStats.add(labelEtatArmee);
        
        this.barEtatArmee = new JProgressBar(0, 100);
        this.barEtatArmee.setStringPainted(true);
        this.barEtatArmee.setString("0%");
        this.barEtatArmee.setValue(0);
        this.barEtatArmee.setFont(fontStats);
        this.panelStats.add(barEtatArmee);
        
        JLabel labelNombreGuerriers = new JLabel("Nombre de guerriers : ");
        labelNombreGuerriers.setFont(this.fontStats);
        this.panelStats.add(labelNombreGuerriers);
        
        this.labelValeurNombreGuerriers = new JLabel("0");
        labelValeurNombreGuerriers.setFont(this.fontStats);
        this.panelStats.add(labelValeurNombreGuerriers);
        
        JLabel labelNombreNavires = new JLabel("Nombre de navires : ");
        labelNombreNavires.setFont(this.fontStats);
        this.panelStats.add(labelNombreNavires);
        
        this.labelValeurNombreNavires = new JLabel("0");
        labelValeurNombreNavires.setFont(this.fontStats);
        this.panelStats.add(labelValeurNombreNavires);
        
        JLabel labelTypeRegime = new JLabel("Type de régime :");
        labelTypeRegime.setFont(this.fontStats);
        this.panelStats.add(labelTypeRegime);
        
        this.labelValeurTypeRegime = new JLabel("Indéfini");
        labelValeurTypeRegime.setFont(this.fontStats);
        this.panelStats.add(labelValeurTypeRegime);
        
        JLabel labelNombreHabitants = new JLabel("Nombre d'habitants : ");
        labelNombreHabitants.setFont(this.fontStats);
        this.panelStats.add(labelNombreHabitants);
        
        this.labelValeurNombreHabitants = new JLabel("0");
        labelValeurNombreHabitants.setFont(fontStats);
        this.panelStats.add(labelValeurNombreHabitants);
        
        JLabel labelNomReligion = new JLabel("Religion : ");
        labelNomReligion.setFont(this.fontStats);
        this.panelStats.add(labelNomReligion);
        
        this.labelValeurNomRegion = new JLabel("Indéfini");
        labelValeurNomRegion.setFont(this.fontStats);
        this.panelStats.add(labelValeurNomRegion);
        
        JLabel labelInfluenceReligion = new JLabel("Influence de la religion : ");
        labelInfluenceReligion.setFont(this.fontStats);
        this.panelStats.add(labelInfluenceReligion);
        
        this.barInfluenceReligion = new JProgressBar(0, 100);
        this.barInfluenceReligion.setStringPainted(true);
        this.barInfluenceReligion.setValue(0);
        this.barInfluenceReligion.setString("0%");
        this.barInfluenceReligion.setFont(fontStats);
        this.panelStats.add(barInfluenceReligion);
	}

	private void initPanelInfos() {
		this.panelInfos.setBackground(Color.LIGHT_GRAY);
        this.panelInfos.setBorder(BorderFactory.createTitledBorder(null, "Statistiques", 0, 0, new Font(Font.SERIF, Font.BOLD, 12), Color.BLACK));
        this.panelInfos.setLayout(new FlowLayout(FlowLayout.CENTER, 90, 5));
        
        JButton boutonStats = new JButton("Stats");
        boutonStats.setFocusable(false);
        boutonStats.addActionListener(new AfficherStats());
        this.panelInfos.add(boutonStats);
        
        JLabel labelTexteAnnee = new JLabel("Année : ");
        labelTexteAnnee.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        this.panelInfos.add(labelTexteAnnee);
        
        this.labelValeurAnnee = new JLabel(String.valueOf(this.simulation.getAnneeActuelle()));
        labelValeurAnnee.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        this.panelInfos.add(labelValeurAnnee);
	}
	
	private void initVariables() {
    	this.simulation = new Simulation(780, SimFactory.buildMap(), SimFactory.buildCivilisation("", null, null, null, null, null, ""), 
    			MobileBuilder.buildMobileManagerEurope(), EvenementHistoriqueBuilder.buildEvenementManagerViking());
    	this.paintStrategy = new PaintStrategy();
    	
    	Continent Europe = CarteBuilder.buildEurope();
		this.simulation.getCarte().setContinent(Europe);
		this.panelSim = new PanelSim(this.simulation.getCarte(), paintStrategy, this.simulation);

        this.panelSim.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.paintStrategy.setTailleBloc(SimConfig.TAILLE_BLOC_EUROPE);
        
        this.panelInfos = new JPanel();
        this.panelStats = new JPanel();
        this.panelNarationRelation = new JPanel();
        this.panelRelations = new JPanel();   
    }
	
	public Simulation getSimulation() {
		return simulation;
	}
	
	public JLabel getLabelValeurAnnee() {
		return labelValeurAnnee;
	}
	
	public JTextPane getTexteNarration() {
		return texteNarration;
	}
	
	public PaintStrategy getPaintStrategy() {
		return paintStrategy;
	}

	public PanelSim getPanelSim() {
		return panelSim;
	}
	
	/**
	 * Classe interne qui permet d'afficher les statistiques 
	 * 
	 * @author Massinissa
	 * @version 1.0
	 */
	private class AfficherStats implements ActionListener {

		/**
		 * Affiche une nouvelle fenêtre avec les statistiques 
		 * @param e : Action déclenché par le joueur 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Thread stats = new Thread(new StatsGUI());
			stats.start();
		}
		
	}
	
	
}