package gui;

import config.SimConfig;
import config.SpritesConfig;

import gui.mobiles.Mobile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import moteur.carte.Bloc;
import moteur.carte.Carte;
import moteur.traitement.MobileManager;

/**
 * Classe qui permet de peindre les différents composants graphiques
 * 
 * @author Alexandre, Tauseef et Massinissa
 * @version 1.0
 */
public class PaintStrategy {

    private final SpritesConfig sprites = new SpritesConfig("src/images");
    private int taille_bloc = SimConfig.TAILLE_BLOC;

    public void peindreCarte(Carte carte, Graphics graphics) {
        
        Bloc[][] blocs = carte.getBlocs();

        int temps = (int) System.currentTimeMillis() / 500;
        BufferedImage imageEau = (temps % 2 == 0) ? this.sprites.getImage("eau1.png"): this.sprites.getImage("eau2.png");
        
        BufferedImage imageTerre = this.sprites.getImage("terre.png");
        BufferedImage imageHerbe = this.sprites.getImage("herbe.png");
        BufferedImage imageArbre = this.sprites.getImage("tree.png"); 
        BufferedImage imagePlage = this.sprites.getImage("sable.png");
        BufferedImage imageMontagne = this.sprites.getImage("montagne.png");
           
        Graphics2D g = (Graphics2D) graphics;
        for (int lineIndex = 0; lineIndex < carte.getNbLignes(); lineIndex++) {
            for (int columnIndex = 0; columnIndex < carte.getNbColonnes(); columnIndex++) {
                Bloc bloc = blocs[lineIndex][columnIndex];
                peindreBloc(imageEau, imageTerre, imageHerbe, imageArbre, imagePlage, imageMontagne, g, bloc); 
            }
        }
    }

	private void peindreBloc(BufferedImage imageEau, BufferedImage imageTerre, BufferedImage imageHerbe,
			BufferedImage imageArbre, BufferedImage imagePlage, BufferedImage imageMontagne, Graphics2D g, Bloc bloc) {
		String typeBloc = bloc.getTypeBloc();
		
		switch (typeBloc) {
			case "EAU":
				g.drawImage(imageEau, bloc.getColonne() * taille_bloc, bloc.getLigne() * taille_bloc,
		                taille_bloc, taille_bloc, null);
				break;
		    case "ARBRE":
		    	g.drawImage(imageHerbe, bloc.getColonne()*taille_bloc, bloc.getLigne()*taille_bloc, taille_bloc,
		                taille_bloc, null);
		    	g.drawImage(imageArbre, bloc.getColonne()*taille_bloc, bloc.getLigne()*taille_bloc, taille_bloc,
		   			 taille_bloc, null);
		    	break;
		    case "HERBE":
		    	g.drawImage(imageHerbe, bloc.getColonne()*taille_bloc, bloc.getLigne()*taille_bloc, taille_bloc,
		                taille_bloc, null);
		    	break;
		    case "TERRE":
		    	g.drawImage(imageTerre, bloc.getColonne()*taille_bloc, bloc.getLigne()*taille_bloc, taille_bloc,
		                taille_bloc, null);
		    	break;
		    case "PLAGE":
		    	g.drawImage(imagePlage, bloc.getColonne()*taille_bloc, bloc.getLigne()*taille_bloc, taille_bloc,
		                taille_bloc, null);
		    	break;
		    case "MONTAGNE":
		    	g.drawImage(imageMontagne, bloc.getColonne()*taille_bloc, bloc.getLigne()*taille_bloc, taille_bloc,
		                taille_bloc, null);
		    	break;
		}
	}
  
    public void peindreMobiles(MobileManager mobileManager, Graphics graphics) {
    	 Graphics2D g = (Graphics2D) graphics;
    	 
    	 int temps = (int) System.currentTimeMillis() / 200;
    	 BufferedImage imageNavire =  this.sprites.getImage("navire.png");
    	 BufferedImage imagePersonnage = (temps % 2 == 0) ? this.sprites.getImage("perso1.png"): this.sprites.getImage("perso2.png");
 		
    	 for (Mobile mobile : mobileManager) {
    		 String typeMobile = mobile.getType();
    		 peindreMobile(g, imagePersonnage, imageNavire, temps, mobile, typeMobile);
    	 }
    	 
    }

	private void peindreMobile(Graphics2D g, BufferedImage imagePersonnage, BufferedImage imageNavire, int temps, Mobile mobile, String typeMobile) {
		switch (typeMobile) {
			case "NAVIRE":
		 		g.drawImage(imageNavire, mobile.getColonne()*taille_bloc, mobile.getLigne()*taille_bloc, taille_bloc,
		                taille_bloc, null);
		 		break;
		 	case "PERSONNAGE":
		 			g.drawImage(imagePersonnage, mobile.getColonne()*taille_bloc, mobile.getLigne()*taille_bloc, taille_bloc,
		                    taille_bloc, null);
		 		break;
		 }
	}
    
}