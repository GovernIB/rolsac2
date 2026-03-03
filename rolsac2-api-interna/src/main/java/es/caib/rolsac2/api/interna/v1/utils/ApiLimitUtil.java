package es.caib.rolsac2.api.interna.v1.utils;

import java.util.List;

/**
 * Utility class to limit the size of returning lists in the internal API.
 */
public class ApiLimitUtil {

    private ApiLimitUtil() {
        // Sonar
    }

    /**
     * Limita el número de elementos de una lista según el límite indicado.
     * Si limit <= 0, no se aplica ningún límite.
     *
     * @param lista La lista a limitar
     * @param limit El límite máximo
     * @param <T>   El tipo de elementos en la lista
     * @return La lista limitada, o la original si no aplica el límite o la lista es nula
     */
    public static <T> List<T> limitList(List<T> lista, int limit) {
        if (lista == null || limit <= 0 || lista.size() <= limit) {
            return lista;
        }
        return lista.subList(0, limit);
    }
}