package service;

import static org.junit.jupiter.api.Assertions.*;

import domain.ModoInfinito;
import domain.Partida;
import domain.Posicao;
import domain.Tabuleiro;
import domain.Tetromino;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class GhostPieceCalculatorTest {
    private final Partida partida = new Partida(new ModoInfinito());

    private static int maxY(Tetromino t) {
        return t.getPosicoes().stream().mapToInt(Posicao::getY).max().getAsInt();
    }

    private static List<Integer> colunas(Tetromino t) {
        return t.getPosicoes().stream().map(Posicao::getX).sorted().collect(Collectors.toList());
    }

    @Test
    void ghostCaiAteOChaoNoTabuleiroVazio() {
        Tetromino ghost = GhostPieceCalculator.calcularGhost(partida);
        assertEquals(Tabuleiro.ALTURA - 1, maxY(ghost));
    }

    @Test
    void ghostParaSobreBlocosJaColocados() {
        Arrays.fill(partida.getTabuleiro().getGrid()[12], 0, 9, Tetromino.Tipo.I);
        Tetromino ghost = GhostPieceCalculator.calcularGhost(partida);
        assertEquals(11, maxY(ghost));
    }

    @Test
    void ghostTemMesmoTipoEMesmasColunasDaPecaAtual() {
        partida.moverPeca(2, 0);
        Tetromino ghost = GhostPieceCalculator.calcularGhost(partida);
        assertEquals(partida.getTetrominoAtual().getTipo(), ghost.getTipo());
        assertEquals(colunas(partida.getTetrominoAtual()), colunas(ghost));
    }

    @Test
    void calcularGhostNaoMoveAPecaAtual() {
        int antes = maxY(partida.getTetrominoAtual());
        GhostPieceCalculator.calcularGhost(partida);
        assertEquals(antes, maxY(partida.getTetrominoAtual()));
    }
}
