package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JSesion;
import es.caib.rolsac2.service.model.SesionDTO;
import es.caib.rolsac2.service.model.filtro.SesionFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del repositorio de tipo de forma de inicio.
 *
 * @author Indra
 */
@Stateless
@Local(SesionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SesionRepositoryBean extends AbstractCrudRepository<JSesion, Long>
        implements SesionRepository {

    protected SesionRepositoryBean() {
        super(JSesion.class);
    }

    @Override
    public Boolean checkSesion(Long idUsuario) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JSesion.COUNT_BY_ID, Long.class);
        query.setParameter("idUsu", idUsuario);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public List<JSesion> findAllSesiones() {
        TypedQuery<JSesion> query = entityManager.createNamedQuery(JSesion.FIND_ALL, JSesion.class);
        return query.getResultList();
    }

    @Override
    public Long countAllSesiones() {
        TypedQuery<Long> query = entityManager.createNamedQuery(JSesion.COUNT_ALL, Long.class);
        return query.getSingleResult();

    }


    private Query getQuery(boolean isTotal, SesionFiltro filtro) {
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JSesion j where 1 = 1 "
            );
        } else {
            sql = new StringBuilder(
                    "SELECT j.idUsuario, j.fechaUltimaSesion, j.perfil, j.idioma, j.idEntidad, j.idUa "
                            + " FROM JSesion j where 1 = 1 ");
        }
        if (filtro.isRellenoPerfil()) {
            sql.append(" and LOWER(j.perfil) LIKE :perfil ");
        }

        if (filtro.isRellenoIdUsuario()) {
            sql.append(" and j.idUsuario = :idUsuario ");
        }
        if (filtro.isRellenoIdioma()) {
            sql.append(" and LOWER(j.idioma) LIKE :idioma ");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.idEntidad = :idEntidad ");
        }

        if (filtro.isRellenoIdUA()) {
            sql.append(" and j.idUa = :idUa ");
        }


        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoPerfil()) {
            query.setParameter("perfil", "%" + filtro.getPerfil().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", "%" + filtro.getIdioma().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdUsuario()) {
            query.setParameter("idUsuario", filtro.getIdUsuario());
        }

        if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }

        if (filtro.isRellenoIdUA()) {
            query.setParameter("idUa", filtro.getIdUA());
        }

        return query;
    }


    @Override
    public List<SesionDTO> findPageByFiltro(SesionFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jSesion = query.getResultList();
        List<SesionDTO> sesionDTOS = new ArrayList<>();

        if (jSesion != null) {
            for (Object[] sesion : jSesion) {
                SesionDTO sesionDTO = new SesionDTO();
                sesionDTO.setIdUsuario((Long) sesion[0]);
                Timestamp ts = (Timestamp) sesion[1];
                sesionDTO.setFechaUltimaSesion(new java.util.Date(ts.getTime()));
                sesionDTO.setPerfil((String) sesion[2]);
                sesionDTO.setIdioma((String) sesion[3]);
                sesionDTO.setIdEntidad((Long) sesion[4]);
                sesionDTO.setIdUa((Long) sesion[5]);

                sesionDTOS.add(sesionDTO);
            }
        }
        return sesionDTOS;
    }


    @Override
    public long countByFiltro(SesionFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public int deleteAllSesiones() {
        Query query = entityManager.createNamedQuery(JSesion.DELETE_ALL);
        return query.executeUpdate();
    }


}
