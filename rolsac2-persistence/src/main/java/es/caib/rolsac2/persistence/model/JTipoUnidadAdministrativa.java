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
@Table(name = "RS2_TIPOUNA",
        indexes = {
                @Index(name = "RS2_TIPOUNA_PK", columnList = "TPUA_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoUnidadAdministrativa.FIND_BY_ID,
                query = "select p from JTipoUnidadAdministrativa p where p.codigo = :id"),
        @NamedQuery(name = JTipoUnidadAdministrativa.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JTipoUnidadAdministrativa p where p.entidad.codigo = :entidad and lower(p.identificador) like :identificador")
})
public class JTipoUnidadAdministrativa extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoUnidadAdministrativa.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoUnidadAdministrativa.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-ua-sequence")
    @Column(name = "TPUA_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TPUA_CODENTI", nullable = false)
    private JEntidad entidad;

    @OneToMany(mappedBy = "tipoUnidadAdministrativa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoUnidadAdministrativaTraduccion> traducciones;

    @Column(name = "TPUA_IDENTI", length = 50, unique = true)
    private String identificador;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
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

    public List<JTipoUnidadAdministrativaTraduccion> getTraducciones() {
        return traducciones;
    }

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
        return "JTipoUnidadAdministrativa{" +
                "id=" + codigo +
                ", entidad=" + entidad +
                ", traducciones=" + traducciones +
                ", identificador='" + identificador + '\'' +
                '}';
    }
}