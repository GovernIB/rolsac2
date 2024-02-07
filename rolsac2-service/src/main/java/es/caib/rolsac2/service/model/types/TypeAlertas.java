package es.caib.rolsac2.service.model.types;

/**
 * Enumerado de tipo alertas (AVISOS O ALERTAS)
 *
 * @author Indra
 */
public enum TypeAlertas {

    ALERTAS(TypeAlertas.ALERTAS_VALOR), AVISOS(TypeAlertas.AVISOS_VALOR);

    public static final String ALERTAS_VALOR = "ALE";
    public static final String AVISOS_VALOR = "AVI";
    /**
     * Label.
     **/
    private String valor;


    /**
     * Constructor.
     **/
    private TypeAlertas(final String label) {
        this.valor = label;
    }

    /**
     * Obtiene enumerado a partir de texto.
     *
     * @param
     * @return
     */
    public static TypeAlertas fromString(final String label) {
        TypeAlertas tipo = null;
        for (final TypeAlertas type : TypeAlertas.values()) {
            if (type.toString().equals(label)) {
                tipo = type;
                break;
            }
        }
        return tipo;
    }

    /**
     * Convierte enum a String
     */
    @Override
    public String toString() {
        return valor;
    }
}
