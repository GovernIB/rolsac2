package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoSexoTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-sexo-sequence", sequenceName = "RS2_TIPOSEX_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOSEX",
  indexes = {
    @Index(name = "RS2_TIPOSEX_PK", columnList = "TPSX_CODIGO")
  }
)
@NamedQueries({
  @NamedQuery(name = JTipoSexo.FIND_BY_ID,
    query = "select p from JTipoSexo p where p.id = :id"),
  @NamedQuery(name = JTipoSexo.COUNT_BY_IDENTIFICADOR,
    query = "select COUNT(p) from JTipoSexo p where p.identificador = :identificador")
})
public class JTipoSexo extends BaseEntity {

  public static final String FIND_BY_ID = "TipoSexo.FIND_BY_ID";
  public static final String COUNT_BY_IDENTIFICADOR = "TipoSexo.COUNT_BY_IDENTIFICADOR";
  private static final long serialVersionUID = 1L;
  /**
   * Descripción
   */
  @OneToMany(mappedBy = "tipoSexo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<JTipoSexoTraduccion> descripcion;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-sexo-sequence")
  @Column(name = "TPSX_CODIGO", nullable = false, length = 10)
  private Long id;

  @Column(name = "TPSX_IDENTI", length = 50)
  private String identificador;

  public JTipoSexo() {
    super();
    // descripcion = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIdentificador() {
    return identificador;
  }

  public void setIdentificador(String identificador) {
    this.identificador = identificador;
  }

  public List<JTipoSexoTraduccion> getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(List<JTipoSexoTraduccion> descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JTipoSexo tipoSexo = (JTipoSexo) o;
    return Objects.equals(id, tipoSexo.id) && Objects.equals(identificador, tipoSexo.identificador);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, identificador);
  }
}
