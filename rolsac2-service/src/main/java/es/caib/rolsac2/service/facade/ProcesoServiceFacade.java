package es.caib.rolsac2.service.facade;


import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcesoDTO;
import es.caib.rolsac2.service.model.ProcesoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.solr.ProcedimientoBaseSolr;

import java.util.List;

/**
 * Instancia service.
 *
 * @author Indra
 */
public interface ProcesoServiceFacade {
    /**
     * Obtiene un filtro por codigo (Long).
     *
     * @param codigo Codigo pk del Peticion
     * @return Peticion que tenga el código
     */
    ProcesoDTO obtenerProcesoPorCodigo(Long codigo);

    /**
     * Borra un Peticion.
     *
     * @param codigo Codigo del Proceso
     */
    void borrar(Long codigo);

    /**
     * Guarda un Peticion.
     */
    void guardar(ProcesoDTO proceso);

    /**
     * Lista Proceso según el filtro.
     *
     * @param filtro Filtro de Proceso
     * @return Lista de ProcesoGrid
     */
    List<ProcesoGridDTO> listar(ProcesoFiltro filtro);

    /**
     * Total de la Lista Proceso según el filtro
     *
     * @param filtro Filtro de Proceso
     * @return Total de elementos
     */
    Integer listarTotal(ProcesoFiltro filtro);

    List<ProcesoGridDTO> listar(final String idioma, final String tipo);

    Pagina<ProcesoGridDTO> findByFiltro(ProcesoFiltro filtro);

    Pagina<ProcedimientoBaseSolr> findSolrByFiltro(ProcesoSolrFiltro filtro);

}
