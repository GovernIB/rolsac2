package es.caib.rolsac2.commons.plugins.indexacion.api.model.types;

/**
 * Tipos de idioma permitidos.
 *
 * @author Indra
 */
public enum EnumIdiomas {
    /**
     * Castellano.
     */
    CASTELLA("es"),
    /**
     * Catalan.
     */
    CATALA("ca"),
    /**
     * Ingles.
     */
    ANGLES("en");

    /**
     * Idioma.
     **/
    private String idioma;

    /**
     * Constructor de la clase.
     *
     * @param iIdioma id idioma en formato texto.
     */
    EnumIdiomas(final String iIdioma) {
        idioma = iIdioma;
    }

    /**
     * Devuelve el enumerado según el valor de texto.
     *
     * @param text id idioma en formato texto.
     * @return id idioma.
     */
    public static EnumIdiomas fromString(final String text) {
        EnumIdiomas respuesta = null;
        if (text != null) {
            for (final EnumIdiomas b : EnumIdiomas.values()) {
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
        return idioma;
    }
}
