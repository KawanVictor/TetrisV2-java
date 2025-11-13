package src.domain;

public class ModoZen extends ModoPartida {
    public ModoZen() { super("Zen"); }
    @Override
    public boolean acabou(Partida partida) { return false; }
}
