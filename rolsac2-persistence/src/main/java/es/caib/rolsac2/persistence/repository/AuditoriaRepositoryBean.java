/**
 *
 */
package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JPersonaAuditoria;
import es.caib.rolsac2.persistence.model.JProcedimientoAuditoria;
import es.caib.rolsac2.persistence.util.ConstantesNegocio;
import es.caib.rolsac2.service.exception.AuditoriaException;
import es.caib.rolsac2.service.model.PersonalDTO;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaDTO;
import es.caib.rolsac2.service.model.auditoria.PersonalAuditoria;
import es.caib.rolsac2.service.model.auditoria.ProcedimientoAuditoria;
import es.caib.rolsac2.service.model.filtro.AuditoriaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Indra
 */
@SuppressWarnings("rawtypes")


@Stateless
@Local(AuditoriaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AuditoriaRepositoryBean extends AbstractCrudRepository<JPersonaAuditoria, Long>
        implements AuditoriaRepository {


    /**
     *
     */
    private static final long serialVersionUID = -7075827032023126212L;

    /**
     * Emmagatzema el tipus d'entitat.
     *
     * @param entityClass
     */
    protected AuditoriaRepositoryBean(Class<JPersonaAuditoria> entityClass) {
        super(entityClass);
    }


    @Override
    public void guardarAuditoria(PersonalAuditoria auditoria, Class jEntidad) {
        try {
            //TODO Revisar
            JPersonaAuditoria jAuditoria = (JPersonaAuditoria) jEntidad.getDeclaredMethod("fromModel", auditoria.getClass()).invoke(null, auditoria);
            entityManager.persist(jAuditoria);
            entityManager.flush();
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            throw new AuditoriaException(e);
        }
    }


    @Override
    public void guardarAuditoria(ProcedimientoAuditoria auditoria, Class jEntidad) {
        try {
            //TODO Revisar
            JProcedimientoAuditoria jAuditoria = (JProcedimientoAuditoria) jEntidad.getDeclaredMethod("fromModel", auditoria.getClass()).invoke(null, auditoria);
            entityManager.persist(jAuditoria);
            entityManager.flush();
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            throw new AuditoriaException(e);
        }
    }


    /**
     * Consulta los registros de una auditoria Lst
     */
    @Override
    public List<AuditoriaGridDTO> listarAuditoria(final AuditoriaFiltro filtro) {
        final List<AuditoriaGridDTO> auditoria = new ArrayList<>();
        final Query query = getQueryListAuditoria(false, filtro);
        @SuppressWarnings("unchecked") final List<Object[]> resultados = query.getResultList();

        if (resultados != null && !resultados.isEmpty()) {
            for (final Object[] resultado : resultados) {
                final AuditoriaGridDTO registroAuditoria = new AuditoriaGridDTO();
                //registroAuditoria.toModel(resultado);
                //auditoria.add(registroAuditoria);
            }
        }

        return auditoria;
    }

    /**
     * Consulta que se realiza en la tabla de auditoria de personas
     *
     * @param total
     * @param filtro
     * @return
     */
    private Query getQueryListAuditoria(final boolean total, final AuditoriaFiltro filtro) {
        final String sql = getSql(total, filtro);

        // Ingresa los parámetros
        final Query query = entityManager.createQuery(sql);

        query.setParameter(ConstantesNegocio.AUDITORIA_PARAMETRO_CODIGO, filtro.getCodigo());
        if (filtro.isRellenoFechaDesde()) {
            query.setParameter("fechaDesde", filtro.getFechaDesde());
        }

        if (filtro.isRellenoFechaHasta()) {
            query.setParameter("fechaHasta", filtro.getFechaHasta());
        }


        if (filtro.isRellenoCambioPuesto() && filtro.getAuditoriaEntidad() instanceof PersonalDTO) {
            query.setParameter("cambioPuesto", filtro.isCambioPuesto());
        }

        if (filtro.isMostrarEtapas()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        if (!total && filtro.isPaginacionActiva()) {
            query.setMaxResults(filtro.getPaginaTamanyo());
            query.setFirstResult(filtro.getPaginaTamanyo() * filtro.getPaginaFirst());
        }

        return query;
    }

    /**
     * Contruye la consutla Sql
     *
     * @param total  Indica si se debe consultar el total de registros
     * @param filtro filtro para las consultas
     * @return
     */

    private String getSql(final boolean total, final AuditoriaFiltro filtro) {
        StringBuilder sql = null;

        if (total) {
            sql = new StringBuilder(ConstantesNegocio.AUDITORIA_SELECT_COUNT);
        } else {
            sql = new StringBuilder(ConstantesNegocio.AUDITORIA_SELECT_BASE_GRID);
            if (filtro.isMostrarResponsable()) {
                sql.append(ConstantesNegocio.AUDITORIA_SELECT_CAMPO_RESPONSABLE);
            }
            if (filtro.isMostrarCambioPuesto()) {
                sql.append(ConstantesNegocio.AUDITORIA_SELECT_CAMPO_CAMBIOPUESTO);
            }
            if (filtro.isMostrarEtapas()) {
                sql.append(ConstantesNegocio.AUDITORIA_SELECT_CAMPO_ETAPAS);
            }
        }

        //construirFromWhereCodigo(sql, filtro);
        construirFiltros(sql, filtro);

        if (!total) {
            sql.append(" order by " + filtro.getOrderBy());
            if (filtro.isRellenoAscendente()) {
                sql.append(" asc ");
            } else {
                sql.append(" desc ");
            }
        }
        return sql.toString();
    }

    /**
     * Construye el sql para los filtros de la consulta
     *
     * @param sql
     * @param filtro
     */
    private void construirFiltros(final StringBuilder sql, final AuditoriaFiltro filtro) {
        if (filtro.isRellenoFechaDesde()) {
            sql.append(" AND P.fechaAuditoria >= :fechaDesde");
        }

        if (filtro.isRellenoFechaHasta()) {
            sql.append(" AND trunc(P.fechaAuditoria) <= :fechaHasta");
        }

        if (filtro.isRellenoCambioPuesto() && filtro.getAuditoriaEntidad() instanceof PersonalDTO) {
            sql.append(" AND P.cambioPuesto = :cambioPuesto");
        }

    }


    /**
     * Lista el total de registros de una consulta
     */
    @Override
    public int listarAuditoriaTotal(final AuditoriaFiltro filtro) {
        final Query query = getQueryListAuditoria(true, filtro);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public JPersonaAuditoria obtenerAuditoriaPorCodigo(Long codigo, Class entidadAuditoria) {
        return null;
    }

    @Override
    public List<EstadisticaDTO> listarEstadisticas(AuditoriaFiltro auditoriaFiltro) {
        return null;
    }



}
