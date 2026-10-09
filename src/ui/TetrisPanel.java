package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import domain.*;
import service.GhostPieceCalculator;

public class TetrisPanel extends JPanel implements KeyListener {
    private final Partida partida;
    private final ModoPartida modo;
    private final Timer timer;
    private static final int GRID_WIDTH = 10, GRID_HEIGHT = 20;
    private static final int PANEL_MARGIN = 38, SIDE_MARGIN = 80, FOOTER = 60;
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
        setPreferredSize(new Dimension(GRID_WIDTH*28 + SIDE_MARGIN*2, GRID_HEIGHT*28 + PANEL_MARGIN + FOOTER));
        setBackground(new Color(18, 19, 32));
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(36, e -> {
            if (!acabou()) partida.atualizar();
            repaint();
        });
        timer.start();
    }

    private boolean acabou() {
        return partida.isGameOver() || modo.acabou(partida);
    }

    @Override
    public void removeNotify() {
        timer.stop();
        super.removeNotify();
    }

    @Override
    public void paintComponent(Graphics gOrig) {
        super.paintComponent(gOrig);
        Graphics2D g = (Graphics2D) gOrig;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tamanho da célula acompanha o tamanho da janela
        int cell = Math.max(8, Math.min((getHeight() - PANEL_MARGIN - FOOTER) / GRID_HEIGHT,
                                        (getWidth() - SIDE_MARGIN*2) / GRID_WIDTH));
        int inset = Math.max(1, cell / 8);
        int totalWidth = GRID_WIDTH*cell, totalHeight = GRID_HEIGHT*cell, panelW = getWidth();
        int x0 = (panelW-totalWidth)/2;

        g.setColor(new Color(45, 50, 70,130));
        g.fillRoundRect(x0-9, PANEL_MARGIN-9, totalWidth+18, totalHeight+18, 30, 30);

        g.setColor(new Color(32, 36, 56));
        g.fillRoundRect(x0, PANEL_MARGIN, totalWidth, totalHeight, 18, 18);

        var grid = partida.getTabuleiro().getGrid();
        for (int y = 0; y < GRID_HEIGHT; y++)
            for (int x = 0; x < GRID_WIDTH; x++) {
                Tetromino.Tipo t = grid[y][x];
                if (t != null) {
                    Color color = COLORS.getOrDefault(t, Color.LIGHT_GRAY);
                    g.setColor(color.darker());
                    g.fillRoundRect(x0+x * cell, PANEL_MARGIN+y * cell, cell, cell, 10, 10);
                    g.setColor(color);
                    g.fill3DRect(x0+x * cell+inset, PANEL_MARGIN+y * cell+inset, cell-inset*2, cell-inset*2, true);
                }
            }
        Tetromino ghost = GhostPieceCalculator.calcularGhost(partida);
        g.setColor(new Color(220, 220, 255, 45));
        for (Posicao p : ghost.getPosicoes())
            g.fillRoundRect(x0+p.getX()*cell, PANEL_MARGIN+p.getY()*cell, cell, cell,8,8);
        Tetromino.Tipo tipo = partida.getTetrominoAtual().getTipo();
        for (Posicao p : partida.getTetrominoAtual().getPosicoes()) {
            Color c = COLORS.getOrDefault(tipo, Color.GREEN);
            g.setColor(c);
            g.fill3DRect(x0+p.getX()*cell+inset, PANEL_MARGIN+p.getY()*cell+inset, cell-inset*2, cell-inset*2, true);
            g.setColor(c.darker());
            g.drawRoundRect(x0+p.getX()*cell, PANEL_MARGIN+p.getY()*cell, cell, cell, 8,8);
        }
        g.setFont(new Font("JetBrains Mono", Font.BOLD, 24));
        g.setColor(new Color(255,255,255,230));
        g.drawString("TETRIS", x0+8, 26);

        int rodapeY = PANEL_MARGIN + totalHeight + 14;
        g.setColor(new Color(55, 80, 170, 170));
        g.fillRoundRect(x0+totalWidth-128, rodapeY, 128, 34, 14, 14);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        if (modo.getNome().equals("Tempo")) {
            g.drawString("Tempo: " + formatSeconds(partida.getSegundosJogando()), x0+totalWidth-120, rodapeY+22);
        } else {
            g.drawString("Level: "+partida.getNivel(), x0+totalWidth-120, rodapeY+22);
        }
        g.drawString("Pontuação: "+ partida.getPontuacao(), x0+8, rodapeY+14);
        g.drawString("Linhas: "+ partida.getLinhasEliminadas(), x0+8, rodapeY+34);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.setColor(new Color(240,240,240));
        g.drawString("HOLD", x0-70, PANEL_MARGIN+22);
        if (partida.getHoldTetromino() != null)
            drawMiniTetromino(g, partida.getHoldTetromino(), x0-70, PANEL_MARGIN+34, 14);
        g.drawString("NEXT", x0+totalWidth+14, PANEL_MARGIN+22);
        drawMiniTetromino(g, partida.getProximoTetromino(), x0+totalWidth+14, PANEL_MARGIN+34, 14);
        if (acabou()) {
            int centroY = PANEL_MARGIN + totalHeight/2;
            g.setColor(new Color(10, 10, 20, 190));
            g.fillRoundRect(x0, centroY-60, totalWidth, 110, 18, 18);
            g.setColor(new Color(255,0,36,225));
            g.setFont(new Font("Arial", Font.BOLD, 36));
            drawCentralizado(g, partida.isGameOver()?"GAME OVER":"WIN!", x0, totalWidth, centroY-10);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.setColor(Color.WHITE);
            drawCentralizado(g, "[R] reiniciar   [ESC] menu", x0, totalWidth, centroY+26);
        }
    }

    private void drawCentralizado(Graphics2D g, String texto, int x0, int largura, int y) {
        g.drawString(texto, x0 + (largura - g.getFontMetrics().stringWidth(texto))/2, y);
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
        TetrisFrame frame = (TetrisFrame) SwingUtilities.getWindowAncestor(this);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            frame.trocarPainel(new MenuPrincipal(frame));
            return;
        }
        if (acabou()) {
            if (e.getKeyCode() == KeyEvent.VK_R) frame.trocarPainel(new TetrisPanel(modo));
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
    public int getPontuacaoAtual() {
        return partida.getPontuacao();
    }
}
