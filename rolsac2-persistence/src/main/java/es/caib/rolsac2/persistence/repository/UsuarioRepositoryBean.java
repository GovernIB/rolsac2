package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.persistence.model.JUsuarioEntidad;
import es.caib.rolsac2.persistence.model.pk.JUsuarioEntidadPK;
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

    public JUsuario findByIdentificador(String identificador) {
        TypedQuery<JUsuario> query = entityManager.createNamedQuery(JUsuario.FIND_BY_IDENTIFICADOR, JUsuario.class);
        query.setParameter("identificador", identificador);
        return query.getSingleResult();
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
                usuarioGridDTO.setNombre((String) jUsuario[2]);
                if (jUsuario[3] != null) {
                    usuarioGridDTO.setEmail((String) jUsuario[3]);
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
            sql = new StringBuilder("SELECT count(j) FROM JUsuario j LEFT OUTER JOIN JUsuarioEntidad usEnt on usEnt.usuario.codigo=j.codigo where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, j.nombre, j.email FROM JUsuario j LEFT OUTER JOIN JUsuarioEntidad usEnt on usEnt.usuario.codigo=j.codigo where 1 = 1 ");
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
        if (filtro.isRellenoEntidad()) {
            sql.append(" AND usEnt.entidad.codigo =:idEntidad");
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
        if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
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

    @Override
    public void anyadirNuevoUsuarioEntidad(JUsuario usuario, Long idEntidad) {
        JUsuarioEntidadPK jUsuarioEntidadPK = new JUsuarioEntidadPK();
        JUsuarioEntidad jUsuarioEntidad = new JUsuarioEntidad();
        jUsuarioEntidad.setUsuario(usuario);
        jUsuarioEntidad.setEntidad(entityManager.find(JEntidad.class, idEntidad));
        jUsuarioEntidadPK.setEntidad(idEntidad);
        jUsuarioEntidadPK.setUsuario(usuario.getCodigo());
        jUsuarioEntidad.setCodigo(jUsuarioEntidadPK);
        entityManager.persist(jUsuarioEntidad);
    }

    @Override
    public void mergeUsuarioEntidad(JUsuario usuario, List<Long> entidades) {

        List<Long> entidadesAsociadas = findEntidadesAsociadas(usuario.getCodigo());

        if (entidades.size() >= entidadesAsociadas.size()) {
            List<Long> entidadesExistentes = new ArrayList<>();
            for (Long idEntidad : entidades) {
                if (entidadesAsociadas.contains(idEntidad)) {
                    entidadesExistentes.add(idEntidad);
                    break;
                } else {
                    anyadirNuevoUsuarioEntidad(usuario, idEntidad);
                }
            }
            if (!entidadesExistentes.containsAll(entidadesAsociadas)) {
                for (Long idEntidad : entidadesAsociadas) {
                    if (!entidadesExistentes.contains(idEntidad)) {
                        eliminarUsuarioEntidad(usuario, idEntidad);
                    }
                }
            }
        } else {
            List<Long> entidadesExistentes = new ArrayList<>();
            for (Long idEntidad : entidadesAsociadas) {
                if (entidades.contains(idEntidad)) {
                    entidadesExistentes.add(idEntidad);
                    break;
                } else {
                    eliminarUsuarioEntidad(usuario, idEntidad);
                }

            }
            if (!entidadesExistentes.containsAll(entidades)) {
                for (Long idEntidad : entidades) {
                    if (!entidadesExistentes.contains(idEntidad)) {
                        anyadirNuevoUsuarioEntidad(usuario, idEntidad);
                    }
                }
            }
        }

    }

    @Override
    public void eliminarUsuarioEntidad(JUsuario usuario, Long idEntidad) {
        JUsuarioEntidadPK jUsuarioEntidadPK = new JUsuarioEntidadPK();
        jUsuarioEntidadPK.setUsuario(usuario.getCodigo());
        jUsuarioEntidadPK.setEntidad(idEntidad);
        JUsuarioEntidad jUsuarioEntidad = entityManager.find(JUsuarioEntidad.class, jUsuarioEntidadPK);
        entityManager.remove(jUsuarioEntidad);
    }

    @Override
    public List<Long> findEntidadesAsociadas(Long idUsuario) {
        String sql = "SELECT j.entidad.codigo FROM JUsuarioEntidad j WHERE j.usuario.codigo = :id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", idUsuario);
        List<Long> entidadesAsociadas = query.getResultList();
        return entidadesAsociadas;
    }
}
