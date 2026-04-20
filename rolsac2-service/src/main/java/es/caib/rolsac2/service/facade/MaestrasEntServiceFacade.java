package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.CategoriaPDUFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaEdificioFiltro;

import java.util.List;

public interface MaestrasEntServiceFacade {

    /**
     * Crea un nuevo tipoMediaEdificio a la base de datos.
     *
     * @param dto datos del tipoMediaEdificio
     * @return identificador
     */
    Long create(TipoMediaEdificioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipoMediaEdificio a la base de datos.
     *
     * @param dto nuevos datos del tipoMediaEdificio
     * @throws RecursoNoEncontradoException si el tipoMediaEdificio con el id no existe.
     */
    void update(TipoMediaEdificioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipoMediaEdificio de la bbdd
     *
     * @param id identificador del tipoMediaEdificio a borrar
     * @throws RecursoNoEncontradoException si el tipoMediaEdificio con el id no existe.
     */
    void deleteTipoMediaEdificio(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el tipoMediaEdificio indicat per l'identificador.
     *
     * @param id identificador del tipoMediaEdificio a cercar
     * @return un opcional amb les dades del tipoMediaEdificio indicat o buid si no existeix.
     */
    TipoMediaEdificioDTO findTipoMediaEdificioById(Long id);

    /**
     * Retorna el listado de tipoMediaEdificio asociado a una entidad
     *
     * @param idEntidad identificador de la entidad.
     * @return un listado con los datos de los tipoMediaEdificio asociados a una entidad.
     */
    List<TipoMediaEdificioDTO> findTipoMediaEdificioByEntidad(Long idEntidad);

    /**
     * Devuelve una página con el tipoMediaEdificio relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de tipoMediaEdificio i la llista de tipoMediaEdificio pel rang indicat.
     */
    Pagina<TipoMediaEdificioGridDTO> findByFiltro(TipoMediaEdificioFiltro filtro);

    /**
     * Devuelve si existe un tipo edi con el identificador indicado
     *
     * @param identificador identificador del tipo edi
     * @param idEntidad     id entidad
     * @return true si existe un tipo edi con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoMediaEdificio(String identificador, Long idEntidad);

    /**
     * Crea un nuevo CategoriaPduDTO a la base de datos.
     *
     * @param dto datos del tipoMediaUA
     * @return identificador
     */
    Long create(CategoriaPDUDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un CategoriaPduDTO a la base de datos.
     *
     * @param dto nuevos datos del CategoriaPduDTO
     * @throws RecursoNoEncontradoException si el CategoriaPduDTO con el id no existe.
     */
    void update(CategoriaPDUDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un CategoriaPduDTO de la bbdd
     *
     * @param id identificador del CategoriaPduDTO a borrar
     * @throws RecursoNoEncontradoException si el CategoriaPduDTO con el id no existe.
     */
    void deleteCategoriaPduDTO(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el CategoriaPduDTO indicat per l'identificador.
     *
     * @param id identificador del CategoriaPduDTO a cercar
     * @return un opcional amb les dades del CategoriaPduDTO indicat o buid si no existeix.
     */
    CategoriaPDUDTO findCategoriaPduDTOById(Long id);

    /**
     * Retorna un listado con los CategoriaPduDTO asociados a una entidad.
     *
     * @param idEntidad identificador de la entidad asociada
     * @return un listado de CategoriaPduDTO
     */
    List<CategoriaPDUDTO> findCategoriaPduDTOByEntidad(Long idEntidad);

    /**
     * Devuelve una página con el CategoriaPduDTO relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de CategoriaPduDTO i la llista de CategoriaPduDTO pel rang indicat.
     */
    Pagina<CategoriaPDUGridDTO> findByFiltro(CategoriaPDUFiltro filtro);

    /**
     * Devuelve si existe un CategoriaPduDTO con el identificador indicado
     *
     * @param identificador identificador del CategoriaPduDTO
     * @return true si existe un CategoriaPduDTO con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorCategoriaPdu(String identificador, Long idEntidad);

    /**
     * Comprueba si una categoria PDU está asociada a un procedimiento
     *
     * @param codigoPDU Codigo Categoria PDU
     * @return true si está asociada a un procedimiento, false en caso contrario
     */
    boolean estaAsociadoCategoriaPDU(Long codigoPDU);
}