package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoPublicoObjectivoPK;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;

import javax.persistence.*;

@Entity
@Table(name = "RS2_PRCPUB")
public class JProcedimientoPublicoObjectivo {
    @EmbeddedId
    private JProcedimientoPublicoObjectivoPK codigo;

    @MapsId("procedimiento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRPO_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimiento;

    @MapsId("tipoPublicoObjetivo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRPO_TIPPOBJ", nullable = false)
    private JTipoPublicoObjetivoEntidad tipoPublicoObjetivo;

    public JProcedimientoPublicoObjectivoPK getCodigo() {
        return codigo;
    }

    public void setCodigo(JProcedimientoPublicoObjectivoPK id) {
        this.codigo = id;
    }

    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimientoWorkflow prpoCodprwf) {
        this.procedimiento = prpoCodprwf;
    }

    public JTipoPublicoObjetivoEntidad getTipoPublicoObjetivo() {
        return tipoPublicoObjetivo;
    }

    public void setTipoPublicoObjetivo(JTipoPublicoObjetivoEntidad prpoTippobj) {
        this.tipoPublicoObjetivo = prpoTippobj;
    }

    public TipoPublicoObjetivoEntidadGridDTO toModel() {
        TipoPublicoObjetivoEntidadGridDTO tipo = this.getTipoPublicoObjetivo().toModel();
        tipo.setCodigoProcWF(this.getProcedimiento().getCodigo());
        return tipo;
    }

}