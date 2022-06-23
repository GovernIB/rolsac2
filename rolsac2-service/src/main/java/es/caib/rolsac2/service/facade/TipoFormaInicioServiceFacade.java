package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoFormaInicioFiltro;

/**
 * Servicio para los casos de uso de mantenimiento de tipo de forma de inicio
 *
 * @author Indra
 */
public interface TipoFormaInicioServiceFacade {

    /**
     * Crea un nuevo tipo de forma de inicio a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del tipo de forma de inicio
     * @return EL identificador del nuevo tipo de forma de inicio
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipo de forma de inicio a la base de datos.
     *
     * @param dto nuevos datos del tipo de forma de inicio
     * @throws RecursoNoEncontradoException si el tipo de forma de inicio con el id no existe.
     */
    void update(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipo de forma de inicio de la bbdd
     *
     * @param id identificador del tipo de forma de inicio a borrar
     * @throws RecursoNoEncontradoException si el tipo de forma de inicio con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional con el tipo de forma de inicio indicado por el identificador
     *
     * @param id identificador del tipo de forma de inicio a buscar
     * @return un opcional con los datos del tipo de forma de inicio indicado o vacío si no existe
     */
    TipoFormaInicioDTO findById(Long id);

    /**
     * Devuelve una página con el tipo de forma de inicio relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de tipo de forma de inicio y la lista tipo de forma de inicio con el rango indicado
     */
    Pagina<TipoFormaInicioGridDTO> findByFiltro(TipoFormaInicioFiltro filtro);

    /**
     * Devuelve si existe un tipo de forma de inicio con el identificador indicado
     *
     * @param identificador identificador del tipo de forma de inicio
     * @return true si existe un tipo de forma de inicio con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificador(String identificador);

}
