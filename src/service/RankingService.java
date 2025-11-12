package src.service;

import src.domain.Jogador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingService {
    private final List<Jogador> ranking = new ArrayList<>();
    public void adicionarJogador(Jogador jogador) {
        ranking.add(jogador);
        ranking.sort(Comparator.comparingInt(Jogador::getPontuacaoMaxima).reversed());
    }
    public List<Jogador> getRanking() { return ranking; }
}
