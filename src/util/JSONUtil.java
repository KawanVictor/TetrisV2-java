package src.util;

public class JSONUtil {
    // Métodos fictícios só para compatibilidade (implementação real depende do formato salvo/carregado)
    public static String toJSON(Object obj) {
        // Retorna uma descrição básica (pode ser extendido para XML, CSV, ou custom)
        return obj.toString();
    }

    public static <T> T fromJSON(String json, Class<T> clazz) {
        // Método não implementado; pode ser removido ou usado para leitura manual. 
        throw new UnsupportedOperationException("Serialização JSON não implementada sem biblioteca externa.");
    }
}
