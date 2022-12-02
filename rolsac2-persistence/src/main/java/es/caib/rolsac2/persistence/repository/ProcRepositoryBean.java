package es.caib.rolsac2.persistence.repository;


import es.caib.rolsac2.persistence.model.JProceso;
import es.caib.rolsac2.persistence.model.JProcesoControl;
import es.caib.rolsac2.service.exception.ProcesoException;
import es.caib.rolsac2.service.model.ProcesoDTO;
import es.caib.rolsac2.service.model.ProcesoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;


import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Stateless
@Local(ProcRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ProcRepositoryBean extends AbstractCrudRepository<JProceso, Long> implements ProcRepository {

    protected ProcRepositoryBean() {
        super(JProceso.class);
    }


    @Override
    public boolean verificarMaestro(String instanciaId, int minMaxMaestroInactivo) {
        boolean res = false;
        boolean tomarControl = false;
        final Date fechaActual = new Date();

        // Recuperamos info actual (debe existir siempre)
        final JProcesoControl jProceso = entityManager.find(JProcesoControl.class, "MAESTRO");
        if (jProceso == null) {
            throw new ProcesoException("No existe fila en tabla de procesos con id " + "MAESTRO");
        }

        // Verifica si esta configurado como maestro
        // - Instancia actual
        if (instanciaId.equals(jProceso.getInstancia())) {
            // Intentamos renovar
            tomarControl = true;
        } else {
            // Si se ha sobrepasado el tiempo sin que la instancia configurada como maestro
            // este activa, se intenta tomar control
            final Calendar cal = Calendar.getInstance();
            cal.setTime(jProceso.getFecha());
            cal.add(Calendar.MINUTE, minMaxMaestroInactivo);
            final Date fechaLimite = cal.getTime();
            tomarControl = fechaLimite.before(fechaActual);
        }

        // Toma de control
        if (tomarControl) {
            final String sql =
                    "UPDATE JProcesoControl p SET p.instancia = :instanciaActual, p.fecha = :fechaActual " +
                            "WHERE p.fecha = :fechaOld AND p.instancia = :instanciaOld";
            final Query query = entityManager.createQuery(sql);
            query.setParameter("instanciaActual", instanciaId);
            query.setParameter("instanciaOld", jProceso.getInstancia());
            query.setParameter("fechaActual", fechaActual);
            query.setParameter("fechaOld", jProceso.getFecha());
            final int update = query.executeUpdate();
            res = (update == 1);
        }

        return res;
    }

    @Override
    public void borrar(Long codigo) {
        final JProceso jProceso = entityManager.find(JProceso.class, codigo);
        if (jProceso != null) {
            entityManager.remove(jProceso);
            entityManager.flush();
        }
    }

    @Override
    public void guardar(ProcesoDTO proceso) {
        JProceso jproceso = null;
        if (proceso.getCodigo() == null) {
            jproceso = JProceso.fromModel(proceso);
            entityManager.persist(jproceso);
        } else {
            jproceso = entityManager.find(JProceso.class, proceso.getCodigo());
            jproceso.merge(proceso);
            this.update(jproceso);
        }
    }

    @Override
    public Integer listarTotal(ProcesoFiltro filtro) {
        final Query query = getQueryList(true, filtro);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public List<ProcesoGridDTO> listar(ProcesoFiltro filtro) {
        final Query query = getQueryList(false, filtro);
        final List<ProcesoGridDTO> listgrid = new ArrayList<>();
        final List<Object[]> resultados = query.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            ProcesoGridDTO tipovalgrid = null;
            for (int i = 0; i < resultados.size(); i++) {
                tipovalgrid = ProcesoGridDTO.cast(resultados.get(i));
                listgrid.add(tipovalgrid);
            }
        }
        return listgrid;
    }

    @Override
    public ProcesoDTO obtenerProcesoPorCodigo(Long codigo) {
        ProcesoDTO proceso = null;
        final JProceso jpeticion = this.findById(codigo);
        if (jpeticion != null) {
            proceso = jpeticion.toModel();
        }
        return proceso;
    }

    @Override
    public List<ProcesoGridDTO> listar(String idioma, String tipo) {
        final ProcesoFiltro filtro = new ProcesoFiltro();
        filtro.setIdioma(idioma);
        filtro.setPaginaFirst(0);
        filtro.setPaginaTamanyo(100);
        return listar(filtro);
    }

    @Override
    public ProcesoDTO obtenerProcesoPorIdentificador(String identificador) {
        ProcesoDTO proceso = null;
        final String sql = "SELECT P FROM JProceso P WHERE P.identificadorProceso = :identificador";
        final Query query = entityManager.createQuery(sql);
        query.setParameter("identificador", identificador);
        final List resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            final JProceso jpeticion = (JProceso) resultList.get(0);
            if (jpeticion != null) {
                proceso = jpeticion.toModel();
            }
        }
        return proceso;
    }

    private String getSql(final boolean total, final ProcesoFiltro filtro) {
        StringBuilder sql;
        if (total) {
            sql = new StringBuilder("SELECT COUNT(P) ");
        } else {
            sql = new StringBuilder("SELECT P.codigo, P.descripcion, P.identificadorProceso, P.cron, P.activo, P.parametrosInvocacion ");

        }
        sql.append("FROM JProceso P ");

        sql.append("WHERE 1=1 ");

        if (filtro.isRellenoCodigo()) {// i
            sql.append(" and P.codigo = :codigo ");
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

    private Query getQueryList(final boolean total, final ProcesoFiltro filtro) {
        final String sql = getSql(total, filtro);
        final Query query = entityManager.createQuery(sql);
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }
        return query;
    }
}
