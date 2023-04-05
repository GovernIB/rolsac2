package es.caib.rolsac2.service.model.types;

import java.util.HashMap;
import java.util.Map;

public enum TypeEstadoDir3 {
    EXISTE_MODIFICADA("EM"),

    NUEVO("NU"),

    ELIMINADO("EL"),

    PADRE_CAMBIO("PC");


    /**
     * Valor
     */
    private static final Map<String, TypeEstadoDir3> BY_VALOR = new HashMap<>();


    static {
        for (TypeEstadoDir3 proceso : values()) {
            BY_VALOR.put(proceso.valor, proceso);
        }
    }

    public final String valor;


    TypeEstadoDir3(String valor) {
        this.valor = valor;
    }

    /**
     * Convierte un string en enumerado.
     *
     * @param text
     * @return
     */
    public static TypeEstadoDir3 fromString(final String text) {
        return BY_VALOR.get(text);
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
