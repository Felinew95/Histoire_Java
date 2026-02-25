package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import moteur.donnees.Personnage;

public class Deplacement {
    
	public Personnage pers = new Personnage("Tauseef", "Soldat", 15, 40);
  
    public boolean avancer(int pas, int distance) {
       if (this.pers.getX() < distance) {
    	   this.pers.setX(this.pers.getX()+pas);
    	   return true;
       }
       
       return false;
    }

    public boolean sauter(int hauteur, int distance) {

        int ySol = 300;              
        int xDepart = 15;            
        int xFin = xDepart + distance;
        
        if (pers.getX() < xFin) {

            double x = pers.getX();


            double num = (x - xDepart) * (xFin - x);

           
            double milieu = (xFin - xDepart) / 2.0;
            double denom = milieu * milieu;

            
            double h = hauteur * (num / denom);

            // sol != 0
            pers.setY((int) (ySol - h));

            // avance pendant le saut
            pers.setX(pers.getX()+1);
            
            return true;

        } else {
            // fin du saut : retourne au sol
            pers.setY(ySol);
            return false;
        }
    }

    public void paint(Graphics g) throws IOException {
    	BufferedImage still = ImageIO.read(new File("src/images/persoTest.png")).getSubimage(0, 0, 60, 75);
        BufferedImage lstill = ImageIO.read(new File("src/images/persoTest.png")).getSubimage(155, 75, 60, 75);
        
        if (avancer(1, 800)) {
        	g.drawImage(lstill, this.pers.getX(), this.pers.getY(), 100, 100, null);
        } else {
        	g.drawImage(still, this.pers.getX(), this.pers.getY(), 100, 100, null);
        }
    }

}
