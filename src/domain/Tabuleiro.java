package src.domain;

import java.util.Arrays;

public class Tabuleiro {
    public static final int LARGURA = 10, ALTURA = 20;
    private final Tetromino.Tipo[][] grid = new Tetromino.Tipo[ALTURA][LARGURA];

    public Tabuleiro() { limpar(); }

    public void limpar() {
        for (int y = 0; y < ALTURA; y++)
            Arrays.fill(grid[y], null);
    }

    public boolean posicaoValida(Tetromino t) {
        for (Posicao p : t.getPosicoes()) {
            int x = p.getX(), y = p.getY();
            if (x < 0 || x >= LARGURA || y < 0 || y >= ALTURA) return false;
            if (grid[y][x] != null) return false;
        }
        return true;
    }

    public void colocarPeca(Tetromino t) {
        for (Posicao p : t.getPosicoes()) {
            int x = p.getX(), y = p.getY();
            if (y >= 0 && y < ALTURA && x >= 0 && x < LARGURA)
                grid[y][x] = t.getTipo();
        }
    }

    public int removerLinhasCompletas() {
        int linhasRemovidas = 0;
        for (int y = ALTURA - 1; y >= 0; y--) {
            if (linhaCompleta(y)) {
                removerLinha(y);
                linhasRemovidas++;
                y++; // reanalisa linha após descida
            }
        }
        return linhasRemovidas;
    }

    private boolean linhaCompleta(int y) {
        for (int x = 0; x < LARGURA; x++) {
            if (grid[y][x] == null) return false;
        }
        return true;
    }

    private void removerLinha(int y) {
        for (int linha = y; linha > 0; linha--) {
            for (int x = 0; x < LARGURA; x++)
                grid[linha][x] = grid[linha - 1][x];
        }
        Arrays.fill(grid[0], null);
    }

    public Tetromino.Tipo[][] getGrid() { return grid; }
}
