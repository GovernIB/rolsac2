package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.EdificioFiltro;
import es.caib.rolsac2.service.model.filtro.EntidadRaizFiltro;
import es.caib.rolsac2.service.model.filtro.PluginFiltro;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;

import java.util.List;

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
     * Método que se utiliza al crear una nueva entidad
     *
     * @param usuarioDTO
     * @param idEntidad
     */
    void createUsuarioEntidad(UsuarioDTO usuarioDTO, Long idEntidad);

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
     * Método para borrar un usuario de una entidad
     *
     * @param id
     */
    void deleteUsuarioEntidad(Long idUsuario, Long idEntidad);

    /**
     * Retorna el usuario relacionado al identificador
     *
     * @param id identificador del usuario
     * @return un opcional con los datos del usuario
     */
    UsuarioDTO findUsuarioById(Long id);

    /**
     * Retorna el usuario relacionado al identificador
     *
     * @param identificador identificador del usuario
     * @return un opcional con los datos del usuario
     */
    UsuarioDTO findUsuarioByIdentificador(String identificador);

    /**
     * Retorna el usuario relacionado al identificador
     *
     * @param identificador
     * @param lang
     * @return
     */
    UsuarioDTO findUsuarioSimpleByIdentificador(String identificador, String lang);

    /**
     * Devuelve una página con el tipo de afectación relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de tipo de afectación y la lista tipo de afectación con el rango indicado
     */
    Pagina<UsuarioGridDTO> findByFiltro(UsuarioFiltro filtro);

    /**
     * Devuelve una página con todos los usuarios con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de usuarios y una lista con el rango indicado
     */
    Pagina<UsuarioGridDTO> findAllUsuariosByFiltro(UsuarioFiltro filtro);

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
    Long createPlugin(PluginDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un plugin a la base de datos.
     *
     * @param dto nuevos datos del plugin
     * @throws RecursoNoEncontradoException si el plugin con el id no existe.
     */
    void updatePlugin(PluginDTO dto) throws RecursoNoEncontradoException;

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
    PluginDTO findPluginById(Long id);

    /**
     * Devuelve una página con el plugin relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total deplugins y la lista plugin con el rango indicado
     */
    Pagina<PluginGridDTO> findByFiltro(PluginFiltro filtro);

    /**
     * Devuelve el listado de plugins que están creados en una entidad
     *
     * @param idEntidad identificador de la entidad
     * @return listado de plugins en una entidad
     */
    List<PluginDTO> listPluginsByEntidad(Long idEntidad);

    /**
     * Comprueba si ya existe un plugin creado con el tipo y el codigoPlugin.
     *
     * @param codigoPlugin
     * @param tipo
     * @return
     */
    boolean existePluginTipo(Long codigoPlugin, String tipo);

    /**
     * Comprueba si ya existe un plugin creado con el tipo en la entidad.
     *
     * @param idEntidad
     * @param tipo
     * @return
     */
    boolean existePluginTipoByEntidad(Long idEntidad, String tipo);

    /**************************************************
     **************** ENTIDAD RAIZ ************************
     **************************************************/

    /**
     * Crea un nuevo plugin a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del tipo de EntidadRaiz
     * @return EL identificador de la nueva EntidadRaiz
     * @throws RecursoNoEncontradoException si la EntidadRaiz no existe
     */
    Long createEntidadRaiz(EntidadRaizDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de una EntidadRaiz a la base de datos.
     *
     * @param dto nuevos datos de la EntidadRaiz
     * @throws RecursoNoEncontradoException si la EntidadRaiz con el id no existe.
     */
    void updateEntidadRaiz(EntidadRaizDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra una EntidadRaiz de la bbdd
     *
     * @param id identificador de la EntidadRaiz a borrar
     * @throws RecursoNoEncontradoException si la EntidadRaiz con el id no existe.
     */
    void deleteEntidadRaiz(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional con la EntidadRaiz indicada por el identificador
     *
     * @param id identificador de la EntidadRaiz a buscar
     * @return un opcional con los datos de la EntidadRaiz indicada o vacío si no existe
     */
    EntidadRaizDTO findEntidadRaizById(Long id);

    /**
     * Devuelve una página con la EntidadRaiz relacionada con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de EntidadRaiz y la lista EntidadRaiz con el rango indicado
     */
    Pagina<EntidadRaizGridDTO> findByFiltro(EntidadRaizFiltro filtro);
}
