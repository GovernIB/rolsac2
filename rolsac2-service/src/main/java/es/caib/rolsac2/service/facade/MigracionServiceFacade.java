package es.caib.rolsac2.service.facade;


import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import java.math.BigDecimal;
import java.util.List;

/**
 * Instancia service.
 *
 * @author Indra
 */
public interface MigracionServiceFacade {

    /**
     * Ejecuta un método.
     *
     * @param metodo
     * @param param1
     * @param param2
     * @return Peticion que tenga el código
     */
    String ejecutarMetodo(String metodo, String param1, String param2);

    /**
     * Devuelve las UAs.
     *
     * @param idEntidad
     * @param idUARaiz
     * @return
     */
    List<BigDecimal> getUAs(Long idEntidad, Long idUARaiz);

    /**
     * Ejecuta el método de migrar Unidades administrativas
     *
     * @param idEntidad
     * @return
     */
    String migrarUAs(List<BigDecimal> idUAs, Long idEntidad, Long idUARaiz, String usuarios);

    /**
     * Ejecuta el método de migrar normativas
     *
     * @param idEntidad
     * @return
     */
    String migrarNormativas(Long idEntidad);

    /**
     * Ejecuta el método de migrar procedimientos y servicios.
     *
     * @param idEntidad
     * @return
     */
    String migrarProcedimientos(Long idEntidad, Long idUARaiz);

    String migrarServicios(Long idEntidad, Long idUARaiz);

    /**
     * Activa las secuencias y restricciones.
     */
    void ejecutarDesactivarRestricciones();

    /**
     * Activa las secuencias y restricciones.
     */
    void ejecutarActivarRestriccionesSecuencias();

    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    void ejecutarRevisarSecuencias();

    /**
     * Obtiene las UAs raiz
     *
     * @return
     */
    List<UnidadAdministrativaDTO> getUnidadAdministrativasRaiz();

    /**
     * Migrar usuarios.
     *
     * @return
     */
    String migrarUsuarios();
}
