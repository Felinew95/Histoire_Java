package tests.manuel;

import gui.fenetres.MainGUI;

/**
 * Classe de test manuel pour lancer l'application principale.
 *
 * <p>
 * Cette classe permet de démarrer l'interface graphique principale du logiciel
 * dans un thread séparé afin de ne pas bloquer le thread principal.
 * </p>
 *
 * @author Massinissa
 * @version 1.0
 */
public class TestMainGUI {

    /**
     * Programme principal pour exécuter le test.
     *
     * <p>
     * Cette méthode crée une instance de {@link MainGUI} et la lance dans un
     * thread séparé.
     * </p>
     *
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        MainGUI main = new MainGUI();
        Thread thMain = new Thread(main);
        thMain.start();
    }

}