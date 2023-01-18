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
     * indica si implica cambio de puesto
     */
    private boolean cambioPuesto;

    /**
     * Entidad por la cual se va a filtrar
     **/
    private Object auditoriaEntidad;

    /**
     * indica si se debe mostrar los componentes gráficos de cambio de puesto
     **/
    private boolean mostrarCambioPuesto;

    /**
     * indica si se debe ocultar los componentes gráficos de Responsable
     **/
    private boolean mostrarResponsable;

    /**
     * indica si se van a mostrar las etapas en el caso de auditoría de empleo público (oposición)
     **/
    private boolean mostrarEtapas;

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

    /**
     * @return the cambioPuesto
     */
    public boolean isCambioPuesto() {
        return cambioPuesto;
    }

    /**
     * @param cambioPuesto the cambioPuesto to set
     */
    public void setCambioPuesto(final boolean cambioPuesto) {
        this.cambioPuesto = cambioPuesto;
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

    /**
     * Valida fechaDesde
     *
     * @return
     */
    public boolean isRellenoFechaDesde() {
        return this.getFechaDesde() != null;
    }

    /**
     * Valida fechaHasta
     *
     * @return
     */
    public boolean isRellenoFechaHasta() {
        return this.getFechaHasta() != null;
    }

    /**
     * Valida si es responsable
     *
     * @return
     */
    public boolean isRellenoResponsable() {
        return this.isResponsable();
    }

    /**
     * Valida si hay cambio de puesto
     *
     * @return
     */
    public boolean isRellenoCambioPuesto() {
        return this.isCambioPuesto();
    }

    /**
     * @return the mostrarCambioPuesto
     */
    public boolean isMostrarCambioPuesto() {
        return mostrarCambioPuesto;
    }

    /**
     * @param mostrarCambioPuesto the mostrarCambioPuesto to set
     */
    public void setMostrarCambioPuesto(final boolean mostrarCambioPuesto) {
        this.mostrarCambioPuesto = mostrarCambioPuesto;
    }


    public boolean isMostrarResponsable() {
        return mostrarResponsable;
    }

    public void setMostrarResponsable(final boolean mostrarResponsable) {
        this.mostrarResponsable = mostrarResponsable;
    }

    @Override
    protected String getDefaultOrder() {
        this.setAscendente(false);
        return "P.fechaAuditoria";
    }

    /**
     * @return the mostrarEtapas
     */
    public boolean isMostrarEtapas() {
        return mostrarEtapas;
    }

    /**
     * @param mostrarEtapas the mostrarEtapas to set
     */
    public void setMostrarEtapas(final boolean mostrarEtapas) {
        this.mostrarEtapas = mostrarEtapas;
    }

}
