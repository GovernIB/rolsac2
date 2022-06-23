package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoNormativaTraduccion;

import javax.persistence.*;
import java.util.List;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-normativa-sequence", sequenceName = "RS2_TIPONOR_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPONOR",
        indexes = {
                @Index(name = "RS2_TIPONOR_PK", columnList = "TPNO_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoNormativa.FIND_BY_ID,
                query = "select p from JTipoNormativa p where p.id = :id"),
        @NamedQuery(name = JTipoNormativa.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JTipoNormativa p where p.identificador = :identificador")
})
public class JTipoNormativa extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoNormativa.FIND_BY_ID";

    public static final String COUNT_BY_IDENTIFICADOR = "TipoNormativa.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-normativa-sequence")
    @Column(name = "TPNO_CODIGO", nullable = false, length = 10)
    private Long id;

    @Column(name = "TPNO_IDENTI", length = 50)
    private String identificador;

    @OneToMany(mappedBy="tipoNormativa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoNormativaTraduccion> descripcion;

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

    public List<JTipoNormativaTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoNormativaTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "JTipoNormativa{" +
                "id=" + id +
                "identificador=" + identificador +
                "descripcion=" + descripcion +
                '}';
    }

}