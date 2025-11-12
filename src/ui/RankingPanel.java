package src.ui;

import src.domain.Jogador;
import src.service.RankingService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RankingPanel extends JPanel {
    public RankingPanel(RankingService rankingService) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Ranking de Pontuação", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        List<Jogador> ranking = rankingService.getRanking();
        String[] columns = {"Posição", "Nome", "Pontuação"};
        String[][] data = new String[ranking.size()][3];
        for (int i = 0; i < ranking.size(); i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = ranking.get(i).getNome();
            data[i][2] = String.valueOf(ranking.get(i).getPontuacaoMaxima());
        }
        JTable table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
