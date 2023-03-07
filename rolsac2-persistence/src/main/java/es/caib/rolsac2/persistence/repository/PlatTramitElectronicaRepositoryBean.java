package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.PlatTramitElectronicaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.PlatTramitElectronicaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de una plataforma de tramitación electrónica
 *
 * @author Indra
 */
@Stateless
@Local(PlatTramitElectronicaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PlatTramitElectronicaRepositoryBean extends AbstractCrudRepository<JPlatTramitElectronica, Long>
        implements PlatTramitElectronicaRepository {

    protected PlatTramitElectronicaRepositoryBean() {
        super(JPlatTramitElectronica.class);
    }

    @Override
    public Optional<JPlatTramitElectronica> findById(String id) {
        TypedQuery<JPlatTramitElectronica> query =
                entityManager.createNamedQuery(JPlatTramitElectronica.FIND_BY_ID, JPlatTramitElectronica.class);
        query.setParameter("codigo", id);
        List<JPlatTramitElectronica> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    private Query getQuery(boolean isTotal, PlatTramitElectronicaFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JPlatTramitElectronica j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma WHERE 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, j.codEntidad, t.descripcion, t.urlAcceso"
                    + " FROM JPlatTramitElectronica j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma WHERE t.idioma=:idioma");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( (cast(j.codigo as string)) like :filtro OR LOWER(j.identificador) LIKE :filtro " +
                    " OR LOWER(j.codEntidad.identificador) LIKE :filtro  ) ");
        }
        if(filtro.isRellenoEntidad()) {
            sql.append(" and j.codEntidad.codigo =:idEntidad");
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
            query.setParameter("codigo", "%" + filtro.getCodigo());
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }
        if(filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }


        return query;
    }

    @Override
    public List<JPlatTramitElectronica> findAll(Long idEntidad) {
        TypedQuery<JPlatTramitElectronica> query =
                entityManager.createQuery("select p from JPlatTramitElectronica p where p.codEntidad.codigo = :idEntidad", JPlatTramitElectronica.class);
        query.setParameter("idEntidad", idEntidad);
        List<JPlatTramitElectronica> result = query.getResultList();
        return result;
    }

    @Override
    public List<PlatTramitElectronicaGridDTO> findPagedByFiltro(PlatTramitElectronicaFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jPlatTramitElectronicas = query.getResultList();
        List<PlatTramitElectronicaGridDTO> platTramitElectronica = new ArrayList<>();
        if (jPlatTramitElectronicas != null) {
            for (Object[] jPlatTramitElectronica : jPlatTramitElectronicas) {
                PlatTramitElectronicaGridDTO platTramitElectronicaGridDTO = new PlatTramitElectronicaGridDTO();
                platTramitElectronicaGridDTO.setCodigo((Long) jPlatTramitElectronica[0]);
                platTramitElectronicaGridDTO.setIdentificador((String) jPlatTramitElectronica[1]);
                platTramitElectronicaGridDTO.setCodEntidad(((JEntidad) jPlatTramitElectronica[2]).getIdentificador());
                //platTramitElectronicaGridDTO.setCodEntidad(((JEntidad) jTramite[2]).getDescripcion(filtro.getIdioma()));
                Literal descripcionLiteral = new Literal();
                descripcionLiteral.add(new Traduccion(filtro.getIdioma(), (String) jPlatTramitElectronica[3]));
                platTramitElectronicaGridDTO.setDescripcion(descripcionLiteral);


                Literal urlLiteral = new Literal();
                urlLiteral.add(new Traduccion(filtro.getIdioma(), (String) jPlatTramitElectronica[4]));
                platTramitElectronicaGridDTO.setUrlAcceso(urlLiteral);

                platTramitElectronica.add(platTramitElectronicaGridDTO);

            }
        }
        return platTramitElectronica;
    }

    @Override
    public Long countByFiltro(PlatTramitElectronicaFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public Boolean checkIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JPlatTramitElectronica.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        if ("descripcion".equals(order) || "urlAcceso".equals(order)) {
            return "t." + order;
        }
        return "j." + order;
    }
}
