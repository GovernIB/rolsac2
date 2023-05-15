package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JUnidadAdministrativaAuditoria;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;

import java.util.List;

public interface UnidadAdministrativaAuditoriaRepository extends CrudRepository<JUnidadAdministrativaAuditoria, Long> {

    void guardar(JUnidadAdministrativaAuditoria jUaAudit);

    /**
     * Devuelve una lista de procedimento auditorias.
     *
     * @param id
     * @return
     */
    List<AuditoriaGridDTO> findUaAuditoriasById(Long id);

    /**
     * Borra las auditorias segun el codigo de UA
     *
     * @param codigo
     */
    void borrarAuditoriasByIdUA(Long codigo);
}
