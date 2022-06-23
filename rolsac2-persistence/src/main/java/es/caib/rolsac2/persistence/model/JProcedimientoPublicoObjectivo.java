package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoPublicoObjectivoPK;

import javax.persistence.*;

@Entity
@Table(name = "RS2_PRCPUB")
public class JProcedimientoPublicoObjectivo {
    @EmbeddedId
    private JProcedimientoPublicoObjectivoPK id;

    @MapsId("procedimiento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRPO_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimiento;

    @MapsId("tipoPublicoObjetivo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRPO_TIPPOBJ", nullable = false)
    private JTipoPublicoObjetivo tipoPublicoObjetivo;

    public JProcedimientoPublicoObjectivoPK getId() {
        return id;
    }

    public void setId(JProcedimientoPublicoObjectivoPK id) {
        this.id = id;
    }

    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimientoWorkflow prpoCodprwf) {
        this.procedimiento = prpoCodprwf;
    }

    public JTipoPublicoObjetivo getTipoPublicoObjetivo() {
        return tipoPublicoObjetivo;
    }

    public void setTipoPublicoObjetivo(JTipoPublicoObjetivo prpoTippobj) {
        this.tipoPublicoObjetivo = prpoTippobj;
    }

}