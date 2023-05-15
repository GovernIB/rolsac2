package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoUnidadAdministrativaTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-ua-sequence", sequenceName = "RS2_TIPOUNA_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOUNA", indexes = {@Index(name = "RS2_TIPOUNA_PK", columnList = "TPUA_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoUnidadAdministrativa.FIND_BY_ID, query = "select p from JTipoUnidadAdministrativa p where p.codigo = :id"), @NamedQuery(name = JTipoUnidadAdministrativa.COUNT_BY_IDENTIFICADOR, query = "select count(p) from JTipoUnidadAdministrativa p where p.entidad.codigo = :entidad and lower(p.identificador) like :identificador")})
public class JTipoUnidadAdministrativa extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoUnidadAdministrativa.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoUnidadAdministrativa.COUNT_BY_IDENTIFICADOR";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-ua-sequence")
    @Column(name = "TPUA_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TPUA_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Traducciones
     */
    @OneToMany(mappedBy = "tipoUnidadAdministrativa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoUnidadAdministrativaTraduccion> traducciones;

    /**
     * Identificador
     */
    @Column(name = "TPUA_IDENTI", length = 50, unique = true)
    private String identificador;

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
     * @param entidad entidad
     */
    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
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
     * Obtiene traducciones.
     *
     * @return traducciones
     */
    public List<JTipoUnidadAdministrativaTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Establece traducciones.
     *
     * @param traducciones traducciones
     */
    public void setTraducciones(List<JTipoUnidadAdministrativaTraduccion> traducciones) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = traducciones;
        } else {
            this.traducciones.addAll(traducciones);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JTipoUnidadAdministrativa that = (JTipoUnidadAdministrativa) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoUnidadAdministrativa{" + "id=" + codigo + ", entidad=" + entidad + ", traducciones=" + traducciones + ", identificador='" + identificador + '\'' + '}';
    }
}