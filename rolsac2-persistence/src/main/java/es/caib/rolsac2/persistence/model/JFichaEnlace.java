package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ficha-enlace-sequence", sequenceName = "RS2_FCHENLA_SEQ", allocationSize = 1)
@Table(name = "RS2_FCHENLA",
        indexes = {
                @Index(name = "RS2_FCHENLA_PK_I", columnList = "FCEN_CODIGO")
        })
public class JFichaEnlace {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-enlace-sequence")
    @Column(name = "FCEN_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCEN_CODFCH", nullable = false)
    private JFicha ficha;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JFicha getFicha() {
        return ficha;
    }

    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }
}