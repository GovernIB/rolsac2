package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.TipoNormativaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoNormativaFiltro;

/**
 * Servicio para los casos de uso de mantenimiento del tipo de normativa.
 *
 * @author jsegovia
 */
public interface TipoNormativaServiceFacade {

    /**
     * Crea un nuevo TipoNormativa a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del TipoNormativa
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo TipoNormativa
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoNormativaDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un TipoNormativa a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(TipoNormativaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un TipoNormativa de la bbdd
     *
     * @param id identificador del TipoNormativa a borrar
     * @throws RecursoNoEncontradoException si el TipoNormativa con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el TipoNormativa indicat per l'identificador.
     *
     * @param id identificador del TipoNormativa a cercar
     * @return un opcional amb les dades del TipoNormativa indicat o buid si no existeix.
     */
    TipoNormativaDTO findById(Long id);

    /**
     * Devuelve una página con el TipoNormativa relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de TipoNormativa i la llista de TipoNormativa pel rang indicat.
     */
    Pagina<TipoNormativaGridDTO> findByFiltro(TipoNormativaFiltro filtro);

    /**
     * Devuelve si existe el identificador en la entidad
     *
     * @param identificador identificador a comprobar
     * @return si existe o no
     */
    Boolean checkIdentificador(String identificador);
}
