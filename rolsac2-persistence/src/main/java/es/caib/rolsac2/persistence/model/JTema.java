package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTemaTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * La clase J tema.
 */
@Entity
@SequenceGenerator(name = "tema-sequence", sequenceName = "RS2_TEMA_SEQ", allocationSize = 1)
@Table(name = "RS2_TEMA", indexes = {@Index(name = "RS2_TEMA_PK_I", columnList = "TEMA_CODIGO")})
@NamedQueries({@NamedQuery(name = JTema.FIND_BY_ID, query = "select p from JTema p where p.codigo = :id"), @NamedQuery(name = JTema.COUNT_BY_IDENTIFICADOR, query = "select count(p) from JTema p where p.identificador = :identificador"), @NamedQuery(name = JTema.COUNT_BY_IDENTIFICADOR_ENTIDAD, query = "select count(p) from JTema p where p.identificador = :identificador and p.entidad.codigo = :entidad")})
public class JTema extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Tema.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "Tema.COUNT_BY_IDENTIFICADOR";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR_ENTIDAD = "Tema.COUNT_BY_IDENTIFICADOR_ENTIDAD";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tema-sequence")
    @Column(name = "TEMA_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TEMA_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Identificador
     */
    @Column(name = "TEMA_IDENTI", nullable = false, length = 50)
    private String identificador;

    /**
     * Tema padre
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMA_PADRE")
    private JTema temaPadre;

    @Column(name = "TEMA_MATHPATH")
    private String mathPath;

    @OneToMany(mappedBy = "tema", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTemaTraduccion> descripcion;

    /**
     * Tipo materia sia que contiene el codigo que se envia a SIA en el proceso.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMA_MATSIA", nullable = true)
    private JTipoMateriaSIA tipoMateriaSIA;

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene entidad.
     *
     * @return entidad
     */
    public JEntidad getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param temaCodenti tema codenti
     */
    public void setEntidad(JEntidad temaCodenti) {
        this.entidad = temaCodenti;
    }

    /**
     * Obtiene identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param temaIdenti tema identi
     */
    public void setIdentificador(String temaIdenti) {
        this.identificador = temaIdenti;
    }

    /**
     * Obtiene tema padre.
     *
     * @return tema padre
     */
    public JTema getTemaPadre() {
        return temaPadre;
    }

    /**
     * Establece tema padre.
     *
     * @param temaPadre tema padre
     */
    public void setTemaPadre(JTema temaPadre) {
        this.temaPadre = temaPadre;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public List<JTemaTraduccion> getDescripcion() {
        return descripcion;
    }

    public String getMathPath() {
        return mathPath;
    }

    public void setMathPath(String mathPath) {
        this.mathPath = mathPath;
    }

    public JTipoMateriaSIA getTipoMateriaSIA() {
        return tipoMateriaSIA;
    }

    public void setTipoMateriaSIA(JTipoMateriaSIA codigoSIA) {
        this.tipoMateriaSIA = codigoSIA;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
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
        return "JTema{" + "codigo=" + codigo + ", entidad=" + entidad + ", identificador='" + identificador + '\'' + ", descripcion=" + descripcion + '}';
    }
}