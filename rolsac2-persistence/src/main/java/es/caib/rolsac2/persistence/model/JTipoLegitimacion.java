package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoLegitimacionTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * La clase J tipo legitimacion.
 */
@Entity
@SequenceGenerator(name = "tipo-legitimacion-sequence", sequenceName = "RS2_TIPOLEG_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOLEG",
        indexes = {
                @Index(name = "RS2_TIPOLEG_PK", columnList = "TPLE_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoLegitimacion.FIND_BY_ID,
                query = "select p from JTipoLegitimacion p where p.codigo = :id"),
        @NamedQuery(name = JTipoLegitimacion.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoLegitimacion p where p.identificador = :identificador")
})
public class JTipoLegitimacion extends BaseEntity {

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoLegitimacion.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoLegitimacion.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "tipoLegitimacion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoLegitimacionTraduccion> descripcion;

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-legitimacion-sequence")
    @Column(name = "TPLE_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Codigo
     */
    @Column(name = "TPLE_IDENTI", length = 50)
    private String identificador;

    /**
     * Instania un nuevo J tipo legitimacion.
     */
    public JTipoLegitimacion() {
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
    public List<JTipoLegitimacionTraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
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
        return Objects.equals(codigo, that.codigo) && Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador);
    }
}
