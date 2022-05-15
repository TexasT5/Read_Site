import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                main_screen mainScreen = new main_screen();
                mainScreen.setVisible(true);
            }
        });
    }
}
