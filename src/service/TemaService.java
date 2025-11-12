package src.service;

import src.infra.ConfiguracaoDao;

public class TemaService {
    private static String temaAtual = "claro";

    public static void carregarTema() {
        temaAtual = ConfiguracaoDao.carregarTema();
    }

    public static void salvarTema(String tema) {
        temaAtual = tema;
        ConfiguracaoDao.salvarTema(tema);
    }

    public static String getTemaAtual() {
        return temaAtual;
    }
}
