package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimientoAuditoria;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativaAuditoria;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;

import java.util.List;

public interface UnidadAdministrativaAuditoriaRepository extends CrudRepository<JUnidadAdministrativaAuditoria, Long> {

    /**
     * Devuelve una lista de procedimento auditorias.
     *
     * @param id
     * @return
     */
    List<AuditoriaGridDTO> findUaAuditoriasById(Long id);
}
