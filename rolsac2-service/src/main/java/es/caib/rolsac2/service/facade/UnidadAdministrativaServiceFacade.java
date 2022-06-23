package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;

import java.util.List;

/**
 * Servicio para los casos de uso de mantenimiento de UA.
 *
 * @author Indra
 */
public interface UnidadAdministrativaServiceFacade {

    /**
     * Obtener los hijos de una UA
     *
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo personal
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    List<UnidadAdministrativaDTO> getHijos(Long idUnitat);


    /**
     * Crea un nueva ua a la base de datos.
     *
     * @param dto datos de la UA
     * @return EL identificador de la nueva UA
     */
    Long create(UnidadAdministrativaDTO dto);

    /**
     * Actualiza los datos de una UA a la base de datos.
     *
     * @param dto nuevos datos del ua
     * @throws RecursoNoEncontradoException si el ua con el id no existe.
     */
    void update(UnidadAdministrativaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra una ua de la bbdd
     *
     * @param id identificador dela ua a borrar
     * @throws RecursoNoEncontradoException si la ua con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna una ua indicat per l'identificador.
     *
     * @param id identificador de la ua a cercar
     * @return un opcional amb les dades del ua indicat o buid si no existeix.
     */
    UnidadAdministrativaDTO findById(Long id);

    /**
     * Devuelve una página con el ua relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de personal i la llista de uas pel rang indicat.
     */
    Pagina<PersonalGridDTO> findByFiltro(UnidadAdministrativaFiltro filtro);

    /**
     * Devuelve el total de uas relacionado con los parámetros del filtro.
     *
     * @param filtro
     * @return
     */
    int countByFiltro(UnidadAdministrativaFiltro filtro);
}
