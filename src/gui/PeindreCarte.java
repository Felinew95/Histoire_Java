package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import config.SimConfig;

import moteur.carte.Bloc;
import moteur.carte.Carte;

public class PeindreCarte {

    public void peindreEau(Carte carte, Graphics graphics) throws IOException {
        int taille_bloc = SimConfig.TAILLE_BLOC;
        Bloc[][] blocs = carte.getBlocs();

        BufferedImage imageEau1 = ImageIO.read(new File("src/images/eau1.png"));
        BufferedImage imageEau2 = ImageIO.read(new File("src/images/eau2.png"));

        for (int lineIndex = 0; lineIndex < carte.getNbLignes(); lineIndex++) {
            for (int columnIndex = 0; columnIndex < carte.getNbColonnes(); columnIndex++) {
                Bloc bloc = blocs[lineIndex][columnIndex];

                int t = (int) System.currentTimeMillis() / 1000;
                BufferedImage image = imageEau2;
                if (t % 2 == 0) {
                    image = imageEau1;
                }

                Graphics2D g = (Graphics2D) graphics;
                g.drawImage(image, bloc.getColonne() * taille_bloc, bloc.getLigne() * taille_bloc, null);
            }
        }
    }

}
