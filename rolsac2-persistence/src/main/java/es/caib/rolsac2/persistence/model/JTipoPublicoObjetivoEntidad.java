package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoPublicoObjetivoEntidadTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.Traduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-publicoobj-ent-sequence", sequenceName = "RS2_TIPOSPU_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOSPU",
        indexes = {
                @Index(name = "RS2_TIPOSPU_PK", columnList = "TPSP_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoPublicoObjetivoEntidad.FIND_BY_ID,
                query = "select p from JTipoPublicoObjetivoEntidad p where p.codigo = :id"),
        @NamedQuery(name = JTipoPublicoObjetivoEntidad.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JTipoPublicoObjetivoEntidad p where p.identificador = :identificador")
})
public class JTipoPublicoObjetivoEntidad extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "JTipoPublicoObjetivoEntidad.FIND_BY_ID";

    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "JTipoPublicoObjetivoEntidad.COUNT_BY_IDENTIFICADOR";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-publicoobj-ent-sequence")
    @Column(name = "TPSP_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPSP_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Tipo
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPSP_CODTPPO", nullable = false)
    private JTipoPublicoObjetivo tipo;

    /**
     * Identificador
     */
    @Column(name = "TPSP_IDENTI", nullable = false, length = 50)
    private String identificador;

    /**
     * Traducciones
     */
    @OneToMany(mappedBy = "tipoPublicoObjetivoEntidad", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoPublicoObjetivoEntidadTraduccion> traducciones;

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
     * Obtiene tipo.
     *
     * @return tipo
     */
    public JTipoPublicoObjetivo getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo tipo
     */
    public void setTipo(JTipoPublicoObjetivo tipo) {
        this.tipo = tipo;
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
    public List<JTipoPublicoObjetivoEntidadTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Establece traducciones.
     *
     * @param traducciones traducciones
     */
    public void setTraducciones(List<JTipoPublicoObjetivoEntidadTraduccion> traducciones) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = traducciones;
        } else {
            this.traducciones.addAll(traducciones);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("JTipoPublicoObjetivoEntidad{");
        if (codigo != null) {
            sb.append("codigo=").append(codigo);
        }
        /*if (entidad != null) {
            sb.append(", entidad=").append(entidad);
        }*/
        if (tipo != null) {
            sb.append(", tipo=").append(tipo);
        }

        if (identificador != null) {
            sb.append(", identificador='").append(identificador).append('\'');
        }
        if (traducciones != null) {
            sb.append(", traducciones=").append(traducciones);
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * To model tipo publico objetivo entidad grid dto.
     *
     * @return tipo publico objetivo entidad grid dto
     */
    public TipoPublicoObjetivoEntidadGridDTO toModel() {
        TipoPublicoObjetivoEntidadGridDTO tipo = new TipoPublicoObjetivoEntidadGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        Literal literal = new Literal();
        if (this.getTraducciones() != null) {
            for (JTipoPublicoObjetivoEntidadTraduccion trad : this.getTraducciones()) {
                literal.add(new Traduccion(trad.getIdioma(), trad.getDescripcion()));
            }
        }
        tipo.setDescripcion(literal);
        tipo.setEmpleadoPublico(this.getTipo().isEmpleadoPublico());
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JTipoPublicoObjetivoEntidad that = (JTipoPublicoObjetivoEntidad) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}