package es.caib.rolsac2.service.facade;


import es.caib.rolsac2.service.model.auditoria.AuditoriaCMGridDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaCMDTO;
import es.caib.rolsac2.service.model.filtro.CuadroMandoFiltro;

import java.util.List;

/**
 * Servicio para obtener las estadísticas del cuadro de mando .
 *
 * @author Indra
 */
public interface CuadroMandoServiceFacade {


    /**
     * Devuelve una lista de auditorias la última semana.
     * @return
     */
    List<AuditoriaCMGridDTO> findAuditoriasUltimaSemana(CuadroMandoFiltro filtro);

    /**
     * Devuelve el listado de altas de procedimientos de la última semana
     * @return listado de altas
     */
    EstadisticaCMDTO countByFiltro(CuadroMandoFiltro filtro);

    /**
     * Cuenta el número de procedimientos que tiene una UA
     * @param uaId
     * @return
     */
    Long countProcedimientosByUa(Long uaId);

    /**
     * Cuenta el número de procedimientos totales en una entidad
     * @return
     */
    Long countAllProcedimientos();

    /**
     * Cuenta el número de servicios que tiene una UA
     * @param uaId
     * @return
     */
    Long countServicioByUa(Long uaId);

    /**
     * Cuenta el número de servicios totales en una entidad
     * @return
     */
    Long countAllServicio();

    /**
     * Cuenta el número de procedimientos en una UA en función de su estado
     * @param uaId
     * @param estado
     * @return
     */
    Long countProcEstadoByUa(Long uaId, String estado);

    /**
     * Cuenta el número de servicios en una UA en función de su estado
     * @param uaId
     * @param estado
     * @return
     */
    Long countServEstadoByUa(Long uaId, String estado);

    EstadisticaCMDTO obtenerEstadisiticasMensuales(CuadroMandoFiltro filtro);

    List<String> obtenerAplicacionesEstadistica();

}
