package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoTasaTraduccion;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * Entidad JPA para la tabla RS2_PRCTAX (Tasas de procedimiento/trámite).
 */
@Entity
@SequenceGenerator(name = "procedimiento-tasa-sequence", sequenceName = "RS2_PRCTAX_SEQ", allocationSize = 1)
@Table(name = "RS2_PRCTAX", indexes = { @Index(name = "RS2_PRCTAX_PK_I", columnList = "PRTX_CODIGO") })
public class JProcedimientoTasa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-tasa-sequence")
    @Column(name = "PRTX_CODIGO", nullable = false)
    private Long codigo;

    @Column(name = "PRTX_CODPRWF", nullable = false)
    private Long idPadre;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRTX_CODPRWF", insertable = false, updatable = false)
    private JProcedimientoTramite tramite;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRTX_CODPRWF", insertable = false, updatable = false)
    private JProcedimientoWorkflow servicio;

    @OneToMany(mappedBy = "tasa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JProcedimientoTasaTraduccion> traducciones;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public JProcedimientoTramite getTramite() {
        return tramite;
    }

    public void setTramite(JProcedimientoTramite tramite) {
        this.tramite = tramite;
        if (tramite != null) {
            this.idPadre = tramite.getCodigo();
        }
    }

    public JProcedimientoWorkflow getServicio() {
        return servicio;
    }

    public void setServicio(JProcedimientoWorkflow servicio) {
        this.servicio = servicio;
        if (servicio != null) {
            this.idPadre = servicio.getCodigo();
        }
    }

    public List<JProcedimientoTasaTraduccion> getTraducciones() {
        return traducciones;
    }

    public void setTraducciones(List<JProcedimientoTasaTraduccion> traducciones) {
        this.traducciones = traducciones;
    }
}
