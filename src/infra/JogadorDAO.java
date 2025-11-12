package src.infra;

import src.domain.Jogador;
import java.sql.*;

public class JogadorDAO {
    public static void salvarJogador(Jogador jogador) {
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT OR REPLACE INTO Jogador (nome, pontuacaoMaxima, totalLinhas, totalJogos, nivelMax) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, jogador.getNome());
            stmt.setInt(2, jogador.getPontuacaoMaxima());
            stmt.setInt(3, jogador.getTotalLinhasEliminadas());
            stmt.setInt(4, jogador.getTotalJogos());
            stmt.setInt(5, jogador.getNivelMaximoAlcancado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Jogador buscarJogador(String nome) {
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Jogador WHERE nome = ?")) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            Jogador jogador = null;
            if (rs.next()) {
                jogador = new Jogador(nome);
                jogador.setPontuacaoMaxima(rs.getInt("pontuacaoMaxima"));
                jogador.adicionarLinhasEliminadas(rs.getInt("totalLinhas"));
                for (int i = 0; i < rs.getInt("totalJogos"); i++) jogador.incrementarJogos();
                jogador.atualizarNivelMaximo(rs.getInt("nivelMax"));
            }
            rs.close();
            return jogador;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
