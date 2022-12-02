package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.ProcesoLogDTO;
import es.caib.rolsac2.service.model.types.TypeEstadoProceso;
import es.caib.rolsac2.service.utils.UtilJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RS2_PROCLOG database table.
 *
 */
/*
 * EsNULL PROCLOG_CODIGO NUMBER(10,0) No 1 CODIGO PROCLOG_CODPROC NUMBER(10,0) No 2 CODIGO PROCESO PROCLOG_ESTADO VARCHAR2(1 CHAR) No 3 ESTADO PROCESO: INICIADO
 * (I) / FINALIZADO OK (S) / FINALIZADO ERROR (N) PROCLOG_FECINI DATE No 4 FECHA INICIO PROCLOG_FECFIN DATE Yes 5 FECHA FIN PROCLOG_ERRMSG VARCHAR2(4000 CHAR)
 * Yes 6 MENSAJE ERROR EN CASO DE FALLO PROCLOG_INFOPROC CLOB Yes 7 INFORMACIÓN ADICIONAL (SERIALIZADA JSON)
 */
@Entity
@SequenceGenerator(name = "proces-log-sequence", sequenceName = "RS2_PROCLOG_SEQ", allocationSize = 1)
@Table(name = "RS2_PROCLOG")
// @NamedQuery(name = "JProcesoLog.findAll", query = "SELECT s FROM JProcesoLog s")
public class JProcesoLog extends BaseEntity  {
  private static final long serialVersionUID = 1L;

  private static final Logger LOG = LoggerFactory.getLogger(JProcesoLog.class);

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proces-sequence")
  @Column(name = "PROCLOG_CODIGO", unique = true, nullable = false, precision = 10)
  private Long codigo;

  @ManyToOne
  @JoinColumn(name = "PROCLOG_CODPROC")
  private JProceso proceso; // PROCLOG_CODPROC NUMBER(10,0) No 2 CODIGO PROCESO

  @Column(name = "PROCLOG_ESTADO", length = 1, nullable = false)
  private String estadoProceso; // ESTADO PROCESO: VACIO("V"), CORRECTO("C"), ALERTA("A"), ERROR("E");

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "PROCLOG_FECINI")
  private Date fechaInicio;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "PROCLOG_FECFIN")
  private Date fechaFin;

  @Column(name = "PROCLOG_ERRMSG")
  private String mensajeError; // MENSAJE ERROR EN CASO DE FALLO

  @Column(name = "PROCLOG_INFOPROC")
  private String informacionProceso; // PROCLOG_INFOPROC CLOB Yes 7 INFORMACIÓN ADICIONAL (SERIALIZADA JSON)

  public Long getCodigo() {
    return this.codigo;
  }

  public void setCodigo(final Long codigo) {
    this.codigo = codigo;
  }

  public JProceso getProceso() {
    return proceso;
  }

  public void setProceso(final JProceso proceso) {
    this.proceso = proceso;
  }

  public String getEstadoProceso() {
    return estadoProceso;
  }

  public void setEstadoProceso(final String estadoProceso) {
    this.estadoProceso = estadoProceso;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(final Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(final Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getMensajeError() {
    return mensajeError;
  }

  public void setMensajeError(final String mensajeError) {
    this.mensajeError = mensajeError;
  }

  public String getInformacionProceso() {
    return informacionProceso;
  }

  public void setInformacionProceso(final String informacionProceso) {
    this.informacionProceso = informacionProceso;
  }

  /**
   * Transforma una entidad JPA en un objeto POJO de Java equivalente.
   *
   * @return
   */
  public ProcesoLogDTO toModel() {
    final ProcesoLogDTO proceso = new ProcesoLogDTO();
    proceso.setCodigo(this.getCodigo());
    proceso.setMensajeError(this.getMensajeError());
    proceso.setFechaFin(this.getFechaFin());
    proceso.setFechaInicio(this.getFechaInicio());
    proceso.setEstadoProceso(TypeEstadoProceso.fromString(this.getEstadoProceso()));
    final ListaPropiedades prop = (ListaPropiedades) UtilJSON.fromJSON(this.getInformacionProceso(), ListaPropiedades.class);
    proceso.setListaPropiedades(prop);
    return proceso;
  }


  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
}
