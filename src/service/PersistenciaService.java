package service;

import domain.Jogador;
import domain.Partida;
import infra.JogadorDAO;
import infra.PartidaDAO;
import java.sql.SQLException;

public class PersistenciaService {
    public static void salvarJogador(Jogador jogador) {
        try {
            new JogadorDAO().salvarJogador(jogador.getNome(), jogador.getPontuacaoMaxima());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Jogador buscarJogador(String nome) {
        try {
            return new JogadorDAO().buscarJogador(nome);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void salvarPartida(Partida partida, String nomeJogador) { PartidaDAO.salvarPartida(partida, nomeJogador); }
}
