package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartidaTest {
    private Partida partida;

    @BeforeEach
    void criarPartida() {
        partida = new Partida(new ModoInfinito());
    }

    private int minX() { return partida.getTetrominoAtual().getPosicoes().stream().mapToInt(Posicao::getX).min().getAsInt(); }
    private int maxX() { return partida.getTetrominoAtual().getPosicoes().stream().mapToInt(Posicao::getX).max().getAsInt(); }
    private int minY() { return partida.getTetrominoAtual().getPosicoes().stream().mapToInt(Posicao::getY).min().getAsInt(); }
    private int maxY() { return partida.getTetrominoAtual().getPosicoes().stream().mapToInt(Posicao::getY).max().getAsInt(); }

    @Test
    void partidaComecaZerada() {
        assertEquals(0, partida.getPontuacao());
        assertEquals(1, partida.getNivel());
        assertEquals(0, partida.getLinhasEliminadas());
        assertFalse(partida.isGameOver());
        assertNotNull(partida.getTetrominoAtual());
        assertNotNull(partida.getProximoTetromino());
        assertNull(partida.getHoldTetromino());
    }

    @Test
    void pecaParaNasParedes() {
        while (partida.moverPeca(-1, 0));
        assertEquals(0, minX());
        assertFalse(partida.moverPeca(-1, 0));

        while (partida.moverPeca(1, 0));
        assertEquals(Tabuleiro.LARGURA - 1, maxX());
        assertFalse(partida.moverPeca(1, 0));
    }

    @Test
    void pecaParaNoChao() {
        while (partida.moverPeca(0, 1));
        assertEquals(Tabuleiro.ALTURA - 1, maxY());
    }

    @Test
    void pecaParaSobreBlocosJaColocados() {
        java.util.Arrays.fill(partida.getTabuleiro().getGrid()[10], 0, 9, Tetromino.Tipo.I);
        while (partida.moverPeca(0, 1));
        assertEquals(9, maxY());
    }

    @Test
    void rotacaoBloqueadaMantemAPeca() {
        // Cerca a peça com blocos em todas as células livres: nenhuma rotação diferente cabe
        Tetromino.Tipo[][] grid = partida.getTabuleiro().getGrid();
        java.util.Set<String> ocupadas = TetrominoTest.coords(partida.getTetrominoAtual());
        for (int y = 0; y < Tabuleiro.ALTURA; y++)
            for (int x = 0; x < Tabuleiro.LARGURA; x++)
                if (!ocupadas.contains(x + "," + y)) grid[y][x] = Tetromino.Tipo.I;

        partida.rotacionarPeca();
        assertEquals(ocupadas, TetrominoTest.coords(partida.getTetrominoAtual()));
    }

    @Test
    void holdGuardaAPecaAtualEPuxaAProxima() {
        Tetromino.Tipo atual = partida.getTetrominoAtual().getTipo();
        Tetromino.Tipo proxima = partida.getProximoTetromino().getTipo();

        assertTrue(partida.ativarHold());
        assertEquals(atual, partida.getHoldTetromino().getTipo());
        assertEquals(proxima, partida.getTetrominoAtual().getTipo());
    }

    @Test
    void holdSoPodeSerUsadoUmaVezPorPeca() {
        assertTrue(partida.ativarHold());
        Tetromino.Tipo guardada = partida.getHoldTetromino().getTipo();

        assertFalse(partida.ativarHold());
        assertEquals(guardada, partida.getHoldTetromino().getTipo());
    }

    @Test
    void pecaGuardadaVoltaParaOTopo() {
        Tetromino.Tipo tipo = partida.getTetrominoAtual().getTipo();
        partida.moverPeca(0, 6);
        partida.rotacionarPeca();
        partida.ativarHold();

        assertEquals(TetrominoTest.coords(new Tetromino(tipo)), TetrominoTest.coords(partida.getHoldTetromino()));
        assertTrue(minY() <= 1, "a peça que entra também começa no topo");
    }
}
