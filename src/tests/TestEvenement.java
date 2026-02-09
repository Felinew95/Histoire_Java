package tests;

import moteur.donnees.Evenement;

/**
 * Classe de test de la classe Evenement
 * 
 * @author Alexandre
 * @version 1.0
 * 
 * @see Evenement
 */
public class TestEvenement {

    /**
     * Programme principal
     * 
     * @param args : Arguments
     */
    public static void main(String[] args) {
        String narration = "Un matin du 8 juin 793, les habitants de l'île de Lindisfarne aperçoivent avec effroi des voiles à \r\n"
                + //
                "l'horizon. Des navires vikings, surgis de nulle part, se rapprochent rapidement de la côte. Le \r\n" + //
                "monastère, pourtant un haut lieu de la foi et de la culture anglo-saxonne, est pris au dépourvu. \r\n"
                + //
                "Personne n'était prêt à faire face à une telle attaque.\r\n" + //
                "Les guerriers vikings débarquent dans un vacarme d’armes et de cris de guerre. Ils pillent le \r\n" + //
                "monastère sans pitié, s’emparant de tout ce qui brille : calices en or, manuscrits richement \r\n" + //
                "enluminés, reliquaires d’une valeur inestimable. Les moines sont massacrés ou emmenés de force \r\n" + //
                "pour être réduits en esclavage. L’église elle-même n’est pas épargnée : les autels sont renversés, les \r\n"
                + //
                "croix brisées.\r\n" + //
                "La nouvelle de ce raid se propage à travers l’Europe chrétienne comme une traînée de poudre. \r\n" + //
                "Bouleversé, l’érudit Alcuin de York écrit au roi Æthelred de Northumbrie : « Jamais on n’avait vu \r\n"
                + //
                "une telle terreur en Bretagne. Personne ne pensait qu’une telle traversée était possible. »\r\n" + //
                "Ce raid marque le véritable début de l’âge viking. Pour les Scandinaves, c’est la preuve que les \r\n"
                + //
                "richesses de l’Occident chrétien sont à leur portée, et que leurs drakkars peuvent les y conduire en \r\n"
                + //
                "un rien de temps. Pour l’Europe, c’est le début de trois longs siècles de terreur venue du Nord";

        Evenement e = new Evenement("Le raid de Lindisfarne", 793, "Angleterre", narration, "Guerre");
        System.out.println("Événement :" + e);

    }

}
