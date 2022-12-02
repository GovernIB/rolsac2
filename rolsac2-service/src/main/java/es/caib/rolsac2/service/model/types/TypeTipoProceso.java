package es.caib.rolsac2.service.model.types;

import java.util.HashMap;
import java.util.Map;

public enum TypeTipoProceso {

    TEST("TEST");



    /** Valor */
    private static final Map<String, TypeTipoProceso> BY_VALOR = new HashMap<>();


    static {
        for (TypeTipoProceso proceso: values()) {
            BY_VALOR.put(proceso.valor, proceso);
        }
    }

    public final String valor;


    TypeTipoProceso(String valor) {
        this.valor = valor;
    }

    /**
     * Convierte un string en enumerado.
     *
     * @param text
     * @return
     */
    public static TypeTipoProceso fromString(final String text) {
        return BY_VALOR.get(text);
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
