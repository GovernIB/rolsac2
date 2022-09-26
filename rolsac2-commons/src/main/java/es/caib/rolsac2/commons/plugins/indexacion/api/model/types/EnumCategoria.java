package es.caib.rolsac2.commons.plugins.indexacion.api.model.types;

/**
 * Tipos de categoria permitidos.
 *
 * @author Indra
 */
public enum EnumCategoria {
    /*** ROLSAC. **/
    /**
     * ROLSAC FICHA.
     **/
    ROLSAC_FICHA("FCH"),
    /**
     * ROLSAC FICHA DOCUMENTO.
     **/
    ROLSAC_FICHA_DOCUMENTO("DFC"),
    /**
     * ROLSAC PROCEDIMIENTO.
     **/
    ROLSAC_PROCEDIMIENTO("PRO"),
    /**
     * ROLSAC PROCEDIMIENTO DOCUMENTO.
     **/
    ROLSAC_PROCEDIMIENTO_DOCUMENTO("DPR"),
    /**
     * ROLSAC SERVICIO.
     **/
    ROLSAC_SERVICIO("SER"),
    /**
     * ROLSAC SERVICIO DOCUMENTO.
     **/
    ROLSAC_SERVICIO_DOCUMENTO("DSR"),
    /**
     * ROLSAC NORMATIVA.
     **/
    ROLSAC_NORMATIVA("NOR"),
    /**
     * ROLSAC NORMATIVA.
     **/
    ROLSAC_NORMATIVA_DOCUMENTO("DNO"),
    /**
     * ROLSAC UNIDAD ADMINISTRATIVA.
     **/
    ROLSAC_UNIDAD_ADMINISTRATIVA("UNA"),
    /**
     * ROLSAC TRAMITE.
     **/
    ROLSAC_TRAMITE("TRA"),
    /**
     * ROLSAC TRAMITE DOCUMENTO.
     **/
    ROLSAC_TRAMITE_DOCUMENTO("DTR"),
    /** GUSITE. **/
    /**
     * GUSITE MICROSITE.
     **/
    GUSITE_MICROSITE("MIC"),
    /**
     * GUSITE CONTENIDO.
     **/
    GUSITE_CONTENIDO("CON"),
    /**
     * GUSITE AGENDA.
     **/
    GUSITE_AGENDA("AGE"),
    /**
     * GUSITE ENCUESTA.
     **/
    GUSITE_ENCUESTA("ENC"),
    /**
     * GUSITE FAQ.
     **/
    GUSITE_FAQ("FAQ"),
    /**
     * GUSITE NOTICIA.
     **/
    GUSITE_NOTICIA("NTC"),
    /**
     * GUSITE ARCHIVO.
     **/
    GUSITE_ARCHIVO("ARC"),
    /**
     * PIDIP_NOTICIAS
     **/
    PIDIP_NOTICIAS("PNT"),
    /**
     * PIDIP COMUNICADOS.
     **/
    PIDIP_COMUNICADOS("PCM"),
    /**
     * PIDIP VIDEOTECA.
     **/
    PIDIP_VIDEOTECA("PVD"),
    /**
     * PIDIP DISCURSOS.
     **/
    PIDIP_DISCURSOS("PDS");

    /**
     * Valor de la categoria.
     **/
    private String categoria;

    /**
     * Constructor de la clase
     *
     * @param iCategoria id categoria en formato texto
     */
    EnumCategoria(final String iCategoria) {
        categoria = iCategoria;
    }

    /**
     * Devuelve el enumerado según el valor de categoria.
     *
     * @param text id categoria en formato texto
     * @return id categoria.
     */
    public static EnumCategoria fromString(final String text) {
        EnumCategoria respuesta = null;
        if (text != null) {
            for (final EnumCategoria b : EnumCategoria.values()) {
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
        return categoria;
    }
}
