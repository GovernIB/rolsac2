package es.caib.rolsac2.back.model;

import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;

/**
 * Mensaje con el resultado del estado destino y de los literales.
 *
 * @author Indra
 */
public class RespuestaFlujo {

    public String mensajes;

    public TypeProcedimientoEstado estadoDestino;

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
}
