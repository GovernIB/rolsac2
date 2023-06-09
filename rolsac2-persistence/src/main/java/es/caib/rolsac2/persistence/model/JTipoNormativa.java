package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoNormativaTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-normativa-sequence", sequenceName = "RS2_TIPONOR_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPONOR", indexes = {@Index(name = "RS2_TIPONOR_PK", columnList = "TPNO_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoNormativa.FIND_BY_ID, query = "select p from JTipoNormativa p where p.codigo = :id"), @NamedQuery(name = JTipoNormativa.COUNT_BY_IDENTIFICADOR, query = "select count(p) from JTipoNormativa p where p.identificador = :identificador")})
public class JTipoNormativa extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoNormativa.FIND_BY_ID";

    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoNormativa.COUNT_BY_IDENTIFICADOR";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-normativa-sequence")
    @Column(name = "TPNO_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Identificador
     */
    @Column(name = "TPNO_IDENTI", length = 50)
    private String identificador;

    @Column(name = "TPNO_CODSIA")
    private Long codigoSIA;

    @Column(name = "TPNO_IDBOIB")
    private Long codigoBOIB;

    /**
     * Descripcion
     */
    @OneToMany(mappedBy = "tipoNormativa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoNormativaTraduccion> descripcion;

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
     * @param identificador identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public List<JTipoNormativaTraduccion> getDescripcion() {
        return descripcion;
    }

    public Long getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Long codSIA) {
        this.codigoSIA = codSIA;
    }

    public Long getCodigoBOIB() {
        return codigoBOIB;
    }

    public void setCodigoBOIB(Long codBOIB) {
        this.codigoBOIB = codBOIB;
    }

    /**
     * Obtiene descripcion.
     *
     * @param idioma idioma
     * @return descripcion
     */
    public String getDescripcion(String idioma) {
        if (descripcion == null || descripcion.isEmpty()) {
            return "";
        }
        for (JTipoNormativaTraduccion trad : this.descripcion) {
            if (trad.getIdioma() != null && idioma.equalsIgnoreCase(idioma)) {
                return trad.getDescripcion();
            }
        }
        return "";
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(List<JTipoNormativaTraduccion> descripcion) {
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
        JTipoNormativa that = (JTipoNormativa) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoNormativa{" + "codigo=" + codigo + ", identificador='" + identificador + '\'' + ", descripcion=" + descripcion + '}';
    }
}