package es.caib.rolsac2.service.model.types;

/**
 * Tipos de rol
 */
public enum TypeProcedimientoEstado {


    /**
     * <P>ESTADO MODIFICACON</P>
     **/
    MODIFICACION("M");

    String perfil;

    TypeProcedimientoEstado(String iPerfil) {
        perfil = iPerfil;
    }

    public static TypeProcedimientoEstado fromString(String iPerfil) {
        TypeProcedimientoEstado tipo = null;
        for (TypeProcedimientoEstado typeRol : TypeProcedimientoEstado.values()) {
            if (typeRol.toString().equals(iPerfil)) {
                tipo = typeRol;
                break;
            }
        }
        return tipo;
    }

    public String toString() {
        return perfil;
    }
}
