package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.repository.MigracionRepository;
import es.caib.rolsac2.service.facade.MigracionServiceFacade;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Servicio que da soporte a la entidad de negocio Peticionstancia.
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(MigracionServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MigracionServiceFacadeBean implements MigracionServiceFacade {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private MigracionRepository migracionRepository;


    @Override
    public String ejecutarMetodo(String metodo, Long param1, Long param2) {
        return migracionRepository.ejecutarMetodo(metodo, param1, param2);
    }
}
