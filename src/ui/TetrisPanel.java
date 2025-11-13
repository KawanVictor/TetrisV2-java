package src.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import src.domain.*;
import src.service.GhostPieceCalculator;

public class TetrisPanel extends JPanel implements KeyListener {
    private final Partida partida;
    private final ModoPartida modo;
    private static final int GRID_WIDTH = 10, GRID_HEIGHT = 20;
    private static final int CELL_SIZE = 34, PANEL_MARGIN = 38;
    private static final Map<Tetromino.Tipo, Color> COLORS = new HashMap<>() {{
        put(Tetromino.Tipo.I, new Color(83, 236, 255));
        put(Tetromino.Tipo.O, new Color(255, 252, 97));
        put(Tetromino.Tipo.T, new Color(183, 74, 255));
        put(Tetromino.Tipo.S, new Color(80, 255, 145));
        put(Tetromino.Tipo.Z, new Color(255, 91, 87));
        put(Tetromino.Tipo.J, new Color(80, 120, 255));
        put(Tetromino.Tipo.L, new Color(255, 168, 60));
    }};
    public TetrisPanel(ModoPartida modo) {
        this.modo = modo;
        this.partida = new Partida(modo);
        setPreferredSize(new Dimension(GRID_WIDTH*CELL_SIZE + PANEL_MARGIN*2, GRID_HEIGHT*CELL_SIZE + 180));
        setBackground(new Color(18, 19, 32));
        setFocusable(true);
        addKeyListener(this);
        new Timer(36, e -> {
            if (!modo.acabou(partida)) partida.atualizar();
            repaint();
        }).start();
    }

    @Override
    public void paintComponent(Graphics gOrig) {
        super.paintComponent(gOrig);
        Graphics2D g = (Graphics2D) gOrig;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Centraliza grid
        int totalWidth = GRID_WIDTH*CELL_SIZE, panelW = getWidth();
        int x0 = (panelW-totalWidth)/2;

        // Tabuleiro com sombra
        g.setColor(new Color(45, 50, 70,130));
        g.fillRoundRect(x0-9, PANEL_MARGIN-9, totalWidth+18, GRID_HEIGHT*CELL_SIZE+18, 30, 30);

        g.setColor(new Color(32, 36, 56));
        g.fillRoundRect(x0, PANEL_MARGIN, totalWidth, GRID_HEIGHT*CELL_SIZE, 18, 18);

        var grid = partida.getTabuleiro().getGrid();
        for (int y = 0; y < GRID_HEIGHT; y++)
            for (int x = 0; x < GRID_WIDTH; x++) {
                Tetromino.Tipo t = grid[y][x];
                if (t != null) {
                    Color color = COLORS.getOrDefault(t, Color.LIGHT_GRAY);
                    g.setColor(color.darker());
                    g.fillRoundRect(x0+x * CELL_SIZE, PANEL_MARGIN+y * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
                    g.setColor(color);
                    g.fill3DRect(x0+x * CELL_SIZE+4, PANEL_MARGIN+y * CELL_SIZE+4, CELL_SIZE-8, CELL_SIZE-8, true);
                }
            }
        Tetromino ghost = GhostPieceCalculator.calcularGhost(partida);
        g.setColor(new Color(220, 220, 255, 45));
        for (Posicao p : ghost.getPosicoes())
            g.fillRoundRect(x0+p.getX()*CELL_SIZE, PANEL_MARGIN+p.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE,8,8);
        Tetromino.Tipo tipo = partida.getTetrominoAtual().getTipo();
        for (Posicao p : partida.getTetrominoAtual().getPosicoes()) {
            Color c = COLORS.getOrDefault(tipo, Color.GREEN);
            g.setColor(c);
            g.fill3DRect(x0+p.getX()*CELL_SIZE+4, PANEL_MARGIN+p.getY()*CELL_SIZE+4, CELL_SIZE-8, CELL_SIZE-8, true);
            g.setColor(c.darker());
            g.drawRoundRect(x0+p.getX()*CELL_SIZE, PANEL_MARGIN+p.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE, 8,8);
        }
        // HUD
        g.setFont(new Font("JetBrains Mono", Font.BOLD, 24));
        g.setColor(new Color(255,255,255,230));
        g.drawString("TETRIS", x0+8, 34);

        // Timer/Status como painel destacado
        g.setColor(new Color(55, 80, 170, 170));
        g.fillRoundRect(x0+totalWidth-148, GRID_HEIGHT*CELL_SIZE + PANEL_MARGIN - 5, 140, 37, 14, 14);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        if (modo.getNome().equals("Tempo")) {
            g.drawString("Tempo: " + formatSeconds(partida.getSegundosJogando()), 
                           x0+totalWidth-142, GRID_HEIGHT*CELL_SIZE+PANEL_MARGIN+18);
        } else {
            g.drawString("Level: "+partida.getNivel(), x0+totalWidth-142, GRID_HEIGHT*CELL_SIZE+PANEL_MARGIN+18);
        }
        // Linhas de status:
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Pontuação: "+ partida.getPontuacao(), x0+8, GRID_HEIGHT*CELL_SIZE+PANEL_MARGIN+18);
        g.drawString("Linhas: "+ partida.getLinhasEliminadas(), x0+8, GRID_HEIGHT*CELL_SIZE+PANEL_MARGIN+37);
        // Next e Hold melhorados
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.setColor(new Color(240,240,240));
        g.drawString("HOLD", x0-54, PANEL_MARGIN+22);
        if (partida.getHoldTetromino() != null)
            drawMiniTetromino(g, partida.getHoldTetromino(), x0-54, PANEL_MARGIN+34, 14);
        g.drawString("NEXT", x0+totalWidth+16, PANEL_MARGIN+22);
        drawMiniTetromino(g, partida.getProximoTetromino(), x0+totalWidth+16, PANEL_MARGIN+34, 14);
        // Game over/win
        if (modo.acabou(partida)) {
            g.setColor(new Color(255,0,36,225));
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString(partida.isGameOver()?"GAME OVER":"WIN!", x0+28, 370);
            g.setFont(new Font("Arial", Font.BOLD, 17));
            g.setColor(Color.WHITE);
            g.drawString("Pressione [R] para reiniciar ou [ESC] para Menu", x0+12, 410);
        }
    }

    private void drawMiniTetromino(Graphics2D g, Tetromino t, int baseX, int baseY, int cellSize) {
        g.setColor(COLORS.getOrDefault(t.getTipo(), Color.LIGHT_GRAY));
        int refX = t.getPosicoes().stream().mapToInt(Posicao::getX).min().orElse(0);
        int refY = t.getPosicoes().stream().mapToInt(Posicao::getY).min().orElse(0);
        for (Posicao p : t.getPosicoes()) {
            g.fillRoundRect(baseX + (p.getX()-refX)*cellSize, baseY + (p.getY()-refY)*cellSize, cellSize, cellSize, 4,4);
        }
    }
    private String formatSeconds(int total) {
        int min = total/60, sec = total%60;
        return String.format("%02d:%02d", min, sec);
    }
    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (modo.acabou(partida)) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                removeKeyListener(this);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.setContentPane(new MenuPrincipal((TetrisFrame) frame));
                frame.revalidate();
                frame.repaint();
            }
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new MenuPrincipal((TetrisFrame) frame));
            frame.revalidate();
            frame.repaint();
            return;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: partida.moverPeca(-1, 0); break;
            case KeyEvent.VK_RIGHT: partida.moverPeca(1, 0); break;
            case KeyEvent.VK_DOWN: partida.moverPeca(0, 1); break;
            case KeyEvent.VK_UP: partida.rotacionarPeca(); break;
            case KeyEvent.VK_SPACE: while (partida.moverPeca(0, 1)); break;
            case KeyEvent.VK_SHIFT: partida.ativarHold(); break;
        }
        repaint();
    }
    @Override public void keyReleased(KeyEvent e) {}
}
