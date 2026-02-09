package tests;

import moteur.donnees.economie.Ressource;

/**
 * Classe de test de la classe Ressource
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Ressource
 */
public class TestRessource {

    /**
     * Programme principal de test
     * 
     * @param args : Arguments
     */
    public static void main(String[] args) {
        Ressource res1 = new Ressource("Or", "matérielle", 100);
        Ressource res2 = new Ressource("Argent", "matérielle", 100);

        System.out.println("Ressource : " + res1);
        System.out.println("Ressource : " + res2);

        if (res2.equals(res1)) {
            System.out.println("\nLes deux ressources sont identiques");
        } else {
            System.out.println("\nLes deux ressources sont différentes");
        }
    }

}
