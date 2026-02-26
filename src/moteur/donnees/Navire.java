package moteur.donnees;

import java.util.Objects;

/**
 * Classe qui représente un navire 
 * 
 * @author Alexandre
 * @version 1.0
 */
public class Navire implements Mobile {

	// Attributs 
	private String type;
	private int x;
	private int y;
	
	/**
	 * Constructeur de la classe Navire 
	 * @param type : Type du navire 
	 * @param x : Position en X
	 * @param y : Position en Y
	 */
	public Navire(String type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter de type
	 * @return Le type de navire
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * Getter de x
	 * @return La position en X
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * Getter de y
	 * @return La position en Y
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * Setter de type 
	 * @param type : Nouveau type du bateau
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Setter de x
	 * @param x: Nouvelle position en X
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Setter de y 
	 * @param y : Nouvelle position en Y
	 */
	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Méthode qui déplace un navire
	 * @param dx : Déplacement en X
	 * @param dy : Déplacement en Y
	 */
	@Override
	public void deplacer(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	/**
	 * Retourne le code de hachage de la classe Navire 
	 * @return Le code de hachage 
	 */
	@Override
	public int hashCode() {
		return Objects.hash(type, x, y);
	}

	/**
	 * Vérifie si deux navires sont identiques 
	 * @return true si deux navires sont identiques, false sinon 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Navire)) {
			return false;
		}
		
		Navire other = (Navire) obj;
		return Objects.equals(type, other.type) && x == other.x && y == other.y;
	}
	
}
