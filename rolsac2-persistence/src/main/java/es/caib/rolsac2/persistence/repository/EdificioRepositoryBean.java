package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEdificio;
import es.caib.rolsac2.service.model.EdificioGridDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.EdificioFiltro;

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
 * Implementación del repositorio de tipo de materia SIA.
 *
 * @author areus
 */
@Stateless
@Local(EdificioRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EdificioRepositoryBean extends AbstractCrudRepository<JEdificio, Long> implements EdificioRepository {

    protected EdificioRepositoryBean() {
        super(JEdificio.class);
    }

    @Override
    public List<EdificioGridDTO> findPagedByFiltro(EdificioFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jEdificios = query.getResultList();
        List<EdificioGridDTO> edificios = new ArrayList<>();
        if (jEdificios != null) {
            for (Object[] jEdificio : jEdificios) {
                EdificioGridDTO edificioGrid = new EdificioGridDTO();
                edificioGrid.setCodigo((Long) jEdificio[0]);
                edificioGrid.setDireccion((String) jEdificio[1]);
                edificioGrid.setPoblacion((String) jEdificio[2]);
                edificioGrid.setCp((String) jEdificio[3]);
                edificioGrid.setTelefono((String) jEdificio[4]);
                edificioGrid.setFax((String) jEdificio[5]);
                edificioGrid.setEmail((String) jEdificio[6]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jEdificio[7]));
                edificioGrid.setDescripcion(literal);
                edificios.add(edificioGrid);
            }
        }
        return edificios;
    }

    @Override
    public long countByFiltro(EdificioFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    private Query getQuery(boolean isTotal, EdificioFiltro filtro) {


        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JEdificio j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.direccion, j.poblacion, j.cp, j.telefono, j.fax, j.email, t.nombre FROM JEdificio j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        }

        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.codigo as string) LIKE :filtro OR LOWER(j.direccion) LIKE :filtro "
                    + " OR LOWER(j.poblacion) LIKE :filtro  " + "OR LOWER(j.cp) LIKE :filtro "
                    + " OR LOWER(j.telefono) LIKE :filtro " + "OR LOWER(j.fax) LIKE :filtro "
                    + " OR LOWER(j.email) LIKE :filtro OR LOWER(t.nombre) LIKE :filtro)");
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
    public Optional<JEdificio> findById(String id) {
        TypedQuery<JEdificio> query = entityManager.createNamedQuery(JEdificio.FIND_BY_ID, JEdificio.class);
        query.setParameter("id", id);
        List<JEdificio> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
