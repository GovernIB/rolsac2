package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoNormativaPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RS2_PRCNOR")
public class JProcedimientoNormativa {

    //RS2_PRCNOR_PK
    @EmbeddedId
    private JProcedimientoNormativaPK id;/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_CODIGO")
    private JProcedimientoWorkflow procedimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NORM_CODIGO")
    private JNormativa normativa;

    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimientoWorkflow prwfCodigo) {
        this.procedimiento = prwfCodigo;
    }

    public JNormativa getNormativa() {
        return normativa;
    }

    public void setNormativa(JNormativa normCodigo) {
        this.normativa = normCodigo;
    }
*/
}