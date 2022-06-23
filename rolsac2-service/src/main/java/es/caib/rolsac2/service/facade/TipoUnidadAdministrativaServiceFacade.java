package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoUnidadAdministrativaFiltro;

/**
 * Servicio para los casos de uso de mantenimiento del tipo de normativa.
 *
 * @author jsegovia
 */
public interface TipoUnidadAdministrativaServiceFacade {

    /**
     * Crea un nuevo TipoUnidadAdministrativa a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del TipoUnidadAdministrativa
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo TipoUnidadAdministrativa
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoUnidadAdministrativaDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un TipoUnidadAdministrativa a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(TipoUnidadAdministrativaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un TipoUnidadAdministrativa de la bbdd
     *
     * @param id identificador del TipoUnidadAdministrativa a borrar
     * @throws RecursoNoEncontradoException si el TipoUnidadAdministrativa con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el TipoUnidadAdministrativa indicat per l'identificador.
     *
     * @param id identificador del TipoUnidadAdministrativa a cercar
     * @return un opcional amb les dades del TipoUnidadAdministrativa indicat o buid si no existeix.
     */
    TipoUnidadAdministrativaDTO findById(Long id);

    /**
     * Devuelve una página con el TipoUnidadAdministrativa relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de TipoUnidadAdministrativa i la llista de TipoUnidadAdministrativa pel rang indicat.
     */
    Pagina<TipoUnidadAdministrativaGridDTO> findByFiltro(TipoUnidadAdministrativaFiltro filtro);


    /**
     * Devuelve si existe el identificador en la entidad
     *
     * @param identificador identificador a comprobar
     * @return si existe o no
     */
    Boolean checkIdentificador(String identificador);

}
