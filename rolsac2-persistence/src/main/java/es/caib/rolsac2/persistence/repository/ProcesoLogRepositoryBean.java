package es.caib.rolsac2.persistence.repository;


import es.caib.rolsac2.persistence.model.JProceso;
import es.caib.rolsac2.persistence.model.JProcesoLog;
import es.caib.rolsac2.service.model.ProcesoLogDTO;
import es.caib.rolsac2.service.model.ProcesoLogGridDTO;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;
import es.caib.rolsac2.service.model.types.TypeEstadoProceso;
import es.caib.rolsac2.service.utils.UtilJSON;


import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@Local(ProcesoLogRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ProcesoLogRepositoryBean extends AbstractCrudRepository<JProcesoLog, Long> implements ProcesoLogRepository {


  protected ProcesoLogRepositoryBean() {
    super(JProcesoLog.class);
  }

  @Inject
  ProcesoRepository procesoRepository;


  @Override
  public Integer listarTotal(final ProcesoLogFiltro filtro) {
    final Query query = getQueryList(true, filtro);
    return Integer.valueOf(query.getSingleResult().toString());
  }

  /**
   * Método que obtiene la lista de datos
   */
  @Override
  public List<ProcesoLogGridDTO> listar(final ProcesoLogFiltro filtro) {
    final Query query = getQueryList(false, filtro);
    final List<ProcesoLogGridDTO> listgrid = new ArrayList<>();
    final List<Object[]> resultados = query.getResultList();
    if (resultados != null && !resultados.isEmpty()) {
      ProcesoLogGridDTO tipovalgrid = null;
      Object[] resultado = null;
      for (int i = 0; i < resultados.size(); i++) {
        resultado = resultados.get(i);
        tipovalgrid = ProcesoLogGridDTO.cast(resultado);
        listgrid.add(tipovalgrid);
      }
    }
    return listgrid;
  }

  @Override
  public ProcesoLogDTO obtenerProcesoLogPorCodigo(final Long codigo) {
    ProcesoLogDTO proceso = null;
    final JProcesoLog jpeticion = this.findById(codigo);
    if (jpeticion != null) {
      proceso = convertProcesoLog(jpeticion);
    }
    return proceso;
  }

  /**
   * Obtiene la sql.
   *
   * @param total
   * @param filtro
   * @return
   */
  private String getSql(final boolean total, final ProcesoLogFiltro filtro) {
    StringBuilder sql;
    if (total) {
      sql = new StringBuilder("SELECT COUNT(PL) ");
    } else {
      sql = new StringBuilder(
          "SELECT PL.codigo, PL.proceso.codigo, PL.fechaInicio, PL.fechaFin, PL.estadoProceso, PL.proceso.identificadorProceso, PL.proceso.descripcion, PL.informacionProceso ");
    }
    sql.append("FROM JProcesoLog PL ");

    sql.append("WHERE 1=1 ");

    if (filtro.isRellenoProceso()) {
      sql.append(" and PL.proceso.codigo =:codigoproceso");
    }

    if (filtro.isRellenoFechaInicio()) {
      sql.append(" and TRUNC(PL.fechaInicio) >= TRUNC(:fechaInicio)");
    }
    if (filtro.isRellenoFechaFin()) {
      sql.append(" and TRUNC(PL.fechaFin) <= TRUNC(:fechaFin)");
    }
    if (filtro.isRellenoEstadoProceso()) {
      sql.append(" and PL.estadoProceso = :estadoProceso");
    }

    if(filtro.isRellenoEntidad()) {
      sql.append(" and PL.proceso.entidad.codigo = :entidad");
    }

    if (!total) {
      sql.append(" ORDER BY " + filtro.getOrderBy());
      if (filtro.isRellenoAscendente()) {
        sql.append(" asc ");
      } else {
        sql.append(" desc ");
      }
    }
    return sql.toString();
  }

  /**
   * Método que genera el objeto Query tanto para el count como para la select.
   *
   * @param total
   * @param filtro
   * @return
   */
  private Query getQueryList(final boolean total, final ProcesoLogFiltro filtro) {
    final String sql = getSql(total, filtro);

    final Query query = entityManager.createQuery(sql);

    if (filtro.isRellenoProceso()) {
      query.setParameter("codigoproceso", filtro.getProceso().getCodigo());
    }

    if (filtro.isRellenoFechaInicio()) {
      query.setParameter("fechaInicio", filtro.getFechaInicio());
    }
    if (filtro.isRellenoFechaFin()) {
      query.setParameter("fechaFin", filtro.getFechaFin());
    }

    if (filtro.isRellenoEstadoProceso()) {
      query.setParameter("estadoProceso", filtro.getEstadoProceso().toString());
    }

    if(filtro.isRellenoEntidad()) {
      query.setParameter("entidad", filtro.getIdEntidad());
    }

    if (!total && filtro.isPaginacionActiva()) {
      query.setMaxResults(filtro.getPaginaTamanyo());
      query.setFirstResult(filtro.getPaginaFirst());
    }

    return query;
  }

  @Override
  public List<ProcesoLogGridDTO> listar(final String idioma, final String tipo) {
    final ProcesoLogFiltro filtro = new ProcesoLogFiltro();
    filtro.setIdioma(idioma);
    filtro.setPaginaFirst(0);
    filtro.setPaginaTamanyo(100);
    return listar(filtro);
  }

  @Override
  public Long auditarInicioProceso(final String idProceso) {
    final JProceso jproceso = obtenerProcesoPorIdentificador(idProceso);
    final JProcesoLog jproclog = new JProcesoLog();
    jproclog.setProceso(jproceso);
    jproclog.setFechaInicio(new Date());
    jproclog.setEstadoProceso(TypeEstadoProceso.VACIO.toString());
    this.entityManager.persist(jproclog);
    return jproclog.getCodigo();
  }

  @Override
  public void auditarFinProceso(final String idProceso, final Long instanciaProceso, final ResultadoProcesoProgramado resultadoProceso) {
    final JProcesoLog jProcesoLog = entityManager.find(JProcesoLog.class, instanciaProceso);
    if (jProcesoLog != null) {
      jProcesoLog.setEstadoProceso(resultadoProceso.isFinalizadoOk() ? TypeEstadoProceso.CORRECTO.toString() : TypeEstadoProceso.ERROR.toString());
      jProcesoLog.setFechaFin(new Date());
      jProcesoLog.setMensajeError(resultadoProceso.getMensajeError());
      jProcesoLog.setInformacionProceso(UtilJSON.toJSON(resultadoProceso.getDetalles()));
      this.update(jProcesoLog);
    }
  }

  @Override
  public Date obtenerUltimaEjecucion(final String idProceso) {
    final String sql = "SELECT max(PL.fechaInicio) from JProcesoLog PL WHERE PL.proceso.identificadorProceso = :idProceso";
    final Query query = entityManager.createQuery(sql);
    query.setParameter("idProceso", idProceso);
    final Date result = (Date) query.getSingleResult();
    return result;
  }


  public JProceso obtenerProcesoPorIdentificador(final String identificador) {
    final String sql = "SELECT P FROM JProceso P WHERE P.identificadorProceso = :identificador";
    final Query query = entityManager.createQuery(sql);
    query.setParameter("identificador", identificador);
    final JProceso jproceso = (JProceso) query.getSingleResult();
    return jproceso;
  }

  public ProcesoLogDTO convertProcesoLog(JProcesoLog jProcesoLog) {
    ProcesoLogDTO procesoLogDTO = jProcesoLog.toModel();
    procesoLogDTO.setProceso(procesoRepository.convertProceso(jProcesoLog.getProceso()));
    return procesoLogDTO;
  }

}


