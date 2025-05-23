package es.caib.rolsac2.commons.plugins.pdu.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RTypeDelete {
    /**
     * ES (Código String: y).
     */
    YES("y"),
    /**
     * EN (Código String: n).
     */
    NO("n");

    /**
     * Valor.
     */
    private String valor;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private RTypeDelete(final String value) {
        valor = value;
    }

    /**
     * Método para obtener el valor.
     *
     * @return el valor
     */
    public String getValue() {
        return valor;
    }

    /**
     * Método para From string de la clase TypeDelete.
     *
     * @param text
     *            Parámetro text
     * @return el type delete
     */
    public static RTypeDelete fromString(final String text) {
        RTypeDelete respuesta = null;
        if (text != null) {
            for (final RTypeDelete b : RTypeDelete.values()) {
                if (text.equalsIgnoreCase(b.getValue())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;
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

}
