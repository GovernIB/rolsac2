package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTemaTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tema-sequence", sequenceName = "RS2_TEMA_SEQ", allocationSize = 1)
@Table(name = "RS2_TEMA",
        indexes = {
                @Index(name = "RS2_TEMA_PK_I", columnList = "TEMA_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JTema.FIND_BY_ID,
            query = "select p from JTema p where p.codigo = :id"),
        @NamedQuery(name = JTema.COUNT_BY_IDENTIFICADOR,
            query = "select count(p) from JTema p where p.identificador = :identificador")
})
public class JTema extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "Tema.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "Tema.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tema-sequence")
    @Column(name = "TEMA_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TEMA_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "TEMA_IDENTI", nullable = false, length = 50)
    private String identificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMA_PADRE")
    private JTema temaPadre;

    @OneToMany(mappedBy = "tema", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTemaTraduccion> descripcion;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad temaCodenti) {
        this.entidad = temaCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String temaIdenti) {
        this.identificador = temaIdenti;
    }

    public JTema getTemaPadre() {
        return temaPadre;
    }

    public void setTemaPadre(JTema temaPadre) {
        this.temaPadre = temaPadre;
    }

    public List<JTemaTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTemaTraduccion> descripcion) {
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
        JTema jTema = (JTema) o;
        return codigo.equals(jTema.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTema{" +
                "codigo=" + codigo +
                ", entidad=" + entidad +
                ", identificador='" + identificador + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}