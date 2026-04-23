package gui.panels.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import gui.fenetres.StatsGUI;
import moteur.donnees.Civilisation;
import utilitaire.MusicManager;

/**
 * Panneau d'informations générales situé en bas de l'interface.
 *
 * <p>
 * Ce panneau regroupe les éléments de contrôle et d'affichage global :
 * <ul>
 *     <li>Bouton d'accès aux statistiques</li>
 *     <li>Affichage de l'année actuelle de simulation</li>
 *     <li>Sélecteur de civilisation (POV)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Il permet également à l'utilisateur de changer dynamiquement
 * la civilisation observée sans interrompre la simulation.
 * </p>
 * 
 * @author Alexandre
 * @author Tauseef
 * 
 * @version 2.0
 */
public class InfosPanel extends JPanel {

	/**
	 * Identification de sérialisation 
	 */
	private static final long serialVersionUID = 8131526906863775177L;

	/** 
	 * Référence vers le panneau principal pour accéder à la simulation et aux données. 
	 */
	private final PanelMainGUI panelMainGUI;

	/** 
	 * Bouton ouvrant la fenêtre de statistiques détaillées. 
	 */
	private final JButton boutonStats = new JButton("Stats");

	/** 
	 * Label affichant le texte statique "Année : ". 
	 */
	private final JLabel labelTexteAnnee = new JLabel("Année : ");

	/** 
	 * Label affichant la valeur actuelle de l'année de simulation. 
	 */
	private final JLabel labelValeurAnnee;

	/** 
	 * Sélecteur de civilisation permettant de changer le POV. 
	 */
	private final JComboBox<String> comboPov;
	
	/**
	 * Police des stats 
	 */
    private final Font FONT_STATS_PANEL = new Font(Font.SANS_SERIF, Font.ITALIC, 20);

	/**
     * Constructeur de InfosPanel.
     *
     * <p>
     * Configure la couleur de fond, le layout, et initialise les composants :
     * le bouton "Stats", le texte et la valeur de l'année, ainsi que le panneau de statistiques.
     * </p>
     * 
     * @param panelMainGUI Référence vers le panneau principal pour accéder à la simulation et aux données. 
     */
    public InfosPanel(PanelMainGUI panelMainGUI) {
        this.panelMainGUI = panelMainGUI;
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        labelValeurAnnee = new JLabel(
                String.valueOf(panelMainGUI.getSimulation().getAnneeActuelle()));

        buildBoutonStats();
        buildTexteAnnee();
        buildValeurAnnee();

        this.add(panelMainGUI.getPanelStats());

        comboPov = buildSelectionneurCivilisation();
        buildSelectorPov(comboPov);
    }
    
    public JLabel getLabelValeurAnnee() {
		return labelValeurAnnee;
	}

	/**
     * Initialise et configure le bouton "Stats"
     */
    private void buildBoutonStats() {
        boutonStats.setFocusable(false);
        boutonStats.addActionListener(new GestionnaireActions());
        this.add(boutonStats);
    }

    /**
     * Initialise et configure le label statique de l'année
     */
    private void buildTexteAnnee() {
        labelTexteAnnee.setFont(FONT_STATS_PANEL);
        this.add(labelTexteAnnee);
    }

    /**
     * Initialise et configure le label dynamique de l'année
     */
    private void buildValeurAnnee() {
        labelValeurAnnee.setFont(FONT_STATS_PANEL);
        this.add(labelValeurAnnee);
    }

    /**
     * Construit le sélecteur de civilisation (POV).
     *
     * <p>
     * Ce composant permet à l'utilisateur de changer dynamiquement
     * la civilisation observée sans interrompre la simulation.
     * </p>
     *
     * @return JComboBox configuré avec les civilisations
     */
    private JComboBox<String> buildSelectionneurCivilisation() {
    	List<Civilisation> toutesLesCivs = panelMainGUI.getToutesLesCivs();
    	
    	int size = toutesLesCivs.size();
        String[] noms = new String[size];
        
        for (int i=0; i<size; i++) {
        	noms[i] = toutesLesCivs.get(i).getNomAffiche();
        }

        JComboBox<String> combo = new JComboBox<>(noms);
        combo.setSelectedIndex(0); // Vikings par défaut
        panelMainGUI.setCivilisationActuelle(toutesLesCivs.get(0));
        
        combo.setFont(new Font("Serif", Font.BOLD, 13));
        combo.setPreferredSize(new Dimension(160, 30));
        combo.setFocusable(false);
        
        combo.setRenderer(new RenduBarre(combo));
        combo.addActionListener(new GestionnaireActions());
        
        return combo;
    }

    /**
     * Ajoute au panneau le sélecteur de civilisation (POV)
     * composé d’un séparateur, d’un label et du {@link JComboBox}.
     *
     * @param combo le sélecteur de civilisations
     */
    private void buildSelectorPov(JComboBox<String> combo) {
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(2, 40));
        this.add(sep);

        JLabel labelPov = new JLabel("Civilisation :");
        labelPov.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));
        labelPov.setForeground(new Color(0x2F2016));
        this.add(labelPov);

        this.add(combo);
    }

    /**
     * Dessine le panneau et son arrière-plan.
     *
     * @param g1 Objet {@link Graphics} fourni par Swing pour le rendu graphique
     */
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.drawImage(panelMainGUI.getImages().getImage("infos.png"),
                0, 0, this.getWidth(), this.getHeight(), null);
    }
    
    /**
     * Gestionnaire d'événements pour l'interface utilisateur principale.
     * 
     * <p>
     * Cette classe interne implémente {@link ActionListener} pour intercepter 
     * les interactions avec les composants Swing du panel, notamment le bouton 
     * de statistiques et la liste déroulante des civilisations.
     * </p>
     * 
     * @author Tauseef
     * @version 2.0
     */
    private class GestionnaireActions implements ActionListener {

        /**
         * Traite les actions déclenchées par l'utilisateur.
         * 
         * <ul>
         * 		<li>
         * 		<b>Bouton Stats :</b> Lance une nouvelle fenêtre de statistiques 
         * 		({@link StatsGUI}) dans un thread séparé pour ne pas bloquer l'EDT.
         * 		</li>
         * 		<li>
         * 		<b>Combo POV :</b> Met à jour la civilisation actuellement visualisée 
         * 		et rafraîchit l'affichage du panel de statistiques.
         * 		</li>
         * </ul>
         * 
         * @param evenement L'événement d'action généré par un composant Swing.
         */
        @Override
        public void actionPerformed(ActionEvent evenement) {
            Object source = evenement.getSource();

            // Affiche les stats dans une nouvelle fenêtre
            if (source == boutonStats) {
                Thread filStats = new Thread(new StatsGUI(panelMainGUI.getChartManager(), panelMainGUI.getInstance()));
                MusicManager.jouerAudio("src/audio/hover.wav", false, false);
                filStats.start();
            } 

            // Affiche la civilisation voulue
            else if (source == comboPov) {
            	List<Civilisation> toutesLesCivs = panelMainGUI.getToutesLesCivs();
            	
                int indexChoisi = comboPov.getSelectedIndex();
                if (indexChoisi >= 0 && indexChoisi < toutesLesCivs.size()) {
                    Civilisation civ = toutesLesCivs.get(indexChoisi);
                    panelMainGUI.setCivilisationActuelle(civ);
                    panelMainGUI.getPanelStats().mettreAJourPov(civ);
                }
            }
        }
    }
    
    /**
     * Rendu personnalisé pour les éléments de la JComboBox des civilisations.
     * 
     * <p>
     * Cette classe permet de colorer l'arrière-plan de chaque cellule en fonction 
     * de la couleur spécifique de la civilisation correspondante.
     * </p>
     * 
     * @author Tauseef
     * @version 1.0
     */
    private class RenduBarre extends DefaultListCellRenderer {
        
        /**
		 * Identification de sérialisation
		 */
		private static final long serialVersionUID = -2306104941478010089L;
		
		/** 
         * Référence à la combo box pour déterminer l'élément sélectionné. 
         */
        private JComboBox<String> combo;
        
        /**
         * Construit un nouveau moteur de rendu.
         * @param combo La {@link JComboBox} associée, utilisée pour récupérer l'index sélectionné.
         */
        private RenduBarre(JComboBox<String> combo) {
            super();
            this.combo = combo;
        }

        /**
         * Retourne le composant configuré pour afficher la cellule de la liste.
         * 
         * <p>
         * La couleur d'arrière-plan est définie par la couleur de la civilisation. 
         * Si l'élément est sélectionné, la couleur est assombrie pour créer un contraste.
         * </p>
         *
         * @param list         La JList que nous peignons.
         * @param value        La valeur retournée par list.getModel().getElementAt(index).
         * @param index        L'index de la cellule. L'index -1 correspond au bouton de la combo (item sélectionné).
         * @param isSelected   Vrai si la cellule est sélectionnée.
         * @param cellHasFocus Vrai si la cellule a le focus.
         * @return Le composant (this) configuré pour dessiner la cellule.
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            int idx = (index == -1) ? combo.getSelectedIndex() : index;
            
            List<Civilisation> toutesLesCivs = panelMainGUI.getToutesLesCivs();
            if (idx >= 0 && idx < toutesLesCivs.size()) {
                Color couleur = toutesLesCivs.get(idx).getCouleur();
                
                // Applique la couleur (plus sombre si sélectionné)
                setBackground(isSelected ? couleur.darker() : couleur);
                setForeground(Color.WHITE);
                setFont(new Font("Serif", Font.BOLD, 13));
            }
            
            return this;
        }
    }   
}