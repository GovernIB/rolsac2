package es.caib.rolsac2.commons.plugins.indexacion.api.model.types;


/**
 * Id campos indexación no almacenados.
 *
 * @author Indra
 */
public enum EnumIndexFieldNoStored {

    /**
     * Search text (añadir idioma).
     */
    SEARCHTEXT_IDIOMA("searchText_"),
    /**
     * Search text optional (añadir idioma).
     */
    SEARCHTEXTOPTIONAL_IDIOMA("searchTextOptional_");

    /**
     * Id campo.
     **/
    private String campoId;

    /**
     * Constructor de la clase.
     *
     * @param id id aplicacion en formato texto.
     */
    EnumIndexFieldNoStored(final String id) {
        campoId = id;
    }

    /**
     * Devuelve el enumerado según el valor de texto.
     *
     * @param text Valor en formato String
     * @return Id aplicacion.
     */
    public static EnumIndexFieldNoStored fromString(final String text) {
        EnumIndexFieldNoStored respuesta = null;
        if (text != null) {
            for (final EnumIndexFieldNoStored b : EnumIndexFieldNoStored.values()) {
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
        return campoId;
    }
}
