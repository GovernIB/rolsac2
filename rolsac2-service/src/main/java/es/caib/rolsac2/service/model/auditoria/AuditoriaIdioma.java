package es.caib.rolsac2.service.model.auditoria;

/**
 * Tipo para indicar idioma.
 *
 * @author Indra
 */
public enum AuditoriaIdioma {
    /**
     * Sin tratamiento idioma (Código String: n).
     */
    NO_IDIOMA("n"),
    /**
     * Castellano (Código String: es).
     */
    CASTELLANO("es"),
    /**
     * Catalan (Código String: ca).
     */
    CATALAN("ca"),
    /**
     * Ingles (codigo String: en)
     */
    INGLES("en"),
    /**
     * Aleman (codigo String : de)
     */
    ALEMAN("de"),
    /**
     * Frances (codigo String : fr)
     */

    FRANCES("fr"),
    /**
     * Italiano (codigo String : it)
     */
    ITALIANO("it");

    /**
     * Valor como string.
     */
    private final String stringValueIdioma;

    /**
     * Constructor.
     *
     * @param valueSiNo Valor como string.
     */
    private AuditoriaIdioma(final String valueSiNo) {
        stringValueIdioma = valueSiNo;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stringValueIdioma;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text string
     * @return TypeSiNo
     */
    public static AuditoriaIdioma fromString(final String text) {
        AuditoriaIdioma respuesta = null;
        if (text != null) {
            for (final AuditoriaIdioma b : AuditoriaIdioma.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

}
