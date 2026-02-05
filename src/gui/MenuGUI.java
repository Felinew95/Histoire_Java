package gui;

import config.SimConfig;
import javax.swing.JFrame;

public class MenuGUI extends JFrame {
	SimConfig config = new SimConfig();
	
	public MenuGUI() {
		initFenetre();
		
	}
	
	private void initFenetre() {
		int x = config.TAILLE_X;
		int y = config.TAILLE_Y;
		
		
		this.setSize(x,y);
		this.setVisible(true);
		this.setResizable(false);
		
		
	}
	
	public SimConfig getConfig() {
		return this.config;
	}

    public void initMenu(){
        
    }





}