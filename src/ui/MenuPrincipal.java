package src.ui;

import java.awt.*;
import javax.swing.*;
import src.domain.*;

public class MenuPrincipal extends JPanel {
    public MenuPrincipal(TetrisFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(14, 15, 26));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("Selecione o Modo de Jogo");
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setForeground(Color.WHITE);
        gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(18,0,20,0);
        add(titulo, gbc);

        JPanel botoes = new JPanel(new GridLayout(0,1, 0, 13));
        botoes.setOpaque(false);
        String[] modos = {"Maratona", "Infinito", "Tempo (3min)", "Sprint (40 linhas)", "Zen"};
        JButton[] btns = new JButton[modos.length];
        for(int i=0;i<modos.length;i++) {
            btns[i] = new JButton(modos[i]);
            btns[i].setFont(new Font("Arial", Font.BOLD, 18));
            btns[i].setBackground(new Color(48,54,98));
            btns[i].setForeground(new Color(230,230,255));
            btns[i].setFocusPainted(false);
            btns[i].setBorder(BorderFactory.createRaisedBevelBorder());
            final int idx = i;
            btns[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) { btns[idx].setBackground(new Color(80,110,230)); }
                public void mouseExited(java.awt.event.MouseEvent evt) { btns[idx].setBackground(new Color(48,54,98)); }
            });
            botoes.add(btns[i]);
        }
        btns[0].addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoMaratona())));
        btns[1].addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoInfinito())));
        btns[2].addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoTempo(180))));
        btns[3].addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoSprint(40))));
        btns[4].addActionListener(e -> frame.trocarPainel(new TetrisPanel(new ModoZen())));
        gbc.gridx=0; gbc.gridy=1; gbc.insets=new Insets(0,0,0,0);
        add(botoes, gbc);
    }
}
