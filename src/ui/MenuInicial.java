package src.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuInicial extends JPanel {
    public MenuInicial(ActionListener onStart, ActionListener onRanking, ActionListener onOpcoes) {
        setLayout(new GridLayout(3, 1, 20, 20));
        JButton jogarBtn = new JButton("Iniciar Jogo");
        JButton rankingBtn = new JButton("Ranking");
        JButton opcoesBtn = new JButton("Opções");
        add(jogarBtn); add(rankingBtn); add(opcoesBtn);
        jogarBtn.addActionListener(onStart);
        rankingBtn.addActionListener(onRanking);
        opcoesBtn.addActionListener(onOpcoes);
    }
}
