package service;

import domain.Partida;
import domain.Tetromino;

public class GhostPieceCalculator {
    public static Tetromino calcularGhost(Partida partida) {
        Tetromino ghost = partida.getTetrominoAtual().clonar();
        while (partida.getTabuleiro().posicaoValida(ghost)) {
            ghost.mover(0, 1);
        }
        ghost.mover(0, -1);
        return ghost;
    }
}
