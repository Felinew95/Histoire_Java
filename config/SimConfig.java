package config;

import java.awt.*;
import java.util.Random;

/**
 * Classe de configuration de la simulation.
 *
 * <p>
 * Contient toutes les constantes et paramètres globaux utilisés
 * pour gérer l'affichage, la simulation, les statistiques et
 * les données initiales.
 * </p>
 *
 * @author Massinissa
 * @author Alexandre
 * @author Tauseef
 *
 * @version 1.5
 */
public class SimConfig {

    /**
     * Générateur de nombres aléatoires pour introduire de la variabilité réaliste
     */
    private static final Random RANDOM = new Random();

    /**
     * Nom de la fenêtre principale de la simulation
     */
    public static final String NOM_FENETRE = "Histoire";

    /**
     * Nom de la fenêtre des statistiques
     */
    public static final String NOM_FENETRE_STATS = "Histoire : Statistiques";

    /**
     * Largeur de la fenêtre principale
     */
    public static final int TAILLE_FENETRE_X = 1550;

    /**
     * Hauteur de la fenêtre principale
     */
    public static final int TAILLE_FENETRE_Y = 820;

    /**
     * Largeur de la fenêtre des statistiques
     */
    public static final int TAILLE_FENETRE_STATS_X = 1300;

    /**
     * Hauteur de la fenêtre des statistiques
     */
    public static final int TAILLE_FENETRE_STATS_Y = 820;

    /**
     * Largeur de la zone de simulation
     */
    public static final int TAILLE_SIM_X = 1340;

    /**
     * Hauteur de la zone de simulation
     */
    public static final int TAILLE_SIM_Y = 700;

    /**
     * Taille d’un bloc pour la carte Europe
     */
    public static final int TAILLE_BLOC_EUROPE = 10;

    /**
     * Taille d’un bloc pour la carte principale
     */
    public static final int TAILLE_BLOC_CARTE = 20;

    /**
     * Nombre de lignes de la carte calculé automatiquement
     */
    public static final int NOMBRE_LIGNES = TAILLE_SIM_Y / TAILLE_BLOC_EUROPE;

    /**
     * Nombre de colonnes de la carte calculé automatiquement
     */
    public static final int NOMBRE_COLONNES = TAILLE_SIM_X / TAILLE_BLOC_EUROPE;

    /**
     * Couleur de survol pour les boutons
     */
    public static final Color COULEUR_SURVOL_BOUTON = new Color(126, 145, 252);

    /**
     * Police des boutons
     */
    public static final Font POLICE_BOUTON = new Font(Font.SERIF, Font.BOLD, 70);

    /**
     * Décalage du texte sur les boutons
     */
    public static final int DECALAGE_TEXTE_BOUTON = 10;

    /**
     * Police pour les labels de la simulation
     */
    public static final Font FONT_LABEL_SIM = new Font("Serif", Font.BOLD | Font.ITALIC, 11);

    /**
     * Police utilisée dans le panneau statistique
     */
    public static final Font FONT_STATS = new Font(Font.SANS_SERIF, Font.ITALIC, 12);

    /**
     * Intervalle de déplacement des unités (en ms)
     */
    public static final long INTERVALLE_DEPLACEMENT_MS = 400;

    /**
     * Intervalle d’incrémentation d’une année pour Europe (en ms)
     */
    public static final long INTERVALLE_TEMPS_ANNEE_EUROPE_MS = 1000;

    /**
     * Intervalle d’incrémentation d’une année pour carte principale (en ms)
     */
    public static final long INTERVALLE_TEMPS_ANNEE_CARTE_MS = 2000;

    /**
     * Année de début de la simulation
     */
    public static final int ANNEE_DEBUT_SIM = 780;

    /**
     * Année de fin de la simulation
     */
    public static final int ANNEE_FIN_SIM = 1075;

    /**
     * Nom de la civilisation simulée
     */
    public static final String NOM_CIVILISATION = "Viking";

    /** 
     * Nombre d'habitants au début de la simulation (compris entre 25 000 et 45 000). 
     */
    public static final int NB_HABITANT_DEBUT_SIM = RANDOM.nextInt(20000) + 25000;

    /** 
     * Âge moyen de la population lors du lancement (compris entre 22 et 26 ans). 
     */
    public static final int AGE_MOYEN_DEBUT_SIM = RANDOM.nextInt(4) + 22;

    /** 
     * Sexe prédominant au sein de la population de départ. 
     */
    public static final String SEXE_MAJORITAIRE_DEBUT_SIM = "HOMME";

    /** 
     * Trésorerie maximale initiale exprimée en Stycas (monnaie de l'époque). 
     */
    public static final float MAX_STYCAS = (float) RANDOM.nextInt(100_000_000) + 50_000_000;

    /** 
     * Nom de la religion prédominante au début de la partie. 
     */
    public static final String NOM_RELIGION_DEBUT = "Paganisme nordique";

    /** 
     * Système de croyance ou dogme initial. 
     */
    public static final String CROYANCE_RELIGION_DEBUT = "Paganisme nordique";

    /** 
     * Pourcentage d'influence de la religion sur la population (0 à 100). 
     */
    public static final float INFLUENCE_RELIGION_DEBUT = 90f;

    /** 
     * Type de régime politique en place au lancement de la simulation. 
     */
    public static final String TYPE_REGIME_DEBUT = "Monarchie viking";

    /** 
     * Niveau de stabilité politique initiale (exprimé en pourcentage). 
     */
    public static final float STABILITE_POLITIQUE_DEBUT = 60f;

    /**
     * Dimension des graphiques affichés dans la fenêtre
     */
    public static final Dimension CHART_DIM = new Dimension(400, 280);

    /**
     * Couleur de fond principale
     */
    public static final Color COLOR_BG_MAIN = new Color(0xD8C39A);

    /**
     * Couleur des cartes
     */
    public static final Color COLOR_BG_CARD = new Color(0xC2A57E);

    /**
     * Couleur de fond interne
     */
    public static final Color COLOR_BG_INNER = new Color(0xE9DDC7);

    /**
     * Couleur des bordures
     */
    public static final Color COLOR_BORDER = new Color(0x4F3524);

    /**
     * Couleur du texte général
     */
    public static final Color COLOR_TEXT = new Color(0x2F2016);

    /**
     * Couleur des titres
     */
    public static final Color COLOR_TITLE = new Color(0x1A100A);

    /**
     * Couleur d’accent principal
     */
    public static final Color COLOR_ACCENT = new Color(0x7A3E1D);
   
    /** 
     * Couleur d'accentuation rouge utilisée pour les éléments mis en évidence ou les erreurs mineures. 
     */
    public static final Color COLOR_ACCENT_RED = new Color(0x8B3A0A);

    /** 
     * Couleur d'accentuation bleue (teinte gris-brun) utilisée pour les éléments secondaires. 
     */
    public static final Color COLOR_ACCENT_BLUE = new Color(0x6B5B45);

    /** 
     * Couleur associée aux messages de réussite ou aux validations (tons dorés/brun clair). 
     */
    public static final Color COLOR_SUCCESS = new Color(0x9B6E3A);

    /** 
     * Couleur d'avertissement utilisée pour attirer l'attention sans bloquer l'utilisateur. 
     */
    public static final Color COLOR_WARNING = new Color(0xA0522D);

}