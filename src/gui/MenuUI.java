package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import moteur.traitement.TraitementImage;
import ressources.configuration.SimConfig;

public class MenuUI extends JPanel{
	SimConfig configFenetre = new SimConfig();
	
	DessinStrategy dessin = new DessinStrategy();
	
	TraitementImage image = new TraitementImage("C:/Users/Tauseef PC Gamer/Desktop/Graphique/GUIWork/src/gui/viking_bg.jpg");
	public BufferedImage fond;
	
	public MenuUI() {
		super();

	}
	


	
	

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.dessin.paint(image,configFenetre, g,this);
		
	
	}





}
