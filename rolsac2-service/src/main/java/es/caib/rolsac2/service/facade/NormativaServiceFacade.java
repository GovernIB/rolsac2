package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.IndexFile;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.DocumentoNormativaFiltro;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface NormativaServiceFacade {

    /**
     * Crea un nueva ua a la base de datos.
     *
     * @param dto  datos de la Normativa
     * @param path path del fichero
     * @return EL identificador de la nueva Normativa
     */
    Long create(NormativaDTO dto, String path);

    /**
     * Actualiza los datos de una Normativa a la base de datos.
     *
     * @param dto  nuevos datos de la Normativa
     * @param path path del fichero
     * @throws RecursoNoEncontradoException si la Normativa con el id no existe.
     */
    void update(NormativaDTO dto, String path) throws RecursoNoEncontradoException;

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
     * Devuelve el listado de normativas asociadas a unaentidad.
     *
     * @param idEntidad identificador de la Entidad a buscar
     * @return un listado con los datos de las Normativas.
     */
    List<NormativaDTO> findByEntidad(Long idEntidad);

    /**
     * Devuelve una página con la Normativa relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pagina con el numero total de Normativas y la lista de Normativas por el rango indicado.
     */
    Pagina<NormativaGridDTO> findByFiltro(NormativaFiltro filtro);

    /**
     * Devuelve todas las Normativa relacionado con los parámetros del filtro
     *
     * @param filtro        filtro de la búsqueda
     * @param exportarDatos
     * @return una pagina con el numero total de Normativas y la lista de Normativas por el rango indicado.
     */
    List<NormativaDTO> findExportByFiltro(NormativaFiltro filtro, ExportarDatos exportarDatos);

    /**
     * Devuelve el total de Normativas relacionado con los parámetros del filtro.
     *
     * @param filtro
     * @return
     */
    int countByFiltro(NormativaFiltro filtro);

    /**
     * Devuelve el total de Normativas relacionado con los parámetros del codigo de la entidad.
     *
     * @param entidadId
     * @return
     */
    Long countByEntidad(Long entidadId);

    /**
     * Devuelve el total de Normativas en la bbdd.
     *
     * @param
     * @return
     */
    Long countAll();

    /**
     * Devuelve el total de Normativas relacionado con los parámetros del codigo de la Unidad Administrativa.
     *
     * @param uaId
     * @return
     */
    Long countByUa(Long uaId);


    /**
     * Muestra las afectaciones relacioandas a una normativa.
     *
     * @param idNormativa
     * @return
     */
    List<AfectacionDTO> findAfectacionesByNormativa(Long idNormativa);

    /**
     * Crea un documento de normativa asociado a ésta.
     *
     * @param dto  nuevos datos del documento Normativa
     * @param path path del fichero
     * @return id del documento de normativa
     */
    Long createDocumentoNormativa(DocumentoNormativaDTO dto, String path);

    /**
     * Actualiza un documento de normativa asociado a ésta.
     *
     * @param dto  datos actualizados del documento normativa
     * @param path path del fichero
     */
    void updateDocumentoNormativa(DocumentoNormativaDTO dto, String path);

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

    /**
     * Comprueba si existen procedimientos con dicha normativa
     *
     * @param codigo
     * @return
     */
    boolean existeProcedimientoConNormativa(Long codigo);

    /**
     * Comprueba si existe el tipo normativa asociado a alguna normativa
     *
     * @param codigoTipoNor
     * @return
     */
    boolean existeTipoNormativa(Long codigoTipoNor);

    /**
     * Comprueba si existe el boletín asociado a alguna normativa
     *
     * @param codigoBol
     * @return
     */
    boolean existeBoletin(Long codigoBol);


    /**
     * Devuelve el listado de procedimientos que tienen asociada la normativa
     *
     * @param idNormativa
     * @return
     */
    List<ProcedimientoNormativaDTO> listarProcedimientosByNormativa(Long idNormativa);

    /**
     * Devuelve el listado de servicios que tienen asociada la normativa
     *
     * @param idNormativa
     * @return
     */
    List<ProcedimientoNormativaDTO> listarServiciosByNormativa(Long idNormativa);

    Long createAfectacion(AfectacionDTO afectacionDTO);

    void updateAfectacion(AfectacionDTO afectacionDTO);

    void deleteAfectacion(Long idAfectacion);

    ProcedimientoSolrDTO findDataIndexacionNormById(Long idNormativa);

    IndexFile findDataIndexacionDocNormById(NormativaDTO normativaDTO, DocumentoNormativaDTO doc, DocumentoTraduccion docTraduccion, List<PathUA> pathUAs, String path);

    /**
     * Devuelve todas las normativas para indexar en solr/elastic
     *
     * @param idEntidad
     * @return
     */
    Pagina<IndexacionDTO> getNormativasParaIndexacion(Long idEntidad);

    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    void actualizarSolr(IndexacionDTO proc, ResultadoAccion resultadoAccion);

    Pagina<NormativaDTO> findByFiltroRest(NormativaFiltro filtro);

    Pagina<DocumentoNormativaDTO> findDocumentoNormativaByFiltroRest(DocumentoNormativaFiltro filtro);

    String obtenerIdiomaEntidad(Long codigo);
}
