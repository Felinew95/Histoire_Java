package moteur.traitement;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TraitementImage extends Traitement{
	private BufferedImage image;
	
	public TraitementImage(String chemin) {
		super("Image");
		lectureImage(chemin);
		
	}
	
	public void lectureImage(String chemin) {
		try {
			image = ImageIO.read(new File(chemin));
		}
	
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return image;
		
	}
	

	
	
	
	



}
