package es.caib.rolsac2.service.model.orden;

public enum PersonalOrden implements AbstractOrden {

    ID("j.id"),
    NOMBRE("j.nombre"),
    USERNAME("j.username"),
    CORREO("j.email");

    /**
     * Valor.
     **/
    private final String valor;

    /**
     * Constructor.
     */
    private PersonalOrden(final String iValor) {
        valor = iValor;
    }

    /**
     * Obtiene enumerado a partir de texto.
     *
     * @param valor
     * @return
     */
    public static PersonalOrden fromString(final String valor) {
        PersonalOrden tipo = null;
        if (valor != null) {
            for (final PersonalOrden type : PersonalOrden.values()) {
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
