package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalDTO;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.filtro.PersonalFiltro;

/**
 * Servicio para los casos de uso de mantenimiento del personal.
 *
 * @author Indra
 */
public interface PersonalServiceFacade {

    /**
     * Crea un nuevo personal a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del personal
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo personal
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(PersonalDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un personal a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(PersonalDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un personal de la bbdd
     *
     * @param id identificador del personal a borrar
     * @throws RecursoNoEncontradoException si el personal con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el personal indicat per l'identificador.
     *
     * @param id identificador del personal a cercar
     * @return un opcional amb les dades del personal indicat o buid si no existeix.
     */
    PersonalDTO findById(Long id);

    /**
     * Devuelve una página con el personal relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de personal i la llista de personal pel rang indicat.
     */
    Pagina<PersonalGridDTO> findByFiltro(PersonalFiltro filtro);

}
