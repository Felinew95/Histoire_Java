
package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ressources.configuration.SimConfig;

public class MainGUI extends JFrame {
	SimConfig config = new SimConfig();
	MenuUI menu = new MenuUI();
	
	public MainGUI() {
		initFenetre();
		
	}
	
	private void initFenetre() {
		int x = config.fenetre.getTailleX();
		int y = config.fenetre.getTailleY();
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocation(config.fenetre.getX(),config.fenetre.getY());
		JButton bouton = new JButton("Lancer");
		bouton.setFocusable(false);

		menu.add(bouton);
		this.setContentPane(menu);
		
	
		
		

		
		
		this.setTitle("Menu UI");
		this.setSize(x,y);
		
		
		
		
		this.setVisible(true);
		
	
	
		
		
	}
	

	
	public SimConfig getConfig() {
		return this.config;
	}


	public static void main(String argv[]) {
		new MainGUI();
	}
    	
    	
    	
    	
    	
    	
 }
    

