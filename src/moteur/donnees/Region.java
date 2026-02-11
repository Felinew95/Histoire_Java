package moteur.donnees;

import java.awt.Point;

/**
 * Classe qui représente une région géographique
 * 
 * @author Alexandre et Massinissa
 * @version 1.0
 */
public class Region {
    
    // Attributs
    private String nom; // Nom de la région
    private Point position;
    private String chef;

    /**
     * Constructeur de la classe Region
     * 
     * @param nom : Nom de la région 
     * @param position : Position de la région 
     * @param chef : Chef de la région
     */
    public Region(String nom, Point position, String chef) {
        this.nom = nom;
        this.position = position;
        this.chef = chef;
    }

    /**
     * Getter de nom 
     * @return Le nom de la région 
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de position 
     * @return La position de la région 
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Getter de chef 
     * @return Le chef de la région 
     */
    public String getChef() {
        return chef;
    }

    /**
     * Setter de nom 
     * @param nom : Nouveau nom de la région 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter de position 
     * @param position : Nouvelle position de la région
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Setter de chef 
     * @param chef : Nouveau chef de la région 
     */
    public void setChef(String chef) {
        this.chef = chef;
    }

    /**
     * Affiche les informations de la région
     * @return l'affichage des informations
     */
    @Override
    public String toString() {
        return "Nom : " + this.getNom() + ", Chef : " + this.getChef() + 
            ", Position : " + this.getPosition();
    }

}
