package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoProcedimientoTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    public static final String FIND_BY_ID = "TipoProcedimiento.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoProcedimiento.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoProcedimiento", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoProcedimientoTraduccion> descripcion;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-procedimiento-sequence")
    @Column(name = "TPPR_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPPR_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "TPPR_IDENTI", length = 50)
    private String identificador;

    public JTipoProcedimiento() {
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

    public List<JTipoProcedimientoTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoProcedimientoTraduccion> descripcion) {
        if (this.descripcion == null || this.descripcion.isEmpty()) {
            this.descripcion = descripcion;
        } else {
            this.descripcion.addAll(descripcion);
        }
    }

    public JEntidad getEntidad() {
        return entidad;
    }

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
