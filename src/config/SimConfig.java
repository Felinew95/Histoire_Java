package config;

import java.awt.Color;
import java.awt.Font;

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
	
    public static final int TAILLE_SIM_X = 1340;
    public static final int TAILLE_SIM_Y = 700;
    
    public static final int TAILLE_BLOC_EUROPE = 10;
    public static final int TAILLE_BLOC_CARTE = 20;
    
    public static final int NOMBRE_LIGNES = TAILLE_SIM_Y / TAILLE_BLOC_EUROPE;
    public static final int NOMBRE_COLONNES = TAILLE_SIM_X / TAILLE_BLOC_EUROPE;
    
    public static final Color COULEUR_SURVOL_BOUTON = new Color(126, 145, 252);
    public static final Font POLICE_BOUTON = new Font(Font.SERIF, Font.BOLD, 70);
    public static final int DECALAGE_TEXTE_BOUTON = 10;
    
    public static final Font FONT_LABEL_SIM = new Font("Serif", Font.BOLD | Font.ITALIC, 11);
    
    public static final long INTERVALLE_DEPLACEMENT_MS = 400;
    
    public static final long INTERVALLE_TEMPS_ANNEE_EUROPE_MS = 1500;
    public static final long INTERVALLE_TEMPS_ANNEE_CARTE_MS = 2500;
    
}
