package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JSeccionTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "seccion-sequence", sequenceName = "RS2_SECCION_SEQ", allocationSize = 1)
@Table(name = "RS2_SECCION",
        indexes = {
                @Index(name = "RS2_SECCION_PK_I", columnList = "SECC_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JSeccion.FIND_BY_ID,
                query = "select p from JSeccion p where p.codigo = :id"),
        @NamedQuery(name = JSeccion.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JSeccion p where p.identificador = :identificador")
})
public class JSeccion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "Seccion.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "Seccion.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seccion-sequence")
    @Column(name = "SECC_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SECC_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "SECC_IDENTI", nullable = false, length = 50)
    private String identificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECC_SECPADRE")
    private JSeccion padre;

    /**
     * SECCIÓN MANTENIBLE SOLO POR ADMIN ENTIDAD
     **/
    @Column(name = "SECC_ADME", nullable = false)
    private boolean manteniblePorAdmEntidad = false;

    @OneToMany(mappedBy = "seccion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JSeccionTraduccion> descripcion;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad seccCodenti) {
        this.entidad = seccCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String seccIdenti) {
        this.identificador = seccIdenti;
    }

    public JSeccion getPadre() {
        return padre;
    }

    public void setPadre(JSeccion seccSecpadre) {
        this.padre = seccSecpadre;
    }

    public boolean getManteniblePorAdmEntidad() {
        return manteniblePorAdmEntidad;
    }

    public void setManteniblePorAdmEntidad(boolean seccAdme) {
        this.manteniblePorAdmEntidad = seccAdme;
    }

    public List<JSeccionTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JSeccionTraduccion> descripcion) {
        if (this.descripcion == null || this.descripcion.isEmpty()) {
            this.descripcion = descripcion;
        } else {
            this.descripcion.addAll(descripcion);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JSeccion jSeccion = (JSeccion) o;
        return codigo.equals(jSeccion.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JSeccion{" +
                "codigo=" + codigo +
                ", entidad=" + entidad +
                ", identificador='" + identificador + '\'' +
                ", manteniblePorAdmEntidad=" + manteniblePorAdmEntidad +
                '}';
    }
}