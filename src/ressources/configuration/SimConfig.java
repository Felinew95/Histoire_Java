package ressources.configuration;

import java.awt.Dimension;

import ressources.utilitaires.Objet;

public class SimConfig {
   
    // Attributs 
    public Objet fenetre = new Objet(700,0,700,500,"Fenêtre");
    
    
	
	private boolean adjustable = true;
	
	public boolean estAdjustable(Dimension dimension) {
		if(dimension.getWidth()<800 && dimension.getHeight()<600 && dimension.getHeight()>=0 && dimension.getWidth()>=0) {
			return adjustable;
		}
		
		return !adjustable;
		
	}
	

	
	
	
}
