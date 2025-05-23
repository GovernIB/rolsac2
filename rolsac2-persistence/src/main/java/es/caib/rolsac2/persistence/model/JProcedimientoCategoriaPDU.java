package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoCategoriaPDUPK;
import es.caib.rolsac2.persistence.model.traduccion.JCategoriaPDUTraduccion;
import es.caib.rolsac2.service.model.CategoriaPDUGridDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase J procedimiento categoria PDU.
 */
@Entity
@Table(name = "RS2_PRCPDU")
public class JProcedimientoCategoriaPDU {
    /**
     * Codigo
     */
    @EmbeddedId
    private JProcedimientoCategoriaPDUPK codigo;

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
    @Column(name = "PRCP_ORDEN")
    private Integer orden;

    /**
     * Normativa
     */
    @MapsId("categoriaPDU")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CPDU_CODIGO", nullable = false)
    private JCategoriaPDU categoriaPDU;


    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public JProcedimientoCategoriaPDUPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(JProcedimientoCategoriaPDUPK codigo) {
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

    public JCategoriaPDU getCategoriaPDU() {
        return categoriaPDU;
    }

    public void setCategoriaPDU(JCategoriaPDU categoriaPDU) {
        this.categoriaPDU = categoriaPDU;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }


    /**
     * To model proc procedimiento normativa dto.
     *
     * @return procedimiento normativa dto
     */
    public JProcedimientoCategoriaPDUPK toModelProc() {
        JProcedimientoCategoriaPDUPK procedimientoNormativaDTO = new JProcedimientoCategoriaPDUPK();
        procedimientoNormativaDTO.setProcedimiento(this.getCodigo().getProcedimiento());
        procedimientoNormativaDTO.setCategoriaPDU(this.getCodigo().getCategoriaPDU());
        //procedimientoNormativaDTO.setOrden(this.getOrden());
        procedimientoNormativaDTO.setProcedimiento(this.getProcedimiento().getCodigo());
        procedimientoNormativaDTO.setCategoriaPDU(this.getCategoriaPDU().getCodigo());
        return procedimientoNormativaDTO;
    }


    public static List<JProcedimientoCategoriaPDU> clonar(List<JProcedimientoCategoriaPDU> jNormativas, JProcedimientoWorkflow jprocWFClonado) {
        List<JProcedimientoCategoriaPDU> retorno = new ArrayList<>();
        if (jNormativas != null) {
            for (JProcedimientoCategoriaPDU jProcedimientoNormativa : jNormativas) {
                JProcedimientoCategoriaPDU jProcedimientoNormativaClonado = JProcedimientoCategoriaPDU.clonar(jProcedimientoNormativa, jprocWFClonado);
                retorno.add(jProcedimientoNormativaClonado);
            }
        }
        return retorno;
    }


    public static JProcedimientoCategoriaPDU clonar(JProcedimientoCategoriaPDU jprocCat, JProcedimientoWorkflow jprocWFClonado) {
        JProcedimientoCategoriaPDU retorno = null;
        if (jprocCat != null) {
            JProcedimientoCategoriaPDUPK jProcedimientoCategoriaPDUPK = new JProcedimientoCategoriaPDUPK();
            jProcedimientoCategoriaPDUPK.setProcedimiento(jprocWFClonado.getCodigo());
            jProcedimientoCategoriaPDUPK.setCategoriaPDU(jprocCat.getCodigo().getCategoriaPDU());
            retorno.setCodigo(jProcedimientoCategoriaPDUPK);
            retorno.setProcedimiento(jprocWFClonado);
            retorno.setCategoriaPDU(jprocCat.getCategoriaPDU());
            retorno.setOrden(jprocCat.getOrden());
        }
        return retorno;
    }

    public CategoriaPDUGridDTO toModelGrid() {
        CategoriaPDUGridDTO categoriaPDUGridDTO = new CategoriaPDUGridDTO();
        categoriaPDUGridDTO.setCodigo(this.getCodigo().getCategoriaPDU());
        categoriaPDUGridDTO.setIdentificador(this.getCategoriaPDU().getIdentificador());
        categoriaPDUGridDTO.setOrden(this.getOrden());
        Literal descripcion = new Literal();
        for (JCategoriaPDUTraduccion trad : this.getCategoriaPDU().getDescripcion()) {
            descripcion.add(new Traduccion(trad.getIdioma(), trad.getDescripcion()));
        }
        categoriaPDUGridDTO.setDescripcion(descripcion);
        return categoriaPDUGridDTO;
    }
}