package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoUnidadAdministrativaObjetivoConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.traduccion.JTipoUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoUnidadAdministrativaFiltro;

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
 * Implementación del repositorio de Personal.
 *
 * @author jsegovia
 */
@Stateless
@Local(TipoUnidadAdministrativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoUnidadAdministrativaRepositoryBean extends AbstractCrudRepository<JTipoUnidadAdministrativa, Long> implements TipoUnidadAdministrativaRepository {

    protected TipoUnidadAdministrativaRepositoryBean() {
        super(JTipoUnidadAdministrativa.class);
    }

    @Inject
    private TipoUnidadAdministrativaObjetivoConverter converter;

    @Override
    public List<TipoUnidadAdministrativaGridDTO> findPagedByFiltro(TipoUnidadAdministrativaFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTipoUnidadAdministrativa = query.getResultList();
        List<TipoUnidadAdministrativaGridDTO> tipoUnidadAdmin = new ArrayList<>();
        if (jTipoUnidadAdministrativa != null) {
            for (Object[] jtipoUnidadAdmin : jTipoUnidadAdministrativa) {
                TipoUnidadAdministrativaGridDTO tipoUnidadAdministrativaGridDTO = new TipoUnidadAdministrativaGridDTO();
                tipoUnidadAdministrativaGridDTO.setCodigo((Long) jtipoUnidadAdmin[0]);
                tipoUnidadAdministrativaGridDTO.setIdentificador((String) jtipoUnidadAdmin[1]);
                tipoUnidadAdministrativaGridDTO.setEntidad(((JEntidad) jtipoUnidadAdmin[2]).getDescripcion(filtro.getIdioma()));
                tipoUnidadAdministrativaGridDTO.setDescripcion(createLiteral((String) jtipoUnidadAdmin[3], filtro.getIdioma()));
                tipoUnidadAdministrativaGridDTO.setCargoMasculino(createLiteral((String) jtipoUnidadAdmin[4], filtro.getIdioma()));
                tipoUnidadAdministrativaGridDTO.setCargoFemenino(createLiteral((String) jtipoUnidadAdmin[5], filtro.getIdioma()));
                tipoUnidadAdministrativaGridDTO.setTratamientoMasculino(createLiteral((String) jtipoUnidadAdmin[6], filtro.getIdioma()));
                tipoUnidadAdministrativaGridDTO.setTratamientoFemenino(createLiteral((String) jtipoUnidadAdmin[7], filtro.getIdioma()));
                tipoUnidadAdmin.add(tipoUnidadAdministrativaGridDTO);
            }
        }
        return tipoUnidadAdmin;
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }

    @Override
    public long countByFiltro(TipoUnidadAdministrativaFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    private Query getQuery(boolean isTotal, TipoUnidadAdministrativaFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j FROM JTipoUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, j.entidad, t.descripcion, t.cargoMasculino, t.cargoFemenino, t.tratamientoMasculino, t.tratamientoFemenino " + " FROM JTipoUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1  ");
        }
        // if (filtro.isRellenoIdUA()) {
        // sql.append(" and j.unidadAdministrativa = :ua");
        // }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) LIKE :filtro OR LOWER(j.identificador) LIKE :filtro " + " OR LOWER(t.descripcion) LIKE :filtro OR LOWER(t.cargoMasculino) LIKE :filtro " + " OR LOWER(t.cargoFemenino) LIKE :filtro OR LOWER(t.tratamientoMasculino) LIKE :filtro " + " OR LOWER(t.tratamientoFemenino) LIKE :filtro " + "OR LOWER(j.entidad.dir3) LIKE :filtro ) ");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.id = :entidad ");
        }
        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo = :codigo ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto() + "%");
        }

        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        if (filtro.isRellenoEntidad()) {
            query.setParameter("entidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }

        return query;
    }

    @Override
    public List<TipoUnidadAdministrativaDTO> findTipo() {
        TypedQuery<JTipoUnidadAdministrativa> query = entityManager.createQuery("SELECT j FROM JTipoUnidadAdministrativa j", JTipoUnidadAdministrativa.class);
        List<JTipoUnidadAdministrativa> jTipoUnidadAdministrativas = query.getResultList();
        List<TipoUnidadAdministrativaDTO> tipoUnidadAdministrativaDTOS = new ArrayList<>();
        if (jTipoUnidadAdministrativas != null) {
            for (JTipoUnidadAdministrativa jtipoUnidadAdministrativa : jTipoUnidadAdministrativas) {
                tipoUnidadAdministrativaDTOS.add(this.converter.createDTO(jtipoUnidadAdministrativa));
            }
        }
        return tipoUnidadAdministrativaDTOS;
    }

    @Override
    public List<JTipoUnidadAdministrativa> findByEntidad(Long idEntidad) {
        String sql = "SELECT j FROM JTipoUnidadAdministrativa j WHERE j.entidad.codigo = :idEntidad";
        Query query = entityManager.createQuery(sql, JTipoUnidadAdministrativa.class);
        query.setParameter("idEntidad", idEntidad);
        return query.getResultList();
    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        switch (order) {
            case "descripcion":
            case "cargoMasculino":
            case "cargoFemenino":
            case "tratamientoMasculino":
            case "tratamientoFemenino":
                return "t." + order;
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
    public Boolean checkIdentificador(String identificador, Long idEntidad) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoUnidadAdministrativa.COUNT_BY_IDENTIFICADOR, Long.class);

        query.setParameter("identificador", identificador.toLowerCase());
        query.setParameter("entidad", idEntidad);
        return query.getSingleResult() > 0;
    }

    @Override
    public List<TipoUnidadAdministrativaDTO> findPagedByFiltroRest(TipoUnidadAdministrativaFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JTipoUnidadAdministrativa> jtipoUnidadAdministrativaes = query.getResultList();
        List<TipoUnidadAdministrativaDTO> tipoUnidadAdministrativaes = new ArrayList<>();
        if (jtipoUnidadAdministrativaes != null) {
            for (JTipoUnidadAdministrativa jtipoUnidadAdministrativa : jtipoUnidadAdministrativaes) {
                TipoUnidadAdministrativaDTO tipoUnidadAdministrativa = converter.createDTO(jtipoUnidadAdministrativa);

                tipoUnidadAdministrativaes.add(tipoUnidadAdministrativa);
            }
        }
        return tipoUnidadAdministrativaes;
    }

    @Override
    public void deleteByEntidad(Long idEntidad) {
        String sql;
        Query query;
        sql = "SELECT t FROM JTipoUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t where j.entidad.codigo = :entidad ";
        query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        List<JTipoUnidadAdministrativaTraduccion> jtraducciones = query.getResultList();
        if (jtraducciones != null) {
            for (JTipoUnidadAdministrativaTraduccion jtraduccion : jtraducciones) {
                entityManager.remove(jtraduccion);
            }
            entityManager.flush();
        }

        sql = "SELECT j FROM JTipoUnidadAdministrativa j where j.entidad.codigo = :entidad ";
        query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        //int totalBorrados = query.executeUpdate();
        List<JTipoUnidadAdministrativa> jtipos = query.getResultList();
        if (jtipos != null) {
            for (JTipoUnidadAdministrativa jtipo : jtipos) {
                entityManager.remove(jtipo);
            }
            entityManager.flush();
        }

        entityManager.flush();
    }

}
