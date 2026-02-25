package moteur.donnees;

import java.util.Objects;

/**
 * Classe qui représente un personnage 
 * 
 * @author Alexandre et Tauseef
 * @version 1.0
 */
public class Personnage {
    
    // Attributs 
    private String nom;
    private String typePersonnage;

    private int x;
    private int y;

    /**
     * Constructeur de la classe Personnage 
     * 
     * @param nom : Nom du personnage 
     * @param ligne : Position en ligne 
     * @param colonne : Position en colonne 
     */
    public Personnage(String nom, String typePersonnage, int x, int y) {
        this.nom = nom;
        this.typePersonnage = typePersonnage;
        this.x = x;
        this.y = y;
    }

    /**
     * Getter de nom 
     * @return Le nom du personnage 
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de type personnage
     * @return Le type du personnage 
     */
    public String getTypePersonnage() {
        return typePersonnage;
    }

    /**
     * Getter de x
     * @return La position en x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter de y 
     * @return La position en y 
     */
    public int getY() {
        return y;
    }

    /**
     * Setter de nom 
     * @param nom : Nouveau nom du personnage 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter de x 
     * @param x : Nouvelle position en x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter de y 
     * @param y : Nouvelle position en y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Setter de typePersonnage
     * @param typePersonnage : Nouveau type du personnage 
     */
    public void setTypePersonnage(String typePersonnage) {
        this.typePersonnage = typePersonnage;
    }
    
    /**
     * Retourne le code de hachage de la classe Personnage
     * @return Le code de hachage 
     */
    @Override
	public int hashCode() {
		return Objects.hash(nom, typePersonnage, x, y);
	}

    /**
     * Vérifie si deux personnages sont identiques 
     * @return true si deux personnages sont identiques, false sinon 
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Personnage)) {
			return false;
		}
		
		Personnage other = (Personnage) obj;
		return Objects.equals(nom, other.nom) && Objects.equals(typePersonnage, other.typePersonnage) && x == other.x
				&& y == other.y;
	}

	/**
     * Affiche les informations du personnage 
     * @return l'affichage des informations 
     */
    @Override
    public String toString() {
        return "Nom : " + this.getNom() + ", Type : " + this.getTypePersonnage() + ", Position : (" + this.getX() + ", "
            + this.getY() + ")" ;
    }

}