package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoNormativaPK;
import es.caib.rolsac2.persistence.model.traduccion.JNormativaTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.ProcedimientoNormativaDTO;
import es.caib.rolsac2.service.model.Traduccion;

import javax.persistence.*;

@Entity
@Table(name = "RS2_PRCNOR")
public class JProcedimientoNormativa {
    @EmbeddedId
    private JProcedimientoNormativaPK codigo;

    @MapsId("procedimiento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRWF_CODIGO", nullable = false)
    private JProcedimientoWorkflow procedimiento;

    @MapsId("normativa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NORM_CODIGO", nullable = false)
    private JNormativa normativa;

    public JProcedimientoNormativaPK getCodigo() {
        return codigo;
    }

    public void setCodigo(JProcedimientoNormativaPK codigo) {
        this.codigo = codigo;
    }

    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimientoWorkflow procedimiento) {
        this.procedimiento = procedimiento;
    }

    public JNormativa getNormativa() {
        return normativa;
    }

    public void setNormativa(JNormativa normativa) {
        this.normativa = normativa;
    }

    public NormativaGridDTO toModelGrid() {
        NormativaGridDTO normativa = new NormativaGridDTO();
        normativa.setCodigo(this.getNormativa().getCodigo());
        //normativa.setBoletinOficial(this.getNormativa().getBoletinOficial());
        //normativa.setTipoNormativa(this.getNormativa().getTipoNormativa());
        //normativa.setFechaAprobacion(this.getNormativa().getFechaAprobacion());
        normativa.setNumero(this.getNormativa().getNumero());
        Literal titulo = new Literal();
        for (JNormativaTraduccion trad : this.getNormativa().getDescripcion()) {
            titulo.add(new Traduccion(trad.getIdioma(), trad.getTitulo()));
        }
        normativa.setTitulo(titulo);
        return normativa;
    }

    public ProcedimientoNormativaDTO toModelProc() {
        ProcedimientoNormativaDTO procedimiento = new ProcedimientoNormativaDTO();
        procedimiento.setCodigo(this.getProcedimiento().getCodigo());
        Literal nombre = new Literal();
        for(JProcedimientoWorkflowTraduccion trad : this.getProcedimiento().getTraducciones()) {
            nombre.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
        }
        procedimiento.setNombre(nombre);
        return procedimiento;
    }
}