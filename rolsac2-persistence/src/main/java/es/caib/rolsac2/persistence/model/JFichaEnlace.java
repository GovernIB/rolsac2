package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J ficha enlace.
 */
@Entity
@SequenceGenerator(name = "ficha-enlace-sequence", sequenceName = "RS2_FCHENLA_SEQ", allocationSize = 1)
@Table(name = "RS2_FCHENLA",
        indexes = {
                @Index(name = "RS2_FCHENLA_PK_I", columnList = "FCEN_CODIGO")
        })
public class JFichaEnlace {
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-enlace-sequence")
    @Column(name = "FCEN_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Ficha
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCEN_CODFCH", nullable = false)
    private JFicha ficha;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    /**
     * Obtiene ficha.
     *
     * @return  ficha
     */
    public JFicha getFicha() {
        return ficha;
    }

    /**
     * Establece ficha.
     *
     * @param ficha  ficha
     */
    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }
}