/**
 *
 */
package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JPersonaAuditoria;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaDTO;
import es.caib.rolsac2.service.model.auditoria.PersonalAuditoria;
import es.caib.rolsac2.service.model.auditoria.ProcedimientoAuditoria;
import es.caib.rolsac2.service.model.filtro.AuditoriaFiltro;

import java.util.List;

/**
 * Accede a los datos de autidoría de una clase
 *
 * @author Indra
 */
public interface AuditoriaRepository extends CrudRepository<JPersonaAuditoria, Long> {

    /**
     * Guarda la auditoría según la clase indicada
     *
     * @param <U>       Tipo de clase que se va a guardar
     * @param auditoria Objeto que tiene la información a guardar
     * @param jEntidad  Entidad por la que se va a guardar la auditoría
     */
    public <U> void guardarAuditoria(PersonalAuditoria auditoria, Class<U> jEntidad);


    /**
     * Guarda la auditoría según la clase indicada
     *
     * @param <U>       Tipo de clase que se va a guardar
     * @param auditoria Objeto que tiene la información a guardar
     * @param jEntidad  Entidad por la que se va a guardar la auditoría
     */
    public <U> void guardarAuditoria(ProcedimientoAuditoria auditoria, Class<U> jEntidad);

    /**
     * Lista la auditoria de la entidad dada
     *
     * @param filtro
     * @return
     */
    public List<AuditoriaGridDTO> listarAuditoria(final AuditoriaFiltro filtro);

    /**
     * Lista el total de resitros
     *
     * @param filtro
     * @return
     */
    public int listarAuditoriaTotal(final AuditoriaFiltro filtro);

    /**
     * Consulta auditoria de una entidad
     *
     * @param <U>
     * @param codigo
     * @param entidadAuditoria
     * @return
     */
    public <U> JPersonaAuditoria obtenerAuditoriaPorCodigo(Long codigo, Class<U> entidadAuditoria);

    /**
     * Lista las estadisticas para el report
     *
     * @param auditoriaFiltro
     * @return
     */
    public List<EstadisticaDTO> listarEstadisticas(AuditoriaFiltro auditoriaFiltro);

}
