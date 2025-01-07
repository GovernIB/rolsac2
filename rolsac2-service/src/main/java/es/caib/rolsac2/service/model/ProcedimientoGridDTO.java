package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "Procedimiento")
public class ProcedimientoGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Codigo del workflow modificado
     */
    private Long codigoWFMod;

    /**
     * Codigo del workflow publicado
     */
    private Long codigoWFPub;

    /**
     * Numero
     */
    private Integer numero = 0;

    /**
     * Tipo
     */
    private String tipo;

    /**
     * Estado
     */
    private String estado;

    /**
     * Estado SIA
     */
    private String estadoSIA;

    /**
     * Fecha SIA
     */
    public Date siaFecha;

    /**
     * Codigo DIR3 SIA
     */
    private String codigoDir3SIA;

    /**
     * Codigo SIA
     */
    private Integer codigoSIA;

    /**
     * Nombre
     */
    private String nombre;

    /**
     * Fecha
     */
    private LocalDate fecha;

    /**
     * Tipo procedimiento
     */
    private String tipoProcedimiento;

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
     * Información tramite inicio
     **/
    private Long tramiteInicioCodigo;
    private Date tramiteInicioFechaPublicacion;
    private Date tramiteIniciofechaCierre;

    public static ProcedimientoGridDTO createGrid(ProcedimientoDTO proc) {
        ProcedimientoGridDTO procedimientoGridDTO = null;
        if (proc != null) {
            procedimientoGridDTO = new ProcedimientoGridDTO();
            procedimientoGridDTO.setCodigo(proc.getCodigo());
            procedimientoGridDTO.setCodigoSIA(proc.getCodigoSIA());
            //procedimientoGridDTO.setCodigoDir3SIA(proc.getCodigoDir3SIA());
            //procedimientoGridDTO.setEstado(proc.getEstado());
            procedimientoGridDTO.setEstadoSIA(proc.getEstadoSIA());
            //procedimientoGridDTO.setFecha(proc.getFecha());
            procedimientoGridDTO.setNombre(proc.getNombre());
            procedimientoGridDTO.setTipo(proc.getTipo());
            //procedimientoGridDTO.setTipoProcedimiento(proc.getTipoProcedimiento());
            procedimientoGridDTO.setFechaActualizacion(proc.getFechaActualizacion());
            //procedimientoGridDTO.setComun(proc.getComun());
            //procedimientoGridDTO.setTramiteInicioCodigo(proc.getTramiteInicioCodigo());
            //procedimientoGridDTO.setTramiteInicioFechaPublicacion(proc.getTramiteInicioFechaPublicacion());
            //procedimientoGridDTO.setTramiteIniciofechaCierre(proc.getTramiteIniciofechaCierre());
            //procedimientoGridDTO.setMensajesPendienteGestor(proc.isMensajesPendienteGestor());
            //procedimientoGridDTO.setMensajesPendienteSupervisor(proc.isMensajesPendienteSupervisor());
        }
        return procedimientoGridDTO;
    }


    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene tipo.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene estado sia.
     *
     * @return estado sia
     */
    public String getEstadoSIA() {
        return estadoSIA;
    }

    /**
     * Establece estado sia.
     *
     * @param estadoSIA estado sia
     */
    public void setEstadoSIA(String estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    /**
     * Obtiene sia fecha.
     *
     * @return sia fecha
     */
    public Date getSiaFecha() {
        return siaFecha;
    }

    /**
     * Establece sia fecha.
     *
     * @param fechaSIA fecha sia
     */
    public void setSiaFecha(Date fechaSIA) {
        this.siaFecha = fechaSIA;
    }

    /**
     * Obtiene codigo sia.
     *
     * @return codigo sia
     */
    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * Establece codigo sia.
     *
     * @param codigoSIA codigo sia
     */
    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    /**
     * Obtiene codigo dir 3 sia.
     *
     * @return codigo dir 3 sia
     */
    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    /**
     * Establece codigo dir 3 sia.
     *
     * @param codigoDir3SIA codigo dir 3 sia
     */
    public void setCodigoDir3SIA(String codigoDir3SIA) {
        this.codigoDir3SIA = codigoDir3SIA;
    }

    /**
     * Obtiene nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene estado.
     *
     * @return estado
     */
    public String getEstado() {
        if (estado == null) {
            return "";
        }
        return estado;
    }

    /**
     * Establece estado.
     *
     * @param estado estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene codigo wf mod.
     *
     * @return codigo wf mod
     */
    public Long getCodigoWFMod() {
        return codigoWFMod;
    }

    /**
     * Establece codigo wf mod.
     *
     * @param codigoWFMod codigo wf mod
     */
    public void setCodigoWFMod(Long codigoWFMod) {
        this.codigoWFMod = codigoWFMod;
    }

    /**
     * Obtiene codigo wf pub.
     *
     * @return codigo wf pub
     */
    public Long getCodigoWFPub() {
        return codigoWFPub;
    }

    /**
     * Establece codigo wf pub.
     *
     * @param codigoWFPub codigo wf pub
     */
    public void setCodigoWFPub(Long codigoWFPub) {
        this.codigoWFPub = codigoWFPub;
    }

    /**
     * Tiene datos publicados boolean.
     *
     * @return boolean
     */
    public boolean tieneDatosPublicados() {
        return this.codigoWFPub != null;
    }

    /**
     * Tiene datos en modificacion boolean.
     *
     * @return boolean
     */
    public boolean tieneDatosEnModificacion() {
        return this.codigoWFMod != null;
    }

    /**
     * Obtiene fecha.
     *
     * @return fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece fecha.
     *
     * @param fecha fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el tipo de procedimiento (ya traducido)
     *
     * @return
     */
    public String getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    /**
     * Estblece el tipo de procedimiento
     *
     * @param tipoProcedimiento
     */
    public void setTipoProcedimiento(String tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    /**
     * Obtiene la fecha de actualizacion
     *
     * @return
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Establece la fecha de actualizacion
     *
     * @param fechaActualizacion
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Obtiene el comun
     *
     * @return
     */
    public Boolean getComun() {
        return comun;
    }

    public String getLiteralComun() {
        if (comun == null) {
            return "";
        }
        return comun ? "Sí" : "No";
    }

    /**
     * Establece el comun
     *
     * @param comun
     */
    public void setComun(Boolean comun) {
        this.comun = comun;
    }

    public Long getTramiteInicioCodigo() {
        return tramiteInicioCodigo;
    }

    public void setTramiteInicioCodigo(Long tramiteInicioCodigo) {
        this.tramiteInicioCodigo = tramiteInicioCodigo;
    }

    public Date getTramiteInicioFechaPublicacion() {
        return tramiteInicioFechaPublicacion;
    }

    public void setTramiteInicioFechaPublicacion(Date tramiteInicioFechaPublicacion) {
        this.tramiteInicioFechaPublicacion = tramiteInicioFechaPublicacion;
    }

    public Date getTramiteIniciofechaCierre() {
        return tramiteIniciofechaCierre;
    }

    public void setTramiteIniciofechaCierre(Date tramiteIniciofechaCierre) {
        this.tramiteIniciofechaCierre = tramiteIniciofechaCierre;
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
        final boolean tieneTramiteInicio = this.tramiteInicioCodigo != null;
        final boolean noCaducado = (getTramiteIniciofechaCierre() == null || getTramiteIniciofechaCierre().after(now));
        final boolean publicado = (getTramiteInicioFechaPublicacion() == null || getTramiteInicioFechaPublicacion().before(now));

        final boolean visible = this.estado != null && this.estado.contains("P");
        return tieneTramiteInicio && visible && noCaducado && publicado;
    }

    @Override
    public String toString() {
        return "ProcedimientoDTO{" + "codigo=" + codigo + ", codigoWFPub:" + codigoWFPub + ", codigoWFMod:" + codigoWFMod + " nombre='" + nombre + '\'' + ", codigoSIA='" + codigoSIA + '\'' + '}';
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
