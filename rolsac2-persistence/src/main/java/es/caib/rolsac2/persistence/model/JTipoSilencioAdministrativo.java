package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoSilencioAdministrativoTraduccion;

import javax.persistence.*;
import java.util.List;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-silencionadmvo-sequence", sequenceName = "RS2_TIPOSAD_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOSAD",
        indexes = {
                @Index(name = "RS2_TIPOSAD_PK", columnList = "TPSA_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoSilencioAdministrativo.FIND_BY_ID,
                query = "select p from JTipoSilencioAdministrativo p where p.id = :id"),
        @NamedQuery(name = JTipoSilencioAdministrativo.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JTipoSilencioAdministrativo p where p.identificador = :identificador")
})
public class JTipoSilencioAdministrativo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoSilencioAdministrativo.FIND_BY_ID";

    public static final String COUNT_BY_IDENTIFICADOR = "TipoSilencioAdministrativo.COUNT_BY_IDENTIFICADOR";


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-silencionadmvo-sequence")
    @Column(name = "TPSA_CODIGO", nullable = false, length = 10)
    private Long id;

    @Column(name = "TPSA_IDENTI", length = 50)
    private String identificador;

    @OneToMany(mappedBy="tipoSilencioAdministrativo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoSilencioAdministrativoTraduccion> descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public List<JTipoSilencioAdministrativoTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoSilencioAdministrativoTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "JTipoSilencioAdministrativo{" +
                "id=" + id +
                "identificador=" + identificador +
                "descripcion=" + descripcion +
                '}';
    }

}