package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.SeccionFiltro;

import java.util.List;

public interface SeccionServiceFacade {

    /**
     * Obtener los hijos de una UA
     *
     * @param idSeccion identificador de la seccion
     * @return EL identificador de las nuevas secciones
     * @throws RecursoNoEncontradoException si la seccion no existe
     */
    List<SeccionDTO> getHijos(Long idSeccion, String idioma);

    List<SeccionDTO> getRoot(String idioma, Long entidadId);

    /**
     * Crea un nueva seccion a la base de datos.
     *
     * @param dto datos de la seccion
     * @return EL identificador de la nueva seccion
     */
    Long create(SeccionDTO dto);

    /**
     * Actualiza los datos de una seccion a la base de datos.
     *
     * @param dto nuevos datos de la seccion
     * @throws RecursoNoEncontradoException si la seccion con el id no existe.
     */
    void update(SeccionDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra una seccion de la bbdd
     *
     * @param id identificador de la seccion a borrar
     * @throws RecursoNoEncontradoException si la seccion con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Devuelve una seccion indicat por el identificador.
     *
     * @param id identificador de la seccion a buscar
     * @return un opcional con los datos de la seccion indicador o vacio si no existe.
     */
    SeccionDTO findById(Long id);

    /**
     * Devuelve una página con la seccion relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pagina con el numero total de seccion y la lista de secciones por el rango indicado.
     */
    Pagina<SeccionGridDTO> findByFiltro(SeccionFiltro filtro);

    /**
     * Devuelve el total de secciones relacionado con los parámetros del filtro.
     *
     * @param filtro
     * @return
     */
    int countByFiltro(SeccionFiltro filtro);

    Long getCountHijos(Long parentId);

    Boolean checkIdentificador(String identificador);
}
