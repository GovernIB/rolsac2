package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;

import java.util.List;

/**
 * Servicio para los casos de uso de mantenimiento de la entidad y la configuración global.
 *
 * @author jsegovia
 */
public interface AdministracionSupServiceFacade {

    /**
     * Crea un nuevo Entidad a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del Entidad
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo Entidad
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long createEntidad(EntidadDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un Entidad a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void updateEntidad(EntidadDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un Entidad de la bbdd
     *
     * @param id identificador del Entidad a borrar
     * @throws RecursoNoEncontradoException si el Entidad con el id no existe.
     */
    void deleteEntidad(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el Entidad indicat per l'identificador.
     *
     * @param id identificador del Entidad a cercar
     * @return un opcional amb les dades del Entidad indicat o buid si no existeix.
     */
    EntidadDTO findEntidadById(Long id);

    /**
     * Devuelve una página con el Entidad relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de Entidad i la llista de Entidad pel rang indicat.
     */
    Pagina<EntidadGridDTO> findEntidadByFiltro(EntidadFiltro filtro);

    /**
     * Devuelve las Entidades Activas
     *
     * @return la lista de entidades activas
     */
    List<EntidadDTO> findEntidadActivas();


    /**
     * Actualiza los datos de una configuración global en la base de datos.
     *
     * @param dto nuevos datos de una configuración global
     * @throws RecursoNoEncontradoException si la configuración global con el id no existe.
     */
    void updateConfGlobal(ConfiguracionGlobalDTO dto) throws RecursoNoEncontradoException;


    /**
     * Retorna un opcional con la configuración global indicada por el identificador.
     *
     * @param id identificador de la configuración global a buscar
     * @return un opcional con los datos de la configuración global indicada o vacío si no existe
     */
    ConfiguracionGlobalDTO findConfGlobalById(Long id);

    /**
     * Devuelve una página con el Entidad relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de Entidad i la llista de Entidad pel rang indicat.
     */
    Pagina<ConfiguracionGlobalGridDTO> findConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro);

    /**
     * Existe identificador de entidad
     *
     * @param identificador
     * @return
     */
    boolean existeIdentificadorEntidad(String identificador);

    FicheroDTO getLogoEntidad(Long codigo);
}
