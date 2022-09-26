package es.caib.rolsac2.commons.plugins.indexacion.api.model;

/**
 * Paginacion.
 *
 * @author Indra
 */
public class PaginationSearch {

    /**
     * Desde que elemento se empieza.
     * Por defecto 0.
     */
    private Long desde = 0l;

    /**
     * Numero maximo de elementos a devolver.
     * Por defecto 10.
     */
    private Long maxElementos = 10l;

    /**
     * Obtiene desde que elemento se empieza.
     *
     * @return the desde que elemento se empieza
     */
    public Long getDesde() {
        return desde;
    }


    /**
     * Establece desde que elemento se empieza.
     *
     * @param desde the new desde que elemento se empieza
     */
    public void setDesde(Long desde) {
        this.desde = desde;
    }


    /**
     * Obtiene numero maximo de elementos a devolver.
     *
     * @return the numero maximo de elementos a devolver
     */
    public Long getMaxElementos() {
        return maxElementos;
    }


    /**
     * Establece numero maximo de elementos a devolver.
     *
     * @param maxElementos the new numero maximo de elementos a devolver
     */
    public void setMaxElementos(Long maxElementos) {
        this.maxElementos = maxElementos;
    }

}
