/**
 *
 */
package es.caib.rolsac2.service.model.auditoria;


import es.caib.rolsac2.service.model.ProcedimientoDTO;

import java.util.Date;

/**
 * Tiene la información de ProcedimientoAuditoriaDTO
 *
 * @author Indra
 */
public class ProcedimientoAuditoria {

    /**
     * codigo
     **/
    private Long codigo;

    /**
     * valores anteriores al último cambio
     **/
    private String valoresAnteriores;

    /**
     * fecha de auditoría
     **/
    private Date fechaAuditoria;

    /**
     * usuario auditoría
     **/
    private String usuarioAuditoria;


    /**
     * persona asociada a la auditoria
     **/
    private ProcedimientoDTO procedimientoDTO;


    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the valoresAnteriores
     */
    public String getValoresAnteriores() {
        return valoresAnteriores;
    }

    /**
     * @param valoresAnteriores the valoresAnteriores to set
     */
    public void setValoresAnteriores(String valoresAnteriores) {
        this.valoresAnteriores = valoresAnteriores;
    }

    /**
     * @return the fechaAuditoria
     */
    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    /**
     * @param fechaAuditoria the fechaAuditoria to set
     */
    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    /**
     * @return the usuarioAuditoria
     */
    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    /**
     * @param usuarioAuditoria the usuarioAuditoria to set
     */
    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    /**
     * @return the oposicion
     */
    public ProcedimientoDTO getProcedimientoDTO() {

        return procedimientoDTO;
    }

    /**
     * @param procedimientoDTO the oposicion to set
     */
    public void setProcedimientoDTO(ProcedimientoDTO procedimientoDTO) {
        this.procedimientoDTO = procedimientoDTO;
    }



}
