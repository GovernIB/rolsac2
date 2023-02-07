package es.caib.rolsac2.service.model.types;

/**
 * Tipos de rol
 */
public enum TypeProcedimientoEstado {


    /**
     * <P>ESTADO MODIFICACON</P>
     **/
    MODIFICACION("M"),

    /**
     * <P>ESTADO MODIFICACIÓN PENDIENTE SUBIR</P>
     */
    PENDIENTE_PUBLICAR("S"),
    /**
     * <P>PENDIENTE RESERVAR</P>
     */
    PENDIENTE_RESERVAR("T"),
    /**
     * <P>PENDIENTE RESERVAR</P>
     */
    PENDIENTE_BORRAR("U"),
    /**
     * <P>PUBLICADO</P>
     */
    PUBLICADO("P"),
    /**
     * <P>BORRADO</P>
     */
    BORRADO("B"),

    /**
     * <P>RESERVA</P>
     */
    RESERVA("R");

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

    public static TypeProcedimientoWorkflow getWorkflowSegunEstado(TypeProcedimientoEstado estado) {
        if (estado == null) {
            return null;
        }

        switch (estado) {
            case MODIFICACION:
            case PENDIENTE_PUBLICAR:
                return TypeProcedimientoWorkflow.MODIFICACION;
            case BORRADO:
            case PUBLICADO:
            case RESERVA:
                return TypeProcedimientoWorkflow.PUBLICADO;
            default:
                return null;
        }
    }


    public TypeProcedimientoWorkflow getWorkflowSegunEstado() {

        switch (this) {
            case MODIFICACION:
            case PENDIENTE_PUBLICAR:
                return TypeProcedimientoWorkflow.MODIFICACION;
            case BORRADO:
            case PUBLICADO:
            case RESERVA:
                return TypeProcedimientoWorkflow.PUBLICADO;
            default:
                return null;
        }
    }

    public boolean mismoWorkflow(TypeProcedimientoEstado estado1, TypeProcedimientoEstado estado2) {
        if (estado1 == null || estado2 == null) {
            return false;
        }

        TypeProcedimientoWorkflow wf1 = estado1.getWorkflowSegunEstado();
        TypeProcedimientoWorkflow wf2 = estado2.getWorkflowSegunEstado();
        return wf1 == wf2;
    }

    public static boolean distintoWorkflow(TypeProcedimientoEstado estado1, TypeProcedimientoEstado estado2) {
        if (estado1 == null || estado2 == null) {
            return true;
        }

        TypeProcedimientoWorkflow wf1 = estado1.getWorkflowSegunEstado();
        TypeProcedimientoWorkflow wf2 = estado2.getWorkflowSegunEstado();
        return wf1 != wf2;
    }

    public String toString() {
        return perfil;
    }

    /**
     * Es estado pendiente
     **/
    public boolean isEstadoPendiente() {
        return this == TypeProcedimientoEstado.PENDIENTE_BORRAR ||
                this == TypeProcedimientoEstado.PENDIENTE_PUBLICAR ||
                this == TypeProcedimientoEstado.PENDIENTE_RESERVAR;
    }

    public String getLiteralMensajePendiente(String idioma) {
        return "es.caib.rolsac2.procServ.accion." + perfil + "." + idioma;
    }
}
