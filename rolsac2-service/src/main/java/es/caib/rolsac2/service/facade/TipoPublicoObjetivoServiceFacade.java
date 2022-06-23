package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoFiltro;

/**
 * Servicio para los casos de uso de mantenimiento del tipo de normativa.
 *
 * @author jsegovia
 */
public interface TipoPublicoObjetivoServiceFacade {

    /**
     * Crea un nuevo TipoPublicoObjetivo a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del TipoPublicoObjetivo
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo TipoPublicoObjetivo
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoPublicoObjetivoDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un TipoPublicoObjetivo a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(TipoPublicoObjetivoDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un TipoPublicoObjetivo de la bbdd
     *
     * @param id identificador del TipoPublicoObjetivo a borrar
     * @throws RecursoNoEncontradoException si el TipoPublicoObjetivo con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el TipoPublicoObjetivo indicat per l'identificador.
     *
     * @param id identificador del TipoPublicoObjetivo a cercar
     * @return un opcional amb les dades del TipoPublicoObjetivo indicat o buid si no existeix.
     */
    TipoPublicoObjetivoDTO findById(Long id);

    /**
     * Devuelve una página con el TipoPublicoObjetivo relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de TipoPublicoObjetivo i la llista de TipoPublicoObjetivo pel rang indicat.
     */
    Pagina<TipoPublicoObjetivoGridDTO> findByFiltro(TipoPublicoObjetivoFiltro filtro);


    /**
     * Devuelve si existe el identificador en la entidad
     *
     * @param identificador identificador a comprobar
     * @return si existe o no
     */
    Boolean checkIdentificador(String identificador);
}
