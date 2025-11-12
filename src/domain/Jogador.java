package src.domain;

public class Jogador {
    private final String nome;
    private int pontuacaoMaxima;
    private int totalLinhasEliminadas;
    private int totalJogos;
    private int nivelMaximoAlcancado;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacaoMaxima = 0;
        this.totalLinhasEliminadas = 0;
        this.totalJogos = 0;
        this.nivelMaximoAlcancado = 1;
    }
    public String getNome() { return nome; }
    public int getPontuacaoMaxima() { return pontuacaoMaxima; }
    public void setPontuacaoMaxima(int valor) {
        if (valor > pontuacaoMaxima) this.pontuacaoMaxima = valor;
    }
    public int getTotalLinhasEliminadas() { return totalLinhasEliminadas; }
    public void adicionarLinhasEliminadas(int linhas) { this.totalLinhasEliminadas += linhas; }
    public int getTotalJogos() { return totalJogos; }
    public void incrementarJogos() { this.totalJogos++; }
    public int getNivelMaximoAlcancado() { return nivelMaximoAlcancado; }
    public void atualizarNivelMaximo(int nivel) {
        if (nivel > this.nivelMaximoAlcancado) this.nivelMaximoAlcancado = nivel;
    }
}
