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
    PENDIENTE_PUBLICAR("PV"),
    /**
     * <P>PENDIENTE RESERVAR</P>
     */
    PENDIENTE_CERRAR("PT"),
    /**
     * <P>PUBLICADO</P>
     */
    PUBLICADO("P"),
    /**
     * <P>CERRAR</P>
     */
    CERRADO("T");

    final String valor;

    TypeProcedimientoEstado(String iValor) {
        valor = iValor;
    }

    public static TypeProcedimientoEstado fromString(String iValor) {
        TypeProcedimientoEstado tipo = null;
        for (TypeProcedimientoEstado typeRol : TypeProcedimientoEstado.values()) {
            if (typeRol.toString().equals(iValor)) {
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
            case PUBLICADO:
            case PENDIENTE_CERRAR:
            case CERRADO:
                return TypeProcedimientoWorkflow.DEFINITIVO;
            default:
                return null;
        }
    }


    public TypeProcedimientoWorkflow getWorkflowSegunEstado() {

        switch (this) {
            case MODIFICACION:
            case PENDIENTE_PUBLICAR:
                return TypeProcedimientoWorkflow.MODIFICACION;
            case PUBLICADO:
            case CERRADO:
            case PENDIENTE_CERRAR:
                return TypeProcedimientoWorkflow.DEFINITIVO;
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
        return valor;
    }

    /**
     * Es estado pendiente
     **/
    public boolean isEstadoPendiente() {
        return this == TypeProcedimientoEstado.PENDIENTE_CERRAR || this == TypeProcedimientoEstado.PENDIENTE_PUBLICAR;
    }

    public String getLiteralMensajePendiente(String idioma) {
        return "es.caib.rolsac2.procServ.accion." + valor + "." + idioma;
    }

    public boolean isEstadoValidacionPDU() {
        return this == TypeProcedimientoEstado.PENDIENTE_PUBLICAR || this == TypeProcedimientoEstado.PUBLICADO || this == TypeProcedimientoEstado.CERRADO;
    }

    public boolean isEstadoValidacionPDUparaIndexar() {
        return this == TypeProcedimientoEstado.PUBLICADO || this == TypeProcedimientoEstado.CERRADO;
    }
}
