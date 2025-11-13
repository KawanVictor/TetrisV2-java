package src.domain;

public class ModoSprint extends ModoPartida {
    private final int objetivoLinhas;
    public ModoSprint(int linhas) {
        super("Sprint");
        this.objetivoLinhas = linhas;
    }
    @Override
    public boolean acabou(Partida partida) {
        return partida.isGameOver() || partida.getLinhasEliminadas() >= objetivoLinhas;
    }
}
