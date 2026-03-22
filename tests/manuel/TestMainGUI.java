package tests.manuel;

import gui.fenetres.MainGUI;

/**
 * Classe qui permet de tester le logiciel 
 * 
 * @author Massinissa
 * @version 1.0
 */
public class TestMainGUI {
	
	/**
	 * Programme principal
	 * 
	 * @param args : Arguments 
	 */
    public static void main(String[] args) {
        MainGUI main = new MainGUI();
        Thread thMain = new Thread(main);
        thMain.start();
    }
    
}
