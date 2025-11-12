package src;

import javax.swing.SwingUtilities;
import src.ui.TetrisFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TetrisFrame frame = new TetrisFrame();
            frame.setVisible(true);
        });
    }
}
