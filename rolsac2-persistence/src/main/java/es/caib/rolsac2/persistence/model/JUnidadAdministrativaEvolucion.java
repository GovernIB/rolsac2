package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "ua-evol-sequence", sequenceName = "RS2_UNAEVO_SEQ", allocationSize = 1)
@Table(name = "RS2_UNAEVO",
        indexes = {
                @Index(name = "RS2_UNAEVO_PK_I", columnList = "UAEV_CODIGO")
        }
)
public class JUnidadAdministrativaEvolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-evol-sequence")
    @Column(name = "UAEV_CODIGO", nullable = false)
    private Integer codigo;

    @Column(name = "UAEV_TIPO", nullable = false, length = 1)
    private String tipo;

    @Column(name = "UAEV_FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UAEV_UADEP")
    private JUnidadAdministrativa unidadAdministrativa;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String uaevTipo) {
        this.tipo = uaevTipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate uaevFecha) {
        this.fecha = uaevFecha;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa uaevUadep) {
        this.unidadAdministrativa = uaevUadep;
    }

}