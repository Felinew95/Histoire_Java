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
    private final String typeBloc;

    /**
     * Constructeur de la classe Bloc
     * 
     * @param ligne   : Position en ligne
     * @param colonne : Position en colonne
     */
    public Bloc(int ligne, int colonne, String typeBloc) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.typeBloc = typeBloc;
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
     * Getter de typeBloc 
     * 
     * @return Le type du bloc
     */
    public String getTypeBloc() {
        return typeBloc;
    }

    /**
     * Affiche les informations d'un bloc
     * 
     * @return L'affichage des coordonnées
     */
    @Override
    public String toString() {
        return "Bloc [ligne=" + ligne + ", colonne=" + colonne + "]";
    }

}
