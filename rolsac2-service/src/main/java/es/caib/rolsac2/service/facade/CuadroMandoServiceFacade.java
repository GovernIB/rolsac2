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
     *
     * @return
     */
    List<AuditoriaCMGridDTO> findAuditoriasUltimaSemana(CuadroMandoFiltro filtro);

    /**
     * Devuelve el listado de altas de procedimientos de la última semana
     *
     * @return listado de altas
     */
    EstadisticaCMDTO countByFiltro(CuadroMandoFiltro filtro);

    /**
     * Cuenta el número de procedimientos que tiene una UA
     *
     * @param uaId
     * @return
     */
    Long countProcedimientosByUa(Long uaId);

    /**
     * Cuenta el número de procedimientos totales en una entidad
     *
     * @return
     */
    Long countAllProcedimientos();

    /**
     * Cuenta el número de servicios que tiene una UA
     *
     * @param uaId
     * @return
     */
    Long countServicioByUa(Long uaId);

    /**
     * Cuenta el número de servicios totales en una entidad
     *
     * @return
     */
    Long countAllServicio();

    /**
     * Cuenta el número de procedimientos en una UA en función de su estado
     *
     * @param uaId
     * @param estado
     * @return
     */
    //Long countProcEstadoByUa(Long uaId, String estado);

    /**
     * Cuenta el número de servicios en una UA en función de su estado
     *
     * @param uaId
     * @param estado
     * @return
     */
    //Long countServEstadoByUa(Long uaId, String estado);

    EstadisticaCMDTO obtenerEstadisiticasMensuales(CuadroMandoFiltro filtro);

    List<String> obtenerAplicacionesEstadistica();

    /**
     * Método nuevo para obtener los procedimientos/servicios de una UA. <br />
     * Hay que tener en cuenta que se pasa 3 totales, de los cuales:
     * <ul>
     *     <li>0: Total de procedimientos/servicios visibles</li>
     *     <li>1: Total de procedimientos/servicios no visibles</li>
     *     <li>2: Total de procedimientos/servicios </li>
     *  </ul>
     *
     * @param codigo Código de la UA
     * @param tipo   Tipo de procedimiento (P Procedimiento, S Servicio)
     * @param lang   Idioma
     * @return
     */
    Long[] getProcedimientosByUa(Long codigo, String tipo, String lang);
}
