package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String HOST = env("TETRIS_DB_HOST", "localhost");
    private static final String PORT = env("TETRIS_DB_PORT", "5432");
    private static final String DATABASE = env("TETRIS_DB_NAME", "tetrisdb");
    private static final String USER = env("TETRIS_DB_USER", "postgres");
    private static final String PASSWORD = env("TETRIS_DB_PASSWORD", "123");

    private static final String BANCO_INEXISTENTE = "3D000";
    private static boolean esquemaCriado = false;

    public static synchronized Connection getConnection() throws SQLException {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url(DATABASE), USER, PASSWORD);
        } catch (SQLException e) {
            if (!BANCO_INEXISTENTE.equals(e.getSQLState())) throw e;
            criarBanco();
            conn = DriverManager.getConnection(url(DATABASE), USER, PASSWORD);
        }
        if (!esquemaCriado) {
            try {
                criarTabelas(conn);
            } catch (SQLException e) {
                conn.close();
                throw e;
            }
            esquemaCriado = true;
        }
        return conn;
    }

    private static void criarBanco() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url("postgres"), USER, PASSWORD);
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE DATABASE \"" + DATABASE.replace("\"", "\"\"") + "\"");
        }
    }

    // Mesmas tabelas de create_tables.sql
    private static void criarTabelas(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS jogadores (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL UNIQUE, " +
                    "melhor_pontuacao INTEGER NOT NULL DEFAULT 0)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS partida (" +
                    "id SERIAL PRIMARY KEY, " +
                    "jogador VARCHAR(100) NOT NULL, " +
                    "pontuacao INTEGER NOT NULL DEFAULT 0, " +
                    "nivel INTEGER NOT NULL DEFAULT 1, " +
                    "linhas INTEGER NOT NULL DEFAULT 0, " +
                    "gameover BOOLEAN NOT NULL DEFAULT FALSE, " +
                    "data_partida TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS configuracao (" +
                    "chave VARCHAR(50) PRIMARY KEY, " +
                    "valor VARCHAR(100) NOT NULL)");
        }
    }

    private static String url(String banco) {
        return "jdbc:postgresql://" + HOST + ":" + PORT + "/" + banco;
    }

    private static String env(String nome, String padrao) {
        String valor = System.getenv(nome);
        return valor == null || valor.isEmpty() ? padrao : valor;
    }
}
