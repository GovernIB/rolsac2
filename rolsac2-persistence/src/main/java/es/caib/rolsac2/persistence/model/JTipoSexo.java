package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoSexoTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * La clase J tipo sexo.
 */
@Entity
@SequenceGenerator(name = "tipo-sexo-sequence", sequenceName = "RS2_TIPOSEX_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOSEX", indexes = {@Index(name = "RS2_TIPOSEX_PK", columnList = "TPSX_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoSexo.FIND_BY_ID, query = "select p from JTipoSexo p where p.codigo = :id"), @NamedQuery(name = JTipoSexo.COUNT_BY_IDENTIFICADOR, query = "select COUNT(p) from JTipoSexo p where p.identificador = :identificador")})
public class JTipoSexo extends BaseEntity {

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoSexo.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoSexo.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoSexo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoSexoTraduccion> descripcion;

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-sexo-sequence")
    @Column(name = "TPSX_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Identificador
     */
    @Column(name = "TPSX_IDENTI", length = 50)
    private String identificador;

    /**
     * Instancia un nuevo J tipo sexo.
     */
    public JTipoSexo() {
        super();
    }

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
    public List<JTipoSexoTraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(List<JTipoSexoTraduccion> descripcion) {
        if (this.descripcion == null || this.descripcion.isEmpty()) {
            this.descripcion = descripcion;
        } else {
            this.descripcion.addAll(descripcion);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JTipoSexo tipoSexo = (JTipoSexo) o;
        return Objects.equals(codigo, tipoSexo.codigo) && Objects.equals(identificador, tipoSexo.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador);
    }
}
