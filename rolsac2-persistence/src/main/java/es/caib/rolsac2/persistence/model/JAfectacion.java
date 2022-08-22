package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "afectacion-sequence", sequenceName = "RS2_AFECTA_SEQ", allocationSize = 1)
@Table(name = "RS2_AFECTA",
        indexes = {
                @Index(name = "RS2_AFECTA_PK_I", columnList = "AFNO_CODIGO")
        })
public class JAfectacion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "afectacion-sequence")
    @Column(name = "AFNO_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AFNO_TIPAFNO", nullable = false)
    private JTipoAfectacion tipoAfectacion;

    @Column(name = "AFNO_NORORG", nullable = false)
    private Long normativaOrigen;

    @Column(name = "AFNO_NORAFE", nullable = false)
    private Long normativaAfectada;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JTipoAfectacion getTipoAfectacion() {
        return tipoAfectacion;
    }

    public void setTipoAfectacion(JTipoAfectacion afnoTipafno) {
        this.tipoAfectacion = afnoTipafno;
    }

    public Long getNormativaOrigen() {
        return normativaOrigen;
    }

    public void setNormativaOrigen(Long normativaOrigen) {
        this.normativaOrigen = normativaOrigen;
    }
}