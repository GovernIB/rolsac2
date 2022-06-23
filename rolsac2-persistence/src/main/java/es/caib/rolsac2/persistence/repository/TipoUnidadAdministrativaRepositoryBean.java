package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoUnidadAdministrativaFiltro;

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
 * Implementación del repositorio de Personal.
 *
 * @author jsegovia
 */
@Stateless
@Local(TipoUnidadAdministrativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoUnidadAdministrativaRepositoryBean extends AbstractCrudRepository<JTipoUnidadAdministrativa, Long>
        implements TipoUnidadAdministrativaRepository {

    protected TipoUnidadAdministrativaRepositoryBean() {
        super(JTipoUnidadAdministrativa.class);
    }

    @Override
    public List<TipoUnidadAdministrativaGridDTO> findPagedByFiltro(TipoUnidadAdministrativaFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTipoUnidadAdministrativa = query.getResultList();
        List<TipoUnidadAdministrativaGridDTO> tipoNormativa = new ArrayList<>();
        if (jTipoUnidadAdministrativa != null) {
            for (Object[] jtipoNom : jTipoUnidadAdministrativa) {
                TipoUnidadAdministrativaGridDTO tipoNormativaGridDTO = new TipoUnidadAdministrativaGridDTO();
                tipoNormativaGridDTO.setId((Long) jtipoNom[0]);
                tipoNormativaGridDTO.setIdentificador((String) jtipoNom[1]);
                tipoNormativaGridDTO.setEntidad(((JEntidad)jtipoNom[2]).getDir3());
                tipoNormativaGridDTO.setDescripcion(createLiteral((String) jtipoNom[3], filtro.getIdioma()));
                tipoNormativaGridDTO.setCargoMasculino(createLiteral((String) jtipoNom[4], filtro.getIdioma()));
                tipoNormativaGridDTO.setCargoFemenino(createLiteral((String) jtipoNom[5], filtro.getIdioma()));
                tipoNormativaGridDTO.setTratamientoMasculino(createLiteral((String) jtipoNom[6], filtro.getIdioma()));
                tipoNormativaGridDTO.setTratamientoFemenino(createLiteral((String) jtipoNom[7], filtro.getIdioma()));
                tipoNormativa.add(tipoNormativaGridDTO);
            }
        }
        return tipoNormativa;
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }

    @Override
    public long countByFiltro(TipoUnidadAdministrativaFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    private Query getQuery(boolean isTotal, TipoUnidadAdministrativaFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoUnidadAdministrativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.id, j.identificador, j.entidad, t.descripcion, t.cargoMasculino, t.cargoFemenino, t.tratamientoMasculino, t.tratamientoFemenino" +
                    " FROM JTipoUnidadAdministrativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1  ");
        }
//        if (filtro.isRellenoIdUA()) {
//            sql.append(" and j.unidadAdministrativa = :ua");
//        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro )");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());
//        if (filtro.isRellenoIdUA()) {
//            query.setParameter("ua", filtro.getIdUA());
//        }
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto() + "%");
        }

        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        switch (order) {
            case "descripcion":
            case "cargoMasculino":
            case "cargoFemenino":
            case "tratamientoMasculino":
            case "tratamientoFemenino": return "t." + order;
            default:
                return "j." + order;
        }
    }

    @Override
    public Optional<JTipoUnidadAdministrativa> findById(String id) {
        TypedQuery<JTipoUnidadAdministrativa> query = entityManager.createNamedQuery(JTipoUnidadAdministrativa.FIND_BY_ID, JTipoUnidadAdministrativa.class);
        query.setParameter("id", id);
        List<JTipoUnidadAdministrativa> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public Boolean checkIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoUnidadAdministrativa.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        return query.getSingleResult().longValue() == 1L;
    }

}
