package src.ui;

import javax.swing.*;
import java.awt.*;

public class TetrisFrame extends JFrame {
    public TetrisFrame() {
        setTitle("Tetris Java PRO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(420, 700);
        setLocationRelativeTo(null);
        setJMenuBar(criarMenuBar());
        trocarPainel(new MenuPrincipal(this));
    }

    public void trocarPainel(JPanel novoPainel) {
        getContentPane().removeAll();
        add(novoPainel, BorderLayout.CENTER);
        revalidate();
        repaint();
        novoPainel.requestFocusInWindow();
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem salvar = new JMenuItem("Salvar Pontuação");
        JMenuItem ranking = new JMenuItem("Ver Ranking");
        JMenuItem sair = new JMenuItem("Sair");

        salvar.addActionListener(e -> {
            Component painel = getContentPane().getComponent(0);
            if(painel instanceof TetrisPanel tetrisPanel) {
                int pontuacao = tetrisPanel.getPontuacaoAtual();
                String nome = JOptionPane.showInputDialog(this, "Digite seu nome:");
                if (nome != null && !nome.isEmpty()) {
                    try {
                        new src.infra.JogadorDAO().salvarJogador(nome, pontuacao);
                        JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Abra um jogo para salvar!");
            }
        });

        ranking.addActionListener(e -> {
            try {
                String msg = new src.infra.JogadorDAO().obterRanking();
                JOptionPane.showMessageDialog(this, msg, "Ranking", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao consultar ranking: " + ex.getMessage());
            }
        });

        sair.addActionListener(e -> System.exit(0));
        menuArquivo.add(salvar);
        menuArquivo.add(ranking);
        menuArquivo.addSeparator();
        menuArquivo.add(sair);
        menuBar.add(menuArquivo);
        return menuBar;
    }
}
