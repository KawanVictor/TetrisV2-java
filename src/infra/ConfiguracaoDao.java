package src.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracaoDao {
    public static void salvarTema(String tema) {
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT OR REPLACE INTO Configuracao (chave, valor) VALUES (?, ?)")) {
            stmt.setString(1, "tema");
            stmt.setString(2, tema);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String carregarTema() {
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT valor FROM Configuracao WHERE chave = ?")) {
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
