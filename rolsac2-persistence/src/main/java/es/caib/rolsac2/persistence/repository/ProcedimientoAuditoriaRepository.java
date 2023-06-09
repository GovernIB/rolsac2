package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimientoAuditoria;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCMGridDTO;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaCMDTO;
import es.caib.rolsac2.service.model.filtro.CuadroMandoFiltro;

import java.util.List;

public interface ProcedimientoAuditoriaRepository extends CrudRepository<JProcedimientoAuditoria, Long> {

    void guardar(JProcedimientoAuditoria jprocAudit);

    /**
     * Devuelve una lista de procedimento auditorias.
     *
     * @param id
     * @return
     */
    List<AuditoriaGridDTO> findProcedimientoAuditoriasById(Long id);

    /**
     * Devuelve una lista de procedimento auditorias.
     *
     * @param id
     * @return
     */
    List<AuditoriaGridDTO> findUAAuditoriasById(Long id);

    /**
     * Devuelve una lista de auditorias de procedimientos o servicios de la última semana por entidad.
     * @return
     */
    List<AuditoriaCMGridDTO> findAuditoriasUltimaSemana(CuadroMandoFiltro filtro);


    /**
     * Devuelve una lista en la que se contabilizan las altas, bajas o modificaciones de la última semana
     * para procedimientos o servicios
     * @param tipo
     * @param accion
     * @return
     */
    EstadisticaCMDTO countByFiltro(CuadroMandoFiltro filtro);



    /**
     * Borra las auditorias según el id procedimiento.
     *
     * @param id
     * @return
     */
    void borrarAuditoriasByIdProcedimiento(Long id);
}
