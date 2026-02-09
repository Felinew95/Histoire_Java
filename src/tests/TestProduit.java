package tests;

import moteur.donnees.economie.Produit;

/**
 * Classe de test de la classe Produit
 * 
 * @author Alexandre
 * @version 1.0
 */
public class TestProduit {

    /**
     * Programme principal
     * 
     * @param args : Arguments
     */
    public static void main(String[] args) {
        Produit p1 = new Produit("Épée", 100, 12);
        Produit p2 = new Produit("Bouclier", 1000, 15);

        System.out.println("Produit : " + p1);
        System.out.println("Produit : " + p2);

        if (p1.equals(p2)) {
            System.out.println("Les deux produits sont identiques");
        } else {
            System.out.println("Les deux produits sont différents");
        }
    }

}
