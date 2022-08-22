package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoMediaUATraduccion;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "fichero-tipo-ua-sequence", sequenceName = "RS2_TIPOMEDUA_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOMEDUA",
        indexes = {
                @Index(name = "RS2_TIPOMEDUA_PK", columnList = "TPMU_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JTipoMediaUA.FIND_BY_ID,
                query = "select p from JTipoMediaUA p where p.codigo = :id"),
        @NamedQuery(name = JTipoMediaUA.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoMediaUA p where p.entidad.codigo = :entidad and lower(p.identificador) = :identificador")
})
public class JTipoMediaUA {
    public static final String FIND_BY_ID = "TipoMediaUA.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoMediaUA.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;

    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoMediaUA", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoMediaUATraduccion> descripcion;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-ua-sequence")
    @Column(name = "TPMU_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPMU_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "TPMU_IDENTI", nullable = false, length = 50)
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

    public void setEntidad(JEntidad tpmuCodenti) {
        this.entidad = tpmuCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String tpmuIdenti) {
        this.identificador = tpmuIdenti;
    }

    public List<JTipoMediaUATraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(
            List<JTipoMediaUATraduccion> descripcion
    ) {
        this.descripcion = descripcion;
    }
}