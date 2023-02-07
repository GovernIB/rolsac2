package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimientoAuditoria;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativaAuditoria;
import es.caib.rolsac2.persistence.util.JSONUtil;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.filtro.AuditoriaFiltro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(ProcedimientoAuditoriaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ProcedimientoAuditoriaRepositoryBean extends AbstractCrudRepository<JProcedimientoAuditoria, Long> implements ProcedimientoAuditoriaRepository {

    protected ProcedimientoAuditoriaRepositoryBean() {
        super(JProcedimientoAuditoria.class);
    }


    @Override
    public void guardar(JProcedimientoAuditoria jprocedimientoAuditoria) {
        entityManager.persist(jprocedimientoAuditoria);
    }

    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoAuditoriaRepository.class);

    @Override
    public List<AuditoriaGridDTO> findUAAuditoriasById(Long id) {
        return findAuditoriasById(id, "UA");
    }

    @Override
    public List<AuditoriaGridDTO> findProcedimientoAuditoriasById(Long id) {
        return findAuditoriasById(id, "PROC");
    }

    private List<AuditoriaGridDTO> findAuditoriasById(Long id, String tipo) {
        final List<AuditoriaGridDTO> auditorias = new ArrayList<>();
        AuditoriaFiltro filtro = new AuditoriaFiltro();
        if ("PROC".equals(tipo)) {
            filtro.setProcedimiento(id);
        }
        if ("UA".equals(tipo)) {
            filtro.setIdUA(id);
        }
        final Query query = getQueryListAuditoria(false, tipo, filtro);
        @SuppressWarnings("unchecked") final List<Object[]> resultados = query.getResultList();

        if (resultados != null && !resultados.isEmpty()) {
            for (final Object[] resultado : resultados) {
                final AuditoriaGridDTO registroAuditoria = new AuditoriaGridDTO();
                registroAuditoria.setCodigo((Integer) resultado[0]);
                if (resultado[2] != null) {
                    Timestamp fecha = (Timestamp) resultado[2];
                    java.util.Date date = new java.util.Date(fecha.getTime());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    registroAuditoria.setFecha(format.format(date));
                }

                registroAuditoria.setModificaciones((String) resultado[3]);
                try {
                    registroAuditoria.setCambios((List<AuditoriaCambio>) JSONUtil.fromListJSON((String) resultado[3], AuditoriaCambio.class));
                } catch (Exception e) {
                    LOG.error("Error generando del json a list auditoriaCambio", e);
                }

                registroAuditoria.setUsuario((String) resultado[4]);
                registroAuditoria.setUsuarioPerfil((String) resultado[5]);
                auditorias.add(registroAuditoria);
            }
        }

        return auditorias;
    }


    /**
     * Consulta que se realiza en la tabla de auditoria de personas
     *
     * @param total
     * @param filtro
     * @return
     */
    private Query getQueryListAuditoria(final boolean total, final String tipo, final AuditoriaFiltro filtro) {
        final String sql = getSql(total, tipo, filtro);

        // Ingresa los parámetros
        final Query query = entityManager.createQuery(sql);

        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }

        if (filtro.isRellenoFechaDesde()) {
            query.setParameter("fechaDesde", filtro.getFechaDesde());
        }

        if (filtro.isRellenoFechaHasta()) {
            query.setParameter("fechaHasta", filtro.getFechaHasta());
        }

        if (filtro.isRellenoProcedimiento()) {
            query.setParameter("procedimiento", filtro.getProcedimiento());
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
     * @param isTotal Indica si se debe consultar el total de registros
     * @param filtro  filtro para las consultas
     * @return
     */

    private String getSql(final boolean isTotal, final String tipo, final AuditoriaFiltro filtro) {
        StringBuilder sql;
        if ("PROC".equalsIgnoreCase(tipo)) {
            if (isTotal) {
                sql = new StringBuilder("SELECT count(j) FROM JProcedimientoAuditoria where 1 = 1 ");
            } else {
                sql = new StringBuilder(
                        "SELECT j.codigo, j.procedimiento.codigo, j.fechaModificacion, j.listaModificaciones, j.usuarioModificacion, j.usuarioPerfil  FROM JProcedimientoAuditoria j where 1 = 1 ");
            }
        } else if ("UA".equalsIgnoreCase(tipo)) {
            if (isTotal) {
                sql = new StringBuilder("SELECT count(j) FROM JUnidadAdministrativaAuditoria where 1 = 1 ");
            } else {
                sql = new StringBuilder(
                        "SELECT j.codigo, j.procedimiento.codigo, j.fechaModificacion, j.listaModificaciones, j.usuarioModificacion  FROM JUnidadAdministrativaAuditoria j where 1 = 1 ");
            }
        } else {
            sql = null;
        }

        if (filtro.isRellenoCodigo()) {
            sql.append(" AND j.codigo = :codigo ");
        }
        if (filtro.isRellenoFechaDesde()) {
            sql.append(" AND FECHA_X >= j.fechaModificacion  ");
        }
        if (filtro.isRellenoFechaHasta()) {
            sql.append(" AND FECHA_X <= j.fechaModificacion  ");
        }
        if (filtro.isRellenoProcedimiento()) {
            sql.append(" AND j.procedimiento.codigo = :procedimiento ");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(filtro.getOrderBy());
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        return sql.toString();
    }
}
