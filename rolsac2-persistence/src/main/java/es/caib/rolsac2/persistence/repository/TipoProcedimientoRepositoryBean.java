package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoProcedimientoConverter;
import es.caib.rolsac2.persistence.model.JTipoProcedimiento;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoProcedimientoDTO;
import es.caib.rolsac2.service.model.TipoProcedimientoGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoProcedimientoFiltro;

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

@Stateless
@Local(TipoSexoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoProcedimientoRepositoryBean extends AbstractCrudRepository<JTipoProcedimiento, Long> implements TipoProcedimientoRepository {

    protected TipoProcedimientoRepositoryBean() {
        super(JTipoProcedimiento.class);
    }

    @Inject
    private TipoProcedimientoConverter converter;

    @Override
    public List<TipoProcedimientoGridDTO> findPagedByFiltro(TipoProcedimientoFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipos = query.getResultList();
        List<TipoProcedimientoGridDTO> tipos = new ArrayList<>();
        if (jtipos != null) {
            for (Object[] jtipoSexo : jtipos) {
                TipoProcedimientoGridDTO tipoGrid = new TipoProcedimientoGridDTO();
                tipoGrid.setCodigo((Long) jtipoSexo[0]);
                tipoGrid.setIdentificador((String) jtipoSexo[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoSexo[2]));
                tipoGrid.setDescripcion(literal);
                tipos.add(tipoGrid);
            }
        }
        return tipos;
    }

    @Override
    public long countByFiltro(TipoProcedimientoFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoProcedimiento.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public List<TipoProcedimientoDTO> findAll() {
        TypedQuery<JTipoProcedimiento> query =
                entityManager.createQuery("SELECT j FROM JTipoProcedimiento j", JTipoProcedimiento.class);
        List<JTipoProcedimiento> jTipos = query.getResultList();
        List<TipoProcedimientoDTO> tipos = new ArrayList<>();
        if (jTipos != null) {
            for (JTipoProcedimiento jtipoSexo : jTipos) {
                tipos.add(this.converter.createDTO(jtipoSexo));
            }
        }
        return tipos;
    }

    private Query getQuery(boolean isTotal, TipoProcedimientoFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoProcedimiento j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 and j.entidad.codigo = :idEntidad ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.identificador, t.descripcion FROM JTipoProcedimiento j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where t.idioma = :idioma and j.entidad.codigo = :idEntidad ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro )");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idEntidad", filtro.getIdEntidad());
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }

        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Optional<JTipoProcedimiento> findById(String id) {
        TypedQuery<JTipoProcedimiento> query = entityManager.createNamedQuery(JTipoProcedimiento.FIND_BY_ID, JTipoProcedimiento.class);
        query.setParameter("id", id);
        List<JTipoProcedimiento> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    public List<TipoProcedimientoDTO> findAll(Long codigoEntidad) {

        TypedQuery query =
                entityManager.createQuery("SELECT j FROM JTipoProcedimiento j where j.entidad.codigo = :idEntidad", JTipoProcedimiento.class);
        query.setParameter("idEntidad", codigoEntidad);
        List<JTipoProcedimiento> jtipos = query.getResultList();
        List<TipoProcedimientoDTO> tipos = new ArrayList<>();
        if (jtipos != null) {
            for (JTipoProcedimiento jtipo : jtipos) {
                tipos.add(this.converter.createDTO(jtipo));
            }
        }
        return tipos;
    }
}
