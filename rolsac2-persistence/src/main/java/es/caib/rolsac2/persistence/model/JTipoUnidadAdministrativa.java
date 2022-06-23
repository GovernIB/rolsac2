package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoSilencioAdministrativoTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoUnidadAdministrativaTraduccion;

import javax.persistence.*;
import java.util.List;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-ua-sequence", sequenceName = "RS2_TIPOUNA_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOUNA",
        indexes = {
                @Index(name = "RS2_TIPOUNA_PK", columnList = "TPUA_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoUnidadAdministrativa.FIND_BY_ID,
                query = "select p from JTipoUnidadAdministrativa p where p.id = :id"),
        @NamedQuery(name = JTipoUnidadAdministrativa.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JTipoUnidadAdministrativa p where p.identificador = :identificador")
})
public class JTipoUnidadAdministrativa extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoUnidadAdministrativa.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoUnidadAdministrativa.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-ua-sequence")
    @Column(name = "TPUA_CODIGO", nullable = false, length = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TPUA_CODENTI")
    private JEntidad entidad;

    @OneToMany(mappedBy="tipoUnidadAdministrativa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoUnidadAdministrativaTraduccion> descripcion;

    @Column(name = "TPUA_IDENTI", length = 50, unique = true)
    private String identificador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public List<JTipoUnidadAdministrativaTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoUnidadAdministrativaTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "JTipoUnidadAdministrativa{" +
                "id=" + id +
                ", entidad=" + entidad +
                ", descripcion=" + descripcion +
                ", identificador='" + identificador + '\'' +
                '}';
    }
}