package es.caib.rolsac2.service.model.types;

import java.util.HashMap;
import java.util.Map;

public enum TypeIdiomaOpcional {

    INGLES("en"),

    ALEMAN("de"),

    FRANCES("fr"),

    ITALIANO("it");



    /** Valor */
    private static final Map<String, TypeIdiomaOpcional> BY_VALOR = new HashMap<>();


    static {
        for (TypeIdiomaOpcional idioma: values()) {
            BY_VALOR.put(idioma.valor, idioma);
        }
    }

    public final String valor;


    TypeIdiomaOpcional(String valor) {
        this.valor = valor;
    }

    /**
     * Convierte un string en enumerado.
     *
     * @param text
     * @return
     */
    public static TypeIdiomaOpcional fromString(final String text) {
        return BY_VALOR.get(text);
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
