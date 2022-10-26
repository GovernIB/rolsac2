package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;

import java.util.List;

public interface NormativaServiceFacade {

    /**
     * Crea un nueva ua a la base de datos.
     *
     * @param dto datos de la Normativa
     * @return EL identificador de la nueva Normativa
     */
    Long create(NormativaDTO dto);

    /**
     * Actualiza los datos de una Normativa a la base de datos.
     *
     * @param dto nuevos datos de la Normativa
     * @throws RecursoNoEncontradoException si la Normativa con el id no existe.
     */
    void update(NormativaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra una Normativa de la bbdd
     *
     * @param id identificador de la Normativa a borrar
     * @throws RecursoNoEncontradoException si la Normativa con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Devuelve una Normativa indicat por el identificador.
     *
     * @param id identificador de la Normativa a buscar
     * @return un opcional con los datos de la Normativa indicador o vacio si no existe.
     */
    NormativaDTO findById(Long id);

    /**
     * Devuelve una página con la Normativa relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pagina con el numero total de Normativas y la lista de Normativas por el rango indicado.
     */
    Pagina<NormativaGridDTO> findByFiltro(NormativaFiltro filtro);

    /**
     * Devuelve el total de Normativas relacionado con los parámetros del filtro.
     *
     * @param filtro
     * @return
     */
    int countByFiltro(NormativaFiltro filtro);

    /**
     * Devuelve una lista con todos los objetos boletinOficial de la bbdd.
     *
     * @return Lista de todos los objectos en Boletin Oficial
     */
    List<BoletinOficialDTO> findBoletinOficial();

    /**
     * Devuelve una lista con todos los objetos de afectacion de la bbdd.
     *
     * @return Lista de todos los objectos en afectacion
     */
    List<AfectacionDTO> findAfectacion();

    /**
     * Crea un documento de normativa asociado a ésta.
     *
     * @param dto nuevos datos del documento Normativa
     * @return id del documento de normativa
     */
    Long createDocumentoNormativa(DocumentoNormativaDTO dto);

    /**
     * Actualiza un documento de normativa asociado a ésta.
     *
     * @param dto datos actualizados del documento normativa
     */
    void updateDocumentoNormativa(DocumentoNormativaDTO dto);

    /**
     * Borra un documento normativa de la bbdd
     *
     * @param id identificador del documento Normativa a borrar
     * @throws RecursoNoEncontradoException si la Normativa con el id no existe.
     */
    void deleteDocumentoNormativa(Long id) throws RecursoNoEncontradoException;

    /**
     * Devuelve un documento Normativa de la bbdd.
     *
     * @return Lista de todos los objectos en Boletin Oficial
     */
     DocumentoNormativaDTO findDocumentoNormativa(Long id);


    /**
     * Devuelve un listado de documentos Normativa relacionados a una normativa.
     *
     * @return Lista de todos los objectos en Boletin Oficial
     */
    List<DocumentoNormativaDTO> findDocumentosNormativa(Long idNormativa);


}
