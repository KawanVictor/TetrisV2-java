package src.domain;

import java.util.Random;

public class Partida {
    private final ModoPartida modo;
    private final Tabuleiro tabuleiro;
    private Tetromino tetrominoAtual, proximoTetromino, holdTetromino;
    private boolean holdDisponivel;
    private int pontuacao, nivel, linhasEliminadas;
    private long ultimaQuedaTimestamp, intervaloQuedaMillis;
    private boolean gameOver;
    private final Random random = new Random();
    private final long startTime;

    public Partida(ModoPartida modo) {
        this.modo = modo;
        this.tabuleiro = new Tabuleiro();
        this.proximoTetromino = criarTetrominoAleatorio();
        this.tetrominoAtual = criarTetrominoAleatorio();
        this.holdTetromino = null;
        this.holdDisponivel = true;
        this.pontuacao = 0;
        this.nivel = 1;
        this.linhasEliminadas = 0;
        this.intervaloQuedaMillis = calcularIntervaloQueda();
        this.ultimaQuedaTimestamp = System.currentTimeMillis();
        this.gameOver = false;
        this.startTime = System.currentTimeMillis();
    }

    private Tetromino criarTetrominoAleatorio() {
        Tetromino.Tipo[] tipos = Tetromino.Tipo.values();
        return new Tetromino(tipos[random.nextInt(tipos.length)]);
    }

    public void atualizar() {
        if (gameOver) return;
        long agora = System.currentTimeMillis();
        if (agora - ultimaQuedaTimestamp > intervaloQuedaMillis) {
            boolean caiu = moverPeca(0, 1);
            if (!caiu) {
                tabuleiro.colocarPeca(tetrominoAtual);
                int linhas = tabuleiro.removerLinhasCompletas();
                if (linhas > 0) {
                    pontuacao += SistemaPontuacao.calcularPontos(linhas, nivel);
                    linhasEliminadas += linhas;
                    atualizarNivel();
                    holdDisponivel = true;
                }
                tetrominoAtual = proximoTetromino;
                proximoTetromino = criarTetrominoAleatorio();
                if (!tabuleiro.posicaoValida(tetrominoAtual)) gameOver = true;
            }
            ultimaQuedaTimestamp = agora;
        }
    }

    public boolean moverPeca(int dx, int dy) {
        Tetromino tentativa = tetrominoAtual.clonar();
        tentativa.mover(dx, dy);
        if (tabuleiro.posicaoValida(tentativa)) {
            tetrominoAtual = tentativa;
            return true;
        }
        return false;
    }

    public void rotacionarPeca() {
        Tetromino tentativa = tetrominoAtual.clonar();
        tentativa.rotacionar();
        if (tabuleiro.posicaoValida(tentativa)) tetrominoAtual = tentativa;
    }

    public boolean ativarHold() {
        if (!holdDisponivel) return false;
        if (holdTetromino == null) {
            holdTetromino = tetrominoAtual;
            tetrominoAtual = proximoTetromino;
            proximoTetromino = criarTetrominoAleatorio();
        } else {
            Tetromino temp = tetrominoAtual;
            tetrominoAtual = holdTetromino;
            holdTetromino = temp;
        }
        holdDisponivel = false;
        return true;
    }

    public Tetromino calcularGhostPiece() {
        Tetromino ghost = tetrominoAtual.clonar();
        while (tabuleiro.posicaoValida(ghost)) ghost.mover(0, 1);
        ghost.mover(0, -1);
        return ghost;
    }

    private void atualizarNivel() {
        int novoNivel = linhasEliminadas / 10 + 1;
        if (novoNivel > nivel) {
            nivel = novoNivel;
            intervaloQuedaMillis = calcularIntervaloQueda();
        }
    }

    private long calcularIntervaloQueda() {
        return Math.max(1000 - (nivel - 1) * 100, 100);
    }

    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public Tetromino getTetrominoAtual() { return tetrominoAtual; }
    public Tetromino getProximoTetromino() { return proximoTetromino; }
    public Tetromino getHoldTetromino() { return holdTetromino; }

    public int getPontuacao() { return pontuacao; }
    public int getNivel() { return nivel; }
    public int getLinhasEliminadas() { return linhasEliminadas; }
    public boolean isGameOver() { return gameOver; }
    public int getSegundosJogando() {
        return (int)((System.currentTimeMillis() - startTime)/1000);
    }
}
