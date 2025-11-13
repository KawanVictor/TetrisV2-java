package src.ui;

import src.domain.ModoMaratona;
import src.domain.ModoInfinito;
import src.domain.ModoTempo;
import src.domain.ModoSprint;
import src.domain.ModoZen;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JPanel {
    public MenuPrincipal(TetrisFrame frame) {
        setLayout(new GridLayout(0, 1, 10, 10));
        setBackground(Color.BLACK);

        JLabel titulo = new JLabel("Selecione o Modo de Jogo", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        add(titulo);

        JButton btnMaratona = new JButton("Maratona");
        JButton btnInfinito = new JButton("Infinito");
        JButton btnTempo = new JButton("Tempo (3min)");
        JButton btnSprint = new JButton("Sprint (40 linhas)");
        JButton btnZen = new JButton("Zen");

        btnMaratona.addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoMaratona())));
        btnInfinito.addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoInfinito())));
        btnTempo.addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoTempo(180))));
        btnSprint.addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoSprint(40))));
        btnZen.addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoZen())));

        add(btnMaratona);
        add(btnInfinito);
        add(btnTempo);
        add(btnSprint);
        add(btnZen);
    }
}
