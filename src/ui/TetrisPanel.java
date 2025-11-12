package src.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import src.domain.Partida;
import src.domain.Posicao;
import src.domain.Tetromino;
import src.service.GhostPieceCalculator;

public class TetrisPanel extends JPanel implements KeyListener {
    private final Partida partida = new Partida();

    // Cores por tipo de peça
    private static final Map<Tetromino.Tipo, Color> COLORS = new HashMap<>() {{
        put(Tetromino.Tipo.I, Color.CYAN);
        put(Tetromino.Tipo.O, Color.YELLOW);
        put(Tetromino.Tipo.T, Color.MAGENTA);
        put(Tetromino.Tipo.S, Color.GREEN);
        put(Tetromino.Tipo.Z, Color.RED);
        put(Tetromino.Tipo.J, Color.BLUE);
        put(Tetromino.Tipo.L, Color.ORANGE);
    }};

    public TetrisPanel() {
        setPreferredSize(new Dimension(300, 480));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);

        // Atualizador do jogo
        new Timer(33, (ActionEvent e) -> {
            partida.atualizar();
            repaint();
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fundo do tabuleiro
        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, 300, 400);

        // Desenhar peças fixas
        var grid = partida.getTabuleiro().getGrid();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                Tetromino.Tipo t = grid[y][x];
                if (t != null) {
                    g.setColor(COLORS.getOrDefault(t, Color.LIGHT_GRAY));
                    g.fill3DRect(x * 30, y * 20, 28, 18, true);
                }
            }
        }

        // Ghost piece
        Tetromino ghost = GhostPieceCalculator.calcularGhost(partida);
        g.setColor(new Color(200, 200, 255, 90));
        for (Posicao p : ghost.getPosicoes()) {
            g.fillRect(p.getX() * 30, p.getY() * 20, 28, 18);
        }

        // Peça ativa
        Tetromino.Tipo tipo = partida.getTetrominoAtual().getTipo();
        g.setColor(COLORS.getOrDefault(tipo, Color.GREEN));
        for (Posicao p : partida.getTetrominoAtual().getPosicoes()) {
            g.fill3DRect(p.getX() * 30, p.getY() * 20, 28, 18, true);
        }

        // Peça HOLD
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("HOLD", 10, 430);
        if (partida.getHoldTetromino() != null) {
            drawMiniTetromino(g, partida.getHoldTetromino(), 10, 440, 8);
        }

        // Próxima peça
        g.drawString("NEXT", 220, 430);
        drawMiniTetromino(g, partida.getProximoTetromino(), 220, 440, 8);

        // Pontuação/nível
        g.setColor(Color.WHITE);
        g.drawString("Score: " + partida.getPontuacao(), 110, 430);
        g.drawString("Level: " + partida.getNivel(), 110, 450);

        // Game Over
        if (partida.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", 60, 200);
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString("Pressione [R] para reiniciar", 65, 230);
        }
    }

    // Desenha minitetrimino (hold/next)
    private void drawMiniTetromino(Graphics g, Tetromino t, int baseX, int baseY, int cellSize) {
        g.setColor(COLORS.getOrDefault(t.getTipo(), Color.LIGHT_GRAY));
        for (Posicao p : t.getPosicoes()) {
            g.fillRect(baseX + (p.getX() - t.getPosicoes().get(0).getX()) * cellSize,
                       baseY + (p.getY() - t.getPosicoes().get(0).getY()) * cellSize,
                       cellSize, cellSize);
        }
    }

    // Teclado
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (partida.isGameOver()) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                // Reiniciar
                removeKeyListener(this);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.setContentPane(new TetrisPanel());
                frame.revalidate();
                frame.repaint();
            }
            return;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: partida.moverPeca(-1, 0); break;
            case KeyEvent.VK_RIGHT: partida.moverPeca(1, 0); break;
            case KeyEvent.VK_DOWN: partida.moverPeca(0, 1); break;
            case KeyEvent.VK_UP: partida.rotacionarPeca(); break;
            case KeyEvent.VK_SPACE: while (partida.moverPeca(0, 1)); break;
            case KeyEvent.VK_SHIFT: partida.ativarHold(); break;
            case KeyEvent.VK_R: if (partida.isGameOver()) break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
