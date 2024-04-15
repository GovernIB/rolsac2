package es.caib.rolsac2.service.facade;


import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;

import java.util.List;

/**
 * Instancia proceso service.
 *
 * @author Indra
 */
public interface ProcesoServiceFacade {
    /**
     * Obtiene un filtro por codigo (Long).
     *
     * @param codigo Codigo proceso
     * @return Proceso que tenga el código
     */
    ProcesoDTO obtenerProcesoPorCodigo(Long codigo);

    /**
     * Borra un Proceso.
     *
     * @param codigo Codigo del Proceso
     */
    void borrar(Long codigo);

    /**
     * Guarda un Proceso.
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

    /**
     * Lista Proceso según el idioma y el tipo.
     *
     * @param idioma Idioma
     * @param tipo   Tipo
     * @return Lista de ProcesoGrid
     */
    List<ProcesoGridDTO> listar(final String idioma, final String tipo);

    /**
     * Lista Proceso según codigo de la entidad
     *
     * @param idEntidad Id de la entidad
     * @return Lista de ProcesoGrid
     */
    List<ProcesoDTO> findProcesoByEntidad(Long idEntidad);

    /**
     * Lista Proceso según el filtro.
     *
     * @param filtro Filtro de Proceso
     * @return Lista de ProcesoGrid
     */
    Pagina<ProcesoGridDTO> findByFiltro(ProcesoFiltro filtro);

    /**
     * Lista Proceso según el filtro.
     *
     * @param filtro Filtro de Proceso
     * @return Lista de ProcesoGrid
     */
    Pagina<IndexacionDTO> findSolrByFiltro(ProcesoSolrFiltro filtro);

    /**
     * Lista Proceso según el filtro.
     *
     * @param filtro Filtro de Proceso
     * @return Lista de ProcesoGrid
     */
    Pagina<IndexacionSIADTO> findSIAByFiltro(ProcesoSIAFiltro filtro);
}
