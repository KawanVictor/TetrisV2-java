package service;

import domain.Partida;

public class HoldManager {
    public static boolean ativarHold(Partida partida) {
        return partida.ativarHold();
    }
}
