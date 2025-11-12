package src.domain;

public class ModoMaratona extends ModoPartida {
    private final int MAX_NIVEL;
    public ModoMaratona(int maxNivel) {
        super("Maratona");
        this.MAX_NIVEL = maxNivel;
    }
    @Override
    public boolean acabou(Partida partida) {
        return partida.isGameOver() || partida.getNivel() >= MAX_NIVEL;
    }
}
