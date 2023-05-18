package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JIndexacion;
import es.caib.rolsac2.service.model.IndexacionDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de tipo de materia SIA.
 *
 * @author Indra
 */
@Stateless
@Local(IndexacionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class IndexacionRepositoryBean extends AbstractCrudRepository<JIndexacion, Long> implements IndexacionRepository {

    protected IndexacionRepositoryBean() {
        super(JIndexacion.class);
    }

    @Override
    public List<IndexacionDTO> findPagedByFiltro(ProcesoSolrFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jdatos = query.getResultList();
        List<IndexacionDTO> datosDTO = new ArrayList<>();
        if (jdatos != null) {
            for (Object[] jdato : jdatos) {
                IndexacionDTO indexacionDTO = new IndexacionDTO();
                indexacionDTO.setCodigo((Long) jdato[0]);
                indexacionDTO.setTipo((String) jdato[1]);
                indexacionDTO.setCodElemento((Long) jdato[2]);
                indexacionDTO.setFechaCreacion((Date) jdato[3]);
                indexacionDTO.setFechaIntentoIndexacion((Date) jdato[4]);
                indexacionDTO.setAccion((Integer) jdato[5]);
                indexacionDTO.setMensajeError((String) jdato[6]);

                datosDTO.add(indexacionDTO);
            }
        }
        return datosDTO;
    }

    @Override
    public long countByFiltro(ProcesoSolrFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }


    private Query getQuery(boolean isTotal, ProcesoSolrFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JIndexacion j where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.tipo, j.codElemento, j.fechaCreacion, j.fechaIntentoIndexacion, j.accion, j.mensajeError FROM JIndexacion j where 1 = 1 ");
        }


        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo = :entidad");
        }
        if (filtro.isRellenoTipo()) {
            sql.append(" and j.tipo like :tipo");
        }
        if (filtro.isRellenoCodElemento()) {
            sql.append(" and j.codElemento = :codElemento");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTipo()) {
            query.setParameter("tipo", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoEntidad()) {
            query.setParameter("entidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoCodElemento()) {
            query.setParameter("codElemento", filtro.getCodElemento());
        }


        return query;
    }

    private String getOrden(String order) {
        if ("descripcion".equalsIgnoreCase(order)) {
            return "t." + order;
        } else {
            return "j." + order;
        }
    }

    @Override
    public Optional<JIndexacion> findById(String id) {
        TypedQuery<JIndexacion> query = entityManager.createNamedQuery(JIndexacion.FIND_BY_ID, JIndexacion.class);
        query.setParameter("id", id);
        List<JIndexacion> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public boolean existeIndexacion(Long idElemento, String tipo, Long idEntidad) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JIndexacion j where j.entidad.codigo = :idEntidad and j.tipo like :tipo and j.codElemento =: idElemento ");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idElemento", idElemento);
        query.setParameter("tipo", tipo);
        query.setParameter("idEntidad", idEntidad);
        Long total = (Long) query.getSingleResult();
        return total > 0;
    }

    @Override
    public void guardarIndexar(Long codElemento, TypeIndexacion tipo, Long idEntidad, int accion) {
        if (!existeIndexacion(codElemento, tipo.toString(), idEntidad)) {
            JIndexacion jIndexacion = new JIndexacion();
            jIndexacion.setTipo(tipo.toString());
            jIndexacion.setCodElemento(codElemento);
            JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
            jIndexacion.setEntidad(jEntidad);
            jIndexacion.setFechaCreacion(new Date());
            jIndexacion.setAccion(accion);
            this.create(jIndexacion);
        }
    }

    @Override
    public void actualizarDato(IndexacionDTO dato, ResultadoAccion resultadoAccion) {
        JIndexacion jIndexacion = entityManager.find(JIndexacion.class, dato.getCodigo());
        if (resultadoAccion.isCorrecto()) {
            entityManager.remove(jIndexacion);
        } else {
            jIndexacion.setMensajeError(resultadoAccion.getMensaje());
            jIndexacion.setFechaIntentoIndexacion(new Date());
            entityManager.merge(jIndexacion);
        }
    }

    @Override
    public void deleteByEntidad(Long idEntidad) {

        String sql = "DELETE FROM JIndexacion j where j.entidad.codigo = :entidad ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        int resultado = query.executeUpdate();
        entityManager.flush();
    }

}
