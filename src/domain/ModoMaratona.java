package src.domain;

public class ModoMaratona extends ModoPartida {
    public ModoMaratona() { super("Maratona"); }
    @Override
    public boolean acabou(Partida partida) {
        return partida.isGameOver() || partida.getNivel() >= 15;
    }
}
