package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.AlertaConverter;
import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.model.JAlerta;
import es.caib.rolsac2.persistence.model.JAlertaUsuario;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.model.AlertaDTO;
import es.caib.rolsac2.service.model.AlertaGridDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.AlertaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(AlertaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AlertaRepositoryBean extends AbstractCrudRepository<JAlerta, Long> implements AlertaRepository {

    protected AlertaRepositoryBean() {
        super(JAlerta.class);
    }

    @Inject
    private AlertaConverter converter;

    @Inject
    private EntidadConverter entidadConverter;

    @Override
    public Optional<JAlerta> findById(String id) {
        TypedQuery<JAlerta> query = entityManager.createNamedQuery(JAlerta.FIND_BY_ID, JAlerta.class);
        query.setParameter("id", id);
        List<JAlerta> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<AlertaGridDTO> findAlertaUsuarioPageByFiltro(AlertaFiltro filtro) {
        Query query = getQueryAlertaUsuario(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jAlerta = query.getResultList();
        List<AlertaGridDTO> alertaGridDTOS = new ArrayList<>();

        if (jAlerta != null) {
            for (Object[] alerta : jAlerta) {
                AlertaGridDTO alertaGridDTO = new AlertaGridDTO();
                alertaGridDTO.setCodigo((Long) alerta[0]);
                alertaGridDTO.setEntidad(entidadConverter.createDTO((JEntidad) alerta[1]));
                alertaGridDTO.setTipo((String) alerta[2]);
                alertaGridDTO.setAmbito((String) alerta[3]);
                if (alerta[4] != null) {
                    Literal desc = new Literal();
                    desc.add(new Traduccion(filtro.getIdioma(), (String) alerta[4]));
                    alertaGridDTO.setDescripcion(desc);
                }
                alertaGridDTO.setFechaIni((Date) alerta[5]);
                alertaGridDTO.setFechaFin((Date) alerta[6]);
                alertaGridDTO.setFecha((Date) alerta[7]);
                alertaGridDTOS.add(alertaGridDTO);
            }
        }
        return alertaGridDTOS;
    }

    @Override
    public List<AlertaGridDTO> findPageByFiltro(AlertaFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jAlerta = query.getResultList();
        List<AlertaGridDTO> alertaGridDTOS = new ArrayList<>();

        if (jAlerta != null) {
            for (Object[] alerta : jAlerta) {
                AlertaGridDTO alertaGridDTO = new AlertaGridDTO();
                alertaGridDTO.setCodigo((Long) alerta[0]);
                alertaGridDTO.setEntidad(entidadConverter.createDTO((JEntidad) alerta[1]));
                alertaGridDTO.setTipo((String) alerta[2]);
                alertaGridDTO.setAmbito((String) alerta[3]);
                if (alerta[4] != null) {
                    Literal desc = new Literal();
                    desc.add(new Traduccion(filtro.getIdioma(), (String) alerta[4]));
                    alertaGridDTO.setDescripcion(desc);
                }
                alertaGridDTO.setFechaIni((Date) alerta[5]);
                alertaGridDTO.setFechaFin((Date) alerta[6]);
                alertaGridDTOS.add(alertaGridDTO);
            }
        }
        return alertaGridDTOS;
    }

    /**
     * Borra todas las alertas usuario que hubiesen anteriormente.
     *
     * @param idAlerta
     */
    @Override
    public void borrarAlertasUsuariosByIdAlerta(Long idAlerta) {
        StringBuilder sql = new StringBuilder("DELETE FROM JAlertaUsuario jau WHERE jau.alerta.codigo = :idAlerta");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idAlerta", idAlerta);
        query.executeUpdate();
    }

    private Query getQueryAlertaUsuario(boolean isTotal, AlertaFiltro filtro) {
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(jau) FROM JAlertaUsuario jau LEFT OUTER JOIN jau.alerta j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma " + " where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.entidad, j.tipo, j.ambito, t.descripcion, j.fechaIni, j.fechaFin, jau.fecha FROM JAlertaUsuario jau LEFT OUTER JOIN jau.alerta j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER (t.descripcion) LIKE :filtro OR cast(j.codigo as string) like :filtro " + " OR LOWER (j.ambito) LIKE :filtro OR LOWER (j.tipo) LIKE :filtro ) ");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo =:idEntidad ");
        }

        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo =:codigo ");
        }

        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER (j.identificador) LIKE :identificador ");
        }

        if (filtro.isRellenoUsuario()) {
            sql.append(" and LOWER (jau.usuario) LIKE :usuario ");
        }


        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }
        if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }
        if (filtro.isRellenoUsuario()) {
            query.setParameter("usuario", filtro.getUsuario());
        }

        return query;
    }


    private Query getQuery(boolean isTotal, AlertaFiltro filtro, boolean isRest) {
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JAlerta j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma " + " where 1 = 1 ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j FROM JAlerta j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.entidad, j.tipo, j.ambito, t.descripcion, j.fechaIni, j.fechaFin FROM JAlerta j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER (t.descripcion) LIKE :filtro OR cast(j.codigo as string) like :filtro " + " OR LOWER (j.ambito) LIKE :filtro OR LOWER (j.tipo) LIKE :filtro ) ");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo =:idEntidad ");
        }

        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo =:codigo ");
        }

        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER (j.identificador) LIKE :identificador ");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }
        if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }

        return query;
    }

    private String getOrden(String order) {
        return "j." + order;
    }

    @Override
    public List<AlertaDTO> getAlertas(String usuario, List<String> perfiles, List<Long> uas) {
        List<AlertaDTO> alertas = new ArrayList<>();
        StringBuilder sql = null;
        //AMBITO TODOS
        sql = new StringBuilder(" SELECT J FROM JAlerta J WHERE J.ambito = 'TOD' AND J.fechaIni < sysdate() AND (j.fechaFin is null OR j.fechaFin > sysdate()) AND J.codigo not in (SELECT alerusu.alerta from JAlertaUsuario alerusu where alerusu.usuario like :usuario ) ");
        sql.append(" order by J.fechaIni desc ");
        Query query = entityManager.createQuery(sql.toString(), JAlerta.class);
        query.setParameter("usuario", usuario);
        List<JAlerta> jalertas = query.getResultList();
        if (jalertas != null) {
            for (JAlerta jalerta : jalertas) {
                AlertaDTO alerta = converter.createDTO(jalerta);
                alertas.add(alerta);
            }
        }

        //AMBITO PERFIL
        sql = new StringBuilder(" SELECT J FROM JAlerta J WHERE J.ambito = 'PER' AND J.fechaIni < sysdate() AND (j.fechaFin is null OR j.fechaFin > sysdate()) AND J.perfil IN :perfiles AND J.codigo not in (SELECT alerusu.alerta from JAlertaUsuario alerusu where alerusu.usuario like :usuario ) ");
        sql.append(" order by J.fechaIni desc ");
        query = entityManager.createQuery(sql.toString(), JAlerta.class);
        query.setParameter("perfiles", perfiles);
        query.setParameter("usuario", usuario);
        jalertas = query.getResultList();
        if (jalertas != null) {
            for (JAlerta jalerta : jalertas) {
                AlertaDTO alerta = converter.createDTO(jalerta);
                alertas.add(alerta);
            }
        }

        //AMBITO UNIDAD ADMINISTRATIVA
        sql = new StringBuilder(" SELECT J FROM JAlerta J WHERE J.ambito = 'UNA' AND J.fechaIni < sysdate() AND (j.fechaFin is null OR j.fechaFin > sysdate()) AND J.codigo not in (SELECT alerusu.alerta from JAlertaUsuario alerusu where alerusu.usuario like :usuario ) ");
        if (uas != null && uas.size() > 0) {
            sql.append(" AND j.unidadAdministrativa.codigo IN :uas  ");
        }
        sql.append(" order by J.fechaIni desc ");
        query = entityManager.createQuery(sql.toString(), JAlerta.class);
        query.setParameter("usuario", usuario);
        if (uas != null && uas.size() > 0) {
            query.setParameter("uas", uas);
        }
        jalertas = query.getResultList();
        if (jalertas != null) {
            for (JAlerta jalerta : jalertas) {
                AlertaDTO alerta = converter.createDTO(jalerta);
                alertas.add(alerta);
            }
        }

        alertas.sort((o1, o2) -> o2.getFechaIni().compareTo(o1.getFechaIni()) < 0 ? -1 : 1);
        return alertas;
    }

    @Override
    public long countAlertaUsuarioByFiltro(AlertaFiltro filtro) {
        return (long) getQueryAlertaUsuario(true, filtro).getSingleResult();
    }

    @Override
    public void marcarAlertaLeida(Long codigo, String identificadorUsuario) {
        JAlerta jalerta = entityManager.find(JAlerta.class, codigo);
        JAlertaUsuario jAlertaUsuario = new JAlertaUsuario();
        jAlertaUsuario.setAlerta(jalerta);
        jAlertaUsuario.setUsuario(identificadorUsuario);
        jAlertaUsuario.setFecha(new Date());
        entityManager.persist(jAlertaUsuario);
    }

    @Override
    public long countByFiltro(AlertaFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }

    @Override
    public List<AlertaDTO> findPagedByFiltroRest(AlertaFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JAlerta> jAlertas = query.getResultList();
        List<AlertaDTO> alertas = new ArrayList<>();
        if (jAlertas != null) {
            for (JAlerta jAlerta : jAlertas) {
                AlertaDTO alerta = converter.createDTO(jAlerta);
                alertas.add(alerta);
            }
        }
        return alertas;
    }

}
