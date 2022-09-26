package es.caib.rolsac2.commons.plugins.indexacion.api.model.types;

/**
 * Id aplicacion permitidos. .
 *
 * @author Indra
 */
public enum EnumAplicacionId {

    /**
     * Rolsac.
     */
    ROLSAC("ROLSAC"),

    /**
     * Gusite.
     */
    GUSITE("GUSITE"),

    /**
     * Pidip.
     **/
    PIDIP("PIDIP");

    /**
     * Id aplicación.
     **/
    private String aplicacionId;

    /**
     * Constructor de la clase.
     *
     * @param iAplicacionId id aplicacion en formato texto.
     */
    EnumAplicacionId(final String iAplicacionId) {
        aplicacionId = iAplicacionId;
    }

    /**
     * Devuelve el enumerado según el valor de texto.
     *
     * @param text Valor en formato String
     * @return Id aplicacion.
     */
    public static EnumAplicacionId fromString(final String text) {
        EnumAplicacionId respuesta = null;
        if (text != null) {
            for (final EnumAplicacionId b : EnumAplicacionId.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;
    }

    @Override
    public String toString() {
        return aplicacionId;
    }
}
