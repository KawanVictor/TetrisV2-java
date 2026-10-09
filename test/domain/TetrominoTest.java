package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class TetrominoTest {

    static Set<String> coords(Tetromino t) {
        return t.getPosicoes().stream().map(p -> p.getX() + "," + p.getY()).collect(Collectors.toSet());
    }

    @ParameterizedTest
    @EnumSource(Tetromino.Tipo.class)
    void todaPecaTemQuatroBlocosEmQualquerRotacao(Tetromino.Tipo tipo) {
        Tetromino t = new Tetromino(tipo);
        for (int rotacao = 0; rotacao < 4; rotacao++) {
            assertEquals(4, coords(t).size(), tipo + " na rotação " + rotacao);
            t.rotacionar();
        }
    }

    @ParameterizedTest
    @EnumSource(Tetromino.Tipo.class)
    void quatroRotacoesVoltamAoFormatoOriginal(Tetromino.Tipo tipo) {
        Tetromino t = new Tetromino(tipo);
        Set<String> original = coords(t);
        for (int i = 0; i < 4; i++) t.rotacionar();
        assertEquals(original, coords(t));
    }

    @Test
    void pecaNasceNoTopoDoTabuleiro() {
        assertEquals(Set.of("3,0", "4,0", "3,1", "4,1"), coords(new Tetromino(Tetromino.Tipo.O)));
        assertEquals(Set.of("3,1", "4,1", "5,1", "6,1"), coords(new Tetromino(Tetromino.Tipo.I)));
    }

    @Test
    void moverDeslocaTodosOsBlocos() {
        Tetromino t = new Tetromino(Tetromino.Tipo.O);
        t.mover(2, 5);
        assertEquals(Set.of("5,5", "6,5", "5,6", "6,6"), coords(t));
    }

    @Test
    void rotacionarMudaOFormatoDaPecaT() {
        Tetromino t = new Tetromino(Tetromino.Tipo.T);
        Set<String> antes = coords(t);
        t.rotacionar();
        assertNotEquals(antes, coords(t));
    }

    @Test
    void cloneEhIndependenteDoOriginal() {
        Tetromino original = new Tetromino(Tetromino.Tipo.L);
        original.rotacionar();
        original.mover(1, 3);
        Tetromino clone = original.clonar();
        assertEquals(coords(original), coords(clone));
        assertEquals(original.getTipo(), clone.getTipo());

        Set<String> antes = coords(original);
        clone.mover(0, 1);
        clone.rotacionar();
        assertEquals(antes, coords(original));
    }
}
