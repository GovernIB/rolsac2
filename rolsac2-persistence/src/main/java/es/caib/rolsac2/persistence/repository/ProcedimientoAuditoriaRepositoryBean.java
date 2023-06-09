package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimientoAuditoria;
import es.caib.rolsac2.persistence.util.JSONUtil;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCMGridDTO;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaCMDTO;
import es.caib.rolsac2.service.model.filtro.AuditoriaFiltro;
import es.caib.rolsac2.service.model.filtro.CuadroMandoFiltro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    public void borrarAuditoriasByIdProcedimiento(Long id) {

        String sql = "SELECT p.codigo FROM JProcedimientoAuditoria p WHERE p.procedimiento.codigo =:id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", id);
        final List<Integer> resultados = query.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            for (Integer codProcAud : resultados) {
                JProcedimientoAuditoria jProcedimientoAuditoria = entityManager.find(JProcedimientoAuditoria.class, codProcAud);
                entityManager.remove(jProcedimientoAuditoria);
            }
        }
        entityManager.flush();
        //String sql = "DELETE FROM JProcedimientoAuditoria p WHERE p.procedimiento.codigo =:id";
        //Query query = entityManager.createQuery(sql);
        //query.setParameter("id", id);
        //query.executeUpdate();
    }

    @Override
    public List<AuditoriaGridDTO> findProcedimientoAuditoriasById(Long id) {
        return findAuditoriasById(id, "PROC");
    }

    @Override
    public List<AuditoriaCMGridDTO> findAuditoriasUltimaSemana(CuadroMandoFiltro filtro) {
        final List<AuditoriaCMGridDTO> auditorias = new ArrayList<>();
        //Creamos fecha de una semana anterior
        Date fechaAhora = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(fechaAhora.toInstant(), ZoneId.systemDefault()).minusDays(7);
        Date fechaSemanaAnterior = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        String sql = "SELECT j.codigo, wf.codigo, j.fechaModificacion, j.usuarioModificacion, j.usuarioPerfil, j.accion, t.nombre FROM JProcedimientoAuditoria j " +
                "LEFT OUTER JOIN JProcedimientoWorkflow wf ON wf.procedimiento.codigo = j.procedimiento.codigo " +
                "LEFT OUTER JOIN JProcedimientoWorkflowTraduccion t ON t.idioma = :idioma AND t.procedimientoWorkflow.codigo = j.procedimiento.codigo " +
                "WHERE j.procedimiento.tipo = :tipo AND j.fechaModificacion >= :fecha " +
                "AND j.fechaModificacion = (SELECT MAX(pr.fechaModificacion) FROM JProcedimientoAuditoria pr WHERE j.procedimiento.codigo = pr.procedimiento.codigo) " +
                "AND (j.accion = 'A' OR j.accion = 'M') ";

        if(filtro.isRellenoUa()) {
            sql+= " AND wf.uaInstructor.codigo = :idUa";
        } else if(filtro.isRellenoEntidad()) {
            sql += " AND wf.uaInstructor.entidad.codigo = :idEntidad";
        }


        final Query query = entityManager.createQuery(sql);
        query.setParameter("idioma", filtro.getIdioma());
        query.setParameter("fecha", fechaSemanaAnterior);
        query.setParameter("tipo", filtro.getTipo());
        if(filtro.isRellenoUa()) {
            query.setParameter("idUa", filtro.getIdUa());
        } else if(filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }

        final List<Object[]> resultados = query.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            for (final Object[] resultado : resultados) {
                final AuditoriaCMGridDTO registroAuditoria = new AuditoriaCMGridDTO();
                registroAuditoria.setCodigo((Integer) resultado[0]);
                registroAuditoria.setIdAuditado((Long) resultado[1]);
                if (resultado[2] != null) {
                    Timestamp fecha = (Timestamp) resultado[2];
                    java.util.Date date = new java.util.Date(fecha.getTime());
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    registroAuditoria.setFecha(format.format(date));
                }

                registroAuditoria.setUsuario((String) resultado[3]);
                registroAuditoria.setUsuarioPerfil((String) resultado[4]);
                registroAuditoria.setAccion((String) resultado[5]);
                registroAuditoria.setNombreAuditado((String) resultado[6]);
                auditorias.add(registroAuditoria);
            }
        }
        return auditorias;
    }



    public EstadisticaCMDTO countByFiltro(CuadroMandoFiltro filtro) {
        //Obtenemos los 7 dias anteriores al de hoy
        List<String> fechas = new ArrayList<>();
        Date fechaAhora = new Date();
        LocalDateTime fechaHoyLdt = LocalDateTime.ofInstant(fechaAhora.toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime primerDiaLdt = fechaHoyLdt.minusDays(7);
        LocalDateTime segundoDiaLdt = fechaHoyLdt.minusDays(6);
        LocalDateTime tercerDiaLdt = fechaHoyLdt.minusDays(5);
        LocalDateTime cuartoDiaLdt = fechaHoyLdt.minusDays(4);
        LocalDateTime quintoDiaLDt = fechaHoyLdt.minusDays(3);
        LocalDateTime sextoDiaLDt = fechaHoyLdt.minusDays(2);
        LocalDateTime septimoDiaLdt = fechaHoyLdt.minusDays(1);
        Date primerDia  = Date.from(primerDiaLdt.atZone(ZoneId.systemDefault()).toInstant());
        Date segundoDia  = Date.from(segundoDiaLdt.atZone(ZoneId.systemDefault()).toInstant());
        Date tercerDia  = Date.from(tercerDiaLdt.atZone(ZoneId.systemDefault()).toInstant());
        Date cuartoDia  = Date.from(cuartoDiaLdt.atZone(ZoneId.systemDefault()).toInstant());
        Date quintoDia  = Date.from(quintoDiaLDt.atZone(ZoneId.systemDefault()).toInstant());
        Date sextoDia  = Date.from(sextoDiaLDt.atZone(ZoneId.systemDefault()).toInstant());
        Date septimoDia  = Date.from(septimoDiaLdt.atZone(ZoneId.systemDefault()).toInstant());
        Date fechaHoy = Date.from(fechaHoyLdt.atZone(ZoneId.systemDefault()).toInstant());

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        fechas.add(simpleDateFormat.format(primerDia));
        fechas.add(simpleDateFormat.format(segundoDia));
        fechas.add(simpleDateFormat.format(tercerDia));
        fechas.add(simpleDateFormat.format(cuartoDia));
        fechas.add(simpleDateFormat.format(quintoDia));
        fechas.add(simpleDateFormat.format(sextoDia));
        fechas.add(simpleDateFormat.format(septimoDia));

        String sql = "SELECT SUM(CASE WHEN (j.fechaModificacion BETWEEN :primerDia AND :segundoDia AND j.procedimiento.tipo = :tipo AND j.accion = :accion) THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN (j.fechaModificacion BETWEEN :segundoDia AND :tercerDia AND j.procedimiento.tipo = :tipo AND j.accion = :accion) THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN (j.fechaModificacion BETWEEN :tercerDia AND :cuartoDia AND j.procedimiento.tipo = :tipo AND j.accion = :accion) THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN (j.fechaModificacion BETWEEN :cuartoDia AND :quintoDia AND j.procedimiento.tipo = :tipo AND j.accion = :accion) THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN (j.fechaModificacion BETWEEN :quintoDia AND :sextoDia AND j.procedimiento.tipo = :tipo AND j.accion = :accion) THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN (j.fechaModificacion BETWEEN :sextoDia AND :septimoDia AND j.procedimiento.tipo = :tipo AND j.accion = :accion) THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN (j.fechaModificacion BETWEEN :septimoDia AND :fechaHoy AND j.procedimiento.tipo = :tipo AND j.accion = :accion) THEN 1 ELSE 0 END) " +
                "FROM JProcedimientoAuditoria j LEFT OUTER JOIN JProcedimientoWorkflow wf ON wf.procedimiento.codigo = j.procedimiento.codigo WHERE 1 = 1 ";

        if(filtro.isRellenoUa()) {
            sql+= " AND wf.uaInstructor.codigo = :idUa";
        } else if(filtro.isRellenoEntidad()) {
            sql += " AND wf.uaInstructor.entidad.codigo = :idEntidad";
        }

        Query query = entityManager.createQuery(sql);
        query.setParameter("primerDia", primerDia);
        query.setParameter("segundoDia", segundoDia);
        query.setParameter("tercerDia", tercerDia);
        query.setParameter("cuartoDia", cuartoDia);
        query.setParameter("quintoDia", quintoDia);
        query.setParameter("sextoDia", sextoDia);
        query.setParameter("septimoDia", septimoDia);
        query.setParameter("fechaHoy", fechaHoy);
        query.setParameter("tipo", filtro.getTipo());
        query.setParameter("accion", filtro.getAccion());
        if(filtro.isRellenoUa()) {
            query.setParameter("idUa", filtro.getIdUa());
        } else if(filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }

        List<Long> resultadosPorDia = new ArrayList<>();
        final Object[] resultado = (Object[]) query.getSingleResult();
        if(resultado != null) {
            resultadosPorDia.add(resultado[0] == null ? 0 : (Long) resultado[0]);
            resultadosPorDia.add(resultado[1] == null ? 0 : (Long) resultado[1]);
            resultadosPorDia.add(resultado[2] == null ? 0 : (Long) resultado[2]);
            resultadosPorDia.add(resultado[3] == null ? 0 : (Long) resultado[3]);
            resultadosPorDia.add(resultado[4] == null ? 0 : (Long) resultado[4]);
            resultadosPorDia.add(resultado[5] == null ? 0 : (Long) resultado[5]);
            resultadosPorDia.add(resultado[6] == null ? 0 : (Long) resultado[6]);
        }

        EstadisticaCMDTO estadistica = new EstadisticaCMDTO();
        estadistica.setDiasSemana(fechas);
        estadistica.setValores(resultadosPorDia);
        return estadistica;

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
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
                registroAuditoria.setLiteralFlujo((String) resultado[6]);
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
                sql = new StringBuilder("SELECT j.codigo, j.procedimiento.codigo, j.fechaModificacion, j.listaModificaciones, j.usuarioModificacion, j.usuarioPerfil, j.literalFlujo  FROM JProcedimientoAuditoria j where 1 = 1 ");
            }
        } else if ("UA".equalsIgnoreCase(tipo)) {
            if (isTotal) {
                sql = new StringBuilder("SELECT count(j) FROM JUnidadAdministrativaAuditoria where 1 = 1 ");
            } else {
                sql = new StringBuilder("SELECT j.codigo, j.procedimiento.codigo, j.fechaModificacion, j.listaModificaciones, j.usuarioModificacion, j.usuarioPerfil, j.literalFlujo  FROM JUnidadAdministrativaAuditoria j where 1 = 1 ");
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
