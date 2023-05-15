package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoMateriaSIATraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.Traduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un tipo materia SIA. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-materia-sia-sequence", sequenceName = "RS2_TIPOMSI_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOMSI", indexes = {@Index(name = "RS2_TIPOMSI_PK", columnList = "TPMS_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoMateriaSIA.FIND_BY_ID, query = "select p from JTipoMateriaSIA p where p.codigo = :id"), @NamedQuery(name = JTipoMateriaSIA.COUNT_BY_IDENTIFICADOR, query = "select COUNT(p) from JTipoMateriaSIA p where p.identificador = :identificador")})
public class JTipoMateriaSIA extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoMateriaSIA.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoMateriaSIA.COUNT_BY_IDENTIFICADOR";

    /**
     * Instancia un nuevo J tipo materia sia.
     */
    public JTipoMateriaSIA() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-materia-sia-sequence")
    @Column(name = "TPMS_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Identificacion del tipo de materia SIA
     */
    @Column(name = "TPMS_IDENTI", length = 50)
    private String identificador;

    /**
     * Codigo SIA que se envia a SIA en el proceso.
     */
    @Column(name = "TPMS_CODSIA")
    private Long codigoSIA;

    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoMateriaSIA", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoMateriaSIATraduccion> descripcion;

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
     * @param identificacion identificacion
     */
    public void setIdentificador(String identificacion) {
        this.identificador = identificacion;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public List<JTipoMateriaSIATraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(List<JTipoMateriaSIATraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene codigo sia.
     *
     * @return codigoSIA
     */
    public Long getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * Establece codigo SIA.
     *
     * @param codigoSIA codigoSIA
     */
    public void setCodigoSIA(Long codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JTipoMateriaSIA)) return false;
        JTipoMateriaSIA that = (JTipoMateriaSIA) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoMateriaSIA{" + "id=" + codigo + "identificador=" + identificador + '}';
    }

    /**
     * To model tipo materia sia grid dto.
     *
     * @return tipo materia sia grid dto
     */
    public TipoMateriaSIAGridDTO toModel() {
        TipoMateriaSIAGridDTO tipo = new TipoMateriaSIAGridDTO();
        tipo.setCodigo(this.getCodigo());
        Literal literal = new Literal();
        for (JTipoMateriaSIATraduccion trad : this.getDescripcion()) {
            literal.add(new Traduccion(trad.getIdioma(), trad.getDescripcion()));
        }
        tipo.setDescripcion(literal);
        tipo.setIdentificador(this.getIdentificador());
        tipo.setCodigoSIA(this.getCodigoSIA());
        return tipo;
    }
}
