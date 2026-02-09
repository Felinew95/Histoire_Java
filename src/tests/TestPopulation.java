package tests;

import moteur.donnees.Population;

/**
 * Classe de test de la classe Population
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Population
 */
public class TestPopulation {

    /**
     * Programme principal
     * 
     * @param args : Arguments
     */
    public static void main(String[] args) {
        Population p = new Population(12334, 30.3f, "HOMME");
        System.out.println("Population : " + p);
    }

}
