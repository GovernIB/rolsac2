package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProceso;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.migracion.FicheroInfo;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;

import java.math.BigDecimal;
import java.util.List;

public interface MigracionRepository extends CrudRepository<JProceso, Long> {

    /**
     * Ejecuta el método.
     *
     * @param metodo
     * @param param1
     * @param param2
     * @return
     */
    public String ejecutarMetodo(String metodo, String param1, String param2);

    void ejecutarMetodo(String metodo);

    /**
     * Obtiene las normativas
     *
     * @param idEntidad
     **/
    List<BigDecimal> getNormativas(Long idEntidad);

    /**
     * Obtiene los procedimientos
     *
     * @param idEntidad
     **/
    List<BigDecimal> getProcedimientos(Long idEntidad, Long codigoUARaiz);

    /**
     * Obtiene los servicios
     *
     * @param idEntidad
     * @return
     */
    List<BigDecimal> getServicios(Long idEntidad, Long codigoUARaiz);

    /**
     * Importa las normativas
     *
     * @param idNormativa
     **/
    String importarNormativa(Long idNormativa, Long codigoEntidad);

    /**
     * Importa el procedimiento
     *
     * @param idProcedimiento
     **/
    String importarProcedimiento(Long idProcedimiento, Long codigoEntidad);

    /**
     * Importa el servicio
     *
     * @param idServicio
     **/
    String importarServicio(Long idServicio, Long codigoEntidad);

    /**
     * Obtiene las UAs
     *
     * @param idEntidad
     * @return
     */
    List<BigDecimal> getUAs(Long idEntidad, Long idUARaiz);

    /**
     * Importa UA
     *
     * @param idUA
     * @param codigoEntidad
     * @param idUARaiz
     * @return
     */
    String importarUA(long idUA, Long codigoEntidad, Long idUARaiz);

    /**
     * Obtiene las UAs raiz
     *
     * @return
     */
    List<UnidadAdministrativaDTO> getUnidadAdministrativasRaiz();

    /**
     * Obtener usuarios
     *
     * @return
     */
    List<String> getUsuarios();

    /**
     * Migra un usuario
     *
     * @param idUsuario
     * @return
     */
    String importarUsuario(String idUsuario);

    /**
     * Importar normativas afectaciones
     *
     * @return
     */
    String importarNormativasAfectaciones();

    /**
     * Desactivar restriccion FK de documentos a archivo
     *
     * @return
     */
    String desactivarRestriccionDocumento();

    /**
     * Activar restriccion FK de documentos a archivo
     *
     * @return
     */
    String activarRestriccionDocumento();

    /**
     * Obtiene todos los archivos.
     *
     * @param idEntidad
     * @param uaRaiz
     * @return
     */
    List<FicheroInfo> getDocumentos(Long idEntidad, Long uaRaiz, boolean soloProcedimientos, boolean soloServicios);


    Long getNormativa(Long codigo);

    /**
     * Comprueba si ya existe le archivo.
     *
     * @param idDoc
     * @return
     */
    boolean existeArchivo(Long idDoc);

    /**
     * Devuelve el id Padre
     *
     * @param codigoDocumentoTraduccion
     * @return
     */
    Long getProcedimiento(Long codigoDocumentoTraduccion);

    /**
     * Migrar Archivo
     *
     * @param idFichero
     * @param codigoFicheroRolsac1
     * @param tipoficheroExterno
     */
    void migrarArchivo(Long idFichero, Long codigoFicheroRolsac1, TypeFicheroExterno tipoficheroExterno);
}
