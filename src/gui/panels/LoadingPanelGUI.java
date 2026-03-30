package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import gui.elements.ProgressBar;
import utilitaire.DessinUtilitaire;

public class LoadingPanelGUI extends JPanel {

	// Attributs 
	private static final long serialVersionUID = 3276608937765319605L;
	
	private double pourcentage = 0;
	private int taille_defaut_x=300;
	private int taille_defaut_y=100;
	private long dernierTemps = System.currentTimeMillis();
	
	private ProgressBar bar;

	public LoadingPanelGUI() {
		super();
		this.setPreferredSize(new Dimension(1000,1000));
		this.setBackground(Color.BLACK);
		
		this.bar = new ProgressBar((int) (9f / 2),(this.getHeight()-taille_defaut_y)/2,taille_defaut_x,taille_defaut_y,100,100, Double.toString(pourcentage), Color.RED);
	 }
	
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
	
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		taille_defaut_x=this.getWidth(); 
		taille_defaut_y=100;
		
		afficherBar(g); 
		
			
		this.bar.construire(g);
		
		DessinUtilitaire.definirCouleur(g,Color.WHITE);
		DessinUtilitaire.afficherTexte(g,new Font(Font.DIALOG,Font.BOLD,20),bar.getTexte()+"%",new Point((int) ((RoundRectangle2D)bar.getForme()).getCenterX(),(int)((RoundRectangle2D)bar.getForme()).getCenterY()+100));
		
		long temps = System.currentTimeMillis() / 500;
		String texte = "Chargement";

		if (incrementer(10)) {
		    long cycle = temps % 4;
		     
		    if (cycle == 3) {
		        texte = "Chargement...";
		    } else if (cycle == 2) {
		        texte = "Chargement..";
		    } else if (cycle == 1) {
		        texte = "Chargement.";
		    }
		}
		
		DessinUtilitaire.afficherTexte(g,new Font(Font.DIALOG,Font.ITALIC,75), texte,new Point((int) ((int)bar.getForme().getBounds2D().getCenterX()),(int) ((int)bar.getForme().getBounds2D().getCenterY()-140 ) ));
		bar.setTexte(Double.toString(pourcentage));	
	}
	
	private void afficherBar(Graphics2D g) {
		float x = (this.getWidth() / 7f - 15);
	    float y = (this.getHeight() - taille_defaut_y) / 2f;
	    float largeurBarre = this.getWidth() * 3f / 4f;  

	    ((RoundRectangle2D) bar.getForme()).setFrame(x, y, largeurBarre, taille_defaut_y);
	    DessinUtilitaire.definirCouleur(g, bar.getCouleur());

	    int remplissageBar = (int) (pourcentage * largeurBarre / 100.0);   
	    DessinUtilitaire.remplirRoundRectangle(g,new Point((int) (x), (int) y),remplissageBar,taille_defaut_y,  100,100);
	}
	
	public boolean incrementer(int temps) {
	    long tempsActuel = System.currentTimeMillis();
	    
	    if (tempsActuel-dernierTemps>=1000) {
	    	if(pourcentage<100) {
	    		if (pourcentage+(100/temps) > 100) {
		    		pourcentage += 100;
		    	} else {
		    		pourcentage += 100/temps;
	    		}
	    		
	    		dernierTemps=tempsActuel;
	    	} else {
		    	return false;
		    }
	    } 
	    
	    return true;
	}
		
	public double getPourcentage() {
		return this.pourcentage;
	}
}
