package src.util;

public class TimerUtil {
    private final long limiteMillis;
    private long inicio;

    public TimerUtil(int limiteSegundos) {
        this.limiteMillis = limiteSegundos * 1000L;
        this.inicio = System.currentTimeMillis();
    }

    public boolean acabou() {
        return (System.currentTimeMillis() - inicio) >= limiteMillis;
    }

    public long restante() {
        long decorrido = System.currentTimeMillis() - inicio;
        return Math.max(0, limiteMillis - decorrido);
    }

    public void reiniciar() {
        this.inicio = System.currentTimeMillis();
    }
}
