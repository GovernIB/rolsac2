package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.PersonalConverter;
import es.caib.rolsac2.persistence.repository.PersonalRepository;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TestServiceFacade;
import es.caib.rolsac2.service.model.filtro.PersonalFiltro;

import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Implementación de los casos de uso de mantenimiento de personal.
 * Es responsabilidad de esta caap definir el limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma
 * els errors JPA amb les excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(TestServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@PermitAll
public class TestServiceFacadeBean implements TestServiceFacade {

    @Inject
    private PersonalRepository personalRepository;

    @Inject
    private PersonalConverter converter;

    @Override
    public boolean test() {
        return true;
    }

    @Override
    public String test2() {
        try {
            personalRepository.findById(1l);
        } catch (Exception e) {
            return "Exception 1";
        }

        PersonalFiltro filtro = new PersonalFiltro();
        filtro.setIdUA(1l);

        try {
            personalRepository.countByFiltro(filtro);
        } catch (Exception e) {
            return "Exception 2";
        }


        try {
            personalRepository.findPagedByFiltro(filtro);
        } catch (Exception e) {
            return "Exception 3";
        }
        return "Fin";
    }
}
