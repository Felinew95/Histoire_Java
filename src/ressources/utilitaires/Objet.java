package ressources.utilitaires;

public class Objet {
	private int cor_x;
	private int cor_y;
	private int taille_x;
	private int taille_y;
	private String type;
	
	public Objet(int cor_x,int cor_y,int taille_x,int taille_y,String type) {
		this.cor_x=cor_x;
		this.cor_y=cor_y;
		this.taille_x=taille_x;
		this.taille_y=taille_y;
		this.type=type;
	}
	
	public String getType() {
		return type;
	}
	public int getX() {
		return cor_x;
	}
	
	public int getY() {
		return cor_y;
	}
	
	public int getTailleX() {
		return taille_x;
	}
	
	public int getTailleY() {
		return taille_y;
	}
	
	public Objet getObjet() {
		return this;
	}
	





}
