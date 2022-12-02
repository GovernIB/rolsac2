package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(UsuarioRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UsuarioRepositoryBean extends AbstractCrudRepository<JUsuario, Long> implements UsuarioRepository {

    protected UsuarioRepositoryBean() {
        super(JUsuario.class);
    }

    @Override
    public Optional<JUsuario> findById(String id) {
        TypedQuery<JUsuario> query = entityManager.createNamedQuery(JUsuario.FIND_BY_ID, JUsuario.class);
        query.setParameter("codigo", id);
        List<JUsuario> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<UsuarioGridDTO> findPagedByFiltro(UsuarioFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jUsuarios = query.getResultList();
        List<UsuarioGridDTO> usuario = new ArrayList<>();
        if (jUsuarios != null) {
            for (Object[] jUsuario : jUsuarios) {
                UsuarioGridDTO usuarioGridDTO = new UsuarioGridDTO();
                usuarioGridDTO.setCodigo((Long) jUsuario[0]);
                usuarioGridDTO.setIdentificador((String) jUsuario[1]);
                if (jUsuario[2] != null) {
                    usuarioGridDTO.setEntidad(((JEntidad) jUsuario[2]).getDescripcion(filtro.getIdioma()));
                }
                usuarioGridDTO.setNombre((String) jUsuario[3]);
                if (jUsuario[4] != null) {
                    usuarioGridDTO.setEmail((String) jUsuario[4]);
                }
                usuario.add(usuarioGridDTO);
            }
        }
        return usuario;
    }


    @Override
    public long countByFiltro(UsuarioFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    private Query getQuery(boolean isTotal, UsuarioFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JUsuario j where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, j.entidad, j.nombre, j.email FROM JUsuario j where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and LOWER(j.nombre) LIKE :filtro OR LOWER(j.identificador) LIKE :filtro OR LOWER(j.email) LIKE :filtro");
        }
        if (filtro.isRellenoId()) {
            sql.append(" and LOWER(j.id) like :id ");
        }
        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER(j.identificador) like :identificador ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoId()) {
            query.setParameter("id", "%" + filtro.getCodigo());
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }

        return query;
    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Boolean checkIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JUsuario.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

}
