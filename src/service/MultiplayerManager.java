package src.service;

import src.domain.ModoPartida;
import src.domain.Partida;

public class MultiplayerManager {
    private final Partida[] partidas;
    private final InputManager[] inputManagers;

    public MultiplayerManager(ModoPartida[] modos) {
        partidas = new Partida[modos.length];
        inputManagers = new InputManager[modos.length];
        for (int i = 0; i < modos.length; i++) {
            partidas[i] = new Partida(modos[i]);
            inputManagers[i] = new InputManager(partidas[i]);
        }
    }

    public void processarEntrada(int jogador, char comando) {
        if (jogador >= 0 && jogador < partidas.length) {
            inputManagers[jogador].processarEntrada(comando);
        }
    }
    public void atualizarTodos() {
        for (Partida p : partidas) p.atualizar();
    }
    public Partida getPartida(int jogador) {
        return partidas[jogador];
    }
}
