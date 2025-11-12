package src.service;

import src.domain.Partida;

public class JogoTetrisService {
    private Partida partida;
    private InputManager inputManager;

    public JogoTetrisService() {
        partida = new Partida();
        inputManager = new InputManager(partida);
    }

    public void atualizar() { partida.atualizar(); }
    public void processarEntrada(char comando) { inputManager.processarEntrada(comando); }
    public Partida getPartida() { return partida; }
}
