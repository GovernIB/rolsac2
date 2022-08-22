package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.TipoMediaEdificioFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaFichaFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaUAFiltro;

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
     * Crea un nuevo tipoMediaUA a la base de datos.
     *
     * @param dto datos del tipoMediaUA
     * @return identificador
     */
    Long create(TipoMediaUADTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipoMediaUA a la base de datos.
     *
     * @param dto nuevos datos del tipoMediaUA
     * @throws RecursoNoEncontradoException si el tipoMediaUA con el id no existe.
     */
    void update(TipoMediaUADTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipoMediaUA de la bbdd
     *
     * @param id identificador del tipoMediaUA a borrar
     * @throws RecursoNoEncontradoException si el tipoMediaUA con el id no existe.
     */
    void deleteTipoMediaUA(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el tipoMediaUA indicat per l'identificador.
     *
     * @param id identificador del tipoMediaUA a cercar
     * @return un opcional amb les dades del tipoMediaUA indicat o buid si no existeix.
     */
    TipoMediaUADTO findTipoMediaUAById(Long id);

    /**
     * Devuelve una página con el tipoMediaUA relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de tipoMediaUA i la llista de tipoMediaUA pel rang indicat.
     */
    Pagina<TipoMediaUAGridDTO> findByFiltro(TipoMediaUAFiltro filtro);

    /**
     * Devuelve si existe un tipo UA con el identificador indicado
     *
     * @param identificador identificador del tipo UA
     * @return true si existe un tipo UA con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoMediaUA(String identificador, Long idEntidad);

    /**
     * Crea un nuevo TipoMediaFicha a la base de datos.
     *
     * @param dto datos del TipoMediaFicha
     * @return identificador
     */
    Long create(TipoMediaFichaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un TipoMediaFicha a la base de datos.
     *
     * @param dto nuevos datos del TipoMediaFicha
     * @throws RecursoNoEncontradoException si el TipoMediaFicha con el id no existe.
     */
    void update(TipoMediaFichaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un TipoMediaFicha de la bbdd
     *
     * @param id identificador del TipoMediaFicha a borrar
     * @throws RecursoNoEncontradoException si el TipoMediaFicha con el id no existe.
     */
    void deleteTipoMediaFicha(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el TipoMediaFicha indicat per l'identificador.
     *
     * @param id identificador del TipoMediaFicha a cercar
     * @return un opcional amb les dades del TipoMediaFicha indicat o buid si no existeix.
     */
    TipoMediaFichaDTO findTipoMediaFichaById(Long id);

    /**
     * Devuelve una página con el TipoMediaFicha relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de TipoMediaFicha i la llista de TipoMediaFicha pel rang indicat.
     */
    Pagina<TipoMediaFichaGridDTO> findByFiltro(TipoMediaFichaFiltro filtro);

    /**
     * Devuelve si existe un tipo UA con el identificador indicado
     *
     * @param identificador identificador del tipo UA
     * @return true si existe un tipo UA con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoMediaFicha(String identificador);

}
