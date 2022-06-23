package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoPublicoObjetivoTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoSilencioAdministrativoTraduccion;

import javax.persistence.*;
import java.util.List;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author jsegovia
 */
@Entity
@SequenceGenerator(name = "tipo-publicoobj-sequence", sequenceName = "RS2_TIPOPUB_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOPUB",
        indexes = {
                @Index(name = "RS2_TIPOPUB_PK", columnList = "TPPO_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoPublicoObjetivo.FIND_BY_ID,
                query = "select p from JTipoPublicoObjetivo p where p.id = :id"),
        @NamedQuery(name = JTipoPublicoObjetivo.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JTipoPublicoObjetivo p where p.identificador = :identificador")
})
public class JTipoPublicoObjetivo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoPublicoObjetivo.FIND_BY_ID";

    public static final String COUNT_BY_IDENTIFICADOR = "JTipoPublicoObjetivo.COUNT_BY_IDENTIFICADOR";


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-publicoobj-sequence")
    @Column(name = "TPPO_CODIGO", nullable = false, length = 10)
    private Long id;

    @OneToMany(mappedBy="tipoPublicoObjetivo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoPublicoObjetivoTraduccion> descripcion;

    @Column(name = "TPPO_IDENTI", length = 50)
    private String identificador;

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

    public List<JTipoPublicoObjetivoTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoPublicoObjetivoTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "JTipoPublicoObjetivo{" +
                "id=" + id +
                "identificador=" + identificador +
                "descripcion=" + descripcion +
                '}';
    }

}