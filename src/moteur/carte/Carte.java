package moteur.carte;

/**
 * Classe qui représente une carte
 * 
 * @author Alexandre
 * @version 1.1
 * 
 * @see Bloc
 */
public class Carte {

    // Attributs
    private int nbLignes;
    private int nbColonnes;
    private Bloc[][] blocs;

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
     * @return La liste des blocs
     */
    public Bloc[][] getBlocs() {
        return blocs;
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
     * Remplissage de la carte
     */
    private void remplirCarte() {
        for (int indice_ligne = 0; indice_ligne < nbLignes; indice_ligne++) {
            for (int indice_colonne = 0; indice_colonne < nbColonnes; indice_colonne++) {
                this.blocs[indice_ligne][indice_colonne] = new Bloc(indice_ligne, indice_colonne);
            }
        }
    }

}
