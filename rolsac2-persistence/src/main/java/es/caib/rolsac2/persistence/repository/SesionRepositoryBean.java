package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoFormaInicioConverter;
import es.caib.rolsac2.persistence.model.JSesion;
import es.caib.rolsac2.persistence.model.JTipoFormaInicio;
import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoFormaInicioFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de tipo de forma de inicio.
 *
 * @author Indra
 */
@Stateless
@Local(SesionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SesionRepositoryBean extends AbstractCrudRepository<JSesion, Long>
        implements SesionRepository {

    protected SesionRepositoryBean() {
        super(JSesion.class);
    }

    @Override
    public Boolean checkSesion(Long idUsuario) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JSesion.COUNT_BY_ID, Long.class);
        query.setParameter("idUsu", idUsuario);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

}
