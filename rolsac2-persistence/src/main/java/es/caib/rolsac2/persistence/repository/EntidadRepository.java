package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre Tipo Silencio Administrativo
 *
 * @author jsegovia
 */
public interface EntidadRepository extends CrudRepository<JEntidad, Long> {

    /**
     * Retorna una entidades que cumplen con la id.
     *
     * @param id
     * @return
     */
    Optional<JEntidad> findById(String id);

    /**
     * Retorna una lista con las entidades que cumplen el filtro
     *
     * @param filtro
     * @return
     */
    List<EntidadGridDTO> findPagedByFiltro(EntidadFiltro filtro);

    /**
     * Retorna una lista con las entidades que tienen el identificador indicado
     *
     * @return
     */
    List<JEntidad> findActivas();

    /**
     * Retorna una lista con los identificadores de las entidades que tienen
     *
     * @param idEntidades
     * @return
     */
    List<String> findRolesDefinidos(List<Long> idEntidades);

    /**
     * Retorna el número de entidades que cumplen el filtro
     *
     * @param filtro
     * @return
     */
    long countByFiltro(EntidadFiltro filtro);

    /**
     * Retorna si la entidad indicada existe por el identificador
     *
     * @param identificador
     * @return
     */
    boolean existeIdentificadorEntidad(String identificador);

    /**
     * Retorna una lista con todas las entidades
     *
     * @return
     */
    List<JEntidad> findAll();

    /**
     * Retorna una lista con todas las entidades
     *
     * @param filtro
     * @return
     */
    List<EntidadDTO> findPagedByFiltroRest(EntidadFiltro filtro);

    /**
     * Retorna el idioma por defecto de la entidad
     *
     * @param idEntidad
     * @return
     */
    String getIdiomaPorDefecto(Long idEntidad);
}
