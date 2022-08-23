package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.FichaDTO;
import es.caib.rolsac2.service.model.FichaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.FichaFiltro;

/**
 * Servicio para los casos de uso de mantenimiento del personal.
 *
 * @author Indra
 */
public interface FichaServiceFacade {

    /**
     * Crea un nuevo fichas a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del personal
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo personal
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(FichaDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un fichas a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(FichaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra una ficha de la bbdd
     *
     * @param id identificador del personal a borrar
     * @throws RecursoNoEncontradoException si el personal con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un ficha amb el personal indicat per l'identificador.
     *
     * @param id identificador del personal a cercar
     * @return un opcional amb les dades del personal indicat o buid si no existeix.
     */
    FichaDTO findById(Long id);

    /**
     * Devuelve una página con las fichas relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de fichas i la llista de fichas pel rang indicat.
     */
    Pagina<FichaGridDTO> findByFiltro(FichaFiltro filtro);

    /**
     * Devuelve el total de fichas relacionado con los parámetros del filtro.
     *
     * @param filtro
     * @return
     */
    int countByFiltro(FichaFiltro filtro);
}