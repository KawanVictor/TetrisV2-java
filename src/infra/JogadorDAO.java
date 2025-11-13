package src.infra;

import java.sql.*;

public class JogadorDAO {
    public void salvarJogador(String nome, int pontuacao) throws SQLException {
        String sql = "INSERT INTO jogadores (nome, melhor_pontuacao) VALUES (?, ?) " +
                    "ON CONFLICT(nome) DO UPDATE SET melhor_pontuacao = EXCLUDED.melhor_pontuacao";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setInt(2, pontuacao);
            ps.executeUpdate();
        }
    }
    public String obterRanking() throws SQLException {
        StringBuilder msg = new StringBuilder();
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT nome, melhor_pontuacao FROM jogadores ORDER BY melhor_pontuacao DESC LIMIT 10")) {
            int pos = 1;
            while(rs.next()) {
                msg.append(pos++)
                   .append(" - ")
                   .append(rs.getString("nome"))
                   .append(": ")
                   .append(rs.getInt("melhor_pontuacao"))
                   .append("\n");
            }
        }
        return msg.length() > 0 ? msg.toString() : "Nenhum registro";
    }
}
