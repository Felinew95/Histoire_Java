package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import moteur.traitement.TraitementImage;
import ressources.configuration.SimConfig;

public class DessinStrategy {
	
	public void paint(TraitementImage image, SimConfig config,Graphics g, java.awt.Component c) {
		Graphics2D dessinLogique = (Graphics2D) g;
		dessinLogique.drawImage(image.getImage(),0, 0,c.getWidth(),c.getHeight(),null);
		TraitementImage image2 = new TraitementImage("C:/Users/Tauseef PC Gamer/Desktop/Graphique/GUIWork/src/gui/vikingsSprites.png");
		dessinLogique.drawImage(image2.getImage(),0, 0,c.getWidth(),c.getHeight(),null);

		dessinLogique.setFont(new Font("Arial", Font.BOLD, 50));
		dessinLogique.setColor(Color.BLACK);

		dessinLogique.drawString( "Histoire", c.getWidth() / 2 - 100,c.getHeight() / 2 - 150) ;
 
 
  

}




}
