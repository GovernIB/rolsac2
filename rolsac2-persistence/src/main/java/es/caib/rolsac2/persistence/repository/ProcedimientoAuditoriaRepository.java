package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimientoAuditoria;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;

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
     * Borra las auditorias según el id procedimiento.
     *
     * @param id
     * @return
     */
    void borrarAuditoriasByIdProcedimiento(Long id);
}
