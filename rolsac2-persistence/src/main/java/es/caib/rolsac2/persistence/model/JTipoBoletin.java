package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-boletin-sequence", sequenceName = "RS2_BOLETI_SEQ", allocationSize = 1)
@Table(name = "RS2_BOLETI",
  indexes = {
    @Index(name = "RS2_BOLETI_PK", columnList = "BOLE_CODIGO")
  }
)
@NamedQueries({
  @NamedQuery(name = JTipoBoletin.FIND_BY_ID,
    query = "select p from JTipoBoletin p where p.id = :id")
})
public class JTipoBoletin extends BaseEntity {

  public static final String FIND_BY_ID = "TipoBoletin.FIND_BY_ID";
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-boletin-sequence")
  @Column(name = "BOLE_CODIGO", nullable = false, length = 10)
  private Long id;

  @Column(name = "BOLE_IDENTI")
  private String identificador;

  @Column(name = "BOLE_NOMBRE")
  private String nombre;

  @Column(name = "BOLE_URL")
  private String url;

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

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JTipoBoletin that = (JTipoBoletin) o;
    return Objects.equals(id, that.id) && Objects.equals(identificador,
      that.identificador) && Objects.equals(nombre, that.nombre) && Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, identificador, nombre, url);
  }
}
