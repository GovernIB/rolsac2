package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEstadistica;
import es.caib.rolsac2.service.model.auditoria.EstadisticaCMDTO;
import es.caib.rolsac2.service.model.auditoria.Periodo;
import es.caib.rolsac2.service.model.filtro.CuadroMandoFiltro;
import es.caib.rolsac2.service.model.filtro.EstadisticaFiltro;
import es.caib.rolsac2.service.utils.PeriodoUtil;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(EstadisticaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EstadisticaRepositoryBean extends AbstractCrudRepository<JEstadistica, Long> implements EstadisticaRepository {

    protected EstadisticaRepositoryBean() {
        super(JEstadistica.class);
    }

    @Override
    public Optional<JEstadistica> findById(String id) {
        TypedQuery<JEstadistica> query = entityManager.createNamedQuery(JEstadistica.FIND_BY_ID, JEstadistica.class);
        query.setParameter("id", id);
        List<JEstadistica> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public JEstadistica findByUk(EstadisticaFiltro filtro) {
        TypedQuery<JEstadistica> query;
        if (filtro.getTipo().equals("P") || filtro.getTipo().equals("S")) {
            query = entityManager.createNamedQuery(JEstadistica.FIND_BY_UK_PROC, JEstadistica.class);
        } else {
            query = entityManager.createNamedQuery(JEstadistica.FIND_BY_UK_UA, JEstadistica.class);
        }
        query.setParameter("codigo", filtro.getCodigo());
        query.setParameter("idApp", filtro.getIdApp());
        query.setParameter("tipo", filtro.getTipo());

        return query.getSingleResult();
    }

    @Override
    public Boolean checkExisteEstadistica(EstadisticaFiltro filtro) {
        TypedQuery<Long> query;
        if (filtro.getTipo().equals("P") || filtro.getTipo().equals("S")) {
            query = entityManager.createNamedQuery(JEstadistica.COUNT_BY_UK_PROC, Long.class);
        } else {
            query = entityManager.createNamedQuery(JEstadistica.COUNT_BY_UK_UA, Long.class);
        }
        query.setParameter("codigo", filtro.getCodigo());
        query.setParameter("idApp", filtro.getIdApp());
        query.setParameter("tipo", filtro.getTipo());
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    public EstadisticaCMDTO countEstadisticasMensuales(CuadroMandoFiltro filtro) {
        Date fechaHoy = new Date();

        List<Periodo> meses = PeriodoUtil.crearListaMeses(PeriodoUtil.getNPreviousMonth(fechaHoy, 11), fechaHoy);

        String sql = "SELECT SUM(CASE WHEN a.fechaAcceso BETWEEN :primerMes AND :segundoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :segundoMes AND :tercerMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :tercerMes AND :cuartoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :cuartoMes AND :quintoMes THEN a.contador ELSE 0 END)," +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :quintoMes AND :sextoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :sextoMes AND :septimoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :septimoMes AND :octavoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :octavoMes AND :novenoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :novenoMes AND :decimoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :decimoMes AND :undecimoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :undecimoMes AND :duodecimoMes THEN a.contador ELSE 0 END), " +
                "SUM(CASE WHEN a.fechaAcceso BETWEEN :duodecimoMes AND :fechaFinalMes THEN a.contador ELSE 0 END) " +
                "FROM JEstadistica j LEFT OUTER JOIN JEstadisticaAcceso a ON a.codEstadistica.codigo = j.codigo " +
                "WHERE j.tipo = :tipo ";

        if (filtro.isRellenoIdApp()) {
            sql += "AND j.identificadorApp = :idApp ";
        }

        if (filtro.isRellenoUa()) {
            sql += " AND j.unidadAdministrativa.codigo = :idUa";
        } else if (filtro.isRellenoEntidad()) {
            sql += " AND j.unidadAdministrativa.entidad.codigo = :idEntidad";
        }

        Query query = entityManager.createQuery(sql);

        query.setParameter("primerMes", meses.get(0).getFechaInicio());
        query.setParameter("segundoMes", meses.get(1).getFechaInicio());
        query.setParameter("tercerMes", meses.get(2).getFechaInicio());
        query.setParameter("cuartoMes", meses.get(3).getFechaInicio());
        query.setParameter("quintoMes", meses.get(4).getFechaInicio());
        query.setParameter("sextoMes", meses.get(5).getFechaInicio());
        query.setParameter("septimoMes", meses.get(6).getFechaInicio());
        query.setParameter("octavoMes", meses.get(7).getFechaInicio());
        query.setParameter("novenoMes", meses.get(8).getFechaInicio());
        query.setParameter("decimoMes", meses.get(9).getFechaInicio());
        query.setParameter("undecimoMes", meses.get(10).getFechaInicio());
        query.setParameter("duodecimoMes", meses.get(11).getFechaInicio());
        query.setParameter("fechaFinalMes", meses.get(11).getFechaFin());
        query.setParameter("tipo", filtro.getTipo());

        if (filtro.isRellenoIdApp()) {
            query.setParameter("idApp", filtro.getIdApp());
        }
        if (filtro.isRellenoUa()) {
            query.setParameter("idUa", filtro.getIdUa());
        } else if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }

        EstadisticaCMDTO estadistica = new EstadisticaCMDTO();

        final Object[] resultado = (Object[]) query.getSingleResult();
        if (resultado != null) {
            List<Long> resultadosPorMes = new ArrayList<>();
            resultadosPorMes.add(resultado[0] == null ? 0 : (Long) resultado[0]);
            resultadosPorMes.add(resultado[1] == null ? 0 : (Long) resultado[1]);
            resultadosPorMes.add(resultado[2] == null ? 0 : (Long) resultado[2]);
            resultadosPorMes.add(resultado[3] == null ? 0 : (Long) resultado[3]);
            resultadosPorMes.add(resultado[4] == null ? 0 : (Long) resultado[4]);
            resultadosPorMes.add(resultado[5] == null ? 0 : (Long) resultado[5]);
            resultadosPorMes.add(resultado[6] == null ? 0 : (Long) resultado[6]);
            resultadosPorMes.add(resultado[7] == null ? 0 : (Long) resultado[7]);
            resultadosPorMes.add(resultado[8] == null ? 0 : (Long) resultado[8]);
            resultadosPorMes.add(resultado[9] == null ? 0 : (Long) resultado[9]);
            resultadosPorMes.add(resultado[10] == null ? 0 : (Long) resultado[10]);
            resultadosPorMes.add(resultado[11] == null ? 0 : (Long) resultado[11]);
            estadistica.setValores(resultadosPorMes);
            List<String> mesesFormateado = new ArrayList<>();
            String pattern = "MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            meses.forEach(m -> mesesFormateado.add(simpleDateFormat.format(m.getFechaInicio())));
            estadistica.setMeses(mesesFormateado);
        }
        return estadistica;
    }

    public List<String> obtenerAplicaciones() {
        String sql = "SELECT j.identificadorApp FROM JEstadistica j GROUP BY j.identificadorApp";
        Query query = entityManager.createQuery(sql, String.class);
        return query.getResultList();
    }
}
