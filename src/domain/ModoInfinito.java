package src.domain;

public class ModoInfinito extends ModoPartida {
    public ModoInfinito() { super("Infinito"); }
    @Override
    public boolean acabou(Partida partida) {
        return partida.isGameOver();
    }
}
