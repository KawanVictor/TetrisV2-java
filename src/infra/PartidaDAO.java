package infra;

import domain.Partida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {
    public static void salvarPartida(Partida partida, String jogadorNome) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO partida (jogador, pontuacao, nivel, linhas, gameover) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, jogadorNome);
            stmt.setInt(2, partida.getPontuacao());
            stmt.setInt(3, partida.getNivel());
            stmt.setInt(4, partida.getLinhasEliminadas());
            stmt.setBoolean(5, partida.isGameOver());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> listarPartidas() {
        List<String> partidas = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT jogador, pontuacao, nivel, linhas FROM partida ORDER BY data_partida DESC");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                partidas.add(rs.getString("jogador") + " - " + rs.getInt("pontuacao") + " pts, nível "
                        + rs.getInt("nivel") + ", " + rs.getInt("linhas") + " linhas");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return partidas;
    }
}
