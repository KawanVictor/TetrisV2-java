package infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracaoDao {
    public static void salvarTema(String tema) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO configuracao (chave, valor) VALUES (?, ?) " +
                "ON CONFLICT(chave) DO UPDATE SET valor = EXCLUDED.valor")) {
            stmt.setString(1, "tema");
            stmt.setString(2, tema);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String carregarTema() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT valor FROM configuracao WHERE chave = ?")) {
            stmt.setString(1, "tema");
            ResultSet rs = stmt.executeQuery();
            String tema = rs.next() ? rs.getString("valor") : "claro";
            rs.close();
            return tema;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
