package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoMediaEdificioTraduccion;

import javax.persistence.*;
import java.util.List;

/**
 * La clase J tipo media edificio.
 */
@Entity
@SequenceGenerator(name = "fichero-tipo-edif-sequence", sequenceName = "RS2_TIPOMEDEDI_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOMEDEDI",
        indexes = {
                @Index(name = "RS2_TIPOMEDEDI_PK", columnList = "TPME_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JTipoMediaEdificio.FIND_BY_ID,
                query = "select p from JTipoMediaEdificio p where p.codigo = :id"),
        @NamedQuery(name = JTipoMediaEdificio.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoMediaEdificio p where p.entidad.codigo = :entidad and lower(p.identificador) like :identificador")
})
public class JTipoMediaEdificio {

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "TipoMediaEdificio.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "TipoMediaEdificio.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoMediaEdificio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoMediaEdificioTraduccion> descripcion;

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-edif-sequence")
    @Column(name = "TPME_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPME_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Identificador
     */
    @Column(name = "TPME_IDENTI", nullable = false, length = 50)
    private String identificador;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public JEntidad getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param tpmeCodenti  tpme codenti
     */
    public void setEntidad(JEntidad tpmeCodenti) {
        this.entidad = tpmeCodenti;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param tpmeIdenti  tpme identi
     */
    public void setIdentificador(String tpmeIdenti) {
        this.identificador = tpmeIdenti;
    }

    /**
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public List<JTipoMediaEdificioTraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
    public void setDescripcion(
            List<JTipoMediaEdificioTraduccion> descripcion
    ) {
        this.descripcion = descripcion;
    }
}