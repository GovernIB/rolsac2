package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * LA clase J procedimiento.
 */
@Entity
@SequenceGenerator(name = "procedimiento-sequence", sequenceName = "RS2_PROC_SEQ", allocationSize = 1)
@Table(name = "RS2_PROC", indexes = {@Index(name = "RS2_PROC_PK_I", columnList = "PROC_CODIGO")})
@NamedQueries({@NamedQuery(name = JProcedimiento.FIND_BY_ID, query = "select p from JProcedimiento p where p.codigo = :id")})
public class JProcedimiento extends BaseEntity {
    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Procedimiento.FIND_BY_ID";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-sequence")
    @Column(name = "PROC_CODIGO", nullable = false)
    private Long codigo;

    /**
     * PROCEDIMIENTO (P) / SERVICIO (S)
     **/
    @Column(name = "PROC_TIPO", nullable = false, length = 1)
    private String tipo;

    /**
     * Fecha SIA
     */
    @Column(name = "PROC_FECACT")
    private Date fechaActualizacion;

    /**
     * Codigo SIA
     */
    @Column(name = "PROC_SIACOD")
    private Integer codigoSIA;

    /**
     * Estiado SIA
     */
    @Column(name = "PROC_SIAEST")
    private String estadoSIA;

    /**
     * Fecha SIA
     */
    @Column(name = "PROC_SIAFC")
    private Date siaFecha;

    /**
     * Codigo Dir3 SIA
     */
    @Column(name = "PROC_SIADIR3", length = 20)
    private String codigoDir3SIA;

    /**
     * Mensajes
     */
    @Column(name = "PROC_MENSA")
    private String mensajes;


    /**
     * Fecha indexacion
     */
    @Column(name = "PROC_DATIDX")
    private Date fechaIndexacion;

    /**
     * Fecha inicio indexacion
     */
    @Column(name = "PROC_DATINX")
    private Date fechaInicioIndexacion;

    /**
     * Error indexacion
     */
    @Column(name = "PROC_ERRIDX")
    private String mensajeIndexacion;

    /**
     * Error indexacion
     */
    @Column(name = "PROC_ERRSIA")
    private String mensajeIndexacionSIA;


    /**
     * Mensajes pdt gestor
     */
    @Column(name = "PROC_PDTGST", nullable = true, precision = 1, scale = 0)
    private boolean mensajesPendienteGestor = false;


    /**
     * Mensajes pdt supervisor
     */
    @Column(name = "PROC_PDTSUP", nullable = true, precision = 1, scale = 0)
    private boolean mensajesPendienteSupervisor = false;


    /**
     * Workflow del procedimiento
     */
    @OneToMany(mappedBy = "procedimiento", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<JProcedimientoWorkflow> procedimientoWF;

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
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
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
     * @param procTipo proc tipo
     */
    public void setTipo(String procTipo) {
        this.tipo = procTipo;
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
     * @param procSiacod proc siacod
     */
    public void setCodigoSIA(Integer procSiacod) {
        this.codigoSIA = procSiacod;
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
     * @param procSiaest proc siaest
     */
    public void setEstadoSIA(String procSiaest) {
        this.estadoSIA = procSiaest;
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
     * @param procSiafc proc siafc
     */
    public void setSiaFecha(Date procSiafc) {
        this.siaFecha = procSiafc;
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
     * @param procSiadir3 proc siadir 3
     */
    public void setCodigoDir3SIA(String procSiadir3) {
        this.codigoDir3SIA = procSiadir3;
    }

    /**
     * Obtiene mensajes.
     *
     * @return mensajes
     */
    public String getMensajes() {
        return mensajes;
    }

    /**
     * Establece mensajes.
     *
     * @param mensajes mensajes
     */
    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    /**
     * Obtiene procedimiento wf.
     *
     * @return procedimiento wf
     */
    public List<JProcedimientoWorkflow> getProcedimientoWF() {
        return procedimientoWF;
    }

    /**
     * Establece procedimiento wf.
     *
     * @param procedimientoWF procedimiento wf
     */
    public void setProcedimientoWF(List<JProcedimientoWorkflow> procedimientoWF) {
        this.procedimientoWF = procedimientoWF;
    }

    public Date getFechaIndexacion() {
        return fechaIndexacion;
    }

    public void setFechaIndexacion(Date fechaIndexacion) {
        this.fechaIndexacion = fechaIndexacion;
    }

    public Date getFechaInicioIndexacion() {
        return fechaInicioIndexacion;
    }

    public void setFechaInicioIndexacion(Date fechaInicioIndexacion) {
        this.fechaInicioIndexacion = fechaInicioIndexacion;
    }

    public String getMensajeIndexacion() {
        return mensajeIndexacion;
    }

    public void setMensajeIndexacion(String errorIndexacion) {
        this.mensajeIndexacion = errorIndexacion;
    }

    public String getMensajeIndexacionSIA() {
        return mensajeIndexacionSIA;
    }

    public void setMensajeIndexacionSIA(String mensajeIndexacionSIA) {
        this.mensajeIndexacionSIA = mensajeIndexacionSIA;
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

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JProcedimiento that = (JProcedimiento) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JProcedimiento{" + "codigo=" + codigo + ", tipo='" + tipo + '\'' + ", codigoSIA=" + codigoSIA + ", estadoSIA=" + estadoSIA + ", siaFecha=" + siaFecha + ", codigoDir3SIA='" + codigoDir3SIA + '\'' + '}';
    }
}