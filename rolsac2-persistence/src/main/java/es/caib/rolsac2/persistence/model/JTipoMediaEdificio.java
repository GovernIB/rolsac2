package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoMediaEdificioTraduccion;

import javax.persistence.*;
import java.util.List;

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

    public static final String FIND_BY_ID = "TipoMediaEdificio.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoMediaEdificio.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoMediaEdificio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoMediaEdificioTraduccion> descripcion;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-edif-sequence")
    @Column(name = "TPME_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPME_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "TPME_IDENTI", nullable = false, length = 50)
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

    public void setEntidad(JEntidad tpmeCodenti) {
        this.entidad = tpmeCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String tpmeIdenti) {
        this.identificador = tpmeIdenti;
    }

    public List<JTipoMediaEdificioTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(
            List<JTipoMediaEdificioTraduccion> descripcion
    ) {
        this.descripcion = descripcion;
    }
}