package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JUnidadOrganica;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementación del repositorio de tipo de afectación.
 *
 * @author Indra
 */
@Stateless
@Local(UnidadOrganicaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UnidadOrganicaRepositoryBean extends AbstractCrudRepository<JUnidadOrganica, Long> implements UnidadOrganicaRepository {

    protected UnidadOrganicaRepositoryBean() {
        super(JUnidadOrganica.class);
    }

    @Override
    public List<JUnidadOrganica> listarUnidadesHijas(String codigoDir3Padre, Long idEntidad) {
        String sql = "SELECT p FROM JUnidadOrganica p WHERE p.codigoDir3Padre = :codDir3 AND p.entidad.codigo=:idEntidad";
        Query query = entityManager.createQuery(sql, JUnidadOrganica.class);
        query.setParameter("codDir3", codigoDir3Padre);
        query.setParameter("idEntidad", idEntidad);
        return query.getResultList();
    }

    @Override
    public JUnidadOrganica obtenerUnidadRaiz(Long idEntidad) {
        String sql = "SELECT p FROM JUnidadOrganica p WHERE p.codigoDir3Padre is null AND p.entidad.codigo =:idEntidad";
        Query query = entityManager.createQuery(sql, JUnidadOrganica.class);
        query.setParameter("idEntidad", idEntidad);
        return (JUnidadOrganica) query.getResultList().get(0);
    }

    @Override
    public List<JUnidadOrganica> findByEntidad(Long idEntidad) {
        String sql = "SELECT j FROM JUnidadOrganica j WHERE j.entidad.codigo = :idEntidad";
        Query query = entityManager.createQuery(sql, JUnidadOrganica.class);
        query.setParameter("idEntidad", idEntidad);
        return query.getResultList();
    }

    @Override
    public void eliminarRegistros(Long idEntidad) {
        String sql = "DELETE FROM JUnidadOrganica p WHERE p.entidad.codigo =:idEntidad";
        Query query = entityManager.createQuery(sql);
        query.setParameter("idEntidad", idEntidad);
        query.executeUpdate();
    }

}
