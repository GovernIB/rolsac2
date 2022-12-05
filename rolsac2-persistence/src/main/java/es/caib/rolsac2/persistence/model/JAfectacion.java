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
public class JAfectacion extends BaseEntity {

    /**
     * Código
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "afectacion-sequence")
    @Column(name = "AFNO_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Tipo afectación
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AFNO_TIPAFNO", nullable = false)
    private JTipoAfectacion tipoAfectacion;

    /**
     * Normativa origen
     */
    @Column(name = "AFNO_NORORG", nullable = false)
    private Long normativaOrigen;

    /**
     * Normativa afectada
     */
    @Column(name = "AFNO_NORAFE", nullable = false)
    private Long normativaAfectada;


    /**
     * Obtiene el codigo.
     *
     * @return codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo
     *
     * @param codigo
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el tipo.
     *
     * @return tipo
     */
    public JTipoAfectacion getTipoAfectacion() {
        return tipoAfectacion;
    }

    /**
     * Establece el codigo
     *
     * @param tipoAfect
     */
    public void setTipoAfectacion(JTipoAfectacion tipoAfect) {
        this.tipoAfectacion = tipoAfect;
    }

    /**
     * Obtiene la normativa origen.
     *
     * @return normativaOrigen
     */
    public Long getNormativaOrigen() {
        return normativaOrigen;
    }

    /**
     * Establece la normativa origen
     *
     * @param normativaOrigen
     */
    public void setNormativaOrigen(Long normativaOrigen) {
        this.normativaOrigen = normativaOrigen;
    }

    /**
     * Obtiene la normativa afectada.
     *
     * @return normativaAfectada
     */
    public Long getNormativaAfectada() {
        return normativaAfectada;
    }

    /**
     * Establece la normativa afectada
     *
     * @param normativaAfectada
     */
    public void setNormativaAfectada(Long normativaAfectada) {
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