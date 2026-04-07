package moteur.carte;

import gui.mobiles.Mobile;
import gui.management.MobileManager;

/**
 * Classe qui représente une carte composée de blocs et pouvant contenir un continent.
 * <p>
 * La carte gère les dimensions (lignes et colonnes), les blocs, le continent chargé et
 * la position des mobiles grâce à un {@link MobileManager}.
 * </p>
 *
 * @author Alexandre
 * @author Massinissa
 * @author Tianxiao.Liu@u-cergy.fr
 *
 * @version 1.2
 *
 * @see Bloc
 * @see Continent
 */
public class Carte {

    /**
     * Nombre de lignes de la carte
     */
    private int nbLignes;

    /**
     * Nombre de colonnes de la carte
     */
    private int nbColonnes;

    /**
     * Tableau des blocs de la carte
     */
    private Bloc[][] blocs;

    /**
     * Continent actuellement chargé sur la carte
     */
    private Continent continent;

    /**
     * Constructeur de la classe Carte.
     * <p>
     * Initialise la carte avec le nombre de lignes et de colonnes, puis remplit tous les blocs avec le type "EAU".
     * </p>
     *
     * @param nbLignes   Nombre de lignes de la carte.
     * @param nbColonnes Nombre de colonnes de la carte.
     */
    public Carte(int nbLignes, int nbColonnes) {
        this.init(nbLignes, nbColonnes);
        this.remplirCarte();
    }

    /**
     * Retourne le nombre de lignes de la carte.
     *
     * @return Nombre de lignes.
     */
    public int getNbLignes() {
        return nbLignes;
    }

    /**
     * Retourne le nombre de colonnes de la carte.
     *
     * @return Nombre de colonnes.
     */
    public int getNbColonnes() {
        return nbColonnes;
    }

    /**
     * Retourne le tableau des blocs de la carte.
     *
     * @return Tableau 2D de {@link Bloc}.
     */
    public Bloc[][] getBlocs() {
        return blocs;
    }

    /**
     * Retourne le bloc correspondant aux coordonnées spécifiées.
     *
     * @param ligne   Ligne du bloc.
     * @param colonne Colonne du bloc.
     * @return Le {@link Bloc} correspondant, ou {@code null} si hors limites.
     */
    public Bloc getBloc(int ligne, int colonne) {
        if (ligne < 0 || ligne >= this.nbLignes || colonne < 0 || colonne >= this.nbColonnes) {
            return null;
        }
        return this.blocs[ligne][colonne];
    }

    /**
     * Initialise la carte avec le nombre de lignes et de colonnes.
     *
     * @param nbLignes   Nombre de lignes.
     * @param nbColonnes Nombre de colonnes.
     */
    private void init(int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.blocs = new Bloc[nbLignes][nbColonnes];
    }

    /**
     * Remplit la carte avec des blocs par défaut de type "EAU".
     */
    private void remplirCarte() {
        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                this.blocs[ligne][colonne] = new Bloc(ligne, colonne, "EAU");
            }
        }
    }

    /**
     * Définit le continent de la carte et applique les types de blocs correspondants.
     *
     * @param continent Le {@link Continent} à charger sur la carte.
     */
    public void setContinent(Continent continent) {
        this.continent = continent;

        if (continent != null) {
            for (Bloc bloc : continent) {
                setTypeBloc(bloc.getLigne(), bloc.getColonne(), bloc.getTypeBloc());
            }
        }
    }

    /**
     * Place les {@link Mobile} sur la carte selon leur position.
     *
     * @param mobileManager Le {@link MobileManager} contenant les mobiles à placer.
     */
    public void placerMobiles(MobileManager mobileManager) {
        for (Mobile mobile : mobileManager) {
            Bloc bloc = this.getBloc(mobile.getLigne(), mobile.getColonne());
            if (bloc != null) {
                bloc.setOccupant(mobile);
            }
        }
    }

    /**
     * Change le type du bloc situé aux coordonnées spécifiées.
     *
     * @param ligne   Ligne du bloc.
     * @param colonne Colonne du bloc.
     * @param type    Nouveau type du bloc.
     */
    private void setTypeBloc(int ligne, int colonne, String type) {
        Bloc blocCarte = this.getBloc(ligne, colonne);
        if (blocCarte != null) {
            blocCarte.setTypeBloc(type);
        }
    }

    /**
     * Vide le continent de la carte et réinitialise tous les blocs en type "EAU".
     */
    public void viderContinent() {
        this.remplirCarte();
        this.continent = null;
    }

}