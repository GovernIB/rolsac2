package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoSilencioAdministrativoFiltro;

/**
 * Servicio para los casos de uso de mantenimiento del tipo de normativa.
 *
 * @author jsegovia
 */
public interface TipoSilencioAdministrativoServiceFacade {

    /**
     * Crea un nuevo  TipoSilencioAdministrativo a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del  TipoSilencioAdministrativo
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo  TipoSilencioAdministrativo
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoSilencioAdministrativoDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un  TipoSilencioAdministrativo a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(TipoSilencioAdministrativoDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un  TipoSilencioAdministrativo de la bbdd
     *
     * @param id identificador del  TipoSilencioAdministrativo a borrar
     * @throws RecursoNoEncontradoException si el  TipoSilencioAdministrativo con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el  TipoSilencioAdministrativo indicat per l'identificador.
     *
     * @param id identificador del  TipoSilencioAdministrativo a cercar
     * @return un opcional amb les dades del  TipoSilencioAdministrativo indicat o buid si no existeix.
     */
    TipoSilencioAdministrativoDTO findById(Long id);

    /**
     * Devuelve una página con el  TipoSilencioAdministrativo relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de  TipoSilencioAdministrativo i la llista de  TipoSilencioAdministrativo pel rang indicat.
     */
    Pagina<TipoSilencioAdministrativoGridDTO> findByFiltro(TipoSilencioAdministrativoFiltro filtro);

    /**
     * Devuelve si existe el identificador en la entidad
     *
     * @param identificador identificador a comprobar
     * @return si existe o no
     */
    Boolean checkIdentificador(String identificador);

}
