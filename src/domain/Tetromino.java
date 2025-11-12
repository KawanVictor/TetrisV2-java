package src.domain;

import java.util.ArrayList;
import java.util.List;

public class Tetromino {
    public enum Tipo { I, O, T, S, Z, J, L }

    // Cada peça possui 4 rotações — até as simétricas!
    public static final int[][][][] FORMAS = {
        // I
        {
            {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}},
            {{0,0,1,0},{0,0,1,0},{0,0,1,0},{0,0,1,0}},
            {{0,0,0,0},{0,0,0,0},{1,1,1,1},{0,0,0,0}},
            {{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}}
        },
        // O (quadrado): todas as rotações iguais
        {
            {{1,1},{1,1}},
            {{1,1},{1,1}},
            {{1,1},{1,1}},
            {{1,1},{1,1}}
        },
        // T
        {
            {{0,1,0},{1,1,1},{0,0,0}},
            {{0,1,0},{0,1,1},{0,1,0}},
            {{0,0,0},{1,1,1},{0,1,0}},
            {{0,1,0},{1,1,0},{0,1,0}}
        },
        // S
        {
            {{0,1,1},{1,1,0},{0,0,0}},
            {{0,1,0},{0,1,1},{0,0,1}},
            {{0,0,0},{0,1,1},{1,1,0}},
            {{1,0,0},{1,1,0},{0,1,0}}
        },
        // Z
        {
            {{1,1,0},{0,1,1},{0,0,0}},
            {{0,0,1},{0,1,1},{0,1,0}},
            {{0,0,0},{1,1,0},{0,1,1}},
            {{0,1,0},{1,1,0},{1,0,0}}
        },
        // J
        {
            {{1,0,0},{1,1,1},{0,0,0}},
            {{0,1,1},{0,1,0},{0,1,0}},
            {{0,0,0},{1,1,1},{0,0,1}},
            {{0,1,0},{0,1,0},{1,1,0}}
        },
        // L
        {
            {{0,0,1},{1,1,1},{0,0,0}},
            {{0,1,0},{0,1,0},{0,1,1}},
            {{0,0,0},{1,1,1},{1,0,0}},
            {{1,1,0},{0,1,0},{0,1,0}}
        }
    };

    private final Tipo tipo;
    private int rotacao;
    private int posX, posY;
    private List<Posicao> blocos;

    public Tetromino(Tipo tipo) {
        this.tipo = tipo;
        this.rotacao = 0;
        this.posX = 3;
        this.posY = 0;
        atualizarBlocos();
    }

    private void atualizarBlocos() {
        blocos = new ArrayList<>();
        int[][] forma = FORMAS[tipo.ordinal()][rotacao];
        for (int y = 0; y < forma.length; y++) {
            for (int x = 0; x < forma[y].length; x++) {
                if (forma[y][x] == 1) {
                    blocos.add(new Posicao(posX + x, posY + y));
                }
            }
        }
    }

    public void mover(int dx, int dy) {
        posX += dx;
        posY += dy;
        atualizarBlocos();
    }

    public void rotacionar() {
        rotacao = (rotacao + 1) % 4;
        atualizarBlocos();
    }

    public List<Posicao> getPosicoes() { return blocos; }
    public Tipo getTipo() { return tipo; }

    public Tetromino clonar() {
        Tetromino clone = new Tetromino(this.tipo);
        clone.rotacao = this.rotacao;
        clone.posX = this.posX;
        clone.posY = this.posY;
        clone.atualizarBlocos();
        return clone;
    }
}
