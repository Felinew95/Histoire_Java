package tests;

import gui.MainGUI;

public class TestGUI {
    public static void main(String[] args) {
        MainGUI main = new MainGUI();
        Thread thMain = new Thread(main);

        thMain.start();
    }
}
