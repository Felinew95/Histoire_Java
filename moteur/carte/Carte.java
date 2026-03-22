package moteur.carte;

import gui.mobiles.Mobile;
import moteur.traitement.MobileManager;

/**
 * Classe qui représente une carte
 * 
 * @author Alexandre et Massinissa
 * @version 1.2
 * 
 * @see Bloc
 * @see Continent
 */
public class Carte {

    // Attributs
    private int nbLignes;
    private int nbColonnes;

    private Bloc[][] blocs;
    private Continent continent;

    /**
     * Constructeur de la classe Carte
     * 
     * @param nbLignes   : Nombre de lignes
     * @param nbColonnes : Nombre de colonnes
     */
    public Carte(int nbLignes, int nbColonnes) {
        this.init(nbLignes, nbColonnes);
        this.remplirCarte();
    }

    /**
     * Getter de nbLignes
     * 
     * @return Le nombre de lignes de la carte
     */
    public int getNbLignes() {
        return nbLignes;
    }

    /**
     * Getter de nbColonnes
     * 
     * @return Le nombre de colonnes de la carte
     */
    public int getNbColonnes() {
        return nbColonnes;
    }

    /**
     * Getter de blocs
     * 
     * @return Le tableau de blocs
     */
    public Bloc[][] getBlocs() {
        return blocs;
    }

    /**
     * Getter de continent
     * 
     * @return Le continent actuellement chargé sur la carte
     */
    public Continent getContinent() {
        return continent;
    }

    /**
     * Retourne un bloc
     * 
     * @param ligne   : Ligne du bloc
     * @param colonne : Colonne du bloc
     * 
     * @return Le bloc correspondant, ou null si hors limites
     */
    public Bloc getBloc(int ligne, int colonne) {
        if (ligne < 0 || ligne >= this.nbLignes || colonne < 0 || colonne >= this.nbColonnes) {
            return null;
        }
        return this.blocs[ligne][colonne];
    }

    /**
     * Vérifie si un bloc existe
     * 
     * @param ligne   : Ligne du bloc
     * @param colonne : Colonne du bloc
     * 
     * @return true si le bloc existe, false sinon
     */
    public boolean estExistBloc(int ligne, int colonne) {
        return this.getBloc(ligne, colonne) != null;
    }

    /**
     * Initialise la carte
     * 
     * @param nbLignes   : Nombre de lignes
     * @param nbColonnes : Nombre de colonnes
     */
    private void init(int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.blocs = new Bloc[nbLignes][nbColonnes];
    }

    /**
     * Remplissage basique de la carte. Les blocs sont de type "EAU" par défaut
     */
    private void remplirCarte() {
        for (int indice_ligne = 0; indice_ligne < nbLignes; indice_ligne++) {
            for (int indice_colonne = 0; indice_colonne < nbColonnes; indice_colonne++) {
                this.blocs[indice_ligne][indice_colonne] = new Bloc(indice_ligne, indice_colonne, "EAU");
            }
        }
    }

    /**
     * Définit le continent de la carte et applique ses blocs
     * 
     * @param continent : Le continent à charger
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
     * Méthode qui place les mobiles sur la carte
     * 
     * @param mobileManager : Manager des mobiles
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
     * Méthode qui change le type du bloc
     * 
     * @param ligne   : Ligne du bloc
     * @param colonne : Colonne du bloc
     * @param type    : Type du bloc
     */
    private void setTypeBloc(int ligne, int colonne, String type) {
        Bloc blocCarte = this.getBloc(ligne, colonne);
        if (blocCarte != null) {
            blocCarte.setTypeBloc(type);
        }
    }

    /**
     * Vide le continent de la carte et réinitialise tous les blocs en EAU
     */
    public void viderContinent() {
        this.remplirCarte();
        this.continent = null;
    }

}