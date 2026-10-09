package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ModoPartidaTest {

    /** Partida com estado fixo, para testar só a condição de término de cada modo. */
    private static class PartidaFalsa extends Partida {
        boolean gameOver;
        int nivel = 1, linhas, segundos;

        PartidaFalsa() { super(new ModoZen()); }
        @Override public boolean isGameOver() { return gameOver; }
        @Override public int getNivel() { return nivel; }
        @Override public int getLinhasEliminadas() { return linhas; }
        @Override public int getSegundosJogando() { return segundos; }
    }

    private final PartidaFalsa partida = new PartidaFalsa();

    @Test
    void maratonaTerminaNoNivel15() {
        ModoPartida modo = new ModoMaratona();
        partida.nivel = 14;
        assertFalse(modo.acabou(partida));
        partida.nivel = 15;
        assertTrue(modo.acabou(partida));
    }

    @Test
    void sprintTerminaAoAtingirOObjetivoDeLinhas() {
        ModoPartida modo = new ModoSprint(40);
        partida.linhas = 39;
        assertFalse(modo.acabou(partida));
        partida.linhas = 40;
        assertTrue(modo.acabou(partida));
    }

    @Test
    void tempoTerminaQuandoOLimiteEhAtingido() {
        ModoPartida modo = new ModoTempo(180);
        partida.segundos = 179;
        assertFalse(modo.acabou(partida));
        partida.segundos = 180;
        assertTrue(modo.acabou(partida));
    }

    @Test
    void infinitoSoTerminaComGameOver() {
        ModoPartida modo = new ModoInfinito();
        partida.nivel = 99;
        partida.linhas = 999;
        partida.segundos = 9999;
        assertFalse(modo.acabou(partida));
        partida.gameOver = true;
        assertTrue(modo.acabou(partida));
    }

    @Test
    void gameOverEncerraMaratonaSprintETempo() {
        partida.gameOver = true;
        assertTrue(new ModoMaratona().acabou(partida));
        assertTrue(new ModoSprint(40).acabou(partida));
        assertTrue(new ModoTempo(180).acabou(partida));
    }

    @Test
    void zenNaoTemCondicaoDeTermino() {
        partida.nivel = 99;
        partida.linhas = 999;
        assertFalse(new ModoZen().acabou(partida));
    }

    @Test
    void cadaModoTemSeuNome() {
        assertEquals("Maratona", new ModoMaratona().getNome());
        assertEquals("Infinito", new ModoInfinito().getNome());
        assertEquals("Tempo", new ModoTempo(180).getNome());
        assertEquals("Sprint", new ModoSprint(40).getNome());
        assertEquals("Zen", new ModoZen().getNome());
    }
}
