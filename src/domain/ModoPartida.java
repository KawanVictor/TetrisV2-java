package src.domain;

public abstract class ModoPartida {
    private final String nome;
    public ModoPartida(String nome) { this.nome = nome; }
    public String getNome() { return nome; }
    public abstract boolean acabou(Partida partida);
}
