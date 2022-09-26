package es.caib.rolsac2.commons.plugins.indexacion.api.model.types;

/**
 * Los pesos según la categoria.
 *
 * @author Indra
 */
public enum EnumPesoCategoria {
    /*** ROLSAC. **/
    /**
     * ROLSAC FICHA.
     **/
    ROLSAC_FICHA("1.0"),
    /**
     * ROLSAC FICHA DOCUMENTO.
     **/
    ROLSAC_FICHA_DOCUMENTO("0.2"),
    /**
     * ROLSAC PROCEDIMIENTO.
     **/
    ROLSAC_PROCEDIMIENTO("1.0"),
    /**
     * ROLSAC PROCEDIMIENTO DOCUMENTO.
     **/
    ROLSAC_PROCEDIMIENTO_DOCUMENTO("0.2"),
    /**
     * ROLSAC NORMATIVA.
     **/
    ROLSAC_NORMATIVA("1.0"),
    /**
     * ROLSAC NORMATIVA.
     **/
    ROLSAC_NORMATIVA_DOCUMENTO("0.2"),
    /**
     * ROLSAC UNIDAD ADMINISTRATIVA.
     **/
    ROLSAC_UNIDAD_ADMINISTRATIVA("1.0"),
    /**
     * ROLSAC TRAMITE.
     **/
    ROLSAC_TRAMITE("0.6"),
    /**
     * ROLSAC TRAMITE.
     **/
    ROLSAC_TRAMITE_DOCUMENTO("0.2"),
    /** GUSITE. **/
    /**
     * GUSITE CONTENIDO.
     **/
    GUSITE_CONTENIDO("1.0"),
    /**
     * GUSITE MICROSITE.
     **/
    GUSITE_MICROSITE("1.0"), // no se indexa, sólo se utiliza para marcar la categoria del padre.
    /**
     * GUSITE AGENDA.
     **/
    GUSITE_AGENDA("0.2"),
    /**
     * GUSITE ENCUESTA.
     **/
    GUSITE_ENCUESTA("0.2"),
    /**
     * GUSITE FAQ.
     **/
    GUSITE_FAQ("0.2"),
    /**
     * GUSITE NOTICIA.
     **/
    GUSITE_NOTICIA("0.2"),
    /**
     * GUSITE ARCHIVO.
     **/
    GUSITE_ARCHIVO("0.2"),
    /**
     * DESCONOCIDO. NO SE DEBERÍA UTILIZAR.
     **/
    DESCONOCIDO("0.1");

    /**
     * Valor del peso para el boost.
     **/
    private String categoria;

    /**
     * Constructor de la clase
     *
     * @param iCategoria id categoria en formato texto
     */
    EnumPesoCategoria(final String iCategoria) {
        categoria = iCategoria;
    }

    /**
     * Devuelve el enumerado según el valor de categoria.
     *
     * @param text id categoria en formato texto
     * @return id categoria.
     */
    public static EnumPesoCategoria fromString(final String text) {
        EnumPesoCategoria respuesta = null;
        if (text != null) {
            for (final EnumPesoCategoria b : EnumPesoCategoria.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;
    }


    /**
     * Devuelve el enumerado según el valor de categoria.
     *
     * @param text id categoria en formato texto
     * @return id categoria.
     */
    public static EnumPesoCategoria fromCategoria(final EnumCategoria categoria) {
        EnumPesoCategoria respuesta = null;
        switch (categoria) {
            case ROLSAC_FICHA:
                respuesta = EnumPesoCategoria.ROLSAC_FICHA;
                break;
            case ROLSAC_FICHA_DOCUMENTO:
                respuesta = EnumPesoCategoria.ROLSAC_FICHA_DOCUMENTO;
                break;
            case ROLSAC_PROCEDIMIENTO:
                respuesta = EnumPesoCategoria.ROLSAC_PROCEDIMIENTO;
                break;
            case ROLSAC_PROCEDIMIENTO_DOCUMENTO:
                respuesta = EnumPesoCategoria.ROLSAC_PROCEDIMIENTO_DOCUMENTO;
                break;
            case ROLSAC_NORMATIVA:
                respuesta = EnumPesoCategoria.ROLSAC_NORMATIVA;
                break;
            case ROLSAC_NORMATIVA_DOCUMENTO:
                respuesta = EnumPesoCategoria.ROLSAC_NORMATIVA_DOCUMENTO;
                break;
            case ROLSAC_UNIDAD_ADMINISTRATIVA:
                respuesta = EnumPesoCategoria.ROLSAC_UNIDAD_ADMINISTRATIVA;
                break;
            case ROLSAC_TRAMITE:
                respuesta = EnumPesoCategoria.ROLSAC_TRAMITE;
                break;
            case ROLSAC_TRAMITE_DOCUMENTO:
                respuesta = EnumPesoCategoria.ROLSAC_TRAMITE_DOCUMENTO;
                break;
            case GUSITE_MICROSITE:
                respuesta = EnumPesoCategoria.GUSITE_MICROSITE;
                break;
            case GUSITE_CONTENIDO:
                respuesta = EnumPesoCategoria.GUSITE_CONTENIDO;
                break;
            case GUSITE_AGENDA:
                respuesta = EnumPesoCategoria.GUSITE_AGENDA;
                break;
            case GUSITE_ENCUESTA:
                respuesta = EnumPesoCategoria.GUSITE_ENCUESTA;
                break;
            case GUSITE_FAQ:
                respuesta = EnumPesoCategoria.GUSITE_FAQ;
                break;
            case GUSITE_NOTICIA:
                respuesta = EnumPesoCategoria.GUSITE_NOTICIA;
                break;
            case GUSITE_ARCHIVO:
                respuesta = EnumPesoCategoria.GUSITE_ARCHIVO;
                break;
            default:
                respuesta = EnumPesoCategoria.DESCONOCIDO;
                break;
        }
        return respuesta;
    }

    @Override
    public String toString() {
        return categoria;
    }
}
