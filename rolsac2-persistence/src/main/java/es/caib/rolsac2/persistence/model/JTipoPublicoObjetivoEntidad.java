package es.caib.rolsac2.persistence.model;

import com.sun.mail.util.LineInputStream;
import es.caib.rolsac2.persistence.model.traduccion.JTipoPublicoObjetivoEntidadTraduccion;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;

import javax.persistence.*;
import java.util.List;

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

    public static final String FIND_BY_ID = "JTipoPublicoObjetivoEntidad.FIND_BY_ID";

    public static final String COUNT_BY_IDENTIFICADOR = "JTipoPublicoObjetivoEntidad.COUNT_BY_IDENTIFICADOR";


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-publicoobj-ent-sequence")
    @Column(name = "TPSP_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPSP_CODENTI", nullable = false)
    private JEntidad entidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPSP_CODTPPO", nullable = false)
    private JTipoPublicoObjetivo tipo;

    @Column(name = "TPSP_IDENTI", nullable = false, length = 10)
    private String identificador;


    @OneToMany(mappedBy = "tipoPublicoObjetivoEntidad", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoPublicoObjetivoEntidadTraduccion> traducciones;

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

    public JTipoPublicoObjetivo getTipo() {
        return tipo;
    }

    public void setTipo(JTipoPublicoObjetivo tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public List<JTipoPublicoObjetivoEntidadTraduccion> getTraducciones() {
        return traducciones;
    }

    public void setTraducciones(List<JTipoPublicoObjetivoEntidadTraduccion> traducciones) {
        this.traducciones = traducciones;
    }

    @Override
    public String toString() {
        return "JTipoPublicoObjetivoEntidad{" +
                "codigo=" + codigo +
                ", entidad=" + entidad +
                ", tipo=" + tipo +
                ", identificador='" + identificador + '\'' +
                ", traducciones=" + traducciones +
                '}';
    }

    public TipoPublicoObjetivoEntidadGridDTO toModel() {
        TipoPublicoObjetivoEntidadGridDTO tipo = new TipoPublicoObjetivoEntidadGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        /*Literal literal = new Literal();
        if (this.getTipo() != null && this.getTipo().getDescripcion() != null) {
            for (JTipoPublicoObjetivoTraduccion trad : this.getTipo().getDescripcion()) {
                literal.add(new Traduccion(trad.getIdioma(), trad.getDescripcion()));
            }

        }*/
        return tipo;
    }


}