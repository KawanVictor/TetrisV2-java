package src.service;

import src.domain.Partida;

public class MultiplayerManager {
    private final Partida[] partidas;
    private final InputManager[] inputManagers;

    public MultiplayerManager(int qtdJogadores) {
        partidas = new Partida[qtdJogadores];
        inputManagers = new InputManager[qtdJogadores];
        for (int i = 0; i < qtdJogadores; i++) {
            partidas[i] = new Partida();
            inputManagers[i] = new InputManager(partidas[i]);
        }
    }

    public void processarEntrada(int jogador, char comando) {
        if (jogador >= 0 && jogador < partidas.length) {
            inputManagers[jogador].processarEntrada(comando);
        }
    }
    public void atualizarTodos() { for (Partida p : partidas) p.atualizar(); }
    public Partida getPartida(int jogador) { return partidas[jogador]; }
}
