package es.caib.rolsac2.commons.plugins.pdu.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RTypeLanguage {


    /**
     * ES (Código String: es).
     */
    ES("es"),
    /**
     * EN (Código String: en).
     */
    EN("en");

    private String valor;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private RTypeLanguage(final String value) {
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
     * Método para From string de la clase TypeLanguage.
     *
     * @param text
     *            Parámetro text
     * @return el type language
     */
    public static RTypeLanguage fromString(final String text) {
        RTypeLanguage respuesta = null;
        if (text != null) {
            for (final RTypeLanguage b : RTypeLanguage.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;

    }

}
