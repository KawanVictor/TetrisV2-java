package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SistemaPontuacaoTest {

    @ParameterizedTest(name = "{0} linha(s) no nível {1} = {2} pontos")
    @CsvSource({
        "1, 1, 100",
        "2, 1, 300",
        "3, 1, 500",
        "4, 1, 800",
        "1, 5, 500",
        "4, 3, 2400",
        "0, 7, 0",
        "5, 1, 0"
    })
    void calculaPontosPorLinhasENivel(int linhas, int nivel, int esperado) {
        assertEquals(esperado, SistemaPontuacao.calcularPontos(linhas, nivel));
    }
}
