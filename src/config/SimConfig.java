package config;

/**
 * Classe de configuration de la simulation
 * 
 * @author Massinissa
 * @version 1.0
 */
public class SimConfig {

    // Attributs
	public static final int TAILLE_FENETRE_X = 1550;
	public static final int TAILLE_FENETRE_Y = 820;
	
    public static final int TAILLE_SIM_X = 1350;
    public static final int TAILLE_SIM_Y = 700;
    
    public static final int TAILLE_BLOC = 20;
    public static final int NOMBRE_LIGNES = TAILLE_SIM_Y / TAILLE_BLOC;
    public static final int NOMBRE_COLONNES = TAILLE_SIM_X / TAILLE_BLOC;

}
