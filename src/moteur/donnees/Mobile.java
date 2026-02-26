package moteur.donnees;

/**
 * Interface Mobile 
 * 
 * @author Alexandre 
 * @version 1.0
 */
public interface Mobile {

	/**
	 * Getter de type
	 * @return Le type du mobile
	 */
	String getType();
	
	/**
	 * Getter de x
	 * @return La position en X
	 */
	int getX();
	
	/**
     * Getter de y 
     * @return La position en y 
     */
	int getY();
	
	/**
     * Setter de x 
     * @param x : Nouvelle position en x
     */
	void setX(int x);
	
	/**
     * Setter de y 
     * @param y : Nouvelle position en y
     */
	void setY(int y);
	
	/**
	 * Méthode qui déplace un mobile 
	 * @param dx : Déplacement en X
	 * @param dy : Déplacement en Y
	 */
	void deplacer(int dx, int dy);
	
}
