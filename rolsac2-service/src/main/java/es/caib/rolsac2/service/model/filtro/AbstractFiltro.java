package es.caib.rolsac2.service.model.filtro;

import java.io.Serializable;

/**
 * Clase abstracta de la que deben heredar los filtros
 *
 * @author Indra
 */
public abstract class AbstractFiltro implements Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Idioma.
     **/
    private String idioma;

    /**
     * Id UA.
     */
    private Long idUA;

    /**
     * Tamaño pagina.
     **/
    private Integer paginaTamanyo = 10; //30

    /**
     * Pagina activa.
     **/
    private Integer paginaFirst = 0;

    /**
     * Order by .
     **/
    private String orderBy;

    private String order = "ASCENDING";

    /**
     * Ascendente.
     **/
    private boolean ascendente = true;

    /**
     * Indicador si se permite el uso de operadores en campos cadena (String)
     */
    private boolean operadoresString = false;

    /**
     * Indica si la paginacion esta activa.
     **/
    private boolean paginacionActiva = true;

    /**
     * Número total de registros encontrados con los criterios de búsqueda
     */
    private Integer total = 0;

    /**
     * Workflow estado.
     **/
    //private TypeWorkflowEstado workflowEstado = TypeWorkflowEstado.PUBLICADO;

    /**
     * Constructor vacío
     */
    public AbstractFiltro() {
        setOrderBy(getDefaultOrder());
    }


    public String getOrderSql() {
        return order == null || order.equals("ASCENDING") ? "asc" : "desc";
    }


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * Obtiene la ordenación por defecto
     */
    // TODO Hay que cambiarlo a AbstractOrden
    protected abstract String getDefaultOrder();


    /**
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(final String idioma) {
        this.idioma = idioma;
    }

    public Long getIdUA() {
        return idUA;
    }

    public void setIdUA(Long idUA) {
        this.idUA = idUA;
    }

    /**
     * @return the paginacionActiva
     */
    public boolean isRellenoIdUA() {
        return idUA != null;
    }

    /**
     * @return the paginaTamanyo
     */
    public Integer getPaginaTamanyo() {
        return paginaTamanyo;
    }

    /**
     * @param paginaTamanyo the paginaTamanyo to set
     */
    public void setPaginaTamanyo(final Integer paginaTamanyo) {
        this.paginaTamanyo = paginaTamanyo;
    }

    /**
     * @return the paginaActiva
     */
    public Integer getPaginaFirst() {
        return paginaFirst;
    }

    /**
     * @param paginaFirst the paginaActiva to set
     */
    public void setPaginaFirst(final Integer paginaFirst) {
        this.paginaFirst = paginaFirst;
    }

    /**
     * @return the orderBy
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy the orderBy to set
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @return the ascendente
     */
    public boolean isAscendente() {
        return ascendente;
    }

    /**
     * @param ascendente the ascendente to set
     */
    public void setAscendente(final boolean ascendente) {
        this.ascendente = ascendente;
    }

    /**
     * Está contemplado ascendente
     */
    public boolean isRellenoAscendente() {
        return this.isAscendente();
    }

    /**
     * @return the operadoresString
     */
    public boolean isOperadoresString() {
        return operadoresString;
    }

    /**
     * Establece operadoresString.
     *
     * @param operadoresString operadoresString a establecer
     */
    public void setOperadoresString(final boolean operadoresString) {
        this.operadoresString = operadoresString;
    }

    /**
     * Está relleno el idioma.
     */
    public boolean isRellenoIdioma() {
        return this.getIdioma() != null && !this.getIdioma().isEmpty();
    }

    /**
     * @return the paginacionActiva
     */
    public boolean isPaginacionActiva() {
        return paginacionActiva;
    }

    /**
     * @param paginacionActiva the paginacionActiva to set
     */
    public void setPaginacionActiva(final boolean paginacionActiva) {
        this.paginacionActiva = paginacionActiva;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * Establece total.
     *
     * @param total total a establecer
     */
    public void setTotal(final Integer total) {
        this.total = total;
    }

}
