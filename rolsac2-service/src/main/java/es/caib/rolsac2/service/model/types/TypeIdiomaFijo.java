package es.caib.rolsac2.service.model.types;

import java.util.HashMap;
import java.util.Map;

public enum TypeIdiomaFijo {

    CATALAN("ca"),

    CASTELLANO("es");



    /** Valor */
    private static final Map<String, TypeIdiomaFijo> BY_VALOR = new HashMap<>();


    static {
        for (TypeIdiomaFijo idioma: values()) {
            BY_VALOR.put(idioma.valor, idioma);
        }
    }

    public final String valor;


    TypeIdiomaFijo(String valor) {
        this.valor = valor;
    }

    /**
     * Convierte un string en enumerado.
     *
     * @param text
     * @return
     */
    public static TypeIdiomaFijo fromString(final String text) {
        return BY_VALOR.get(text);
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
