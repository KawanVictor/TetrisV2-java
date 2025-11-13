package src.domain;

public class ModoTempo extends ModoPartida {
    private final int tempoLimiteSegundos;
    public ModoTempo(int tempo) {
        super("Tempo");
        this.tempoLimiteSegundos = tempo;
    }
    @Override
    public boolean acabou(Partida partida) {
        return partida.isGameOver() || partida.getSegundosJogando() >= tempoLimiteSegundos;
    }
}
