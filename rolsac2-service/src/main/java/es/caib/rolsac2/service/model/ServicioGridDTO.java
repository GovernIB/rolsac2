package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "ServicioGrid")
public class ServicioGridDTO extends ModelApi {
    private Long codigo;

    private Long codigoWFMod;

    private Long codigoWFPub;
    private String tipo;

    private String estado;
    private String estadoSIA;
    public Date siaFecha;
    private String codigoDir3SIA;
    private Integer codigoSIA;
    private String nombre;

    private LocalDate fecha;
    /**
     * Fecha actualizacion
     */
    private Date fechaActualizacion;

    /**
     * Comun
     */
    private Boolean comun;

    /**
     * Tiene mensajes pendientes.
     **/
    private boolean mensajesPendienteGestor;
    private boolean mensajesPendienteSupervisor;

    /**
     * Información visibilidad
     **/
    private Date fechaPublicacion;
    private Date fechaDespublicacion;


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstadoSIA() {
        return estadoSIA;
    }

    public void setEstadoSIA(String estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    public Date getSiaFecha() {
        return siaFecha;
    }

    public void setSiaFecha(Date fechaSIA) {
        this.siaFecha = fechaSIA;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    public void setCodigoDir3SIA(String codigoDir3SIA) {
        this.codigoDir3SIA = codigoDir3SIA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        if (estado == null) {
            return "";
        }
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCodigoWFMod() {
        return codigoWFMod;
    }

    public void setCodigoWFMod(Long codigoWFMod) {
        this.codigoWFMod = codigoWFMod;
    }

    public Long getCodigoWFPub() {
        return codigoWFPub;
    }

    public void setCodigoWFPub(Long codigoWFPub) {
        this.codigoWFPub = codigoWFPub;
    }

    public boolean tieneDatosPublicados() {
        return this.codigoWFPub != null;
    }

    public boolean tieneDatosEnModificacion() {
        return this.codigoWFMod != null;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isMensajesPendienteGestor() {
        return mensajesPendienteGestor;
    }

    public void setMensajesPendienteGestor(boolean mensajesPendienteGestor) {
        this.mensajesPendienteGestor = mensajesPendienteGestor;
    }

    public boolean isMensajesPendienteSupervisor() {
        return mensajesPendienteSupervisor;
    }

    public void setMensajesPendienteSupervisor(boolean mensajesPendienteSupervisor) {
        this.mensajesPendienteSupervisor = mensajesPendienteSupervisor;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaDespublicacion() {
        return fechaDespublicacion;
    }

    public void setFechaDespublicacion(Date fechaDespublicacion) {
        this.fechaDespublicacion = fechaDespublicacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Boolean getComun() {
        return comun;
    }

    public void setComun(Boolean comun) {
        this.comun = comun;
    }

    public String getLiteralComun() {
        if (comun == null) {
            return "";
        }
        return comun ? "Sí" : "No";
    }


    /**
     * Icono de visibilidad
     *
     * @return
     */
    public String getIcon() {
        if (this.isVisible()) {
            return "pi pi-eye iconoVerde";
        } else {
            return "pi pi-eye-slash iconoRojo";
        }

    }


    /**
     * Tiene mensajes pendientes.
     *
     * @return
     */
    public boolean tieneMensajesPendientes() {
        return isMensajesPendienteGestor() || isMensajesPendienteSupervisor();
    }

    /**
     * Devuelve la ruta dela imagen.
     *
     * @return
     */
    public String getCssMensajes() {
        if (isMensajesPendienteGestor() && isMensajesPendienteSupervisor()) {
            return "../images/email_GS.png";
        } else if (isMensajesPendienteGestor()) {
            return "../images/email_G.png";
        } else {//isMensajesPendienteSupervisor()
            return "../images/email_S.png";
        }
    }

    private boolean isVisible() {
        final Date now = new Date();

        final boolean noCaducado = (getFechaDespublicacion() == null || getFechaDespublicacion().after(now));
        final boolean publicado = (getFechaPublicacion() == null || getFechaPublicacion().before(now));

        final boolean visible = this.estado != null && this.estado.contains("P");
        return visible && noCaducado && publicado;
    }

    @Override
    public String toString() {
        return "ProcedimientoDTO{" + "codigo=" + codigo + ", nombre='" + nombre + '\'' + ", codigoSIA='" + codigoSIA + '\'' + '}';
    }
}
