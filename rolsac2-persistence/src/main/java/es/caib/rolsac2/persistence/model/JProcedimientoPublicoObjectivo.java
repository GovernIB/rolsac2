package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoPublicoObjectivoPK;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;

import javax.persistence.*;

/**
 * La clase J procedimiento publico objectivo.
 */
@Entity
@Table(name = "RS2_PRCPUB")
public class JProcedimientoPublicoObjectivo {
    /**
     * Codigo
     */
    @EmbeddedId
    private JProcedimientoPublicoObjectivoPK codigo;

    /**
     * Procedimiento
     */
    @MapsId("procedimiento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRPO_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimiento;

    /**
     * Tipo publico objetivo
     */
    @MapsId("tipoPublicoObjetivo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRPO_TIPPOBJ", nullable = false)
    private JTipoPublicoObjetivoEntidad tipoPublicoObjetivo;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public JProcedimientoPublicoObjectivoPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(JProcedimientoPublicoObjectivoPK id) {
        this.codigo = id;
    }

    /**
     * Obtiene procedimiento.
     *
     * @return  procedimiento
     */
    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece procedimiento.
     *
     * @param prpoCodprwf  prpo codprwf
     */
    public void setProcedimiento(JProcedimientoWorkflow prpoCodprwf) {
        this.procedimiento = prpoCodprwf;
    }

    /**
     * Obtiene tipo publico objetivo.
     *
     * @return  tipo publico objetivo
     */
    public JTipoPublicoObjetivoEntidad getTipoPublicoObjetivo() {
        return tipoPublicoObjetivo;
    }

    /**
     * Establece tipo publico objetivo.
     *
     * @param prpoTippobj  prpo tippobj
     */
    public void setTipoPublicoObjetivo(JTipoPublicoObjetivoEntidad prpoTippobj) {
        this.tipoPublicoObjetivo = prpoTippobj;
    }

    /**
     * To model tipo publico objetivo entidad grid dto.
     *
     * @return  tipo publico objetivo entidad grid dto
     */
    public TipoPublicoObjetivoEntidadGridDTO toModel() {
        TipoPublicoObjetivoEntidadGridDTO tipo = this.getTipoPublicoObjetivo().toModel();
        tipo.setCodigoProcWF(this.getProcedimiento().getCodigo());
        return tipo;
    }

}