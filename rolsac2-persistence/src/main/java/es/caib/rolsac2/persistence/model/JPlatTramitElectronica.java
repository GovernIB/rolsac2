package es.caib.rolsac2.persistence.model;

import java.util.Objects;

import javax.persistence.*;

/**
 * Representacion de una plataforma de tramitación electrónica. A nivel de clase, definimos la secuencia que
 * utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "plat-tramit-electronica-sequence", sequenceName = "RS2_PLATRE_SEQ", allocationSize = 1)
@Table(name = "RS2_PLATRE", indexes = {@Index(name = "RS2_PLATRE_PK", columnList = "PTTR_CODIGO")})
@NamedQueries({
    @NamedQuery(name = JPlatTramitElectronica.FIND_BY_ID,
        query = "select p from JPlatTramitElectronica p where p.id = :id"),
    @NamedQuery(name = JPlatTramitElectronica.FIND_ALL, query = "select p from JPlatTramitElectronica p")})
public class JPlatTramitElectronica extends BaseEntity {

  private static final long serialVersionUID = 1L;

  public static final String FIND_BY_ID = "PlatTramitElectronica.FIND_BY_ID";
  public static final String FIND_ALL = "PlatTramitElectronica.FIND_ALL";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plat-tramit-electronica-sequence")
  @Column(name = "PTTR_CODIGO", nullable = false, length = 10)
  private Long id;

  /**
   * Código entidad
   */
  @ManyToOne
  @JoinColumn(name = "PTTR_CODENTI", nullable = false)
  private JEntidad codEntidad;

  /**
   * Identificador
   */
  @Column(name = "PTTR_IDENTI", nullable = false, length = 50)
  private String identificador;

  /**
   * Descripción
   */
  @Column(name = "PTTR_DESCR", nullable = false, length = 255)
  private String descripcion;

  /**
   * URL acceso
   */
  @Column(name = "PTTR_URL", nullable = false, length = 500)
  private String urlAcceso;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public JEntidad getCodEntidad() {
    return codEntidad;
  }

  public void setCodEntidad(JEntidad codEntidad) {
    this.codEntidad = codEntidad;
  }

  public String getIdentificador() {
    return identificador;
  }

  public void setIdentificador(String identificador) {
    this.identificador = identificador;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getUrlAcceso() {
    return urlAcceso;
  }

  public void setUrlAcceso(String urlAcceso) {
    this.urlAcceso = urlAcceso;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof JPlatTramitElectronica))
      return false;
    JPlatTramitElectronica that = (JPlatTramitElectronica) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "JPlatTramitElectronica{" + "id=" + id + ", codEntidad=" + codEntidad + ", identificador='" + identificador
        + '\'' + ", descripcion='" + descripcion + '\'' + ", urlAcceso=" + urlAcceso + '}';
  }
}
