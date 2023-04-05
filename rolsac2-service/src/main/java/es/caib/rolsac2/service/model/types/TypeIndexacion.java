package es.caib.rolsac2.service.model.types;

/**
 * Tipos de indexacion
 */
public enum TypeIndexacion {

    /**
     * <P>PROCEDIMIENTOS</P>
     **/
    PROCEDIMIENTO("PRO"),
    /**
     * <P>SERVICIO</P>
     **/
    SERVICIO("SER"),
    /**
     * <P>NORMATIVA</P>
     **/
    NORMATIVA("NOR"),
    /**
     * <P>UNIDAD ADMINISTRATIVA</P>
     **/
    UNIDAD_ADMINISTRATIVA("UNA"),
    ;

    String tipo;

    TypeIndexacion(String itipo) {
        tipo = itipo;
    }

    public static TypeIndexacion fromString(String iTipo) {
        TypeIndexacion tipo = null;
        if (iTipo != null) {
            for (TypeIndexacion typeRol : TypeIndexacion.values()) {
                if (typeRol.toString().equals(iTipo)) {
                    tipo = typeRol;
                    break;
                }
            }
        }
        return tipo;
    }

    public String toString() {
        return tipo;
    }
}
