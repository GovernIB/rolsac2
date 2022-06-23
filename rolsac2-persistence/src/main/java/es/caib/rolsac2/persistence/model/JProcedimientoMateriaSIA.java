package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoMateriaSIAPK;

import javax.persistence.*;

@Entity
@Table(name = "RS2_PRCMAS")
public class JProcedimientoMateriaSIA {
    @EmbeddedId
    private JProcedimientoMateriaSIAPK id;

    @MapsId("procedimento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRMS_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimientoWF;

    @MapsId("tipoMateriaSIA")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRMS_TIPMSIA", nullable = false)
    private JTipoMateriaSIA tipoMateriaSIA;

    public JProcedimientoMateriaSIAPK getId() {
        return id;
    }

    public void setId(JProcedimientoMateriaSIAPK id) {
        this.id = id;
    }

    public JProcedimientoWorkflow getProcedimientoWF() {
        return procedimientoWF;
    }

    public void setProcedimientoWF(JProcedimientoWorkflow prmsCodprwf) {
        this.procedimientoWF = prmsCodprwf;
    }

    public JTipoMateriaSIA getTipoMateriaSIA() {
        return tipoMateriaSIA;
    }

    public void setTipoMateriaSIA(JTipoMateriaSIA prmsTipmsia) {
        this.tipoMateriaSIA = prmsTipmsia;
    }

}