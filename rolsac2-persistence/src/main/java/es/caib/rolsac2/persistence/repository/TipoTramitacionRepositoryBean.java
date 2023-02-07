package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoTramitacionConverter;
import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.TipoTramitacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de tipo de tramitación.
 *
 * @author Indra
 */
@Stateless
@Local(TipoTramitacionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoTramitacionRepositoryBean extends AbstractCrudRepository<JTipoTramitacion, Long>
        implements TipoTramitacionRepository {

    @Inject
    private TipoTramitacionConverter converter;

    protected TipoTramitacionRepositoryBean() {
        super(JTipoTramitacion.class);
    }

    @Override
    public List<TipoTramitacionGridDTO> findPagedByFiltro(TipoTramitacionFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTiposTramitacion = query.getResultList();
        List<TipoTramitacionGridDTO> tipoTramitacion = new ArrayList<>();
        if (jTiposTramitacion != null) {
            for (Object[] jTipoTramitacion : jTiposTramitacion) {
                TipoTramitacionGridDTO tipoTramitacionGridDTO = new TipoTramitacionGridDTO();
                tipoTramitacionGridDTO.setCodigo((Long) jTipoTramitacion[0]);
                tipoTramitacionGridDTO.setTramitPresencial((Boolean) jTipoTramitacion[1]);
                tipoTramitacionGridDTO.setTramitElectronica((Boolean) jTipoTramitacion[2]);
                tipoTramitacionGridDTO.setUrlTramitacion((String) jTipoTramitacion[3]);
                tipoTramitacionGridDTO.setCodPlatTramitacion((String) jTipoTramitacion[4]);
                tipoTramitacionGridDTO.setTramiteId((String) jTipoTramitacion[5]);
                tipoTramitacionGridDTO.setTramiteVersion((Integer) jTipoTramitacion[6]);
                tipoTramitacionGridDTO.setTramiteParametros((String) jTipoTramitacion[7]);

                tipoTramitacion.add(tipoTramitacionGridDTO);
            }
        }
        return tipoTramitacion;
    }

    @Override
    public long countByFiltro(TipoTramitacionFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public List<TipoTramitacionDTO> findAll() {
        TypedQuery<JTipoTramitacion> query =
                entityManager.createQuery("SELECT j FROM JTipoTramitacion j", JTipoTramitacion.class);
        return converter.createTipoTramitacionDTOs(query.getResultList());
    }

    @Override
    public List<TipoTramitacionDTO> findPlantillas(Long idEntidad) {
        TypedQuery<JTipoTramitacion> query = entityManager.createQuery(
                "SELECT j FROM JTipoTramitacion j WHERE j.plantilla = true and j.entidad.codigo = :idEntidad", JTipoTramitacion.class);
        query.setParameter("idEntidad", idEntidad);
        return converter.createTipoTramitacionDTOs(query.getResultList());
    }

    @Override
    public JTipoTramitacion crearActualizar(JTipoTramitacion jTipoTramitacion) {
        if (jTipoTramitacion.getCodigo() == null) {
            entityManager.persist(jTipoTramitacion);
        } else {
            entityManager.merge(jTipoTramitacion);
        }
        return jTipoTramitacion;
    }

    @Override
    public void borrar(Long codigo) {

        JTipoTramitacion jTipoTramitacion = entityManager.find(JTipoTramitacion.class, codigo);
        entityManager.flush();
        entityManager.remove(jTipoTramitacion);

    }


    private Query getQuery(boolean isTotal, TipoTramitacionFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoTramitacion j JOIN j.codPlatTramitacion p where 1 = 1 ");
        } else {
            // sql = new StringBuilder("SELECT j.codigo, j.identificador, j.descripcion FROM JTipoTramitacion j where 1
            // = 1 ");
            sql = new StringBuilder(
                    "SELECT j.codigo, j.tramitPresencial, j.tramitElectronica, j.urlTramitacion, p.identificador, "
                            + " j.tramiteId, j.tramiteVersion, j.tramiteParametros FROM JTipoTramitacion j "
                            + " JOIN j.codPlatTramitacion p where 1 = 1  ");
        }
        // if (filtro.isRellenoIdUA()) {
        // sql.append(" and j.unidadAdministrativa = :ua");
        // }

        if (filtro.isRellenoTexto()) {
            // sql.append(" LEFT JOIN e.descripcion.trads t ");
            sql.append(
                    // " and ( cast(j.id as string) like :filtro OR (t.idioma.identificador = :idioma AND
                    // LOWER(t.literal) LIKE
                    // :filtro) OR LOWER(j.identificador) LIKE :filtro )");
                    " and ( cast(j.codigo as string) like :filtro OR "
                            + " cast(j.tramitPresencial as string) like :filtro OR "
                            + " cast(j.tramitElectronica as string) like :filtro OR "
                            + " cast(j.urlTramitacion as string) like :filtro OR "
                            + " cast(p.identificador as string) like :filtro OR "
                            + " cast(j.tramiteId as string) like :filtro OR "
                            + " cast(j.tramiteVersion as string) like :filtro OR "
                            + " cast(j.tramiteParametros as string) like :filtro)");
        }

        if (filtro.isRellenoTipoPlantilla()) {
            sql.append(" and j.plantilla = :plantilla ");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }


        Query query = entityManager.createQuery(sql.toString());
        // if (filtro.isRellenoIdUA()) {
        // query.setParameter("ua", filtro.getIdUA());
        // }
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto() + "%");
        }
        if (filtro.isRellenoTipoPlantilla()) {
            query.setParameter("plantilla", filtro.getTipoPlantilla());
        }

        return query;
    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Optional<JTipoTramitacion> findById(String id) {
        TypedQuery<JTipoTramitacion> query =
                entityManager.createNamedQuery(JTipoTramitacion.FIND_BY_ID, JTipoTramitacion.class);
        query.setParameter("id", id);
        List<JTipoTramitacion> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
