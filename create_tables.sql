-- Banco: tetrisdb (PostgreSQL)
--   CREATE DATABASE tetrisdb;
-- A aplicação cria o banco e estas tabelas automaticamente na primeira conexão;
-- este script serve para criação manual.

CREATE TABLE IF NOT EXISTS jogadores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    melhor_pontuacao INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS partida (
    id SERIAL PRIMARY KEY,
    jogador VARCHAR(100) NOT NULL,
    pontuacao INTEGER NOT NULL DEFAULT 0,
    nivel INTEGER NOT NULL DEFAULT 1,
    linhas INTEGER NOT NULL DEFAULT 0,
    gameover BOOLEAN NOT NULL DEFAULT FALSE,
    data_partida TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS configuracao (
    chave VARCHAR(50) PRIMARY KEY,
    valor VARCHAR(100) NOT NULL
);
