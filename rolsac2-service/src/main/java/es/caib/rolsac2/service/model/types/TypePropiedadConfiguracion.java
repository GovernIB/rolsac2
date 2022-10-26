package es.caib.rolsac2.service.model.types;

import java.util.HashMap;
import java.util.Map;

public enum TypePropiedadConfiguracion {
    PATH_FICHEROS_EXTERNOS("ficherosExternos.path");


    /** Valor */
    private static final Map<String, TypePropiedadConfiguracion> BY_VALOR = new HashMap<>();


    static {
        for (TypePropiedadConfiguracion propiedadConfiguracion: values()) {
            BY_VALOR.put(propiedadConfiguracion.valor, propiedadConfiguracion);
        }
    }

    public final String valor;


    TypePropiedadConfiguracion(String valor) {
        this.valor = valor;
    }

    /**
     * Convierte un string en enumerado.
     *
     * @param text
     * @return
     */
    public static TypePropiedadConfiguracion fromString(final String text) {
        return BY_VALOR.get(text);
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
