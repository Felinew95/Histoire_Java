package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import config.SpritesConfig;
import gui.elements.Bouton;
import gui.fenetres.MainGUI;
import gui.management.UIFactory;

/**
 * Classe qui représente le menu de démarrage 
 * 
 * @author Tauseef et Alexandre
 * @version 1.0
 */
public class PanelMenuGUI extends JPanel implements MouseListener, MouseMotionListener {
	
	// Attributs 
	private static final long serialVersionUID = -1248105144588644477L;
	private Bouton jouer;
	private Bouton credits;
	private Bouton quitter;
	
	SpritesConfig images = new SpritesConfig("src/images");
	
	private int tailleTexte=0;
	private int y=0;
	private int taille_defaut_x=300;
	private int taille_defaut_y=100;
	private int marge=150;
	
	private MainGUI mainGUI;
	
	/**
	 * Constructeur de la classe PanelMenuGUI
	 * 
	 * @param mainGUI : Fenêtre principale
	 */
	public PanelMenuGUI(MainGUI mainGUI) {
		super();
		this.setPreferredSize(new Dimension(1000,1000));
		
		this.mainGUI = mainGUI;
		this.initBoutons();
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		
		Graphics2D g = (Graphics2D)g1;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		peindreMainMenu(g);
	}
	
	/**
	 * Méthode qui initialise les boutons
	 */
	private void initBoutons() {
		jouer = UIFactory.buildBouton((this.getWidth() - taille_defaut_x) / 2, (this.getHeight() - taille_defaut_y) / 2, taille_defaut_x, taille_defaut_y, 100, 100, "Lancer", Color.WHITE);
		credits =  UIFactory.buildBouton((this.getWidth()-taille_defaut_x)/2,(this.getHeight()-taille_defaut_y)/2+ marge*1,taille_defaut_x, taille_defaut_y, 100, 100, "Crédits", Color.WHITE);
		quitter =  UIFactory.buildBouton((this.getWidth()-taille_defaut_x)/2,(this.getHeight()-taille_defaut_y)/2+ marge*2,taille_defaut_x, taille_defaut_y, 100, 100, "Quitter", Color.WHITE);		
	}

	private void peindreMainMenu(Graphics g) {	
		g.drawImage(images.getImage("viking_bg.jpg"),0,0,this.getWidth(),this.getHeight(),null);
		
		if(y<(this.getHeight()/2)) {
			y+=5;
		} else if(tailleTexte<200) {
			tailleTexte+=5;
		} else {
			y=(this.getHeight()/2);
		}
		
		g.drawImage(images.getImage("viking_bg.jpg"),0,0,this.getWidth(),this.getHeight(),null);	
		if (y == (this.getHeight()/2)) {
			construireBoutons(g);
		}
		dessinerTexte(g, this.getWidth(), y, "Histoire", tailleTexte);
	}

	/**
	 * Méthode qui construit les boutons 
	 * 
	 * @param g : l'environnement graphique pour dessiner les boutons
	 */
	public void construireBoutons(Graphics g) {
		((RoundRectangle2D) jouer.getForme()).setFrame(
			    (this.getWidth()-taille_defaut_x)/2,
			    (this.getHeight()-taille_defaut_y)/2 + marge*0,
			    taille_defaut_x,
			    taille_defaut_y
			);

		((RoundRectangle2D)credits.getForme()).setFrame(
			    (this.getWidth()-taille_defaut_x)/2,
			    (this.getHeight()-taille_defaut_y)/2 + marge*1,
			    taille_defaut_x,
			    taille_defaut_y
			);

		((RoundRectangle2D)quitter.getForme()).setFrame(
			    (this.getWidth()-taille_defaut_x)/2,
			    (this.getHeight()-taille_defaut_y)/2 + marge*2,
			    taille_defaut_x,
			    taille_defaut_y
		);
		
		jouer.construire(g);
		credits.construire(g);
		quitter.construire(g);
	}
	
	/**
	 * Méthode qui dessine le texte 
	 * 
	 * @param g : l'environnement graphique pour dessiner les boutons
	 * @param x : Position en x 
	 * @param y : Position en y
	 * @param texte : Contenu 
	 * @param taille : Taille du texte 
	 */
	private void dessinerTexte(Graphics g, int x, int y, String texte, int taille) {
		g.setFont(new Font(Font.DIALOG,Font.BOLD,taille));
		FontMetrics fm = g.getFontMetrics();
   
    	int tx = (x - fm.stringWidth(texte))/2 ;
    	int ty = (y + fm.getAscent())/2;
    	g.setColor(Color.black);
    	
		g.drawString(texte ,tx, ty);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (this.jouer.contient(mx, my)) {
			this.mainGUI.lancerJeu();
		}
		
		if (this.quitter.contient(mx, my)) {
			this.mainGUI.quitterJeu();
		}
		
		if (this.credits.contient(mx, my)) {
			this.mainGUI.afficherCredits();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
	    int my = e.getY();

	    jouer.setSurvoleZone(jouer.contient(mx, my));
	    credits.setSurvoleZone(credits.contient(mx, my));
	    quitter.setSurvoleZone(quitter.contient(mx, my));

	    repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
