package moteur.carte;

import gui.mobiles.Mobile;

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
    private String typeBloc;
    
    private Mobile occupant = null;
    private boolean accessible = true;


    /**
     * Constructeur de la classe Bloc
     * 
     * @param ligne   : Position en ligne
     * @param colonne : Position en colonne
     * @param typeBloc : Type du bloc
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
     * Getter de occupant
     * 
	 * @return L'occupant
	 */
	public Mobile getOccupant() {
		return occupant;
	}

	/**
	 * Getter de accessible
	 * 
	 * @return L'état accessible
	 */
	public boolean isAccessible() {
		return accessible;
	}
	
	/**
	 * Setter de occupant 
	 * 
	 * @param occupant : Nouvel occupant
	 */
	public void setOccupant(Mobile occupant) {
		this.occupant = occupant;
		setAccessible(occupant);
	}

	/**
	 * Setter de accessible 
	 * 
	 * @param occupant : Nouveau occupant
	 */
	private void setAccessible(Mobile occupant) {
		if (occupant == null) {
			this.accessible = true;
		} else {
			this.accessible = false;
		}
	}
	
	/**
	 * Setter de typeBloc
	 * 
	 * @param typeBloc: Nouveau type du bloc 
	 */
	public void setTypeBloc(String typeBloc) {
		this.typeBloc = typeBloc;
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
