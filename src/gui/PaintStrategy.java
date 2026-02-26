package gui;

import config.SimConfig;
import config.SpritesConfig;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import moteur.carte.Bloc;
import moteur.carte.Carte;
import moteur.carte.Ile;

public class PaintStrategy {

    private final SpritesConfig sprites = new SpritesConfig("src/images");

    public void peindreEau(Carte carte, Graphics graphics) {
        int taille_bloc = SimConfig.TAILLE_BLOC;
        Bloc[][] blocs = carte.getBlocs();

        BufferedImage imageEau1 = this.sprites.getImage("eau1.png");
        BufferedImage imageEau2 = this.sprites.getImage("eau2.png");
        
        BufferedImage image;
        image = imageEau2;

        int temps = (int) System.currentTimeMillis() / 500;
        if (temps % 2 == 0) {
            image = imageEau1;
        }

        Graphics2D g = (Graphics2D) graphics;
        for (int lineIndex = 0; lineIndex < carte.getNbLignes(); lineIndex++) {
            for (int columnIndex = 0; columnIndex < carte.getNbColonnes(); columnIndex++) {
                Bloc bloc = blocs[lineIndex][columnIndex];
                g.drawImage(image, bloc.getColonne() * taille_bloc, bloc.getLigne() * taille_bloc,
                        taille_bloc, taille_bloc, null);
            }
        }
    }

    public void peindreTerres(Carte carte, Graphics graphics) throws IllegalArgumentException {
        ArrayList<Ile> terres = carte.getTerres();
        int taille_bloc = SimConfig.TAILLE_BLOC;

        BufferedImage imageTerre = this.sprites.getImage("terre.png");
        BufferedImage imageArbre = this.sprites.getImage("tree.png"); 
        BufferedImage imagePlage = this.sprites.getImage("sable.png");

        Graphics2D g = (Graphics2D) graphics;

        for (Ile terre : terres) {
            ArrayList<Bloc> blocs = terre.getBlocs();

            for (Bloc bloc : blocs) {
                String typeBloc = bloc.getTypeBloc();
                
                switch (typeBloc) {
	                case "ARBRE":
	                	g.drawImage(imageTerre, bloc.getColonne()*taille_bloc, bloc.getLigne()*taille_bloc, taille_bloc,
	                            taille_bloc, null);
	                	g.drawImage(imageArbre, bloc.getColonne()*taille_bloc, bloc.getLigne()*taille_bloc, taille_bloc,
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
                }
                
            }
        }
    }

 
}