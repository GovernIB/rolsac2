package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoViaTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * La clase J tipo via.
 */
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

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoVia.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoVia.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "tipoVia", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoViaTraduccion> descripcion;

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-via-sequence")
    @Column(name = "TPVI_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Identificador
     */
    @Column(name = "TPVI_IDENTI", length = 50)
    private String identificador;

    /**
     * Instancia una nueva J tipo via.
     */
    public JTipoVia() {
        super();
    }

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public List<JTipoViaTraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
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
