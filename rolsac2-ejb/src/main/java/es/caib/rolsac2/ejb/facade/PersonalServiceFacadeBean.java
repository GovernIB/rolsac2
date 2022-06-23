package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.PersonalConverter;
import es.caib.rolsac2.persistence.model.JPersonal;
import es.caib.rolsac2.persistence.repository.PersonalRepository;
import es.caib.rolsac2.persistence.repository.PersonalRepositoryBean;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalDTO;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.filtro.PersonalFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
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
@Local(PersonalServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonalServiceFacadeBean implements PersonalServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalRepositoryBean.class);

    @Resource
    private SessionContext context;
    @Inject
    private PersonalRepository personalRepository;

    @Inject
    private PersonalConverter converter;

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(PersonalDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {

        //Principal p = context.getCallerPrincipal();
        //context.isCallerInRole()

        // Comprovam que el codiSia no existeix ja (
        if (dto.getId() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JPersonal jpersonal = converter.createEntity(dto);
        jpersonal.setUnidadAdministrativa(dto.getIdUnidadAdministrativa());
        personalRepository.create(jpersonal);
        return jpersonal.getId();
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(PersonalDTO dto) throws RecursoNoEncontradoException {
        JPersonal jpersonal = personalRepository.getReference(dto.getId());
        converter.mergeEntity(jpersonal, dto);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JPersonal personal = personalRepository.getReference(id);
        personalRepository.delete(personal);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public PersonalDTO findById(Long id) {

        JPersonal jpersonal = personalRepository.getReference(id);
        PersonalDTO personalDTO = converter.createDTO(jpersonal);
        return personalDTO;
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<PersonalGridDTO> findByFiltro(PersonalFiltro filtro) {
        List<PersonalGridDTO> items = personalRepository.findPagedByFiltro(filtro);
        long total = personalRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }

    @Override
    public int countByFiltro(PersonalFiltro filtro) {
        return (int) personalRepository.countByFiltro(filtro);
    }
}
