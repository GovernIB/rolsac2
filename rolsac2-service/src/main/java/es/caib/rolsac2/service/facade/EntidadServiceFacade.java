package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UsuarioDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;

import java.util.List;

/**
 * Servicio para los casos de uso de mantenimiento de una entidad
 *
 * @author Indra
 */
public interface EntidadServiceFacade {

    /**
     * Crea una nueva entidad a la base de datos relacionada con la unidad indicada.
     *
     * @param dto        datos de la entidad
     * @param usuarioDTO Usuario
     * @return EL identificador de la nueva entidad
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(EntidadDTO dto, UsuarioDTO usuarioDTO) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de una entidad a la base de datos.
     *
     * @param dto nuevos datos de la entidad
     * @throws RecursoNoEncontradoException si la entidad con el id no existe.
     */
    void update(EntidadDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra una entidad de la bbdd
     *
     * @param id identificador de la entidad a borrar
     * @throws RecursoNoEncontradoException si la entidad con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional con la entidad indicado por el identificador
     *
     * @param id identificador de la entidad a buscar
     * @return un opcional con los datos de la entidad o vacío si no existe
     */
    EntidadDTO findById(Long id);

    /**
     * Retorna una lista con todas las entidades
     *
     * @return una lista con los datos de todas las entidades o una lista vacía si
     * no hay ninguna
     */
    List<EntidadDTO> findAll();

    /**
     * Devuelve una página con la entidad relacionada con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de la entidad y la lista de las
     * entidades con el rango indicado
     */
    Pagina<EntidadGridDTO> findByFiltro(EntidadFiltro filtro);

    /**
     * Devuelve una página con la entidad relacionada con los parámetros del filtro
     *
     * @param filtro
     * @return
     */
    Pagina<EntidadDTO> findByFiltroRest(EntidadFiltro filtro);

    /**
     * Devuelve el idioma por defecto de la entidad
     *
     * @param idEntidad
     * @return
     */
    String getIdiomaPorDefecto(Long idEntidad);
}
