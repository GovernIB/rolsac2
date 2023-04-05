package es.caib.rolsac2.back.model;

import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;

/**
 * Mensaje con el resultado del estado destino y de los literales.
 *
 * @author Indra
 */
public class RespuestaFlujo {

    private String codigoProcedimiento;
    public String mensajes;

    public TypeProcedimientoEstado estadoDestino;

    /**
     * Indican si se han leido todos los mensajes por parte del gestor o supervisor
     **/
    private boolean pendienteMensajesSupervisor;
    private boolean pendienteMensajesGestor;

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    public TypeProcedimientoEstado getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(TypeProcedimientoEstado estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public boolean isPendienteMensajesSupervisor() {
        return pendienteMensajesSupervisor;
    }

    public void setPendienteMensajesSupervisor(boolean pendienteMensajesSupervisor) {
        this.pendienteMensajesSupervisor = pendienteMensajesSupervisor;
    }

    public boolean isPendienteMensajesGestor() {
        return pendienteMensajesGestor;
    }

    public void setPendienteMensajesGestor(boolean pendienteMensajesGestor) {
        this.pendienteMensajesGestor = pendienteMensajesGestor;
    }

    public String getCodigoProcedimiento() {
        return codigoProcedimiento;
    }

    public void setCodigoProcedimiento(String codigoProcedimiento) {
        this.codigoProcedimiento = codigoProcedimiento;
    }
}
