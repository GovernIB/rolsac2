package es.caib.rolsac2.commons.plugins.indexacion.api.model.types;


/**
 * Id campos indexación almacenados.
 * <p>
 * Los multiidioma deben acabar en "_IDIOMA"
 *
 * @author Indra
 */
public enum EnumIndexFieldStored {

    /**
     * Id.
     */
    ID("id"),
    /**
     * Categoria.
     */
    CATEGORIA("categoria"),
    /**
     * Categoria.
     */
    ID_PADRE("idPadre"),
    /**
     * Categoria padre.
     */
    CATEGORIA_PADRE("padreCategoria"),
    /**
     * Aplicacion id.
     */
    ID_APLICACION("aplicacionId"),
    /**
     * Titulo (añadir idioma).
     */
    TITULO_IDIOMA("titulo_"),
    /**
     * Descripcion (añadir idioma).
     */
    DESCRIPCION_IDIOMA("descripcion_"),
    /**
     * Descripcion padre (añadir idioma).
     */
    DESCRIPCION_PADRE_IDIOMA("padreDescripcion_"),
    /**
     * Url (añadir idioma).
     */
    URL_IDIOMA("url_"),
    /**
     * Url padre (añadir idioma).
     */
    URL_PADRE_IDIOMA("padreUrl_"),
    /**
     * Extension (añadir idioma).
     */
    EXTENSION_IDIOMA("extension_"),
    /**
     * Extension ingles.
     */
    FOTO_URL("fotoUrl"),
    /**
     * Path UO.
     */
    PATH_UO("pathUo"),
    /**
     * Id familia.
     */
    ID_FAMILIA("familiaId"),
    /**
     * Id materia.
     */
    ID_MATERIA("materiaId"),
    /**
     * Id publico.
     */
    ID_PUBLICO("publicoId"),
    /**
     * Telematico.
     */
    TELEMATICO("telematico"),
    /**
     * Interno.
     */
    INTERNO("interno"),
    /**
     * Fecha actualizacion.
     */
    FECHA_ACTUALIZACION("fechaActualizacion"),
    /**
     * Fecha publicacion.
     */
    FECHA_PUBLICACION("fechaPublicacion"),
    /**
     * Fecha caducidad.
     */
    FECHA_CADUCIDAD("fechaCaducidad"),
    /**
     * Fecha plazo inicio.
     */
    FECHA_PLAZO_INICIO("fechaPlazoIni"),
    /**
     * Fecha plazo fin.
     */
    FECHA_PLAZO_FIN("fechaPlazoFin"),
    /**
     * Id familia.
     */
    ID_RAIZ("idRaiz"),
    /**
     * Id familia.
     */
    CATEGORIA_RAIZ("categoriaRaiz");

    /**
     * Id campo.
     **/
    private String campoId;

    /**
     * Constructor de la clase.
     *
     * @param iAplicacionId id aplicacion en formato texto.
     */
    EnumIndexFieldStored(final String iAplicacionId) {
        campoId = iAplicacionId;
    }

    /**
     * Devuelve el enumerado según el valor de texto.
     *
     * @param text Valor en formato String
     * @return Id aplicacion.
     */
    public static EnumIndexFieldStored fromString(final String text) {
        EnumIndexFieldStored respuesta = null;
        if (text != null) {
            for (final EnumIndexFieldStored b : EnumIndexFieldStored.values()) {
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
