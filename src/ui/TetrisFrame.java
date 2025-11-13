package src.ui;

import java.awt.*;
import javax.swing.*;

public class TetrisFrame extends JFrame {
    public TetrisFrame() {
        setTitle("Tetris Java PRO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(350, 520);
        setLocationRelativeTo(null);
        trocarPainel(new MenuPrincipal(this));
    }

    public void trocarPainel(JPanel novoPainel) {
        getContentPane().removeAll();
        add(novoPainel, BorderLayout.CENTER);
        revalidate();
        repaint();
        novoPainel.requestFocusInWindow();
    }
}
