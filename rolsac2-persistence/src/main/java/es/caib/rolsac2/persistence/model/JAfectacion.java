package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Clase JAfectacion
 */
@Entity
@SequenceGenerator(name = "afectacion-sequence", sequenceName = "RS2_AFECTA_SEQ", allocationSize = 1)
@Table(name = "RS2_AFECTA",
        indexes = {
                @Index(name = "RS2_AFECTA_PK_I", columnList = "AFNO_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JAfectacion.FIND_BY_ID,
                query = "select p from JAfectacion p where p.codigo = :codigo"),
        @NamedQuery(name = JAfectacion.FIND_BY_NORMATIVA_AFECTADA,
                query="select p from JAfectacion p left outer join p.normativaAfectada t where t.codigo=:codigo"),
        @NamedQuery(name = JAfectacion.FIND_BY_NORMATIVA_ORIGEN,
                query="select p from JAfectacion p left outer join p.normativaOrigen t where t.codigo=:codigo")
})
public class JAfectacion extends BaseEntity {

    public static final String FIND_BY_ID = "afectacion.FIND_BY_ID";
    public static final String FIND_BY_NORMATIVA_AFECTADA = "afectacion.FIND_BY_NORMATIVA_AFECTADA";

    public static final String FIND_BY_NORMATIVA_ORIGEN = "afectacion.FIND_BY_NORMATIVA_ORIGEN";

    /**
     * Código
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "afectacion-sequence")
    @Column(name = "AFNO_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Tipo afectación
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AFNO_TIPAFNO", nullable = false)
    private JTipoAfectacion tipoAfectacion;

    /**
     * Normativa origen
     */
    @ManyToOne
    @JoinColumn(name = "AFNO_NORORG", nullable = false)
    private JNormativa normativaOrigen;

    /**
     * Normativa afectada
     */
    @ManyToOne
    @JoinColumn(name = "AFNO_NORAFE", nullable = false)
    private JNormativa normativaAfectada;


    /**
     * Obtiene el codigo.
     *
     * @return codigo codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo
     *
     * @param codigo the codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el tipo.
     *
     * @return tipo tipo afectacion
     */
    public JTipoAfectacion getTipoAfectacion() {
        return tipoAfectacion;
    }

    /**
     * Establece el codigo
     *
     * @param tipoAfect the tipo afect
     */
    public void setTipoAfectacion(JTipoAfectacion tipoAfect) {
        this.tipoAfectacion = tipoAfect;
    }

    /**
     * Obtiene la normativa origen.
     *
     * @return normativaOrigen normativa origen
     */
    public JNormativa getNormativaOrigen() {
        return normativaOrigen;
    }

    /**
     * Establece la normativa origen
     *
     * @param normativaOrigen the normativa origen
     */
    public void setNormativaOrigen(JNormativa normativaOrigen) {
        this.normativaOrigen = normativaOrigen;
    }

    /**
     * Obtiene la normativa afectada.
     *
     * @return normativaAfectada normativa afectada
     */
    public JNormativa getNormativaAfectada() {
        return normativaAfectada;
    }

    /**
     * Establece la normativa afectada
     *
     * @param normativaAfectada the normativa afectada
     */
    public void setNormativaAfectada(JNormativa normativaAfectada) {
        this.normativaAfectada = normativaAfectada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JAfectacion that = (JAfectacion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JAfectacion{" +
                "codigo=" + codigo +
                ", tipoAfectacion=" + tipoAfectacion +
                ", normativaOrigen=" + normativaOrigen +
                ", normativaAfectada=" + normativaAfectada +
                '}';
    }
}