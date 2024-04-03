package es.caib.rolsac2.api.externa.v1.model.respuestas;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * RespuestaBase. Estructura de respuesta que contiene la información comun a todas las respuestas.
 *
 * @author indra
 */
@XmlRootElement
@Schema(name = "RespuestaBase", description = "Respuesta Base")
public class RespuestaBase {

    /**
     * Status a retornar.
     **/
    @Schema(required = true, description = "Status")
    private String status;

    /**
     * Mensaje de  error.
     **/
    @Schema(description = "Mensaje")
    private String mensaje;

    /**
     * Numero de Elementos.
     **/
    @Schema(required = true, description = "Numero de Elementos")
    private Long numeroElementos;

    /**
     * codigo
     **/
    @Schema(description = "Tiempo en milisegundos de realizacion de la consulta")
    private Long tiempo;

    public RespuestaBase(String status, String mensaje, Long numeroElementos, Long tiempo) {
        super();
        this.status = status;
        this.mensaje = mensaje;
        this.numeroElementos = numeroElementos;
        this.tiempo = tiempo;
    }

    public RespuestaBase() {
        this.status = null;
        this.mensaje = null;
        this.numeroElementos = null;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * @return the mensajeError
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensajeError to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the numeroElementos
     */
    public Long getNumeroElementos() {
        return numeroElementos;
    }

    /**
     * @param numeroElementos the numeroElementos to set
     */
    public void setNumeroElementos(Long numeroElementos) {
        this.numeroElementos = numeroElementos;
    }

    /**
     * Método que devuelve el tiempo de la consulta.
     *
     * @return tiempo
     */
    public Long getTiempo() {
        return tiempo;
    }

    /**
     * Método que establece el tiempo de la consulta.
     */
    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }
}
