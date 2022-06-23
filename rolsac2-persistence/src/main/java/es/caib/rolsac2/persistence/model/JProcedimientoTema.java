package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoTemaPK;

import javax.persistence.*;

@Entity
@Table(name = "RS2_PRCTEM")
public class JProcedimientoTema {
    @EmbeddedId
    private JProcedimientoTemaPK id;

    @MapsId("tema")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTM_CODTEMA", nullable = false)
    private JTema tema;

    @MapsId("procedimiento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTM_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimiento;


    public JProcedimientoTemaPK getId() {
        return id;
    }

    public void setId(JProcedimientoTemaPK id) {
        this.id = id;
    }

    public JTema getTema() {
        return tema;
    }

    public void setTema(JTema prtmCodtema) {
        this.tema = prtmCodtema;
    }

    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimientoWorkflow prtmCodprwf) {
        this.procedimiento = prtmCodprwf;
    }

}