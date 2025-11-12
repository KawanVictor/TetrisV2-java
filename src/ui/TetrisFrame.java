package src.ui;

import javax.swing.*;
import java.awt.*;

public class TetrisFrame extends JFrame {
    public TetrisFrame() {
        setTitle("Tetris Java PRO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(520, 600);
        setLocationRelativeTo(null);
        add(new TetrisPanel(), BorderLayout.CENTER);
    }

    public void trocarPainel(JPanel novoPainel) {
        getContentPane().removeAll();
        add(novoPainel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
