package es.caib.rolsac2.persistence.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import es.caib.rolsac2.persistence.converter.TipoBoletinConverter;
import es.caib.rolsac2.persistence.model.JTipoBoletin;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoBoletinGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoBoletinFiltro;

@Stateless
@Local(TipoBoletinRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoBoletinRepositoryBean extends AbstractCrudRepository<JTipoBoletin, Long> implements TipoBoletinRepository {

    protected TipoBoletinRepositoryBean() {
        super(JTipoBoletin.class);
    }

    @Inject
    private TipoBoletinConverter converter;

    @Override
    public List<TipoBoletinGridDTO> findPagedByFiltro(TipoBoletinFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipoBoletines = query.getResultList();
        List<TipoBoletinGridDTO> tipoBoletines = new ArrayList<>();
        if (jtipoBoletines != null) {
            for (Object[] jtipoBoletin : jtipoBoletines) {
                TipoBoletinGridDTO tipoBoletinGrid = new TipoBoletinGridDTO();
                tipoBoletinGrid.setCodigo((Long) jtipoBoletin[0]);
                tipoBoletinGrid.setIdentificador((String) jtipoBoletin[1]);
                tipoBoletinGrid.setNombre((String) jtipoBoletin[2]);
                tipoBoletinGrid.setUrl((String) jtipoBoletin[3]);

                tipoBoletines.add(tipoBoletinGrid);
            }
        }
        return tipoBoletines;
    }

    @Override
    public long countByFiltro(TipoBoletinFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    @Override
    public boolean checkIdentificadorTipoBoletin(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoBoletin.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }


    private Query getQuery(boolean isTotal, TipoBoletinFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoBoletin j where 1 = 1 ");
        } else if (isRest) {
        	sql = new StringBuilder("SELECT j FROM JTipoBoletin j where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, j.nombre, j.url FROM JTipoBoletin j where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro OR LOWER(j.nombre) LIKE :filtro OR LOWER(j.url) LIKE :filtro )");
        }
        if (filtro.isRellenoCodigo()) {
        	sql.append(" and j.codigo = :codigo ");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoCodigo()) {
        	query.setParameter("codigo", filtro.getCodigo());
        }
        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Optional<JTipoBoletin> findById(String id) {
        TypedQuery<JTipoBoletin> query = entityManager.createNamedQuery(JTipoBoletin.FIND_BY_ID, JTipoBoletin.class);
        query.setParameter("id", id);
        List<JTipoBoletin> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<TipoBoletinDTO> findAll(){
        TypedQuery<JTipoBoletin> query =
                entityManager.createQuery("SELECT j FROM JTipoBoletin  j", JTipoBoletin.class);
        List<JTipoBoletin> jTipoBoletines = query.getResultList();
        List<TipoBoletinDTO> tipoBoletinDTOS = new ArrayList<>();
        if (jTipoBoletines != null) {
            for (JTipoBoletin jTipoBoletin : jTipoBoletines) {
                tipoBoletinDTOS.add(this.converter.createDTO(jTipoBoletin));
            }
        }
        return tipoBoletinDTOS;
    }

    @Override
    public List<TipoBoletinDTO> findPagedByFiltroRest(TipoBoletinFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JTipoBoletin> jtipoBoletines = query.getResultList();
        List<TipoBoletinDTO> tipoBoletines = new ArrayList<>();
        if (jtipoBoletines != null) {
            for (JTipoBoletin jtipoBoletin : jtipoBoletines) {
                TipoBoletinDTO tipoBoletin = converter.createDTO(jtipoBoletin);

                tipoBoletines.add(tipoBoletin);
            }
        }
        return tipoBoletines;
    }
}
