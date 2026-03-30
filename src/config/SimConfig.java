package config;

import java.awt.Color;
import java.awt.Font;

/**
 * Classe de configuration de la simulation
 * 
 * @author Massinissa & Alexandre & Tauseef
 * @version 1.0
 */
public class SimConfig {

    // Attributs

    // Nom de la fenêtre
    public static final String NOM_FENETRE = "Histoire"; // Fenêtre principale
    public static final String NOM_FENETRE_STATS = "Histoire : Statistiques"; // Fenêtre statistiques

    // Taille de la fenêtre
	public static final int TAILLE_FENETRE_X = 1550;
	public static final int TAILLE_FENETRE_Y = 820;

    // Taille de la carte en simulation
    public static final int TAILLE_SIM_X = 1340;
    public static final int TAILLE_SIM_Y = 700;

    // Taille des blocs de la carte
    public static final int TAILLE_BLOC_EUROPE = 10;
    public static final int TAILLE_BLOC_CARTE = 20;

    // Nombre de lignes et colonnes de la carte
    public static final int NOMBRE_LIGNES = TAILLE_SIM_Y / TAILLE_BLOC_EUROPE;
    public static final int NOMBRE_COLONNES = TAILLE_SIM_X / TAILLE_BLOC_EUROPE;

    // Style des boutons
    public static final Color COULEUR_SURVOL_BOUTON = new Color(126, 145, 252);
    public static final Font POLICE_BOUTON = new Font(Font.SERIF, Font.BOLD, 70);
    public static final int DECALAGE_TEXTE_BOUTON = 10;

    // Style des noms des régions
    public static final Font FONT_LABEL_SIM = new Font("Serif", Font.BOLD | Font.ITALIC, 11);

    // Style panneau statistique
    public static final Font FONT_STATS = new Font(Font.SANS_SERIF, Font.ITALIC, 13);

    // Intervalles de temps
    public static final long INTERVALLE_DEPLACEMENT_MS = 400;
    public static final long INTERVALLE_TEMPS_ANNEE_EUROPE_MS = 1500;
    public static final long INTERVALLE_TEMPS_ANNEE_CARTE_MS = 2500;

    // Données de départ
    public static final int ANNEE_DEBUT_SIM = 780;
    public static final String NOM_CIVILISATION = "Viking";

    // Données de départ sur la population
    public static final int NB_HABITANT_DEBUT_SIM = 20000;
    public static final int AGE_MOYEN_DEBUT_SIM = 25;
    public static final String SEXE_MAJORITAIRE_DEBUT_SIM = "HOMME";

    // Données sur l'économie
    public static final float MAX_STYCAS = 20_000_000f;
    public static final String[] NOM_PRODUITS = {
            "Fer", "Argent", "Bois", "Pierre", "Pain", "Eau", "Outils", "Vêtements", "Vin", "Bijoux", "Armes"
    };

    // Données de départ sur la religion
    public static final String NOM_RELIGION_DEBUT = "Paganisme nordique";
    public static final String CROYANCE_RELIGION_DEBUT = "Paganisme nordique";
    public static final float INFLUENCE_RELIGION_DEBUT = 90f;

    // Données de départ sur la politique
    public static final String TYPE_REGIME_DEBUT = "Monarchie viking";
    public static final float STABILITE_POLITIQUE_DEBUT = 60f;
    public static final String POLITIQUE_MILITAIRE_DEBUT = "Offensive";
    public static final String POLITIQUE_ECONOMIQUE_DEBUT = "Libre-échange";
    public static final String POLITIQUE_DIPLOMATIQUE_DEBUT = "Expansionniste";

}
