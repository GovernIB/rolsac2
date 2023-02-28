package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoNormativaPK;
import es.caib.rolsac2.persistence.model.traduccion.JNormativaTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.ProcedimientoNormativaDTO;
import es.caib.rolsac2.service.model.Traduccion;

import javax.persistence.*;

/**
 * La clase J procedimiento normativa.
 */
@Entity
@Table(name = "RS2_PRCNOR")
public class JProcedimientoNormativa {
    /**
     * Codigo
     */
    @EmbeddedId
    private JProcedimientoNormativaPK codigo;

    /**
     * Procedimiento
     */
    @MapsId("procedimiento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRWF_CODIGO", nullable = false)
    private JProcedimientoWorkflow procedimiento;

    /**
     * Fase
     */
    @Column(name = "PRNO_ORDEN")
    private Integer orden;

    /**
     * Normativa
     */
    @MapsId("normativa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NORM_CODIGO", nullable = false)
    private JNormativa normativa;


    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public JProcedimientoNormativaPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(JProcedimientoNormativaPK codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene procedimiento.
     *
     * @return procedimiento
     */
    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece procedimiento.
     *
     * @param procedimiento procedimiento
     */
    public void setProcedimiento(JProcedimientoWorkflow procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * Obtiene normativa.
     *
     * @return normativa
     */
    public JNormativa getNormativa() {
        return normativa;
    }

    /**
     * Establece normativa.
     *
     * @param normativa normativa
     */
    public void setNormativa(JNormativa normativa) {
        this.normativa = normativa;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * To model grid normativa grid dto.
     *
     * @return normativa grid dto
     */
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
        normativa.setOrden(this.getOrden());
        normativa.setTitulo(titulo);
        return normativa;
    }

    /**
     * To model proc procedimiento normativa dto.
     *
     * @return procedimiento normativa dto
     */
    public ProcedimientoNormativaDTO toModelProc() {
        ProcedimientoNormativaDTO procedimiento = new ProcedimientoNormativaDTO();
        procedimiento.setCodigo(this.getProcedimiento().getCodigo());
        procedimiento.setOrden(this.getOrden());
        Literal nombre = new Literal();
        for (JProcedimientoWorkflowTraduccion trad : this.getProcedimiento().getTraducciones()) {
            nombre.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
        }
        procedimiento.setNombre(nombre);
        return procedimiento;
    }
}