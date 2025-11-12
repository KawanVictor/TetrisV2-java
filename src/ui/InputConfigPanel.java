package src.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputConfigPanel extends JPanel {
    public InputConfigPanel() {
        setLayout(new GridLayout(6, 2));
        add(new JLabel("Mover Esquerda:")); add(new JTextField(1));
        add(new JLabel("Mover Direita:")); add(new JTextField(1));
        add(new JLabel("Girar:")); add(new JTextField(1));
        add(new JLabel("Descer:")); add(new JTextField(1));
        add(new JLabel("Hold:")); add(new JTextField(1));
        JButton salvar = new JButton("Salvar");
        add(salvar);
        salvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(InputConfigPanel.this, "Configurações salvas.");
            }
        });
    }
}
