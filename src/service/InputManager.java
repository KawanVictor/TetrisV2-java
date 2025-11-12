package src.service;

import src.domain.Partida;

public class InputManager {
    private final Partida partida;

    public InputManager(Partida partida) {
        this.partida = partida;
    }

    public void processarEntrada(char comando) {
        switch (comando) {
            case 'a': partida.moverPeca(-1, 0); break;
            case 'd': partida.moverPeca(1, 0);  break;
            case 's': partida.moverPeca(0, 1);  break;
            case 'w': partida.rotacionarPeca(); break;
            case 'h': partida.ativarHold();     break;
        }
    }
}
