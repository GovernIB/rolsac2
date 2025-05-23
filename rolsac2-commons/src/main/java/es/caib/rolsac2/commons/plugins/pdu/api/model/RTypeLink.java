package es.caib.rolsac2.commons.plugins.pdu.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Tipo de Link.
 *
 * @author Indra
 *
 */

public enum RTypeLink {
    /**
     * Information (Código String: Information).
     */
    Information("Information"),
    /**
     * Procedure (Código String: Procedure).
     */
    Procedure("Procedure");


    /**
     * Valor como string.
     */
    private final String valor;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private RTypeLink(final String value) {
        valor = value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    @JsonValue
    public String toString() {
        return valor;
    }

    /**
     * Método para From string de la clase TypeDocumento.
     *
     * @param text
     *            Parámetro text
     * @return el type documento
     */
    public static RTypeLink fromString(final String text) {
        RTypeLink respuesta = null;
        if (text != null) {
            for (final RTypeLink b : RTypeLink.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;
    }
}
