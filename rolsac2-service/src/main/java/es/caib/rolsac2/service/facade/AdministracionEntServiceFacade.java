package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.EdificioFiltro;
import es.caib.rolsac2.service.model.filtro.PluginFiltro;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;

public interface AdministracionEntServiceFacade {

    /**************************************************
     **************** EDIFICIO ************************
     **************************************************/
    /**
     * Crea un nuevo edificio a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del edificio
     * @return EL identificador del nuevo edificio
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(EdificioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un edificio a la base de datos.
     *
     * @param dto nuevos datos del edificio
     * @throws RecursoNoEncontradoException si el edificio con el id no existe.
     */
    void update(EdificioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un edificio de la bbdd
     *
     * @param id identificador del edificio a borrar
     * @throws RecursoNoEncontradoException si el edificio con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional con el edificio indicado por el identificador
     *
     * @param id identificador del edificio a buscar
     * @return un opcional con los datos del edificio indicado o vacío si no existe
     */
    EdificioDTO findById(Long id);

    /**
     * Devuelve una página con el edificio relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de edificio y la lista edificio con el rango indicado
     */
    Pagina<EdificioGridDTO> findByFiltro(EdificioFiltro filtro);

    /**************************************************
     **************** USUARIO ************************
     **************************************************/

    /**
     * Crea un nuevo tipo de afectación a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del tipo de afectación
     * @return EL identificador del nuevo tipo de afectación
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(UsuarioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipo de afectación a la base de datos.
     *
     * @param dto nuevos datos del tipo de afectación
     * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
     */
    void update(UsuarioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipo de afectación de la bbdd
     *
     * @param id identificador del tipo de afectación a borrar
     * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
     */
    void deleteUsuario(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional con el tipo de afectación indicado por el identificador
     *
     * @param id identificador del tipo de afectación a buscar
     * @return un opcional con los datos del tipo de afectación indicado o vacío si no existe
     */
    UsuarioDTO findUsuarioById(Long id);

    /**
     * Devuelve una página con el tipo de afectación relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de tipo de afectación y la lista tipo de afectación con el rango indicado
     */
    Pagina<UsuarioGridDTO> findByFiltro(UsuarioFiltro filtro);

    /**
     * Devuelve si existe el identificador en la entidad
     *
     * @param identificador identificador a comprobar
     * @return si existe o no
     */
    Boolean checkIdentificadorUsuario(String identificador);

    /**************************************************
     **************** PLUGINS ************************
     **************************************************/

    /**
     * Crea un nuevo plugin a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del tipo de afectación
     * @return EL identificador del nuevo plugin
     * @throws RecursoNoEncontradoException si el plugin no existe
     */
    Long createPlugin(PluginDto dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un plugin a la base de datos.
     *
     * @param dto nuevos datos del plugin
     * @throws RecursoNoEncontradoException si el plugin con el id no existe.
     */
    void updatePlugin(PluginDto dto) throws RecursoNoEncontradoException;

    /**
     * Borra un plugin de la bbdd
     *
     * @param id identificador del plugin a borrar
     * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
     */
    void deletePlugin(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional con el plugin indicado por el identificador
     *
     * @param id identificador del plugin a buscar
     * @return un opcional con los datos del plugin indicado o vacío si no existe
     */
    PluginDto findPluginById(Long id);

    /**
     * Devuelve una página con el plugin relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total deplugins y la lista plugin con el rango indicado
     */
    Pagina<PluginGridDTO> findByFiltro(PluginFiltro filtro);

}
