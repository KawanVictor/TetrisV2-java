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
    private static final int CELL_SIZE = 32, PANEL_MARGIN = 22;
    private static final Map<Tetromino.Tipo, Color> COLORS = new HashMap<>() {{
        put(Tetromino.Tipo.I, new Color(41, 230, 255));
        put(Tetromino.Tipo.O, new Color(255, 231, 0));
        put(Tetromino.Tipo.T, new Color(170, 51, 255));
        put(Tetromino.Tipo.S, new Color(67, 242, 106));
        put(Tetromino.Tipo.Z, new Color(255, 50, 62));
        put(Tetromino.Tipo.J, new Color(46, 90, 255));
        put(Tetromino.Tipo.L, new Color(255, 168, 30));
    }};
    public TetrisPanel(ModoPartida modo) {
        this.modo = modo;
        this.partida = new Partida(modo);
        setPreferredSize(new Dimension(GRID_WIDTH*CELL_SIZE + PANEL_MARGIN*2, GRID_HEIGHT*CELL_SIZE + 160));
        setBackground(new Color(16, 19, 32));
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
        g.setColor(new Color(32, 34, 50));
        g.fillRoundRect(PANEL_MARGIN-5, PANEL_MARGIN-5, GRID_WIDTH*CELL_SIZE+10, GRID_HEIGHT*CELL_SIZE+10, 18, 18);

        var grid = partida.getTabuleiro().getGrid();
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                Tetromino.Tipo t = grid[y][x];
                if (t != null) {
                    Color color = COLORS.getOrDefault(t, Color.LIGHT_GRAY);
                    g.setColor(color.darker().darker());
                    g.fillRoundRect(PANEL_MARGIN+x * CELL_SIZE, PANEL_MARGIN+y * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
                    g.setColor(color);
                    g.fill3DRect(PANEL_MARGIN+x * CELL_SIZE+3, PANEL_MARGIN+y * CELL_SIZE+3, CELL_SIZE-6, CELL_SIZE-6, true);
                }
            }
        }
        Tetromino ghost = GhostPieceCalculator.calcularGhost(partida);
        g.setColor(new Color(200, 200, 255, 40));
        for (Posicao p : ghost.getPosicoes()) {
            g.fillRoundRect(PANEL_MARGIN+p.getX() * CELL_SIZE, PANEL_MARGIN+p.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
        }
        Tetromino.Tipo tipo = partida.getTetrominoAtual().getTipo();
        g.setColor(COLORS.getOrDefault(tipo, Color.GREEN));
        for (Posicao p : partida.getTetrominoAtual().getPosicoes()) {
            g.setColor(COLORS.getOrDefault(tipo, Color.GREEN));
            g.fill3DRect(PANEL_MARGIN+p.getX() * CELL_SIZE+3, PANEL_MARGIN+p.getY() * CELL_SIZE+3, CELL_SIZE-6, CELL_SIZE-6, true);
            g.setColor(COLORS.getOrDefault(tipo, Color.GREEN).darker().darker());
            g.drawRoundRect(PANEL_MARGIN+p.getX()*CELL_SIZE, PANEL_MARGIN+p.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
        }
        // HUD/VISOR
        g.setFont(new Font("Arial", Font.BOLD, 21));
        g.setColor(Color.WHITE);
        g.drawString("Modo: " + modo.getNome(), 18, GRID_HEIGHT*CELL_SIZE + 60);
        g.setFont(new Font("Arial", Font.BOLD, 17));
        g.drawString("Score: " + partida.getPontuacao(), 18, GRID_HEIGHT*CELL_SIZE + 88);
        g.drawString("Level: " + partida.getNivel(), 142, GRID_HEIGHT*CELL_SIZE + 88);
        g.drawString("Lines: " + partida.getLinhasEliminadas(), 240, GRID_HEIGHT*CELL_SIZE + 88);
        if (modo.getNome().equals("Tempo")) {
            g.drawString("Time: " + partida.getSegundosJogando() + "s", 180, GRID_HEIGHT*CELL_SIZE + 115);
        }
        // Next e Hold
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("HOLD", 10, 22);
        if (partida.getHoldTetromino() != null)
            drawMiniTetromino(g, partida.getHoldTetromino(), 10, 30, 11);
        g.drawString("NEXT", PANEL_MARGIN+GRID_WIDTH*CELL_SIZE-60, 22);
        drawMiniTetromino(g, partida.getProximoTetromino(), PANEL_MARGIN+GRID_WIDTH*CELL_SIZE-60, 30, 11);
        // GAME OVER / WIN
        if (modo.acabou(partida)) {
            g.setColor(new Color(255,0,36,210));
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString(partida.isGameOver()?"GAME OVER":"WIN!", 52, 250);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("Pressione [R] para reiniciar ou [ESC] p/ Menu", 35, 274);
        }
    }
    private void drawMiniTetromino(Graphics2D g, Tetromino t, int baseX, int baseY, int cellSize) {
        g.setColor(COLORS.getOrDefault(t.getTipo(), Color.LIGHT_GRAY));
        int refX = t.getPosicoes().stream().mapToInt(Posicao::getX).min().orElse(0);
        int refY = t.getPosicoes().stream().mapToInt(Posicao::getY).min().orElse(0);
        for (Posicao p : t.getPosicoes()) {
            g.fillRoundRect(baseX + (p.getX()-refX)*cellSize, baseY + (p.getY()-refY)*cellSize, cellSize, cellSize, 3, 3);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
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
    @Override
    public void keyReleased(KeyEvent e) {}
}
