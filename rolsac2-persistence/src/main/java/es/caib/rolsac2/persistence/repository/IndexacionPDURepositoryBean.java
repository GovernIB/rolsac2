package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.pdu.api.model.ResultadoPdu;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JIndexacionPdu;
import es.caib.rolsac2.persistence.model.JIndexacionSIA;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.service.model.IndexacionPDUDto;
import es.caib.rolsac2.service.model.filtro.ProcesoPduFiltro;
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
 * Implementación del repositorio de indexación de peticiones a PDU (Pasarela Digital Única).
 *
 * @author Indra
 */
@Stateless
@Local(IndexacionPDURepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class IndexacionPDURepositoryBean extends AbstractCrudRepository<JIndexacionPdu, Long> implements IndexacionPDURepository {

    protected IndexacionPDURepositoryBean() {
        super(JIndexacionPdu.class);
    }

    @Override
    public List<IndexacionPDUDto> findPagedByFiltro(ProcesoPduFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jdatos = query.getResultList();
        List<IndexacionPDUDto> datosDTO = new ArrayList<>();
        if (jdatos != null) {
            for (Object[] jdato : jdatos) {
                IndexacionPDUDto indexacionDTO = new IndexacionPDUDto();
                indexacionDTO.setCodigo((Long) jdato[0]);
                String tipo = (String) jdato[1];
                String tipoIndexacion = null;
                if ("P".equals(tipo)) {
                    tipoIndexacion = TypeIndexacion.PROCEDIMIENTO.toString();
                } else if ("S".equals(tipo)) {
                    tipoIndexacion = TypeIndexacion.SERVICIO.toString();
                }
                indexacionDTO.setTipo(tipoIndexacion);
                indexacionDTO.setCodElemento((Long) jdato[2]);
                indexacionDTO.setFechaCreacion((Date) jdato[3]);
                indexacionDTO.setFechaIntentoIndexacion((Date) jdato[4]);
                indexacionDTO.setMensajeError((String) jdato[5]);
                indexacionDTO.setAccion((Integer) jdato[6]);
//                indexacionDTO.setExiste((Integer) jdato[7]);
                datosDTO.add(indexacionDTO);
            }
        }
        return datosDTO;
    }

    @Override
    public long countByFiltro(ProcesoPduFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }


    private Query getQuery(boolean isTotal, ProcesoPduFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JIndexacionPdu j LEFT JOIN j.procedimiento p LEFT OUTER JOIN p.procedimientoWF WF ON wf.workflow = 0 where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.procedimiento.tipo, j.procedimiento.codigo, j.fechaCreacion, j.fechaIntentoIndexacion, " +
                    "j.mensajeError, j.accion FROM JIndexacionPdu j LEFT JOIN j.procedimiento p LEFT OUTER JOIN p.procedimientoWF WF ON wf.workflow = 0 where 1 = 1 ");
        }

        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo = :entidad");
        }
        if (filtro.isRellenoTipo()) {
            sql.append(" and j.procedimiento.tipo like :tipo");
        }
        if (filtro.isRellenoCodElemento()) {
            sql.append(" and j.procedimiento.codigo = :codElemento");
        }

        if (filtro.isRellenoIntegrarPdu()) {
            sql.append(" and wf.integrarPdu = :integrarPdu");
        }

        if (filtro.isRellenoEstadoProcedimiento()) {
            sql.append(" and wf.estado = :estado");
        }


        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoEntidad()) {
            query.setParameter("entidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoTipo()) {
            query.setParameter("tipo", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoCodElemento()) {
            query.setParameter("codElemento", filtro.getCodElemento());
        }
        if (filtro.isRellenoIntegrarPdu()) {
            query.setParameter("integrarPdu", filtro.getIntegrarPdu());
        }
        if (filtro.isRellenoEstadoProcedimiento()) {
            query.setParameter("estado", filtro.getEstadoProcedimiento().toString());
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
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JIndexacionPdu j where j.entidad.codigo = :idEntidad and j.procedimiento.codigo =: idElemento ");

        Query query = entityManager.createQuery(sql.toString());
//        query.setParameter("tipo", tipo);
        query.setParameter("idElemento", idElemento);
        query.setParameter("idEntidad", idEntidad);
        Long total = (Long) query.getSingleResult();
        return total > 0;
    }

    @Override
    public void guardarIndexar(Long codElemento, String tipo, Long idEntidad, int accion) {
        if (existeIndexacion(codElemento, tipo, idEntidad)) {
            String sql = "DELETE FROM JIndexacionPdu j where j.entidad.codigo = :entidad and j.procedimiento.codigo = :codElemento";
            Query query = entityManager.createQuery(sql);
            query.setParameter("entidad", idEntidad);
            query.setParameter("codElemento", codElemento);
            query.executeUpdate();
        }

        JIndexacionPdu jIndexacion = new JIndexacionPdu();
        if (codElemento != null) {
            JProcedimiento procedimiento = new JProcedimiento();
            procedimiento.setCodigo(codElemento);
            jIndexacion.setProcedimiento(procedimiento);
        }
        JEntidad jEntidad = entityManager.getReference(JEntidad.class, idEntidad);
        jIndexacion.setEntidad(jEntidad);
        jIndexacion.setFechaCreacion(new Date());
        jIndexacion.setAccion(accion);
        this.create(jIndexacion);

    }

    @Override
    public void actualizarDato(IndexacionPDUDto dato, ResultadoPdu resultadoAccion) {
        JIndexacionPdu jIndexacion = entityManager.find(JIndexacionPdu.class, dato.getCodigo());
        if(jIndexacion == null){
            return;
        }
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

        String sql = "DELETE FROM JIndexacionPdu j where j.entidad.codigo = :entidad ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        query.executeUpdate();
        entityManager.flush();
    }

    @Override
    public void deleteByCodElemento(Long codElemento) {
        String sql = "DELETE FROM JIndexacionPdu j where j.procedimiento.codigo = :codElemento ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("codElemento", codElemento);
        query.executeUpdate();
        entityManager.flush();
    }


}
