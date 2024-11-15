package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoPublicoObjetivoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.Traduccion;

import javax.persistence.*;
import java.util.List;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
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
                query = "select p from JTipoPublicoObjetivo p where p.codigo = :id"),
        @NamedQuery(name = JTipoPublicoObjetivo.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JTipoPublicoObjetivo p where p.identificador = :identificador")
})
public class JTipoPublicoObjetivo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "JTipoPublicoObjetivo.FIND_BY_ID";

    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "JTipoPublicoObjetivo.COUNT_BY_IDENTIFICADOR";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-publicoobj-sequence")
    @Column(name = "TPPO_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Descripcion
     */
    @OneToMany(mappedBy = "tipoPublicoObjetivo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoPublicoObjetivoTraduccion> descripcion;

    /**
     * Identificador
     */
    @Column(name = "TPPO_IDENTI", length = 50)
    private String identificador;

    /**
     * Es empleado publico
     */
    @Column(name = "TPPO_EMPPUB", nullable = false, precision = 1, scale = 0)
    private boolean empleadoPublico;


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
    public List<JTipoPublicoObjetivoTraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene descripcion.
     *
     * @param idioma idioma
     * @return descripcion
     */
    public String getDescripcion(String idioma) {
        if (descripcion == null || descripcion.isEmpty()) {
            return "";
        }
        for (JTipoPublicoObjetivoTraduccion trad : this.descripcion) {
            if (trad.getIdioma() != null && idioma.equalsIgnoreCase(idioma)) {
                return trad.getDescripcion();
            }
        }
        return "";
    }

    public boolean isEmpleadoPublico() {
        return empleadoPublico;
    }

    public void setEmpleadoPublico(boolean empleadoPublico) {
        this.empleadoPublico = empleadoPublico;
    }


    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(List<JTipoPublicoObjetivoTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "JTipoPublicoObjetivo{" +
                "id=" + codigo +
                ", identificador=" + identificador +
                ", empleadoPublico=" + empleadoPublico +
                '}';
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
        if (this.getDescripcion() != null) {
            for (JTipoPublicoObjetivoTraduccion trad : this.getDescripcion()) {
                literal.add(new Traduccion(trad.getIdioma(), trad.getDescripcion()));
            }
        }
        return tipo;
    }
}