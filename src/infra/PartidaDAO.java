package src.infra;

import src.domain.Partida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartidaDAO {
    public static void salvarPartida(Partida partida, String jogadorNome) {
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Partida (jogador, pontuacao, nivel, linhas, gameover) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, jogadorNome);
            stmt.setInt(2, partida.getPontuacao());
            stmt.setInt(3, partida.getNivel());
            stmt.setInt(4, partida.getTabuleiro().removerLinhasCompletas());
            stmt.setBoolean(5, partida.isGameOver());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet listarPartidas() {
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Partida")) {
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
