package es.caib.rolsac2.persistence.model;


import es.caib.rolsac2.service.model.ProcesoDTO;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.utils.UtilJSON;


import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "RS2_PROCES database table.
 *
 */
/*
 * PROCES_CODIGO NUMBER(10,0) No 1 CODIGO PROCES_IDENTI VARCHAR2(10 CHAR) No 2 IDENTIFICADOR PROCESO PROCES_DESCRI VARCHAR2(100 CHAR) No 3 DESCRIPCIÓN PROCESO
 * PROCES_CRON VARCHAR2(100 CHAR) Yes 4 CRON PROCES_ACTIVO NUMBER(1,0) No 0 5 ACTIVO PROCES_PARAMS VARCHAR2(1000 CHAR) Yes 6 PARÁMETROS INVOCACIÓN (SERIALIZADO
 * JSON)
 *
 */

@Entity
@SequenceGenerator(name = "proces-sequence", sequenceName = "RS2_PROCES_SEQ", allocationSize = 1)
@Table(name = "RS2_PROCES",
        indexes = {
          @Index(name="RS2_PROCES_PK_I", columnList = "PROCES_CODIGO")
        }
)
// @NamedQuery(name = "JProceso.findAll", query = "SELECT s FROM JProceso s")
/*@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "proceso_atenea", procedureName = "RS2_ATENEA",
    parameters = {@StoredProcedureParameter(mode = ParameterMode.OUT, name = "ret", type = String.class)})})*/
public class JProceso extends BaseEntity {

  /** Serial version UID. **/
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proces-sequence")
  @Column(name = "PROCES_CODIGO", unique = true, nullable = false, precision = 10)
  private Long codigo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "PROCES_CODENTI", nullable = false)
  private JEntidad entidad;

  @Column(name = "PROCES_IDENTI", length = 10, nullable = false)
  private String identificadorProceso;

  @Column(name = "PROCES_DESCRI", length = 100, nullable = false)
  private String descripcion;

  @Column(name = "PROCES_CRON", length = 100)
  private String cron;

  @Column(name = "PROCES_ACTIVO", nullable = false)
  private Boolean activo; // NUMBER(1,0) No 0 5 ACTIVO

  @Column(name = "PROCES_PARAMS", length = 2000)
  private String parametrosInvocacion; // PARÁMETROS INVOCACIÓN (SERIALIZADO JSON)

  @OneToMany(mappedBy = "proceso", cascade = CascadeType.REMOVE)
  private List<JProcesoLog> jProcesoLog;

  public JEntidad getEntidad() { return entidad; }

  public void setEntidad(JEntidad entidad) { this.entidad = entidad; }

  public String getIdentificadorProceso() {
    return identificadorProceso;
  }

  public void setIdentificadorProceso(final String identificadorProceso) {
    this.identificadorProceso = identificadorProceso;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(final String descripcion) {
    this.descripcion = descripcion;
  }

  public String getCron() {
    return cron;
  }

  public void setCron(final String cron) {
    this.cron = cron;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(final Boolean activo) {
    this.activo = activo;
  }

  public String getParametrosInvocacion() {
    return parametrosInvocacion;
  }

  public void setParametrosInvocacion(final String parametrosInvocacion) {
    this.parametrosInvocacion = parametrosInvocacion;
  }


  public List<JProcesoLog> getjProcesoLog() {
    return jProcesoLog;
  }

  public void setjProcesoLog(final List<JProcesoLog> jProcesoLog) {
    this.jProcesoLog = jProcesoLog;
  }

  /**
   * Transforma una entidad JPA en un objeto POJO de Java equivalente.
   *
   * @return
   */
  public ProcesoDTO toModel() {
    final ProcesoDTO proceso = new ProcesoDTO();
    proceso.setCodigo(this.getCodigo());
    proceso.setDescripcion(this.getDescripcion());
    proceso.setActivo(this.getActivo());
    proceso.setCron(this.getCron());
    proceso.setIdentificadorProceso(this.getIdentificadorProceso());
    proceso.setParametrosInvocacion((List<Propiedad>) UtilJSON.fromListJSON(this.getParametrosInvocacion(), Propiedad.class));
    return proceso;
  }

  /**
   * Transforma un objeto POJO de Java en una entidad JPA equivalente .
   *
   * @return
   */
  public static JProceso fromModel(final ProcesoDTO proceso) {
    JProceso jproceso = null;
    if (proceso != null) {
      jproceso = new JProceso();
      jproceso.setCodigo(proceso.getCodigo());
      jproceso.setDescripcion(proceso.getDescripcion());
      jproceso.setActivo(proceso.getActivo());
      jproceso.setCron(proceso.getCron());
      jproceso.setIdentificadorProceso(proceso.getIdentificadorProceso());
      jproceso.setParametrosInvocacion(UtilJSON.toJSON(proceso.getParametrosInvocacion()));
    }
    return jproceso;
  }

  /**
   * Genera un merge con el Proceso
   *
   * @param
   */
  public void merge(final ProcesoDTO proceso) {
    this.setDescripcion(proceso.getDescripcion());
    this.setActivo(proceso.getActivo());
    this.setCron(proceso.getCron());
    this.setIdentificadorProceso(proceso.getIdentificadorProceso());
    this.setParametrosInvocacion(UtilJSON.toJSON(proceso.getParametrosInvocacion()));
  }

  public Long getCodigo() {
    return this.codigo;
  }
  public void setCodigo(final Long codigo) {
    this.codigo = codigo;

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
