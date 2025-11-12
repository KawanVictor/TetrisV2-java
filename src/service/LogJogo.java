package src.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogJogo {
    private static final String LOGFILE = "tetris.log";

    public static void logar(String mensagem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGFILE, true))) {
            writer.write(LocalDateTime.now() + " - " + mensagem + "\n");
        } catch (IOException e) {
            System.err.println("Falha ao gravar log: " + e.getMessage());
        }
    }
}
