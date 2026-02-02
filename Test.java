import javax.swing.JFrame;

public class Test extends JFrame {
    
    public Test(String titre) {
        super(titre);

        this.setVisible(true);
        this.pack();
    }
    
    public static void main(String[] args) {
        new Test("Test");
    }
}