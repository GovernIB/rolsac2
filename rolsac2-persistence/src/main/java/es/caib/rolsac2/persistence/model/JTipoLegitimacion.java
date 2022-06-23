package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoLegitimacionTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-legitimacion-sequence", sequenceName = "RS2_TIPOLEG_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOLEG",
  indexes = {
    @Index(name = "RS2_TIPOLEG_PK", columnList = "TPLE_CODIGO")
  }
)
@NamedQueries({
  @NamedQuery(name = JTipoLegitimacion.FIND_BY_ID,
    query = "select p from JTipoLegitimacion p where p.id = :id"),
  @NamedQuery(name = JTipoLegitimacion.COUNT_BY_IDENTIFICADOR,
    query = "select COUNT(p) from JTipoLegitimacion p where p.identificador = :identificador")
})
public class JTipoLegitimacion extends BaseEntity {

  public static final String FIND_BY_ID = "TipoLegitimacion.FIND_BY_ID";
  public static final String COUNT_BY_IDENTIFICADOR = "TipoLegitimacion.COUNT_BY_IDENTIFICADOR";
  private static final long serialVersionUID = 1L;
  @OneToMany(mappedBy = "tipoLegitimacion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<JTipoLegitimacionTraduccion> descripcion;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-legitimacion-sequence")
  @Column(name = "TPLE_CODIGO", nullable = false, length = 10)
  private Long id;

  @Column(name = "TPLE_IDENTI", length = 50)
  private String identificador;

  public JTipoLegitimacion() {
    super();
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

  public List<JTipoLegitimacionTraduccion> getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(List<JTipoLegitimacionTraduccion> descripcion) {
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
    JTipoLegitimacion that = (JTipoLegitimacion) o;
    return Objects.equals(id, that.id) && Objects.equals(identificador, that.identificador);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, identificador);
  }
}
