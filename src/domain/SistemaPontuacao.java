package src.domain;

public class SistemaPontuacao {
    public static int calcularPontos(int linhas, int nivel) {
        switch (linhas) {
            case 1: return 100 * nivel;
            case 2: return 300 * nivel;
            case 3: return 500 * nivel;
            case 4: return 800 * nivel;
            default: return 0;
        }
    }
}
