package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabuleiroTest {
    private Tabuleiro tabuleiro;

    @BeforeEach
    void criarTabuleiro() {
        tabuleiro = new Tabuleiro();
    }

    private void preencherLinha(int y) {
        Arrays.fill(tabuleiro.getGrid()[y], Tetromino.Tipo.I);
    }

    private Tetromino pecaO(int dx, int dy) {
        Tetromino t = new Tetromino(Tetromino.Tipo.O);
        t.mover(dx, dy);
        return t;
    }

    @Test
    void tabuleiroComecaVazio() {
        for (Tetromino.Tipo[] linha : tabuleiro.getGrid())
            for (Tetromino.Tipo celula : linha) assertNull(celula);
    }

    @Test
    void posicaoDentroDoTabuleiroVazioEhValida() {
        assertTrue(tabuleiro.posicaoValida(pecaO(0, 0)));
        assertTrue(tabuleiro.posicaoValida(pecaO(-3, 0)), "encostada na parede esquerda");
        assertTrue(tabuleiro.posicaoValida(pecaO(5, 0)), "encostada na parede direita");
        assertTrue(tabuleiro.posicaoValida(pecaO(0, 18)), "encostada no chão");
    }

    @Test
    void posicaoForaDoTabuleiroEhInvalida() {
        assertFalse(tabuleiro.posicaoValida(pecaO(-4, 0)), "além da parede esquerda");
        assertFalse(tabuleiro.posicaoValida(pecaO(6, 0)), "além da parede direita");
        assertFalse(tabuleiro.posicaoValida(pecaO(0, 19)), "abaixo do chão");
        assertFalse(tabuleiro.posicaoValida(pecaO(0, -1)), "acima do topo");
    }

    @Test
    void pecaColocadaOcupaAsCelulasEBloqueiaOutras() {
        Tetromino peca = pecaO(0, 18);
        tabuleiro.colocarPeca(peca);

        assertEquals(Tetromino.Tipo.O, tabuleiro.getGrid()[18][3]);
        assertEquals(Tetromino.Tipo.O, tabuleiro.getGrid()[19][4]);
        assertFalse(tabuleiro.posicaoValida(peca));
        assertFalse(tabuleiro.posicaoValida(pecaO(1, 17)), "sobreposição parcial");
        assertTrue(tabuleiro.posicaoValida(pecaO(0, 16)), "logo acima");
    }

    @Test
    void linhaIncompletaNaoEhRemovida() {
        preencherLinha(19);
        tabuleiro.getGrid()[19][5] = null;

        assertEquals(0, tabuleiro.removerLinhasCompletas());
        assertNotNull(tabuleiro.getGrid()[19][0]);
    }

    @Test
    void linhaCompletaEhRemovidaEOQueEstaAcimaDesce() {
        preencherLinha(19);
        tabuleiro.getGrid()[18][0] = Tetromino.Tipo.T;

        assertEquals(1, tabuleiro.removerLinhasCompletas());
        assertEquals(Tetromino.Tipo.T, tabuleiro.getGrid()[19][0]);
        assertNull(tabuleiro.getGrid()[19][1]);
        assertNull(tabuleiro.getGrid()[18][0]);
    }

    @Test
    void variasLinhasCompletasSaoRemovidasDeUmaVez() {
        for (int y = 16; y <= 19; y++) preencherLinha(y);
        tabuleiro.getGrid()[15][7] = Tetromino.Tipo.S;

        assertEquals(4, tabuleiro.removerLinhasCompletas());
        assertEquals(Tetromino.Tipo.S, tabuleiro.getGrid()[19][7]);
        assertNull(tabuleiro.getGrid()[15][7]);
    }

    @Test
    void linhasCompletasSeparadasPorUmaIncompleta() {
        preencherLinha(19);
        tabuleiro.getGrid()[18][2] = Tetromino.Tipo.J;
        preencherLinha(17);

        assertEquals(2, tabuleiro.removerLinhasCompletas());
        assertEquals(Tetromino.Tipo.J, tabuleiro.getGrid()[19][2]);
        assertNull(tabuleiro.getGrid()[19][0]);
        assertNull(tabuleiro.getGrid()[18][2]);
    }

    @Test
    void limparEsvaziaOTabuleiro() {
        preencherLinha(10);
        tabuleiro.limpar();
        assertNull(tabuleiro.getGrid()[10][0]);
    }
}
