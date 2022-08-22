package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "tram-modelo-sequence", sequenceName = "RS2_TRMSOL_SEQ", allocationSize = 1)
@Table(name = "RS2_TRMSOL",
        indexes = {
                @Index(name = "RS2_TRMSOL_PK_I", columnList = "PRTR_CODIGO")
        }
)
public class JTramiteModeloSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tram-modelo-sequence")
    @Column(name = "PRTR_CODIGO", nullable = false)
    private Integer codigo;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }
}