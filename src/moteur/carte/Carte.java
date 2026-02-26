package moteur.carte;

import java.util.ArrayList;

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
    private final ArrayList<Ile> terres = new ArrayList<>();

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
     * Getter de terres
     * @return La liste des iles 
     */
    public ArrayList<Ile> getTerres() {
        return terres;
    }
    
    /**
     * Retourne un bloc 
     * @param ligne : Ligne du bloc 
     * @param colonne : Colonne du bloc 
     * @return retourne le bloc correspondant 
     */
    public Bloc getBloc(int ligne, int colonne) {
    	if (ligne < 0 || ligne >= this.nbLignes || colonne < 0 || colonne >= this.nbColonnes) {
    		return null;
    	}
    	
    	return this.blocs[ligne][colonne];
    }
    
    /**
     * Vérifie si un bloc existe 
     * @param ligne : Ligne du bloc 
     * @param colonne : Colonne du bloc 
     * @return true si le bloc existe, false sinon 
     */
    public boolean estExistBloc(int ligne, int colonne) {
		return this.getBloc(ligne, colonne) != null;
    }
    
    /**
     * Vérifie si une ile existe 
     * @param ile : une ile 
     * @return true si une ile existe, false sinon 
     */
    public boolean estExistIle(Ile ile) {
    	return this.terres.contains(ile);
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
     * Ajoute une ile à la carte 
     * @param ile : Une ile 
     */
    public void ajouterIle(Ile ile) {
    	if (!this.estExistIle(ile)) {    		
    		for (Bloc bloc: ile) {
    			int ligne = bloc.getLigne();
    			int colonne = bloc.getColonne();
    			String type = bloc.getTypeBloc();
    			
    			Bloc blocCarte = this.getBloc(ligne, colonne);
    			if (blocCarte != null) {
    				blocCarte.setTypeBloc(type);
    			}
    		}
    		
    		this.terres.add(ile);
    	}
    }
       
}
