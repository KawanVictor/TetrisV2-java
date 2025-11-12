package src.service;

import src.domain.Jogador;
import src.domain.Partida;
import src.infra.JogadorDAO;
import src.infra.PartidaDAO;

public class PersistenciaService {
    public static void salvarJogador(Jogador jogador) { JogadorDAO.salvarJogador(jogador); }
    public static Jogador buscarJogador(String nome) { return JogadorDAO.buscarJogador(nome); }
    public static void salvarPartida(Partida partida, String nomeJogador) { PartidaDAO.salvarPartida(partida, nomeJogador); }
}
