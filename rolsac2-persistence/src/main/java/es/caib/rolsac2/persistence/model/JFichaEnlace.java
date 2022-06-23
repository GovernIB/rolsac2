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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCEN_CODFCH", nullable = false)
    private JFicha ficha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JFicha getFicha() {
        return ficha;
    }

    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }
}