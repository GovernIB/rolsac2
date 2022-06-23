package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.PersonalConverter;
import es.caib.rolsac2.persistence.repository.PersonalRepository;
import es.caib.rolsac2.persistence.repository.PersonalRepositoryBean;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
@Local(UnidadAdministrativaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UnidadAdministrativaServiceFacadeBean implements UnidadAdministrativaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalRepositoryBean.class);

    @Resource
    private SessionContext context;
    @Inject
    private PersonalRepository personalRepository;

    @Inject
    private PersonalConverter converter;

    @Override
    public List<UnidadAdministrativaDTO> getHijos(Long idUnitat) {
        List lista = new ArrayList<>();
        if (idUnitat == null) {
            return lista;
        } else if (idUnitat == 1l) {
            UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO(2l, "Conselleria d'educació", "Conselleria de educación");
            lista.add(ua);
        } else if (idUnitat == 2l) {
            UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO(3l, "Secretaría d'educació", "Secreteria de educación");
            lista.add(ua);
        } else if (idUnitat == 3l) {
            UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO(4l, "SubSecretaría d'educació", "SubSecreteria de educación");
            lista.add(ua);
        } else {
            //long valor = (long) Math.random();
            //UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO(valor, "Texte " + valor, " Texto " + valor);
        }
        return lista;
    }

    @Override
    public Long create(UnidadAdministrativaDTO dto) {
        return null;
    }

    @Override
    public void update(UnidadAdministrativaDTO dto) throws RecursoNoEncontradoException {

    }

    @Override
    public void delete(Long id) throws RecursoNoEncontradoException {

    }

    @Override
    public UnidadAdministrativaDTO findById(Long id) {
        return null;
    }

    @Override
    public Pagina<PersonalGridDTO> findByFiltro(UnidadAdministrativaFiltro filtro) {
        return null;
    }

    @Override
    public int countByFiltro(UnidadAdministrativaFiltro filtro) {
        return 0;
    }
}
