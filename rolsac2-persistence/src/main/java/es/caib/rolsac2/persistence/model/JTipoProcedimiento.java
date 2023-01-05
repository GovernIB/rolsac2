package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoProcedimientoTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * La clase J tipo procedimiento.
 */
@Entity
@SequenceGenerator(name = "tipo-procedimiento-sequence", sequenceName = "RS2_TIPOPRO_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOPRO",
        indexes = {
                @Index(name = "RS2_TIPOPRO_PK", columnList = "TPPR_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoProcedimiento.FIND_BY_ID,
                query = "select p from JTipoProcedimiento p where p.codigo = :id"),
        @NamedQuery(name = JTipoProcedimiento.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoProcedimiento p where p.identificador = :identificador")
})
public class JTipoProcedimiento extends BaseEntity {

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoProcedimiento.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoProcedimiento.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoProcedimiento", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoProcedimientoTraduccion> descripcion;

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-procedimiento-sequence")
    @Column(name = "TPPR_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPPR_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Identificador
     */
    @Column(name = "TPPR_IDENTI", length = 50)
    private String identificador;

    /**
     * Instancia un nuevo J tipo procedimiento.
     */
    public JTipoProcedimiento() {
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
    public List<JTipoProcedimientoTraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
    public void setDescripcion(List<JTipoProcedimientoTraduccion> descripcion) {
        if (this.descripcion == null || this.descripcion.isEmpty()) {
            this.descripcion = descripcion;
        } else {
            this.descripcion.addAll(descripcion);
        }
    }

    /**
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public JEntidad getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JTipoProcedimiento tipo = (JTipoProcedimiento) o;
        return Objects.equals(codigo, tipo.codigo) && Objects.equals(identificador, tipo.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador);
    }
}
