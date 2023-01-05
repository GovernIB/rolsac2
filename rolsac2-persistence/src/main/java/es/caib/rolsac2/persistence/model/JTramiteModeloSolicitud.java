package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J tramite modelo solicitud.
 */
@Entity
@SequenceGenerator(name = "tram-modelo-sequence", sequenceName = "RS2_TRMSOL_SEQ", allocationSize = 1)
@Table(name = "RS2_TRMSOL",
        indexes = {
                @Index(name = "RS2_TRMSOL_PK_I", columnList = "PRTR_CODIGO")
        }
)
public class JTramiteModeloSolicitud {

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tram-modelo-sequence")
    @Column(name = "PRTR_CODIGO", nullable = false)
    private Integer codigo;

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
}