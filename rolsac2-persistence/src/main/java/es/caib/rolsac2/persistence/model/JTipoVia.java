package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoViaTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-via-sequence", sequenceName = "RS2_TIPOVIA_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOVIA",
        indexes = {
                @Index(name = "RS2_TIPOVIA_PK", columnList = "TPVI_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoVia.FIND_BY_ID,
                query = "select p from JTipoVia p where p.codigo = :id"),
        @NamedQuery(name = JTipoVia.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoVia p where p.identificador = :identificador")
})
public class JTipoVia extends BaseEntity {

    public static final String FIND_BY_ID = "TipoVia.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoVia.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "tipoVia", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoViaTraduccion> descripcion;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-via-sequence")
    @Column(name = "TPVI_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @Column(name = "TPVI_IDENTI", length = 50)
    private String identificador;

    public JTipoVia() {
        super();
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public List<JTipoViaTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoViaTraduccion> descripcion) {
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
        JTipoVia jTipoVia = (JTipoVia) o;
        return Objects.equals(codigo, jTipoVia.codigo) && Objects.equals(identificador, jTipoVia.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador);
    }
}
