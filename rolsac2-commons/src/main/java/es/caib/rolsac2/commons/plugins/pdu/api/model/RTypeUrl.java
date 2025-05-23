package es.caib.rolsac2.commons.plugins.pdu.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Tipo de URL.
 *
 * @author Indra
 *
 */
public enum RTypeUrl {

    /**
     * Webpage (Código String: Web page).
     */
    Webpage("Web page"),
    /**
     * Procedure (Código String: Procedure).
     */
    Webfolder("Web folder");


    /**
     * Valor como string.
     */
    private final String valor;

    /** Constructor.
     * @param value valor como String
     * */
    private RTypeUrl(final String value) {
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
     * Método para From string de la clase TypeUrl.
     *
     * @param text
     *            Parámetro text
     * @return el type url
     */
    public static RTypeUrl fromString(final String text) {
        RTypeUrl respuesta = null;
        if (text != null) {
            for (final RTypeUrl b : RTypeUrl.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;
    }
}
