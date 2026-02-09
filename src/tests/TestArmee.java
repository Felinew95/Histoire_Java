package tests;

import moteur.donnees.Armee;

/**
 * Classe de test de la classe Armee
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Armee
 */
public class TestArmee {

    /**
     * Programme Principal
     * 
     * @param args : Arguments
     */
    public static void main(String[] args) {
        Armee a = new Armee("Ivar", 120423, 12808, 14, 100);
        System.out.println("Armée : " + a);
    }

}
