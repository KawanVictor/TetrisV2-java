package src.ui;

import src.service.TemaService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemaSelectorPanel extends JPanel {
    public TemaSelectorPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Selecione o Tema Visual:"));
        String[] temas = {"claro", "escuro", "colorido"};
        JComboBox<String> comboTema = new JComboBox<>(temas);
        comboTema.setSelectedItem(TemaService.getTemaAtual());
        add(comboTema);
        JButton aplicarBtn = new JButton("Aplicar");
        add(aplicarBtn);

        aplicarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TemaService.salvarTema((String)comboTema.getSelectedItem());
                JOptionPane.showMessageDialog(TemaSelectorPanel.this, "Tema aplicado!");
            }
        });
    }
}
