package es.caib.rolsac2.service.model.orden;

/**
 * Campos orden.
 **/
public enum ProcesoOrden implements AbstractOrden {

    CODIGO("P.codigo");

    /**
     * Valor.
     **/
    private final String valor;

    /**
     * Constructor.
     */
    private ProcesoOrden(final String iValor) {
        valor = iValor;
    }

    /**
     * Obtiene enumerado a partir de texto.
     *
     * @param valor
     * @return
     */
    public static ProcesoOrden fromString(final String valor) {
        ProcesoOrden tipo = null;
        if (valor != null) {
            for (final ProcesoOrden type : ProcesoOrden.values()) {
                if (type.toString().contentEquals(valor)) {
                    tipo = type;
                    break;
                }
            }
        }
        return tipo;
    }

    @Override
    public String toString() {
        return valor;
    }
}
