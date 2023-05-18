package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JIndexacionSIA;
import es.caib.rolsac2.service.model.IndexacionSIADTO;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;

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
public class IndexacionSIARepositoryBean extends AbstractCrudRepository<JIndexacionSIA, Long> implements IndexacionSIARepository {

    protected IndexacionSIARepositoryBean() {
        super(JIndexacionSIA.class);
    }

    @Override
    public List<IndexacionSIADTO> findPagedByFiltro(ProcesoSIAFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jdatos = query.getResultList();
        List<IndexacionSIADTO> datosDTO = new ArrayList<>();
        if (jdatos != null) {
            for (Object[] jdato : jdatos) {
                IndexacionSIADTO indexacionDTO = new IndexacionSIADTO();
                indexacionDTO.setCodigo((Long) jdato[0]);
                indexacionDTO.setTipo((String) jdato[1]);
                indexacionDTO.setCodElemento((Long) jdato[2]);
                indexacionDTO.setFechaCreacion((Date) jdato[3]);
                indexacionDTO.setFechaIntentoIndexacion((Date) jdato[4]);
                indexacionDTO.setMensajeError((String) jdato[5]);
                indexacionDTO.setEstado((Integer) jdato[6]);
                indexacionDTO.setExiste((Integer) jdato[7]);
                datosDTO.add(indexacionDTO);
            }
        }
        return datosDTO;
    }

    @Override
    public long countByFiltro(ProcesoSIAFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }


    private Query getQuery(boolean isTotal, ProcesoSIAFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JIndexacionSIA j where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.tipo, j.codElemento, j.fechaCreacion, j.fechaIntentoIndexacion, j.mensajeError, j.estado, j.existe FROM JIndexacionSIA j where 1 = 1 ");
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
    public Optional<JIndexacionSIA> findById(String id) {
        TypedQuery<JIndexacionSIA> query = entityManager.createNamedQuery(JIndexacionSIA.FIND_BY_ID, JIndexacionSIA.class);
        query.setParameter("id", id);
        List<JIndexacionSIA> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public boolean existeIndexacion(Long idElemento, String tipo, Long idEntidad) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JIndexacionSIA j where j.entidad.codigo = :idEntidad and j.tipo like :tipo and j.codElemento =: idElemento ");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("tipo", tipo);
        query.setParameter("idElemento", idElemento);
        query.setParameter("idEntidad", idEntidad);
        Long total = (Long) query.getSingleResult();
        return total > 0;
    }

    @Override
    public void guardarIndexar(Long codElemento, String tipo, Long idEntidad, int estado, int existe) {
        if (!existeIndexacion(codElemento, tipo, idEntidad)) {
            JIndexacionSIA jIndexacion = new JIndexacionSIA();
            jIndexacion.setCodElemento(codElemento);
            JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
            jIndexacion.setEntidad(jEntidad);
            jIndexacion.setFechaCreacion(new Date());
            jIndexacion.setTipo(tipo);
            jIndexacion.setEstado(estado);
            jIndexacion.setExiste(existe);
            this.create(jIndexacion);
        }
    }

    @Override
    public void actualizarDato(IndexacionSIADTO dato, ResultadoSIA resultadoAccion) {
        JIndexacionSIA jIndexacion = entityManager.find(JIndexacionSIA.class, dato.getCodigo());
        if (resultadoAccion.isNoError() || (resultadoAccion.getMensaje() != null && resultadoAccion.getMensaje().startsWith("0167"))) {
            entityManager.remove(jIndexacion);
        } else {
            jIndexacion.setMensajeError(resultadoAccion.getMensaje());
            jIndexacion.setFechaIntentoIndexacion(new Date());
            entityManager.merge(jIndexacion);
        }
    }

    @Override
    public void deleteByEntidad(Long idEntidad) {

        String sql = "DELETE FROM JIndexacionSIA j where j.entidad.codigo = :entidad ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        int resultado = query.executeUpdate();
        entityManager.flush();
    }

}
