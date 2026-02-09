package moteur.carte;

/**
 * Classe qui représente un bloc
 * 
 * @author Tauseef
 * @version 1.1
 */
public class Bloc {

    // Attributs
    private final int ligne;
    private final int colonne;

    /**
     * Constructeur de la classe Bloc
     * 
     * @param ligne   : Position en ligne
     * @param colonne : Position en colonne
     */
    public Bloc(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    /**
     * Getter de ligne
     * 
     * @return La position du bloc en ligne
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Getter de colonne
     * 
     * @return La position du bloc en colonne
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Affiche les informations d'un bloc
     * 
     * @return L'affichage des coordonnées
     */
    @Override
    public String toString() {
        return "Block [line=" + ligne + ", column=" + colonne + "]";
    }

}
