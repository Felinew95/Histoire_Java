package gui.panels.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import config.SimConfig;

/**
 * Panneau interne affichant la narration de la simulation.
 *
 * <p>
 * Cette classe étend {@link JPanel} et sert à afficher le texte narratif de l'évolution
 * de la simulation dans un {@link JTextPane} justifié. Le texte est décoratif et
 * non éditable par l'utilisateur. L'arrière-plan est une image ("zone_par.png").
 * </p>
 *
 * @author Tauseef
 * @version 2.0
 */
public class NarrationPanel extends JPanel {
	
	/**
	 * Identifiant de sérialisation
	 */
	private static final long serialVersionUID = 4507733367365335456L;

	/**
     * Zone de texte affichant la narration de la simulation
     */
    private final JTextPane texteNarration = new JTextPane();

    /**
     * Largeur fixe du panneau de narration
     */
    private static final int NARRATION_WIDTH = 200;

    /**
     * Espacement du layout principal (horizontal et vertical)
     */
    private static final int GAP = 10;

    /**
     * Taille de la police de la narration
     */
    private static final int FONT_NARRATION_SIZE = 13;

    /**
     * Couleur du texte de la narration
     */
    private final Color COLOR_NARRATION = new Color(80, 40, 20);
    
    /**
     * Référence du panel principal 
     */
    private PanelMainGUI panelMainGUI;

    /**
     * Constructeur du panneau de narration.
     *
     * <p>
     * Initialise les dimensions, le layout, l'image de bordure et le texte narratif.
     * </p>
     * 
     * @param panelMainGUI Référence vers le panneau principal pour accéder à la simulation et aux données. 
     */
    public NarrationPanel(PanelMainGUI panelMainGUI) {
        Dimension dimPanelNarration = new Dimension(NARRATION_WIDTH, SimConfig.TAILLE_FENETRE_Y);
        this.setMaximumSize(dimPanelNarration);
        this.setMinimumSize(dimPanelNarration);
        this.setPreferredSize(dimPanelNarration);
        
        this.panelMainGUI = panelMainGUI;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 100, GAP));

        this.buildTextePanel();

        texteNarration.setOpaque(false);
        this.add(texteNarration);
    }

    /**
     * Retourne la zone de texte affichant la narration de la simulation.
     *
     * @return le JTextPane de narration
     */
    public JTextPane getTexteNarration() {
        return texteNarration;
    }
    
    /**
     * Initialise et configure le {@link JTextPane} pour la narration.
     *
     * <p>
     * La zone de texte est non éditable, justifiée, avec des marges et une couleur personnalisée.
     * </p>
     */
    private void buildTextePanel() {
        Dimension dimTexteNarration = new Dimension(NARRATION_WIDTH, SimConfig.TAILLE_FENETRE_Y);
        this.texteNarration.setPreferredSize(dimTexteNarration);
        this.texteNarration.setMaximumSize(dimTexteNarration);
        this.texteNarration.setMinimumSize(dimTexteNarration);

        this.texteNarration.setEditable(false);
        this.texteNarration.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.texteNarration.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        this.texteNarration.setFont(new Font("Serif", Font.ITALIC, FONT_NARRATION_SIZE));
        this.texteNarration.setForeground(COLOR_NARRATION);

        StyledDocument doc = texteNarration.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_JUSTIFIED);
        StyleConstants.setLineSpacing(style, 0.2f);
        StyleConstants.setLeftIndent(style, 10);
        StyleConstants.setRightIndent(style, 10);
        doc.setParagraphAttributes(0, doc.getLength(), style, false);

        this.texteNarration.setText("");
    }

    /**
     * Dessine le panneau et son arrière-plan avec l'image "zone_par.png".
     *
     * @param g1 Objet {@link Graphics} fourni par Swing pour le rendu graphique
     */
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.drawImage(panelMainGUI.getImages().getImage("zone_par.png"), 0, 0, this.getWidth(), this.getHeight(), null);
    }

}
