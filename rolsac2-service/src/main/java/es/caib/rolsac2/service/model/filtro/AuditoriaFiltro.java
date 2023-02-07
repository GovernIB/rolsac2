package es.caib.rolsac2.service.model.filtro;

import java.util.Date;

/**
 * Filtro para las búsquedas de filtros
 *
 * @author Indra
 */
public class AuditoriaFiltro extends AbstractFiltro {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    private Long codigo;

    /**
     * Fecha desde
     */
    private Date fechaDesde;

    /**
     * Fecha hasta
     */
    private Date fechaHasta;

    /**
     * responsable
     */
    private boolean responsable;

    /**
     * Entidad por la cual se va a filtrar
     **/
    private Object auditoriaEntidad;

    /**
     * Procedimiento
     **/
    private Long procedimiento;

    /**
     * Unidad Administrativa
     */
    private Long unidadAdministrativa;

    /**
     * @return the fechaDesde
     */
    public Date getFechaDesde() {
        return fechaDesde;
    }

    /**
     * @param fechaDesde the fechaDesde to set
     */
    public void setFechaDesde(final Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    /**
     * @return the fechaHasta
     */
    public Date getFechaHasta() {
        return fechaHasta;
    }

    /**
     * @param fechaHasta the fechaHasta to set
     */
    public void setFechaHasta(final Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    /**
     * @return the responsable
     */
    public boolean isResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(final boolean responsable) {
        this.responsable = responsable;
    }

    public Long getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Long procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * @return the auditoriaEntidad
     */
    public Object getAuditoriaEntidad() {
        return auditoriaEntidad;
    }

    /**
     * @param auditoriaClase the auditoriaEntidad to set
     */
    public void setAuditoriaEntidad(final Object auditoriaClase) {
        this.auditoriaEntidad = auditoriaClase;
    }


    public Long getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Long unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

    public boolean isRellenoCodigo() {
        return this.getCodigo() != null;
    }

    /**
     * Valida fechaDesde
     *
     * @return
     */
    public boolean isRellenoFechaDesde() {
        return this.getFechaDesde() != null;
    }

    public boolean isRellenoProcedimiento() {
        return this.getProcedimiento() != null;
    }

    /**
     * Valida fechaHasta
     *
     * @return
     */
    public boolean isRellenoFechaHasta() {
        return this.getFechaHasta() != null;
    }

    public boolean isRellenoUA() {
        return this.getUnidadAdministrativa() != null;
    }

    /**
     * Valida si es responsable
     *
     * @return
     */
    public boolean isRellenoResponsable() {
        return this.isResponsable();
    }

    @Override
    protected String getDefaultOrder() {
        this.setAscendente(false);
        return "J.codigo";
    }


}
